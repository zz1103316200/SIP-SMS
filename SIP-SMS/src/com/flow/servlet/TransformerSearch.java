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

public class TransformerSearch extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TransformerSearch() {
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
		String style = "InputType";
		String value = "Data_1";*/
		String startNum = (String)request.getParameter("startNum");//�õ���ʼ������
		String pageNum = (String)request.getParameter("pageNum");//�õ�ÿҳ��ʾ��������
		String style = (String)request.getParameter("style");///�õ��������ͣ�������������ƣ�TransfName)�������ʽ(InputType)�������ʽ(OutputType) ����(TransfDescription)��״̬(TransfStatus)
		String value = (String)request.getParameter("value");//�õ�ĳ���������͵�ֵ
		int start = Integer.valueOf(startNum);
		int page = Integer.valueOf(pageNum);
		TransformerDao tDao = new TransformerDao();
	
		List <TransformerVo> transformerList = new ArrayList<TransformerVo>();//���ڴ�����е�ҵ�����
		JSONArray transformerList1	= new JSONArray();//���ڴ�ű�ҳ��ת�����
		JSONArray transformerList2 = new JSONArray();//���ڴ���������������ľ�����Ϣ
		transformerList = tDao.selectAll();
		if(style.equals("TransfName")){
			for(int i=0;i<transformerList.size();i++){
				TransformerVo protemp = transformerList.get(i);
				if(protemp.getTransfName().toString().contains(value)){
					JSONObject json = new JSONObject();
					try {
						json.put("TransfName",protemp.getTransfName());
						json.put("InputType", protemp.getInputType());
						json.put("OutputType",protemp.getOutputType());
						json.put("TransfDescription", protemp.getTransfDescription());
						json.put("TransfStatus", protemp.getTransfStatus());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					transformerList2.put(json);
				}
			}
		}else if(style.equals("InputType")){
			for(int i=0;i<transformerList.size();i++){
				TransformerVo protemp = transformerList.get(i);
				if(protemp.getInputType().toString().equals(value)){
					JSONObject json = new JSONObject();
					try {
						json.put("TransfName",protemp.getTransfName());
						json.put("InputType", protemp.getInputType());
						json.put("OutputType",protemp.getOutputType());
						json.put("TransfDescription", protemp.getTransfDescription());
						json.put("TransfStatus", protemp.getTransfStatus());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					transformerList2.put(json);
				}
			}
		}else if(style.equals("OutputType")){
			for(int i=0;i<transformerList.size();i++){
				TransformerVo protemp = transformerList.get(i);
				if(protemp.getOutputType().toString().equals(value)){
					JSONObject json = new JSONObject();
					try {
						json.put("TransfName",protemp.getTransfName());
						json.put("InputType", protemp.getInputType());
						json.put("OutputType",protemp.getOutputType());
						json.put("TransfDescription", protemp.getTransfDescription());
						json.put("TransfStatus", protemp.getTransfStatus());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					transformerList2.put(json);
				}
			}
		}else if(style.equals("TransfDescription")){
			for(int i=0;i<transformerList.size();i++){
				TransformerVo protemp = transformerList.get(i);
				if(protemp.getTransfDescription().toString().contains(value)){
					JSONObject json = new JSONObject();
					try {
						json.put("TransfName",protemp.getTransfName());
						json.put("InputType", protemp.getInputType());
						json.put("OutputType",protemp.getOutputType());
						json.put("TransfDescription", protemp.getTransfDescription());
						json.put("TransfStatus", protemp.getTransfStatus());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					transformerList2.put(json);
				}
			}
		}else if(style.equals("TransfStatus")){
			for(int i=0;i<transformerList.size();i++){
				TransformerVo protemp = transformerList.get(i);
				if(protemp.getTransfStatus().toString().contains(value)){
					JSONObject json = new JSONObject();
					try {
						json.put("TransfName",protemp.getTransfName());
						json.put("InputType", protemp.getInputType());
						json.put("OutputType",protemp.getOutputType());
						json.put("TransfDescription", protemp.getTransfDescription());
						json.put("TransfStatus", protemp.getTransfStatus());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					transformerList2.put(json);
				}
			}
		}
		
		int length = transformerList2.length();//�õ����е�����������ת������ĸ���
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
				transformerList1.put(transformerList2.get(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(transformerList1);
		response.setContentType("application/json;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		out.println(transformerList1);
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
