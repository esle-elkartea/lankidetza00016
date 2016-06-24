package com.code.aon.updater.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author igayarre
 *  
 */
public class XMLReader {
	
	protected SAXParser saxParser;
	
	public XMLReader() {
		init();
	}
	private void init() {
		try {
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			saxParserFactory.setNamespaceAware(true);
			saxParser = saxParserFactory.newSAXParser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public HashMap parse(InputStream is) throws IOException {
		try {
			XMLHandler handler = new XMLHandler();
			saxParser.parse(is, handler);
			return handler.getMap();
		} catch (org.xml.sax.SAXException e) {
			e.printStackTrace();
		}
		return null;
	}

	public HashMap parse(String file) throws IOException {
		InputStream  is = XMLReader.class.getResourceAsStream(file); 
		return this.parse(is);
	}

	private class XMLHandler extends DefaultHandler {

		private HashMap<String, Object> map;
		private List<String> list;
		
		public void startElement(String namespaceUri, String localName,
				String qualifiedName, Attributes attributes)
				throws SAXException {
			if (AON.equals(qualifiedName)){
				map = new HashMap<String, Object>();
			}else if (PROPERTY.equals(qualifiedName)){
				int namePos = attributes.getIndex(PROPERTY_NAME);
				int valuePos = attributes.getIndex(PROPERTY_VALUE);
				map.put(attributes.getValue(namePos),attributes.getValue(valuePos));
			}else if (UPDATE_FILES.equals(qualifiedName)){
				list = new ArrayList<String>();
			}else if (UPDATE_FILE.equals(qualifiedName)){
				int valuePos = attributes.getIndex(UPDATE_FILE_NAME);
				list.add(attributes.getValue(valuePos));
			}
		}
		public void endElement(String namespaceUri, String localName,
				String qualifiedName) throws SAXException {
			if (UPDATE_FILE.equals(qualifiedName)){
				map.put(UPDATE_FILES, list);
			}
		}
		public void characters(char[] chars, int startIndex, int endIndex) {
		}
		public void processingInstruction(String target, String data){
		} 
		public HashMap getMap(){
			return map;
		}
	}
	
	private static String AON = "aon-project";  
	private static String PROPERTY = "property";  
	private static String PROPERTY_NAME = "name";
	private static String PROPERTY_VALUE = "value";
	private static String UPDATE_FILES = "update.database.files";  
	private static String UPDATE_FILE = "update.database.file";
	private static String UPDATE_FILE_NAME = "value";
	
	static public void main(String[] args) {
		try {
			File f = new File("C:/TRABAJO/instalaciones/AonSolutions/instalation/bin/mysql/config.database.xml");
			XMLReader a = new XMLReader();
			InputStream is = new FileInputStream(f);
			HashMap map = a.parse(is);
			Iterator iter = map.keySet().iterator();
			while (iter.hasNext()){
				Object key = iter.next();
				Object value = map.get(key);
				System.out.println(key+"->"+value);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
