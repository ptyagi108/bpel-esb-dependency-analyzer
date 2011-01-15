package com.tomecode.soa.protocols.ftp;

/**
 * FTP folder
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/ *
 */
public final class FtpFolder {

	private String name;

	private FtpServer ftpServer;

	public FtpFolder(String name, FtpServer ftpServer) {
		this.name = name;
		this.ftpServer = ftpServer;
	}

	public final String getName() {
		return name;
	}

	public final FtpServer getFtpServer() {
		return ftpServer;
	}

}
