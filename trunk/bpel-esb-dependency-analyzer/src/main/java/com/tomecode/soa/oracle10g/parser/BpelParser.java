package com.tomecode.soa.oracle10g.parser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.tomecode.soa.oracle10g.bpel.BpelOperations;
import com.tomecode.soa.oracle10g.bpel.BpelProcessStrukture;
import com.tomecode.soa.oracle10g.bpel.BpelProject;
import com.tomecode.soa.oracle10g.bpel.Operation;
import com.tomecode.soa.oracle10g.bpel.PartnerLinkBinding;
import com.tomecode.soa.oracle10g.bpel.activity.Activity;
import com.tomecode.soa.oracle10g.bpel.activity.Catch;
import com.tomecode.soa.oracle10g.bpel.activity.Invoke;
import com.tomecode.soa.oracle10g.bpel.activity.OnMessage;
import com.tomecode.soa.oracle10g.bpel.activity.PartnerLink;
import com.tomecode.soa.oracle10g.bpel.activity.Receive;
import com.tomecode.soa.oracle10g.bpel.activity.Reply;
import com.tomecode.soa.oracle10g.bpel.activity.Throw;
import com.tomecode.soa.oracle10g.bpel.activity.Transform;
import com.tomecode.soa.oracle10g.bpel.activity.Variable;

/**
 * Parser for Oracle 10g BPEL process
 * 
 * @author Tomas Frastia
 * 
 */
public final class BpelParser extends AbstractParser {

	/**
	 * list of parsed process
	 */
	private final List<BpelProject> parsedProcess = new ArrayList<BpelProject>();

	/**
	 * parse bpel process from url
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
	 * parse bpel by bpel project folder
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
		return parseBpelXml(bpelXml, bpelXmlFile);
	}

	/**
	 * 
	 * pase bpel.xml
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
	 * chekc if process is parsed
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
	 * parse all bpel process to tree strukture
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
	 * parse all actvities in the BPEL process
	 * 
	 * @param elements
	 * @param root
	 */
	private final void parseBpelProcessActivities(List<?> elements, Activity root, BpelProcessStrukture strukture) {
		for (Object e : elements) {
			Element element = (Element) e;
			if (element.getName().equals("sequence") || element.getName().equals("scope") || element.getName().equals("switch") || element.getName().equals("flow") || element.getName().equals("flowN") || element.getName().equals("case") || element.getName().equals("otherwise")
					|| element.getName().equals("faultHandlers") || element.getName().equals("eventHandlers") || element.getName().equals("catchAll") || element.getName().equals("onAlarm") || element.getName().equals("compensationHandler") || element.getName().equals("pick")
					|| element.getName().equals("variables") || element.getName().equals("partnerLinks")) {
				Activity activity = new Activity(element.getName(), element.attributeValue("name"));
				root.addActivity(activity);
				parseBpelProcessActivities(element.elements(), activity, strukture);
			} else if (element.getName().equals("variable")) {
				Variable variable = new Variable(element.attributeValue("name"), element.attributeValue("messageType"), strukture);
				root.addActivity(variable);
			} else if (element.getName().equals("partnerLink")) {
				PartnerLink partnerLink = new PartnerLink(element.attributeValue("name"), element.attributeValue("partnerLinkType"), element.attributeValue("myRole"), element.attributeValue("partnerRole"), strukture);
				root.addActivity(partnerLink);
			} else if (element.getName().equals("receive")) {
				root.addActivity(new Receive(element.attributeValue("name"), element.attributeValue("variable"), element.attributeValue("partnerLink"), element.attributeValue("operation")));
			} else if (element.getName().equals("invoke")) {
				root.addActivity(new Invoke(element.attributeValue("name"), element.attributeValue("inputVariable"), element.attributeValue("outputVariable"), element.attributeValue("partnerLink"), element.attributeValue("operation")));
			} else if (element.getName().equals("reply")) {
				root.addActivity(new Reply(element.attributeValue("name"), element.attributeValue("variable"), element.attributeValue("partnerLink"), element.attributeValue("operation")));
			} else if (element.getName().equals("throw")) {
				root.addActivity(new Throw(element.attributeValue("name"), element.attributeValue("faultVariable")));
			} else if (element.getName().equals("onMessage")) {
				OnMessage onMessageActivity = new OnMessage(element.attributeValue("variable"), element.attributeValue("partnerLink"), element.attributeValue("operation"), element.attributeValue("headerVariable"));
				root.addActivity(onMessageActivity);
				parseBpelProcessActivities(element.elements(), onMessageActivity, strukture);
			} else if (element.getName().equals("catch")) {
				Catch catchActivity = new Catch(element.attributeValue("faultName"), element.attributeValue("faultVariable"));
				root.addActivity(catchActivity);
				parseBpelProcessActivities(element.elements(), catchActivity, strukture);
			} else if (element.getName().equals("assign")) {
				Element eAnnotation = element.element("annotation");
				if (eAnnotation != null) {
					Element ePattern = eAnnotation.element("pattern");
					if (ePattern != null && "transformation".equals(ePattern.getText())) {
						Transform transform = new Transform(element.attributeValue("name"), null, null);
						root.addActivity(transform);
						return;
					}
				}

				Activity activity = new Activity(element.getName(), element.attributeValue("name"));
				root.addActivity(activity);

			} else {
				Activity activity = new Activity(element.getName(), element.attributeValue("name"));
				root.addActivity(activity);
			}

			// TODO: DOROBIT transformation
		}
	}

	/**
	 * pase all partnerlinks in the BPEL process
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
	 * find useage for partner link
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
				if (element.getName().equals("receive") || element.getName().equals("invoke") || element.getName().equals("reply")) {

					if (element.attributeValue("partnerLink") == null) {
						new NullPointerException("").printStackTrace();
					} else {
						if (element.attributeValue("partnerLink").equals(partnerLinkName)) {
							BpelOperations bpelOperations = bpelProject.getBpelOperations();
							Operation operation = new Operation(element.getName(), element.attributeValue("name"), element.attributeValue("operation"), bpelProject, bpelOperations.getBpelProcess().findPartnerLinkBinding(element.attributeValue("partnerLink")),
									getOperationPath(element));
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
			activities.add(new Activity(element.getName(), element.attributeValue("name")));
			element = element.getParent();

		}
		return activities;
	}

	/**
	 * parse bpel in by wsdl
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
				partnerLinkBinding.setDependencyProject(findParsedProcess(processName));
			} else {
				// parse file dependencie
				File file = new File(url.getFile());
				BpelProject parseBpelProcess = findParsedProcess(file);
				if (parseBpelProcess != null) {
					partnerLinkBinding.setDependencyProject(parseBpelProcess);
				} else {
					parseBpelXml(file.getParentFile());
				}
			}

		} catch (Exception e) {
			int index = partnerLinkBinding.getWsdlLocation().lastIndexOf(".");
			if (index != -1) {
				String processName = partnerLinkBinding.getWsdlLocation().substring(0, index);
				partnerLinkBinding.setDependencyProject(findParsedProcess(processName));
			} else {
				partnerLinkBinding.setParseErrror(e);
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

	private final BpelProject findParsedProcess(String processName) {
		for (BpelProject bpelProcess : parsedProcess) {
			if (bpelProcess.getId() != null) {
				if (bpelProcess.getId().equals(processName)) {
					return bpelProcess;
				}
			} else if (bpelProcess.getSrc() != null) {
				if (bpelProcess.getSrc().equals(processName)) {
					return bpelProcess;
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

}
