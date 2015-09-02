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
	      // �ļ���С�����ֵ�����洢���ڴ���
	      factory.setSizeThreshold(maxMemSize);

	      // ����һ���µ��ļ��ϴ��������
	      ServletFileUpload upload = new ServletFileUpload(factory);
	      // �����ϴ����ļ���С�����ֵ
	      upload.setSizeMax( maxFileSize );

	      try{ 
	      // �������󣬻�ȡ�ļ���
	      List fileItems = (List) upload.parseRequest(request);//��request�õ������ϴ�����б�
	      // �����ϴ����ļ���
	      Iterator i = ((java.util.List) fileItems).iterator();
	    
	      while ( i.hasNext () ) 
	      {
	    	  System.out.println("==========11");
	         FileItem fi = (FileItem)i.next();
	         if ( !fi.isFormField () )	//�ж�������ͨ������file����fileitem�ļ����裬���Ի�ȡ��С��·��   
	         {
	            // ��ȡ�ϴ��ļ��Ĳ���
	            String fieldName = fi.getFieldName();
	            String fileName = fi.getName();
	            String contentType = fi.getContentType();
	            boolean isInMemory = fi.isInMemory();
	            long sizeInBytes = fi.getSize();
	            // д���ļ�
	        
	            tomcatPath  = request.getRealPath("/").replace("/", "\\").replace("\\", "\\\\");//�õ�������tomcat�µ�·��
	            String[] str = fileName.split("\\.");//���ϴ����ļ���Ϊ�ļ������׺������  
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
		javaPath = getServletContext().getInitParameter("file-upload"); //ͨ�������ļ�web.xml��ȡ�ļ�·��
		picPath =  getServletContext().getInitParameter("picture-upload");//ͨ�������ļ�web.xml��ȡͼƬ��·��
		javaPath1 = System.getProperty("user.dir");
	}

}
