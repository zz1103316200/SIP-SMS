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

import com.flow.dao.BusinessComponentDao;
import com.flow.dao.IDAO;
import com.flow.dao.ProtocolDao;
import com.flow.vo.BusinessComponentVo;

public class BusinessSearch extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public BusinessSearch() {
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
		PrintWriter out = response.getWriter();
		//测试数据
		/*String startNum = "0";	
		String pageNum = "5";
		String style = "Description";
		String value = "comp";*/
		String startNum = (String)request.getParameter("startNum");//得到起始的条码
		String pageNum = (String)request.getParameter("pageNum");//得到每页显示的条码数
		String style = (String)request.getParameter("style");//得到搜索类型，包括：业务名称（CompName)、服务名(ServiceName)、所属系统(System)描述(Description)、状态（Status)
		String value = (String)request.getParameter("value");//得到某种搜索类型的值

		int start = Integer.valueOf(startNum);
		int page = Integer.valueOf(pageNum);
		BusinessComponentDao bDao = new BusinessComponentDao();
	
		List <BusinessComponentVo> businessCompList = new ArrayList<BusinessComponentVo>();//用于存放所有的业务组件
		JSONArray businessCompList1	= new JSONArray();//用于存放本页的业务组件
		JSONArray businessCompList2 = new JSONArray();//用于存放所有满足条件的具体信息
		businessCompList = bDao.selectAll();
		if(style.equals("CompName")){
			for(int i=0;i<businessCompList.size();i++){
				BusinessComponentVo protemp = businessCompList.get(i);
				if(protemp.getCompName().toString().contains(value)){
					JSONObject json = new JSONObject();
					try {
						json.put("CompName", protemp.getCompName());
						json.put("ServiceName", protemp.getServiceName());
						json.put("System", protemp.getSystem());
						json.put("Description", protemp.getDescription());
						json.put("Status", protemp.getStatus());
						json.put("ServcieID", protemp.getServiceID());
						json.put("InterfaceName", protemp.getInterfaceName());
						json.put("SubServiceName", protemp.getSubServiceName());	
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					businessCompList2.put(json);
				}
			}
		}else if(style.equals("ServiceName")){
			for(int i=0;i<businessCompList.size();i++){
				BusinessComponentVo protemp = businessCompList.get(i);
				if(protemp.getServiceName().toString().contains(value)){
					JSONObject json = new JSONObject();
					try {
						json.put("CompName", protemp.getCompName());
						json.put("ServiceName", protemp.getServiceName());
						json.put("System", protemp.getSystem());
						json.put("Description", protemp.getDescription());
						json.put("Status", protemp.getStatus());
						json.put("ServcieID", protemp.getServiceID());
						json.put("InterfaceName", protemp.getInterfaceName());
						json.put("SubServiceName", protemp.getSubServiceName());	
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					businessCompList2.put(json);
				}
			}
		}else if(style.equals("System")){
			for(int i=0;i<businessCompList.size();i++){
				BusinessComponentVo protemp = businessCompList.get(i);
				if(protemp.getSystem().toString().contains(value)){
					JSONObject json = new JSONObject();
					try {
						json.put("CompName", protemp.getCompName());
						json.put("ServiceName", protemp.getServiceName());
						json.put("System", protemp.getSystem());
						json.put("Description", protemp.getDescription());
						json.put("Status", protemp.getStatus());
						json.put("ServcieID", protemp.getServiceID());
						json.put("InterfaceName", protemp.getInterfaceName());
						json.put("SubServiceName", protemp.getSubServiceName());	
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					businessCompList2.put(json);
				}
			}
		}else if(style.equals("Description")){
			for(int i=0;i<businessCompList.size();i++){
				BusinessComponentVo protemp = businessCompList.get(i);
				if(protemp.getDescription().toString().contains(value)){
					JSONObject json = new JSONObject();
					try {
						json.put("CompName", protemp.getCompName());
						json.put("ServiceName", protemp.getServiceName());
						json.put("System", protemp.getSystem());
						json.put("Description", protemp.getDescription());
						json.put("Status", protemp.getStatus());
						json.put("ServcieID", protemp.getServiceID());
						json.put("InterfaceName", protemp.getInterfaceName());
						json.put("SubServiceName", protemp.getSubServiceName());	
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					businessCompList2.put(json);
				}
			}
		}else if(style.equals("Status")){
			for(int i=0;i<businessCompList.size();i++){
				BusinessComponentVo protemp = businessCompList.get(i);
				if(protemp.getStatus().toString().equals(value)){
					JSONObject json = new JSONObject();
					try {
						json.put("CompName", protemp.getCompName());
						json.put("ServiceName", protemp.getServiceName());
						json.put("System", protemp.getSystem());
						json.put("Description", protemp.getDescription());
						json.put("Status", protemp.getStatus());
						json.put("ServcieID", protemp.getServiceID());
						json.put("InterfaceName", protemp.getInterfaceName());
						json.put("SubServiceName", protemp.getSubServiceName());	
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					businessCompList2.put(json);
				}
			}
		}
		
		int length = businessCompList2.length();//得到所有的满足条件的业务组件的个数
		if(length<start){
			out.print("wrong");//向前台输出超出范围了
			System.out.println("out of range");
		}else{
		int tem = length-start; //从start这条开始，所有将其前面的减掉
		if(page>tem){
			page = tem;//如果这一页可以显示的条码数多于可以显示的条数
		}
		int end = start+page-1;//得到这一页的最后一条记录的条码
		for(int i=start;i<=end;i++){
			try {
				businessCompList1.put(businessCompList2.get(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println(businessCompList1);//测试用例
		response.setContentType("application/json;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		out.println(businessCompList1);
		out.flush(); 
		out.close();
		}
		
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
