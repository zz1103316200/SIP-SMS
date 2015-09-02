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
		String message = "start工程没有返回信息";
		try{
			//这里会阻塞，直到获得请求才往下进行
			socket = serverSocket.accept();
			System.out.println("socket 连接成功");
			ois = new ObjectInputStream(socket.getInputStream());
			Message msg = (Message)ois.readObject();
			if(msg.getType() == MsgType.SENDFLOW)
			{
				message = (String)msg.getBody();
				System.out.println("StartRetMsgServer接收到的数据："+message);
			}
		}catch (Exception e) {
				try {
					System.out.println("关闭StartRetMsgServer客户端的serverSocket");
					serverSocket.close();
				} catch (IOException e1) {
				}
        }finally{
			try {
				ois.close();
				if(serverSocket != null){
					System.out.println("关闭StartRetMsgServer客户端的serverSocket");
					serverSocket.close();
				}
	        	//此处必须要socket.close(),否则出现Socket reset异常
	            if(socket != null){
	            	System.out.println("关闭StartRetMsgServer客户端的socket");
	              	socket.close();
	            }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		 
		return message;
	}
	
}
