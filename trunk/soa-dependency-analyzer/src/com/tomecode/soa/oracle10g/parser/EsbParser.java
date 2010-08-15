package com.tomecode.soa.oracle10g.parser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.oracle10g.esb.EsbGrp;
import com.tomecode.soa.oracle10g.esb.EsbOperation;
import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.soa.oracle10g.esb.EsbRoutingRule;
import com.tomecode.soa.oracle10g.esb.EsbSvc;
import com.tomecode.soa.oracle10g.esb.EsbSys;
import com.tomecode.soa.oracle10g.project.PartnerLinkBinding;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.parser.ServiceParserException;

/**
 * 
 * Parser for Oracle 10g ESB services
 * 
 * @author Tomas Frastia
 * 
 */
public final class EsbParser extends AbstractParser {

	/**
	 * Constructor
	 */
	public EsbParser() {

	}

	/**
	 * parse {@link EsbProject}
	 * 
	 * @param file
	 *            project folder
	 * @return
	 * @throws ServiceParserException
	 */
	public final EsbProject parse(File projectFolder) throws ServiceParserException {
		EsbProject esbProject = new EsbProject(findProjectName(projectFolder), projectFolder);

		List<File> findedFiles = new ArrayList<File>();
		findEsbsvcFiles(projectFolder, findedFiles, ".esbsys");

		for (File esbSysFile : findedFiles) {
			esbProject.addBasicEsbNode(parseEsbSys(esbSysFile));
		}

		findedFiles.clear();
		findEsbsvcFiles(projectFolder, findedFiles, ".esbgrp");

		for (File esbGrpFile : findedFiles) {
			parseEsbGrp(esbGrpFile, esbProject);
		}
		findedFiles.clear();
		findEsbsvcFiles(projectFolder, findedFiles, ".esbsvc");

		for (File esbSvcFile : findedFiles) {
			parseEsbsvc(esbSvcFile, esbProject);
		}

		return esbProject;
	}

	private final void parseEsbGrp(File esbGrpFile, EsbProject esbProject) throws ServiceParserException {
		Element eService = parseXml(esbGrpFile);

		EsbGrp esbGrp = new EsbGrp(esbGrpFile, eService.attributeValue("name"), eService.attributeValue("qname"));
		Element parent = eService.element("parent");
		if (parent != null) {

			if ("serviceGroup".equals(parent.attributeValue("type"))) {
				EsbGrp grp = esbProject.findEsbGrpByQname(parent.attributeValue("qname"));
				if (grp != null) {
					grp.addBasicEsbNode(esbGrp);
				} else {
					esbProject.addBasicEsbNode(esbGrp);
				}
			} else if ("system".equals(parent.attributeValue("type"))) {
				EsbSys sys = esbProject.findEsbSysByQname(parent.attributeValue("qname"));
				if (sys != null) {
					sys.addBasicEsbNode(esbGrp);
				} else {
					esbProject.addBasicEsbNode(esbGrp);
				}
			} else {
				esbProject.addBasicEsbNode(esbGrp);
			}

		}
	}

	/**
	 * parse {@link EsbSys}
	 * 
	 * @param esbSysFile
	 * @return
	 * @throws ServiceParserException
	 */
	private final EsbSys parseEsbSys(File esbSysFile) throws ServiceParserException {
		Element eService = parseXml(esbSysFile);
		return new EsbSys(esbSysFile, eService.attributeValue("name"), eService.attributeValue("qname"));
	}

	/**
	 * trying find esb project name
	 * 
	 * @param projectFolder
	 * @return
	 */
	private final String findProjectName(File projectFolder) {

		File[] files = projectFolder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile() && file.getName().endsWith(".jpr")) {
					return file.getName().substring(0, file.getName().indexOf(".jpr"));
				}
			}
		}
		return projectFolder.getName();
	}

	/**
	 * find all *.esbsvc in project dir
	 * 
	 * @param projectDir
	 * @param findedFiles
	 */
	private final void findEsbsvcFiles(File projectDir, List<File> findedFiles, String fileTypeName) {
		File[] files = projectDir.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			if (file.isDirectory()) {
				findEsbsvcFiles(file, findedFiles, fileTypeName);
			}
			if (file.isFile() && file.getName().endsWith(fileTypeName)) {
				findedFiles.add(file);
			}
		}
	}

	/**
	 * parse {@link EsbSvc}
	 * 
	 * @param file
	 * @throws ServiceParserException
	 */
	public final void parseEsbsvc(File file, EsbProject esbProject) throws ServiceParserException {
		Element eService = parseXml(file);
		EsbSvc esb = new EsbSvc(file, eService.attributeValue("name"), eService.attributeValue("qname"));
		esb.setOwnerEsbProject(esbProject);

		Element eServiceDefinition = eService.element("serviceDefinition");
		if (eServiceDefinition != null) {
			esb.setWsdlURL(eServiceDefinition.elementTextTrim("wsdlURL"));

			Element eEndpointDefinition = eServiceDefinition.element("endpointDefinition");
			if (eEndpointDefinition != null) {
				esb.setConcreteWSDLURL(eEndpointDefinition.elementTextTrim("concreteWSDLURL"));
				esb.setSoapEndpointURI(eEndpointDefinition.elementTextTrim("soapEndpointURI"));
			}
		}
		parseOperations(eService, esb);

		Element parent = eService.element("parent");
		if (parent != null) {
			if ("serviceGroup".equals(parent.attributeValue("type"))) {
				EsbGrp esbGrp = esbProject.findEsbGrpByQname(parent.attributeValue("qname"));
				esbGrp.addBasicEsbNode(esb);
			} else if ("system".equals(parent.attributeValue("type"))) {
				EsbSys esbSys = esbProject.findEsbSysByQname(parent.attributeValue("qname"));
				if (esbSys != null) {
					esbSys.addBasicEsbNode(esb);
				} else {
					EsbSys esbSysNew = new EsbSys(parent.attributeValue("qname"));
					esbSysNew.addBasicEsbNode(esb);
					esbProject.addBasicEsbNode(esbSysNew);
				}
			}
		}

	}

	/**
	 * parse {@link EsbOperation}
	 * 
	 * @param eService
	 * @param esb
	 */
	@SuppressWarnings("unchecked")
	private final void parseOperations(Element eService, EsbSvc esb) {
		Element eOperations = eService.element("operations");
		if (eOperations != null) {
			List<Element> operations = eOperations.elements("operationInfo");
			if (operations != null) {
				for (Element operation : operations) {
					EsbOperation esbOperation = new EsbOperation(operation.attributeValue("qname"), operation.attributeValue("wsdlOperation"));
					esb.addEsbOperation(esbOperation);
					parseRoutingRule(esbOperation, operation);
				}
			}
		}
	}

	/**
	 * parse {@link EsbRoutingRule}
	 * 
	 * @param esbOperation
	 * @param operation
	 */
	@SuppressWarnings("unchecked")
	private final void parseRoutingRule(EsbOperation esbOperation, Element operation) {
		Element eRoutingRules = operation.element("routingRules");
		if (eRoutingRules != null) {
			List<Element> routingRules = eRoutingRules.elements("routingRule");
			if (routingRules != null) {
				for (Element routingRule : routingRules) {

					Element targetOperation = routingRule.element("targetOperation");
					if (targetOperation != null) {
						EsbRoutingRule esbRoutingRule = new EsbRoutingRule(targetOperation.attributeValue("qname"), targetOperation.attributeValue("serviceQName"));
						esbOperation.addRoutingRule(esbRoutingRule);
					}
				}
			}

		}
	}

	/**
	 * parse WSDL and find BPEL
	 * 
	 * @param partnerLinkBinding
	 */
	public final String createEsbSystemUrl(PartnerLinkBinding partnerLinkBinding) {
		try {
			URL url = new URL(partnerLinkBinding.getWsdlLocation());

			if (url.getFile().startsWith("/esb/wsil/")) {
				return url.getFile().replace("/esb/wsil/", "");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * convert WSDL to qName
	 * 
	 * @param wsdlLocation
	 * @return
	 */
	public final String convertWsdlToQname(URL url) {
		try {
			if (url.getFile().startsWith("/esb/wsil/")) {
				String urlQname = url.getFile().replace("/esb/wsil/", "");
				if (urlQname.endsWith("?wsdl")) {
					return urlQname.substring(0, urlQname.lastIndexOf("?wsdl")).replace("/", ".");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
