package com.tomecode.soa.ora.osb10g.parser;

import java.io.File;
import java.io.InputStream;

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
	 * Parse Business Service in OSB 10g
	 * 
	 * @param file
	 * @return
	 * @throws ServiceParserException
	 */
	public final BusinessService parseBusinessService(File file) throws ServiceParserException {
		return parseBusinessService(file, parseXml(file));

	}

	/**
	 * Parse Business Service in OSB 10g
	 * 
	 * @param file
	 * @param inputStream
	 * @return
	 * @throws ServiceParserException
	 */
	public final BusinessService parseBusinessService(File file, InputStream inputStream) throws ServiceParserException {
		return parseBusinessService(file, parseXml(inputStream));
	}

	private final BusinessService parseBusinessService(File file, Element eXml) {
		if ("xml-fragment".equals(eXml.getName())) {
			BusinessService businessService = new BusinessService(file);
			Element eCoreEntry = eXml.element("coreEntry");
			if (eCoreEntry != null) {
				businessService.setDescription(eCoreEntry.elementText("description"));
				businessService.setBinding(parseBinding(eCoreEntry.element("binding")));
			}
			businessService.setEndpointConfig(parseEndpointConfig(eXml, businessService));
			return businessService;
		}
		return null;
	}

}
