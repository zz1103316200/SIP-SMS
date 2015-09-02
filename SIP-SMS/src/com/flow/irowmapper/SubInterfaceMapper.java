package com.flow.irowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.flow.vo.ServicesVo;

public class SubInterfaceMapper implements IrowMapper{

	public Object mapper(ResultSet rs) {
		// TODO Auto-generated method stub
		ServicesVo vo = new ServicesVo();
		try{
				vo.setInterfaceName(rs.getString("InterfaceName"));
				
				return vo;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return vo;
	}

}
