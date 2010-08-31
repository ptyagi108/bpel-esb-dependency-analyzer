package com.tomecode.soa.wsdl.parser;

import java.io.File;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.parser.ServiceParserException;
import com.tomecode.soa.wsdl.PortType;
import com.tomecode.soa.wsdl.Wsdl;
import com.tomecode.soa.wsdl.WsdlOperation;

/**
 * 
 * parser for {@link Wsdl}
 * 
 * @author Tomas Frastia
 * 
 */
public class WsdlParser extends AbstractParser {

	/**
	 * Constructor
	 */
	public WsdlParser() {

	}

	/**
	 * parse WSDL file
	 * 
	 * @param file
	 * @return
	 */
	public Wsdl parseWsdl(File file) {
		if (file.exists()) {
			try {
				Element element = parseXml(file);
				Wsdl wsdl = new Wsdl(file, element.attributeValue("name"));
				parsePortTypes(element.elements("portType"), wsdl);
				return wsdl;
			} catch (ServiceParserException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * parse list of {@link PortType}
	 * 
	 * @param elements
	 * @param wsdl
	 */
	protected final void parsePortTypes(List<?> elements, Wsdl wsdl) {
		if (elements != null) {
			for (Object o : elements) {
				Element element = (Element) o;
				wsdl.addPortType(parsePortType(element));
			}

		}
	}

	/**
	 * parse element: portType in WSDL file
	 * 
	 * @param ePortType
	 * @return
	 */
	protected final PortType parsePortType(Element ePortType) {
		PortType portType = new PortType(ePortType.attributeValue("name"));

		List<?> eOperations = ePortType.elements("operation");
		for (Object o : eOperations) {
			Element eOperation = (Element) o;
			portType.addWsdlOperations(new WsdlOperation(eOperation.attributeValue("name")));
		}
		return portType;
	}
}
