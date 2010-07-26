package com.tomecode.soa.oracle10g.parser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.tomecode.soa.bpel.activity.Activity;
import com.tomecode.soa.bpel.activity.Assign;
import com.tomecode.soa.bpel.activity.Case;
import com.tomecode.soa.bpel.activity.CaseOtherwise;
import com.tomecode.soa.bpel.activity.Catch;
import com.tomecode.soa.bpel.activity.Email;
import com.tomecode.soa.bpel.activity.Fax;
import com.tomecode.soa.bpel.activity.FlowN;
import com.tomecode.soa.bpel.activity.HumanTask;
import com.tomecode.soa.bpel.activity.Invoke;
import com.tomecode.soa.bpel.activity.OnAlarm;
import com.tomecode.soa.bpel.activity.OnMessage;
import com.tomecode.soa.bpel.activity.Pager;
import com.tomecode.soa.bpel.activity.PartnerLink;
import com.tomecode.soa.bpel.activity.Receive;
import com.tomecode.soa.bpel.activity.Reply;
import com.tomecode.soa.bpel.activity.Sms;
import com.tomecode.soa.bpel.activity.Throw;
import com.tomecode.soa.bpel.activity.Transform;
import com.tomecode.soa.bpel.activity.Variable;
import com.tomecode.soa.bpel.activity.Voice;
import com.tomecode.soa.bpel.activity.Wait;
import com.tomecode.soa.bpel.activity.While;
import com.tomecode.soa.bpel.activity.Assign.AssignOperation;
import com.tomecode.soa.bpel.activity.Assign.OperationType;
import com.tomecode.soa.oracle10g.Workspace;
import com.tomecode.soa.oracle10g.bpel.BpelOperations;
import com.tomecode.soa.oracle10g.bpel.BpelProcessStrukture;
import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.oracle10g.bpel.Operation;
import com.tomecode.soa.oracle10g.bpel.PartnerLinkBinding;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.project.UnknownProject;
import com.tomecode.soa.wsdl.parser.WsdlParser;

/**
 * Parser for Oracle 10g BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class Ora10gBpelParser extends AbstractParser {

	/**
	 * list of parsed process
	 */
	private final List<BpelProject> parsedProcess = new ArrayList<BpelProject>();

	/**
	 * WSDL parser
	 */
	private final WsdlParser wsdlParser;

	/**
	 * Constructor
	 */
	public Ora10gBpelParser() {
		wsdlParser = new WsdlParser();
	}

	/**
	 * parse BPEL process from URL
	 * 
	 * @param data
	 * @return
	 */
	public final String getProcessNameFromUrl(String data) {
		String code = "/orabpel/";
		int i = data.indexOf(code);
		if (i == -1)
			return null;
		String partial = data.substring(i + code.length());
		i = partial.indexOf("/");
		partial = partial.substring(i + 1);
		return partial.substring(0, partial.indexOf("/"));
	}

	/**
	 * parse BPEL by BPEL project folder
	 * 
	 * @param file
	 * @return
	 * @throws ServiceParserException
	 */
	public final BpelProject parseBpelXml(File file) throws ServiceParserException {
		File bpelXmlFile = file;

		if (file.isDirectory() && file.getName().endsWith(File.separator + "bpel")) {
			bpelXmlFile = new File(file + File.separator + "bpel.xml");
		} else if (file.isDirectory()) {
			bpelXmlFile = new File(file + File.separator + "bpel" + File.separator + "bpel.xml");
		}

		Element bpelXml = parseXml(bpelXmlFile);
		BpelProject bpelProject = parseBpelXml(bpelXml, bpelXmlFile);

		File wsdl = new File(bpelXmlFile.getParent() + File.separator + bpelProject.toString() + ".wsdl");
		bpelProject.setWsdl(wsdlParser.parseWsdl(wsdl));
		return bpelProject;
	}

	/**
	 * 
	 * parse bpel.xml
	 * 
	 * @param eBpelXml
	 *            parsed bpel.xml
	 * @param bpelXmlFile
	 * @return
	 * @throws ServiceParserException
	 */
	private final BpelProject parseBpelXml(Element eBpelXml, File bpelXmlFile) throws ServiceParserException {
		Element eBPELProcess = eBpelXml.element("BPELProcess");
		BpelProject bpelProcess = new BpelProject(eBPELProcess.attributeValue("id"), eBPELProcess.attributeValue("src"), bpelXmlFile);

		if (!isParsedProcess(bpelProcess)) {
			parsedProcess.add(bpelProcess);
		}

		List<?> eListOfPartnerLinkBindinds = eBPELProcess.element("partnerLinkBindings").elements("partnerLinkBinding");
		for (Object e : eListOfPartnerLinkBindinds) {
			Element ePartnerLink = (Element) e;
			PartnerLinkBinding partnerLinkBinding = new PartnerLinkBinding(ePartnerLink.attributeValue("name"), parseWsdlLocation(ePartnerLink));

			bpelProcess.addPartnerLinkBinding(partnerLinkBinding);
			parseBpelByWsdl(partnerLinkBinding);
		}

		Element bpelRootElement = parseXml(new File(bpelXmlFile.getParentFile() + File.separator + bpelProcess.getSrc()));
		parseBpelOperations(bpelRootElement, bpelProcess);
		parseBpelProcessStrukture(bpelRootElement, bpelProcess.getBpelProcessStrukture());
		return bpelProcess;
	}

	/**
	 * check, if process is parsed
	 * 
	 * @param newBpelProcess
	 * @return
	 */
	private final boolean isParsedProcess(BpelProject newBpelProcess) {
		for (BpelProject pBpelProcess : parsedProcess) {
			if (pBpelProcess.getBpelXmlFile().toString().equals(newBpelProcess.getBpelXmlFile().toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * parse all BPEL process to tree structure
	 * 
	 * @param root
	 * @param strukture
	 */
	private final void parseBpelProcessStrukture(Element root, BpelProcessStrukture strukture) {
		Activity activity = new Activity(root.getName());
		strukture.addActivity(activity);
		parseBpelProcessActivities(root.elements(), activity, strukture);
	}

	/**
	 * 
	 * parse all activities in the BPEL process
	 * 
	 * @param elements
	 * @param root
	 */
	private final void parseBpelProcessActivities(List<?> elements, Activity root, BpelProcessStrukture strukture) {
		for (Object e : elements) {
			Element element = (Element) e;
			if (element.getName().equals("sequence") || element.getName().equals("switch") || element.getName().equals("flow") || element.getName().equals("faultHandlers")
					|| element.getName().equals("eventHandlers") || element.getName().equals("catchAll") || element.getName().equals("compensationHandler") || element.getName().equals("pick")
					|| element.getName().equals("variables") || element.getName().equals("partnerLinks")) {
				Activity activity = new Activity(element.getName(), element.attributeValue("name"));
				root.addActivity(activity);
				parseBpelProcessActivities(element.elements(), activity, strukture);
			} else if (element.getName().equals("variable")) {
				Variable variable = new Variable(element.attributeValue("name"), element.attributeValue("messageType"));
				root.addActivity(variable);
			} else if (element.getName().equals("partnerLink")) {
				PartnerLink partnerLink = new PartnerLink(element.attributeValue("name"), element.attributeValue("partnerLinkType"), element.attributeValue("myRole"), element
						.attributeValue("partnerRole"));
				root.addActivity(partnerLink);
			} else if (element.getName().equals("receive")) {
				root.addActivity(new Receive(element.attributeValue("name"), element.attributeValue("variable"), element.attributeValue("partnerLink"), element.attributeValue("operation")));
			} else if (element.getName().equals("invoke")) {
				root.addActivity(new Invoke(element.attributeValue("name"), element.attributeValue("inputVariable"), element.attributeValue("outputVariable"), element.attributeValue("partnerLink"),
						element.attributeValue("operation")));
			} else if (element.getName().equals("reply")) {
				root.addActivity(new Reply(element.attributeValue("name"), element.attributeValue("variable"), element.attributeValue("partnerLink"), element.attributeValue("operation")));
			} else if (element.getName().equals("throw")) {
				root.addActivity(new Throw(element.attributeValue("name"), element.attributeValue("faultVariable")));
			} else if (element.getName().equals("onMessage")) {
				OnMessage onMessageActivity = new OnMessage(element.attributeValue("variable"), element.attributeValue("partnerLink"), element.attributeValue("operation"), element
						.attributeValue("headerVariable"));
				root.addActivity(onMessageActivity);
				parseBpelProcessActivities(element.elements(), onMessageActivity, strukture);
			} else if (element.getName().equals("catch")) {
				Catch catchActivity = new Catch(element.attributeValue("faultName"), element.attributeValue("faultVariable"));
				root.addActivity(catchActivity);
				parseBpelProcessActivities(element.elements(), catchActivity, strukture);
			} else if (element.getName().equals("assign")) {
				parseAssignActivity(element, root);
			} else if (element.getName().equals("case")) {
				Element eAnnotation = element.element("annotation");
				if (eAnnotation != null) {
					Element ePattern = eAnnotation.element("pattern");
					if (ePattern != null) {
						if (ePattern.attributeValue("patternName") != null && "case".equals(ePattern.attributeValue("patternName"))) {
							Case caseActivity = new Case(ePattern.getText(), getVariableFromExpression(element.attributeValue("condition")));
							root.addActivity(caseActivity);
							parseBpelProcessActivities(element.elements(), caseActivity, strukture);
						}
					}
				} else {
					Case caseActivity = new Case(element.getName(), getVariableFromExpression(element.attributeValue("condition")));
					root.addActivity(caseActivity);
					parseBpelProcessActivities(element.elements(), caseActivity, strukture);

				}
			} else if (element.getName().equals("otherwise")) {
				CaseOtherwise caseOtherwise = new CaseOtherwise("otherwise");
				root.addActivity(caseOtherwise);
				parseBpelProcessActivities(element.elements(), caseOtherwise, strukture);

			} else if (element.getName().equals("scope")) {
				parseSpecialScopes(element, root, strukture);
			} else if (element.getName().equals("onAlarm")) {
				String variable = getVariableFromExpression(element.attributeValue("until"));
				if (variable == null) {
					variable = getVariableFromExpression(element.attributeValue("for"));
				}
				OnAlarm onAlarm = new OnAlarm(variable);
				root.addActivity(onAlarm);
				parseBpelProcessActivities(element.elements(), onAlarm, strukture);
			} else if (element.getName().equals("wait")) {
				String variable = getVariableFromExpression(element.attributeValue("until"));
				if (variable == null) {
					variable = getVariableFromExpression(element.attributeValue("for"));
				}
				Wait wait = new Wait(element.attributeValue("name"), variable);
				root.addActivity(wait);

			} else if (element.getName().equals("while")) {
				While whileActivity = new While(element.attributeValue("name"), element.attributeValue("condition"), getVariableFromExpression(element.attributeValue("condition")));
				root.addActivity(whileActivity);
				parseBpelProcessActivities(element.elements(), whileActivity, strukture);
			} else if (element.getName().equals("flowN")) {
				FlowN flowN = new FlowN(element.attributeValue("name"), getVariableFromExpression(element.attributeValue("N")), element.attributeValue("indexVariable"));
				root.addActivity(flowN);
				parseBpelProcessActivities(element.elements(), flowN, strukture);
			} else if (element.getName().equals("annotation")) {
				//
			} else {
				Activity activity = new Activity(element.getName(), element.attributeValue("name"));
				root.addActivity(activity);
			}
		}
	}

	/**
	 * parse assign activity
	 * 
	 * @param element
	 * @param root
	 */
	private final void parseAssignActivity(Element element, Activity root) {
		Element eAnnotation = element.element("annotation");
		if (eAnnotation != null) {
			Element ePattern = eAnnotation.element("pattern");
			if (ePattern != null && "transformation".equals(ePattern.getText())) {
				Transform transform = parseTransform(element);// new
				// Transform(element.attributeValue("name"),
				// null, null);
				root.addActivity(transform);
			} else {
				Assign assign = new Assign(element.attributeValue("name"));
				parseAssignOperation(assign, element);
				root.addActivity(assign);
			}

		} else {
			Assign assign = new Assign(element.attributeValue("name"));
			parseAssignOperation(assign, element);
			root.addActivity(assign);
		}

	}

	/**
	 * parse operation in assign
	 * 
	 * @param assign
	 * @param element
	 */
	private final void parseAssignOperation(Assign assign, Element eAssign) {
			List<?> list = eAssign.elements();
			for (Object e : list) {
				Element eOperation = (Element) e;
				// System.out.println(eOperation.getName());
			if (eOperation.getName().equals("copy")) {
				assign.addOperations(parseOperationCopyInAssign(OperationType.COPY, eOperation));
			} else if (eOperation.getName().equals("append")) {
				assign.addOperations(parseOperationCopyInAssign(OperationType.APPEND, eOperation));
			} else if (eOperation.getName().equals("insertAfter")) {
				assign.addOperations(parseOperationCopyInAssign(OperationType.INSERT_AFTER, eOperation));
			} else if (eOperation.getName().equals("insertBefore")) {
				assign.addOperations(parseOperationCopyInAssign(OperationType.INSERT_BEFORE, eOperation));
			} else if (eOperation.getName().equals("copyList")) {
				assign.addOperations(parseOperationCopyInAssign(OperationType.COPYLIST, eOperation));
			} else if (eOperation.getName().equals("remove")) {
				assign.addOperations(parseOperationCopyInAssign(OperationType.REMOVE, eOperation));
			} else if (eOperation.getName().equals("rename")) {
				assign.addOperations(parseOperationCopyInAssign(OperationType.RENAME, eOperation));
			}

		}
	}

	/**
	 * parse expression
	 * 
	 * @param expression
	 * @return
	 */
	private final String getVariableFromExpression(String expression) {
		if (expression != null) {
			int index = expression.indexOf("getVariableData('");
			if (index != -1) {
				expression = expression.substring(index + "getVariableData('".length());
				index = expression.indexOf("'");
				if (index != -1) {
					return expression.substring(0, index);
				}
			}
		}
		return null;
	}

	/**
	 * parse {@link Transform} activity
	 * 
	 * @param element
	 * @return
	 */
	private final Transform parseTransform(Element element) {
		String name = element.attributeValue("name");
		String fromVariable = null, toVariable = null;

		Element eCopy = element.element("copy");
		if (eCopy == null) {
			return new Transform(name, fromVariable, toVariable);
		}

		Element eFrom = eCopy.element("from");

		if (eFrom != null) {
			if (eFrom.attributeValue("variable") != null) {
				fromVariable = eFrom.attributeValue("variable");
			} else if (eFrom.attributeValue("expression") != null) {
				fromVariable = eFrom.attributeValue("expression");
				if (fromVariable != null) {
					fromVariable = getVariableFromExpression(fromVariable);
				}
			}
		}

		Element eTo = eCopy.element("to");
		if (eTo != null) {
			toVariable = eTo.attributeValue("variable");
		}

		return new Transform(name, fromVariable, toVariable);
	}

	/**
	 * parse assign operation = copy
	 * 
	 * @param eOperation
	 * @return
	 */
	private final AssignOperation parseOperationCopyInAssign(OperationType type, Element eOperation) {
		Element eFrom = eOperation.element("from");

		String fromPartnerLink = null, toPartnerLink = null;
		String from = null;
		if (eFrom != null) {
			if (eFrom.attributeValue("variable") != null) {
				from = eFrom.attributeValue("variable");
			} else if (eFrom.attributeValue("expression") != null) {
				from = eFrom.attributeValue("expression");
				if (from != null) {
					from = getVariableFromExpression(from);
				}
			} else if (eFrom.attributeValue("partnerLink") != null) {
				fromPartnerLink = eFrom.attributeValue("partnerLink");
			}
		}
		String to = null;
		Element eTo = eOperation.element("to");
		if (eTo != null) {
			to = eTo.attributeValue("variable");
			if (eTo.attributeValue("partnerLink") != null) {
				toPartnerLink = eTo.attributeValue("partnerLink");
			}
		}

		return new AssignOperation(type, from, to, fromPartnerLink, toPartnerLink);
	}

	/**
	 * parse BPEL activities by type
	 * 
	 * @param element
	 * @return
	 */
	private final Activity parseBpelActivity(Element element) {

		if (element.getName().equals("receive")) {
			return new Receive(element.attributeValue("name"), element.attributeValue("variable"), element.attributeValue("partnerLink"), element.attributeValue("operation"));
		} else if (element.getName().equals("invoke")) {
			return new Invoke(element.attributeValue("name"), element.attributeValue("inputVariable"), element.attributeValue("outputVariable"), element.attributeValue("partnerLink"), element
					.attributeValue("operation"));
		} else if (element.getName().equals("reply")) {
			return new Reply(element.attributeValue("name"), element.attributeValue("variable"), element.attributeValue("partnerLink"), element.attributeValue("operation"));
		} else if (element.getName().equals("throw")) {
			return new Throw(element.attributeValue("name"), element.attributeValue("faultVariable"));
		} else if (element.getName().equals("onMessage")) {
			return new OnMessage(element.attributeValue("variable"), element.attributeValue("partnerLink"), element.attributeValue("operation"), element.attributeValue("headerVariable"));
		} else if (element.getName().equals("catch")) {
			return new Catch(element.attributeValue("faultName"), element.attributeValue("faultVariable"));
		} else if (element.getName().equals("assign")) {
			Element eAnnotation = element.element("annotation");
			if (eAnnotation != null) {
				Element ePattern = eAnnotation.element("pattern");
				if (ePattern != null && "transformation".equals(ePattern.getText())) {
					return new Transform(element.attributeValue("name"), null, null);
				}
			}
			return new Assign(element.attributeValue("name"));
		} else if (element.getName().equals("case")) {
			Element eAnnotation = element.element("annotation");
			if (eAnnotation != null) {
				Element ePattern = eAnnotation.element("pattern");
				if (ePattern != null) {
					if (ePattern.attributeValue("patternName") != null && "case".equals(ePattern.attributeValue("patternName"))) {
						return new Case(ePattern.getText(), getVariableFromExpression(element.attributeValue("condition")));
					}
				}
			}
		} else if (element.getName().equals("otherwise")) {
			return new CaseOtherwise("otherwise");
		} else if (element.getName().equals("scope")) {
			Element eAnnotation = element.element("annotation");
			if (eAnnotation != null) {
				Element ePattern = eAnnotation.element("pattern");
				if (ePattern != null) {
					String patternName = ePattern.attributeValue("patternName");
					if (patternName != null) {
						if (patternName.endsWith(":email")) {
							return new Email(element.attributeValue("name"));
						} else if (patternName.endsWith(":fax")) {
							return new Fax(element.attributeValue("name"));
						} else if (patternName.endsWith(":sms")) {
							return new Sms(element.attributeValue("name"));
						} else if (patternName.endsWith(":voice")) {
							return new Voice(element.attributeValue("name"));
						} else if (patternName.endsWith(":pager")) {
							return new Pager(element.attributeValue("name"));
						} else if (patternName.endsWith(":workflow")) {
							return new HumanTask(element.attributeValue("name"));
						}
					}
				}
			}
		} else if (element.getName().equals("onAlarm")) {
			return new OnAlarm(null);
		} else if (element.getName().equals("wait")) {
			return new Wait(element.attributeValue("name"), null);
		} else if (element.getName().equals("while")) {
			return new While(element.attributeValue("name"), null, null);
		} else if (element.getName().equals("flowN")) {
			return new FlowN(element.attributeValue("name"), null, null);
		}

		return new Activity(element.getName(), element.attributeValue("name"));
	}

	/**
	 * parse special scopes for example: email, sms, voice and fax
	 * 
	 * @param element
	 * @param root
	 * @param strukture
	 */
	private final void parseSpecialScopes(Element element, Activity root, BpelProcessStrukture strukture) {
		Element eAnnotation = element.element("annotation");
		if (eAnnotation != null) {
			Element ePattern = eAnnotation.element("pattern");
			if (ePattern != null) {
				String patternName = ePattern.attributeValue("patternName");
				if (patternName != null) {
					if (patternName.endsWith(":email")) {
						Email email = new Email(element.attributeValue("name"));
						root.addActivity(email);
						parseBpelProcessActivities(element.elements(), email, strukture);
						return;
					} else if (patternName.endsWith(":fax")) {
						Fax fax = new Fax(element.attributeValue("name"));
						root.addActivity(fax);
						parseBpelProcessActivities(element.elements(), fax, strukture);
						return;
					} else if (patternName.endsWith(":sms")) {
						Sms sms = new Sms(element.attributeValue("name"));
						root.addActivity(sms);
						parseBpelProcessActivities(element.elements(), sms, strukture);
						return;
					} else if (patternName.endsWith(":voice")) {
						Voice voice = new Voice(element.attributeValue("name"));
						root.addActivity(voice);
						parseBpelProcessActivities(element.elements(), voice, strukture);
						return;
					} else if (patternName.endsWith(":pager")) {
						Pager pager = new Pager(element.attributeValue("name"));
						root.addActivity(pager);
						parseBpelProcessActivities(element.elements(), pager, strukture);
						return;
					} else if (patternName.endsWith(":workflow")) {
						HumanTask humanTask = new HumanTask(element.attributeValue("name"));
						root.addActivity(humanTask);
						parseBpelProcessActivities(element.elements(), humanTask, strukture);
						return;
					}

				}
			}
		}
		Activity activity = new Activity(element.getName(), element.attributeValue("name"));
		root.addActivity(activity);
		parseBpelProcessActivities(element.elements(), activity, strukture);
	}

	/**
	 * parse all partnerlinks in the BPEL process
	 * 
	 * @param element
	 * @param bpelOperations
	 * @throws ServiceParserException
	 */
	private void parseBpelOperations(Element element, BpelProject bpelProject) throws ServiceParserException {
		List<?> eList = element.element("partnerLinks").elements("partnerLink");
		for (Object e : eList) {
			Element ePartnerLink = (Element) e;
			String partnerLinkName = ePartnerLink.attributeValue("name");
			findUsageForPartnerLink(partnerLinkName, bpelProject, element);
		}
	}

	/**
	 * find usage for partner link
	 * 
	 * @param partnerLinkName
	 * @param bpelOperations
	 * @param root
	 */
	private final void findUsageForPartnerLink(String partnerLinkName, BpelProject bpelProject, Element root) {
		List<?> listOfElements = root.elements();
		if (listOfElements != null) {
			for (Object e : listOfElements) {
				Element element = (Element) e;
				if (element.getName().equals("receive") || element.getName().equals("invoke") || element.getName().equals("reply") || element.getName().equals("onMessage")) {

					if (element.attributeValue("partnerLink") == null) {
						// TODO: ///
						new NullPointerException("").printStackTrace();
					} else {
						if (element.attributeValue("partnerLink").equals(partnerLinkName)) {
							BpelOperations bpelOperations = bpelProject.getBpelOperations();
							Operation operation = new Operation(element.getName(), element.attributeValue("name"), element.attributeValue("operation"), bpelProject, bpelOperations.getBpelProcess()
									.findPartnerLinkBinding(element.attributeValue("partnerLink")), getOperationPath(element));

							if (partnerLinkName.equals("BPELProcess11")) {
								toString();
							}
							bpelOperations.addOperation(operation);
						}
					}
				}
				findUsageForPartnerLink(partnerLinkName, bpelProject, element);
			}
		}
	}

	/**
	 * 
	 * find operation path
	 * 
	 * 
	 * @param element
	 * @return
	 */
	private final List<Activity> getOperationPath(Element element) {
		List<Activity> activities = new ArrayList<Activity>();
		while (element.getParent() != null) {

			Activity activity = parseBpelActivity(element);
			activities.add(activity);
			element = element.getParent();

		}
		return activities;
	}

	/**
	 * parse BPEL in by WSDL
	 * 
	 * @param wsdlLocation
	 * @return
	 * @throws MalformedURLException
	 * @throws DocumentException
	 */
	public final void parseBpelByWsdl(PartnerLinkBinding partnerLinkBinding) throws ServiceParserException {
		try {
			URL url = new URL(partnerLinkBinding.getWsdlLocation());

			if (url.getProtocol().equals("http") || url.getProtocol().equals("https")) {
				String processName = getProcessNameFromUrl(url.toString());

				BpelProject bpelProject = findParsedProcess(processName, partnerLinkBinding.getParent().getBpelXmlFile());
				if (bpelProject == null) {
					partnerLinkBinding.setUnknownProject(new UnknownProject(partnerLinkBinding));
				} else {
					partnerLinkBinding.setDependencyBpelProject(bpelProject);
				}

				// partnerLinkBinding.setDependencyBpelProject(findParsedProcess(partnerLinkBinding.getParent()));
			} else {
				// parse file dependencies
				File file = new File(url.getFile());
				BpelProject parseBpelProcess = findParsedProcess(file);
				if (parseBpelProcess != null) {
					partnerLinkBinding.setDependencyBpelProject(parseBpelProcess);// setDependencyProject(parseBpelProcess);
				} else {
					parseBpelXml(file.getParentFile());
				}
			}

		} catch (Exception e) {
			int index = partnerLinkBinding.getWsdlLocation().lastIndexOf(".");
			if (index != -1) {
				String processName = partnerLinkBinding.getWsdlLocation().substring(0, index);
				BpelProject findBpelProject = findParsedProcess(processName, partnerLinkBinding.getParent().getBpelXmlFile());
				if (findBpelProject != null) {
					partnerLinkBinding.setDependencyBpelProject(findBpelProject);
				} else {
					partnerLinkBinding.setUnknownProject(new UnknownProject(partnerLinkBinding));
				}
			} else {
				partnerLinkBinding.setUnknownProject(new UnknownProject(partnerLinkBinding));
			}
		}
	}

	/**
	 * find {@link BpelProject} in list of {@link #parsedProcess} by bpel.xml
	 * 
	 * @param file
	 * @return
	 */
	private final BpelProject findParsedProcess(File file) {
		if (file.getName().endsWith("BPELProcess7.wsdl")) {
			file.toString();
		}
		if (file.getName().endsWith(".wsdl") || file.getName().endsWith("?wsdl")) {
			file = new File(file.getParent() + File.separator + "bpel.xml");
		}
		for (BpelProject bpelProcess : parsedProcess) {
			if (bpelProcess.getBpelXmlFile().equals(file)) {
				return bpelProcess;
			}
		}
		return null;
	}

	/**
	 * find {@link BpelProject} in list of {@link #parsedProcess}
	 * 
	 * @param processName
	 * @return if not found return null
	 */
	private final BpelProject findParsedProcess(String name, File bpelXml) {
		for (BpelProject bpelProcess : parsedProcess) {
			if (bpelProcess.getId() != null) {
				if (bpelProcess.getId().equals(name)) {
					if (compareByWorksapce(bpelProcess.getBpelXmlFile(), bpelXml)) {
						return bpelProcess;
					}
				}
			} else if (bpelProcess.getSrc() != null) {
				if (bpelProcess.getSrc().equals(name)) {
					if (compareByWorksapce(bpelProcess.getBpelXmlFile(), bpelXml)) {
						return bpelProcess;
					}
				}
			}
		}
		return null;
	}

	/**
	 * parse wsdlLocation attribute in bpel.xml
	 * 
	 * @param ePartnerLink
	 * @return
	 */
	private final String parseWsdlLocation(Element ePartnerLink) {
		List<?> properties = ePartnerLink.elements("property");
		for (Object e : properties) {
			Element eProperty = (Element) e;
			if (eProperty.attributeValue("name").equals("wsdlLocation")) {
				return eProperty.getTextTrim();
			}
		}
		return null;
	}

	/**
	 * compare two {@link BpelProject} by {@link Workspace}
	 * 
	 * @param target
	 * @param source
	 * @return
	 */
	private final boolean compareByWorksapce(File targetBpelXml, File sourceBpelXml) {
		File targetFile = findJws(targetBpelXml);
		File sourceFile = findJws(sourceBpelXml);
		if (targetFile == null || sourceFile == null) {
			return false;
		}
		return targetFile.toString().equals(sourceFile.toString());
	}

	private final File findJws(File file) {
		while (null != (file = file.getParentFile())) {
			File[] files = file.listFiles();
			if (files != null) {
				for (File f : files) {
					if (f.isFile() && f.getName().endsWith(".jws")) {
						return f;
					}
				}
			}
		}

		// if (file != null) {
		// File[] files = file.getParentFile().listFiles();
		// if (files != null) {
		// for (File f : files) {
		// if (f.isFile() && f.getName().endsWith(".jws")) {
		// return f;
		// }
		// if (file.isDirectory()) {
		// File jws = findAllJws(f.getParentFile());
		// if (jws != null) {
		// return jws;
		// }
		// }
		//
		// }
		// }
		// }
		return null;
	}
}
