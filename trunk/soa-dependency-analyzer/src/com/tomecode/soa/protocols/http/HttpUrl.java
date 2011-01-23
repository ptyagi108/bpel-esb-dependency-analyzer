package com.tomecode.soa.protocols.http;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;
import com.tomecode.soa.protocols.Node;

/**
 * HTTP URL
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class HttpUrl implements ImageFace, Node<HttpUrl> {

	/**
	 * parent {@link HttpServer}
	 */
	private HttpServer parentHttpServer;

	/**
	 * HTTP URL
	 */
	private String url;

	private final List<Node<?>> nodes = new ArrayList<Node<?>>();

	/**
	 * Constructor
	 * 
	 * @param url
	 *            HTTP URL
	 * @param httpServer
	 *            parent server
	 */
	public HttpUrl(String url, HttpServer httpServer) {
		this.parentHttpServer = httpServer;
		this.url = url;
	}

	public final HttpServer getHttpServer() {
		return parentHttpServer;
	}

	public final String getUrl() {
		return url;
	}

	@Override
	public final Image getImage(boolean small) {
		if (small) {
			return ImageFactory.HTTP_URL_SMALL;
		}
		return ImageFactory.HTTP_URL;
	}

	public final String toString() {
		return url;
	}

	@Override
	public final String getToolTip() {
		return "Http URL: " + url;
	}

	@Override
	public final HttpServer getParent() {
		return parentHttpServer;
	}

	@Override
	public final List<?> getChilds() {
		return nodes;
	}

	@Override
	public final HttpUrl getObj() {
		return this;
	}

}
