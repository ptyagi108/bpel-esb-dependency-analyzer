package com.tomecode.soa.parser;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Basic parser, for all parsers
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 * 
 */
public abstract class AbstractParser {

	/**
	 * parse file to Xml
	 * 
	 * @param file
	 * @return
	 * @throws ServiceParserException
	 */
	protected final Element parseXml(File file) throws ServiceParserException {
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(file);
		} catch (DocumentException e) {
			throw new ServiceParserException(e);
		} catch (Exception e) {
			throw new ServiceParserException(e);
		}
		return document.getRootElement();
	}

	/**
	 * return file name whitout file extension
	 * 
	 * @param name
	 * @return
	 */
	public final String getNameWithouExtension(String name) {
		int index = name.lastIndexOf(".");
		if (index != -1) {
			return name.substring(0, index);
		}
		return name;
	}
}
