package com.code.aon.updater.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.faces.event.ActionEvent;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import com.code.aon.common.ManagerBeanException;
import com.code.aon.ui.menu.jsf.MenuEvent;
import com.code.aon.updater.DownloadEvent;
import com.code.aon.updater.DownloadListener;
import com.code.aon.updater.Updater;
import com.code.aon.updater.config.ConfigProperties;
import com.code.aon.updater.config.XMLReader;

public class UpdaterController implements DownloadListener {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(UpdaterController.class.getName());

	private String CONFIF_DB_FILE = "/config.database.xml";

	private UploadedFile updateFile;

	private URL url;
	
	private String message;

	/*
	 * 0-INIT
	 * 1-DOWNLOADING
	 * 2-INSTALLING
	 * 3-FINISHED
	 * 4-ERROR
	 * 5-NULL URL
	 */
	private int status;
	private double downloadSize;
	private String downloadInfo;
	private Integer bytesDownloaded;

	/**
	 * @return the updateFile
	 */
	public UploadedFile getUpdateFile() {
		return updateFile;
	}

	/**
	 * @param updateFile the updateFile to set
	 */
	public void setUpdateFile(UploadedFile updateFile) {
		this.updateFile = updateFile;
	}

	/**
	 * @return the downloadSize
	 */
	public double getDownloadSize() {
		return downloadSize;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the bytesDownloaded
	 */
	public Integer getBytesDownloaded() {
		return bytesDownloaded;
	}
	
	/**
	 * @return the downloadInfo
	 */
	public String getDownloadInfo() {
		return downloadInfo;
	}

	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}
	
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	public Integer getDownloadReady() {
		try{
			getConfigUpdateURL();
			url.openStream();
			return 1;
		}catch (Exception e) {
		}
		return 0;
	}

	@SuppressWarnings("unused")
	public void urlUpdate(ActionEvent event) throws ManagerBeanException, IOException {
		getConfigUpdateURL();
		update(true);
	}

	public void getConfigUpdateURL() throws IOException{
		HashMap map = getConfigParams();
		url = new URL((String)map.get(ConfigProperties.URL));
	}
	
	public HashMap getConfigParams() throws IOException{
		File f = new File(getConfigFilePath());
		XMLReader a = new XMLReader();
		InputStream is = new FileInputStream(f);
		HashMap map = a.parse(is);
		return map;
	}
	
	public String getConfigFilePath(){
		String path = System.getProperty("user.dir") + File.separator + "mysql";
		path = path.replace('\\', '/');
        String file = path + "/" + CONFIF_DB_FILE;
		return file;
	}
	
	@SuppressWarnings("unused")
	public void fileUpdate(ActionEvent event) throws ManagerBeanException, MalformedURLException, IOException {
		if (updateFile!=null){
			File f = new File(updateFile.getName());
			url = f.toURL();
			update (false);
		}
		this.status = 4;
	}
	
	@SuppressWarnings("unused")
	public void init(ActionEvent event){
		init();
	}
	
	@SuppressWarnings("unused")
	public void init(MenuEvent event){
		init();
	}
	
	public void init(){
		this.bytesDownloaded = new Integer ( 0 );
		this.downloadInfo = "";
		this.downloadSize = 0;
		this.message = null;
		this.status = 0;
	}

	private void update(boolean findNext) throws IOException{
		this.status = 1;
		Thread thread = new SetupThread ( this, findNext );
		thread.start();
	}
	
	public void downloadHappen(DownloadEvent event) {
		int download = event.getDownload();
		download +=  ( this.bytesDownloaded ).intValue();
		this.bytesDownloaded = new Integer ( download );
		if ((this.downloadSize - download)==0){
			this.status = 2; 
		}
	}

	public static class SetupThread extends Thread {

		private UpdaterController uc;
		private boolean findNext;
		
		public SetupThread ( UpdaterController uc, boolean findNext ){
			this.uc = uc;
			this.findNext = findNext;
		}

		public void run () {
			try {
				do {
					uc.status = 1;
					Updater updater = new Updater ( uc.getUrl() );
					uc.downloadInfo = uc.getUrl().toString();
					uc.bytesDownloaded = 0;
					updater.addDownloadListener ( uc );
					try {
						uc.downloadSize = updater.getDownloadSize();
					} catch (IOException e) {
					}
					updater.setup();
					uc.status = 3;
				} while (hasToContinue());
			}catch ( FileNotFoundException ex ) {
				uc.message = ex.getMessage();
				uc.status = 5;
			}catch ( Exception ex ) {
				uc.message = ex.getMessage();
				uc.status = 4;
			}
		}
		
		private boolean hasToContinue(){
			try {
				if (findNext){
					uc.getConfigUpdateURL();
					URL url = uc.getUrl();
					url.openStream();
					return true;
				}
			} catch (Exception e) {
			}
			return false;
		}
		
	}

	public String getVersion(){
		HashMap map;
		try {
			map = getConfigParams();
			return ((String)map.get(ConfigProperties.VERSION));
		} catch (IOException e) {
		}
		return null;
	}

	
}
