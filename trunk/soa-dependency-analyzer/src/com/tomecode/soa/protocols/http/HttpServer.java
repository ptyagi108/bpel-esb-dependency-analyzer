package com.tomecode.soa.protocols.http;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyGroupView;
import com.tomecode.soa.dependency.analyzer.gui.utils.PropertyViewData;
import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.ora.osb10g.services.Service;
import com.tomecode.soa.protocols.Node;

/**
 * (c) Copyright Tomecode.com, 2010-2011. All rights reserved.
 * 
 * 
 * HTTP server
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
@PropertyGroupView(type = "HTTP Server")
public final class HttpServer implements ImageFace, Node<HttpServer> {

	@PropertyViewData(title = "Https:")
	private boolean https;

	@PropertyViewData(title = "Server:")
	private String server;

	@PropertyViewData(title = "Port:")
	private int port;
	/**
	 * list of {@link HttpUrl}
	 */
	private final List<HttpUrl> httpUrls;

	/**
	 * parent service
	 */
	private Object parentService;

	/**
	 * Constructor
	 * 
	 * @param isHttps
	 * @param server
	 *            server host
	 * @param port
	 *            server port
	 */
	public HttpServer(boolean isHttps, String server, int port) {
		this.httpUrls = new ArrayList<HttpUrl>();
		this.https = isHttps;
		this.server = server;
		this.port = port;
	}

	public final String getServer() {
		return server;
	}

	public final int getPort() {
		return port;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			if (https) {
				return ImageFactory.HTTPS_SERVER_SMALL;
			}
			return ImageFactory.HTTP_SERVER_SMALL;
		}
		if (https) {
			return ImageFactory.HTTPS_SERVER;
		}
		return ImageFactory.HTTP_SERVER;
	}

	@Override
	public final String getToolTip() {
		return "Type: HTTP" + (https ? "S" : "") + "\nName: " + server + (port != -1 ? ("\nPort: " + String.valueOf(port)) : "");
	}

	public final boolean isHttps() {
		return https;
	}

	public final List<HttpUrl> getHttpUrls() {
		return httpUrls;
	}

	public final void addUrl(String url) {
		if (!existUrl(url)) {
			httpUrls.add(new HttpUrl(url, this));
		}
	}

	private final boolean existUrl(String url) {
		for (HttpUrl httpUrl : httpUrls) {
			if (httpUrl.getUrl().equals(url)) {
				return true;
			}
		}
		return false;
	}

	public final String toString() {
		if (port != -1) {
			return server + ":" + port;
		}
		return server;
	}

	/**
	 * @return the parentService
	 */
	public final Object getParentService() {
		return parentService;
	}

	public final void setParentService(Service parentService) {
		this.parentService = parentService;
	}

	@Override
	public Object getParent() {
		return parentService;
	}

	@Override
	public final HttpServer getObj() {
		return this;
	}

	@Override
	public final List<HttpUrl> getChilds() {
		return httpUrls;
	}

}
