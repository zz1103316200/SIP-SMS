package com.flow.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.flow.irowmapper.IrowMapper;

public class DbManager {

	private Connection conn;

	public void beginTrans() {
		try {
			if (conn == null) {
				
				conn = BackConn.getConn();
				conn.setAutoCommit(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void commit() {
		try {
			this.conn.commit();
			this.conn = null;
		} catch (SQLException e) {
			this.rollback();
			e.printStackTrace();
		}
	}

	public void rollback() {

		try {
			this.conn.rollback();
			this.conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	public List select(String sql, Object obj[], IrowMapper map) {	
		
		
		Connection conn = BackConn.getConn();
		try {			
			PreparedStatement pstm = conn.prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					pstm.setObject(i + 1, obj[i]);
				}
			}
			
			ResultSet rs = pstm.executeQuery();
			
			List list = new ArrayList();
			while (rs.next()) {
				Object obj1 = map.mapper(rs);
				list.add(obj1);
			}
			rs.close();
			pstm.close();
			conn.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList();
		}
	}

	
	public Object selectById(String sql, Object obj[], IrowMapper map) {
		Connection conn = BackConn.getConn();
		System.out.println("sql:"+sql);
		try {
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					pstm.setObject(i + 1, obj[i]);
				}
			}
			ResultSet rs = pstm.executeQuery();
			Object backObj = null;
			if (rs.next()) {
				backObj = map.mapper(rs);
			}
			rs.close();
			pstm.close();
			conn.close();
//			if(backObj==null){
//				System.out.println("sssssssssss");
//			}
			return backObj;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public boolean execute(String sql, Object[] obj) {// 执行一条语句
		PreparedStatement pstm = null;
		try {
			
			if (conn == null || conn.isClosed()) {
				conn = BackConn.getConn();
			}
			
			pstm = conn.prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					
					pstm.setObject(i + 1, obj[i]);
					
				}
			}
			
			int iden = pstm.executeUpdate();
			
			if (iden >= 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("操作异常！ ");
			System.out.println("rollback");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		} finally {

			try {
				pstm.close();
				if (conn.getAutoCommit()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}
	
	
	
	public int executeCount(String sql,Object obj[]){
		
		try {
			conn = BackConn.getConn();
			PreparedStatement pstm=conn.prepareStatement(sql);
			if(obj!=null){
				
				int len=obj.length;
				for (int i = 0; i < len; i++) {
					
					pstm.setObject(i+1, obj[i]);
					
				}
			}
			
			ResultSet rs=pstm.executeQuery();
			Object backObj=new Object();
			int count=0;
			if(rs.next()){
				count=rs.getInt(1);
			}
			rs.close();
			pstm.close();
			conn.close();
			return count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
}

}
