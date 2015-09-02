package function;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

import conf.readConf;


public class AddJar {
	
	static readConf rc = new readConf();
	public static String confFilePath = "c:/TransfConfg.xml";
	public static String TransfPath = rc.read(confFilePath, "TransfPath");
	
	private String jarFilePath;	//jarFilePath�д�ŵ���Mule.jar��λ��
	private String localPath;	//localPath�д�ŵ��ǽ�ѹMule.jar֮���ŵ�λ��
	private String mainFilePath;	//mainFilePath�д�ŵ���main.jar�������λ��
	private String mainLocalPath;	//mainLocalPath�д�ŵ���main.jar��ѹ֮����ļ�·��
	private String jarName;	    // xxx.jar
	private String projectName=null;
	private Vector<String> classNames=new Vector<String>();  //���class��·����
	private Vector<String> jarNames = new Vector<String>();	//��ŵ���jar����·����
	private String jarLibPath ;	//��ŵ���jar���ŵ�λ��
	
	//��ŵ���class�ļ��İ�����ɵ�·��
	private ArrayList<String> nestPath = new ArrayList<String>();
	
	public AddJar(String name,String JarName){
		projectName = name;
		getClassNames();
		getJarNames();
		jarName = JarName;
		jarFilePath=TransfPath+"\\"+jarName;
		localPath=TransfPath+"\\"+jarName.substring(0, jarName.length()-4);
		mainFilePath=localPath+"\\main\\main.jar";
		mainLocalPath=localPath+"\\main\\main";
		jarLibPath = localPath + "\\lib";
	}
	
	//���ص�������jar����·��
	public void getJarNames(){
		String path = TransfPath + "\\" + projectName + "\\jar";
		File file = new File(path);
		if(file.exists()){
			File[] files = file.listFiles();
			System.out.println(files.length);
			if(files.length > 0){
				//��������jar��
				for(File f : files ){
					if(f.isFile()  && f.getName().endsWith(".jar")){
						jarNames.add(f.getAbsolutePath());
					}
				}
			}
		}
		//û��jar������jarNames���ﲻ�����������õ��Ļ��ж�jarNames��size��
	}
	
	//���ص�������class��·��
	public void getClassNames(){
		String path = null;
		File file = null;
		//�����service���ͽ�service\bin�µ��ļ�����ӵ�main.jar��Ŀ¼��
		if( projectName.equals("service")){
			
			path =TransfPath+"\\"+projectName+"\\bin";
			file = new File(path);
			
			functionGetAbsPath(file);
		}else{
			
			path = TransfPath+"\\"+projectName+"\\bin";
			file=new File(path);
			
			functionGetAbsPath(file);
		}		
	}
	
	public void functionGetAbsPath(File file){
		File[] files = file.listFiles();
		System.out.println(files.length);   //1
		for(File f : files ){
			if(f.isFile()  && f.getName().endsWith(".class")){
				classNames.add(f.getAbsolutePath());
			}
			else if(f.isDirectory()){
				functionGetAbsPath(f);
			}
		}
	}
	
	public boolean invoke() throws IOException{
		boolean flag = false;
		
		System.out.println("class�ļ��ĸ�����" + classNames.size());
		for(String s : classNames){
			System.out.println(s);
		}
		
		System.out.println("jar�ļ��ĸ���:" + jarNames.size());
		for(String s : jarNames){
			System.out.println(s);
		}
		
		//���ν�ѹ
		jieyaJar(jarFilePath,localPath);
		jieyaJar(mainFilePath,mainLocalPath);
		System.out.println("��ѹ�ɹ�");
		
		//д��class�ļ���jar��
		boolean f = writeToMain();
		System.out.println(f);
		if(f == true){
			System.out.println("class�ļ���ӳɹ�");
			//���bat�ļ�
			addBatFile();
			System.out.println("bat�ļ���ӳɹ�");
			//����ļ���Jar��ɾ��ѹ������ļ�
			dabaoJar();			
			System.out.println("����ɹ�");
			
			flag = true;
		}else{
			flag = false;
		}
		
		return flag;
	}
	
	public void jieyaJar(String jarPath,String jieyaPath) throws IOException {
		
		JarFile jarFile=new JarFile(jarPath);
		
		int bufferSize=1024;
	    byte[] buffer = new byte[bufferSize];
	    JarInputStream jar = null;
	    JarEntry jarEntry = null;
	    try {
	        jar = new JarInputStream(new FileInputStream(jarFile.getName()));
	        while ((jarEntry = (JarEntry) jar.getNextEntry()) != null) {
	            File file = new File(jieyaPath, jarEntry.getName()).getAbsoluteFile();
	            if (jarEntry.isDirectory()) {
	                file.mkdirs();
	            } else {
	                File parent = file.getParentFile();
	                if (!parent.exists()) {
	                    parent.mkdirs();
	                }
	                file.createNewFile();
	                long size = jarEntry.getSize();
	                FileOutputStream fos = null;
	                try {
	                    fos = new FileOutputStream(file);
	                    int readLen = -1;
	                    while (size != 0) {
	                        readLen = jar.read(buffer);
	                        size -= readLen;
	                        if (readLen != -1) {
	                            fos.write(buffer, 0, readLen);
	                        }
	                    }
	                } finally {
	                    if (fos != null) {
	                        fos.close();
	                    }
	                }
	            }
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (jar != null) {
	                jar.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

	public boolean writeToMain(){
		boolean flag = false;
	     try { 
		      // Add new file last ������µ��ļ���jar�ļ�����
			  FileInputStream fis;
			  FileOutputStream fos;
			  
			  String pathPre = "";
			  if(projectName.equals("service")){
				  pathPre = TransfPath+"\\"+projectName+"\\bin";
			  }
			  else{
				  pathPre = TransfPath+"\\"+projectName+"\\bin";
			  }
			   
			  int bytesRead;
			  byte[] buffer=new byte[1024];
			  int i=0;
			  while(i<classNames.size()){
				  String path = classNames.get(i);
				  File tmp = new File(path);
				  fis = new FileInputStream(tmp);
				  
				  String deal = path.substring(pathPre.length());
				  
				  //20140919���˴�����class·������Ƕ·����������ɵ�·��
				  nestPath.add(deal);
				  
				  System.out.println(path);
				  System.out.println(deal);
				  
				  //���deal�Ľ���к���Ŀ¼����ļ������ڸ�Ŀ¼�£�class�а���
				  if(deal.contains("\\")){
					  //��дĿ¼֮ǰ�ȴ���Ŀ¼
					  String createPath = mainLocalPath + File.separator + deal.substring(0,deal.lastIndexOf("\\"));
					  System.out.println("createPath"+createPath);
					  File file = new File(createPath);
					  if(!file.exists()){
						  file.mkdirs();
						  System.out.println("����Ŀ¼�ɹ�");
					  }
				  }
				  
				  String mainPath = mainLocalPath + File.separator + deal;
				  System.out.println("mainPath "+mainPath);
				  
				  //��дĿ¼֮ǰ�ȴ���Ŀ¼  
				  fos = new FileOutputStream(mainPath);
				  try {
					  while ((bytesRead = fis.read(buffer)) != -1) { 
					        fos.write(buffer, 0, bytesRead); 
					  } 
				  } finally { 
				       fis.close(); 
				       fos.close();
				  }
				       
				  i++;
			  }//class�ļ�����д��

		   } catch (FileNotFoundException e) {
	    	    System.out.println("class�ļ�д�����");
	            return flag;
	     	} catch (IOException ex) { 
		    	  System.err.println("file operation error");
		    	  return flag;
	       } 
	     

		  //��jar�ļ�д��Mule/libĿ¼��
		  //����jar by JarFile 
	     try{
	    	//�ص�
	    	 if(jarNames.size() > 0){
		    	 for(String s : jarNames){
			    	 String src = s ;
			         JarFile jarFile = new JarFile(src);
			         Enumeration<JarEntry> jarEntrys = jarFile.entries();
			         
			         String des = jarLibPath + "\\" + src.substring(src.lastIndexOf("\\")+1);
			         
			         System.out.println(src+"\n"+ des);
			         
			         JarOutputStream jarOut = new JarOutputStream(new FileOutputStream(des));
			         byte[] bytes = new byte[1024];
			         
			         while(jarEntrys.hasMoreElements()){
			             JarEntry entryTemp = jarEntrys.nextElement();
			             jarOut.putNextEntry(entryTemp);
			             BufferedInputStream in = new BufferedInputStream(jarFile.getInputStream(entryTemp));
			             int len = in.read(bytes, 0, bytes.length);
			             while(len != -1){
			                 jarOut.write(bytes, 0, len);
			                 len = in.read(bytes, 0, bytes.length);
			             }
			             in.close();
			             jarOut.closeEntry();
			         }
			         
			         jarOut.finish();
			         jarOut.close();
			         jarFile.close();
					  
		    	 }
	    	 }
	    	 
	    	 //calss�ļ���jar��ȫ��д���ٽ�flag��true
	    	 flag = true;
			  
	     }catch (FileNotFoundException e) {
	    	    System.out.println("1:jar��д�����");
	            return flag;
	     	} catch (IOException e) {
	            System.out.println("2:jar��д�����");
	            return flag;
	        }//jar��д�뵽�˽���
	     
		 return flag;
	}
		
	public void dabaoJar(){
		try {
			//���ļ������Ȼ��ɾ�����ǰ��Դ�ļ�
			Process process = Runtime.getRuntime().exec(mainLocalPath+"\\dabao1.bat");
			process.getOutputStream().close(); 
			LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println("line1"+line);
			}
			br.close();
			
			File_Folder ff =new File_Folder();
			ff.deleteFolder(mainLocalPath);
			
			process = Runtime.getRuntime().exec(localPath+"\\dabao2.bat");
			process.getOutputStream().close(); 
			br = new LineNumberReader(new InputStreamReader(process.getInputStream()));
			line = null;
			while ((line = br.readLine()) != null) {
				System.out.println("line2"+line);
			}
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
		
	public void addBatFile(){
		try{
			//�������main�Ĵ���ļ�
			File file=new File(mainLocalPath+"\\dabao1.bat");
			DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new BufferedOutputStream(new FileOutputStream(file))));
	        dos.writeBytes("D:");
	        dos.writeBytes("\n\r");
	        dos.writeBytes("cd tmp");
	        dos.writeBytes("\n\r");
			dos.writeBytes("cd "+jarName.substring(0, jarName.length()-4)+"\\main\\main");
	        dos.writeBytes("\n\r");
	        
	        String string="jar cfm ../main.jar "+TransfPath+"/manifest/manifest1.mf"+" *";
			dos.writeBytes(string);
	        dos.flush();
	        dos.close();
			
			//����ⲿjar�Ĵ���ļ�
			file=new File(localPath+"\\dabao2.bat");
			dos = new DataOutputStream(new BufferedOutputStream(new BufferedOutputStream(new FileOutputStream(file))));
			dos.writeBytes("D:");
	        dos.writeBytes("\n\r");
	        dos.writeBytes("cd tmp");
	        dos.writeBytes("\n\r");
	        dos.writeBytes("cd "+jarName.substring(0, jarName.length()-4));
	        dos.writeBytes("\n\r");
	        
	        string="jar cfm ../"+jarName+" ../manifest/manifest2.mf"+" *";
			dos.writeBytes(string);
	        dos.flush();
	        dos.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
		
	public void deleteRootFile(String path){
		Runtime run=Runtime.getRuntime();
		Process p;
		String read;
		BufferedReader bufferedReader;
		try {
			p=run.exec("cmd /c del "+path+"/q/a/s/f");	//  "/c"˵���ڿ����ֲ���
			bufferedReader = new BufferedReader( new InputStreamReader(p.getInputStream()));  
			while ( (read=bufferedReader.readLine()) != null) {  
				System.out.println(read);
			}
			p.waitFor();
			
		} catch (IOException e) {
			System.out.println("ɾ����Ŀ¼��ָ�����");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("�������");
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getNestPath(){
		return nestPath;
	}
		
	public static void main(String[] args){
		AddJar jar=new AddJar("id1","Mule.jar");
    	try {
			jar.invoke();
		} catch (IOException e) {
			System.out.println("WWWWWWWWWWWWWW");
			e.printStackTrace();
		}
    	
    	System.exit(0);
	}

}
