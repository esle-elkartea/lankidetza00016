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

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/** 
 * This class represents a single match test
 * 
 * @author  $Author: ecastellano $
 * @version $Revision: 1.2 $
 */
public class MagicMatcher {
	
	private static final Logger log = Logger.getLogger(MagicMatcher.class.getName());
	private ArrayList<MagicMatcher> subMatchers = new ArrayList<MagicMatcher>(0);
	private MagicMatch match = null;

	/** 
	 * constructor 
	 */
	public MagicMatcher() {
		log.fine("MagicMatcher: instantiated");
	}

	/**
	 * @param match
	 */
	public void setMatch(MagicMatch match) {
		log.fine("MagicMatcher: setMatch()");
		this.match = match;
	}

	/**
	 * @return MagicMatch
	 */
	public MagicMatch getMatch() {
		log.fine("MagicMatcher: getMatch()");
		return this.match;
	}

	/**
	 * test to see if everything is in order for this match
	 * @return whether or not this match has enough data to be valid
	 */
	public boolean isValid() {
		log.fine("MagicMatcher: isValid()");

		if ((match == null) || (match.getTest() == null)) {
			return false;
		}

		String type = new String(match.getTest().array());
		char comparator = match.getComparator();
		String description = match.getDescription();
		String test = new String(match.getTest().array());
		
		if ((type != null) && !type.equals("") &&
		    (comparator != '\0') && 
			(comparator == '=' || comparator == '!' || 
			 comparator == '>' || comparator == '<') &&
			 (description != null) && !description.equals("") &&
			 (test != null) && !test.equals(""))
		{
			return true;
		}
		return false;
	}

	/** 
	 * add a submatch to this magic match 
	 * 
	 * @param m a magic match
	 */
	public void addSubMatcher(MagicMatcher m) {
		log.fine("MagicMatcher: addSubMatcher()");
		subMatchers.add(m);
	}

	/** 
	 * set all submatches
	 * 
	 * @param a a collection of submatches
	 */
	public void setSubMatchers(Collection<MagicMatcher> a) {
		log.fine("MagicMatcher: setSubMatchers(): for match '"+match.getDescription()+"'");
		subMatchers.clear();
		subMatchers.addAll(a);
	}

	/** 
	 * get all submatches for this magic match 
	 * 
	 * @return a collection of submatches
	 */
	public Collection<MagicMatcher> getSubMatchers() {
		log.fine("MagicMatcher: getSubMatchers()");
		return subMatchers;
	}

	/** 
	 * test to see if this match or any submatches match 
	 * 
	 * @param f the file that should be used to test the match
	 * @return the deepest magic match object that matched
	 * @throws IOException 
	 * @throws UnsupportedTypeException  
	 */
	public MagicMatch test(File f) 
		throws IOException, UnsupportedTypeException 
	{
		log.fine("MagicMatcher: test(File)");

		int offset = match.getOffset();
		String description = match.getDescription();
		String type = match.getType();

		log.fine("MagicMatcher: test(File): testing '"+f.getName()+"' for '"+description+"'");

		log.fine("MagicMatcher: test(File): \n=== BEGIN MATCH INFO ==");
		log.fine(match.print());
		log.fine("MagicMatcher: test(File): \n=== END MATCH INFO ====\n");

		RandomAccessFile file = null;
		file = new RandomAccessFile(f, "r");

		try {
			int length = 0;

			if (type.equals("byte")) {
				length = 1;
			} else if (type.equals("short") || type.equals("leshort") || type.equals("beshort")) {
				length = 4;
			} else if (type.equals("long") || type.equals("lelong") || type.equals("belong")) {
				length = 8;
			} else if (type.equals("string")) {
				length = match.getTest().capacity();
			} else if (type.equals("regex")) {
				length = (int)file.length()-offset;
				if (length < 0) { length = 0; }
			} else {
				throw new UnsupportedTypeException("unsupported test type '"+type+"'");
			}

			// we know this match won't work since there isn't enough data for the test
			if (length > file.length()-offset) {
				return null;
			}

            byte []buf = new byte[length];
			file.seek(offset);

			int bytesRead = 0;
            int size = 0;
			boolean done = false;
            while (!done) {
				size = file.read(buf, 0, length-bytesRead);
				if (size == -1) {
					throw new IOException("reached end of file before all bytes were read");
				}
				bytesRead += size;

				if (bytesRead == length) {
					done = true;
				}
            }

			log.fine("MagicMatcher: test(File): stream size is '"+buf.length+"'");

			MagicMatch match = null;
			MagicMatch submatch = null;
			if (testInternal(buf)) {
				// set the top level match to this one
				match = getMatch();

				log.fine("MagicMatcher: test(File): testing matched '"+description+"'");
				// set the data on this match
				
				if ((subMatchers != null) && (subMatchers.size() > 0)) {
					log.fine("MagicMatcher: test(File): testing "+subMatchers.size()+" submatches for '"+description+"'");
					for (int i=0; i < subMatchers.size(); i++) {
						log.fine("MagicMatcher: test(File): testing submatch "+i);
						MagicMatcher m = subMatchers.get(i);
						if ((submatch = m.test(f)) != null) {
							log.fine("MagicMatcher: test(File): submatch "+i+" matched with '"+submatch.getDescription()+"'");
							match.addSubMatch(submatch);
						} else {
							log.fine("MagicMatcher: test(File): submatch "+i+" doesn't match");
						}
					}
				}
			}
			return match;
        } finally {
			try { file.close(); } catch (Exception fce) {}
		}
	}

	/** 
	 * test to see if this match or any submatches match 
	 * 
	 * @param data the data that should be used to test the match
	 * @return the deepest magic match object that matched
	 * @throws IOException 
	 * @throws UnsupportedTypeException  
	 */
	public MagicMatch test(byte[] data) 
		throws IOException, UnsupportedTypeException 
	{
		log.fine("MagicMatcher: test(byte[])");

		int offset = match.getOffset();
		String description = match.getDescription();
		String type = match.getType();

		log.fine("MagicMatcher: test(byte[]): testing byte[] data for '"+description+"'");

		log.fine("MagicMatcher: test(byte[]): \n=== BEGIN MATCH INFO ==");
		log.fine(match.print());
		log.fine("MagicMatcher: test(byte[]): \n=== END MATCH INFO ====\n");

		int length = 0;

		if (type.equals("byte")) {
			length = 1;
		} else if (type.equals("short") || type.equals("leshort") || type.equals("beshort")) {
			length = 4;
		} else if (type.equals("long") || type.equals("lelong") || type.equals("belong")) {
			length = 8;
		} else if (type.equals("string")) {
			length = match.getTest().capacity();
		} else if (type.equals("regex")) {
			// FIXME - something wrong here, shouldn't have to subtract 1???
			length = data.length - offset-1;
			if (length < 0) { length = 0; }
		} else {
			throw new UnsupportedTypeException("unsupported test type "+type);
		}

		byte []buf = new byte[length];
		log.fine("MagicMatcher: test(byte[]): offset="+offset+",length="+length+",data length=" + data.length);
		if (offset+length < data.length) {
			System.arraycopy(data, offset, buf, 0, length);
			
			log.fine("MagicMatcher: test(byte[]): stream size is '"+buf.length+"'");

			MagicMatch match = null;
			MagicMatch submatch = null;
			if (testInternal(buf)) {
				// set the top level match to this one
				match = getMatch();

				log.fine("MagicMatcher: test(byte[]): testing matched '"+description+"'");
				// set the data on this match
				
				if ((subMatchers != null) && (subMatchers.size() > 0)) {
					log.fine("MagicMatcher: test(byte[]): testing "+subMatchers.size()+" submatches for '"+description+"'");
					for (int i=0; i < subMatchers.size(); i++) {
						log.fine("MagicMatcher: test(byte[]): testing submatch "+i);
						MagicMatcher m = subMatchers.get(i);
						if ((submatch = m.test(data)) != null) {
							log.fine("MagicMatcher: test(byte[]): submatch "+i+" matched with '"+submatch.getDescription()+"'");
							match.addSubMatch(submatch);
						} else {
							log.fine("MagicMatcher: test(byte[]): submatch "+i+" doesn't match");
						}
					}
				}
			}
			return match;
		} 
		return null;
	}

	private boolean testInternal(byte[] data) {
		log.fine("MagicMatcher: testInternal(byte[])");
		
		if (data.length == 0) {
			return false;
		}
		String type = match.getType();
		String test = new String(match.getTest().array());
		String mimeType = match.getMimeType();
		String description  = match.getDescription();

		ByteBuffer buffer = ByteBuffer.allocate(data.length);

		if ((type != null) && (test != null) && (test.length() > 0)) {
			if (type.equals("string")) {
				buffer = buffer.put(data);
				return testString(buffer);
			} else if (type.equals("byte")) {
				buffer = buffer.put(data);
				return testByte(buffer);
			} else if (type.equals("short")) {
				buffer = buffer.put(data);
				return testShort(buffer);
			} else if (type.equals("leshort")) {
				buffer = buffer.put(data);
				buffer.order(ByteOrder.LITTLE_ENDIAN);
				return testShort(buffer);
			} else if (type.equals("beshort")) {
				buffer = buffer.put(data);
				buffer.order(ByteOrder.BIG_ENDIAN);
				return testShort(buffer);
			} else if (type.equals("long")) {
				buffer = buffer.put(data);
				return testLong(buffer);
			} else if (type.equals("lelong")) {
				buffer = buffer.put(data);
				buffer.order(ByteOrder.LITTLE_ENDIAN);
				return testLong(buffer);
			} else if (type.equals("belong")) {
				buffer = buffer.put(data);
				buffer.order(ByteOrder.BIG_ENDIAN);
				return testLong(buffer);
			} else if (type.equals("regex")) {
				return testRegex(new String(data));
//			} else if (type.equals("date")) {
//				return testDate(data, BIG_ENDIAN);
//			} else if (type.equals("ledate")) {
//				return testDate(data, LITTLE_ENDIAN);
//			} else if (type.equals("bedate")) {
//				return testDate(data, BIG_ENDIAN);
			} else {
				log.severe("MagicMatcher: testInternal(byte[]): invalid test type '"+type+"'");
			}
		} else {
			log.severe("MagicMatcher: testInternal(byte[]): type or test is empty for '"+mimeType+" - "+description+"'");
		}

		return false;
	}

	/** 
	 * test the data against the test byte
	 * 
	 * @param data the data we are testing
	 * @return if we have a match
	 */
	private boolean testByte(ByteBuffer data) {
		log.fine("MagicMatcher: testByte()");

		String test = new String(match.getTest().array());
		char comparator = match.getComparator();
		long bitmask = match.getBitmask();

		byte b = data.get(0);
		b = (byte)(b & bitmask);
		log.fine("MagicMatcher: testByte(): decoding '"+test+"' to byte");
		
		int tst = Integer.decode(test).byteValue();
		byte t = (byte)(tst & 0xff);
		log.fine("MagicMatcher: testByte(): applying bitmask '"+bitmask+"' to '"+tst+"', result is '"+t+"'");
		log.fine("MagicMatcher: testByte(): comparing byte '"+b+"' to '"+t+"'");

		switch (comparator) {
			case '=': return t == b;
			case '!': return t != b;
			case '>': return t > b;
			case '<': return t < b;
		}

		return false;
	}

	/** 
	 * test the data against the byte array
	 * 
	 * @param data the data we are testing
	 * @return if we have a match
	 */
	private boolean testString(ByteBuffer data) {
		log.fine("MagicMatcher: testString()");

		ByteBuffer test = match.getTest();
		char comparator = match.getComparator();

		byte []b = data.array();
		byte []t = test.array();
		
		boolean diff = false;
		int i = 0;
		for (i=0; i < t.length; i++) {
			log.fine("testing byte '"+b[i]+"' from '"+new String(data.array())+"' against byte '"+t[i]+"' from '"+new String(test.array())+"'");
			if (t[i] != b[i]) {
				diff = true;
				break;
			}
		}

		switch (comparator) {
			case '=': return !diff;
			case '!': return diff;
			case '>': return t[i] > b[i];
			case '<': return t[i] < b[i];
		}

		return false;
	}

	/** 
	 * test the data against a short
	 * 
	 * @param data the data we are testing
	 * @return if we have a match
	 */
    private boolean testShort(ByteBuffer data) {
		log.fine("MagicMatcher: testShort()");

		short val = 0;
		String test = new String(match.getTest().array());
		char comparator = match.getComparator();
		long bitmask = match.getBitmask();

		val = byteArrayToShort(data);

		// apply bitmask before the comparison
		val = (short)(val & (short)bitmask);

		short tst = 0;
		try {
			tst = Integer.decode(test).shortValue();
		} catch (NumberFormatException e) {
			log.severe("MagicMatcher: testShort(): "+e);
			return false;
			//if (test.length() == 1) {	
			//	tst = new Integer(Character.getNumericValue(test.charAt(0))).shortValue();
			//}
		}

		log.fine("MagicMatcher: testShort(): testing '"+Long.toHexString(val)+"' against '"+Long.toHexString(tst)+"'");

		switch (comparator) {
			case '=':
				return val == tst;
			case '!':
				return val != tst;
			case '>':
				return val > tst;
			case '<':
				return val < tst;
		}

		return false;
    }

	/** 
	 * test the data against a long
	 * 
	 * @param data the data we are testing
	 * @return if we have a match
	 */
    private boolean testLong(ByteBuffer data) {
		log.fine("MagicMatcher: testLong()");

		long val = 0;
		String test = new String(match.getTest().array());
		char comparator = match.getComparator();
		long bitmask = match.getBitmask();

		val = byteArrayToLong(data);

		// apply bitmask before the comparison
		val = val & bitmask;

		long tst = Long.decode(test).longValue();

		log.fine("MagicMatcher: testLong(): testing '"+Long.toHexString(val)+"' against '"+test+"' => '"+Long.toHexString(tst)+"'");

		switch (comparator) {
			case '=':
				return val == tst;
			case '!':
				return val != tst;
			case '>':
				return val > tst;
			case '<':
				return val < tst;
		}

		return false;
    }

	/** 
	 * test the data against a regex
	 * 
	 * @param data the data we are testing
	 * @return if we have a match
	 */
    private boolean testRegex(String text) {
		log.fine("MagicMatcher: testRegex()");

		String test = new String(match.getTest().array());
		char comparator = match.getComparator();

		Pattern pattern = Pattern.compile( test );
		log.fine("MagicMatcher: testRegex(): searching for '"+test+"'");
		if (comparator == '=') {
			if (pattern.matcher(text).matches()) {
				return true;
			} 
			return false;
		} else if (comparator == '!') {
			if (pattern.matcher(text).matches()) {
				return false;
			} 
			return true;
		}
		return false;
	}

    /**
	 * convert a byte array to a short
	 *
     * @param data buffer of byte data
     * @return byte array converted to a short
     */
    private short byteArrayToShort(ByteBuffer data) {
		return data.getShort(0);
    }

    /**
	 * convert a byte array to a long
	 *
     * @param data buffer of byte data
     * @return byte arrays (high and low bytes) converted to a long value
     */
    private long byteArrayToLong(ByteBuffer data) {
		return data.getInt(0);
    }
}
