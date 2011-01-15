package com.tomecode.soa.protocols.http;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * HTTP URL
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class HttpUrl implements ImageFace {

	/**
	 * parent {@link HttpServer}
	 */
	private HttpServer parentHttpServer;

	/**
	 * HTTP URL
	 */
	private String url;

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
			return null;
		}
		return ImageFactory.HTTP_URL;
	}

	@Override
	public final String getToolTip() {
		return "Http URL: " + url;
	}

}
