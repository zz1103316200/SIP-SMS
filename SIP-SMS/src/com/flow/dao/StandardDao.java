package com.flow.dao;

import java.util.List;

import com.flow.db.DbManager;
import com.flow.irowmapper.BusinessComponentMapper;
import com.flow.irowmapper.StandardMapper;

public class StandardDao implements IDAO{

	public boolean delete(Object[] obj) {
		String sql="delete from standard where DataType=?";
		DbManager db=new DbManager();

		return db.execute(sql, obj);
	}

	public boolean update(Object[] obj) {
		String sql = "update standard set DataTypeContent=?,DataTypeDescription=?,DataTypeStatus=? where DataType=?";

		// ('username','password','path','name','organization','370781','2012-12-12',15,'男','java','13890000000','陕西','西安','户县','吃葡萄的地方','1');"
				DbManager db = new DbManager();
				
				return db.execute(sql, obj);
	}

	public boolean add(Object[] obj) {
		String sql = "insert into standard"+
				"(DataType,DataTypeContent,DataTypeDescription,DataTypeStatus)"+
				"values"+
				"(?,?,?,?)";

		// ('username','password','path','name','organization','370781','2012-12-12',15,'男','java','13890000000','陕西','西安','户县','吃葡萄的地方','1');"
		DbManager db = new DbManager();
		
		return db.execute(sql, obj);
	}

	public List select(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object selectById(String id) {
		String sql="select * from standard where DataType='"+id+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.selectById(sql,obj, new StandardMapper());
	}

	public List selectAll() {
		// TODO Auto-generated method stub
		String sql = "select * from standard";
		DbManager db = new DbManager();
		Object obj[] = new Object[]{};
		return db.select(sql, obj, new StandardMapper());
		
	}

	public int selectCount(Object[] obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List select(String sql, Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
