package userManage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import Message.Message;
import Message.MsgType;

public class login {
	private static String server = "192.170.20.120";
	private static final int SEEPort = 5000;
	
	public String userLogin(ArrayList<String> loginInfo) throws UnknownHostException, IOException, ClassNotFoundException{
		Socket socket = new Socket(server, SEEPort);
		ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());
		Message outMes=new Message(MsgType.LOGIN,loginInfo);
		outputStream.writeObject(outMes);
		outputStream.flush();
				
		String result = null;
		ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
		Message inMes=(Message)inputStream.readObject();
		if(inMes.getType() == MsgType.LOGINRESULT){
			result =  (String) inMes.getBody();
		}
		socket.close();
		
		return result;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
