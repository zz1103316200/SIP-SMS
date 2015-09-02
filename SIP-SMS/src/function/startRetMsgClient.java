package function;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import Message.Message;
import Message.MsgType;

public class startRetMsgClient {
	private Socket socket;
	private ObjectOutputStream oos;
	
	public startRetMsgClient(String targetIp ,int targetPort){
		try {
			this.socket=new Socket(targetIp,targetPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public  void sendString(String  str) {
		try{
			oos = new ObjectOutputStream(socket.getOutputStream());
			String obj = str;
			Message outMes=new Message(MsgType.SENDFLOW,obj);//服务调用请求
			oos.writeObject(outMes);
			oos.flush();
			System.out.println("startRetMsgClient已经写入数据："+obj);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				oos.close();
	        	//此处必须要socket.close(),否则出现Socket reset异常
	            if(socket != null){
	            	System.out.println("关闭startRetMsgClient客户端的socket");
	              	socket.close();
	            }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

