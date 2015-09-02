package com.flow.irowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.flow.vo.FlowTableVo;
import com.flow.vo.InterfaceVo;

public class InterfaceMapper implements IrowMapper{

	public Object mapper(ResultSet rs) {
		InterfaceVo vo=new InterfaceVo();
		
		try {
			
			vo.setServiceID(rs.getString("ServiceID"));
			vo.setSubServiceName(rs.getString("SubServiceName"));
			vo.setInterfaceName(rs.getString("InterfaceName"));
			vo.setInputType(rs.getString("InputType"));
			vo.setOutputType(rs.getString("OutputType"));
			//vo.setInterfaceDescription(rs.getString("InterfaceDescription"));
			vo.setNameSpace(rs.getString("NameSpace"));
			vo.setInputProtocol(rs.getString("InputProtocol"));
			vo.setOutputProtocol(rs.getString("OutputProtocol"));
			//vo.setInterfaceCallInfo("InterfaceCallInfo");
			
			return vo;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vo;
	}

}
