package com.flow.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.flow.dao.BusinessComponentDao;
import com.flow.dao.ProtocolDao;
import com.flow.dao.ServicesDao;
import com.flow.vo.ServicesVo;

public class ProtocolAdd extends HttpServlet {

	   
	/**
	 * Constructor of the object.
	 */
	public ProtocolAdd() {
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
			/*String protocolName = "aa";
			String description = "cc";
			String FileName = "aaa";*/
			String FileName = (String)request.getParameter("FileName");
			String protocolName = (String)request.getParameter("CompName");//��ȡЭ�����������
			String description = (String)request.getParameter("Description");//��ȡЭ�����������
			String property = (String)request.getParameter("property");//��ȡЭ�����������
			System.out.println("property:"+property);
			String status = "true";//Ĭ�������Ϊtrue
	  
			
			String[] pic = FileName.split("\\\\");
			String pictureURL = pic[pic.length-1];//�õ�ͼƬ��·�����ŵ����ݿ���
	    	ProtocolDao pDao = new ProtocolDao();
	        boolean p =pDao.add(new Object[]{protocolName,description,status,property,pictureURL});
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
