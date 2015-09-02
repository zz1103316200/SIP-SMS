package com.flow.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.flow.dao.ProtocolDao;
import com.flow.model.Port;
import com.flow.model.Vertex;
import com.flow.vo.ProtocolVo;
import com.flow.model.Edge;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FlowTool {
		//String str;
		
		//这个方法是通过json的str封装成三个map vertexMap portMap 和 edgeMap
		public void getMap(String str,Vertex start,HashMap vertexMap,HashMap portMap,HashMap edgeMap){
			//ProtocolDao pd = new ProtocolDao();
			//List protocolList = pd.selectAll();
			//System.out.println("size:"+str);
			JSONArray jsonArray = JSONArray.fromObject(str);
			
			for(int i=0;i<jsonArray.size();i++){
				//将vertex放到vertexMap中
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.getString(i));
				//String realType=jsonObject.getString("realType");
				String type = jsonObject.getString("type");
				if((type.equals("vertex"))&&(jsonObject.getString("realType").equals("Start"))){
					start.setId(jsonObject.getString("id"));
					start.setCompType(jsonObject.getString("compType"));
					vertexMap.put(jsonObject.getString("id"), start);
				//将splitter组件放到vertexMap中
				}else if(type.equals("vertex")&&(jsonObject.getString("realType").equals("Splitter"))){
					Vertex vertex = new Vertex(jsonObject.getString("realType"));
					vertex.setId(jsonObject.getString("id"));
					vertex.setCompType(jsonObject.getString("compType"));
					vertex.setValue("expression", jsonObject.getString("expression"));
					vertexMap.put(jsonObject.getString("id"), vertex);
				//将aggregator放到vertexMap中
				}else if(type.equals("vertex")&&(jsonObject.getString("realType").equals("Aggregator"))){
					Vertex vertex = new Vertex(jsonObject.getString("realType"));
					vertex.setId(jsonObject.getString("id"));
					vertex.setCompType(jsonObject.getString("compType"));
					vertex.setValue("storePrefix", jsonObject.getString("storePrefix"));
					vertex.setValue("timeout", jsonObject.getString("timeout"));
					vertexMap.put(jsonObject.getString("id"), vertex);
				//将option放到vertexMap中
				}else if(type.equals("vertex")&&(jsonObject.getString("realType").equals("Option"))){
					Vertex vertex = new Vertex(jsonObject.getString("realType"));
					vertex.setId(jsonObject.getString("id"));
					vertex.setCompType(jsonObject.getString("compType"));
					vertex.setValue("option", jsonObject.getString("option"));
					vertexMap.put(jsonObject.getString("id"), vertex);
				//将其他控制组件放到vertexMap中
				}else if(type.equals("vertex")&&(jsonObject.getString("compType").equals("1"))){
					Vertex vertex = new Vertex(jsonObject.getString("realType"));
					vertex.setId(jsonObject.getString("id"));
					vertex.setCompType(jsonObject.getString("compType"));
					vertexMap.put(jsonObject.getString("id"), vertex);
				//将业务组件放到vertexMap中
				}else if(type.equals("vertex")&&(jsonObject.getString("compType").equals("2"))){
					Vertex vertex = new Vertex(jsonObject.getString("realType"));
					vertex.setId(jsonObject.getString("id"));
					vertex.setCompType(jsonObject.getString("compType"));
					//vertex.setValue("name", jsonObject.getString("name"));
					//vertex.setValue("interfaceName", jsonObject.getString("interfaceName"));
					vertexMap.put(jsonObject.getString("id"), vertex);
				//将协议组件放到vertexMap中
				}else if(type.equals("vertex")&&(jsonObject.getString("compType").equals("3"))){
					Vertex vertex = new Vertex(jsonObject.getString("realType"));
					vertex.setId(jsonObject.getString("id"));
					vertex.setCompType(jsonObject.getString("compType"));
					ProtocolDao pd = new ProtocolDao();
					ProtocolVo pv = (ProtocolVo)pd.selectByName(jsonObject.getString("realType"));
					String propertiesStr = pv.getProtocolProperties();
					JSONArray properJsonArray = JSONArray.fromObject(propertiesStr);
					for(int j=0;j<properJsonArray.size();j++){
						JSONObject propertiesJsonObject = JSONObject.fromObject(properJsonArray.getString(j));
						vertex.setValue(propertiesJsonObject.getString("name"), jsonObject.getString("property"+(j+1)));
					}
					vertexMap.put(jsonObject.getString("id"), vertex);
				//将port放到portMap中
				}else if(!(type.equals("edge"))){
					Port port = new Port("port");
					port.setId(jsonObject.getString("id"));
					port.setInterName(jsonObject.getString("portName"));
					//System.out.println("portID---"+jsonObject.getString("id"));
					//System.out.println("portID---"+jsonObject.getString("id"));
					//父子互相赋值
					Vertex parent=(Vertex) vertexMap.get(jsonObject.getString("parentId"));
					
					if(type.equals("Input")){
						parent.addInputChild(port);
					}else{
						parent.addOutputChild(port);
					}
					port.setParent(parent);
					portMap.put(jsonObject.getString("id"), port);
				//将edge放到edgeMap中
				}else if(type.equals("edge")){
					Edge edge = new Edge("edge");
					Port source=(Port)portMap.get(jsonObject.getString("sourceId"));
					
					Port target=(Port)portMap.get(jsonObject.getString("targetId"));
				
					edge.setId(jsonObject.getString("id"));
					edge.setSource(source);
				    edge.setTarget(target);
				 
					//只有源节点才有这个edge列表
					source.addEdge(edge);
					edgeMap.put(jsonObject.getString("id"), edge);
				}
			}
		}
 }
