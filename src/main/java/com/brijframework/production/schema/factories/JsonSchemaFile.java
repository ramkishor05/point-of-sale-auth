package com.brijframework.production.schema.factories;

import java.util.List;

public class JsonSchemaFile {
	public String id;
	public int order;
	public List<JsonSchemaObject> objects;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public List<JsonSchemaObject> getObjects() {
		return objects;
	}
	public void setObjects(List<JsonSchemaObject> objects) {
		this.objects = objects;
	}
	
}
