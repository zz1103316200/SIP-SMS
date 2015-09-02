package com.flow.irowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.flow.vo.StandardVo;

public class StandardMapper implements IrowMapper{

	public Object mapper(ResultSet rs) {
		StandardVo vo=new StandardVo();
		
		try {
			
			vo.setDataType(rs.getString("DataType"));
			vo.setDataTypeContent(rs.getString("DataTypeContent"));
			vo.setDataTypeDescription(rs.getString("DataTypeDescription"));
			vo.setDataTypeStatus(rs.getString("DataTypeStatus"));
			
			return vo;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vo;
	}

}
