package com.code.aon.updater;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Enumeration;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLClassLoader;
import java.net.MalformedURLException;

import java.util.zip.ZipFile;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.jar.Attributes;
import java.util.zip.ZipException;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public class Updater extends Object  {
	
	private static final int buff_size = 1024;
	
	private static List<FileExecutor> def_executors ;

	private URL url;
	
	private List executors;
	
	private File tempFile ;
	private boolean suspend = false ;
	
	
	private static List<FileExecutor> getDefaultExecutors () {
		if ( def_executors == null ) {
			def_executors = new ArrayList<FileExecutor>();
			def_executors.add ( new JarExecutor() );
			def_executors.add ( new ZipExecutor() );
			def_executors.add ( new SystemExecutor() );
		}
		return def_executors;
	}
	
	private DownloadEventSupport downloadEventSupport;
	
	public Updater ( URL url ) {
		super();
		this.url = url;
		this.downloadEventSupport = new DownloadEventSupport();
		this.executors = new ArrayList<FileExecutor>( getDefaultExecutors());
	}
	
	public Updater ( String urlStr ) 
	throws MalformedURLException
	{
		this ( new URL ( urlStr ) );
	}
	
	public int getDownloadSize () throws IOException {
		URLConnection connection = url.openConnection();
		return connection.getContentLength();
	}

	public File setup () 
	throws IOException 
	{
		tempFile = download();
		if ( tempFile != null ) 
			execute ( tempFile );
		return tempFile;
	}
	
	public String getFileName() {
		String fileName = url.getFile();
		int idx = fileName.lastIndexOf ( '/' ) + 1;
		String name =  fileName.substring( idx );
		return name;
	}

	public File getTempFile() {
		return tempFile;
	}
	
	public void suspend () {
		suspend = true;
	}
	
	public File download () 
	throws IOException
	{
		File tempFile = createTempFile();
		OutputStream out = new FileOutputStream ( tempFile );
		InputStream input = url.openStream();
		byte buff [] = new byte [ buff_size ];
		int read = input.read ( buff, 0, buff.length );
		while ( read != -1 && !suspend ) {
			fireDownloadEvent ( read ); 
			out.write ( buff, 0, read );			
			read = input.read ( buff, 0, buff.length );
		}
		out.close();
		input.close();
		if ( !suspend ) {		
			return tempFile;
		}
		tempFile.delete();
		return null;
	}
	
	public void execute ( File file ) 
	throws IOException 
	{
		Iterator iter = executors.iterator();
		while ( iter.hasNext() ) {
			FileExecutor executor = ( FileExecutor ) iter.next();
			if ( executor.acceptFile ( file ) ) {
				executor.exec ( file );
				return;
			}
		}
	}
	
	public File createTempFile () throws IOException {
		String fileName = url.getFile();
		int idx = fileName.lastIndexOf ( '/' ) + 1;
		String name =  fileName.substring( idx );
		int idx2 = name.lastIndexOf ( '.' );
		if ( idx2 != -1 ) {
			String preffix = name.substring ( 0, idx2 );
			String suffix = name.substring ( idx2  ) ;
			return File.createTempFile ( preffix , suffix );
		}
		return File.createTempFile ( name , null );
	}	
	
	public void addDownloadListener ( DownloadListener listener ) {
		downloadEventSupport.addDownloadListener ( listener );		
	}
	
	public void removeDownloadListener ( DownloadListener listener ) {
		downloadEventSupport.removeDownloadListener ( listener );		
	}

	public void fireDownloadEvent ( int download ) {
		DownloadEvent event = new DownloadEvent ( this , download );
		downloadEventSupport.fireDownloadEvent ( event );
	}

	public static interface FileExecutor  {
		public boolean acceptFile ( File file );
		public void exec ( File file ) throws IOException ;
	}
	
	public static class ZipExecutor implements FileExecutor 
	{
		
		public boolean acceptFile ( File file ){
			try {
				new ZipFile( file );
				return true;
			}
			catch ( ZipException ex ) {
				return false;
			}
			catch ( IOException ex ) {
				return false;
			}
			catch ( Exception ex ) {
				return false;
			}
		}
		
		public void exec ( File file ) 
		throws IOException {
			ZipFile zipFile = new ZipFile ( file ) ;
			String baseDir = System.getProperty ( "user.dir", "" );
			unzip ( zipFile , new File ( baseDir ) );
		}
		
		public void unzip ( ZipFile zipFile, File baseDir ) 
		throws IOException 
		{
			Enumeration entries = zipFile.entries();
			while ( entries.hasMoreElements() ) {
				ZipEntry entry = ( ZipEntry ) entries.nextElement();
				if ( entry.isDirectory() ) {
					File dir = new File ( baseDir , entry.getName() ) ;
					dir.mkdirs();
				}
				else {
					unzip ( zipFile, entry, baseDir );					
				}
			}			
		}
		
		public void unzip ( ZipFile zipFile, ZipEntry entry , File baseDir ) 
		throws IOException 
		{
			File file = new File ( baseDir, entry.getName());
			FileOutputStream out = new FileOutputStream ( file );
			InputStream input = zipFile.getInputStream( entry ) ;
			byte buff [] = new byte [ buff_size ]; 
			int read = input.read ( buff , 0, buff.length );
			while ( read != -1 ) {
				out.write ( buff, 0, read );
				read = input.read ( buff , 0, buff.length );
			}
			input.close();
			out.close();
		}
	}

	public static class JarExecutor implements FileExecutor 
	{
		
		public boolean acceptFile ( File file ){
			try {
				JarFile jarFile = new JarFile( file );
				String mainClass = getMainClass( jarFile );
				return ( mainClass != null ) ;
			}
			catch ( ZipException ex ) {
				return false;
			}
			catch ( IOException ex ) {
				return false;
			}
			catch ( Exception ex ) {
				return false;
			}
		}
		
		public void exec ( File file ) 
		throws IOException {
			URLClassLoader loader = new URLClassLoader ( new URL [] { file.toURL() } , 
																									 ClassLoader.getSystemClassLoader() 
																								  );
			String mainClass = getMainClass ( new JarFile ( file ) );
			try {
				Class clazz = loader.loadClass ( mainClass );		
				String args [] = {};
				Method method = clazz.getMethod ( "main" , new Class [] { args.getClass() } );
				method.invoke ( clazz, new Object [] { args } );
			}
			catch ( ClassNotFoundException ex ) {
				throw new IOException ( ex.getMessage() );
			}																					  
			catch ( NoSuchMethodException ex ) {
				throw new IOException ( ex.getMessage() );
			}																					  
			catch ( IllegalAccessException ex ) {
				throw new IOException ( ex.getMessage() );
			}																					  
			catch ( InvocationTargetException ex ) {
				throw new IOException ( ex.getMessage() );
			}																					  
		}
		
		public String getMainClass ( JarFile jarFile ) 
		throws IOException 
		{
			Manifest manifest = jarFile.getManifest();
			Attributes attrs = manifest.getMainAttributes();
			return attrs.getValue ( Attributes.Name.MAIN_CLASS );
		}
	}
	
	public static class SystemExecutor implements FileExecutor 
	{
		
		public boolean acceptFile ( File file ){
				return true;
		}
		
		public void exec ( File file ) 
		throws IOException {
			Runtime.getRuntime().exec ( file.getAbsolutePath() );
		}
	}

	public static void main ( String args [] ) {
		try {
			System.setProperty ( "user.dir", "C:\\Tmp" );
			Updater updater = new Updater ("http://www.directia.net/afadfa/downloads/update_aon_1.jar");
			System.out.println ( updater.getDownloadSize() );
			updater.setup();
		}
		catch ( Exception ex ) {
			ex.printStackTrace();
		}		
	}
	

}