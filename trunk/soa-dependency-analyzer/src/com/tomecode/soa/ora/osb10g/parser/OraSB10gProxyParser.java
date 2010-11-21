package com.tomecode.soa.ora.osb10g.parser;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import com.tomecode.soa.ora.osb10g.activity.Alert;
import com.tomecode.soa.ora.osb10g.activity.Assign;
import com.tomecode.soa.ora.osb10g.activity.Branch;
import com.tomecode.soa.ora.osb10g.activity.BranchNodeCondition;
import com.tomecode.soa.ora.osb10g.activity.BranchNodeOperation;
import com.tomecode.soa.ora.osb10g.activity.DefaultBranch;
import com.tomecode.soa.ora.osb10g.activity.Delete;
import com.tomecode.soa.ora.osb10g.activity.DynamicRoute;
import com.tomecode.soa.ora.osb10g.activity.ElseIf;
import com.tomecode.soa.ora.osb10g.activity.Error;
import com.tomecode.soa.ora.osb10g.activity.Foreach;
import com.tomecode.soa.ora.osb10g.activity.If;
import com.tomecode.soa.ora.osb10g.activity.IfDefault;
import com.tomecode.soa.ora.osb10g.activity.IfThenElse;
import com.tomecode.soa.ora.osb10g.activity.Insert;
import com.tomecode.soa.ora.osb10g.activity.JavaCallout;
import com.tomecode.soa.ora.osb10g.activity.Log;
import com.tomecode.soa.ora.osb10g.activity.MflTransform;
import com.tomecode.soa.ora.osb10g.activity.OsbActivity;
import com.tomecode.soa.ora.osb10g.activity.PipelinePairNode;
import com.tomecode.soa.ora.osb10g.activity.PipelineRequest;
import com.tomecode.soa.ora.osb10g.activity.PipelineResponse;
import com.tomecode.soa.ora.osb10g.activity.Rename;
import com.tomecode.soa.ora.osb10g.activity.Replace;
import com.tomecode.soa.ora.osb10g.activity.Reply;
import com.tomecode.soa.ora.osb10g.activity.Report;
import com.tomecode.soa.ora.osb10g.activity.Route;
import com.tomecode.soa.ora.osb10g.activity.RouteNode;
import com.tomecode.soa.ora.osb10g.activity.RouteOutboundTransform;
import com.tomecode.soa.ora.osb10g.activity.RouteResponseTransform;
import com.tomecode.soa.ora.osb10g.activity.Router;
import com.tomecode.soa.ora.osb10g.activity.RoutingOptions;
import com.tomecode.soa.ora.osb10g.activity.RoutingTable;
import com.tomecode.soa.ora.osb10g.activity.RoutingTableCase;
import com.tomecode.soa.ora.osb10g.activity.RoutingTableDefaultCase;
import com.tomecode.soa.ora.osb10g.activity.Skip;
import com.tomecode.soa.ora.osb10g.activity.Stage;
import com.tomecode.soa.ora.osb10g.activity.TransportHeaders;
import com.tomecode.soa.ora.osb10g.activity.Validate;
import com.tomecode.soa.ora.osb10g.activity.WsCallout;
import com.tomecode.soa.ora.osb10g.activity.WsCalloutRequestTransform;
import com.tomecode.soa.ora.osb10g.activity.WsCalloutResponseTransform;
import com.tomecode.soa.ora.osb10g.services.Binding;
import com.tomecode.soa.ora.osb10g.services.Binding.BindingType;
import com.tomecode.soa.ora.osb10g.services.Binding.WsdlServiceBinding;
import com.tomecode.soa.ora.osb10g.services.Binding.WsldServiceBindingType;
import com.tomecode.soa.ora.osb10g.services.Proxy;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependencies;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency;
import com.tomecode.soa.ora.osb10g.services.dependnecies.ServiceDependency.ServiceDependencyType;
import com.tomecode.soa.parser.ServiceParserException;

/**
 * Oracle SB 10g PROXY service parser
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public final class OraSB10gProxyParser extends OraSB10gBasicServiceParser {

	// public static final void main(String[] arg) {
	// String ms =
	// "<ctx:route> <ctx:service isProxy='true'>{concat\"var/flow/AVT\",$MSGTYPE)}</ctx:service> </ctx:route>";
	// parseXqueryRouting(ms);
	// }
	//
	// private static final String parseXqueryRouting(String xQuery) {
	// if (xQuery == null || xQuery.trim().length() == 0) {
	// return null;
	// }
	// int index = xQuery.indexOf(":service");
	// if (index != -1) {
	//
	// }
	//
	// return null;
	// }

	/**
	 * parse Proxy Service in OSB 10g
	 * 
	 * @param file
	 * @return
	 * @throws ServiceParserException
	 */
	public final Proxy parseProxy(File file) throws ServiceParserException {
		return parseProxy(file, parseXml(file));
	}

	/**
	 * parse Proxy Service in OSB 10g
	 * 
	 * @param file
	 * @param inputStream
	 * @return
	 * @throws ServiceParserException
	 */
	public final Proxy parseProxy(File file, InputStream inputStream) throws ServiceParserException {
		return parseProxy(file, parseXml(inputStream));
	}

	public final Proxy parseProxy(File file, Element eXml) throws ServiceParserException {
		if (eXml != null) {
			Element eCoreEntry = eXml.element("coreEntry");
			if (eCoreEntry != null) {
				Proxy proxy = new Proxy(file);
				proxy.setEndpointConfig(parseEndpointConfig(eXml));
				parseCoreEntrty(eCoreEntry, proxy);
				parseFlow(eXml.element("router"), proxy);
				return proxy;
			}
		}
		return null;
	}

	/**
	 * parse element coreEntry
	 * 
	 * @param eCoreEntry
	 * @param proxy
	 */
	private final void parseCoreEntrty(Element eCoreEntry, Proxy proxy) {
		proxy.setIsEnabled(Boolean.parseBoolean(eCoreEntry.attributeValue("isEnabled")));
		proxy.setIsAutoPublish(Boolean.parseBoolean(eCoreEntry.attributeValue("isAutoPublish")));
		proxy.setDescription(eCoreEntry.elementText("description"));

		parseBinding(eCoreEntry.element("binding"), proxy);
	}

	/**
	 * parse flow
	 * 
	 * @param eRouter
	 * @param proxy
	 */
	private final void parseFlow(Element eRouter, Proxy proxy) {
		Router router = new Router();
		proxy.getStructure().addActivity(router);
		// parseActivities(eRouter.elements(), router);

		try {
			Element eFlow = eRouter.element("flow");
			if (eFlow != null) {
				parseFlowStructure(proxy.getServiceDependencies(), eFlow.elements(), router);
				List<OsbActivity> pipelines = parsePipelines(proxy.getServiceDependencies(), eRouter.elements("pipeline"));
				linkingPipeLines(pipelines, router);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * linking pipeLine-s with flow
	 * 
	 * @param pipelines
	 * @param router
	 */
	private final void linkingPipeLines(List<OsbActivity> pipelines, Router router) {
		for (OsbActivity osbActivity : pipelines) {
			if (osbActivity instanceof PipelineRequest) {
				PipelineRequest pipelineRequest = (PipelineRequest) osbActivity;
				replacePipelineRequest(pipelineRequest, router.getActivities());
			} else if (osbActivity instanceof PipelineResponse) {
				PipelineResponse pipelineResponse = (PipelineResponse) osbActivity;
				replacePipelineResponse(pipelineResponse, router.getActivities());
			}
		}
	}

	/**
	 * 
	 * @param pipelineResponse
	 * @param root
	 */
	private final void replacePipelineResponse(PipelineResponse pipelineResponse, List<OsbActivity> activities) {
		PipelineResponse response = findPipelineResponse(pipelineResponse.getName(), activities);
		if (response != null) {
			response.merge(pipelineResponse.getActivities());
		}
	}

	/**
	 * 
	 * @param pipelineRequest
	 * @param root
	 */
	private final void replacePipelineRequest(PipelineRequest pipelineRequest, List<OsbActivity> activities) {
		PipelineRequest request = findPipelineRequest(pipelineRequest.getName(), activities);
		if (request != null) {
			request.merge(pipelineRequest.getActivities());
		}
	}

	private final PipelineResponse findPipelineResponse(String name, List<OsbActivity> activities) {
		try {
			for (OsbActivity osbActivity : activities) {
				if (osbActivity instanceof PipelineResponse && osbActivity.getActivities().isEmpty()) {
					return (PipelineResponse) osbActivity;
				}

				else {
					// return findPipelineResponse(name,
					// osbActivity.getActivities());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * find {@link PipelineRequest} in list of {@link OsbActivity}
	 * 
	 * @param name
	 * @param activities
	 * @return
	 */
	private final PipelineRequest findPipelineRequest(String name, List<OsbActivity> activities) {
		for (OsbActivity osbActivity : activities) {
			if (osbActivity instanceof PipelineRequest && osbActivity.getActivities().isEmpty()) {
				return (PipelineRequest) osbActivity;
			}

			else {
				return findPipelineRequest(name, osbActivity.getActivities());
			}
		}
		return null;
	}

	/**
	 * parse element: flow
	 * 
	 * @param serviceDepependnecies
	 *            contains all the dependencies for parsing PROXY service
	 * @param eFlow
	 *            current element in XML
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parseFlowContent(ServiceDependencies serviceDependencies, Element eFlow, OsbActivity root) {
		if (eFlow != null) {
			parseFlowStructure(serviceDependencies, eFlow.elements(), root);
		}
	}

	/**
	 * 
	 * parse request or response pipelines
	 * 
	 * @param serviceDependencies
	 *            contains all the dependencies for parsing PROXY service
	 * @param ePipelines
	 *            current element in XML
	 * @return list of parsed {@link OsbActivity}
	 */
	private final List<OsbActivity> parsePipelines(ServiceDependencies serviceDependencies, List<?> ePipelines) {
		List<OsbActivity> osbActivities = new ArrayList<OsbActivity>();
		if (ePipelines != null) {
			for (Object o : ePipelines) {
				Element element = (Element) o;
				if ("request".equals(element.attributeValue("type"))) {
					PipelineRequest pipelineRequest = new PipelineRequest(element.attributeValue("name"));
					parseActivities(serviceDependencies, element.elements(), pipelineRequest);
					osbActivities.add(pipelineRequest);
				} else if ("response".equals(element.attributeValue("type"))) {
					PipelineResponse pipelineResponse = new PipelineResponse(element.attributeValue("name"));
					parseActivities(serviceDependencies, element.elements(), pipelineResponse);
					osbActivities.add(pipelineResponse);
				}
			}
		}
		return osbActivities;
	}

	/**
	 * parse all types
	 * 
	 * 
	 * @param serviceDependencies
	 *            contains all the dependencies for parsing PROXY service
	 * @param elements
	 *            list of {@link Element}
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parseActivities(ServiceDependencies serviceDependencies, List<?> elements, OsbActivity root) {
		for (Object o : elements) {
			Element e = (Element) o;
			if ("stage".equals(e.getName())) {
				Stage stage = new Stage(e.attributeValue("name"));
				root.addActivity(stage);
				Element eActions = e.element("actions");
				if (eActions != null) {
					parseActivities(serviceDependencies, eActions.elements(), stage);
				}
			} else if ("actions".equals(e.getName())) {
				parseActivities(serviceDependencies, e.elements(), root);
			} else if ("ifThenElse".equals(e.getName())) {
				IfThenElse ifThenElse = new IfThenElse();
				root.addActivity(ifThenElse);
				parseActivities(serviceDependencies, e.elements(), ifThenElse);
			} else if ("case".equals(e.getName())) {
				if (root instanceof IfThenElse) {
					IfThenElse ifThenElse = (IfThenElse) root;
					if (ifThenElse.hasIf()) {
						ElseIf elseIf = new ElseIf();
						root.getParent().addActivity(elseIf);
						parseActivities(serviceDependencies, e.elements(), elseIf);
					} else {
						If iff = new If();
						ifThenElse.addActivity(iff);
						parseActivities(serviceDependencies, e.elements(), iff);
					}
				} else {
					parseActivities(serviceDependencies, e.elements(), root);
				}
			} else if ("delete".equals(e.getName())) {
				root.addActivity(new Delete());
			} else if ("default".equals(e.getName())) {
				if (root instanceof IfThenElse) {
					IfDefault ifDefault = new IfDefault();
					root.addActivity(ifDefault);
					parseActivities(serviceDependencies, e.elements(), ifDefault);
				}
			} else if ("Error".equals(e.getName())) {
				root.addActivity(new Error(e.elementTextTrim("errCode"), e.elementTextTrim("message")));
			} else if ("reply".equals(e.getName())) {
				root.addActivity(new Reply());
			} else if ("skip".equals(e.getName())) {
				root.addActivity(new Skip());
			} else if ("assign".equals(e.getName())) {
				root.addActivity(new Assign());
			} else if ("insert".equals(e.getName())) {
				root.addActivity(new Insert());
			} else if ("javaCallout".equals(e.getName())) {
				root.addActivity(new JavaCallout());
			} else if ("mflTransform".equals(e.getName())) {
				root.addActivity(new MflTransform());
			} else if ("rename".equals(e.getName())) {
				root.addActivity(new Rename());
			} else if ("replace".equals(e.getName())) {
				root.addActivity(new Replace());
			} else if ("validate".equals(e.getName())) {
				root.addActivity(new Validate());
			} else if ("alert".equals(e.getName())) {
				root.addActivity(new Alert(e.elementText("severity")));
			} else if ("report".equals(e.getName())) {
				root.addActivity(new Report());
			} else if ("log".equals(e.getName())) {
				root.addActivity(new Log(e.elementTextTrim("logLevel")));
			} else if ("foreach".equals(e.getName())) {
				Foreach foreach = new Foreach();
				root.addActivity(foreach);

				Element eActions = e.element("actions");
				if (eActions != null) {
					parseActivities(serviceDependencies, eActions.elements(), foreach);
				}
			} else if ("dynamic-route".equals(e.getName())) {
				DynamicRoute dynamicRoute = new DynamicRoute();
				root.addActivity(dynamicRoute);
				parseService(serviceDependencies, e, root);
				parseOutboundTransform(serviceDependencies, e, dynamicRoute);
			} else if ("route".equals(e.getName())) {
				parseRoute(serviceDependencies, e, root);
			} else if ("routingTable".equals(e.getName())) {
				parseRoutingTable(serviceDependencies, e.getParent(), root);
			} else if ("transport-headers".equals(e.getName())) {
				TransportHeaders transportHeaders = new TransportHeaders();
				root.addActivity(transportHeaders);
			} else if ("wsCallout".equals(e.getName())) {
				parseWsCallout(serviceDependencies, e, root);
			} else if ("routing-options".equals(e.getName())) {
				RoutingOptions routingOptions = new RoutingOptions();
				root.addActivity(routingOptions);
			}
		}
	}

	/**
	 * 
	 * element: wsCallout
	 * 
	 * @param serviceDependencies
	 *            contains all the dependencies for parsing PROXY service
	 * @param eWsCallout
	 *            current element in XML
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parseWsCallout(ServiceDependencies serviceDependencies, Element eWsCallout, OsbActivity root) {

		WsCallout wsCallout = new WsCallout();
		root.addActivity(wsCallout);
		parseService(serviceDependencies, eWsCallout, wsCallout);

		Element e = eWsCallout.element("requestTransform");
		if (e != null) {
			WsCalloutRequestTransform requestTransform = new WsCalloutRequestTransform();
			wsCallout.addActivity(requestTransform);
			parseActivities(serviceDependencies, e.elements(), requestTransform);
		}

		e = eWsCallout.element("responseTransform");
		if (e != null) {
			WsCalloutResponseTransform responseTransform = new WsCalloutResponseTransform();
			wsCallout.addActivity(responseTransform);
			parseActivities(serviceDependencies, e.elements(), responseTransform);
		}
	}

	/**
	 * 
	 * parse content of element: router
	 * 
	 * @param serviceDependencies
	 *            contains all the dependencies for parsing PROXY service
	 * @param elements
	 *            current element in XML
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parseFlowStructure(ServiceDependencies serviceDependencies, List<?> elements, OsbActivity root) {
		for (Object o : elements) {
			Element e = (Element) o;

			if ("pipeline-node".equals(e.getName())) {
				parsePipeLineNodeFlow(e, root);
			} else if ("branch-node".equals(e.getName())) {
				parseBranchNodeFlow(serviceDependencies, e, root);
			} else if ("route-node".equals(e.getName())) {
				parseRouteNode(serviceDependencies, e, root);
			}
		}
	}

	/**
	 * 
	 * element: route-node
	 * 
	 * @param serviceDependencies
	 *            contains all the dependencies for parsing PROXY service
	 * @param eRouteNode
	 *            current element in XML
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parseRouteNode(ServiceDependencies serviceDependencies, Element eRouteNode, OsbActivity root) {
		RouteNode routeNode = new RouteNode(eRouteNode.attributeValue("name"));
		root.addActivity(routeNode);
		Element eActions = eRouteNode.element("actions");
		if (eActions != null) {
			parseRoutingTable(serviceDependencies, eActions, routeNode);
			// parseRoute(serviceDependencies, eActions.element("route"),
			// routeNode);
			parseActivities(serviceDependencies, eActions.elements(), routeNode);
		}
	}

	/**
	 * 
	 * parse element: routingTable
	 * 
	 * @param serviceDependencies
	 *            contains all the dependencies for parsing PROXY service
	 * @param e
	 *            current element in XML
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parseRoutingTable(ServiceDependencies serviceDependencies, Element e, OsbActivity root) {
		Element eRoutingTable = e.element("routingTable");
		if (eRoutingTable != null) {

			RoutingTable routingTable = new RoutingTable();
			root.addActivity(routingTable);

			List<?> elements = eRoutingTable.elements();
			if (elements != null) {
				for (Object o : elements) {
					Element element = (Element) o;
					if ("case".equals(element.getName())) {
						RoutingTableCase routingTableCase = new RoutingTableCase();
						routingTable.addActivity(routingTableCase);
						parseRoute(serviceDependencies, element.element("route"), routingTableCase);
					} else if ("defaultCase".equals(element.getName())) {
						RoutingTableDefaultCase routingTableDefaultCase = new RoutingTableDefaultCase();
						routingTable.addActivity(routingTableDefaultCase);
						parseRoute(serviceDependencies, element.element("route"), routingTableDefaultCase);
					}
				}
			}
		}
	}

	/**
	 * parse element: route
	 * 
	 * @param serviceDependencies
	 *            contains all the dependencies for parsing PROXY service
	 * @param element
	 *            current element in XML
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parseRoute(ServiceDependencies serviceDependencies, Element element, OsbActivity root) {
		Route route = new Route();
		root.addActivity(route);
		parseService(serviceDependencies, element, route);
		parseOutboundTransform(serviceDependencies, element, route);

	}

	/**
	 * parse element: service
	 * 
	 * @param serviceDependencies
	 * @param element
	 * @param root
	 */
	private final void parseService(ServiceDependencies serviceDependencies, Element element, OsbActivity root) {
		Element eService = element.element("service");
		if (eService != null) {

			ServiceDependencyType type = ServiceDependencyType.parse(eService.attributeValue("type"));
			if (type == ServiceDependencyType.UNKNOWN) {
				Element eXqueryText = eService.element("xqueryText");
				if (eXqueryText != null) {
					eXqueryText.getTextTrim();
				}

			} else {
				ServiceDependency depndency = new ServiceDependency(root, eService.attributeValue("ref"), type);
				serviceDependencies.addDependency(depndency);
			}

		}
	}

	/**
	 * parse element: outboundTransform
	 * 
	 * @param serviceDependencies
	 *            contains all the dependencies for parsing PROXY service
	 * @param element
	 *            current element in XML
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parseOutboundTransform(ServiceDependencies serviceDependencies, Element element, OsbActivity root) {
		Element eOutboundTransform = element.element("outboundTransform");
		if (eOutboundTransform != null) {
			RouteOutboundTransform outboundTransform = new RouteOutboundTransform();
			root.addActivity(outboundTransform);
			parseActivities(serviceDependencies, eOutboundTransform.elements(), outboundTransform);
		}

		Element eResponseTransform = element.element("responseTransform");
		if (eResponseTransform != null) {
			RouteResponseTransform responseTransform = new RouteResponseTransform();
			root.addActivity(responseTransform);
			parseActivities(serviceDependencies, eResponseTransform.elements(), responseTransform);
		}
	}

	/**
	 * parse branch-node
	 * 
	 * @param serviceDependencies
	 *            contains all the dependencies for parsing PROXY service
	 * @param eBranchNode
	 *            current element in XML
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parseBranchNodeFlow(ServiceDependencies serviceDependencies, Element eBranchNode, OsbActivity root) {
		if ("condition".equals(eBranchNode.attributeValue("type"))) {
			BranchNodeCondition branchNodeCondition = new BranchNodeCondition(eBranchNode.attributeValue("name"));
			root.addActivity(branchNodeCondition);
			Element eBranchTable = eBranchNode.element("branch-table");
			if (eBranchTable != null) {
				List<?> eBranchs = eBranchTable.elements();
				for (Object o : eBranchs) {
					Element eBranch = (Element) o;
					if ("branch".equals(eBranch.getName())) {
						Branch branch = new Branch(eBranch.attributeValue("name"));
						branchNodeCondition.addActivity(branch);
						parseFlowContent(serviceDependencies, eBranch.element("flow"), branch);
					} else if ("default-branch".equals(eBranch.getName())) {
						DefaultBranch defaultBranch = new DefaultBranch();
						branchNodeCondition.addActivity(defaultBranch);
						parseFlowContent(serviceDependencies, eBranch.element("flow"), defaultBranch);
					}

				}
			}

		} else if ("operation".equals(eBranchNode.attributeValue("type"))) {
			BranchNodeOperation branchNodeOperation = new BranchNodeOperation(eBranchNode.attributeValue("name"));
			root.addActivity(branchNodeOperation);
			Element eBranchTable = eBranchNode.element("branch-table");
			if (eBranchTable != null) {
				List<?> eBranchs = eBranchTable.elements();
				for (Object o : eBranchs) {
					Element eBranch = (Element) o;
					if ("branch".equals(eBranch.getName())) {
						Branch branch = new Branch(eBranch.attributeValue("name"));
						branchNodeOperation.addActivity(branch);
						parseFlowContent(serviceDependencies, eBranch.element("flow"), branch);
					} else if ("default-branch".equals(eBranch.getName())) {
						DefaultBranch defaultBranch = new DefaultBranch();
						branchNodeOperation.addActivity(defaultBranch);
						parseFlowContent(serviceDependencies, eBranch.element("flow"), defaultBranch);
					}

				}
			}
		}
	}

	/**
	 * 
	 * parse pipeline-node
	 * 
	 * @param ePipelineNode
	 *            current element in XML
	 * @param root
	 *            parent {@link OsbActivity}
	 */
	private final void parsePipeLineNodeFlow(Element ePipelineNode, OsbActivity root) {
		PipelinePairNode pipelinePairNode = new PipelinePairNode(ePipelineNode.attributeValue("name"));
		root.addActivity(pipelinePairNode);
		List<?> elements = ePipelineNode.elements();
		for (Object o : elements) {
			Element element = (Element) o;
			if ("request".equals(element.getName())) {
				pipelinePairNode.addActivity(new PipelineRequest(element.getTextTrim()));
			} else if ("response".equals(element.getName())) {
				pipelinePairNode.addActivity(new PipelineResponse(element.getTextTrim()));
			}
		}
	}

	/**
	 * parse element binding
	 * 
	 * @param eBinding
	 * @param proxy
	 */
	private final void parseBinding(Element eBinding, Proxy proxy) {
		if (eBinding != null) {
			BindingType bindingType = BindingType.parse(eBinding.attributeValue("type"));
			Binding binding = new Binding(bindingType, Boolean.parseBoolean(eBinding.attributeValue("isSoap12")));
			proxy.setBinding(binding);
			if (bindingType == BindingType.SOAP_SERVICES) {
				Element eWsdl = eBinding.element("wsdl");
				binding.setWsdlRef(eWsdl.attributeValue("ref"));

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
		}
	}
}
