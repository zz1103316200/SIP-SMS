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
	 * �û���¼
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
	 * �û��Ƿ����
	 */
	public Boolean userExist(String userName) throws IOException, ClassNotFoundException{
		Socket socket = new Socket(server, SEEPort);
		//��������淢��Message��Ϣ
		ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());
		Message outMes=new Message(MsgType.USEREXIST,userName);
		outputStream.writeObject(outMes);
		outputStream.flush();
		
		//���շ������淵�ص�Message��Ϣ
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
	 * �û�ע��
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
	 * �޸��û�Ȩ��
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
	 * �޸�����
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
	 * ɾ���û�
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
		
		
//		�û���¼
//		String[] loginInfo = {"user","123456"};
//
//		ArrayList<String> result = um.userLogin(loginInfo);
//		for(String s : result)
//			System.out.println(s);
		
		

	}

}
