package com.flow.irowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.flow.vo.BusinessComponentVo;

public class BusinessComponentMapper implements IrowMapper{

	public Object mapper(ResultSet rs) {
		BusinessComponentVo vo=new BusinessComponentVo();
		
		try {
			
			vo.setCompName(rs.getString("CompName"));
			vo.setServiceName(rs.getString("ServiceName"));
			vo.setServiceID(rs.getString("ServiceID"));
			vo.setSystem(rs.getString("System"));
			vo.setCallInfo(rs.getString("CallInfo"));
			vo.setDescription(rs.getString("Description"));
			vo.setStatus(rs.getString("Status"));
			vo.setProperties(rs.getString("Properties"));
			vo.setPictureURL(rs.getString("PictureURL"));
			vo.setInterfaceName(rs.getString("InterfaceName"));
			vo.setSubServiceName(rs.getString("SubServiceName"));
			
			return vo;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vo;
	}
	
}
