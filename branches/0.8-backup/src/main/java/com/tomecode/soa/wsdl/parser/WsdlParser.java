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
public final class WsdlParser extends AbstractParser {

	/**
	 * Constructor
	 */
	public WsdlParser() {

	}

	/**
	 * parse wsdl file
	 * 
	 * @param file
	 * @return
	 */
	public final Wsdl parseWsdl(File file) {
		if (file.exists()) {
			try {
				Element element = parseXml(file);
				Wsdl wsdl = new Wsdl(file, element.attributeValue("name"));

				Element ePortType = element.element("portType");
				if (ePortType != null) {
					wsdl.setPortType(parsePortType(ePortType));
				}
				return wsdl;
			} catch (ServiceParserException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * parse element: portType in wsdl file
	 * 
	 * @param ePortType
	 * @return
	 */
	private final PortType parsePortType(Element ePortType) {
		PortType portType = new PortType(ePortType.attributeValue("name"));

		List<?> eOperations = ePortType.elements("operation");
		for (Object o : eOperations) {
			Element eOperation = (Element) o;
			portType.addWsdlOperations(new WsdlOperation(eOperation.attributeValue("name")));
		}
		return portType;
	}
}
