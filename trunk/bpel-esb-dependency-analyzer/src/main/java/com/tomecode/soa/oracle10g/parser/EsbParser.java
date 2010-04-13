package com.tomecode.soa.oracle10g.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.oracle10g.esb.Esbsvc;
import com.tomecode.soa.oracle10g.esb.EsbOperation;
import com.tomecode.soa.oracle10g.esb.EsbProject;
import com.tomecode.soa.oracle10g.esb.EsbRoutingRule;

/**
 * 
 * Esb parser
 * 
 * @author Tomas Frastia
 * 
 */
public final class EsbParser extends AbstractParser {

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
		EsbProject esbProject = new EsbProject(projectFolder, findProjectName(projectFolder));

		List<File> findedFiles = new ArrayList<File>();
		findEsbsvcFiles(projectFolder, findedFiles);

		for (File esbSvcFile : findedFiles) {
			esbProject.addEsb(parseEsbsvc(esbSvcFile));
		}

		return esbProject;
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
	private final void findEsbsvcFiles(File projectDir, List<File> findedFiles) {
		File[] files = projectDir.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			if (file.isDirectory()) {
				findEsbsvcFiles(file, findedFiles);
			}
			if (file.isFile() && file.getName().endsWith(".esbsvc")) {
				findedFiles.add(file);
			}
		}
	}

	/**
	 * parse {@link Esbsvc}
	 * 
	 * @param file
	 * @throws ServiceParserException
	 */
	public final Esbsvc parseEsbsvc(File file) throws ServiceParserException {
		Element eService = parseXml(file);
		Esbsvc esb = new Esbsvc(file, eService.attributeValue("name"), eService.attributeValue("qname"));

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

		return esb;
	}

	/**
	 * parse {@link EsbOperation}
	 * 
	 * @param eService
	 * @param esb
	 */
	@SuppressWarnings("unchecked")
	private final void parseOperations(Element eService, Esbsvc esb) {
		Element eOperations = eService.element("operations");
		if (eOperations != null) {
			List<Element> operations = eOperations.elements("operationInfo");
			if (operations != null) {
				for (Element operation : operations) {
					EsbOperation esbOperation = new EsbOperation(operation.attributeValue("qname"), operation.attributeValue("wsdlOperation"));
					esb.addOperation(esbOperation);
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
					EsbRoutingRule esbRoutingRule = new EsbRoutingRule(targetOperation.attributeValue("qname"), targetOperation.attributeValue("serviceQName"));
					esbOperation.addRoutingRule(esbRoutingRule);
				}
			}

		}
	}
}
