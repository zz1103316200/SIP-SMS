package com.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import com.flow.dao.ServicesDao;
import com.flow.vo.ServicesVo;

public class ServiceList extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ServiceList() {
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
		response.setContentType("text/html");
		//String type = "System";
		String  type = (String) request.getParameter("type");
		System.out.println(type+"=====");
		PrintWriter out = response.getWriter();
		ServicesDao sDao = new ServicesDao();
		JSONArray serviceName = new JSONArray();//用来存放所有的服务名
		JSONArray serviceSystem = new JSONArray();//用来存放所有的服务所属系统
		JSONArray Result = new JSONArray(); 
		if(type.equals("serviceId")){
			System.out.println("serviceId");
			List<ServicesVo> service = sDao.selectOne();//得到所有的服务
			for(int i=0;i<service.size();i++){
				JSONObject json = new JSONObject();
				try {
					json.put(service.get(i).getServiceID(), service.get(i).getServiceName());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Result.put(json);
			}		
		}else if(type.equals("SubService")){
			System.out.println("SubService");
			String  ID = (String) request.getParameter("ID");
			System.out.println(ID);
			List<ServicesVo> service = sDao.selectTwo(ID);//得到所有的服务
			for(int i=0;i<service.size();i++){
				JSONObject json = new JSONObject();
				try {
					json.put(ID, service.get(i).getSubServiceName());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Result.put(json);
			}
		}else if(type.equals("Interface")){
			System.out.println("Interface");
			String  ID = (String) request.getParameter("ID");
			String  Name = (String) request.getParameter("Name");
			System.out.println(ID);
			System.out.println(Name);
			List<ServicesVo> service = sDao.selectThree(ID,Name);//得到所有的服务		
			for(int i=0;i<service.size();i++){
				JSONObject json = new JSONObject();
				try {
					json.put(ID, service.get(i).getInterfaceName());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Result.put(json);
			}
		}else if(type.equals("ServiceName")){
			System.out.println("ServiceName");
			List<ServicesVo> service = sDao.selectOne();//得到所有的服务
			for(int i=0;i<service.size();i++){
				serviceName.put(service.get(i).getServiceName());
			}
		}else if(type.equals("System")){
			System.out.println("System");
			List<ServicesVo> service = sDao.selectOne();//得到所有的服务
			for(int i=0;i<service.size();i++){
				serviceSystem.put(service.get(i).getOwnerSystem());
			}
		}
		
		
		
	
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
//		out.print(Result);
//		System.out.println("return:"+Result);
		if(type.equals("ServiceName")){
			out.print(serviceName);
			System.out.println(serviceName);//测试用例
		}else if(type.equals("System")){
			out.print(serviceSystem);
			System.out.println(serviceSystem);//测试用例
		}else
		{
			out.print(Result);
			System.out.println("return:"+Result);//测试用例
		}
		out.flush();
		out.close();
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
			
			doGet(request,response);
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
