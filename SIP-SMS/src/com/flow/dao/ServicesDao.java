package com.flow.dao;

import java.util.List;

import com.flow.db.DbManager;
import com.flow.irowmapper.BusinessComponentMapper;
import com.flow.irowmapper.ServicesMapper;
import com.flow.irowmapper.SubInterfaceMapper;
import com.flow.irowmapper.SubServiceMapper;

public class ServicesDao implements IDAO {

	public boolean delete(Object[] obj) {
		// TODO Auto-generated method stub
		String sql="delete from services where ServiceID=?";
		DbManager db=new DbManager();

		return db.execute(sql, obj);
	}

	public boolean update(Object[] obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean add(Object[] obj) {
		// TODO Auto-generated method stub
		return false;
	}

	//在BusinessList中调用，在添加页面的下拉框中展示库里的一级服务
	public List selectOne() {
		// TODO Auto-generated method stub
		String sql="select * from services";
		DbManager db=new DbManager();
		return db.select(sql, null, new ServicesMapper());
	}
	
	//在BusinessList中调用，在添加页面的下拉框中展示库里的二级服务
	public List selectTwo(String id) {
		// TODO Auto-generated method stub
		String sql="select * from subservice where ServiceID='"+id+"'";
		DbManager db=new DbManager();
		return db.select(sql, null, new SubServiceMapper());
	}
	
	//在BusinessList中调用，在添加页面的下拉框中展示库里的二级服务
	public List selectThree(String id,String name) {
		// TODO Auto-generated method stub
		String sql="select * from interface where ServiceID='"+id+"' and SubServiceName='"+name+"'";
		DbManager db=new DbManager();
		return db.select(sql, null, new SubInterfaceMapper());
	}

	//在BusinessAdd中调用，
	public Object selectById(String id) {
		// TODO Auto-generated method stub
		String sql="select * from services where ServiceID='"+id+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.selectById(sql,obj, new ServicesMapper());
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
