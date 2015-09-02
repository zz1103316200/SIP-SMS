package function;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Message.Message;
import Message.MsgType;

public class StartRetMsgServer {
	private ServerSocket serverSocket = null;	
	private Socket socket = null;
	private ObjectInputStream ois = null;
	
	public StartRetMsgServer(int targetPort){
		try {
			serverSocket = new ServerSocket(targetPort);		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String acceptString() {
		String message = "start����û�з�����Ϣ";
		try{
			//�����������ֱ�������������½���
			socket = serverSocket.accept();
			System.out.println("socket ���ӳɹ�");
			ois = new ObjectInputStream(socket.getInputStream());
			Message msg = (Message)ois.readObject();
			if(msg.getType() == MsgType.SENDFLOW)
			{
				message = (String)msg.getBody();
				System.out.println("StartRetMsgServer���յ������ݣ�"+message);
			}
		}catch (Exception e) {
				try {
					System.out.println("�ر�StartRetMsgServer�ͻ��˵�serverSocket");
					serverSocket.close();
				} catch (IOException e1) {
				}
        }finally{
			try {
				ois.close();
				if(serverSocket != null){
					System.out.println("�ر�StartRetMsgServer�ͻ��˵�serverSocket");
					serverSocket.close();
				}
	        	//�˴�����Ҫsocket.close(),�������Socket reset�쳣
	            if(socket != null){
	            	System.out.println("�ر�StartRetMsgServer�ͻ��˵�socket");
	              	socket.close();
	            }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		 
		return message;
	}
	
}
