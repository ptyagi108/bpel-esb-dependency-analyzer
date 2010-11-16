package com.tomecode.soa.ora.osb10g.parser;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.ora.osb10g.services.config.EndpointBPEL10g;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig.ProviderProtocol;
import com.tomecode.soa.ora.osb10g.services.config.EndpointDsp;
import com.tomecode.soa.ora.osb10g.services.config.EndpointEJB;
import com.tomecode.soa.ora.osb10g.services.config.EndpointFTP;
import com.tomecode.soa.ora.osb10g.services.config.EndpointFile;
import com.tomecode.soa.ora.osb10g.services.config.EndpointHttp;
import com.tomecode.soa.ora.osb10g.services.config.EndpointJPD;
import com.tomecode.soa.ora.osb10g.services.config.EndpointJca;
import com.tomecode.soa.ora.osb10g.services.config.EndpointJms;
import com.tomecode.soa.ora.osb10g.services.config.EndpointLocal;
import com.tomecode.soa.ora.osb10g.services.config.EndpointMQ;
import com.tomecode.soa.ora.osb10g.services.config.EndpointMail;
import com.tomecode.soa.ora.osb10g.services.config.EndpointSB;
import com.tomecode.soa.ora.osb10g.services.config.EndpointSFTP;
import com.tomecode.soa.ora.osb10g.services.config.EndpointWS;
import com.tomecode.soa.ora.osb10g.services.config.ProviderSpecificDsp;
import com.tomecode.soa.ora.osb10g.services.config.ProviderSpecificEJB;
import com.tomecode.soa.ora.osb10g.services.config.ProviderSpecificHttp;
import com.tomecode.soa.ora.osb10g.services.config.ProviderSpecificJms;
import com.tomecode.soa.parser.AbstractParser;

/**
 * 
 * Basic parser for Proxy and Business service
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public abstract class OraSB10gBasicServiceParser extends AbstractParser {

	/**
	 * parsing endpoint config
	 * 
	 * @param eXml
	 * @return
	 */
	protected final EndpointConfig parseEndpointConfig(Element eXml) {
		Element eEndpointConfig = eXml.element("endpointConfig");
		if (eEndpointConfig != null) {
			String providerId = eEndpointConfig.elementText("provider-id");
			if (ProviderProtocol.LOCAL.name().equalsIgnoreCase(providerId)) {
				return new EndpointLocal();
			} else if (ProviderProtocol.EJB.name().equalsIgnoreCase(providerId)) {
				return parseEJBtransport(eEndpointConfig);
			} else if (ProviderProtocol.HTTP.name().equalsIgnoreCase(providerId)) {
				return parseHttpTransport(eEndpointConfig);
			} else if (ProviderProtocol.JCA.name().equalsIgnoreCase(providerId)) {
				return parseJCAtransport(eEndpointConfig);
			} else if (ProviderProtocol.SB.name().equalsIgnoreCase(providerId)) {
				return parseSBtransport(eEndpointConfig);
			} else if (ProviderProtocol.WS.name().equalsIgnoreCase(providerId)) {
				return parseWStransport(eEndpointConfig);
			} else if (ProviderProtocol.FILE.name().equalsIgnoreCase(providerId)) {
				return parseFILEtransport(eEndpointConfig);
			} else if (ProviderProtocol.DSP.name().equalsIgnoreCase(providerId)) {
				return parseDSPtransport(eEndpointConfig);
			} else if (ProviderProtocol.FTP.name().equalsIgnoreCase(providerId)) {
				return parseFTPtransport(eEndpointConfig);
			} else if (ProviderProtocol.JPD.name().equalsIgnoreCase(providerId)) {
				return parseJPDtransport(eEndpointConfig);
			} else if (ProviderProtocol.JMS.name().equalsIgnoreCase(providerId)) {
				return parseJMStransport(eEndpointConfig);
			} else if (ProviderProtocol.MAIL.name().equalsIgnoreCase(providerId)) {
				return parseMAILtransport(eEndpointConfig);
			} else if (ProviderProtocol.MQ.name().equalsIgnoreCase(providerId)) {
				return parseMQtransport(eEndpointConfig);
			} else if (ProviderProtocol.SFTP.name().equalsIgnoreCase(providerId)) {
				return parseSFTPtransport(eEndpointConfig);
			} else if (ProviderProtocol.BPEL_10G.name().equalsIgnoreCase(providerId)) {
				return parseBPEL10Gtransport(eEndpointConfig);
			}
		}
		return null;
	}

	/**
	 * parsing endpoint with BPEL-10g transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointBPEL10g parseBPEL10Gtransport(Element eEndpointConfig) {
		EndpointBPEL10g bpel10g = new EndpointBPEL10g();
		bpel10g.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return bpel10g;
	}

	/**
	 * parsing endpoint with SFTP transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointSFTP parseSFTPtransport(Element eEndpointConfig) {
		EndpointSFTP sftp = new EndpointSFTP();
		sftp.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return sftp;
	}

	/**
	 * parsing endpoint with MQ transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointMQ parseMQtransport(Element eEndpointConfig) {
		EndpointMQ mq = new EndpointMQ();
		mq.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return mq;
	}

	/**
	 * parsing endpoint with MAIL transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointMail parseMAILtransport(Element eEndpointConfig) {
		EndpointMail mail = new EndpointMail();
		mail.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return mail;
	}

	/**
	 * parsing endpoint with JMS transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointJms parseJMStransport(Element eEndpointConfig) {
		EndpointJms jms = new EndpointJms();
		jms.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		jms.setProviderSpecificJms(parseProviderSpecificJms(eEndpointConfig.element("provider-specific")));
		return jms;
	}

	/**
	 * parsing endpoint with JPD transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointJPD parseJPDtransport(Element eEndpointConfig) {
		EndpointJPD jpd = new EndpointJPD();
		jpd.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return jpd;
	}

	/**
	 * parsing endpoint with FTP transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointFTP parseFTPtransport(Element eEndpointConfig) {
		EndpointFTP ftp = new EndpointFTP();
		ftp.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return ftp;
	}

	/**
	 * parsing endpoint with DSP transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointDsp parseDSPtransport(Element eEndpointConfig) {
		EndpointDsp dsp = new EndpointDsp();
		dsp.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		dsp.setProviderSpecificDsp(parseProviderSpecificDsp(eEndpointConfig.element("provider-specific")));
		return dsp;
	}

	/**
	 * parse {@link ProviderSpecificDsp}
	 * 
	 * @param element
	 * @return
	 */
	private final ProviderSpecificDsp parseProviderSpecificDsp(Element element) {
		ProviderSpecificDsp providerSpecificDsp = new ProviderSpecificDsp();
		if (element != null) {
			providerSpecificDsp.setRequestResponse(Boolean.parseBoolean(element.elementTextTrim("request-response")));
		}
		return providerSpecificDsp;
	}

	/**
	 * parsing endpoint with WS transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointFile parseFILEtransport(Element eEndpointConfig) {
		EndpointFile file = new EndpointFile();
		file.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return file;
	}

	/**
	 * parsing endpoint with WS transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointWS parseWStransport(Element eEndpointConfig) {
		EndpointWS ws = new EndpointWS();
		ws.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return ws;
	}

	/**
	 * parsing endpoint with SB transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointSB parseSBtransport(Element eEndpointConfig) {
		EndpointSB sb = new EndpointSB();
		sb.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return sb;
	}

	/**
	 * parsing endpoing with JCA transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointJca parseJCAtransport(Element eEndpointConfig) {
		EndpointJca jca = new EndpointJca();
		jca.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return jca;
	}

	/**
	 * parsing endpoint with HTTP transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointHttp parseHttpTransport(Element eEndpointConfig) {
		EndpointHttp http = new EndpointHttp();
		http.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		http.setProviderSpecificHttp(parseProviderSpecificHttp(eEndpointConfig.element("provider-specific")));
		return http;
	}

	/**
	 * parse provider specific HTTP
	 * 
	 * @param element
	 * @return
	 */
	private final ProviderSpecificHttp parseProviderSpecificHttp(Element element) {
		ProviderSpecificHttp providerSpecificHttp = new ProviderSpecificHttp();
		if (element != null) {
			Element outboundProperties = element.element("outbound-properties");
			if (outboundProperties != null) {
				providerSpecificHttp.setRequestMethod(outboundProperties.elementTextTrim("request-method"));
			}
		}

		return providerSpecificHttp;
	}

	/**
	 * parsing endpoint with EJB transport
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointEJB parseEJBtransport(Element eEndpointConfig) {
		EndpointEJB ejb = new EndpointEJB();
		ejb.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		ejb.setProviderSpecificEJB(parseProviderSpecificEJB(eEndpointConfig.element("provider-specific")));
		return ejb;
	}

	/**
	 * parse {@link ProviderSpecificEJB}
	 * 
	 * @param element
	 * @return
	 */
	private final ProviderSpecificEJB parseProviderSpecificEJB(Element element) {
		ProviderSpecificEJB providerSpecificEJB = new ProviderSpecificEJB();
		if (element != null) {
			Element eService = element.element("service");
			if (eService != null) {
				Element e = eService.element("clientJar");
				if (e != null) {
					providerSpecificEJB.setClientJar(e.attributeValue("ref"));
				}
				e = eService.element("ejbHome");
				if (e != null) {
					providerSpecificEJB.setEjbHome(e.attributeValue("classname"));
				}
				e = eService.element("ejbObject");
				if (e != null) {
					providerSpecificEJB.setEjbObject(e.attributeValue("classname"));
				}
			}
		}
		return providerSpecificEJB;
	}

	/**
	 * parse all URI element
	 * 
	 * @param eURI
	 * @return
	 */
	private final List<String> parseTrasportURI(List<?> eURI) {
		List<String> list = new ArrayList<String>();
		if (eURI != null) {
			for (Object o : eURI) {
				Element e = (Element) o;
				String value = e.elementTextTrim("value");
				if (value != null && value.trim().length() != 0) {
					list.add(value);
				}
			}
		}
		return list;
	}

	/**
	 * parse provider specific JMS
	 * 
	 * @param list
	 * @return
	 */
	private final ProviderSpecificJms parseProviderSpecificJms(Element element) {
		ProviderSpecificJms providerSpecificJms = new ProviderSpecificJms();
		if (element != null) {
			providerSpecificJms.setQueue(Boolean.parseBoolean(element.elementTextTrim("is-queue")));
			Element outboundProperties = element.element("outbound-properties");
			if (outboundProperties != null) {
				boolean isResponseRequired = Boolean.parseBoolean(outboundProperties.elementTextTrim("response-required"));
				providerSpecificJms.setResponseRequired(isResponseRequired);
				if (!isResponseRequired) {
					return providerSpecificJms;
				}
				providerSpecificJms.setResponseURI(outboundProperties.elementText("response-URI"));
			}
		}
		return providerSpecificJms;
	}
}
