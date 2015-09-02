package com.flow.irowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.flow.vo.Comp_interfaceVo;
import com.flow.vo.FlowTableVo;

public class FlowTableMapper implements IrowMapper{

	public Object mapper(ResultSet rs) {
		FlowTableVo vo=new FlowTableVo();
		
		try {
			
			vo.setFlowName(rs.getString("FlowName"));
			vo.setFlowDescription(rs.getString("FlowDescription"));
			vo.setFlowString(rs.getString("FlowString"));
			vo.setFlowStyle(rs.getString("FlowStyle"));
			vo.setFlowStatus(rs.getString("FlowStatus"));
			
			return vo;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vo;
	}
	
}
