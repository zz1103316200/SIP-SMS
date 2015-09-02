package function;

import java.beans.PropertyVetoException;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import db.DB;

public class OperationJSON {

	private DB db;
	
	public OperationJSON(){
		db = DB.getInstance();
		if(db.getConnection() != null){
			System.out.println("���ݿ����ӳɹ�");
		}
		else{
			System.out.println("���ݿ�����ʧ��");
		}
	}
	
	public ArrayList<Object> getParams(String type, String payload){
		ArrayList<Object> values = new ArrayList<Object>();
		try {
//			//�������ݿ�õ�type�����ݽṹ
//			String json = getJSON(type);
			//�õ�type�ĸ������ݵ�ֵ,�����ArrayList<Object>�ı�����
			JSONObject jsonobject  = new JSONObject(payload);
			Iterator ite = jsonobject.keys();
			String key = null;
			while(ite.hasNext()){
				key = (String) ite.next();
				values.add(jsonobject.get(key));
			}
			System.out.println(values);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return values;
	}
	
	public Object getObject(String type, ArrayList<Object> values){
		JSONObject TypeJson = new JSONObject();
		try{
			//����type��ʵ��,�����Ѿ��õ�type�����ݽṹjson
			String sql="select DataTypeContent from standard where DataType=?";
			String[] param = {type};
			ResultSet rs = db.query(sql, param);
			String content = "null";
			try {
				content = rs.getString("DataTypeContent");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JSONObject jsonobject = new JSONObject(content);
			Iterator ite = jsonobject.keys();
			String key = null;
			int i = 0;
			while(ite.hasNext()){
				key = (String) ite.next();
				System.out.println(key + "\t" + values.get(i));
				TypeJson.put(key, values.get(i++));
			}
			
			System.out.println(TypeJson.toString());
		}catch(JSONException e){
			e.printStackTrace();
		}
		return TypeJson;
	}

	
}
