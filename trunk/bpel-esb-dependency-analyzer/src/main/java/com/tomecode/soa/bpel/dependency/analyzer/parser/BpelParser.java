package com.tomecode.soa.bpel.dependency.analyzer.parser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tomecode.soa.bpel.model.Activity;
import com.tomecode.soa.bpel.model.BpelOperations;
import com.tomecode.soa.bpel.model.BpelProcess;
import com.tomecode.soa.bpel.model.BpelProcessStrukture;
import com.tomecode.soa.bpel.model.Operation;
import com.tomecode.soa.bpel.model.PartnerLinkBinding;

/**
 * Parser for BPEL
 * 
 * @author Tomas Frastia
 * 
 */
public final class BpelParser {

	/**
	 * contains parsed list of bpel.xml
	 */
	private final Hashtable<File, BpelProcess> parsedProcess = new Hashtable<File, BpelProcess>();

	public static final void main(String[] arg) throws BpelParserException {
		BpelParser bpelParser = new BpelParser();
		BpelProcess bpelProcess = bpelParser.parseBpelXml(new File("C:/ORACLE/projects/BPEL/samples/Client/bpel"));

		bpelProcess.toString();

	}

	/**
	 * parse bpel by bpel project folder
	 * 
	 * @param file
	 * @return
	 * @throws BpelParserException
	 */
	public final BpelProcess parseBpelXml(File file) throws BpelParserException {
		File bpelXmlFile = file.getName().equals("bpel.xml") ? file : new File(file + File.separator + "bpel.xml");
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

		if (parsedProcess.containsKey(bpelXmlFile)) {
			return parsedProcess.get(bpelXmlFile);
		} else {
			parsedProcess.put(bpelXmlFile, bpelProcess);
		}
		int index = 0;
		List<?> eListOfPartnerLinkBindinds = eBPELProcess.element("partnerLinkBindings").elements("partnerLinkBinding");
		for (Object e : eListOfPartnerLinkBindinds) {
			Element ePartnerLink = (Element) e;
			PartnerLinkBinding partnerLinkBinding = new PartnerLinkBinding(ePartnerLink.attributeValue("name"), parseWsdlLocation(ePartnerLink));

			// // TODO : doriesit - parsovanie clienta
			if (index != 0) {
				bpelProcess.addPartnerLinkBinding(partnerLinkBinding);
				partnerLinkBinding.setBpelProcess(parseBpelByWsdl(partnerLinkBinding.getWsdlLocation()));

			}
			index++;
		}

		Element bpelRootElement = parseXml(new File(bpelXmlFile.getParentFile() + File.separator + bpelProcess.getSrc()));
		parseBpelOperations(bpelRootElement, bpelProcess.getBpelOperations());
		parseBpelProcessStrukture(bpelRootElement, bpelProcess.getBpelProcessStrukture());
		return bpelProcess;
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
	private final BpelProcess parseBpelByWsdl(String wsdlLocation) throws BpelParserException {
		URL url;
		try {
			url = new URL(wsdlLocation);
		} catch (MalformedURLException e) {
			e.getMessage().equals("no protocol");
			e.printStackTrace();
			return null;
		}

		if (!url.getProtocol().equals("file")) {
			return null;
		}
		File file = new File(url.getFile());
		BpelProcess parseBpelProcess = parsedProcess.get(file);
		if (parseBpelProcess != null) {
			return parseBpelProcess;
		}
		return parseBpelXml(file.getParentFile());
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
