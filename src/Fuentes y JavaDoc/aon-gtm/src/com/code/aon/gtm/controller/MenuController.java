package com.code.aon.gtm.controller;

import java.util.LinkedList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.code.aon.ui.menu.jsf.MenuManager;
import com.code.aon.ui.util.AonUtil;

public class MenuController {
	
	private static final String MENU_MANAGER_NAME = "menuManager";

	private List<SelectItem> menus;
	
	private String selectedMenu;

	public String getSelectedMenu() {
		return selectedMenu;
	}

	public void setSelectedMenu(String selectedMenu) {
		this.selectedMenu = selectedMenu;
	}

	public List<SelectItem> getMenus() {
		if(menus == null){
			menus = new LinkedList<SelectItem>();
			SelectItem item = new SelectItem("AON_GTA", "aon GTA");
			menus.add(item);
			item = new SelectItem("AON_ACCOUNT", "aon ACCOUNT");
			menus.add(item);
			item = new SelectItem("AON_FINANCE", "aon FINANCE");
			menus.add(item);
		}
		return menus;
	}
	
	public void onMenuChange(ValueChangeEvent event){
		if(event.getNewValue() != null){
			MenuManager manager = (MenuManager)AonUtil.getRegisteredBean(MENU_MANAGER_NAME);
			manager.setCurrentMenu((String) event.getNewValue());
		}
	}
}
