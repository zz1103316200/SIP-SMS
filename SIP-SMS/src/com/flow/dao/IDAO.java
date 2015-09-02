package com.flow.dao;

import java.util.List;

public interface IDAO {
	public boolean delete(Object obj[]);

	public boolean update(Object obj[]);

	public boolean add(Object obj[]);

	public List select(Object obj[]);
	
	public Object selectById(String id);
	
	public List select(String sql,Object obj[]);
	
	public int selectCount(Object obj[]);
}
