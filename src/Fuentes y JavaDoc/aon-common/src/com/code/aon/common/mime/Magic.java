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
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;

/** 
 * This class is the primary class for jMimeMagic
 * 
 * @author  $Author: ecastellano $
 * @version $Revision: 1.2 $
 */
public class Magic {
	
	private static final Logger log = Logger.getLogger(Magic.class.getName());
	private boolean initialized = false;
	private static MagicParser magicParser = null;

	/**
	 * constructor
	 */
	public Magic() {
		log.fine("Magic: instantiated");
	}

	/** 
	 * create a parser and initialize it
	 * 
	 * @throws MagicParseException 
	 */
	public synchronized void initialize() throws MagicParseException {
		log.fine("Magic: initialize()");
		if (!initialized) {
			log.fine("Magic: initializing");
			magicParser = new MagicParser();
			magicParser.initialize();
		}
	}

	/** 
	 * return the parsed MagicMatch objects that were created from the
	 * magic.xml definitions
	 * 
	 * @return the parsed MagicMatch objects
	 * @throws MagicParseException 
	 */
	public Collection<MagicMatcher> getMatchers() throws MagicParseException {
		log.fine("Magic: getMatchers()");
		initialize();

		return magicParser.getMatchers();
	}
	
	/** 
	 *  
	 * 
	 * @param data 
	 * @return MagicMatch
	 * @throws MagicParseException 
	 * @throws MagicMatchNotFoundException 
	 * @throws MagicException 
	 */
	public MagicMatch getMagicMatch(byte[] data) 
		throws MagicParseException, MagicMatchNotFoundException, MagicException
	{
		log.fine("Magic: getMagicMatch(byte[])");
		initialize();

		Collection<MagicMatcher> matchers = magicParser.getMatchers();
		log.fine("Magic: getMagicMatch(byte[]): have "+matchers.size()+" matchers");
		MagicMatcher matcher = null;
		MagicMatch match = null;
		Iterator<MagicMatcher> i = matchers.iterator();
		while (i.hasNext()) {
			matcher = i.next();

			log.fine("Magic: getMagicMatch(byte[]): trying to match: "+matcher.getMatch().getMimeType());
			try {
				if ((match = matcher.test(data)) != null) {
					log.fine("Magic: getMagicMatch(byte[]): matched "+matcher.getMatch().getMimeType());
					return match;
				}
			} catch (IOException e) {
				log.severe("Magic: getMagicMatch(byte[]): "+e);
				throw new MagicException(e);
			} catch (UnsupportedTypeException e) {
				log.severe("Magic: getMagicMatch(byte[]): "+e);
				throw new MagicException(e);
			}
		}
		throw new MagicMatchNotFoundException();
	}

	/** 
	 *  
	 * 
	 * @param file 
	 * @return MagicMatch 
	 * @throws MagicParseException 
	 * @throws MagicMatchNotFoundException 
	 * @throws MagicException 
	 */
	public MagicMatch getMagicMatch(File file) 
		throws MagicParseException, MagicMatchNotFoundException, MagicException
	{
		log.fine("Magic: getMagicMatch(File)");
		initialize();

		Collection<MagicMatcher> matchers = magicParser.getMatchers();
		log.fine("Magic: getMagicMatch(File): have "+matchers.size()+" matches");
		MagicMatcher matcher = null;
		MagicMatch match = null;
		Iterator<MagicMatcher> i = matchers.iterator();
		while (i.hasNext()) {
			matcher = i.next();

			log.fine("Magic: getMagicMatch(File): trying to match: "+matcher.getMatch().getDescription());
			try {
				if ((match = matcher.test(file)) != null) {
					log.fine("Magic: getMagicMatch(File): matched "+matcher.getMatch().getDescription());
					return match;
				}
			} catch (UnsupportedTypeException e) {
				log.severe("Magic: getMagicMatch(File): "+e);
				throw new MagicException(e);
			} catch (IOException e) {
				log.severe("Magic: getMagicMatch(File): "+e);
				throw new MagicException(e);
			}
		}
		throw new MagicMatchNotFoundException();
	}

	/** 
	 *  
	 * 
	 * @param magic 
	 * @throws MagicParseException 
	 */
	public static void printMagicFile(Magic magic) throws MagicParseException {
		magic.initialize();

		Collection<MagicMatcher> matchers = magic.getMatchers();
		System.out.println("have "+matchers.size()+" matches");

		MagicMatcher matcher = null;
		Iterator<MagicMatcher> i = matchers.iterator();
		while (i.hasNext()) {
			matcher = i.next();
			System.out.println("printing");
			printMagicMatcher(matcher, "");
		}
	}

	/** 
	 * print a magic match
	 * 
	 * @param matcher 
	 * @param spacing 
	 */
	public static void printMagicMatcher(MagicMatcher matcher, String spacing) {
		System.out.println(spacing + "name: "+matcher.getMatch().getDescription());
		System.out.println(spacing + "children: ");
		Collection<MagicMatcher> matchers = matcher.getSubMatchers();
		Iterator<MagicMatcher> i = matchers.iterator();
		while (i.hasNext()) {
			printMagicMatcher(i.next(), spacing+"  ");
		}
	}

	/** 
	 * print a magic match
	 * 
	 * @param match 
	 * @param spacing 
	 */
	public static void printMagicMatch(MagicMatch match, String spacing) {
		System.out.println(spacing+"=============================");
		System.out.println(spacing+"mime type: "+match.getMimeType());
		System.out.println(spacing+"description: "+match.getDescription());
		System.out.println(spacing+"extension: "+match.getExtension());
		System.out.println(spacing+"test: "+new String(match.getTest().array()));
		System.out.println(spacing+"bitmask: "+match.getBitmask());
		System.out.println(spacing+"offset: "+match.getOffset());
		System.out.println(spacing+"length: "+match.getLength());
		System.out.println(spacing+"type: "+match.getType());
		System.out.println(spacing+"comparator: "+match.getComparator());
		System.out.println(spacing+"=============================");
		Collection<MagicMatch> submatches = match.getSubMatches();
		Iterator<MagicMatch> i = submatches.iterator();
		while (i.hasNext()) {
			printMagicMatch(i.next(), spacing+"    ");
		}
	}

	/** 
	 *  
	 * 
	 * @param args 
	 */
	public static void main(String [] args) {
		Magic magic = new Magic();
		try {
			magic.initialize();

			File f = new File(args[0]);
			if (f.exists()) {
				MagicMatch match = magic.getMagicMatch(f);

				System.out.println("filename: "+args[0]);
				printMagicMatch(match, "");

//				Collection submatches = match.getSubMatches();
//				if (match == null) {
//					System.out.println(args[0]+": unknown");
//				} else {
//					System.out.println("=============================");
//					System.out.println("filename: "+args[0]);
//					System.out.println("mime type: "+match.getMimeType());
//					System.out.println("description: "+match.getDescription());
//					System.out.println("extension: "+match.getExtension());
//					System.out.println("test: "+new String(match.getTest().array()));
//					System.out.println("bitmask: "+match.getBitmask());
//					System.out.println("offset: "+match.getOffset());
//					System.out.println("length: "+match.getLength());
//					System.out.println("type: "+match.getType());
//					System.out.println("comparator: "+match.getComparator());
//					System.out.println("=============================");
//
//					Iterator i = submatches.iterator();
//					while (i.hasNext()) {
//						System.out.println("== SUBMATCH =================");
//						MagicMatch m = (MagicMatch)i.next();
//						System.out.println(m.print());
//						System.out.println("=============================");
//					}
//				}

//				FileInputStream fis = new FileInputStream(f);
//				ByteBuffer buffer = ByteBuffer.allocate((int)f.length());
//				byte []buf = new byte[2048];
//				int size = 0;
//				while ((size = fis.read(buf, 0, 2048)) > 0) {
//					buffer.put(buf, 0, size);
//				}
//				byte []tmp = buffer.array();
//				match = parser.getMagicMatch(tmp);
//				if (match == null) {
//					System.out.println(args[0]+": unknown");
//				} else {
//					System.out.println(args[0]+": "+match.getDescription());
//					System.out.println(match.getMimeType());
//				}
			} else {
				System.err.println("file '"+f.getCanonicalPath() +"' not found");
			}
		} catch (MagicMatchNotFoundException e) {
			System.out.println("no match found");
		} catch (Exception e) {
			System.err.println("error: "+e);
			e.printStackTrace(System.err);
		}
	}
}
