package com.flow.dao;

import java.util.List;

import com.flow.db.DbManager;
import com.flow.irowmapper.BusinessComponentMapper;
import com.flow.irowmapper.InterfaceMapper;

public class InterfaceDao implements IDAO{

	public boolean delete(Object[] obj) {
		String sql="delete from interface where ServiceID=? and Interfacename=?";
		DbManager db=new DbManager();

		return db.execute(sql, obj);
	}

	public boolean update(Object[] obj) {
		String sql = "update interface set ServiceName=?,InputType=?,OutputType=?,InterfaceDescription=? where ServiceID=? and Interfacename=?";

		// ('username','password','path','name','organization','370781','2012-12-12',15,'男','java','13890000000','陕西','西安','户县','吃葡萄的地方','1');"
				DbManager db = new DbManager();
				
				return db.execute(sql, obj);
	}

	public boolean add(Object[] obj) {
		String sql = "insert into interface"+
				"(ServiceID,ServiceName,Interfacename,InputType,OutputType,InterfaceDescription)"+
				"values"+
				"(?,?,?,?,?,?)";

		// ('username','password','path','name','organization','370781','2012-12-12',15,'男','java','13890000000','陕西','西安','户县','吃葡萄的地方','1');"
		DbManager db = new DbManager();
		
		return db.execute(sql, obj);
	}

	public List select(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object selectById(String id) {
		String sql="select * from interface where ServiceID='"+id+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.selectById(sql,obj, new InterfaceMapper());
	}
	
	public Object selectByInter(String serviceName,String interfaceName) {
		String sql="select * from interface where SubServiceName='"+serviceName+"' and InterfaceName='"+interfaceName+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.selectById(sql,obj, new InterfaceMapper());
	}
	
	public List selectByName(String serviceName) {
		String sql="select * from interface where SubServiceName='"+serviceName+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.select(sql,obj, new InterfaceMapper());
	}

	public List select(String sql, Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List selectAll() {
		String sql="select * from interface";
		DbManager db=new DbManager();
		return db.select(sql, null, new InterfaceMapper());
		
	}

	public int selectCount(Object[] obj) {
		// TODO Auto-generated method stub
		return 0;
	}

}
