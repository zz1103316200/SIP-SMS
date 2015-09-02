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
		//测试数据
		/*String startNum = "0";	
		String pageNum = "5";
		String style = "InputType";
		String value = "Data_1";*/
		String startNum = (String)request.getParameter("startNum");//得到起始的条码
		String pageNum = (String)request.getParameter("pageNum");//得到每页显示的条码数
		String style = (String)request.getParameter("style");///得到搜索类型，包括：组件名称（TransfName)、输入格式(InputType)、输出格式(OutputType) 描述(TransfDescription)、状态(TransfStatus)
		String value = (String)request.getParameter("value");//得到某种搜索类型的值
		int start = Integer.valueOf(startNum);
		int page = Integer.valueOf(pageNum);
		TransformerDao tDao = new TransformerDao();
	
		List <TransformerVo> transformerList = new ArrayList<TransformerVo>();//用于存放所有的业务组件
		JSONArray transformerList1	= new JSONArray();//用于存放本页的转换组件
		JSONArray transformerList2 = new JSONArray();//用于存放所有满足条件的具体信息
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
		
		int length = transformerList2.length();//得到所有的满足条件的转换组件的个数
		if(length<start){
			out.print("wrong");//向前台输出超出范围了
			System.out.println("out of range");
		}else{
		int tem = length-start; //从start这条开始，所有将其前面的减掉
		if(page>tem){
			page = tem;//如果这一页可以显示的条码数多于可以显示的条数
		}
		int end = start+page-1;//得到这一页的最后一条记录的条码
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
