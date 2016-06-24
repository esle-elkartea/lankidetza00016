package com.code.aon.updater;


import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;


public class DownloadEventSupport   {

	private List<DownloadListener> listeners;
	
	public DownloadEventSupport () {
		super ();
		listeners = new ArrayList<DownloadListener>();
	}
	
	public void addDownloadListener ( DownloadListener listener ) {
		if ( !listeners.contains ( listener ) ) {
			listeners.add ( listener );
		}
	}
	
	public void removeDownloadListener ( DownloadListener listener ) {
		if ( listeners.contains ( listener ) ) {
			listeners.remove ( listener );
		}
	}
	
	public void fireDownloadEvent ( DownloadEvent event ) {
		Iterator iter = listeners.iterator ();
		while ( iter.hasNext() ) {
			DownloadListener listener = ( DownloadListener ) iter.next() ;
			listener.downloadHappen ( event );
		}		
	}
	
}