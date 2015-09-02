package com.flow.irowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.flow.vo.ServicesVo;
import com.flow.vo.SubServiceVo;

public class SubServiceMapper implements IrowMapper{

	public Object mapper(ResultSet rs) {
		// TODO Auto-generated method stub
		SubServiceVo vo = new SubServiceVo();
		try{
				vo.setSubServiceID(rs.getInt("SubServiceID"));
				vo.setServiceID(rs.getString("ServiceID"));
				vo.setSubServiceName(rs.getString("SubServiceName"));
				vo.setIcon(rs.getString("Icon"));
				vo.setKeyword(rs.getString("Keyword"));
				vo.setDescription(rs.getString("Description"));
				vo.setMetadata(rs.getString("Metadata"));
				vo.setWsdl(rs.getString("Wsdl"));
				vo.setUrl(rs.getString("URL"));
				
				return vo;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return vo;
	}

}
