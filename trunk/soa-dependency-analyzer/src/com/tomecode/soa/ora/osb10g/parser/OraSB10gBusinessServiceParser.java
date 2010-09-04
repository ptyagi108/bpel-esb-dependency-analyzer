package com.tomecode.soa.ora.osb10g.parser;

import java.io.File;

import org.dom4j.Element;

import com.tomecode.soa.ora.osb10g.services.BusinessService;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.parser.ServiceParserException;

/**
 * Oracle Service Bus 10gr3 - Business Service
 * 
 * @author Tomas Frastia
 * 
 */
public final class OraSB10gBusinessServiceParser extends AbstractParser {

	public final BusinessService parseBusinessService(File file) throws ServiceParserException {
		Element eXml = parseXml(file);

		Element eXmlFragment = eXml.element("xml-fragment");
		if (eXmlFragment != null) {
			BusinessService businessService = new BusinessService(file);
			return businessService;
		}
		return null;
	}
}
