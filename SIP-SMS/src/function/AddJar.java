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
	
	private String jarFilePath;	//jarFilePath中存放的是Mule.jar的位置
	private String localPath;	//localPath中存放的是解压Mule.jar之后存放的位置
	private String mainFilePath;	//mainFilePath中存放的是main.jar打包到的位置
	private String mainLocalPath;	//mainLocalPath中存放的是main.jar解压之后的文件路径
	private String jarName;	    // xxx.jar
	private String projectName=null;
	private Vector<String> classNames=new Vector<String>();  //存放class的路径名
	private Vector<String> jarNames = new Vector<String>();	//存放的是jar包的路径名
	private String jarLibPath ;	//存放的是jar包放的位置
	
	//存放的是class文件的包名造成的路径
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
	
	//返回的是所有jar包的路径
	public void getJarNames(){
		String path = TransfPath + "\\" + projectName + "\\jar";
		File file = new File(path);
		if(file.exists()){
			File[] files = file.listFiles();
			System.out.println(files.length);
			if(files.length > 0){
				//工程中有jar包
				for(File f : files ){
					if(f.isFile()  && f.getName().endsWith(".jar")){
						jarNames.add(f.getAbsolutePath());
					}
				}
			}
		}
		//没有jar包对于jarNames这里不做处理，后面用到的话判断jarNames的size。
	}
	
	//返回的是所有class的路径
	public void getClassNames(){
		String path = null;
		File file = null;
		//如果是service，就将service\bin下的文件都添加到main.jar的目录下
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
		
		System.out.println("class文件的个数：" + classNames.size());
		for(String s : classNames){
			System.out.println(s);
		}
		
		System.out.println("jar文件的个数:" + jarNames.size());
		for(String s : jarNames){
			System.out.println(s);
		}
		
		//两次解压
		jieyaJar(jarFilePath,localPath);
		jieyaJar(mainFilePath,mainLocalPath);
		System.out.println("解压成功");
		
		//写入class文件，jar包
		boolean f = writeToMain();
		System.out.println(f);
		if(f == true){
			System.out.println("class文件添加成功");
			//添加bat文件
			addBatFile();
			System.out.println("bat文件添加成功");
			//打包文件成Jar，删除压缩后的文件
			dabaoJar();			
			System.out.println("打包成功");
			
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
		      // Add new file last ，添加新的文件到jar文件包中
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
				  
				  //20140919，此处存下class路径的内嵌路径，包名造成的路径
				  nestPath.add(deal);
				  
				  System.out.println(path);
				  System.out.println(deal);
				  
				  //如果deal的结果中含有目录项，即文件不是在根目录下（class有包）
				  if(deal.contains("\\")){
					  //打开写目录之前先创建目录
					  String createPath = mainLocalPath + File.separator + deal.substring(0,deal.lastIndexOf("\\"));
					  System.out.println("createPath"+createPath);
					  File file = new File(createPath);
					  if(!file.exists()){
						  file.mkdirs();
						  System.out.println("创建目录成功");
					  }
				  }
				  
				  String mainPath = mainLocalPath + File.separator + deal;
				  System.out.println("mainPath "+mainPath);
				  
				  //打开写目录之前先创建目录  
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
			  }//class文件到此写完

		   } catch (FileNotFoundException e) {
	    	    System.out.println("class文件写入出错");
	            return flag;
	     	} catch (IOException ex) { 
		    	  System.err.println("file operation error");
		    	  return flag;
	       } 
	     

		  //将jar文件写入Mule/lib目录下
		  //复制jar by JarFile 
	     try{
	    	//重点
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
	    	 
	    	 //calss文件和jar包全部写完再将flag置true
	    	 flag = true;
			  
	     }catch (FileNotFoundException e) {
	    	    System.out.println("1:jar包写入出错");
	            return flag;
	     	} catch (IOException e) {
	            System.out.println("2:jar包写入出错");
	            return flag;
	        }//jar包写入到此结束
	     
		 return flag;
	}
		
	public void dabaoJar(){
		try {
			//将文件打包，然后删除打包前的源文件
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
			//添加内如main的打包文件
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
			
			//添加外部jar的打包文件
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
			p=run.exec("cmd /c del "+path+"/q/a/s/f");	//  "/c"说明黑框闪现不见
			bufferedReader = new BufferedReader( new InputStreamReader(p.getInputStream()));  
			while ( (read=bufferedReader.readLine()) != null) {  
				System.out.println(read);
			}
			p.waitFor();
			
		} catch (IOException e) {
			System.out.println("删除主目录的指令出错");
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("处理出错");
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
