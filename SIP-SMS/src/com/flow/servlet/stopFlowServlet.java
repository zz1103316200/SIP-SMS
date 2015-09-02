package com.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flow.dao.FlowTableDao;
import com.flow.vo.FlowTableVo;

import function.DynamicStartProject;
import function.EndClient;
import function.RunJar;
import function.SocketTools;
import function.StartClient;
import function.StartRetMsgServer;
import function.startRetMsgClient;

public class stopFlowServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public stopFlowServlet() {
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
		
		try{
//			RunJar rj = new RunJar();
//			if(rj.getJarStat() == true){
				//向Mule发送停止流程的额消息
	       		System.out.println("Client发送信息");
	       		EndClient ec = new EndClient(SocketTools.targetIp,SocketTools.startPort);
	       		ec.sendString("EndFlow",flowName,SocketTools.targetIp);
	       		
	       		System.out.println("流程停止已经成功操作");
//			} 
//			else{
////				startRetMsgClient armc = new startRetMsgClient(SocketTools.targetIp , SocketTools.webPort);
////				armc.sendString("流程没有启动，所以不会停止");
//				System.out.println("流程没有启动，所以不会停止");
//			}
		}catch(Exception e){
			e.printStackTrace();
		}

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
