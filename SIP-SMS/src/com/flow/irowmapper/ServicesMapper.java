package com.flow.irowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.flow.vo.ServicesVo;

public class ServicesMapper implements IrowMapper{

	public Object mapper(ResultSet rs) {
		// TODO Auto-generated method stub
		ServicesVo vo = new ServicesVo();
		try{
				vo.setServiceID(rs.getString("ServiceID"));
				vo.setServiceName(rs.getString("ServiceName"));
				vo.setVersion(rs.getString("Version"));
				vo.setDescription(rs.getString("Description"));
				vo.setDeployer(rs.getString("Deployer"));
				vo.setParamAmount(rs.getInt("ParamAmount"));
				vo.setParameter(rs.getString("Parameter"));
				vo.setServiceType(rs.getString("ServiceType"));
				vo.setBusinessType(rs.getString("BusinessType"));
				vo.setCopyNumber(rs.getInt("CopyNumber"));
				vo.setMaxRunningNumber(rs.getInt("MaxRunningNumber"));
				vo.setFileName(rs.getString("FileName"));
				vo.setOwnerSystem(rs.getString("OwnerSystem"));
				vo.setKeyWords(rs.getString("KeyWords"));
				vo.setDevLanguage(rs.getString("DevLanguage"));
				vo.setDeveloper(rs.getString("Developer"));
				vo.setIcon(rs.getString("Icon"));
				vo.setPort(rs.getInt("Port"));
				vo.setReleaseTime(rs.getString("ReleaseTime"));
				return vo;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return vo;
	}

}
