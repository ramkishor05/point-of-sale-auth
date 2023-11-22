package com.brijframwork.authorization.beans;

import java.util.ArrayList;
import java.util.List;

public class UIMenuGroup {

	private long id;
	private String title;
	private String url;
	private String icon;
	private String type;
	
	private List<UIMenuItem> menuItems;
	private Integer order;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getOrder() {
		if(order==null) {
			order=0;
		}
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
	public List<UIMenuItem> getMenuItems() {
		if(menuItems==null) {
			menuItems=new ArrayList<UIMenuItem>();
		}
		return menuItems;
	}

	public void setMenuItems(List<UIMenuItem> menuItems) {
		this.menuItems = menuItems;
	}
}
