package com.flow.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.flow.dao.BusinessComponentDao;
import com.flow.dao.IDAO;
import com.flow.dao.ProtocolDao;
import com.flow.vo.BusinessComponentVo;

public class BusinessSearch extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public BusinessSearch() {
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
		//��������
		/*String startNum = "0";	
		String pageNum = "5";
		String style = "Description";
		String value = "comp";*/
		String startNum = (String)request.getParameter("startNum");//�õ���ʼ������
		String pageNum = (String)request.getParameter("pageNum");//�õ�ÿҳ��ʾ��������
		String style = (String)request.getParameter("style");//�õ��������ͣ�������ҵ�����ƣ�CompName)��������(ServiceName)������ϵͳ(System)����(Description)��״̬��Status)
		String value = (String)request.getParameter("value");//�õ�ĳ���������͵�ֵ

		int start = Integer.valueOf(startNum);
		int page = Integer.valueOf(pageNum);
		BusinessComponentDao bDao = new BusinessComponentDao();
	
		List <BusinessComponentVo> businessCompList = new ArrayList<BusinessComponentVo>();//���ڴ�����е�ҵ�����
		JSONArray businessCompList1	= new JSONArray();//���ڴ�ű�ҳ��ҵ�����
		JSONArray businessCompList2 = new JSONArray();//���ڴ���������������ľ�����Ϣ
		businessCompList = bDao.selectAll();
		if(style.equals("CompName")){
			for(int i=0;i<businessCompList.size();i++){
				BusinessComponentVo protemp = businessCompList.get(i);
				if(protemp.getCompName().toString().contains(value)){
					JSONObject json = new JSONObject();
					try {
						json.put("CompName", protemp.getCompName());
						json.put("ServiceName", protemp.getServiceName());
						json.put("System", protemp.getSystem());
						json.put("Description", protemp.getDescription());
						json.put("Status", protemp.getStatus());
						json.put("ServcieID", protemp.getServiceID());
						json.put("InterfaceName", protemp.getInterfaceName());
						json.put("SubServiceName", protemp.getSubServiceName());	
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					businessCompList2.put(json);
				}
			}
		}else if(style.equals("ServiceName")){
			for(int i=0;i<businessCompList.size();i++){
				BusinessComponentVo protemp = businessCompList.get(i);
				if(protemp.getServiceName().toString().contains(value)){
					JSONObject json = new JSONObject();
					try {
						json.put("CompName", protemp.getCompName());
						json.put("ServiceName", protemp.getServiceName());
						json.put("System", protemp.getSystem());
						json.put("Description", protemp.getDescription());
						json.put("Status", protemp.getStatus());
						json.put("ServcieID", protemp.getServiceID());
						json.put("InterfaceName", protemp.getInterfaceName());
						json.put("SubServiceName", protemp.getSubServiceName());	
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					businessCompList2.put(json);
				}
			}
		}else if(style.equals("System")){
			for(int i=0;i<businessCompList.size();i++){
				BusinessComponentVo protemp = businessCompList.get(i);
				if(protemp.getSystem().toString().contains(value)){
					JSONObject json = new JSONObject();
					try {
						json.put("CompName", protemp.getCompName());
						json.put("ServiceName", protemp.getServiceName());
						json.put("System", protemp.getSystem());
						json.put("Description", protemp.getDescription());
						json.put("Status", protemp.getStatus());
						json.put("ServcieID", protemp.getServiceID());
						json.put("InterfaceName", protemp.getInterfaceName());
						json.put("SubServiceName", protemp.getSubServiceName());	
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					businessCompList2.put(json);
				}
			}
		}else if(style.equals("Description")){
			for(int i=0;i<businessCompList.size();i++){
				BusinessComponentVo protemp = businessCompList.get(i);
				if(protemp.getDescription().toString().contains(value)){
					JSONObject json = new JSONObject();
					try {
						json.put("CompName", protemp.getCompName());
						json.put("ServiceName", protemp.getServiceName());
						json.put("System", protemp.getSystem());
						json.put("Description", protemp.getDescription());
						json.put("Status", protemp.getStatus());
						json.put("ServcieID", protemp.getServiceID());
						json.put("InterfaceName", protemp.getInterfaceName());
						json.put("SubServiceName", protemp.getSubServiceName());	
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					businessCompList2.put(json);
				}
			}
		}else if(style.equals("Status")){
			for(int i=0;i<businessCompList.size();i++){
				BusinessComponentVo protemp = businessCompList.get(i);
				if(protemp.getStatus().toString().equals(value)){
					JSONObject json = new JSONObject();
					try {
						json.put("CompName", protemp.getCompName());
						json.put("ServiceName", protemp.getServiceName());
						json.put("System", protemp.getSystem());
						json.put("Description", protemp.getDescription());
						json.put("Status", protemp.getStatus());
						json.put("ServcieID", protemp.getServiceID());
						json.put("InterfaceName", protemp.getInterfaceName());
						json.put("SubServiceName", protemp.getSubServiceName());	
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					businessCompList2.put(json);
				}
			}
		}
		
		int length = businessCompList2.length();//�õ����е�����������ҵ������ĸ���
		if(length<start){
			out.print("wrong");//��ǰ̨���������Χ��
			System.out.println("out of range");
		}else{
		int tem = length-start; //��start������ʼ�����н���ǰ��ļ���
		if(page>tem){
			page = tem;//�����һҳ������ʾ�����������ڿ�����ʾ������
		}
		int end = start+page-1;//�õ���һҳ�����һ����¼������
		for(int i=start;i<=end;i++){
			try {
				businessCompList1.put(businessCompList2.get(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println(businessCompList1);//��������
		response.setContentType("application/json;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		out.println(businessCompList1);
		out.flush(); 
		out.close();
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
	}

}
