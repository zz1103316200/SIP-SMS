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

public class ProtocolSearch extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ProtocolSearch() {
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
		String style = "ProtocolDescription";
		String value = "adf";*/
		String startNum = (String)request.getParameter("startNum");//得到起始的条码
		String pageNum = (String)request.getParameter("pageNum");//得到每页显示的条码数
		String style = (String)request.getParameter("style");//得到搜索类型，包括：协议名称（protocolName)、描述(protocolDescription)、状态（protocolStatus)
		String value = (String)request.getParameter("value");//得到某种搜索类型的值
		int start = Integer.valueOf(startNum);
		int page = Integer.valueOf(pageNum);
		ProtocolDao pDao = new ProtocolDao();
	
		List <ProtocolVo> protocolList = new ArrayList<ProtocolVo>();//用于存放所有的协议组件
		JSONArray protocolList1	= new JSONArray();//用于存放本页的协议组件
		JSONArray protocolList2 = new JSONArray();//用于存放所有满足条件的具体信息
		protocolList = pDao.selectAll();
		if(style.equals("ProtocolName")){
			for(int i=0;i<protocolList.size();i++){
				ProtocolVo protemp = protocolList.get(i);
				if(protemp.getProtocolName().toString().equals(value)){
					JSONObject json = new JSONObject();
					try {
						json.put("ProtocolName", protemp.getProtocolName()) ;
						json.put("ProtocolDescription",protemp.getProtocolDescription());
						json.put("ProtocolStatus",protemp.getProtocolStatus());
						json.put("ProtocolID", protemp.getProtocolID());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					protocolList2.put(json);
				}
			}
		}else if(style.equals("ProtocolDescription"))
		{	
			for(int i=0;i<protocolList.size();i++){
				ProtocolVo protemp = protocolList.get(i);
				if(protemp.getProtocolDescription().toString().contains(value)){
					JSONObject json = new JSONObject();
					try {
						json.put("ProtocolName", protemp.getProtocolName()) ;
						json.put("ProtocolDescription",protemp.getProtocolDescription());
						json.put("ProtocolStatus",protemp.getProtocolStatus());
						json.put("ProtocolID", protemp.getProtocolID());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					protocolList2.put(json);
				}
			}
		}else if(style.equals("ProtocolStatus"))
		{	
			for(int i=0;i<protocolList.size();i++){
				ProtocolVo protemp = protocolList.get(i);
				if(protemp.getProtocolStatus().toString().equals(value)){
					JSONObject json = new JSONObject();
					try {
						json.put("ProtocolName", protemp.getProtocolName()) ;
						json.put("ProtocolDescription",protemp.getProtocolDescription());
						json.put("ProtocolStatus",protemp.getProtocolStatus());
						json.put("ProtocolID", protemp.getProtocolID());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					protocolList2.put(json);
				}
			}
		}
		
		int length = protocolList2.length();//得到所有的满足条件的协议组件的个数
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
				protocolList1.put(protocolList2.get(i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(protocolList1);
		response.setContentType("application/json;charset=UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		out.println(protocolList1);
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
