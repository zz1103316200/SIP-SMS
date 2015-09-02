package com.compile;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;
import function.DynamicCompile;

import Message.Message;
import Message.MsgType;

//这个文件只完成socket监听和编译的功能
public class CompileServer {

private int listenHeartPort;
private ServerSocket listenHeartSocket;
public CompileServer(int listenHeartPort)
{

	this.listenHeartPort = listenHeartPort;
	try {
		this.listenHeartSocket = new ServerSocket(this.listenHeartPort);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public void start()
{
	
	System.out.println("开始监听端口"+listenHeartPort);
	while(true){
		Socket socket;
		try {
			socket = this.listenHeartSocket.accept();
			new ResponseHeart(socket).start();;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
//响应线程
class ResponseHeart extends Thread
{
	Socket socket;
	public ResponseHeart(Socket socket)
	{
		this.socket = socket;
	}
	public void run()
	{
		try{
			//接受
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			Message msg =  (Message) ois.readObject();
			if(msg.getType().equals(MsgType.compileFile)){
			
				ArrayList<String> al = (ArrayList<String>) msg.getBody();
				String classpath = al.get(0);   //classpath
				String fileURL = al.get(1);     //javapath
				System.out.println(classpath);
				System.out.println(fileURL);
				
				 ArrayList<String> javapath = new ArrayList<String> ();
				 javapath.add(fileURL);
				//编译
				DynamicCompile d = new DynamicCompile(classpath,javapath);
				if(d.status == 0){
					System.out.println("编译成功");
					//响应
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(new Message(MsgType.compileFile,"true"));
					oos.flush();
				}else{
					System.out.println("编译失败");
					//响应
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(new Message(MsgType.compileFile,"false"));
					oos.flush();
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(!socket.isClosed())
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		
		}
		
	}
	
}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new CompileServer(7002).start();
	}

}
