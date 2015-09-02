package com.flow.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flow.model.Port;

public class Vertex {
	String id;
    //inputChild,outputChild是为了处理只有一个端口的情况，但是业务组件有多个端口所有用inputChildList,outputChildList；这两种情况同时放到一个对象中站在面向对象的角度是不合理的，只是为了方便
	//public  Port inputChild;
	public List inputChildList =new ArrayList();
	
	public List outputChildList =new ArrayList();
	//public Port outputChild;
	private String type;
	public String compType;
	Map<String,String> value =new HashMap<String,String>();
	
	public Vertex(String type){
		this.type=type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, String> getValue() {
		return value;
	}

	public void setValue(String key,String _value) {
		value.put(key, _value);
	}
	public String getFromName(String key)
	{
	    return value.get(key);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompType() {
		return compType;
	}
	public void setCompType(String compType) {
		this.compType = compType;
	}
	public List getInputChildList() {
		return inputChildList;
	}
	public List getOutputChildList() {
		return outputChildList;
	}
	public void addInputChild(Port port) {
		// TODO Auto-generated method stub
		this.inputChildList.add(port);
	}
	public void addOutputChild(Port port) {
		// TODO Auto-generated method stub
		this.outputChildList.add(port);
	}
	
	
}
