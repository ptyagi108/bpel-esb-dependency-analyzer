package com.tomecode.soa.ora.osb10g.parser;

import java.io.File;

import org.dom4j.Element;

import com.tomecode.soa.ora.osb10g.services.BusinessService;
import com.tomecode.soa.parser.ServiceParserException;

/**
 * Oracle Service Bus 10gr3 - Business Service
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class OraSB10gBusinessServiceParser extends OraSB10gBasicServiceParser {

	/**
	 * Parse OSB Business Service file
	 * 
	 * @param file
	 * @return
	 * @throws ServiceParserException
	 */
	public final BusinessService parseBusinessService(File file) throws ServiceParserException {
		Element eXml = parseXml(file);
		if ("xml-fragment".equals(eXml.getName())) {
			BusinessService businessService = new BusinessService(file);
			businessService.setEndpointConfig(parseEndpointConfig(eXml));
			return businessService;
		}
		return null;
	}

}
