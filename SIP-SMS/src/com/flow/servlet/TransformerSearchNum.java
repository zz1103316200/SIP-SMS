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
import com.flow.dao.TransformerDao;
import com.flow.vo.BusinessComponentVo;
import com.flow.vo.TransformerVo;

public class TransformerSearchNum extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TransformerSearchNum() {
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
		/*String style = "TransfDescription";
		String value = "Data";*/
		String style = (String)request.getParameter("style");//�õ��������ͣ�������������ƣ�TransfName)�������ʽ(InputType)�������ʽ(OutputType) ����(TransfDescription)��״̬(TransfStatus)
		String value = (String)request.getParameter("value");//�õ�ĳ���������͵�ֵ
		TransformerDao tDao = new TransformerDao();
		List <TransformerVo> transformerList = new ArrayList<TransformerVo>();//���ڴ�����е�ת�����
		List<TransformerVo> transformerList1 = new ArrayList<TransformerVo>();//���ڴ����������������ת�����
		transformerList = tDao.selectAll();
		
		if(style.equals("TransfName")){
			for(int i=0;i<transformerList.size();i++){
				TransformerVo comptemp = transformerList.get(i);
				if(comptemp.getTransfName().toString().contains(value)){
					transformerList1.add(comptemp);
				}
			}
		}else if(style.equals("InputType")){
			for(int i=0;i<transformerList.size();i++){
				TransformerVo comptemp = transformerList.get(i);
				if(comptemp.getInputType().toString().equals(value)){
					transformerList1.add(comptemp);
				}
			}
		}else if(style.equals("OutputType")){
			for(int i=0;i<transformerList.size();i++){
				TransformerVo comptemp = transformerList.get(i);
				if(comptemp.getOutputType().toString().equals(value)){
					transformerList1.add(comptemp);
				}
			}
		}else if(style.equals("TransfDescription")){
			for(int i=0;i<transformerList.size();i++){
				TransformerVo comptemp = transformerList.get(i);
				if(comptemp.getTransfDescription().toString().contains(value)){
					transformerList1.add(comptemp);
				}
			}
		}else if(style.equals("TransfStatus")){
			for(int i=0;i<transformerList.size();i++){
				TransformerVo comptemp = transformerList.get(i);
				if(comptemp.getTransfStatus().toString().contains(value)){
					transformerList1.add(comptemp);
				}
			}
		}
		int num = transformerList1.size();//�õ����������������
		JSONObject ja = new JSONObject();
		try {
		
			ja.put("transformerSearchNum", num);
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
