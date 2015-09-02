package com.flow.dao;

import java.util.List;

import com.flow.db.DbManager;
import com.flow.irowmapper.BusinessComponentMapper;
import com.flow.irowmapper.ProtocolMapper;

public class ProtocolDao implements IDAO{

	public boolean delete(Object[] obj) {
		String sql="delete from protocol where ProtocolID=?";
		DbManager db=new DbManager();

		return db.execute(sql, obj);
	}

	public boolean update(Object[] obj) {
		String sql = "update protocol set ProtocolName=?,ProtocolDescription=?,ProtocolStatus=?,ProtocolProperties=?,ProtocolURL=? where ProtocolID=?";

		// ('username','password','path','name','organization','370781','2012-12-12',15,'男','java','13890000000','陕西','西安','户县','吃葡萄的地方','1');"
				DbManager db = new DbManager();
				
				return db.execute(sql, obj);
	}
	public boolean updateStatus(Object[] obj){
		String sql = "update protocol set ProtocolStatus=? where ProtocolID=?";
		DbManager db = new DbManager();
		return db.execute(sql, obj);
	}
	
	public boolean updateEdit(Object[] obj) {
		String sql = "update protocol set ProtocolName=?,ProtocolDescription=? where ProtocolID=?";
				DbManager db = new DbManager();
				return db.execute(sql, obj);
	}
	public boolean add(Object[] obj) {
		String sql = "insert into protocol"+
				"(ProtocolName,ProtocolDescription,ProtocolStatus,ProtocolProperties,ProtocolURL)"+
				"values"+
				"(?,?,?,?,?)";

		// ('username','password','path','name','organization','370781','2012-12-12',15,'男','java','13890000000','陕西','西安','户县','吃葡萄的地方','1');"
		DbManager db = new DbManager();
		
		return db.execute(sql, obj);
	}

	public List selectAll() {
		String sql="select * from protocol";
		DbManager db=new DbManager();
		return db.select(sql, null, new ProtocolMapper());
		
	}

	public Object selectById(String id) {
		String sql="select * from protocol where ProtocolID='"+id+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.selectById(sql,obj, new ProtocolMapper());
	}
	
	public Object selectByName(String id) {
		String sql="select * from protocol where ProtocolName='"+id+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.selectById(sql,obj, new ProtocolMapper());
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
