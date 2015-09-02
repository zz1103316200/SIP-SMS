package com.flow.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;


public class BackConn {
	public static DataSource ds;
	
	
		
	
	public static Connection getConn(){
		Connection conn=null;
		try {
			conn=ds.getConnection();
			//System.out.println(conn);
			//System.out.println("OK");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String sql="select * from flowTable";
//		
//		
//		try {
//			Statement pstm = conn.createStatement();
//			ResultSet rs = pstm.executeQuery(sql);
//			if (rs.next()) {
//				String result = rs.getString("FlowName");
//				System.out.println("result:"+result);
//				conn.close();
//			} else {
//				conn.close();		
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
		return conn;
	}
	
	
}
