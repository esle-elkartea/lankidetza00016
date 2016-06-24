package com.code.aon.updater;

import java.util.EventObject;

public class DownloadEvent extends EventObject  {

	private int download;

	public DownloadEvent ( Object source, int download ) {
		super ( source );
		this.download = download ;
	}
	
	public int getDownload (){
		return download;
	}
}