package com.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flow.dao.BusinessComponentDao;
import com.flow.dao.TransformerDao;
import com.flow.vo.BusinessComponentVo;
import com.flow.vo.TransformerVo;

public class TransformerEdit extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TransformerEdit() {
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
		/*String inputType = "boolean";
		String outputType = "Custom2";
		String description = "����������Custom2ת��Custom1";
		String transformerName = "Custom2_To_Custom1";*/
		String inputType = (String)request.getParameter("InputType");//���ת������������ʽ
		String outputType = (String)request.getParameter("OutputType");//���ת������������ʽ
		String transformerName = (String)request.getParameter("TransfName");//����޸ĺ���������
		String description = (String)request.getParameter("TransfDescription");//����޸ĺ��ת���������
		TransformerDao tDao = new TransformerDao();
		boolean t = tDao.updateEdit(new Object[]{transformerName,description,inputType,outputType});
		
		if(t==true){
			out.print("success");//�޸ĳɹ�
		}else if(t==false){
			out.print("fail");//�޸�ʧ��
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
