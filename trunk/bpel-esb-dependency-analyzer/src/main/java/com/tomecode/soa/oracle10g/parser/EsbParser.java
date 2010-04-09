package com.tomecode.soa.oracle10g.parser;

import java.io.File;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.oracle10g.esb.Esb;
import com.tomecode.soa.oracle10g.esb.EsbOperation;
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
	 * parse {@link Esb}
	 * 
	 * @param file
	 * @throws ServiceParserException
	 */
	public final Esb parse(File file) throws ServiceParserException {
		Element eService = parseXml(file);
		Esb esb = new Esb(file, eService.attributeValue("name"), eService.attributeValue("qname"));

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
	private final void parseOperations(Element eService, Esb esb) {
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
