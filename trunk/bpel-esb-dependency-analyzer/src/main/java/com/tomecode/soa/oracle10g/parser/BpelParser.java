package com.tomecode.soa.oracle10g.parser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tomecode.soa.oracle10g.model.Activity;
import com.tomecode.soa.oracle10g.model.BpelOperations;
import com.tomecode.soa.oracle10g.model.BpelProcess;
import com.tomecode.soa.oracle10g.model.BpelProcessStrukture;
import com.tomecode.soa.oracle10g.model.Operation;
import com.tomecode.soa.oracle10g.model.PartnerLinkBinding;

/**
 * Parser for BPEL
 * 
 * @author Tomas Frastia
 * 
 */
public final class BpelParser {

	private final List<BpelProcess> parsedProcess = new ArrayList<BpelProcess>();

	public static final void main(String[] arg) throws BpelParserException {
		BpelParser bpelParser = new BpelParser();
		BpelProcess bpelProcess = bpelParser.parseBpelXml(new File("C:/ORACLE/projects/BPEL/samples/BPELProcess1"));
		bpelProcess.toString();
	}

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
	 * @throws BpelParserException
	 */
	public final BpelProcess parseBpelXml(File file) throws BpelParserException {
		File bpelXmlFile = file;

		if (file.isDirectory() && file.getName().endsWith(File.separator + "bpel")) {
			bpelXmlFile = new File(file + File.separator + "bpel.xml");
		} else if (file.isDirectory()) {
			bpelXmlFile = new File(file + File.separator + "bpel" + File.separator + "bpel.xml");
		}

		// file.getName().equals("bpel.xml") ? file : new File(file +
		// File.separator + "bpel" + File.separator + "bpel.xml");
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
	 * @throws BpelParserException
	 */
	private final BpelProcess parseBpelXml(Element eBpelXml, File bpelXmlFile) throws BpelParserException {
		Element eBPELProcess = eBpelXml.element("BPELProcess");
		BpelProcess bpelProcess = new BpelProcess(eBPELProcess.attributeValue("id"), eBPELProcess.attributeValue("src"), bpelXmlFile);

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
		parseBpelOperations(bpelRootElement, bpelProcess.getBpelOperations());
		parseBpelProcessStrukture(bpelRootElement, bpelProcess.getBpelProcessStrukture());
		return bpelProcess;
	}

	/**
	 * chekc if process is parsed
	 * 
	 * @param newBpelProcess
	 * @return
	 */
	private final boolean isParsedProcess(BpelProcess newBpelProcess) {
		for (BpelProcess pBpelProcess : parsedProcess) {
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
		parseBpelProcessActivities(root.elements(), activity);
	}

	/**
	 * 
	 * parse all actvities in the BPEL process
	 * 
	 * @param elements
	 * @param root
	 */
	private final void parseBpelProcessActivities(List<?> elements, Activity root) {
		for (Object e : elements) {
			Element element = (Element) e;
			if (element.getName().equals("sequence") || element.getName().equals("scope") || element.getName().equals("switch") || element.getName().equals("flow") | element.getName().endsWith(":flowN")) {
				Activity activity = new Activity(element.getName(), element.attributeValue("name"));
				root.addActivity(activity);
				parseBpelProcessActivities(element.elements(), activity);
			} else {
				Activity activity = new Activity(element.getName(), element.attributeValue("name"));
				root.addActivity(activity);
			}
		}
	}

	/**
	 * pase all partnerlinks in the BPEL process
	 * 
	 * @param element
	 * @param bpelOperations
	 * @throws BpelParserException
	 */
	private void parseBpelOperations(Element element, BpelOperations bpelOperations) throws BpelParserException {
		List<?> eList = element.element("partnerLinks").elements("partnerLink");
		for (Object e : eList) {
			Element ePartnerLink = (Element) e;
			String partnerLinkName = ePartnerLink.attributeValue("name");

			// TODO: parse all partnerLinks in scopes
			System.out.println("find:" + partnerLinkName);
			findUsageForPartnerLink(partnerLinkName, bpelOperations, element);
		}

	}

	/**
	 * find useage for partner link
	 * 
	 * @param partnerLinkName
	 * @param bpelOperations
	 * @param root
	 */
	private final void findUsageForPartnerLink(String partnerLinkName, BpelOperations bpelOperations, Element root) {
		List<?> listOfElements = root.elements();
		if (listOfElements == null) {
			return;
		}
		for (Object e : listOfElements) {
			Element element = (Element) e;
			if (element.getName().equals("receive") || element.getName().equals("invoke") || element.getName().equals("reply") || element.getName().equals("pick")) {
				if (element.attributeValue("partnerLink").equals(partnerLinkName)) {
					System.out.println(element.getName() + " " + element.attributeValue("name") + " " + element.attributeValue("partnerLink"));
					Operation operation = new Operation(element.getName(), element.attributeValue("name"), element.attributeValue("operation"), bpelOperations.getBpelProcess().findPartnerLinkBinding(element.attributeValue("partnerLink")));
					bpelOperations.addOperation(operation);
				}
			}
			findUsageForPartnerLink(partnerLinkName, bpelOperations, element);
		}
	}

	/**
	 * parse bpel in by wsdl
	 * 
	 * @param wsdlLocation
	 * @return
	 * @throws MalformedURLException
	 * @throws DocumentException
	 */
	public final void parseBpelByWsdl(PartnerLinkBinding partnerLinkBinding) throws BpelParserException {

		try {
			URL url = new URL(partnerLinkBinding.getWsdlLocation());

			if (url.getProtocol().equals("http") || url.getProtocol().equals("https")) {
				String processName = getProcessNameFromUrl(url.toString());
				partnerLinkBinding.setBpelProcess(findParsedProcess(processName));
			} else {
				// parse file dependencie
				File file = new File(url.getFile());
				BpelProcess parseBpelProcess = findParsedProcess(file);
				if (parseBpelProcess != null) {
					partnerLinkBinding.setBpelProcess(parseBpelProcess);
				} else {
					parseBpelXml(file.getParentFile());
				}
			}

		} catch (Exception e) {
			int index = partnerLinkBinding.getWsdlLocation().lastIndexOf(".");
			if (index != -1) {
				String processName = partnerLinkBinding.getWsdlLocation().substring(0, index);
				partnerLinkBinding.setBpelProcess(findParsedProcess(processName));
			} else {
				partnerLinkBinding.setParseErrror(e);
			}
		}
	}

	/**
	 * find {@link BpelProcess} in list of {@link #parsedProcess} by bpel.xml
	 * 
	 * @param file
	 * @return
	 */
	private final BpelProcess findParsedProcess(File file) {
		if (file.getName().endsWith(".wsdl") || file.getName().endsWith("?wsdl")) {
			file = new File(file.getParent() + File.separator + "bpel.xml");
		}
		for (BpelProcess bpelProcess : parsedProcess) {
			if (bpelProcess.getBpelXmlFile().toString().equals(file.toString())) {
				return bpelProcess;
			}
		}
		return null;
	}

	private final BpelProcess findParsedProcess(String processName) {
		for (BpelProcess bpelProcess : parsedProcess) {
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

	/**
	 * parse file to Xml
	 * 
	 * @param file
	 * @return
	 * @throws BpelParserException
	 */
	private final Element parseXml(File file) throws BpelParserException {
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(file);
		} catch (DocumentException e) {
			throw new BpelParserException(e);
		}
		return document.getRootElement();
	}

}
