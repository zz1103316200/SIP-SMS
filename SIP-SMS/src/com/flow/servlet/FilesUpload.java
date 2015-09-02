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

public class FilesUpload extends HttpServlet {
	   private boolean isMultipart;
	   private String filePath;
	   private int maxFileSize = 10 * 1024*1024;
	   private int maxMemSize = 4 * 1024*1024;
	   private File file ;
	   private String tomcatPath ;
	   private String picPath;
	   private String javaPath;
	   private String fileURL;
	   private String javaPath1;
	/**
	 * Constructor of the object.
	 */
	public FilesUpload() {
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
		 System.out.println("==========");
		
	      DiskFileItemFactory factory = new DiskFileItemFactory();
	      // 文件大小的最大值将被存储在内存中
	      factory.setSizeThreshold(maxMemSize);

	      // 创建一个新的文件上传处理程序
	      ServletFileUpload upload = new ServletFileUpload(factory);
	      // 允许上传的文件大小的最大值
	      upload.setSizeMax( maxFileSize );

	      try{ 
	      // 解析请求，获取文件项
	      List fileItems = (List) upload.parseRequest(request);//从request得到所有上传域的列表
	      // 处理上传的文件项
	      Iterator i = ((java.util.List) fileItems).iterator();
	    
	      while ( i.hasNext () ) 
	      {
	    	  System.out.println("==========11");
	         FileItem fi = (FileItem)i.next();
	         if ( !fi.isFormField () )	//判读不是普通表单域即是file操作fileitem文件步骤，可以获取大小、路径   
	         {
	            // 获取上传文件的参数
	            String fieldName = fi.getFieldName();
	            String fileName = fi.getName();
	            String contentType = fi.getContentType();
	            boolean isInMemory = fi.isInMemory();
	            long sizeInBytes = fi.getSize();
	            // 写入文件
	        
	            tomcatPath  = request.getRealPath("/").replace("/", "\\").replace("\\", "\\\\");//得到工程在tomcat下的路径
	            String[] str = fileName.split("\\.");//将上传的文件分为文件名与后缀两部分  
	            System.out.println(str[1]+"==========");
	            if(str[1].equals("java")){
	            	filePath = javaPath;
	            	//filePath = javaPath1;
	            }else{
	            	filePath = picPath;
	            }
	            if( fileName.lastIndexOf("\\") >= 0 ){
	               file = new File( tomcatPath+filePath + 
	               fileName.substring( fileName.lastIndexOf("\\"))) ;
	            }else{
	               file = new File(tomcatPath+ filePath + 
	               fileName.substring(fileName.lastIndexOf("\\")+1)) ;
	               System.out.println("filepath:"+tomcatPath+filePath+fileName);  
	             //  fileURL = tomcatPath+filePath+fileName;
	            }
	            fi.write( file ) ;
	            out.print("success");
	         }
	      }
	   }catch(Exception ex) {
	       System.out.println(ex);
	   }
		
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
		picPath =  getServletContext().getInitParameter("picture-upload");//通过配置文件web.xml获取图片的路径
		javaPath1 = System.getProperty("user.dir");
	}

}
