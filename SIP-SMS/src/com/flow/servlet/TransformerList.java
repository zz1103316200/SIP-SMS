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
import com.flow.dao.TransformerDao;
import com.flow.vo.BusinessComponentVo;
import com.flow.vo.TransformerVo;

public class TransformerList extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TransformerList() {
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
		
		TransformerDao tDao = new TransformerDao();
		List <TransformerVo> transformerList = new ArrayList<TransformerVo>();//���ڴ�����е�ת�����
		List<TransformerVo> transformerList1	= new ArrayList<TransformerVo>();//���ڴ�ű�ҳ��ת�����
		JSONArray transformerList2	 = new JSONArray();//���а������԰�����������ƣ�TransformerName)�������ʽ(TransformerInputStyle)�������ʽ(TransformerOutputStyle) ����(TransformerDescription)��״̬��TransformerStatus)
		transformerList = tDao.selectAll();
		int length = transformerList.size();//�õ����е�ת������ĸ���
		int tem = length-start; //��start������ʼ�����н���ǰ��ļ���
		if(page>tem){
			page = tem;//�����һҳ������ʾ�����������ڿ�����ʾ������
		}
		int end = start+page-1;//�õ���һҳ�����һ����¼������
		for(int i=start;i<=end;i++){
			transformerList1.add(transformerList.get(i));
			//System.out.println(protocolList1.get(i-start));//��������õ�
		}
		
		//�����ݿ��е���Ϣ������������String�������ʽ�ŵ�ArrayList������
		for(int i=0;i<transformerList1.size();i++){
			TransformerVo temp = transformerList1.get(i);
			JSONObject json = new JSONObject();
		
			try {
				json.put("TransfName",temp.getTransfName());	
				json.put("InputType", temp.getInputType());
				json.put("OutputType",temp.getOutputType() );
				json.put("TransfDescription",temp.getTransfDescription() );
				json.put("TransfStatus",temp.getTransfStatus() );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			transformerList2.put(json);
		}
		
		response.setContentType("application/json;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		out.println(transformerList2);
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
