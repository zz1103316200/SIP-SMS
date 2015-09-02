package com.flow.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flow.dao.BusinessComponentDao;
import com.flow.vo.BusinessComponentVo;
import com.flow.vo.TransformerVo;

public class BusinessDestroy extends HttpServlet {
	private String picPath;
	private String tomcatPath;
	/**
	 * Constructor of the object.
	 */
	public BusinessDestroy() {
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
		//String serviceId = "4722338ba5944c80b7a39237d21e3b26";
		String serviceId = (String)request.getParameter("ServcieID");
		BusinessComponentDao bDao = new BusinessComponentDao();
		
		//��ȡ�����Ϣ��Ϊ���ҵĵ�ʱ�ϴ���ͼƬ
		BusinessComponentVo business= (BusinessComponentVo) bDao.selectById(serviceId);
		String FileName = business.getPictureURL().toString();
		tomcatPath  = request.getRealPath("/").replace("/", "\\").replace("\\", "\\\\");//�õ�������tomcat�µ�·��
		String pictureURL = tomcatPath+picPath+FileName;//�õ�ͼƬ��·�����ŵ����ݿ���	
	    File file = new File(pictureURL);  
	    // ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	    }  
		
		//�ж��Ƿ�ɾ���ɹ�
		
			
			boolean b = bDao.delete(new Object[]{serviceId});
			if(b==true){
				out.print("success");//ɾ���ɹ�
			}else if(b==false){
				out.print("fail");//ɾ��ʧ��
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
		picPath = getServletContext().getInitParameter("picture-upload"); //ͨ�������ļ�web.xml��ȡ�ļ�·��
	}

}
