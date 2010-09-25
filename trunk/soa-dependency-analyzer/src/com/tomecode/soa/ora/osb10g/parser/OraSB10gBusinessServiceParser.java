package com.tomecode.soa.ora.osb10g.parser;

import java.io.File;

import org.dom4j.Element;

import com.tomecode.soa.ora.osb10g.services.BusinessService;
import com.tomecode.soa.ora.osb10g.services.EndpointWS;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig;
import com.tomecode.soa.ora.osb10g.services.config.EndpointHttp;
import com.tomecode.soa.ora.osb10g.services.config.EndpointJca;
import com.tomecode.soa.ora.osb10g.services.config.EndpointLocal;
import com.tomecode.soa.ora.osb10g.services.config.EndpointSB;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig.ProviderProtocol;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.parser.ServiceParserException;

/**
 * Oracle Service Bus 10gr3 - Business Service
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class OraSB10gBusinessServiceParser extends AbstractParser {

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

	/**
	 * endpoint config
	 * 
	 * @param eXml
	 * @return
	 */
	private final EndpointConfig parseEndpointConfig(Element eXml) {
		Element eEndpointConfig = eXml.element("endpointConfig");
		if (eEndpointConfig != null) {
			String providerId = eEndpointConfig.elementText("provider-id");
			if (ProviderProtocol.HTTP.name().equalsIgnoreCase(providerId)) {
				return new EndpointHttp(parseEndpointUri(eEndpointConfig));
			} else if (ProviderProtocol.JCA.name().equalsIgnoreCase(providerId)) {
				return new EndpointJca(parseEndpointUri(eEndpointConfig));
			} else if (ProviderProtocol.LOCAL.name().equalsIgnoreCase(providerId)) {
				return new EndpointLocal();
			} else if (ProviderProtocol.SB.name().equalsIgnoreCase(providerId)) {
				return new EndpointSB(parseEndpointUri(eEndpointConfig));
			} else if (ProviderProtocol.WS.name().equalsIgnoreCase(providerId)) {
				return new EndpointWS(parseEndpointUri(eEndpointConfig));
			}
		}
		return null;
	}

	private final String parseEndpointUri(Element eEndpointConfig) {
		Element eUri = eEndpointConfig.element("URI");
		return eUri == null ? null : eUri.elementTextTrim("value");
	}
}
