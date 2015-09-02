package com.flow.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flow.dao.ProtocolDao;
import com.flow.dao.TransformerDao;
import com.flow.vo.ProtocolVo;
import com.flow.vo.TransformerVo;

public class TransformerDestroy extends HttpServlet {
	  private String javaPath;
	  private String tomcatPath;
	/**
	 * Constructor of the object.
	 */
	public TransformerDestroy() {
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
		/*String inputType = "aw";
		String outputType = "bs";*/
		String inputType = (String)request.getParameter("InputType");
		String outputType = (String)request.getParameter("OutputType");
		TransformerDao tDao = new TransformerDao();
		
		TransformerVo transformer= (TransformerVo) tDao.selectById(inputType,outputType);
		String FileName = transformer.getFileURL().toString();
		tomcatPath  = request.getRealPath("/").replace("/", "\\").replace("\\", "\\\\");//得到工程在tomcat下的路径
		String fileURL = tomcatPath+javaPath+FileName;
	    File file = new File(fileURL);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	    
	    }  
	    
	
				boolean t = tDao.delete(new Object[]{inputType,outputType});
				if(t==true){
					out.print("success");//删除成功
				}else if(t==false){
					out.print("fail");//删除失败
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
		javaPath = getServletContext().getInitParameter("file-upload"); //通过配置文件web.xml获取文件路径
	}

}
