package com.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flow.dao.BusinessComponentDao;
import com.flow.dao.InterfaceDao;
import com.flow.dao.ProtocolTransDao;
import com.flow.dao.TransformerDao;
import com.flow.vo.BusinessComponentVo;
import com.flow.vo.InterfaceVo;
import com.flow.vo.ProtocolTransVo;
import com.flow.vo.TransformerVo;

public class checkCompServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public checkCompServlet() {
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
		String sourceName = request.getParameter("sourceName");
		String targetName = request.getParameter("targetName");
		String inportName = request.getParameter("inportName");
		String outportName = request.getParameter("outportName");
//		BusinessComponentDao bcDao = new BusinessComponentDao();
//		BusinessComponentVo bcV1 = (BusinessComponentVo)bcDao.selectByName(sourceName);
//		BusinessComponentVo bcV2 = (BusinessComponentVo)bcDao.selectByName(targetName);
//		String serviceName1 = bcV1.getServiceName();
//		String serviceName2 = bcV2.getServiceName();
		InterfaceDao inDao = new InterfaceDao();
		InterfaceVo source= (InterfaceVo)inDao.selectByInter(sourceName,outportName);
		InterfaceVo target= (InterfaceVo)inDao.selectByInter(targetName,inportName);
		String tag ="1";
//		for(int i=0;i<inList1.size();i++){
//			for(int j=0;j<inList2.size();j++){
//				if(inList1.get(i).getOutputType().equals(inList2.get(j).getInputType())) tag=true;
//			}
//		}
		if(!(source.getOutputType().equalsIgnoreCase(target.getInputType()))){
			TransformerDao td = new TransformerDao();
			Object tv = td.selectById(source.getOutputType(), target.getInputType());
			//System.out.println(tv.toString());
			if(tv == null){
				tag="2";
			}
		}else if(!(source.getOutputProtocol().equalsIgnoreCase(target.getInputProtocol()))){
			ProtocolTransDao td = new ProtocolTransDao();
			Object tv = td.selectByProtocol(source.getOutputProtocol(), target.getInputProtocol());
			//System.out.println(tv.toString());
			if(tv == null){
				tag="3";
			}
		}
		System.out.println("tag:----"+tag);
		out.print(tag);
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
