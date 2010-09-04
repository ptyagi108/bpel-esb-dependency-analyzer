package com.tomecode.soa.ora.osb10g.services;

import java.io.File;

/**
 * Business service
 * 
 * @author Tomas Frastia
 * 
 */
public final class BusinessService implements Service {

	private String name;

	private File file;

	public BusinessService(File file) {
		this.file = file;
		int index = file.getName().indexOf(".biz");
		if (index != -1) {
			name = file.getName().substring(0, index);
		} else {
			name = file.getName();
		}
	}

	public final File getFile() {
		return file;
	}

	@Override
	public final String getName() {
		return name;
	}

}
