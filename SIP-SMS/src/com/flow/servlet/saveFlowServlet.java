package com.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.flow.dao.BusinessComponentDao;
import com.flow.dao.Comp_interfaceDao;
import com.flow.dao.FlowTableDao;
import com.flow.dao.InterfaceDao;
import com.flow.dao.ProtocolDao;
import com.flow.dao.ProtocolTransDao;
import com.flow.dao.SubServiceDao;
import com.flow.dao.TransformerDao;
import com.flow.model.Edge;
import com.flow.model.Port;
import com.flow.model.Vertex;
import com.flow.tools.FlowTool;
import com.flow.vo.BusinessComponentVo;
import com.flow.vo.Comp_interfaceVo;
import com.flow.vo.InterfaceVo;
import com.flow.vo.ProtocolTransVo;
import com.flow.vo.ProtocolVo;
import com.flow.vo.SubServiceVo;
import com.flow.vo.TransformerVo;


public class saveFlowServlet extends HttpServlet {
	private boolean flag = true;
	//����xml������str����
	private String flowname;
	//���ڷ�������ʱ����λ��
	int next_x =150;
	int next_y =100;
	public String xmlStr="";
	//static int id = 1;
	JSONArray jsonProperArray = new JSONArray();
	//xmlͷ��ʽ
	private	String m_xml_declare="<?xml version="+'"'+"1.0"+'"'+" encoding="+'"'+"UTF-8"+'"'+"?>";
	private	String m_xml_namespace="<mule xmlns="+'"'+"http://www.mulesoft.org/schema/mule/core"+'"'+" xmlns:http="+'"'+"http://www.mulesoft.org/schema/mule/http"+'"'+"\n"+" xmlns:ftp="+'"'
		+"http://www.mulesoft.org/schema/mule/ftp"+'"'+" xmlns:file="+'"'+"http://www.mulesoft.org/schema/mule/file"+'"'+"\n"+" xmlns:doc="+'"'+"http://www.mulesoft.org/schema/mule/documentation"
				+'"'+" xmlns:spring="+'"'+"http://www.springframework.org/schema/beans" +'"'+"\n"+" xmlns:core="+'"'+"http://www.mulesoft.org/schema/mule/core"+'"'+" xmlns:ajax="+'"'
		+"http://www.mulesoft.org/schema/mule/ajax"+'"'+"\n"+" xmlns:cxf="+'"'+"http://www.mulesoft.org/schema/mule/cxf"+'"'+" xmlns:xsi="+'"'+"http://www.w3.org/2001/XMLSchema-instance"
		+'"'+" version="+'"'+"CE-3.2.1" +'"'+"\n"+" xsi:schemaLocation="+'"'+"http://www.mulesoft.org/schema/mule/http http://www.mulesoft.org/schema/mule/http/current/mule-http.xsd "+"\n"
		+"http://www.mulesoft.org/schema/mule/ftp http://www.mulesoft.org/schema/mule/ftp/current/mule-ftp.xsd "+"\n"
		+"http://www.mulesoft.org/schema/mule/file http://www.mulesoft.org/schema/mule/file/current/mule-file.xsd "+"\n"
		+"http://www.springframework.org/schema/beans  http://www.springframework.org/schema/beans/spring-beans-3.0.xsd "+"\n"
		+"http://www.mulesoft.org/schema/mule/core http://www.mulesoft.org/schema/mule/core/current/mule.xsd "+"\n"
		+"http://www.mulesoft.org/schema/mule/ajax http://www.mulesoft.org/schema/mule/ajax/current/mule-ajax.xsd "+"\n"
		+"http://www.mulesoft.org/schema/mule/cxf http://www.mulesoft.org/schema/mule/cxf/current/mule-cxf.xsd "+'"'+">";
	private	String m_xml_flow_1="\t"+"<flow name="+'"';
	private	String m_xml_flow_2 = '"'+" doc:name="+'"';
	private	String m_xml_flow_3 = '"'+">";
	private	String m_xml_end="\n	 </flow>\n</mule>";
	/**
	 * Constructor of the object.
	 */
	public saveFlowServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("application/json;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		HashMap vertexMap = new HashMap<String,Vertex>();
		HashMap edgeMap = new HashMap<String,Edge>();
		HashMap portMap = new HashMap<String,Port>();
		Vertex start = new Vertex("Start");	
		String submitJson = request.getParameter("submitJson");
		String flowName = request.getParameter("flowName");
		this.flowname = flowName;
		String flowDescription = request.getParameter("flowDescription");
		System.out.println("submitJson:"+submitJson+"-----flowName:"+flowName);
		FlowTool ft = new FlowTool();
		ft.getMap(submitJson, start, vertexMap, portMap, edgeMap);	
		//System.out.println("vertexMap:"+vertexMap.size()+"edgeMap:"+edgeMap.size()+"portMap:"+portMap.size());
		//�õ�xml��ʽ���ַ���
		//xmlStr=m_xml_declare+"\n\n"+m_xml_namespace+"\n"+m_xml_flow;
		//�Ȱ�Start�ŵ�JSONArray��
		getVertexJson(start,"Start.png");
		getInputJson(start);
		getOutputJson(start);
		
		xmlStr = xmlStr + m_xml_flow_1 + flowname + m_xml_flow_2 + flowname + m_xml_flow_3;
		getXmlStr(start);
		//xmlStr+=m_xml_end;
		xmlStr += "</flow>";
		addEdgeToJson(submitJson);
		System.out.println("xml:"+xmlStr);
		//��xml���������ݿ���
		FlowTableDao fd = new FlowTableDao();
		fd.add(new Object[]{flowName,flowDescription,xmlStr, jsonProperArray.toString() ,"unregisted"});
		xmlStr="";
		//���ߵĹ�ϵҲ�ӵ�jsonProperArray��ͨ�������submitJson����ɡ�
		next_x =150;
		next_y =100;
		System.out.println("next_x:"+next_x);
		jsonProperArray = new JSONArray();
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

	public void getXmlStr(Vertex model){
		
		Port output = getOutput(model);
		if(output.getEdgeList().size()!=0){
			
			
			//����Choice�Ͳ�����Choice�ķ�֧
			if(model.getType().equals("Choice")){
				getVertexJson(model,"Choice.png");
				getInputJson(model);
				getOutputJson(model);
				xmlStr += "<choice doc:name="+'"'+"Choice"+'"'+">";
				Vertex endAll = recursiveAllVertex(model);
				next_x += 150;
				next_y = 100;
				getVertexJson(endAll,"End.png");
				getInputJson(endAll);
				getOutputJson(endAll);
				xmlStr += "</choice>";
				getXmlStr(endAll);
			}else{
				//�õ�edge��target�ڵ� Ȼ���ڵõ����ڵ�
			
				if(output.getEdgeList()==null||output.getEdgeList().size()==0){
					return;
				}
				Edge edge=(Edge)output.getEdgeList().get(0);
				
			
				
				Vertex nextVertex=edge.getTarget().getParent();
				if(nextVertex.getType().equals("End")){
					//��end�ŵ�json����
					getVertexJson(nextVertex,"End.png");
					getInputJson(nextVertex);
					getOutputJson(nextVertex);
					return;
				}
				//�ֱ���ҵ�������Э������Ĳ�ͬ���
				if(nextVertex.getCompType().equals("1")){
					getControlString(nextVertex);
				}else if(nextVertex.getCompType().equals("2")){
					getBusinessString(nextVertex);
				}else if(nextVertex.getCompType().equals("3")){
					getProtoString(nextVertex);
				}
				//str��������װ				
				getXmlStr(nextVertex);
				
				
			}
		}
		
		
	}
	//��������Ǵ�Э������ҵ����Դ�������ֵ��Ӧ�ŵ�json������
	public JSONObject getPropertiesJSON(Vertex vertex){
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("type", vertex.getType());
		ProtocolDao pd = new ProtocolDao();
		ProtocolVo pv = (ProtocolVo)pd.selectByName(vertex.getType());
		String propertiesStr = pv.getProtocolProperties();
		JSONArray properJsonArray = JSONArray.fromObject(propertiesStr);
		for(int j=0;j<properJsonArray.size();j++){
			JSONObject propertiesJsonObject = JSONObject.fromObject(properJsonArray.getString(j));
			jsonObj.put(propertiesJsonObject.getString("name"), vertex.getFromName(propertiesJsonObject.getString("name")));
			//vertex.setValue(propertiesJsonObject.getString("name"), jsonObject.getString("property"+(j+1)));
		}
		
		return jsonObj;
		
	}
	
	//��������ǵõ����������xml��
	public void getControlString(Vertex vertex){
		if(vertex.getType().equals("Splitter")){
			//��Splitter�ŵ�json����
			JSONObject json = new JSONObject(); 
			json.put("id", vertex.getId());
			
			json.put("type", "vertex");
			json.put("realType", vertex.getType());
			json.put("compType", vertex.getCompType());
			json.put("_x", next_x);
			json.put("_y", next_y);
			json.put("expression",vertex.getFromName("expression"));
			json.put("pictureURl", "Splitter.png");
			jsonProperArray.add(json);
			next_x += 150;
			getInputJson(vertex);
			getOutputJson(vertex);
			xmlStr +="<splitter expression="+'"'+vertex.getFromName("expression")+'"'+" doc:name="+'"'+"Splitter"+'"'+"/>";
		}else if(vertex.getType().equals("Resequence")){
			//��Resequence�ŵ�json����
			JSONObject json = new JSONObject(); 
			json.put("id", vertex.getId());
			
			json.put("type", "vertex");
			json.put("realType", vertex.getType());
			json.put("compType", vertex.getCompType());
			//json.put("expression",vertex.getFromName("expression"));
			json.put("_x", next_x);
			json.put("_y", next_y);
			json.put("pictureURl", "ALL.png");
			jsonProperArray.add(json);
			next_x += 150;
			getInputJson(vertex);
			getOutputJson(vertex);
			xmlStr +="<resequencer failOnTimeout="+'"'+"true"+'"'+" doc:name="+'"'+"Resequencer"+'"'+"/>";
		}else if(vertex.getType().equals("Aggregator")){
			//��Splitter�ŵ�json����
			JSONObject json = new JSONObject(); 
			json.put("id", vertex.getId());
			
			json.put("type", "vertex");
			json.put("realType", vertex.getType());
			json.put("compType", vertex.getCompType());
			json.put("storePrefix",vertex.getFromName("storePrefix"));
			json.put("timeout",vertex.getFromName("timeout"));
			json.put("_x", next_x);
			json.put("_y", next_y);
			json.put("pictureURl", "CollectionAggregator.png");
			jsonProperArray.add(json);
			next_x += 150;
			getInputJson(vertex);
			getOutputJson(vertex);
			xmlStr +="<collection-aggregator timeout="+'"'+vertex.getFromName("timeout")+'"'+" storePrefix="+'"'+vertex.getFromName("storePrefix")+'"'+" doc:name="+'"'+"Collection Aggregator"+'"'+"/>";
		}
	}
	
	//��������ǵõ�ҵ�������xml��
	public void getBusinessString(Vertex nextVertex){
//		String compName = nextVertex.getType();
//		String interName = nextVertex.getFromName("interfaceName");
//		//��ͨ�����������������ҵ�������
		SubServiceDao bd = new SubServiceDao(); 
		SubServiceVo bcV = (SubServiceVo)bd.selectByServiceName(nextVertex.getType());
//		String serName = bcV.getServiceName();
//		System.out.println("sername:"+serName+"----interName:"+interName);
//		//��ͨ���������ӽӿڵ��ñ����ҵ����õĴ�
//		Comp_interfaceDao ciD = new Comp_interfaceDao();
//		Comp_interfaceVo  ciV = (Comp_interfaceVo)ciD.selectByInter(serName, interName);
//		xmlStr += ciV.getInterfaceCallInfo();
//		JSONObject jsonObj = new JSONObject();	
//		jsonObj.put("id", id);
//		id++;
//		jsonObj.put("type", nextVertex.getType());
//		jsonObj.put("compType", nextVertex.getCompType());
//		jsonObj.put("name", nextVertex.getFromName("name"));
//		jsonObj.put("interfaceName", nextVertex.getFromName("interfaceName"));
//		jsonObj.put("pictureURl", bcV.getPictureURL());
//		jsonProperArray.add(jsonObj);
		getBusinessVertexJson(nextVertex,bcV.getUrl());
		List<Port> inlp = new ArrayList();
		inlp = nextVertex.getInputChildList();
		for(int i = 0;i<inlp.size();i++){
			getPortJson(inlp.get(i),"Input");
		}
		List<Port> outlp = new ArrayList();
		outlp = nextVertex.getOutputChildList();
		for(int i = 0;i<outlp.size();i++){
			getPortJson(outlp.get(i),"Output");
		}
		Port port = getOutput(nextVertex);
		Comp_interfaceDao ciD = new Comp_interfaceDao();
		Comp_interfaceVo  ciV = (Comp_interfaceVo)ciD.selectByInter(nextVertex.getType(), port.getInterName());
		xmlStr += ciV.getInterfaceCallInfo();
		//Port nextPort = (Port)nextVertex.getOutputChildList().get(0);
		//����ҵ�����֮��Ҫ�õ�Э��ת���͸�ʽת���Ĵ�
		Edge nextEdge = (Edge)port.getEdgeList().get(0);
		if(nextEdge.getTarget().getParent().getCompType().equals("2")){
			Vertex nVertex = nextEdge.getTarget().getParent();
			Port nPort = getOutput(nVertex);
			xmlStr += getProtocolTran(nextVertex,port,nVertex,nPort);
		}
	}
	
	//��������ǵõ�Э�������xml��
	public void getProtoString(Vertex nextVertex){
		//System.out.println("�����ˣ�");
		String type = nextVertex.getType().toLowerCase();
		if(flag == true){
			xmlStr += "<" + type + ":inbound-endpoint";
			flag = false;
		}else{
			xmlStr += "<" + type + ":outbound-endpoint";
		}
		//�õ����������
		JSONObject jsonObj = new JSONObject();	
		jsonObj.put("id", nextVertex.getId());
		//id++;
		jsonObj.put("type", "vertex");
		jsonObj.put("realType", nextVertex.getType());
		jsonObj.put("compType", "3");
		jsonObj.put("_x", next_x);
		jsonObj.put("_y", next_y);
		ProtocolDao pd = new ProtocolDao();
		ProtocolVo pv = (ProtocolVo)pd.selectByName(nextVertex.getType());
		jsonObj.put("pictureURl", pv.getProtocolURL());
		//�����ݿ��еõ���������Դ�
		String propertiesStr = pv.getProtocolProperties();
		JSONArray properJsonArray = JSONArray.fromObject(propertiesStr);
		//�����Դ�ͨ��json����ķ�ʽ����
		for(int j=0;j<properJsonArray.size();j++){
			JSONObject propertiesJsonObject = JSONObject.fromObject(properJsonArray.getString(j));
			String propertyType = propertiesJsonObject.getString("name");
			String propertyValue = nextVertex.getFromName(propertyType);

			//�����Ա��json����ʽ���Ա�������ݿ���
			jsonObj.put("property"+(j+1), propertyValue);
			//��װ��Ҫ��xml��
			if(propertyType.toLowerCase().contains("name")){
				xmlStr+=" doc:name="+'"'+propertyValue+'"';
				
			}else{
				xmlStr+=" "+propertyType+"="+'"'+propertyValue+'"';
			}
			
			//vertex.setValue(propertiesJsonObject.getString("name"), jsonObject.getString("property"+(j+1)));
		}
		xmlStr+="/>";
		for(int j=properJsonArray.size();j<10;j++){
			jsonObj.put("property"+(j+1), "");
		}
		next_x += 150;
		jsonProperArray.add(jsonObj);
		getInputJson(nextVertex);
		getOutputJson(nextVertex);
	}
	//�ݹ�Choice�ؼ�
	public Vertex recursiveAllVertex(Vertex choiceModel){
		Port outPort=(Port)choiceModel.getOutputChildList().get(0);
		List edgeList=outPort.getEdgeList();
		Vertex end = null;
		int tag_x = next_x;
		for(int i=0;i<edgeList.size();i++){
			//�ȵõ�all�����з�֧�ߣ��ӱߵõ�Ŀ�Ľڵ�port���ٴ�port�õ���Vertex
			next_y =100*(i+1);
			next_x =tag_x;
			Edge edgeModel=(Edge)edgeList.get(i);
			Port input=edgeModel.getTarget();
			//�ǵ������ж�һ���ǲ���Ϊ��
			
			if(input.getParent()!=null){
				Vertex nextVertex=input.getParent();
				end= forEachAll(nextVertex);
				//System.out.println("end:----------"+end.getType());
			}
			
			
		}
		return end;
		
	}
	
	//����Choice��һ��֧��
	public Vertex forEachAll(Vertex vertex){
		Vertex tempVertex=vertex;
		if(!tempVertex.getType().equals("Option")){
			System.out.println("����ƥ�����");
		}else{
			String option = tempVertex.getFromName("option");
			int tag_option = 0;
			//��option�ŵ�json����
			getOptionJson(vertex,"FirstSuccessful.png");
			getInputJson(vertex);
			getOutputJson(vertex);
			if(!((option==null)||(option.equals("else"))||(option.equals("")))){
				xmlStr += " <when expression="+'"'+tempVertex.getFromName("option")+'"'+">";
				tag_option=0;
			}else{
				xmlStr += "<otherwise>";
				tag_option=1;
			}
			Port output = getOutput(tempVertex);
			Edge edge=(Edge)output.getEdgeList().get(0);
			if(edge.getTarget().getParent()==null||edge.getTarget().getParent().getType().equals("End")){
				return edge.getTarget().getParent();
		    }else{
		    	tempVertex=edge.getTarget().getParent();
		    	//Choice��Ƕ��Choice
				while(!tempVertex.getType().equals("End")){
					if(tempVertex.getType().equals("Choice")){
						getVertexJson(tempVertex,"Choice.png");
						getInputJson(tempVertex);
						getOutputJson(tempVertex);
						Vertex choiceModel=(Vertex)vertex;
						recursiveAllVertex(choiceModel);	
					}
					//����option֮���
					//�ֱ���ҵ�������Э������Ĳ�ͬ���
					if(tempVertex.getCompType().equals("1")){
						getControlString(tempVertex);
					}else if(tempVertex.getCompType().equals("2")){
						getBusinessString(tempVertex);
					}else if(tempVertex.getCompType().equals("3")){
						getProtoString(tempVertex);
					}
					Port theOutputChild = getOutput(tempVertex);
					if( theOutputChild.getEdgeList().size()==0){
				    	break;
				    }
					Edge edge1=(Edge)theOutputChild.getEdgeList().get(0);
				    
				    if(edge1.getTarget().getParent()==null||edge1.getTarget().getParent().getType().equals("End")){
				    	tempVertex=edge1.getTarget().getParent();
				    	break;
				    }else{
				    	tempVertex=edge1.getTarget().getParent();
				    }
				    continue;
				}
		    }
			
			if(tag_option==0){
				xmlStr+=" </when>  ";
			}else{
				xmlStr+=" </otherwise>  ";
			}
			
		}

		
		return tempVertex;
		
	}
	//�õ��ڵ�ĳ���
	public Port getOutput(Vertex vertex){
		
		for(int i=0;i<vertex.getOutputChildList().size();i++){
			Port port = (Port)vertex.getOutputChildList().get(i);
			if(port.getEdgeList().size()!=0) return port;
		}
		return (Port)vertex.getOutputChildList().get(0);
		
	}
	
	//�õ�Vertex�� Json
	public void getVertexJson(Vertex vertex,String url){
		
		//Port port = (Port)vertex.getInputChildList().get(0);
		JSONObject json = new JSONObject(); 
		//System.out.println("id--"+vertex.getId());
		json.put("id", vertex.getId());
		//String parentId = port.getParent().getId();
		//json.put("parentId", parentId);
		json.put("type", "vertex");
		json.put("realType", vertex.getType());
		json.put("compType", vertex.getCompType());
		json.put("pictureURl", url);
		json.put("_x", next_x);
		json.put("_y", next_y);
		next_x += 150;
		jsonProperArray.add(json);
		
	}
	
	//�õ�BusinessVertex�� Json
	public void getBusinessVertexJson(Vertex vertex,String url){
		
		//Port port = (Port)vertex.getInputChildList().get(0);
		
		JSONObject json = new JSONObject(); 
		//System.out.println("id--"+vertex.getId());
		json.put("id", vertex.getId());
		//String parentId = port.getParent().getId();
		//json.put("parentId", parentId);
		json.put("type", "vertex");
		json.put("realType", vertex.getType());
		json.put("portSize", vertex.outputChildList.size());
		json.put("compType", vertex.getCompType());
		json.put("pictureURl", url);
		json.put("_x", next_x);
		json.put("_y", next_y);
		jsonProperArray.add(json);
		next_x += 150;
		
	}
	
	//�õ�Vertex�� Json
	public void getOptionJson(Vertex vertex,String url){
		
		//Port port = (Port)vertex.getInputChildList().get(0);
		JSONObject json = new JSONObject(); 
		//System.out.println("id--"+vertex.getId());
		json.put("id", vertex.getId());
		//String parentId = port.getParent().getId();
		//json.put("parentId", parentId);
		json.put("type", "vertex");
		json.put("realType", vertex.getType());
		json.put("compType", vertex.getCompType());
		json.put("option", vertex.getFromName("option"));
		json.put("_x", next_x);
		json.put("_y", next_y);
		json.put("pictureURl", url);
		jsonProperArray.add(json);
		next_x += 150;
	}
	
	//�õ�Vertex��inputPort Json
	public void getInputJson(Vertex vertex){
		
		Port port = (Port)vertex.getInputChildList().get(0);
		JSONObject json = new JSONObject(); 
		json.put("id", port.getId());
		String parentId = port.getParent().getId();
		json.put("parentId", parentId);
		json.put("type", "Input");
		json.put("portName", "");
		jsonProperArray.add(json);
		
	}
	
	//�õ�Vertex��outputPort Json
	public void getOutputJson(Vertex vertex){
		
		Port port = (Port)vertex.getOutputChildList().get(0);
		JSONObject json = new JSONObject(); 
		json.put("id", port.getId());
		String parentId = port.getParent().getId();
		json.put("parentId", parentId);
		json.put("type", "Output");
		json.put("portName", "");
		jsonProperArray.add(json);
		
	}
	//�õ�port�� Json
	public void getPortJson(Port port,String type){

		JSONObject json = new JSONObject(); 
		json.put("id", port.getId());
		String parentId = port.getParent().getId();
		json.put("parentId", parentId);
		json.put("type", type);
		json.put("portName", port.getInterName());
		jsonProperArray.add(json);
		
	}
	//���ߵĹ�ϵ�ӵ�json��
	public void addEdgeToJson(String submitJson){
		JSONArray jsonArray = JSONArray.fromObject(submitJson);
		for(int i =0;i<jsonArray.size();i++){
			JSONObject jsonObj = JSONObject.fromObject(jsonArray.get(i));
			if(jsonObj.getString("type").equals("edge")){
				jsonProperArray.add(jsonObj);
			}
		}
	}
	
	//�õ�Э��ת����ʽ��
	public String getProtocolTran(Vertex sourceVertex,Port sourcePort,Vertex targetVertex,Port targetPort){
		
		InterfaceDao inDao = new InterfaceDao();
		InterfaceVo source= (InterfaceVo)inDao.selectByInter(sourceVertex.getType(),sourcePort.getInterName());
		InterfaceVo target= (InterfaceVo)inDao.selectByInter(targetVertex.getType(),targetPort.getInterName());
		String str = "";
//		for(int i=0;i<inList1.size();i++){
//			for(int j=0;j<inList2.size();j++){
//				if(inList1.get(i).getOutputType().equals(inList2.get(j).getInputType())) tag=true;
//			}
//		}
		if(!(source.getOutputProtocol().equalsIgnoreCase(target.getInputProtocol()))){
			ProtocolTransDao td = new ProtocolTransDao();
			ProtocolTransVo tv = (ProtocolTransVo)td.selectByProtocol(source.getOutputProtocol(), target.getInputProtocol());
			//System.out.println(tv.toString());
			if(tv!=null){
				str = tv.getProtocolTransInfo();
			}
		} 
		
		if(!(source.getOutputType().equalsIgnoreCase(target.getInputType()))){
			TransformerDao td = new TransformerDao();
			TransformerVo tv = (TransformerVo)td.selectById(source.getOutputType(), target.getInputType());
			//System.out.println(tv.toString());
			if(tv != null){
				str = str.replace("&&", tv.getTransfInfo());
			}
		}
		return str;
	}
		
	
}
