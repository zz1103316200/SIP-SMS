package com.flow.dao;

import java.util.List;

import com.flow.db.DbManager;
import com.flow.irowmapper.FlowTableMapper;

public class FlowTableDao implements IDAO{

	public boolean delete(Object[] obj) {
		String sql="delete from flowtable where FlowName=?";
		DbManager db=new DbManager();

		return db.execute(sql, obj);
	}

	public boolean update(Object[] obj) {
		String sql = "update flowtable set FlowDescription=?,FlowString=?,FlowStyle=? where FlowName=?";

		// ('username','password','path','name','organization','370781','2012-12-12',15,'男','java','13890000000','陕西','西安','户县','吃葡萄的地方','1');"
				DbManager db = new DbManager();
				
				return db.execute(sql, obj);
	}

	public boolean add(Object[] obj) {
		String sql = "insert into flowtable"+
				"(FlowName,FlowDescription,FlowString,FlowStyle,FlowStatus)"+
				"values"+
				"(?,?,?,?,?)";

		// ('username','password','path','name','organization','370781','2012-12-12',15,'男','java','13890000000','陕西','西安','户县','吃葡萄的地方','1');"
		DbManager db = new DbManager();
		
		return db.execute(sql, obj);
	}

	public List select(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object selectById(String id) {
		String sql="select * from flowtable where FlowName='"+id+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.selectById(sql,obj, new FlowTableMapper());
	}
	
	public Object selectByName(String id) {
		String sql="select * from flowtable where FlowName='"+id+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.selectById(sql,obj, new FlowTableMapper());
	}

	public List select(String sql, Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public List selectAll() {
		String sql="select * from flowtable";
		DbManager db=new DbManager();
		return db.select(sql, null, new FlowTableMapper());
		
	}
	public int selectCount(Object[] obj) {
		// TODO Auto-generated method stub
		return 0;
	}

}
