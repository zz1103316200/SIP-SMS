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
		//测试用例
		/*String style = "TransfDescription";
		String value = "Data";*/
		String style = (String)request.getParameter("style");//得到搜索类型，包括：组件名称（TransfName)、输入格式(InputType)、输出格式(OutputType) 描述(TransfDescription)、状态(TransfStatus)
		String value = (String)request.getParameter("value");//得到某种搜索类型的值
		TransformerDao tDao = new TransformerDao();
		List <TransformerVo> transformerList = new ArrayList<TransformerVo>();//用于存放所有的转换组件
		List<TransformerVo> transformerList1 = new ArrayList<TransformerVo>();//用于存放所有满足条件的转换组件
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
		int num = transformerList1.size();//得到搜索结果的总条数
		JSONObject ja = new JSONObject();
		try {
		
			ja.put("transformerSearchNum", num);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(ja);//测试用例
		out.println(ja);
		out.flush(); 
		out.close();
		
		//返回结果实例是json串：{"businessSearchNum":5}
		
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
