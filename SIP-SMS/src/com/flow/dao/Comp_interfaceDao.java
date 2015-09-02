package com.flow.dao;

import java.util.List;

import com.flow.db.DbManager;
import com.flow.irowmapper.Comp_interfaceMapper;

public class Comp_interfaceDao implements IDAO{

	public boolean delete(Object[] obj) {
		String sql="delete from interfacecall where ServiceID=? and Interfacename=?";
		DbManager db=new DbManager();

		return db.execute(sql, obj);
	}

	public boolean update(Object[] obj) {
		String sql = "update interfacecall set InterfaceCallInfo=? where ServiceID=? and Interfacename=?";

		// ('username','password','path','name','organization','370781','2012-12-12',15,'男','java','13890000000','陕西','西安','户县','吃葡萄的地方','1');"
				DbManager db = new DbManager();
				
				return db.execute(sql, obj);
	}

	public boolean add(Object[] obj) {
		String sql = "insert into interfacecall"+
				"(ServiceID,Interfacename,InterfaceCallInfo)"+
				"values"+
				"(?,?,?)";

		// ('username','password','path','name','organization','370781','2012-12-12',15,'男','java','13890000000','陕西','西安','户县','吃葡萄的地方','1');"
		DbManager db = new DbManager();
		
		return db.execute(sql, obj);
	}

	public List select(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object selectById(String id) {
		String sql="select * from interfacecall where ServiceID='"+id+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.selectById(sql,obj, new Comp_interfaceMapper());
	}
	
	public Object selectByInter(String serviceName,String interfacename) {
		String sql="select * from interfacecall where ServiceName='"+serviceName+"' and InterfaceName='"+interfacename+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.selectById(sql,obj, new Comp_interfaceMapper());
	}

	public List select(String sql, Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public int selectCount(Object[] obj) {
		// TODO Auto-generated method stub
		return 0;
	}

}
