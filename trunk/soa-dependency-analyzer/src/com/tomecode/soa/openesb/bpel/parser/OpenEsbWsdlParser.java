package com.tomecode.soa.openesb.bpel.parser;

import java.io.File;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.parser.ServiceParserException;
import com.tomecode.soa.wsdl.PartnerLinkType;
import com.tomecode.soa.wsdl.Role;
import com.tomecode.soa.wsdl.Wsdl;
import com.tomecode.soa.wsdl.parser.WsdlParser;

/**
 * parse of WSDL file - in Open ESB projects
 * 
 * @author Tomas Frastia
 * 
 */
public final class OpenEsbWsdlParser extends WsdlParser {

	public final Wsdl parseWsdl(File file) {
		if (file.exists()) {
			try {
				Element element = parseXml(file);
				Wsdl wsdl = new Wsdl(file, element.attributeValue("name"));
				parsePortTypes(element.elements("portType"), wsdl);
				parsePartnerLinkTypes(element.elements("partnerLinkType"), wsdl);
				return wsdl;
			} catch (ServiceParserException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * parse {@link PartnerLinkType}
	 * 
	 * @param elements
	 * @param wsdl
	 */
	private final void parsePartnerLinkTypes(List<?> elements, Wsdl wsdl) {
		if (elements != null) {
			for (Object o : elements) {
				Element element = (Element) o;
				PartnerLinkType partnerLinkType = new PartnerLinkType(element.attributeValue("name"));
				wsdl.addPartnerLinkType(partnerLinkType);
				parseRole(element.elements("role"), partnerLinkType, wsdl);
			}
		}
	}

	/**
	 * parse {@link Role} for {@link PartnerLinkType}
	 * 
	 * @param elements
	 * @param partnerLinkType
	 * @param wsdl
	 */
	private final void parseRole(List<?> elements, PartnerLinkType partnerLinkType, Wsdl wsdl) {
		if (elements != null) {
			for (Object o : elements) {
				Element element = (Element) o;
				Role role = new Role(element.attributeValue("name"), partnerLinkType, wsdl.findPortType(element.attributeValue("portType")));
				partnerLinkType.addRole(role);
			}
		}
	}

}
