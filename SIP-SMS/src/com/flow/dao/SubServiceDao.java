package com.flow.dao;

import java.util.List;

import com.flow.db.DbManager;
import com.flow.irowmapper.InterfaceMapper;
import com.flow.irowmapper.SubServiceMapper;

public class SubServiceDao implements IDAO{
	
	public boolean delete(Object[] obj) {
		String sql="delete from subservice where ServiceID=?";
		DbManager db=new DbManager();

		return db.execute(sql, obj);
	}

	public boolean update(Object[] obj) {
		String sql = "update subservice set ServiceID=?,SubServiceName=?,Icon=?,Keyword=?,Description=?,Metadata=?,Wsdl=?,URL=? where SubServiceID=?";

		// ('username','password','path','name','organization','370781','2012-12-12',15,'男','java','13890000000','陕西','西安','户县','吃葡萄的地方','1');"
				DbManager db = new DbManager();
				
				return db.execute(sql, obj);
	}

	public boolean add(Object[] obj) {
		String sql = "insert into subservice"+
				"(SubServiceID,ServiceID,SubServiceName,Icon,Keyword,Description,Metadata,Wsdl,URL)"+
				"values"+
				"(?,?,?,?,?,?,?,?,?)";

		// ('username','password','path','name','organization','370781','2012-12-12',15,'男','java','13890000000','陕西','西安','户县','吃葡萄的地方','1');"
		DbManager db = new DbManager();
		
		return db.execute(sql, obj);
	}

	public List select(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object selectById(String id) {
		String sql="select * from subservice where SubServiceID='"+id+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.selectById(sql,obj, new SubServiceMapper());
	}
	
	public Object selectByServiceName(String id) {
		String sql="select * from subservice where SubServiceName='"+id+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.selectById(sql,obj, new SubServiceMapper());
	}
	
	public List selectByName(String id) {
		String sql="select * from subservice where SubServiceName='"+id+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.select(sql,obj, new SubServiceMapper());
	}

	public List select(String sql, Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List selectAll() {
		String sql="select * from subservice";
		DbManager db=new DbManager();
		return db.select(sql, null, new SubServiceMapper());
		
	}

	public int selectCount(Object[] obj) {
		// TODO Auto-generated method stub
		return 0;
	}

}
