import java.util.ArrayList;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import function.OperationJSON;


public class Data_2_To_Data_1 implements Callable{

	public Object onCall(MuleEventContext arg0) throws Exception {
		String payload = arg0.getMessageAsString();
		OperationJSON os = new OperationJSON();
		//得到参数中有用的数据
		ArrayList<Object> values = os.getParams("Data_2", payload);
		
		String str = "";
		for(Object object : values){
			str = str + object.toString() + " ";
		}
		System.out.println(str);
		
		//中间是处理参数的代码，由开发人员来书写
		//将type1的参数处理后放在ArrayList<Object>中作为type2的末班参数，由此来创建一个type2的对象
		ArrayList<Object> params = new ArrayList<Object>();
		
		params.add(str);
		
		//下面是创建type2的对象
		Object obj = os.getObject("Data_1", params);
		return obj;
	}
}
