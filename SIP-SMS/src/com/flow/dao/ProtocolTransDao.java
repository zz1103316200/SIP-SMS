package com.flow.dao;

import java.util.List;

import com.flow.db.DbManager;
import com.flow.irowmapper.ProtocolMapper;
import com.flow.irowmapper.ProtocolTransMapper;

public class ProtocolTransDao {
	public boolean delete(Object[] obj) {
		String sql="delete from protocoltrans where ProtocolTransName=?";
		DbManager db=new DbManager();

		return db.execute(sql, obj);
	}

	public boolean update(Object[] obj) {
		String sql = "update protocoltrans set InputProtocol=?,OutputProtocol=?,ProtocolTransInfo=?,ProtocolTransStatus=?,ProtocolTransDesc=? where ProtocolTransName=?";

		// ('username','password','path','name','organization','370781','2012-12-12',15,'男','java','13890000000','陕西','西安','户县','吃葡萄的地方','1');"
				DbManager db = new DbManager();
				
				return db.execute(sql, obj);
	}
	public boolean updateStatus(Object[] obj){
		String sql = "update protocoltrans set ProtocolTransStatus=? where ProtocolTransName=?";
		DbManager db = new DbManager();
		return db.execute(sql, obj);
	}
	
	public boolean updateEdit(Object[] obj) {
		String sql = "update protocoltrans set ProtocolName=?,ProtocolDescription=? where ProtocolTransName=?";
				DbManager db = new DbManager();
				return db.execute(sql, obj);
	}
	public boolean add(Object[] obj) {
		String sql = "insert into protocoltrans"+
				"(ProtocolTransName,InputProtocol,OutputProtocol,ProtocolTransInfo,ProtocolTransStatus,ProtocolTransDesc)"+
				"values"+
				"(?,?,?,?,?,?)";

		// ('username','password','path','name','organization','370781','2012-12-12',15,'男','java','13890000000','陕西','西安','户县','吃葡萄的地方','1');"
		DbManager db = new DbManager();
		
		return db.execute(sql, obj);
	}

	public List selectAll() {
		String sql="select * from protocoltrans";
		DbManager db=new DbManager();
		return db.select(sql, null, new ProtocolMapper());
		
	}

	public Object selectById(String id) {
		String sql="select * from protocoltrans where ProtocolTransName='"+id+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.selectById(sql,obj, new ProtocolMapper());
	}
	
	public Object selectByProtocol(String input,String output) {
		String sql="select * from protocoltrans where InputProtocol='"+input+"' and OutputProtocol='"+output+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.selectById(sql,obj, new ProtocolTransMapper());
	}

	public List select(String sql, Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public int selectCount(Object[] obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List select(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
