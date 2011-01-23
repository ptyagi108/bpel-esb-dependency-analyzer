package com.tomecode.soa.ora.osb10g.parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.ora.osb10g.services.Binding;
import com.tomecode.soa.ora.osb10g.services.Binding.BindingType;
import com.tomecode.soa.ora.osb10g.services.Binding.WsdlServiceBinding;
import com.tomecode.soa.ora.osb10g.services.Binding.WsldServiceBindingType;
import com.tomecode.soa.ora.osb10g.services.Service;
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
import com.tomecode.soa.ora.osb10g.services.config.EndpointUNKNOWN;
import com.tomecode.soa.ora.osb10g.services.config.EndpointWS;
import com.tomecode.soa.ora.osb10g.services.config.ProviderSpecificDsp;
import com.tomecode.soa.ora.osb10g.services.config.ProviderSpecificEJB;
import com.tomecode.soa.ora.osb10g.services.config.ProviderSpecificHttp;
import com.tomecode.soa.ora.osb10g.services.config.ProviderSpecificJms;
import com.tomecode.soa.ora.osb10g.services.protocols.ftp.FtpServer;
import com.tomecode.soa.ora.osb10g.services.protocols.ftp.SFtpServer;
import com.tomecode.soa.ora.osb10g.services.protocols.jms.JMSConnectionFactory;
import com.tomecode.soa.ora.osb10g.services.protocols.jms.JMSServer;
import com.tomecode.soa.parser.AbstractParser;
import com.tomecode.soa.protocols.Node;
import com.tomecode.soa.protocols.ejb.EjbHome;
import com.tomecode.soa.protocols.ejb.EjbMethod;
import com.tomecode.soa.protocols.ejb.EjbObject;
import com.tomecode.soa.protocols.ejb.EjbProvider;
import com.tomecode.soa.protocols.file.File;
import com.tomecode.soa.protocols.http.HttpServer;

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
	protected final EndpointConfig<?> parseEndpointConfig(Element eXml, Service service) {
		EndpointConfig<?> endpoint = null;

		Element eEndpointConfig = eXml.element("endpointConfig");

		if (eEndpointConfig != null) {
			String providerId = eEndpointConfig.elementText("provider-id");
			if (ProviderProtocol.LOCAL.name().equalsIgnoreCase(providerId)) {
				endpoint = new EndpointLocal();
			} else if (ProviderProtocol.EJB.name().equalsIgnoreCase(providerId)) {
				endpoint = parseEJBtransport(eEndpointConfig, service);
			} else if (ProviderProtocol.HTTP.name().equalsIgnoreCase(providerId)) {
				endpoint = parseHttpTransport(eEndpointConfig, service);
			} else if (ProviderProtocol.JCA.name().equalsIgnoreCase(providerId)) {
				endpoint = parseJCAtransport(eEndpointConfig);
			} else if (ProviderProtocol.SB.name().equalsIgnoreCase(providerId)) {
				endpoint = parseSBtransport(eEndpointConfig);
			} else if (ProviderProtocol.WS.name().equalsIgnoreCase(providerId)) {
				endpoint = parseWStransport(eEndpointConfig);
			} else if (ProviderProtocol.FILE.name().equalsIgnoreCase(providerId)) {
				endpoint = parseFILEtransport(eEndpointConfig, service);
			} else if (ProviderProtocol.DSP.name().equalsIgnoreCase(providerId)) {
				endpoint = parseDSPtransport(eEndpointConfig);
			} else if (ProviderProtocol.FTP.name().equalsIgnoreCase(providerId)) {
				endpoint = parseFTPtransport(eEndpointConfig, service);
			} else if (ProviderProtocol.JPD.name().equalsIgnoreCase(providerId)) {
				endpoint = parseJPDtransport(eEndpointConfig);
			} else if (ProviderProtocol.JMS.name().equalsIgnoreCase(providerId)) {
				endpoint = parseJMStransport(eEndpointConfig, service);
			} else if (ProviderProtocol.MAIL.name().equalsIgnoreCase(providerId)) {
				endpoint = parseMAILtransport(eEndpointConfig);
			} else if (ProviderProtocol.MQ.name().equalsIgnoreCase(providerId)) {
				endpoint = parseMQtransport(eEndpointConfig);
			} else if (ProviderProtocol.SFTP.name().equalsIgnoreCase(providerId)) {
				endpoint = parseSFTPtransport(eEndpointConfig, service);
			} else if (ProviderProtocol.BPEL_10G.name().equalsIgnoreCase(providerId)) {
				endpoint = parseBPEL10Gtransport(eEndpointConfig);
			} else {
				endpoint = parseUnknownEndpoint(eEndpointConfig);
			}

			endpoint.setInbound(Boolean.parseBoolean(eEndpointConfig.elementText("inbound")));
		} else {
			endpoint = parseUnknownEndpoint(eEndpointConfig);
		}
		return endpoint;
	}

	/**
	 * parse unknown endpoint
	 * 
	 * @param eEndpointConfig
	 * @return
	 */
	private final EndpointUNKNOWN parseUnknownEndpoint(Element eEndpointConfig) {
		EndpointUNKNOWN unknown = new EndpointUNKNOWN();
		unknown.setProviderId(eEndpointConfig.elementText("provider-id"));
		unknown.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		return unknown;
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
	private final EndpointSFTP parseSFTPtransport(Element eEndpointConfig, Service service) {
		EndpointSFTP sftp = new EndpointSFTP();
		sftp.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		for (SFtpServer sFtpServer : sftp.getSFtpServers()) {
			sFtpServer.setParentService(service);
		}
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
	private final EndpointJms parseJMStransport(Element eEndpointConfig, Service parentService) {
		EndpointJms jms = new EndpointJms();
		jms.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		jms.setProviderSpecificJms(parseProviderSpecificJms(eEndpointConfig.element("provider-specific")));
		jms.setParentService(parentService);
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
	private final EndpointFTP parseFTPtransport(Element eEndpointConfig, Service service) {
		EndpointFTP ftp = new EndpointFTP();
		ftp.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		for (Node<FtpServer> ftpServer : ftp.getNodes()) {
			ftpServer.getObj().setParentService(service);
		}
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
	private final EndpointFile parseFILEtransport(Element eEndpointConfig, Service service) {
		EndpointFile file = new EndpointFile();
		file.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		for (File f : file.getNodes()) {
			f.setParentService(service);
		}
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
	private final EndpointHttp parseHttpTransport(Element eEndpointConfig, Service service) {
		EndpointHttp http = new EndpointHttp();
		http.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));
		http.setProviderSpecificHttp(parseProviderSpecificHttp(eEndpointConfig.element("provider-specific")));
		for (Node<HttpServer> httpServer : http.getNodes()) {
			httpServer.getObj().setParentService(service);
		}
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
	private final EndpointEJB parseEJBtransport(Element eEndpointConfig, Service service) {
		EndpointEJB ejb = new EndpointEJB();
		ejb.putAllURI(parseTrasportURI(eEndpointConfig.elements("URI")));

		if (!ejb.getUris().isEmpty()) {
			EjbHome ejbHome = parseEjbHome(eEndpointConfig.element("provider-specific"));
			if (ejbHome != null) {
				String[] ejbUris = ejb.getUris().get(0).toString().split(":");
				EjbProvider ejbProvider = new EjbProvider(ejbUris[1]);
				ejbProvider.addEjbHome(ejbHome);
				ejb.addEjbProvider(ejbProvider);
				ejbProvider.setParentService(service);
			}
		}

		return ejb;
	}

	/**
	 * parse {@link ProviderSpecificEJB}
	 * 
	 * @param element
	 * @return
	 */
	private final EjbHome parseEjbHome(Element element) {
		if (element != null) {
			Element eService = element.element("service");
			if (eService != null) {
				Element eEjbHome = eService.element("ejbHome");
				if (eEjbHome != null) {
					EjbHome ejbHome = new EjbHome(eEjbHome.attributeValue("classname"));
					Element eEjbObject = eService.element("ejbObject");
					if (eEjbObject != null) {
						EjbObject ejbObject = new EjbObject(eEjbObject.attributeValue("classname"));
						ejbHome.addEjbObject(ejbObject);

						Element eMethod = eEjbObject.element("method");
						if (eMethod != null) {
							ejbObject.addEjbMethod(new EjbMethod(eMethod.attributeValue("name"), eMethod.attributeValue("signature")));
						}
					}

					return ejbHome;
				}
			}
		}

		return null;
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

	/**
	 * parse list of URIs to {@link JMSServer}
	 * 
	 * @param jmsUris
	 */
	public static final void parseJMSServerUris(List<String> jmsUris, List<JMSServer> jmsServers) {
		for (String jmsUri : jmsUris) {
			parseJMSServerUris(jmsUri, jmsServers);
		}
	}

	/**
	 * parse list of URIs to {@link JMSServer}
	 * 
	 * @param jmsUri
	 * @param jmsServers
	 */
	public static final void parseJMSServerUris(String jmsUri, List<JMSServer> jmsServers) {
		int index = jmsUri.indexOf("jms://");
		if (index != -1) {
			jmsUri = jmsUri.substring(index + "jms://".length());
			index = jmsUri.indexOf("/");
			if (index != -1) {

				String jmsResource = jmsUri.substring(index + 1);
				JMSConnectionFactory connectionFactory = null;
				int moduleIndex = jmsResource.indexOf("/");
				if (moduleIndex == -1) {
					connectionFactory = new JMSConnectionFactory(jmsResource);
				} else {
					connectionFactory = new JMSConnectionFactory(jmsResource.substring(0, moduleIndex));
					connectionFactory.addJmsQueue(jmsResource.substring(moduleIndex + 1));
				}

				String serversURI = jmsUri.substring(0, index);

				String servers[] = serversURI.split(",");
				for (String server : servers) {
					JMSServer jmsServer = new JMSServer(server);

					if (!existsJMSServer(jmsServers, jmsServer)) {
						jmsServer.addJMSConnectionFactory(connectionFactory);
						jmsServers.add(jmsServer);
					}
				}

			}

		}
	}

	/**
	 * check, whether exists {@link JMSServer}
	 * 
	 * @param jmsServers
	 * @param jmsServer
	 * @return
	 */
	private final static boolean existsJMSServer(List<JMSServer> jmsServers, JMSServer jmsServer) {
		for (JMSServer server : jmsServers) {
			if (server.getName().equals(jmsServer.getName())) {
				if (server.getPort() != null) {
					if (server.getPort().equals(jmsServer.getPort())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * parse element binding
	 * 
	 * @param eBinding
	 * @param proxy
	 */
	protected final Binding parseBinding(Element eBinding) {
		if (eBinding != null) {
			BindingType bindingType = BindingType.parse(eBinding.attributeValue("type"));
			Binding binding = new Binding(bindingType, Boolean.parseBoolean(eBinding.attributeValue("isSoap12")));

			Element eRequest = eBinding.element("request");
			if (eRequest != null) {
				binding.setRequest(eRequest.attributeValue("type"));
			}
			Element eResponse = eBinding.element("response");
			if (eResponse != null) {
				binding.setResponse(eResponse.attributeValue("type"));
			}

			if (bindingType == BindingType.SOAP_SERVICES) {
				Element eWsdl = eBinding.element("wsdl");
				if (eWsdl != null) {
					binding.setWsdlRef(eWsdl.attributeValue("ref"));
				}

				Element eWsdlBinding = eBinding.element("binding");
				if (eWsdlBinding != null) {
					String name = eWsdlBinding.elementTextTrim("name");
					String namespace = eWsdlBinding.elementTextTrim("namespace");
					WsdlServiceBinding wsdlServiceBinding = new WsdlServiceBinding(WsldServiceBindingType.BINDING, name, namespace);
					binding.setWsdlServiceBinding(wsdlServiceBinding);
				}

				Element eWsdlPort = eBinding.element("port");
				if (eWsdlPort != null) {
					String name = eWsdlPort.elementTextTrim("name");
					String namespace = eWsdlPort.elementTextTrim("namespace");
					WsdlServiceBinding wsdlServiceBinding = new WsdlServiceBinding(WsldServiceBindingType.PORT, name, namespace);
					binding.setWsdlServiceBinding(wsdlServiceBinding);
				}

				Element eSelector = eBinding.element("selector");
				if (eSelector != null) {
					Element eMapping = eSelector.element("mapping");
					if (eMapping != null) {
						binding.setWsdlOperation(eMapping.attributeValue("operation"));
					}
				}
			}

			return binding;
		}

		return null;
	}

	/**
	 * parse {@link HttpServer}
	 * 
	 * @param uris
	 * @param httpServers
	 */
	public final static void parseHttpServersUris(List<String> uris, List<HttpServer> httpServers) {
		for (String uri : uris) {
			boolean isHttps = false;
			try {
				URL url = new URL(uri);
				if ("https".equals(url.getProtocol())) {
					isHttps = true;
				}
				HttpServer exists = findExistsHttpServer(isHttps, url.getHost(), url.getPort(), httpServers);
				if (exists == null) {
					exists = new HttpServer(isHttps, url.getHost(), url.getPort());
					httpServers.add(exists);
				}
				if (url.getPath().trim().length() != 0) {
					exists.addUrl(url.getPath());
				}
			} catch (MalformedURLException e) {
				HttpServer exists = findExistsHttpServer(false, "", -1, httpServers);
				if (exists == null) {
					exists = new HttpServer(false, "", -1);
					httpServers.add(exists);
				}
				if (uri.trim().length() != 0) {
					exists.addUrl(uri);
				}
			}
		}
	}

	/**
	 * check, whether exists {@link HttpServer}
	 * 
	 * @param isHttps
	 * @param url
	 * @param httpServers
	 * @return
	 */
	private final static HttpServer findExistsHttpServer(boolean isHttps, String host, int port, List<HttpServer> httpServers) {
		for (HttpServer httpServer : httpServers) {
			if (host.equals(httpServer.getServer()) && isHttps == httpServer.isHttps() && port == httpServer.getPort()) {
				return httpServer.getObj();
			}
		}
		return null;
	}

	/**
	 * parse FTP urls
	 * 
	 * @param uris
	 * @param ftpServers
	 */
	public final static void parseFtpServerUris(List<String> uris, List<FtpServer> ftpServers) {
		for (String uri : uris) {
			try {
				URL url = new URL(uri);
				FtpServer ftpServer = findExistsFtpServer(url.getHost(), url.getPort(), ftpServers);
				if (ftpServer == null) {
					ftpServer = new FtpServer(url.getHost(), url.getPort());
					ftpServers.add(ftpServer);
				}
				if (url.getPath().trim().length() != 0) {
					ftpServer.addUrl(url.getPath());
				}
			} catch (MalformedURLException e) {
			}
		}
	}

	/**
	 * check, whether exists {@link FtpServer}
	 * 
	 * @param host
	 * @param port
	 * @param ftpServers
	 * @return
	 */
	private final static FtpServer findExistsFtpServer(String host, int port, List<FtpServer> ftpServers) {
		for (FtpServer ftpServer : ftpServers) {
			if (host.equals(ftpServer.getServer()) && port == ftpServer.getPort()) {
				return ftpServer;
			}
		}
		return null;
	}

	private final static SFtpServer findExistsSFtpServer(String host, int port, List<SFtpServer> ftpServers) {
		for (SFtpServer ftpServer : ftpServers) {
			if (host.equals(ftpServer.getServer()) && port == ftpServer.getPort()) {
				return ftpServer;
			}
		}
		return null;
	}

	public static void parseSFtpServerUris(List<String> uris, List<SFtpServer> ftpServers) {
		for (String uri : uris) {
			try {
				URL url = new URL(uri);
				SFtpServer ftpServer = findExistsSFtpServer(url.getHost(), url.getPort(), ftpServers);
				if (ftpServer == null) {
					ftpServer = new SFtpServer(url.getHost(), url.getPort());
					ftpServers.add(ftpServer);
				}
				if (url.getPath().trim().length() != 0) {
					ftpServer.addUrl(url.getPath());
				}
			} catch (MalformedURLException e) {
			}
		}
	}

}
