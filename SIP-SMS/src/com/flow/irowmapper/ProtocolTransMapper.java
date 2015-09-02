package com.flow.irowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.flow.vo.ProtocolTransVo;
import com.flow.vo.ProtocolVo;

public class ProtocolTransMapper implements IrowMapper{
	public Object mapper(ResultSet rs) {
		ProtocolTransVo vo=new ProtocolTransVo();
		
		try {
			
			vo.setProtocolTransName(rs.getString("ProtocolTransName"));
			vo.setInputProtocol(rs.getString("InputProtocol"));
			vo.setOutputProtocol(rs.getString("OutputProtocol"));
			vo.setProtocolTransInfo(rs.getString("ProtocolTransInfo"));
			vo.setProtocolTransStatus(rs.getString("ProtocolTransStatus"));
			vo.setProtocolTransDesc(rs.getString("ProtocolTransDesc"));
			
			return vo;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vo;
	}
}
