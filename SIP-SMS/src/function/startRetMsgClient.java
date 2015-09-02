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
			Message outMes=new Message(MsgType.SENDFLOW,obj);//�����������
			oos.writeObject(outMes);
			oos.flush();
			System.out.println("startRetMsgClient�Ѿ�д�����ݣ�"+obj);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				oos.close();
	        	//�˴�����Ҫsocket.close(),�������Socket reset�쳣
	            if(socket != null){
	            	System.out.println("�ر�startRetMsgClient�ͻ��˵�socket");
	              	socket.close();
	            }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

