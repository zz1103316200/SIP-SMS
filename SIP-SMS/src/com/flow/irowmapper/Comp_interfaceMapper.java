package com.flow.irowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.flow.vo.BusinessComponentVo;
import com.flow.vo.Comp_interfaceVo;

public class Comp_interfaceMapper implements IrowMapper{

	public Object mapper(ResultSet rs) {
		Comp_interfaceVo vo=new Comp_interfaceVo();
		
		try {
			
			vo.setServiceName(rs.getString("ServiceName"));
			vo.setInterfaceName(rs.getString("InterfaceName"));
			vo.setInterfaceCallInfo(rs.getString("InterfaceCallInfo"));
			
			return vo;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vo;
	}

}
