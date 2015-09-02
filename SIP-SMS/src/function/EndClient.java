package function;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import Message.Message;
import Message.MsgType;

public class EndClient {
	
	//���client���������������ϵ���������
	private Socket socket;
		
	public EndClient(String targetIp ,int targetPort){
		try {
			this.socket=new Socket(targetIp,targetPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public  void sendString(String cmd,String name,String ip) {
		try{
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ArrayList<Object> obj = new ArrayList<Object>();
			obj.add(cmd);
			obj.add(name);
			obj.add(ip);
			Message outMes=new Message(MsgType.SENDFLOW,obj);//�����������
			oos.writeObject(outMes);
			oos.flush();
				
			oos.close();
			if(socket != null){
				System.out.println("�ر�StartClient�ͻ��˵�socket");
				socket.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}
		
}
