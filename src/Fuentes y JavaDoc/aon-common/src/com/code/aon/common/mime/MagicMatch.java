/*
jMimeMagic(TM) is a Java library for determining the MIME type of files or
streams.
Copyright (C) 2004 David Castro

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

For more information, please email arimus@users.sourceforge.net
*/
package com.code.aon.common.mime;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/** 
 * This class represents a single match test
 * 
 * @author  $Author: ecastellano $
 * @version $Revision: 1.2 $
 */
public class MagicMatch {
	
	private static final Logger log = Logger.getLogger(MagicMatch.class.getName());
	private String mimeType = null;
	private String extension = null;
	private String description = null;
	private ByteBuffer test = null;
	private int	offset = 0;
	private int length = 0;
	// possible types:
	//     byte, short, long, string, date, beshort, belong, bedate, leshort,
	//     lelong, ledate, regex
	private String type = "";
	private long bitmask = 0xFFFFFFFFL;
	private char comparator = '\0';
	private ArrayList<MagicMatch> subMatches = new ArrayList<MagicMatch>(0);
	private Map<String, String> properties;

	/** 
	 * constructor 
	 */
	public MagicMatch() {
		log.fine("MagicMagic: instantiated");
	}

	/** 
	 * print information about this match 
	 * @return String
	 */
	public String print() {
		String string = "\n";
		string += "mime type: "+mimeType+"\n";
		string += "description: "+description+"\n";
		string += "extension: "+extension+"\n";
		string += "offset: "+offset+"\n";
		string += "length: "+length+"\n";
		string += "test: "+new String(test.array())+"\n";
		string += "type: "+type+"\n";
		string += "comparator: "+comparator+"\n";
		string += "bitmask: "+bitmask;
		return string;
	}

	/** 
	 * set the mime type for this magic match 
	 * 
	 * @param value 
	 */
	public void setMimeType(String value) {
		mimeType = value;
	}

	/** 
	 * get the magic match for this magic match 
	 * 
	 * @return the mime type for this magic match
	 */
	public String getMimeType() {
		return mimeType;
	}

	/** 
	 * set the extension for this magic match 
	 * 
	 * @param value 
	 */
	public void setExtension(String value) {
		extension = value;
	}

	/** 
	 * get the extension for this magic match 
	 * 
	 * @return the extension for this magic match
	 */
	public String getExtension() {
		return extension;
	}

	/** 
	 * set the description for this magic match 
	 * 
	 * @param value 
	 */
	public void setDescription(String value) {
		description = value;
	}

	/** 
	 * get the description for this magic match 
	 * 
	 * @return the description for thie magic match
	 */
	public String getDescription() {
		return description;
	}

	/** 
	 * set the test value for thie magic match 
	 * 
	 * @param value 
	 */
	public void setTest(ByteBuffer value) {
		test = value;
	}

	/** 
	 * get the test value for this magic match 
	 * 
	 * @return ByteBuffer
	 */
	public ByteBuffer getTest() {
		return test;
	}

	/** 
	 * set the offset in the stream we are comparing to the test value for this
	 * magic match
	 * 
	 * @param value 
	 */
	public void setOffset(int value) {
		this.offset = value;
	}

	/** 
	 * get the offset in the stream we are comparing to the test value for this
	 * magic match 
	 * 
	 * @return the offset for this magic match
	 */
	public int getOffset() {
		return offset;
	}

	/** 
	 * set the length we are restricting the comparison to for this magic match 
	 * 
	 * @param value 
	 */
	public void setLength(int value) {
		this.length = value;
	}

	/** 
	 * get the length we are restricting the comparison to for this magic match 
	 * 
	 * @return The length we are restricting the comparison to for this magic match
	 */
	public int getLength() {
		return length;
	}

	/** 
	 * set the type of match to perform for this magic match
	 * 
	 * @param value 
	 */
	public void setType(String value) {
		this.type = value;
	}

	/** 
	 * get the type of match for this magic match 
	 * 
	 * @return  The type of match for this magic match.
	 */
	public String getType() {
		return type;
	}

	/** 
	 * set the bitmask that will be applied for this magic match 
	 * 
	 * @param value 
	 */
	public void setBitmask(String value) {
		if (value != null) {
			this.bitmask = Long.decode(value).intValue();
		}
	}

	/** 
	 * get the bitmask that will be applied for this magic match 
	 * 
	 * @return the bitmask for this magic match
	 */
	public long getBitmask() {
		return bitmask;
	}

	/** 
	 * set the comparator for this magic match 
	 * 
	 * @param value 
	 */
	public void setComparator(String value) {
		this.comparator = value.charAt(0);
	}

	/** 
	 * get the comparator for this magic match 
	 * 
	 * @return the comparator for this magic match
	 */
	public char getComparator() {
		return comparator;
	}

	/** 
	 * set the properties for this magic match 
	 * 
	 * @param properties
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	/** 
	 * get the properties for this magic match 
	 * 
	 * @return the properties for this magic match
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/** 
	 * add a submatch to this magic match 
	 * 
	 * @param m a magic match
	 */
	public void addSubMatch(MagicMatch m) {
		log.fine("adding submatch '"+m.getDescription()+"' to '"+getDescription()+"'");
		subMatches.add(m);
	}

	/** 
	 * set all submatches
	 * 
	 * @param a a collection of submatches
	 */
	public void setSubMatches(Collection<MagicMatch> a) {
		log.fine("setting submatches for '"+getDescription()+"'");
		subMatches.clear();
		subMatches.addAll(a);
	}

	/** 
	 * get all submatches for this magic match 
	 * 
	 * @return a collection of submatches
	 */
	public Collection<MagicMatch> getSubMatches() {
		return subMatches;
	}

	/** 
	 * determine if this match or any submatches has the description 
	 * 
	 * @param desc 
	 * @return whether or not the description matches
	 */
	public boolean descriptionMatches(String desc) {
		if ((description != null) && description.equals(desc)) {
			return true;
		}
		Collection<MagicMatch> submatches = getSubMatches();
		Iterator<MagicMatch> i = submatches.iterator();
		MagicMatch m = null;
		while (i.hasNext()) {
			m = i.next();
			if (m.descriptionMatches(desc)) {
				return true;
			}
		}
		return false;
	}

	/** 
	 * determine if this match or any submatches has the description 
	 * 
	 * @param desc 
	 * @return whether or not the description matches
	 */
	public boolean mimeTypeMatches(String desc) {
		if ((mimeType != null) && mimeType.equals(desc)) {
			return true;
		}
		Collection<MagicMatch> submatches = getSubMatches();
		Iterator<MagicMatch> i = submatches.iterator();
		MagicMatch m = null;
		while (i.hasNext()) {
			m = i.next();
			if (m.mimeTypeMatches(desc)) {
				return true;
			}
		}
		return false;
	}
}
