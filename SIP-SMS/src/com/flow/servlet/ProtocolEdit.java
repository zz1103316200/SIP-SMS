package com.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flow.dao.BusinessComponentDao;
import com.flow.dao.ProtocolDao;
import com.flow.vo.BusinessComponentVo;
import com.flow.vo.ProtocolVo;

public class ProtocolEdit extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ProtocolEdit() {
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
		//��������
	/*	String protocolId = "5";
		String protocolName = "comp3";
		String description = "comp3";*/
		String protocolId = (String)request.getParameter("ProtocolID");//���Э�������Id
		String protocolName = (String)request.getParameter("ProtocolName");//����޸ĺ���������
		String description = (String)request.getParameter("ProtocolDescription");//����޸ĺ��Э���������
		ProtocolDao pDao = new ProtocolDao();
		boolean p = pDao.updateEdit(new Object[]{protocolName,description,protocolId});
		
		 if(p==true){
	  			out.print("success");//��ӳɹ�
	  		}else if(p==false){
	  			out.print("fail");//���ʧ��
	  		}
	  		System.out.println(p);
		
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
