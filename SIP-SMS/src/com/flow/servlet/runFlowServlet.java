package com.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flow.dao.FlowTableDao;
import com.flow.vo.FlowTableVo;

import function.DynamicStartProject;

public class runFlowServlet extends HttpServlet {

	private String m_xml_declare = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	private String m_xml_namespace = "<mule xmlns:file=\"http://www.mulesoft.org/schema/mule/file\" xmlns:ftp=\"http://www.mulesoft.org/schema/mule/ee/ftp\" xmlns:http=\"http://www.mulesoft.org/schema/mule/http\" xmlns=\"http://www.mulesoft.org/schema/mule/core\" xmlns:doc=\"http://www.mulesoft.org/schema/mule/documentation\"\n"
			+ "\txmlns:spring=\"http://www.springframework.org/schema/beans\" version=\"EE-3.5.0\"\n"
			+ "\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
			+ "\txsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-current.xsd\n"
			+ "http://www.mulesoft.org/schema/mule/core http://www.mulesoft.org/schema/mule/core/current/mule.xsd\n"
			+ "http://www.mulesoft.org/schema/mule/http http://www.mulesoft.org/schema/mule/http/current/mule-http.xsd\n"
			+ "http://www.mulesoft.org/schema/mule/ee/ftp http://www.mulesoft.org/schema/mule/ee/ftp/current/mule-ftp-ee.xsd\n"
			+ "http://www.mulesoft.org/schema/m"
			+ "ule/file http://www.mulesoft.org/schema/mule/file/current/mule-file.xsd\">";
	
	private String m_xml_namespace_old = "<mule xmlns:file=\"http://www.mulesoft.org/schema/mule/file\" xmlns:ftp=\"http://www.mulesoft.org/schema/mule/ftp\" xmlns:http=\"http://www.mulesoft.org/schema/mule/http\" xmlns=\"http://www.mulesoft.org/schema/mule/core\" xmlns:doc=\"http://www.mulesoft.org/schema/mule/documentation\"\n"
			+ "\txmlns:spring=\"http://www.springframework.org/schema/beans\" version=\"CE-3.2.1\"\n"
			+ "\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
			+ "\txsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd\n"
			+ "http://www.mulesoft.org/schema/mule/core http://www.mulesoft.org/schema/mule/core/current/mule.xsd\n"
			+ "http://www.mulesoft.org/schema/mule/http http://www.mulesoft.org/schema/mule/http/current/mule-http.xsd\n"
			+ "http://www.mulesoft.org/schema/mule/ftp http://www.mulesoft.org/schema/mule/ftp/current/mule-ftp.xsd\n"
			+ "http://www.mulesoft.org/schema/mule/file http://www.mulesoft.org/schema/mule/file/current/mule-file.xsd\">";
	
	
	private String m_xml_start = "";
	private String m_xml_end="</mule>";
	private String m_xml_flow = "";
	
	/**
	 * Constructor of the object.
	 */
	public runFlowServlet() {
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

		this.doPost(request, response);
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

		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		String flowName = request.getParameter("flowName"); 
		System.out.println("flowName:"+flowName);
		
		//读取数据库，查找流程信息，在暂存文件下建立流程文件，
		FlowTableDao flowtable = new FlowTableDao();
		FlowTableVo flow = (FlowTableVo) flowtable.selectByName(flowName);  //用那个表对象，将object转换为哪个对象，然后进行获取元素
		String flowDesc = flow.getFlowDescription();
		String flowContent = flow.getFlowString();
		m_xml_flow = flowContent;
		//给配置文件xml添加头和尾巴
		String xmlStr = m_xml_declare +"\n\n"+ m_xml_namespace_old + "\n" + m_xml_flow  + "\n" + m_xml_end;
		

		//动态启动流程
		DynamicStartProject dsp = new DynamicStartProject(flowName,flowDesc,xmlStr);
		String msg = dsp.invoke();
		
		out.print(msg);
		out.flush();
		out.close();
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
