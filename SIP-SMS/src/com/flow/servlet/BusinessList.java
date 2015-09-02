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
import com.flow.dao.ProtocolDao;
import com.flow.vo.BusinessComponentVo;
import com.flow.vo.ProtocolVo;

public class BusinessList extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public BusinessList() {
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
		String pageNum = "5";*/
		String startNum = (String)request.getParameter("startNum");//�õ���ʼ������
		String pageNum = (String)request.getParameter("pageNum");//�õ�ÿҳ��ʾ��������
		int start = Integer.valueOf(startNum);
		int page = Integer.valueOf(pageNum);
		
		BusinessComponentDao bDao = new BusinessComponentDao();
		List <BusinessComponentVo> businessCompList = new ArrayList<BusinessComponentVo>();//���ڴ�����е�ҵ�����
		List<BusinessComponentVo> businessCompList1	= new ArrayList<BusinessComponentVo>();//���ڴ�ű�ҳ��ҵ�����
		JSONArray businessCompList2	 = new JSONArray();//���ڴ�ű�Ҳ��ҵ�������ÿ������ֵ��ҵ���������businessCompName  ������:businessCompServiceName ����ϵͳ�� businessCompSystem ������businessCompDescription ״̬��businessCompStatus 
		businessCompList = bDao.selectAll();
		int length = businessCompList.size();//�õ����е�ҵ������ĸ���
		int tem = length-start; //��start������ʼ�����н���ǰ��ļ���
		if(page>tem){
			page = tem;//�����һҳ������ʾ�����������ڿ�����ʾ������
		}
		int end = start+page-1;//�õ���һҳ�����һ����¼������
		for(int i=start;i<=end;i++){
			businessCompList1.add(businessCompList.get(i));
			//System.out.println(protocolList1.get(i-start));//��������õ�
		}
		
		//�����ݿ��е���Ϣ������������String�������ʽ�ŵ�ArrayList������
		for(int i=0;i<businessCompList1.size();i++){
			BusinessComponentVo temp = businessCompList1.get(i);
			JSONObject json = new JSONObject();
			try {
				json.put("CompName",temp.getCompName());
				json.put("ServiceName", temp.getServiceName());
				json.put("System", temp.getSystem());
				json.put("Description", temp.getDescription());
				json.put("Status", temp.getStatus());
				json.put("ServiceID", temp.getServiceID());//���������
				json.put("InterfaceName", temp.getInterfaceName());
				json.put("SubServiceName", temp.getSubServiceName());			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			businessCompList2.put(json);
		} 
		System.out.println(businessCompList2);//��������
		response.setContentType("application/json;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		out.println(businessCompList2);
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
