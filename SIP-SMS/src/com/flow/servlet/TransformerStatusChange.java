package com.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flow.dao.ProtocolDao;
import com.flow.dao.TransformerDao;
import com.flow.vo.ProtocolVo;
import com.flow.vo.TransformerVo;

public class TransformerStatusChange extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TransformerStatusChange() {
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
		//测试用例
	/*	String inputType = "boolean";
		String outputType = "Custom2";
		String status = "false";*/
		String inputType = (String)request.getParameter("inputType");//得到转换组件的输入类型
		String outputType = (String)request.getParameter("outputType");//得到转换组件的输出类型
		String status = (String)request.getParameter("status");//得到协议组件的状态
		TransformerDao tDao = new TransformerDao();
		Boolean t = tDao.updateStatus(new Object[]{status,inputType,outputType});
		System.out.println(inputType+"-"+outputType+"-"+status);
		if(t==true){
			out.print("success");//修改成功
		}else if(t==false){
			out.print("fail");//修改失败
		}
		System.out.println(t);

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
