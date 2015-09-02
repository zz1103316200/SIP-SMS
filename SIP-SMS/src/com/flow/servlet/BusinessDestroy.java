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
		//测试数据
		//String serviceId = "4722338ba5944c80b7a39237d21e3b26";
		String serviceId = (String)request.getParameter("ServcieID");
		BusinessComponentDao bDao = new BusinessComponentDao();
		
		//获取这个信息是为了找的当时上传的图片
		BusinessComponentVo business= (BusinessComponentVo) bDao.selectById(serviceId);
		String FileName = business.getPictureURL().toString();
		tomcatPath  = request.getRealPath("/").replace("/", "\\").replace("\\", "\\\\");//得到工程在tomcat下的路径
		String pictureURL = tomcatPath+picPath+FileName;//得到图片的路径并放到数据库中	
	    File file = new File(pictureURL);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	    }  
		
		//判断是否删除成功
		
			
			boolean b = bDao.delete(new Object[]{serviceId});
			if(b==true){
				out.print("success");//删除成功
			}else if(b==false){
				out.print("fail");//删除失败
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
		picPath = getServletContext().getInitParameter("picture-upload"); //通过配置文件web.xml获取文件路径
	}

}
