package com.tomecode.soa.dependency.analyzer.view.graph;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.GuiUtils;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig;
import com.tomecode.soa.ora.osb10g.services.config.EndpointConfig.ProviderProtocol;
import com.tomecode.soa.ora.osb10g.services.config.EndpointDsp;
import com.tomecode.soa.ora.osb10g.services.config.EndpointEJB;
import com.tomecode.soa.ora.osb10g.services.config.EndpointFile;
import com.tomecode.soa.ora.osb10g.services.config.EndpointHttp;
import com.tomecode.soa.ora.osb10g.services.config.EndpointJms;
import com.tomecode.soa.ora.osb10g.services.config.EndpointUNKNOWN;
import com.tomecode.soa.ora.osb10g.services.config.ProviderSpecificJms;
import com.tomecode.soa.ora.suite10g.esb.EsbSvc;
import com.tomecode.soa.ora.suite10g.esb.Ora10gEsbProject;
import com.tomecode.soa.ora.suite10g.project.BpelEsbDependency;
import com.tomecode.soa.ora.suite10g.project.PartnerLinkBinding;
import com.tomecode.soa.project.Project;
import com.tomecode.soa.project.ProjectType;
import com.tomecode.soa.project.UnknownProject;
import com.tomecode.soa.protocols.ejb.EjbMethod;
import com.tomecode.soa.protocols.http.HttpServer;
import com.tomecode.soa.protocols.jms.JMSConnectionFactory;
import com.tomecode.soa.protocols.jms.JMSQueue;
import com.tomecode.soa.protocols.jms.JMSServer;
import com.tomecode.soa.services.BpelProcess;
import com.tomecode.soa.workspace.MultiWorkspace;
import com.tomecode.soa.workspace.Workspace;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * tool tip factory - helper class
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
final class ToolTipFactory {

	private ToolTipFactory() {

	}

	/**
	 * create tool tip for {@link BpelProcess}
	 * 
	 * @param process
	 * @return
	 */
	// TODO: detail information about bpel process
	static final IFigure createToolTip(BpelProcess process) {
		IFigure tooltip = createDefaultToolTip();
		tooltip.add(GuiUtils.createLabel2dBold("Name:"));
		tooltip.add(createLabel(process.getName(), process.getImage(true)));
		tooltip.add(GuiUtils.createLabel2dBold("Path:"));
		tooltip.add(new org.eclipse.draw2d.Label(process.getFile() != null ? process.getFile().toString() : ""));
		return tooltip;
	}

	/**
	 * create tool tip for {@link Service}
	 * 
	 * @param service
	 * @return
	 */
	static final IFigure createToolTip(Service service) {
		IFigure tooltip = createDefaultToolTip();
		tooltip.add(GuiUtils.createLabel2dBold("Type: "));
		tooltip.add(new org.eclipse.draw2d.Label(service.getType().toString()));
		tooltip.add(GuiUtils.createLabel2dBold("Name: "));
		tooltip.add(createLabel(service.getName(), service.getImage()));

		if (service.getDescription() != null && service.getDescription().length() != 0) {
			tooltip.add(GuiUtils.createLabel2dBold("Description: "));
			tooltip.add(new org.eclipse.draw2d.Label(service.getDescription()));
		}
		tooltip.add(GuiUtils.createLabel2dBold("Path: "));
		tooltip.add(new org.eclipse.draw2d.Label(service.getFile() != null ? service.getFile().toString() : ""));
		createEndpointConfingToTooltip(service.getEndpointConfig(), tooltip);

		return tooltip;
	}

	/**
	 * add endpoint config for tooltip
	 * 
	 * @param endpointConfig
	 * @param tooltip
	 */
	private static final void createEndpointConfingToTooltip(EndpointConfig endpointConfig, IFigure tooltip) {
		if (endpointConfig != null) {
			tooltip.add(GuiUtils.createLabel2dBold("Endpoint Type: "));
			if (endpointConfig.getProtocol() == ProviderProtocol.UNKNOWN) {
				tooltip.add(new org.eclipse.draw2d.Label(((EndpointUNKNOWN) endpointConfig).getProviderId()));
			} else {
				tooltip.add(new org.eclipse.draw2d.Label(endpointConfig.getProtocol().toString()));
			}

			createUrisLables(endpointConfig.getUris(), tooltip);
			fillProviderSpecific(endpointConfig, tooltip);
		}
	}

	private static final void createUrisLables(List<String> uris, IFigure tooltip) {
		if (!uris.isEmpty()) {
			tooltip.add(GuiUtils.createLabel2dBold("URIs: "));
			Iterator<String> i = uris.iterator();
			while (i.hasNext()) {
				tooltip.add(new org.eclipse.draw2d.Label(i.next()));
				if (i.hasNext()) {
					tooltip.add(new org.eclipse.draw2d.Label(""));
				}
			}

		}
	}

	/**
	 * add provider specific from {@link EndpointConfig}
	 * 
	 * @param endpointConfig
	 * @param tooltip
	 */
	private static final void fillProviderSpecific(EndpointConfig endpointConfig, IFigure tooltip) {
		if (endpointConfig.getProtocol() == ProviderProtocol.JMS) {
			EndpointJms endpointJms = (EndpointJms) endpointConfig;
			ProviderSpecificJms jms = endpointJms.getProviderSpecificJms();
			if (jms.getResponseURI() != null && jms.getResponseURI().trim().length() != 0) {
				tooltip.add(GuiUtils.createLabel2dBold("Response URI: "));
				tooltip.add(new org.eclipse.draw2d.Label(jms.getResponseURI()));
			}
		} else if (endpointConfig.getProtocol() == ProviderProtocol.HTTP) {
			EndpointHttp endpointHttp = (EndpointHttp) endpointConfig;
			if (endpointHttp.getProviderSpecificHttp().getRequestMethod() != null) {
				tooltip.add(GuiUtils.createLabel2dBold("Request Method: "));
				tooltip.add(new org.eclipse.draw2d.Label(endpointHttp.getProviderSpecificHttp().getRequestMethod()));
			}
		} else if (endpointConfig.getProtocol() == ProviderProtocol.DSP) {
			EndpointDsp endpointDsp = (EndpointDsp) endpointConfig;
			tooltip.add(GuiUtils.createLabel2dBold("Request Response: "));
			tooltip.add(new org.eclipse.draw2d.Label(String.valueOf(endpointDsp.getProviderSpecificDsp().isRequestResponse())));
		} else if (endpointConfig.getProtocol() == ProviderProtocol.EJB) {
			EndpointEJB endpointEJB = (EndpointEJB) endpointConfig;
			// if (endpointEJB.getProviderSpecificEJB().getClientJar() != null)
			// {
			// tooltip.add(GuiUtils.createLabel2dBold("Client Jar: "));
			// tooltip.add(new
			// org.eclipse.draw2d.Label(endpointEJB.getProviderSpecificEJB().getClientJar()));
			// }

		}
	}

	/**
	 * create tool tip for {@link Project}
	 * 
	 * @param project
	 * @return
	 */
	static final IFigure createToolTip(Project project) {
		IFigure tooltip = createDefaultToolTip();
		tooltip.add(GuiUtils.createLabel2dBold("Name: "));
		tooltip.add(createLabel(project.getName(), project.getImage(true)));
		if (project.getType() == ProjectType.UNKNOWN) {
			UnknownProject unknownProject = (UnknownProject) project;
			tooltip.add(GuiUtils.createLabel2dBold("Type: "));
			tooltip.add(new org.eclipse.draw2d.Label(unknownProject.getTypeText()));
			tooltip.add(GuiUtils.createLabel2dBold("WSDL Location:"));
			tooltip.add(new org.eclipse.draw2d.Label(unknownProject.getPartnerLinkBinding().getWsdlLocation()));
		} else {
			tooltip.add(GuiUtils.createLabel2dBold("Type: "));
			tooltip.add(new org.eclipse.draw2d.Label(project.getType().getTitle()));
			tooltip.add(GuiUtils.createLabel2dBold("Path: "));
			tooltip.add(new org.eclipse.draw2d.Label(project.getFile() != null ? project.getFile().toString() : ""));
		}

		return tooltip;
	}

	/**
	 * create tool tip for {@link Workspace}
	 * 
	 * @param workspace
	 * @return
	 */
	static final IFigure createToolTip(Workspace workspace) {
		IFigure tooltip = createDefaultToolTip();
		tooltip.add(GuiUtils.createLabel2dBold("Name: "));
		tooltip.add(createLabel(workspace.getName(), workspace.getImage(true)));
		tooltip.add(GuiUtils.createLabel2dBold("Type:"));
		tooltip.add(new org.eclipse.draw2d.Label("Workspace " + workspace.getType().toString()));
		tooltip.add(GuiUtils.createLabel2dBold("Path:"));
		tooltip.add(new org.eclipse.draw2d.Label(workspace.getFile() != null ? workspace.getFile().toString() : ""));
		return tooltip;
	}

	/**
	 * create tool tip for {@link MultiWorkspace}
	 * 
	 * @param multiWorkspace
	 * @return
	 */
	static final IFigure createToolTip(MultiWorkspace multiWorkspace) {
		IFigure tooltip = createDefaultToolTip();
		tooltip.add(GuiUtils.createLabel2dBold("Name: "));
		tooltip.add(createLabel(multiWorkspace.getName(), multiWorkspace.getImage(true)));
		tooltip.add(GuiUtils.createLabel2dBold("Type: "));
		tooltip.add(new org.eclipse.draw2d.Label("Multi-Workspace " + multiWorkspace.getType().toString()));
		tooltip.add(GuiUtils.createLabel2dBold("Path: "));
		tooltip.add(new org.eclipse.draw2d.Label(multiWorkspace.getPath() != null ? multiWorkspace.getPath().toString() : ""));
		return tooltip;
	}

	/**
	 * create default and empty tool tip
	 * 
	 * @return
	 */
	private static final IFigure createDefaultToolTip() {
		IFigure tooltip = new Figure();
		tooltip.setBorder(new MarginBorder(5, 5, 5, 5));
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.numColumns = 2;
		tooltip.setLayoutManager(gridLayout);
		return tooltip;
	}

	/**
	 * create tool tip for {@link PartnerLinkBinding}
	 * 
	 * @param partnerLinkBinding
	 * @return
	 */
	final static IFigure createToolTip(PartnerLinkBinding partnerLinkBinding) {
		IFigure tooltip = createDefaultToolTip();
		tooltip.add(GuiUtils.createLabel2dBold("Partner Link: "));
		tooltip.add(createLabel(partnerLinkBinding.getName(), partnerLinkBinding.getImage(true)));

		tooltip.add(GuiUtils.createLabel2dBold("WSDL Location: "));
		tooltip.add(new org.eclipse.draw2d.Label(partnerLinkBinding.getWsdlLocation()));

		if (partnerLinkBinding.getDependencyEsbProject() != null) {
			BpelEsbDependency esbDependency = partnerLinkBinding.getDependencyEsbProject();
			tooltip.add(GuiUtils.createLabel2dBold("a reference to the ESB Project: "));
			Ora10gEsbProject esbProject = esbDependency.getEsbProject();
			tooltip.add(createLabel(esbProject.getName(), esbProject.getImage(true)));
			tooltip.add(GuiUtils.createLabel2dBold("Service: "));
			EsbSvc esbSvc = esbDependency.getEsbSvc();
			if (esbSvc != null) {
				tooltip.add(createLabel(esbSvc.getName(), esbSvc.getImage(true)));
			} else {
				tooltip.add(new org.eclipse.draw2d.Label(("")));
			}

		}

		return tooltip;
	}

	/**
	 * create label with image
	 * 
	 * @param name
	 * @param image
	 * @return
	 */
	private static final org.eclipse.draw2d.Label createLabel(String name, Image image) {
		org.eclipse.draw2d.Label label = new org.eclipse.draw2d.Label(image);
		label.setText(name);
		return label;
	}

	/**
	 * create tool tip for {@link EsbSvc}
	 * 
	 * @param esbSvc
	 * @return
	 */
	final static IFigure createToolTip(EsbSvc esbSvc) {
		IFigure tooltip = createDefaultToolTip();

		tooltip.add(GuiUtils.createLabel2dBold("Name: "));
		tooltip.add(createLabel(esbSvc.getName(), esbSvc.getImage(true)));
		tooltip.add(GuiUtils.createLabel2dBold("Type: "));
		tooltip.add(new org.eclipse.draw2d.Label(esbSvc.getTypeDescription()));

		if (esbSvc.getConcreteWSDLURL() != null && esbSvc.getConcreteWSDLURL().trim().length() != 0) {
			tooltip.add(GuiUtils.createLabel2dBold("Concrete WSDL URL: "));
			tooltip.add(new org.eclipse.draw2d.Label(esbSvc.getConcreteWSDLURL()));
		}
		if (esbSvc.getSoapEndpointURI() != null && esbSvc.getSoapEndpointURI().trim().length() != 0) {
			tooltip.add(GuiUtils.createLabel2dBold("SOAP Endpoint URI: "));
			tooltip.add(new org.eclipse.draw2d.Label(esbSvc.getSoapEndpointURI()));
		}
		return tooltip;
	}

	/**
	 * create tooltip for {@link JMSServer}
	 * 
	 * @param server
	 * @return
	 */
	static final IFigure createToolTip(JMSServer server) {
		IFigure tooltip = createDefaultToolTip();
		tooltip.add(GuiUtils.createLabel2dBold("Type: "));
		tooltip.add(createLabel("JMS Server", server.getImage(true)));
		tooltip.add(GuiUtils.createLabel2dBold("Name: "));
		tooltip.add(new org.eclipse.draw2d.Label(server.getName()));
		if (server.getPort() != null) {
			tooltip.add(GuiUtils.createLabel2dBold("Port: "));
			tooltip.add(new org.eclipse.draw2d.Label(server.getPort()));
		}
		return tooltip;
	}

	/**
	 * create tooltip for {@link JMSConnectionFactory}
	 * 
	 * @param connectionFactory
	 * @return
	 */
	static final IFigure createToolTip(JMSConnectionFactory connectionFactory) {
		IFigure tooltip = createDefaultToolTip();
		tooltip.add(GuiUtils.createLabel2dBold("Type: "));
		tooltip.add(createLabel("JMS Connection Factory", connectionFactory.getImage(true)));
		tooltip.add(GuiUtils.createLabel2dBold("Name: "));
		tooltip.add(new org.eclipse.draw2d.Label(connectionFactory.getName()));
		return tooltip;
	}

	/**
	 * create tooltip for {@link JMSQueue}
	 * 
	 * @param queue
	 * @return
	 */
	final static IFigure createToolTip(JMSQueue queue) {
		IFigure tooltip = createDefaultToolTip();
		tooltip.add(GuiUtils.createLabel2dBold("Type: "));
		tooltip.add(createLabel("JMS Queue", queue.getImage(true)));
		tooltip.add(GuiUtils.createLabel2dBold("Name: "));
		tooltip.add(new org.eclipse.draw2d.Label(queue.getName()));
		return tooltip;
	}

	/**
	 * create tooltip for {@link HttpServer}
	 * 
	 * @param httpServer
	 * @return
	 */
	final static IFigure createToolTip(HttpServer httpServer) {
		IFigure tooltip = createDefaultToolTip();
		tooltip.add(GuiUtils.createLabel2dBold("Type: "));
		tooltip.add(createLabel("HTTP Server", httpServer.getImage(true)));
		tooltip.add(GuiUtils.createLabel2dBold("Protocol: "));
		if (httpServer.isHttps()) {
			tooltip.add(new org.eclipse.draw2d.Label("https"));
		} else {
			tooltip.add(new org.eclipse.draw2d.Label("http"));
		}
		tooltip.add(GuiUtils.createLabel2dBold("Name: "));
		tooltip.add(new org.eclipse.draw2d.Label(httpServer.getServer()));
		if (httpServer.getPort() != -1) {
			tooltip.add(GuiUtils.createLabel2dBold("Port: "));
			tooltip.add(new org.eclipse.draw2d.Label(String.valueOf(httpServer.getPort())));
		}
		return tooltip;
	}

	/**
	 * create tooltip for {@link EndpointFile}
	 * 
	 * @param endpointFile
	 * @return
	 */
	final static IFigure createToolTip(EndpointFile endpointFile) {
		IFigure tooltip = createDefaultToolTip();
		tooltip.add(GuiUtils.createLabel2dBold("Type: "));
		tooltip.add(createLabel("File", endpointFile.getImage()));
		tooltip.add(GuiUtils.createLabel2dBold("File: "));
		tooltip.add(new org.eclipse.draw2d.Label(endpointFile.getUris().get(0)));
		return tooltip;
	}

	/**
	 * create default tooltip
	 * 
	 * @param type
	 * @param name
	 * @param image
	 * @return
	 */
	final static IFigure createToolTip(String type, String name, Image image) {
		IFigure tooltip = createDefaultToolTip();
		tooltip.add(GuiUtils.createLabel2dBold("Type: "));
		tooltip.add(createLabel(type, image));
		tooltip.add(GuiUtils.createLabel2dBold("Name: "));
		tooltip.add(new org.eclipse.draw2d.Label(name));
		return tooltip;
	}

	/**
	 * create default tooltip
	 * 
	 * @param type
	 * @param name
	 * @param port
	 * @param image
	 * @return
	 */
	final static IFigure createToolTip(String type, String name, int port, Image image) {
		IFigure tooltip = createDefaultToolTip();
		tooltip.add(GuiUtils.createLabel2dBold("Type: "));
		tooltip.add(createLabel(type, image));
		tooltip.add(GuiUtils.createLabel2dBold("Name: "));
		tooltip.add(new org.eclipse.draw2d.Label(name));
		if (port != -1) {
			tooltip.add(GuiUtils.createLabel2dBold("Port: "));
			tooltip.add(new org.eclipse.draw2d.Label(String.valueOf(port)));
		}
		return tooltip;
	}

	final static IFigure createToolTip(EjbMethod ejbMethod) {
		return null;
	}

	final static IFigure createToolTip(EndpointConfig endpointConfig) {
		IFigure tooltip = createDefaultToolTip();
		tooltip.add(GuiUtils.createLabel2dBold("Type: "));
		tooltip.add(createLabel("Unknown Service", ImageFactory.UNKNOWN_SERVICE));
		createEndpointConfingToTooltip(endpointConfig, tooltip);
		return tooltip;
	}
}
