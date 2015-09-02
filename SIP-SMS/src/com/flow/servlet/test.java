package com.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flow.dao.BusinessComponentDao;
import com.flow.dao.Comp_interfaceDao;
import com.flow.dao.IDAO;
import com.flow.init.testDao;
import com.flow.vo.BusinessComponentVo;
import com.flow.vo.Comp_interfaceVo;

public class test extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public test() {
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
//		testDao td = new testDao();
//		td.test();
//		IDAO bsDao = new BusinessComponentDao();
		//bsDao.add(new Object[]{"comp3","comp3","comp3","comp3","comp3","comp3","true","comp3","comp3"});
//		BusinessComponentVo bv = (BusinessComponentVo)bsDao.selectById("comp2");
//		System.out.println("status:"+bv.getStatus());
		//bsDao.update(new Object[]{"comp3","comp3","comp3","comp2","comp2","true","comp2","comp2","comp2"});
		//bsDao.delete(new Object[]{"comp3"});
		//IDAO bsDao = new Comp_interfaceDao();
		//bsDao.add(new Object[]{"ser","ser","ser"});
		//Comp_interfaceVo bv = (Comp_interfaceVo)bsDao.selectById("ser");
		//System.out.println("status:"+bv.getInterfaceName());
		//bsDao.update(new Object[]{"comp3","comp3","comp3","comp2","comp2","true","comp2","comp2","comp2"});
		//bsDao.delete(new Object[]{"comp3"});
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
