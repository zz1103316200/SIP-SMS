package com.flow.dao;

import java.util.List;

import com.flow.db.DbManager;
import com.flow.irowmapper.BusinessComponentMapper;
import com.flow.irowmapper.TransformerMapper;

public class TransformerDao implements IDAO{

	public boolean delete(Object[] obj) {
		String sql="delete from transformer where InputType=? and outputType=?";
		DbManager db=new DbManager();

		return db.execute(sql, obj);
	}

	public boolean update(Object[] obj) {
		String sql = "update transformer set TransfName=? Transfinfo=?,TransfDescription=? where InputType=? and OutputType=?";

		// ('username','password','path','name','organization','370781','2012-12-12',15,'男','java','13890000000','陕西','西安','户县','吃葡萄的地方','1');"
				DbManager db = new DbManager();
				
				return db.execute(sql, obj);
	}
	
	public boolean  updateStatus(Object[] obj) {
		String sql = "update transformer set TransfStatus=? where InputType=?and OutputType=?";
		DbManager db = new DbManager();
		return db.execute(sql, obj);
	}
	
	public boolean updateEdit(Object[] obj) {
		String sql = "update transformer set TransfName=? ,TransfDescription=? where InputType=? and OutputType=?";

		
				DbManager db = new DbManager();
				
				return db.execute(sql, obj);
	}
	public boolean add(Object[] obj) {
		String sql = "insert into transformer"+
				"(TransfName,InputType,OutputType,Transfinfo,TransfDescription,TransfStatus,FileURL)"+
				"values"+
				"(?,?,?,?,?,?,?)";
			System.out.println("sql:"+sql);
		// ('username','password','path','name','organization','370781','2012-12-12',15,'男','java','13890000000','陕西','西安','户县','吃葡萄的地方','1');"
		DbManager db = new DbManager();
		
		return db.execute(sql, obj);
	}

	public List select(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object selectById(String inputType,String outputType) {
		String sql="select * from transformer where InputType='"+inputType+"'and OutputType='"+outputType+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.selectById(sql,obj, new TransformerMapper());
	}
	 public List selectAll(){
		    String sql="select * from transformer";
			DbManager db=new DbManager();
			return db.select(sql, null, new TransformerMapper());
	 }
	
	 public List selectByInputType(String inputType){
		    String sql="select * from transformer where InputType='"+inputType+"'";
			DbManager db=new DbManager();
			return db.select(sql, null, new TransformerMapper());
	 }

	public int selectCount(Object[] obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List select(String sql, Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
