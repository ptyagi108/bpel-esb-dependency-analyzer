package com.tomecode.soa.protocols.http;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.tomecode.soa.dependency.analyzer.icons.ImageFace;
import com.tomecode.soa.dependency.analyzer.icons.ImageFactory;

/**
 * HTTP server
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class HttpServer implements ImageFace {

	private boolean https;

	private String server;

	private int port;
	/**
	 * list of {@link HttpUrl}
	 */
	private final List<HttpUrl> httpUrls;

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
}
