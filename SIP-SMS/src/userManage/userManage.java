package userManage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import conf.readConf;

import Message.Message;
import Message.MsgType;

public class userManage {
	
	static readConf rc = new readConf();
	
	private static final String confFilePath="C:\\Users\\Thaf\\Desktop\\confg.xml";
	private static final String server = rc.read(confFilePath, "SEEip");
	private static final int SEEPort = Integer.parseInt(rc.read(confFilePath, "SEEport"));
	
	/*
	 * 用户登录
	 */
	public ArrayList<String> userLogin(String[] loginInfo) throws UnknownHostException, IOException, ClassNotFoundException{
		Socket socket = new Socket(server, SEEPort);
		ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());
		Message outMes=new Message(MsgType.LOGIN,loginInfo);
		outputStream.writeObject(outMes);
		outputStream.flush();
				
		ArrayList<String> result = null;
		ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
		Message inMes=(Message)inputStream.readObject();
		if(inMes.getType() == MsgType.LOGIN){
			result =  (ArrayList<String>) inMes.getBody();
		}
		socket.close();
		
		return result;
	}
	
	/*
	 * 用户是否存在
	 */
	public Boolean userExist(String userName) throws IOException, ClassNotFoundException{
		Socket socket = new Socket(server, SEEPort);
		//向服务引擎发送Message消息
		ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());
		Message outMes=new Message(MsgType.USEREXIST,userName);
		outputStream.writeObject(outMes);
		outputStream.flush();
		
		//接收服务引擎返回的Message消息
		Boolean flag = null;
		ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
		Message inMes=(Message)inputStream.readObject();
		if(inMes.getType() == MsgType.USEREXISTRESULT){
			flag = (Boolean) inMes.getBody();
		}
		socket.close();
		return flag;
	}
	
	/*
	 * 用户注册
	 */
	public String userRegister(ArrayList<String> registerInfo) throws UnknownHostException, IOException, ClassNotFoundException{
		Socket socket = new Socket(server, SEEPort);
		ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());
		Message outMes=new Message(MsgType.REGISTER,registerInfo);
		outputStream.writeObject(outMes);
		outputStream.flush();
				
		String result = null;
		ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
		Message inMes=(Message)inputStream.readObject();
		if(inMes.getType() == MsgType.REGISTERRESULT){
			result =  (String) inMes.getBody();
		}
		socket.close();
		
		return result;
	}

	/*
	 * 修改用户权限
	 */
	public String userAuthority(ArrayList<String> AuthorityInfo) throws UnknownHostException, IOException, ClassNotFoundException{
		Socket socket = new Socket(server, SEEPort);
		ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());
		Message outMes=new Message(MsgType.AUTHORITY,AuthorityInfo);
		outputStream.writeObject(outMes);
		outputStream.flush();
				
		String result = null;
		ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
		Message inMes=(Message)inputStream.readObject();
		if(inMes.getType() == MsgType.AUTHORITYRESULT){
			result =  (String) inMes.getBody();
		}
		socket.close();
		
		return result;
	}
	/*
	 * 修改密码
	 */
	public String changePsw(ArrayList<String> userInfo) throws UnknownHostException, IOException, ClassNotFoundException{
		Socket socket = new Socket(server, SEEPort);
		ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());
		Message outMes=new Message(MsgType.CHANGEPSW,userInfo);
		outputStream.writeObject(outMes);
		outputStream.flush();
				
		String result = null;
		ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
		Message inMes=(Message)inputStream.readObject();
		if(inMes.getType() == MsgType.CHANGEPSWRESULT){
			result =  (String) inMes.getBody();
		}
		socket.close();
		
		return result;
	}
	/*
	 * 删除用户
	 */
	public String userDelete(String userName) throws UnknownHostException, IOException, ClassNotFoundException{
		Socket socket = new Socket(server, SEEPort);
		ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());
		Message outMes=new Message(MsgType.DELETEUSER,userName);
		outputStream.writeObject(outMes);
		outputStream.flush();
				
		String result = null;
		ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
		Message inMes=(Message)inputStream.readObject();
		if(inMes.getType() == MsgType.DELETEUSERRESULT){
			result =  (String) inMes.getBody();
		}
		socket.close();
		
		return result;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		
		userManage um = new userManage();
		
		
//		用户登录
//		String[] loginInfo = {"user","123456"};
//
//		ArrayList<String> result = um.userLogin(loginInfo);
//		for(String s : result)
//			System.out.println(s);
		
		

	}

}
