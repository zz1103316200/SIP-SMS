package com.flow.model;

import com.flow.model.Port;

public class Edge {
	public Port sourcePort;
	public Port targetPort; 
	public String id;
	

	public String type;
	
	public Edge(String type){
		this.type=type;
	}
	
	public void setSource(Port p) {
		// TODO Auto-generated method stub
		this.sourcePort=p;
	}

	public void setTarget(Port p) {
		// TODO Auto-generated method stub
		this.targetPort=p;
	}
	public Port getSource() {
		// TODO Auto-generated method stub
		return this.sourcePort;
	}
	
	public Port getTarget() {
		// TODO Auto-generated method stub
		return this.targetPort;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
