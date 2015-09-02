package com.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flow.dao.BusinessComponentDao;
import com.flow.vo.BusinessComponentVo;

public class BusinessStatusChange extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public BusinessStatusChange() {
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
	/*	String compentId = "sid1";
		String status = "false";*/
		String compentId = (String)request.getParameter("ServiceID");//�õ�ҵ�������Id
		String status = (String)request.getParameter("Status");//�õ�ҵ�������״̬
		BusinessComponentDao bDao = new BusinessComponentDao();
		//�޸Ŀ���Ķ�Ӧ�м�¼��״̬��Ϣ
		boolean b= bDao.updateStatus(new Object[]{status,compentId});
		System.out.println(compentId+"-"+status);
		if(b==true){
			out.print("success");//���ĳɹ�
		}else if(b==false){
			out.print("fail");//����ʧ��
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
