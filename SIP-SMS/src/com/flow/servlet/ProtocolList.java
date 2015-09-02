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

import com.flow.dao.ProtocolDao;
import com.flow.vo.ProtocolVo;

public class ProtocolList extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ProtocolList() {
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
	 * �о�Э�����
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		//��������
		/*String startNum = "3";	
		String pageNum = "5";*/
		String startNum = (String)request.getParameter("startNum");//�õ���ʼ������
		String pageNum = (String)request.getParameter("pageNum");//�õ�ÿҳ��ʾ��������
		int start = Integer.valueOf(startNum);
		int page = Integer.valueOf(pageNum);
		
		ProtocolDao pDao = new ProtocolDao();
		List <ProtocolVo> protocolList = new ArrayList<ProtocolVo>();//���ڴ�����е�Э�����
		List<ProtocolVo> protocolList1	= new ArrayList<ProtocolVo>();//���ڴ�ű�ҳ��Э�����
		JSONArray protocolList2	 = new JSONArray();//���ڴ�ű�Ҳ��Э�������ÿ������ֵ��Э��ID��ProtocolIDЭ������ProtocolName ������ProtocolDescription ״̬��ProtocolStatus 
		protocolList = pDao.selectAll();
		int length = protocolList.size();//�õ����е�Э������ĸ���
		int tem = length-start; //��start������ʼ�����н���ǰ��ļ���
		if(page>tem){
			page = tem;//�����һҳ������ʾ�����������ڿ�����ʾ������
		}
		int end = start+page-1;//�õ���һҳ�����һ����¼������
		for(int i=start;i<=end;i++){
			protocolList1.add(protocolList.get(i));
			//System.out.println(protocolList1.get(i-start));//��������õ�
		}
		
		//�����ݿ��е���Ϣ������������String�������ʽ�ŵ�ArrayList������
		
		for(int i=0;i<protocolList1.size();i++){
			ProtocolVo temp = protocolList1.get(i);
			JSONObject jsonObj = new JSONObject();
			try {
				jsonObj.put("ProtocolName", temp.getProtocolName());
				jsonObj.put("ProtocolDescription", temp.getProtocolDescription());
				jsonObj.put("ProtocolStatus", temp.getProtocolStatus());
				jsonObj.put("ProtocolID", temp.getProtocolID());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			protocolList2.put(jsonObj);
		}

		System.out.println(protocolList2);
		response.setContentType("application/json;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		out.println(protocolList2);
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
