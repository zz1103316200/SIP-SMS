package com.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flow.dao.BusinessComponentDao;
import com.flow.dao.InterfaceDao;
import com.flow.dao.ProtocolDao;
import com.flow.dao.SubServiceDao;
import com.flow.dao.TransformerDao;
import com.flow.vo.BusinessComponentVo;
import com.flow.vo.InterfaceVo;
import com.flow.vo.ProtocolVo;
import com.flow.vo.SubServiceVo;
import com.flow.vo.TransformerVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ComponentListServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ComponentListServlet() {
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
		JSONObject json = new JSONObject();
		JSONArray compListJson = new JSONArray();
		//得到协议组件列表
		ProtocolDao pd = new ProtocolDao();
		List pl = pd.selectAll();
		for(int i = 0;i<pl.size();i++){
			ProtocolVo pv= (ProtocolVo)pl.get(i);
			if( pv.getProtocolStatus().equals("true")){
				json.put("name", pv.getProtocolName());
				json.put("type", "3");
				json.put("url", pv.getProtocolURL());
				compListJson.add(json);
			}
		}
		//得到业务组件列表
		InterfaceDao bcd = new InterfaceDao();
		SubServiceDao ssd = new SubServiceDao();
		BusinessComponentDao bud = new BusinessComponentDao();
		List<InterfaceVo> bl = bcd.selectAll();
		//int i=0;
		List<BusinessComponentVo> buv  = bud.selectAll();
		List serviName = new ArrayList() ;
		for(int i = 0;i<buv.size();i++){
			//System.out.println("size---"+bl.size());
			BusinessComponentVo bcv= (BusinessComponentVo)buv.get(i);
			String subServiceName =  bcv.getSubServiceName();
			json.put("name", subServiceName);
			List<InterfaceVo> subList = (List)bcd.selectByName(subServiceName);
			String inter = "";
			//得到接口串
			for(int j = 0;j<subList.size();j++){
				InterfaceVo sub= (InterfaceVo)subList.get(j);
				inter+=sub.getInterfaceName()+",";
			}
			if(inter.equals("")){
				json.put("inter", "");
			}else{
				json.put("inter", inter.substring(0, (inter.length()-1)));
			}
			
			json.put("type", "2");
			//得到子服务的url
			SubServiceVo ssv = (SubServiceVo)ssd.selectByServiceName(subServiceName);
			json.put("url", ssv.getUrl());
			compListJson.add(json);
			
//			int tag = 0;
//			for(int k = 0;k<serviName.size();k++){
//				if((serviName.get(k)).equals(subServiceName)){
//					tag = 1;
//				}else{
//					
//				}
//			}
//			
//			if(tag==0){
//				serviName.add(subServiceName);
//				json.put("name", subServiceName);
//				
//				List<InterfaceVo> subList = (List)bcd.selectByName(subServiceName);
//				String inter = "";
//				//得到接口串
//				for(int j = 0;j<subList.size();j++){
//					InterfaceVo sub= (InterfaceVo)subList.get(j);
//					inter+=sub.getInterfaceName()+",";
//				}
//			
//				//System.out.println("inter---"+inter.substring(0, (inter.length()-1)));
//				json.put("inter", inter.substring(0, (inter.length()-1)));
//				json.put("type", "2");
//				//得到子服务的url
//				SubServiceVo ssv = (SubServiceVo)ssd.selectByServiceName(subServiceName);
//				json.put("url", ssv.getUrl());
//				compListJson.add(json);
//			}
		}
		//得到转换器组件列表
		TransformerDao td = new TransformerDao();
		List tl = td.selectAll();
		for(int i = 0;i<tl.size();i++){
			TransformerVo tv= (TransformerVo)tl.get(i);
			if( tv.getTransfStatus().equals("true")){
				json.put("name", tv.getTransfName());
				json.put("type", "4");
				//json.put("url", tv.getProtocolURL());
				compListJson.add(json);
			}
		}
		//System.out.println("component List:"+compListJson);
		out.print(compListJson);
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

}
