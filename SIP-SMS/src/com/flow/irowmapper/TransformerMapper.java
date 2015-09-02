package com.flow.irowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.flow.vo.TransformerVo;

public class TransformerMapper implements IrowMapper{

	public Object mapper(ResultSet rs) {
		TransformerVo vo=new TransformerVo();
		
		try {
			System.out.println("TransfName:"+rs.getString("TransfName"));
			vo.setTransfName(rs.getString("TransfName"));
			vo.setInputType(rs.getString("InputType"));
			vo.setOutputType(rs.getString("OutputType"));
			vo.setTransfInfo(rs.getString("TransfInfo"));
			vo.setTransfDescription(rs.getString("TransfDescription"));
			vo.setTransfStatus(rs.getString("TransfStatus"));
			vo.setFileURL(rs.getString("FileURL"));
			return vo;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vo;
	}

}
