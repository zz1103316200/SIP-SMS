package com.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import com.flow.dao.StandardDao;
import com.flow.vo.StandardVo;

public class InputTypeList extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public InputTypeList() {
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
	 * 对输入格式进行列举
	 */
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		StandardDao sDao = new StandardDao();
		List<StandardVo> standardList = new ArrayList<StandardVo>();//用于存放所有的standard记录
		List<String> standardList1	 = new ArrayList<String>();//用于存放所有的输入格式
		standardList = sDao.selectAll();
		int num = standardList.size();//输入格式的总条数
		for(int i=0;i<num;i++){
			StandardVo temp = standardList.get(i);
			standardList1.add(temp.getDataType());
		}
		JSONArray ja = JSONArray.fromObject(standardList1);
		System.out.println(ja);//测试用例
		response.setContentType("application/json;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		out.println(ja);
		out.flush(); 
		out.close();
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
	}

}
