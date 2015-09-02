package com.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.flow.dao.BusinessComponentDao;
import com.flow.dao.InterfaceDao;
import com.flow.vo.BusinessComponentVo;
import com.flow.vo.InterfaceVo;

public class getInterOfSerServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public getInterOfSerServlet() {
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
		String compName = request.getParameter("name");
		//��ͨ��������ҵ�������
		BusinessComponentDao bcD = new BusinessComponentDao();
		BusinessComponentVo bcV = (BusinessComponentVo)bcD.selectByName(compName);
		String servicename = bcV.getServiceName();
		//��ͨ���������ҵ��ӿ�list
		InterfaceDao inD = new InterfaceDao();
		List<InterfaceVo> inList = inD.selectByName(servicename);
		JSONArray interList = new JSONArray();
		JSONObject subJson = new JSONObject();
		for(int i=0;i<inList.size();i++){
			JSONObject jsonObj =new JSONObject();
			InterfaceVo interVo = inList.get(i);
			jsonObj.put("name", interVo.getInterfaceName());
			interList.add(jsonObj);
		}
		subJson.put("serviceName", servicename);
		subJson.put("interfaceName", interList);
		System.out.println("interfaceList:"+subJson);
		out.print(subJson);
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
