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
import com.flow.dao.TransformerDao;
import com.flow.vo.StandardVo;
import com.flow.vo.TransformerVo;

public class OutputTypeList extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public OutputTypeList() {
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
	 * �о������ʽ����transformer�д������ʽ���е������ʽɾ����ʣ���ô���ù��Ľ����о�
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			//��������
			//String inputType = "boolean";
			String inputType = (String)request.getParameter("inputType");//��ȡ�������͵������ʽ
			StandardDao sDao = new StandardDao();
			List<StandardVo> standardList = new ArrayList<StandardVo>();//���ڴ�����е�standard��¼
			List<String> standardList1	 = new ArrayList<String>();//���ڴ�����е������ʽ
			standardList = sDao.selectAll();
			int num = standardList.size();//�����ʽ��������
			for(int i=0;i<num;i++){
				StandardVo temp = standardList.get(i);
				standardList1.add(temp.getDataType());
			}
			
			//���������ʽ��ƥ�䣬��transformer�д������ʽ���е������ʽɾ��
			TransformerDao tDao = new TransformerDao();
			List<TransformerVo> transformerList = tDao.selectByInputType(inputType);//�õ����е�inputType�������͵�����
			for(int i=0;i<transformerList.size();i++){
				TransformerVo temp = transformerList.get(i);
				for(int j=0;j<standardList1.size();j++){
					if(temp.getOutputType().toString().equals(standardList1.get(j))){
						standardList1.remove(j);
						j--;
					}
				}
			}
			//�������ʽ�е������ʽҲ����ɾ������
			for(int i=0;i<standardList1.size();i++){
				if(standardList1.get(i).toString().equals(inputType)){
					standardList1.remove(i);
					i--;
				}
			}
			JSONArray ja = JSONArray.fromObject(standardList1);
			System.out.println(ja);//��������
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
