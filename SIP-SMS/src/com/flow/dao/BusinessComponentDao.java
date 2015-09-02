package com.flow.dao;

import java.util.List;

import com.flow.db.DbManager;
import com.flow.irowmapper.BusinessComponentMapper;
import com.flow.irowmapper.ProtocolMapper;
import com.flow.vo.BusinessComponentVo;

public class BusinessComponentDao implements IDAO{

	//��BusinessDestroy�е��ã�����Id��ɾ������ļ�¼
	public boolean delete(Object[] obj) {
		String sql="delete from businesscomponent where ServiceID=?";
		DbManager db=new DbManager();

		return db.execute(sql, obj);
	}

	public boolean update(Object[] obj) {
		String sql = "update businesscomponent set CompName=?,ServiceName=?,System=?,CallInfo=?,Description=?,Status=?,Properties=?,PictureURL=? where ServiceID=?";

		// ('username','password','path','name','organization','370781','2012-12-12',15,'��','java','13890000000','����','����','����','�����ѵĵط�','1');"
				DbManager db = new DbManager();
				
				return db.execute(sql, obj);
	}
	 public boolean updateStatus(Object[] obj){
		 String sql = "update businesscomponent set Status=? where ServiceID=?";
		 DbManager db = new DbManager();
		 return db.execute(sql, obj);
	 }
	 
	 public boolean updateEdit(Object[] obj){
		 String sql = "update businesscomponent set CompName=?,Description=? where ServiceID=?";
		 DbManager db = new DbManager();
		 return db.execute(sql, obj);
	 }
	public boolean add(Object[] obj) {
		String sql = "insert into businesscomponent"+
				"(CompName,ServiceName,ServiceID,System,CallInfo,Description,Status,Properties,PictureURL,InterfaceName,SubServiceName)"+
				"values"+
				"(?,?,?,?,?,?,?,?,?,?,?)";

		// ('username','password','path','name','organization','370781','2012-12-12',15,'��','java','13890000000','����','����','����','�����ѵĵط�','1');"
		DbManager db = new DbManager();
		
		return db.execute(sql, obj);
	}

	public List select(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	//��BusinessDestroy�е��ã���ȡ��Ϣ��ɾ��ͼƬ
	public Object selectById(String id) {
		String sql="select * from businesscomponent where ServiceID='"+id+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.selectById(sql,obj, new BusinessComponentMapper());
	}
	
	public Object selectByName(String id) {
		String sql="select * from businesscomponent where CompName='"+id+"'";
		// TODO Auto-generated method stub
		
		DbManager db=new DbManager();
		
		Object obj[]=new Object[]{};
		
		return db.selectById(sql,obj, new BusinessComponentMapper());
	}
	
	public List selectAll() {
		String sql="select * from businesscomponent";
		DbManager db=new DbManager();
		return db.select(sql, null, new BusinessComponentMapper());
		
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
