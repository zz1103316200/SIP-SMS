package com.flow.irowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.flow.vo.ProtocolVo;

public class ProtocolMapper implements IrowMapper{

	public Object mapper(ResultSet rs) {
		ProtocolVo vo=new ProtocolVo();
		
		try {
			
			vo.setProtocolID(rs.getInt("ProtocolID"));
			vo.setProtocolName(rs.getString("ProtocolName"));
			vo.setProtocolDescription(rs.getString("ProtocolDescription"));
			vo.setProtocolStatus(rs.getString("ProtocolStatus"));
			vo.setProtocolProperties(rs.getString("ProtocolProperties"));
			vo.setProtocolURL(rs.getString("ProtocolURL"));
			
			return vo;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vo;
	}

}
