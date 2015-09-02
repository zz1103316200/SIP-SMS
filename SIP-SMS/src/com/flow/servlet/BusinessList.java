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
		//测试数据
		/*String startNum = "0";	
		String pageNum = "5";*/
		String startNum = (String)request.getParameter("startNum");//得到起始的条码
		String pageNum = (String)request.getParameter("pageNum");//得到每页显示的条码数
		int start = Integer.valueOf(startNum);
		int page = Integer.valueOf(pageNum);
		
		BusinessComponentDao bDao = new BusinessComponentDao();
		List <BusinessComponentVo> businessCompList = new ArrayList<BusinessComponentVo>();//用于存放所有的业务组件
		List<BusinessComponentVo> businessCompList1	= new ArrayList<BusinessComponentVo>();//用于存放本页的业务组件
		JSONArray businessCompList2	 = new JSONArray();//用于存放本也得业务组件的每个属性值：业务组件名：businessCompName  服务名:businessCompServiceName 所属系统： businessCompSystem 描述：businessCompDescription 状态：businessCompStatus 
		businessCompList = bDao.selectAll();
		int length = businessCompList.size();//得到所有的业务组件的个数
		int tem = length-start; //从start这条开始，所有将其前面的减掉
		if(page>tem){
			page = tem;//如果这一页可以显示的条码数多于可以显示的条数
		}
		int end = start+page-1;//得到这一页的最后一条记录的条码
		for(int i=start;i<=end;i++){
			businessCompList1.add(businessCompList.get(i));
			//System.out.println(protocolList1.get(i-start));//测试输出用的
		}
		
		//将数据库中的信息解析出来，以String数组的形式放到ArrayList对象中
		for(int i=0;i<businessCompList1.size();i++){
			BusinessComponentVo temp = businessCompList1.get(i);
			JSONObject json = new JSONObject();
			try {
				json.put("CompName",temp.getCompName());
				json.put("ServiceName", temp.getServiceName());
				json.put("System", temp.getSystem());
				json.put("Description", temp.getDescription());
				json.put("Status", temp.getStatus());
				json.put("ServiceID", temp.getServiceID());//这个是主键
				json.put("InterfaceName", temp.getInterfaceName());
				json.put("SubServiceName", temp.getSubServiceName());			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			businessCompList2.put(json);
		} 
		System.out.println(businessCompList2);//测试用例
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
