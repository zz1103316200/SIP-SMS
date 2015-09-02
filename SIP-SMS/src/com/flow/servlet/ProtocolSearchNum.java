package com.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.flow.dao.ProtocolDao;
import com.flow.vo.ProtocolVo;

public class ProtocolSearchNum extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ProtocolSearchNum() {
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

		response.setContentType("application/json;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		//测试用例
		/*String style = "ProtocolDescription";
		String value = "ad";*/
		String style = (String)request.getParameter("style");//得到搜索类型，包括：协议名称（ProtocolName)、描述(ProtocolDescription)、状态（ProtocolStatus)
		String value = (String)request.getParameter("value");//得到某种搜索类型的值
		ProtocolDao pDao = new ProtocolDao();
		List <ProtocolVo> protocolList = new ArrayList<ProtocolVo>();//用于存放所有的协议组件
		List<ProtocolVo> protocolList1 = new ArrayList<ProtocolVo>();//用于存放所有满足条件的协议组件
		protocolList = pDao.selectAll();
		if(style.equals("ProtocolName")){
			for(int i=0;i<protocolList.size();i++){
				ProtocolVo protemp = protocolList.get(i);
				if(protemp.getProtocolName().toString().contains(value)){
					protocolList1.add(protemp);
				}
			}
		}else if(style.equals("ProtocolDescription"))
		{	
			for(int i=0;i<protocolList.size();i++){
				ProtocolVo protemp = protocolList.get(i);
				if(protemp.getProtocolDescription().toString().contains(value)){
					protocolList1.add(protemp);
				}
			}
		}else if(style.equals("ProtocolStatus"))
		{	
			for(int i=0;i<protocolList.size();i++){
				ProtocolVo protemp = protocolList.get(i);
				if(protemp.getProtocolStatus().toString().equals(value)){
					protocolList1.add(protemp);
				}
			}
		}
		int num = protocolList1.size();//得到搜索结果的总条数
		JSONObject ja = new JSONObject();
		try {
			ja.put("protocolSearchNum", num);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(ja);//测试用例
		out.println(ja);
		out.flush(); 
		out.close();
		
		//返回结果实例是json串：{"protocolSearchNum":5}
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
