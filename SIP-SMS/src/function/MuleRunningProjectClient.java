package function;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import Message.Message;
import Message.MsgType;

public class MuleRunningProjectClient {
	private Socket socket;
	
	public MuleRunningProjectClient(String targetIp ,int targetPort){
		try {
			this.socket=new Socket(targetIp,targetPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public  Vector<String> getInfo() {
		Vector<String> running = new Vector<String>();
		try{
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			String obj = "MuleRunningProject";
			Message outMes=new Message(MsgType.SENDFLOW,obj);//服务调用请求
			oos.writeObject(outMes);
			oos.flush();
			
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			Message msg = (Message)ois.readObject();
			if(msg.getType() == MsgType.SENDFLOW)
			{
				running = (Vector<String>)msg.getBody();		
			}
			
			ois.close();
			if(socket != null){
				socket.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return running;
	}
}

