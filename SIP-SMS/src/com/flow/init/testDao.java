package com.flow.init;

import com.flow.dao.BusinessComponentDao;
import com.flow.dao.IDAO;
import com.flow.vo.BusinessComponentVo;

public class testDao {
	public void test(){
		IDAO bsDao = new BusinessComponentDao();
		//bsDao.add(new Object[]{"comp3","comp3","comp3","comp3","comp3","comp3","true","comp3","comp3"});
		BusinessComponentVo bv = (BusinessComponentVo)bsDao.selectById("comp2");
		System.out.println("status:"+bv.getStatus());
	}
}
