package com.tomecode.soa.ora.suite10g.parser;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tomecode.soa.activity.Activity;
import com.tomecode.soa.activity.ActivityType;
import com.tomecode.soa.ora.suite10g.activity.Assign;
import com.tomecode.soa.ora.suite10g.activity.Assign.AssignOperation;
import com.tomecode.soa.ora.suite10g.activity.Assign.OperationType;
import com.tomecode.soa.ora.suite10g.activity.Case;
import com.tomecode.soa.ora.suite10g.activity.CaseOtherwise;
import com.tomecode.soa.ora.suite10g.activity.Catch;
import com.tomecode.soa.ora.suite10g.activity.CatchAll;
import com.tomecode.soa.ora.suite10g.activity.CompensationHandler;
import com.tomecode.soa.ora.suite10g.activity.Email;
import com.tomecode.soa.ora.suite10g.activity.Empty;
import com.tomecode.soa.ora.suite10g.activity.EventHandlers;
import com.tomecode.soa.ora.suite10g.activity.FaultHandlers;
import com.tomecode.soa.ora.suite10g.activity.Fax;
import com.tomecode.soa.ora.suite10g.activity.Flow;
import com.tomecode.soa.ora.suite10g.activity.FlowN;
import com.tomecode.soa.ora.suite10g.activity.HumanTask;
import com.tomecode.soa.ora.suite10g.activity.Invoke;
import com.tomecode.soa.ora.suite10g.activity.OnAlarm;
import com.tomecode.soa.ora.suite10g.activity.OnMessage;
import com.tomecode.soa.ora.suite10g.activity.Pager;
import com.tomecode.soa.ora.suite10g.activity.PartnerLink;
import com.tomecode.soa.ora.suite10g.activity.PartnerLinks;
import com.tomecode.soa.ora.suite10g.activity.Pick;
import com.tomecode.soa.ora.suite10g.activity.Receive;
import com.tomecode.soa.ora.suite10g.activity.Reply;
import com.tomecode.soa.ora.suite10g.activity.Sequence;
import com.tomecode.soa.ora.suite10g.activity.Sms;
import com.tomecode.soa.ora.suite10g.activity.SwitchActivity;
import com.tomecode.soa.ora.suite10g.activity.Throw;
import com.tomecode.soa.ora.suite10g.activity.Transform;
import com.tomecode.soa.ora.suite10g.activity.Variable;
import com.tomecode.soa.ora.suite10g.activity.Variables;
import com.tomecode.soa.ora.suite10g.activity.Voice;
import com.tomecode.soa.ora.suite10g.activity.Wait;
import com.tomecode.soa.ora.suite10g.activity.While;
import com.tomecode.soa.ora.suite10g.project.Operation;
import com.tomecode.soa.ora.suite10g.project.Ora10gBpelProcessStrukture;
import com.tomecode.soa.ora.suite10g.project.Ora10gBpelProject;
import com.tomecode.soa.ora.suite10g.project.PartnerLinkBinding;
import com.tomecode.soa.ora.suite10g.workspace.Ora10gWorkspace;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.parser.ServiceParserException;
import com.tomecode.soa.project.UnknownProject;
import com.tomecode.soa.project.UnknownProject.UnknownProjectType;
import com.tomecode.soa.wsdl.parser.WsdlParser;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * Parser for Oracle 10g BPEL process
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class Ora10gBpelParser extends AbstractParser {

	/**
	 * list of parsed process
	 */
	private final List<Ora10gBpelProject> parsedProcess = new ArrayList<Ora10gBpelProject>();

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
	public final Ora10gBpelProject parseBpelXml(Ora10gBpelProject bpelProject, File file) throws ServiceParserException {
		File bpelXmlFile = getBpelFileXml(file);

		bpelProject.setBpelXmlFile(bpelXmlFile);
		Element bpelXml = parseXml(bpelXmlFile);

		// Ora10gBpelProject bpelProject =
		parseBpelXml(bpelXml, bpelXmlFile, bpelProject);

		File wsdl = new File(bpelXmlFile.getParent() + File.separator + bpelProject.toString() + ".wsdl");
		bpelProject.setWsdl(wsdlParser.parseWsdl(wsdl));
		return bpelProject;
	}

	public final Ora10gBpelProject parseBpelXml(File file) throws ServiceParserException {
		File bpelXmlFile = getBpelFileXml(file);

		Element bpelXml = parseXml(bpelXmlFile);
		Ora10gBpelProject bpelProject = parseBpelXml(bpelXml, bpelXmlFile);

		File wsdl = new File(bpelXmlFile.getParent() + File.separator + bpelProject.toString() + ".wsdl");
		bpelProject.setWsdl(wsdlParser.parseWsdl(wsdl));
		return bpelProject;
	}

	private final File getBpelFileXml(File file) {
		if (file.isDirectory() && file.getName().endsWith(File.separator + "bpel")) {
			return new File(file + File.separator + "bpel.xml");
		} else if (file.isDirectory()) {
			return new File(file + File.separator + "bpel" + File.separator + "bpel.xml");
		}
		return file;
	}

	/**
	 * parsed bpel.xml
	 * 
	 * @param eBpelXml
	 * @param bpelXmlFile
	 * @param bpelProcess
	 * @return
	 * @throws ServiceParserException
	 */
	private final Ora10gBpelProject parseBpelXml(Element eBpelXml, File bpelXmlFile, Ora10gBpelProject bpelProcess) throws ServiceParserException {
		Element eBPELProcess = eBpelXml.element("BPELProcess");
		// File f = bpelXmlFile.getParentFile().getParentFile();
		// Ora10gBpelProject bpelProcess = new
		// Ora10gBpelProject(eBPELProcess.attributeValue("id"),
		// eBPELProcess.attributeValue("src"), bpelXmlFile, f);

		bpelProcess.setId(eBPELProcess.attributeValue("id"));
		bpelProcess.setSrc(eBPELProcess.attributeValue("src"));

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
		parseBpelProcessStrukture(bpelRootElement, bpelProcess.getBpelProcessStrukture());
		// parseBpelOperations(bpelRootElement, bpelProcess);

		return bpelProcess;
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
	private final Ora10gBpelProject parseBpelXml(Element eBpelXml, File bpelXmlFile) throws ServiceParserException {
		Element eBPELProcess = eBpelXml.element("BPELProcess");
		File f = bpelXmlFile.getParentFile().getParentFile();
		Ora10gBpelProject bpelProcess = new Ora10gBpelProject(eBPELProcess.attributeValue("id"), eBPELProcess.attributeValue("src"), bpelXmlFile, f);

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
		parseBpelProcessStrukture(bpelRootElement, bpelProcess.getBpelProcessStrukture());
		// parseBpelOperations(bpelRootElement, bpelProcess);

		return bpelProcess;
	}

	/**
	 * check, if process is parsed
	 * 
	 * @param newBpelProcess
	 * @return
	 */
	private final boolean isParsedProcess(Ora10gBpelProject newBpelProcess) {
		for (Ora10gBpelProject pBpelProcess : parsedProcess) {
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
	private final void parseBpelProcessStrukture(Element root, Ora10gBpelProcessStrukture strukture) {
		Activity activity = new Activity(ActivityType.parseOra10gBpelActivityType(root.getName()), root.getName());
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
	private final void parseBpelProcessActivities(List<?> elements, Activity root, Ora10gBpelProcessStrukture strukture) {
		for (Object e : elements) {
			Element element = (Element) e;
			if (element.getName().equals("compensationHandler")) {
				CompensationHandler compensationHandler = new CompensationHandler(element.attributeValue("name"));
				root.addActivity(compensationHandler);
				parseBpelProcessActivities(element.elements(), compensationHandler, strukture);
			} else if (element.getName().equals("eventHandlers")) {
				EventHandlers eventHandlers = new EventHandlers(element.attributeValue("name"));
				root.addActivity(eventHandlers);
				parseBpelProcessActivities(element.elements(), eventHandlers, strukture);
			} else if (element.getName().equals("pick")) {
				Pick pick = new Pick(element.attributeValue("name"));
				root.addActivity(pick);
				parseBpelProcessActivities(element.elements(), pick, strukture);
			} else if (element.getName().equals("faultHandlers")) {
				FaultHandlers faultHandlers = new FaultHandlers();
				root.addActivity(faultHandlers);
				parseBpelProcessActivities(element.elements(), faultHandlers, strukture);
			} else if (element.getName().equals("catchAll")) {
				CatchAll catchAll = new CatchAll(element.attributeValue("name"));
				root.addActivity(catchAll);
				parseBpelProcessActivities(element.elements(), catchAll, strukture);
			} else if (element.getName().equals("flow")) {
				Flow flow = new Flow(element.attributeValue("name"));
				root.addActivity(flow);
				parseBpelProcessActivities(element.elements(), flow, strukture);
			} else if (element.getName().equals("sequence")) {
				Sequence sequence = new Sequence(element.attributeValue("name"));
				root.addActivity(sequence);
				parseBpelProcessActivities(element.elements(), sequence, strukture);
			} else if (element.getName().equals("switch")) {
				SwitchActivity switchActivity = new SwitchActivity();
				root.addActivity(switchActivity);
				parseBpelProcessActivities(element.elements(), switchActivity, strukture);
			} else if (element.getName().equals("partnerLinks")) {
				PartnerLinks partnerLinks = new PartnerLinks();
				root.addActivity(partnerLinks);
				parseBpelProcessActivities(element.elements(), partnerLinks, strukture);
			} else if (element.getName().equals("variables")) {
				Variables variables = new Variables();
				root.addActivity(variables);
				parseBpelProcessActivities(element.elements(), variables, strukture);

			} else if (element.getName().equals("variable")) {
				Variable variable = new Variable(element.attributeValue("name"), element.attributeValue("messageType"));
				root.addActivity(variable);
			} else if (element.getName().equals("partnerLink")) {
				PartnerLink partnerLink = new PartnerLink(element.attributeValue("name"), element.attributeValue("partnerLinkType"), element.attributeValue("myRole"),
						element.attributeValue("partnerRole"));
				root.addActivity(partnerLink);
			} else if (element.getName().equals("receive")) {
				Receive receive = new Receive(element.attributeValue("name"), element.attributeValue("variable"), element.attributeValue("partnerLink"), element.attributeValue("operation"));
				root.addActivity(receive);
				paseOperations(element, receive, strukture.getProject());

			} else if (element.getName().equals("invoke")) {
				Invoke invoke = new Invoke(element.attributeValue("name"), element.attributeValue("inputVariable"), element.attributeValue("outputVariable"), element.attributeValue("partnerLink"),
						element.attributeValue("operation"));
				root.addActivity(invoke);
				paseOperations(element, invoke, strukture.getProject());

			} else if (element.getName().equals("reply")) {
				Reply reply = new Reply(element.attributeValue("name"), element.attributeValue("variable"), element.attributeValue("partnerLink"), element.attributeValue("operation"));
				root.addActivity(reply);
				paseOperations(element, reply, strukture.getProject());

			} else if (element.getName().equals("throw")) {
				root.addActivity(new Throw(element.attributeValue("name"), element.attributeValue("faultVariable")));
			} else if (element.getName().equals("onMessage")) {
				OnMessage onMessageActivity = new OnMessage(element.attributeValue("variable"), element.attributeValue("partnerLink"), element.attributeValue("operation"),
						element.attributeValue("headerVariable"));
				root.addActivity(onMessageActivity);
				paseOperations(element, onMessageActivity, strukture.getProject());

				parseBpelProcessActivities(element.elements(), onMessageActivity, strukture);
			} else if (element.getName().equals("catch")) {
				Catch catchActivity = new Catch(element.attributeValue("faultName"), element.attributeValue("faultVariable"));
				root.addActivity(catchActivity);
				parseBpelProcessActivities(element.elements(), catchActivity, strukture);
			} else if (element.getName().equals("empty")) {
				root.addActivity(new Empty(element.attributeValue("name")));
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
				// TODO: parse annotations
			} else {
				Activity activity = new Activity(ActivityType.parseOra10gBpelActivityType(element.getName()), element.attributeValue("name"));
				root.addActivity(activity);
			}
		}
	}

	private final void paseOperations(Element element, Activity activity, Ora10gBpelProject bpelProject) {
		Operation operation = new Operation(activity, element.attributeValue("operation"), bpelProject, bpelProject.getBpelOperations().getBpelProcess()
				.findPartnerLinkBinding(element.attributeValue("partnerLink")));// ,
																				// getOperationPath(element));
		bpelProject.getBpelOperations().addOperation(operation);
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

	// /**
	// * parse BPEL activities by type
	// *
	// * @param element
	// * @return
	// */
	// private final Activity parseBpelActivity(Element element) {
	//
	// if (element.getName().equals("receive")) {
	// return new Receive(element.attributeValue("name"),
	// element.attributeValue("variable"),
	// element.attributeValue("partnerLink"),
	// element.attributeValue("operation"));
	// } else if (element.getName().equals("invoke")) {
	// return new Invoke(element.attributeValue("name"),
	// element.attributeValue("inputVariable"),
	// element.attributeValue("outputVariable"),
	// element.attributeValue("partnerLink"),
	// element.attributeValue("operation"));
	// } else if (element.getName().equals("reply")) {
	// return new Reply(element.attributeValue("name"),
	// element.attributeValue("variable"),
	// element.attributeValue("partnerLink"),
	// element.attributeValue("operation"));
	// } else if (element.getName().equals("throw")) {
	// return new Throw(element.attributeValue("name"),
	// element.attributeValue("faultVariable"));
	// } else if (element.getName().equals("onMessage")) {
	// return new OnMessage(element.attributeValue("variable"),
	// element.attributeValue("partnerLink"),
	// element.attributeValue("operation"),
	// element.attributeValue("headerVariable"));
	// } else if (element.getName().equals("catch")) {
	// return new Catch(element.attributeValue("faultName"),
	// element.attributeValue("faultVariable"));
	// } else if (element.getName().equals("assign")) {
	// Element eAnnotation = element.element("annotation");
	// if (eAnnotation != null) {
	// Element ePattern = eAnnotation.element("pattern");
	// if (ePattern != null && "transformation".equals(ePattern.getText())) {
	// return new Transform(element.attributeValue("name"), null, null);
	// }
	// }
	// return new Assign(element.attributeValue("name"));
	// } else if (element.getName().equals("case")) {
	// Element eAnnotation = element.element("annotation");
	// if (eAnnotation != null) {
	// Element ePattern = eAnnotation.element("pattern");
	// if (ePattern != null) {
	// if (ePattern.attributeValue("patternName") != null &&
	// "case".equals(ePattern.attributeValue("patternName"))) {
	// return new Case(ePattern.getText(),
	// getVariableFromExpression(element.attributeValue("condition")));
	// }
	// }
	// }
	// } else if (element.getName().equals("otherwise")) {
	// return new CaseOtherwise("otherwise");
	// } else if (element.getName().equals("scope")) {
	// Element eAnnotation = element.element("annotation");
	// if (eAnnotation != null) {
	// Element ePattern = eAnnotation.element("pattern");
	// if (ePattern != null) {
	// String patternName = ePattern.attributeValue("patternName");
	// if (patternName != null) {
	// if (patternName.endsWith(":email")) {
	// return new Email(element.attributeValue("name"));
	// } else if (patternName.endsWith(":fax")) {
	// return new Fax(element.attributeValue("name"));
	// } else if (patternName.endsWith(":sms")) {
	// return new Sms(element.attributeValue("name"));
	// } else if (patternName.endsWith(":voice")) {
	// return new Voice(element.attributeValue("name"));
	// } else if (patternName.endsWith(":pager")) {
	// return new Pager(element.attributeValue("name"));
	// } else if (patternName.endsWith(":workflow")) {
	// return new HumanTask(element.attributeValue("name"));
	// }
	// }
	// }
	// }
	// } else if (element.getName().equals("onAlarm")) {
	// return new OnAlarm(null);
	// } else if (element.getName().equals("wait")) {
	// return new Wait(element.attributeValue("name"), null);
	// } else if (element.getName().equals("while")) {
	// return new While(element.attributeValue("name"), null, null);
	// } else if (element.getName().equals("flowN")) {
	// return new FlowN(element.attributeValue("name"), null, null);
	// }
	//
	// return new
	// Activity(ActivityType.parseOra10gBpelActivityType(element.getName()),
	// element.attributeValue("name"));
	// }

	/**
	 * parse special scopes for example: email, sms, voice and fax
	 * 
	 * @param element
	 * @param root
	 * @param strukture
	 */
	private final void parseSpecialScopes(Element element, Activity root, Ora10gBpelProcessStrukture strukture) {
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
		Activity activity = new Activity(ActivityType.parseOra10gBpelActivityType(element.getName()), element.attributeValue("name"));
		root.addActivity(activity);
		parseBpelProcessActivities(element.elements(), activity, strukture);
	}

	// /**
	// * parse all partnerlinks in the BPEL process
	// *
	// * @param element
	// * @param bpelOperations
	// * @throws ServiceParserException
	// */
	// private void parseBpelOperations(Element element, Ora10gBpelProject
	// bpelProject) throws ServiceParserException {
	// List<?> eList = element.element("partnerLinks").elements("partnerLink");
	// for (Object e : eList) {
	// Element ePartnerLink = (Element) e;
	// String partnerLinkName = ePartnerLink.attributeValue("name");
	// findUsageForPartnerLink(partnerLinkName, bpelProject, element);
	// }
	// }

	// /**
	// * find usage for partner link
	// *
	// * @param partnerLinkName
	// * @param bpelOperations
	// * @param root
	// */
	// private final void findUsageForPartnerLink(String partnerLinkName,
	// Ora10gBpelProject bpelProject, Element root) {
	// List<?> listOfElements = root.elements();
	// if (listOfElements != null) {
	// for (Object e : listOfElements) {
	// Element element = (Element) e;
	// if (element.getName().equals("receive") ||
	// element.getName().equals("invoke") || element.getName().equals("reply")
	// || element.getName().equals("onMessage")) {
	//
	// if (element.attributeValue("partnerLink") == null) {
	// // TODO: if partnerlink is null then what?
	// new NullPointerException("").printStackTrace();
	// } else {
	// if (element.attributeValue("partnerLink").equals(partnerLinkName)) {
	// BpelOperations bpelOperations = bpelProject.getBpelOperations();
	//
	// Activity activity = parseBpelActivity(element);
	//
	// // activity =
	// //
	// findRefenceForActivity(bpelProject.getBpelProcessStrukture().getActivities(),
	// // getOperationPath(element));
	//
	// Operation operation = new Operation(activity,
	// element.attributeValue("operation"), bpelProject,
	// bpelOperations.getBpelProcess().findPartnerLinkBinding(
	// element.attributeValue("partnerLink")), getOperationPath(element));
	// bpelOperations.addOperation(operation);
	// }
	// }
	// }
	// findUsageForPartnerLink(partnerLinkName, bpelProject, element);
	// }
	// }
	// }

	// private final Activity findRefenceForActivity(List<Activity>
	// struktureActivities, List<Activity> activities) {
	// Activity realActivity = null;
	// for (Activity activity : activities) {
	// realActivity = findRefenceForActivity(activity,
	// struktureActivities.get(0).getActivities());
	// }
	//
	// return realActivity;
	// }
	//
	// private final Activity findRefenceForActivity(Activity activity,
	// List<Activity> struktureActivities) {
	// for (Activity structureActivty : struktureActivities) {
	// if (activity.getName().equals(structureActivty.getName()) &&
	// activity.getActivtyType() == structureActivty.getActivtyType()) {
	// return structureActivty;
	// }
	// }
	// return null;
	// }

	// /**
	// *
	// * find operation path
	// *
	// *
	// * @param element
	// * @return
	// */
	// private final List<Activity> getOperationPath(Element element) {
	// List<Activity> activities = new ArrayList<Activity>();
	// while (element.getParent() != null) {
	//
	// Activity activity = parseBpelActivity(element);
	// activities.add(activity);
	// element = element.getParent();
	//
	// }
	// return activities;
	// }

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

				Ora10gBpelProject bpelProject = findParsedProcess(processName, partnerLinkBinding.getParent().getBpelXmlFile());
				if (bpelProject == null) {
					partnerLinkBinding.setUnknownProject(parseProjectFromWsdl(partnerLinkBinding));
				} else {
					partnerLinkBinding.setDependencyBpelProject(bpelProject);
				}

				// partnerLinkBinding.setDependencyBpelProject(findParsedProcess(partnerLinkBinding.getParent()));
			} else {
				// parse file dependencies
				File file = new File(url.getFile());
				Ora10gBpelProject parseBpelProcess = findParsedProcess(file);
				if (parseBpelProcess != null) {
					partnerLinkBinding.setDependencyBpelProject(parseBpelProcess);// setDependencyProject(parseBpelProcess);
				} else {

					// TODO: dokoncit
					// parseBpelXml(file.getParentFile());
					// System.err.println("parse unused bpel process");
				}
			}

		} catch (Exception e) {
			int index = partnerLinkBinding.getWsdlLocation().lastIndexOf(".");
			if (index != -1) {
				String processName = partnerLinkBinding.getWsdlLocation().substring(0, index);
				Ora10gBpelProject findBpelProject = findParsedProcess(processName, partnerLinkBinding.getParent().getBpelXmlFile());
				if (findBpelProject != null) {
					partnerLinkBinding.setDependencyBpelProject(findBpelProject);
				} else {
					partnerLinkBinding.setUnknownProject(parseProjectFromWsdl(partnerLinkBinding));
				}
			} else {
				partnerLinkBinding.setUnknownProject(parseProjectFromWsdl(partnerLinkBinding));
			}
		}
	}

	/**
	 * 
	 * parse the type of project {@link UnknownProjectType} from WSDL
	 * 
	 * @param wsdlFile
	 * @param partnerLinkBinding
	 * @return
	 */
	public static final UnknownProject parseProjectFromWsdl(PartnerLinkBinding partnerLinkBinding) {
		try {

			// parse unknown WSDL type

			UnknownProject unknownProject = parseProjectFromWsdlURL(partnerLinkBinding);
			if (unknownProject != null) {
				return unknownProject;
			}

			File wsdlFile = new File(partnerLinkBinding.getWsdlLocation());
			if (wsdlFile.exists()) {
				Element element = new SAXReader().read(new FileInputStream(wsdlFile)).getRootElement();
				return parseUnknowProjetType(element.attributeValue("targetNamespace"), partnerLinkBinding);

			} else {

				wsdlFile = new File(partnerLinkBinding.getParent().getFile() + File.separator + "bpel" + File.separator + partnerLinkBinding.getWsdlLocation());
				if (wsdlFile.exists()) {
					Element element = new SAXReader().read(new FileInputStream(wsdlFile)).getRootElement();
					return parseUnknowProjetType(element.attributeValue("targetNamespace"), partnerLinkBinding);
				}

			}

		} catch (Exception e) {
		}
		return new UnknownProject(partnerLinkBinding);
	}

	private static final UnknownProject parseProjectFromWsdlURL(PartnerLinkBinding partnerLinkBinding) {
		try {
			URL url = new URL(partnerLinkBinding.getWsdlLocation());
			File wsdlFile = new File(url.getPath());
			if (wsdlFile.exists()) {
				Element element = new SAXReader().read(new FileInputStream(wsdlFile)).getRootElement();
				return parseUnknowProjetType(element.attributeValue("targetNamespace"), partnerLinkBinding);
			}
		} catch (Exception e) {

		}

		return null;
	}

	/**
	 * 
	 * find the type of project {@link UnknownProjectType}, according to
	 * namespace
	 * 
	 * @param namespace
	 * @param partnerLinkBinding
	 * @return
	 */
	private static final UnknownProject parseUnknowProjetType(String namespace, PartnerLinkBinding partnerLinkBinding) {
		if (namespace == null) {
			return new UnknownProject(partnerLinkBinding);
		} else if (namespace.contains("pcbpel/adapter/ftp")) {
			return new UnknownProject(partnerLinkBinding, UnknownProjectType.FTP);
		} else if (namespace.contains("pcbpel/adapter/jms")) {
			return new UnknownProject(partnerLinkBinding, UnknownProjectType.JMS);
		} else if (namespace.contains("pcbpel/adapter/file")) {
			return new UnknownProject(partnerLinkBinding, UnknownProjectType.FILE);
		} else if (namespace.contains("pcbpel/adapter/db")) {
			return new UnknownProject(partnerLinkBinding, UnknownProjectType.DB);
		} else if (namespace.contains("pcbpel/adapter/aq")) {
			return new UnknownProject(partnerLinkBinding, UnknownProjectType.AQ);
		} else if (namespace.contains("pcbpel/adapter/mq")) {
			return new UnknownProject(partnerLinkBinding, UnknownProjectType.MQ);
		}

		return new UnknownProject(partnerLinkBinding);
	}

	/**
	 * find {@link Ora10gBpelProject} in list of {@link #parsedProcess} by
	 * bpel.xml
	 * 
	 * @param file
	 * @return
	 */
	private final Ora10gBpelProject findParsedProcess(File file) {
		if (file.getName().endsWith("BPELProcess7.wsdl")) {
			file.toString();
		}
		if (file.getName().endsWith(".wsdl") || file.getName().endsWith("?wsdl")) {
			file = new File(file.getParent() + File.separator + "bpel.xml");
		}
		for (Ora10gBpelProject bpelProcess : parsedProcess) {
			if (bpelProcess.getBpelXmlFile().equals(file)) {
				return bpelProcess;
			}
		}
		return null;
	}

	/**
	 * find {@link Ora10gBpelProject} in list of {@link #parsedProcess}
	 * 
	 * @param processName
	 * @return if not found return null
	 */
	private final Ora10gBpelProject findParsedProcess(String name, File bpelXml) {
		for (Ora10gBpelProject bpelProcess : parsedProcess) {
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
	 * compare two {@link Ora10gBpelProject} by {@link Ora10gWorkspace}
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

		return null;
	}
}
