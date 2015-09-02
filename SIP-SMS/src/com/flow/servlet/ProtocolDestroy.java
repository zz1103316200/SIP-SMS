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
import com.flow.dao.ProtocolDao;
import com.flow.vo.BusinessComponentVo;
import com.flow.vo.ProtocolVo;
import com.flow.vo.TransformerVo;

public class ProtocolDestroy extends HttpServlet {
	   private String tomcatPath ;
	   private String picPath;
	   private String pictureURL;
	/**
	 * Constructor of the object.
	 */
	public ProtocolDestroy() {
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
		//String protocolId = "6";
		String protocolId = (String)request.getParameter("ProtocolID");
		ProtocolDao pDao = new ProtocolDao();
		
		ProtocolVo protocol= (ProtocolVo) pDao.selectById(protocolId);
		String FileName = protocol.getProtocolURL().toString();
		tomcatPath  = request.getRealPath("/").replace("/", "\\").replace("\\", "\\\\");//�õ�������tomcat�µ�·��
		String pictureURL = tomcatPath+picPath+FileName;//�õ�ͼƬ��·��
	    File file = new File(pictureURL);  
	    // ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        
	    }  
	   
		boolean p = pDao.delete(new Object[]{protocolId});
		//�ж��Ƿ�ɾ���ɹ�
		 if(p==true){
	  			out.print("success");//ɾ���ɹ�
	  		}else if(p==false){
	  			out.print("fail");//ɾ��ʧ��
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
		picPath = getServletContext().getInitParameter("picture-upload"); //ͨ�������ļ�web.xml��ȡ�ļ�·��
	}

}
