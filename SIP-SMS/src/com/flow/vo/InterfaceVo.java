package com.flow.vo;

public class InterfaceVo {
	String serviceID;
	String interfaceID;
	//String serviceName;
	String subServiceName;
	String interfaceName;
	String nameSpace;
	String inputType;
	String outputType;
	String inputProtocol;
	String outputProtocol;
	//String interfaceDescription;
	//String interfaceCallInfo;
	public String getServiceID() {
		return serviceID;
	}
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}
	
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getOutputType() {
		return outputType;
	}
	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}
	
	public String getInterfaceID() {
		return interfaceID;
	}
	public void setInterfaceID(String interfaceID) {
		this.interfaceID = interfaceID;
	}
	public String getSubServiceName() {
		return subServiceName;
	}
	public void setSubServiceName(String subServiceName) {
		this.subServiceName = subServiceName;
	}
	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	public String getInputProtocol() {
		return inputProtocol;
	}
	public void setInputProtocol(String inputProtocol) {
		this.inputProtocol = inputProtocol;
	}
	public String getOutputProtocol() {
		return outputProtocol;
	}
	public void setOutputProtocol(String outputProtocol) {
		this.outputProtocol = outputProtocol;
	}
}
