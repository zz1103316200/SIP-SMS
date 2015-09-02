package function;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import conf.readConf;

public class DynamicStartProject {
	static readConf rc = new readConf();
	public static String confFilePath = "c:/TransfConfg.xml";
	public static String TransfPath = rc.read(confFilePath, "TransfPath");
	public static String RunningPath = rc.read(confFilePath, "RunningPath");
	
	
	private String ProjName;
	private String desc;
	private String xmlContent;
	
	public DynamicStartProject(String ProjName, String desc, String xmlContent){
		this.ProjName = ProjName;
		this.xmlContent = xmlContent;
		this.desc = desc;
	}
	
	public String invoke(){
		String ret = ProjName + "启动失败";
//		String root = System.getProperty("user.dir")+ File.separator +"tmp";
		String root = RunningPath+ File.separator +"tmp";
		System.out.println("root "+root);
		File_Folder ff = new File_Folder();
		String path = root + File.separator + ProjName;
		System.out.println("path "+path);
		
		//修改xdesb.xml文件
		boolean flag_4 = modify(root);
		if(flag_4 == false){
			//同流程名的流程以存在，删除
			ff.deleteFolder(path);
		}
		
		//首先根据流程名创建目录
		boolean flag_1 = ff.createFolder(path);
	
		if(flag_1 == true){
			//流程目录创建成功，继续下面的文件创建
			//流程目录下生成需要的properties和config文件	
			boolean flag_2 = createProperties(path);
			boolean flag_3 = createConfig(path);
			if(flag_2 && flag_3){
				//property、config、xdesb.xml修改成功，
				//文件创建成功的前提下才可以启动流程
				ret = start();
			}else{
				//回滚，删除上面所创建的文件
				rollBack();
			}
		}else{
			//回滚，删除上面所创建的文件
			rollBack();
		}
		
		return ret;
		
	}

	//生成property文件
	public boolean createProperties(String path){
		String content="log4j.rootLogger = INFO,file\n"+"log4j.appender.file = org.apache.log4j.FileAppender\n"+
				"log4j.appender.file.File = "+ProjName+"/"+ProjName+".log\n"+
				"log4j.appender.file.Append = false\n"+
				"log4j.appender.file.Threshold = DEBUG\n"+
				"log4j.appender.file.layout = org.apache.log4j.PatternLayout\n"+
				"log4j.appender.file.layout.ConversionPattern =  %-5p %d [%t] %c: %m%n";

		try {
			File file=new File(path+"\\"+ProjName+"-property.properties");
			FileWriter fw  = new FileWriter(file);
			fw.write(content);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			System.out.println("property文件创建失败");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//生成xml配置文件
	public boolean createConfig(String path){
		try {
			File file=new File(path+"\\"+ProjName+"-config.xml");
			FileWriter fw  = new FileWriter(file);
			fw.write(xmlContent);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			System.out.println("xml配置文件创建失败");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//修改xdesb.xml文件
	public boolean modify(String root1){
		try {
			 SAXReader reader = new SAXReader();
			 String filePath = root1 + File.separator + "xdesb.xml";
	         File file = new File(filePath);
	         Element root=null;
	         Document doc=null;
	         if (file.exists()) {
	             try {
				       doc = reader.read(file);// 读取XML文件
				        root = doc.getRootElement();
				        
				        //查看此流程列表中是否已经存在此名称的流程
				        Iterator<Element> ite = root.elementIterator();
				        while(ite.hasNext()){
				        	Element project = ite.next();
				        	String name = project.attributeValue("name");
				        	if(name.equals(ProjName)){
				        		System.out.println("同样流程名的流程已存在");
				        		return false;
				        	}
				        }
			    } catch (DocumentException e) {
			    	e.printStackTrace();
			    }
			 }else{
			  doc=DocumentHelper.createDocument();
			   root = doc.addElement("all");
			 }
			 Element project = root.addElement("project");
			 project.addAttribute("name", ProjName);
			 if(desc.equals(null)){
				 project.addAttribute("xml", "0");
			 }
			 else{
				 project.addAttribute("xml", desc);
			 }
			 
			 OutputFormat format = OutputFormat.createPrettyPrint();
	         format.setEncoding("GBK");// 设置XML文件的编码格式
			 XMLWriter output = new XMLWriter(new FileWriter(filePath),format);
			 output.write( doc ); 
			 output.close();
			 
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return true;
	}

	//启动流程
	public String start(){
		String ret = "";
		long time = 0;
		try{
			RunJar rj = new RunJar();
			if(rj.getJarStat() == false){
				time = 30000;
	   			rj.run();
	   			
	       		//判断是不是第一次启动，若是，则time时间久一些
	       		System.out.println("等待10s");
	       		try{
	       			Thread.sleep(time);
	       		}catch( InterruptedException e ){
	       			System.out.println("停顿打断");
	       		}
			} 
			else{
				time = 0;
				Thread.sleep(time);
			}
			System.out.println("Client发送信息");
       		StartClient sc = new StartClient(SocketTools.targetIp,SocketTools.startPort);
       		sc.sendString("Start",ProjName,SocketTools.targetIp);
			
			StartRetMsgServer retMsg = new StartRetMsgServer(SocketTools.retPort);
			String message = retMsg.acceptString();
			System.out.println("服务器返回的消息："+message);
			
			//发出启动状态信息
			JOptionPane.showMessageDialog(null, message, "消息", JOptionPane.INFORMATION_MESSAGE);
		
			System.out.println("工程启动状态的数据已经写入");
			
			ret = message;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ret;
	}
	
	//回滚
	public void rollBack(){
		System.out.println("删除文件夹");
		File_Folder ff = new File_Folder();
		ff.deleteFolder(TransfPath+File.separator+ProjName);
	}
	
}
