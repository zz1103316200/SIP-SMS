package com.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.flow.dao.BusinessComponentDao;
import com.flow.dao.ProtocolDao;
import com.flow.vo.BusinessComponentVo;
import com.flow.vo.ProtocolVo;

public class BusinessSearchNum extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public BusinessSearchNum() {
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
		response.setContentType("application/json;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		//��������
		/*String style = "Status";
		String value = "true";*/
		String style = (String)request.getParameter("style");//�õ��������ͣ�������ҵ�����ƣ�CompName)��������(ServiceName)������ϵͳ(System)����(Description)��״̬��Status)
		String value = (String)request.getParameter("value");//�õ�ĳ���������͵�ֵ
		System.out.println("in businessNum");
		BusinessComponentDao bDao = new BusinessComponentDao();
		List <BusinessComponentVo> businessCompList = new ArrayList<BusinessComponentVo>();//���ڴ�����е�ҵ�����
		List<BusinessComponentVo> businessCompList1 = new ArrayList<BusinessComponentVo>();//���ڴ����������������ҵ�����
		businessCompList = bDao.selectAll();
		
		if(style.equals("CompName")){
			for(int i=0;i<businessCompList.size();i++){
				BusinessComponentVo comptemp = businessCompList.get(i);
				if(comptemp.getCompName().toString().contains(value)){
					businessCompList1.add(comptemp);
				}
			}
		}else if(style.equals("ServiceName")){
			for(int i=0;i<businessCompList.size();i++){
				BusinessComponentVo comptemp = businessCompList.get(i);
				if(comptemp.getServiceName().toString().contains(value)){
					businessCompList1.add(comptemp);
				}
			}
		}else if(style.equals("System")){
			for(int i=0;i<businessCompList.size();i++){
				BusinessComponentVo comptemp = businessCompList.get(i);
				if(comptemp.getSystem().toString().contains(value)){
					businessCompList1.add(comptemp);
				}
			}
		}else if(style.equals("Description")){
			for(int i=0;i<businessCompList.size();i++){
				BusinessComponentVo comptemp = businessCompList.get(i);
				if(comptemp.getDescription().toString().contains(value)){
					businessCompList1.add(comptemp);
				}
			}
		}else if(style.equals("Status")){
			for(int i=0;i<businessCompList.size();i++){
				BusinessComponentVo comptemp = businessCompList.get(i);
				if(comptemp.getStatus().toString().contains(value)){
					businessCompList1.add(comptemp);
				}
			}
		}
		int num = businessCompList1.size();//�õ����������������
		JSONObject ja = new JSONObject();
		try {
			ja.put("businessSearchNum", num);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(ja);//��������
		out.println(ja);
		out.flush(); 
		out.close();
		
		//���ؽ��ʵ����json����{"businessSearchNum":5}
		
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
