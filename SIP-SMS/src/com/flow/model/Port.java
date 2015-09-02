package com.flow.model;

import java.util.ArrayList;
import java.util.List;

import com.flow.model.Edge;
import com.flow.model.Vertex;

public class Port {
	public String id;
	
	public String interName;
	
	public Vertex parent;
	public String type;
	public List edgeList=new ArrayList();
	
	public Port(String type){
		this.type=type;
	}
	
	public void setParent(Vertex parentModel) {
		// TODO Auto-generated method stub
		this.parent=parentModel;
	}
	public Vertex getParent() {
		// TODO Auto-generated method stub
		return this.parent;
	}
	
	public List getEdgeList(){
		return this.edgeList;
		
	}
	public void addEdge(Edge edge) {
		// TODO Auto-generated method stub
		this.edgeList.add(edge);
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getInterName() {
		return interName;
	}

	public void setInterName(String interName) {
		this.interName = interName;
	}
	
	public void setEdgeList(List edgeList) {
		this.edgeList = edgeList;
	}
}
