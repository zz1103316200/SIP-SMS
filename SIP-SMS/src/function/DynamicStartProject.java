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
		String ret = ProjName + "����ʧ��";
//		String root = System.getProperty("user.dir")+ File.separator +"tmp";
		String root = RunningPath+ File.separator +"tmp";
		System.out.println("root "+root);
		File_Folder ff = new File_Folder();
		String path = root + File.separator + ProjName;
		System.out.println("path "+path);
		
		//�޸�xdesb.xml�ļ�
		boolean flag_4 = modify(root);
		if(flag_4 == false){
			//ͬ�������������Դ��ڣ�ɾ��
			ff.deleteFolder(path);
		}
		
		//���ȸ�������������Ŀ¼
		boolean flag_1 = ff.createFolder(path);
	
		if(flag_1 == true){
			//����Ŀ¼�����ɹ�������������ļ�����
			//����Ŀ¼��������Ҫ��properties��config�ļ�	
			boolean flag_2 = createProperties(path);
			boolean flag_3 = createConfig(path);
			if(flag_2 && flag_3){
				//property��config��xdesb.xml�޸ĳɹ���
				//�ļ������ɹ���ǰ���²ſ�����������
				ret = start();
			}else{
				//�ع���ɾ���������������ļ�
				rollBack();
			}
		}else{
			//�ع���ɾ���������������ļ�
			rollBack();
		}
		
		return ret;
		
	}

	//����property�ļ�
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
			System.out.println("property�ļ�����ʧ��");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//����xml�����ļ�
	public boolean createConfig(String path){
		try {
			File file=new File(path+"\\"+ProjName+"-config.xml");
			FileWriter fw  = new FileWriter(file);
			fw.write(xmlContent);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			System.out.println("xml�����ļ�����ʧ��");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//�޸�xdesb.xml�ļ�
	public boolean modify(String root1){
		try {
			 SAXReader reader = new SAXReader();
			 String filePath = root1 + File.separator + "xdesb.xml";
	         File file = new File(filePath);
	         Element root=null;
	         Document doc=null;
	         if (file.exists()) {
	             try {
				       doc = reader.read(file);// ��ȡXML�ļ�
				        root = doc.getRootElement();
				        
				        //�鿴�������б����Ƿ��Ѿ����ڴ����Ƶ�����
				        Iterator<Element> ite = root.elementIterator();
				        while(ite.hasNext()){
				        	Element project = ite.next();
				        	String name = project.attributeValue("name");
				        	if(name.equals(ProjName)){
				        		System.out.println("ͬ���������������Ѵ���");
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
	         format.setEncoding("GBK");// ����XML�ļ��ı����ʽ
			 XMLWriter output = new XMLWriter(new FileWriter(filePath),format);
			 output.write( doc ); 
			 output.close();
			 
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return true;
	}

	//��������
	public String start(){
		String ret = "";
		long time = 0;
		try{
			RunJar rj = new RunJar();
			if(rj.getJarStat() == false){
				time = 30000;
	   			rj.run();
	   			
	       		//�ж��ǲ��ǵ�һ�����������ǣ���timeʱ���һЩ
	       		System.out.println("�ȴ�10s");
	       		try{
	       			Thread.sleep(time);
	       		}catch( InterruptedException e ){
	       			System.out.println("ͣ�ٴ��");
	       		}
			} 
			else{
				time = 0;
				Thread.sleep(time);
			}
			System.out.println("Client������Ϣ");
       		StartClient sc = new StartClient(SocketTools.targetIp,SocketTools.startPort);
       		sc.sendString("Start",ProjName,SocketTools.targetIp);
			
			StartRetMsgServer retMsg = new StartRetMsgServer(SocketTools.retPort);
			String message = retMsg.acceptString();
			System.out.println("���������ص���Ϣ��"+message);
			
			//��������״̬��Ϣ
			JOptionPane.showMessageDialog(null, message, "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
		
			System.out.println("��������״̬�������Ѿ�д��");
			
			ret = message;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ret;
	}
	
	//�ع�
	public void rollBack(){
		System.out.println("ɾ���ļ���");
		File_Folder ff = new File_Folder();
		ff.deleteFolder(TransfPath+File.separator+ProjName);
	}
	
}
