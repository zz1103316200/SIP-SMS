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

import com.flow.dao.BusinessComponentDao;
import com.flow.vo.BusinessComponentVo;

public class BusinessEdit extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public BusinessEdit() {
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
		/*String componentId = "sid1";
		String businessName = "comp3";
		String description = "comp3";*/
		String componentId = (String)request.getParameter("ServiceID");//获得业务组件的Id
		String businessName = (String)request.getParameter("CompName");//获得修改后的组件名称
		String description = (String)request.getParameter("Description");//获得修改后的业务组件描述
		BusinessComponentDao bDao = new BusinessComponentDao();
		//直接进行改库操作
		boolean b = bDao.updateEdit(new Object[]{businessName,description,componentId});
		
		if(b==true){
			out.print("success");//修改成功
		}else if(b==false){
			out.print("fail");//修改失败
		}
		System.out.println(b);
		
		
		
		
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
