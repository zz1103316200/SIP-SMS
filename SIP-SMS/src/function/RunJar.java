package function;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.Ps;

import conf.readConf;

public class RunJar {
	static readConf rc = new readConf();
	public static String confFilePath = "c:/TransfConfg.xml";
	public static String TransfPath = rc.read(confFilePath, "TransfPath");
	public static String RunningPath = rc.read(confFilePath, "RunningPath");
	
	private Process p;
	private Sigar sigar = new Sigar();
	private long pid = 0;
	
	public void run(){
		//java代码启动Mule.jar
		//Mule.jar在启动的时候start服务器线程已经在等待
		Runtime run=Runtime.getRuntime();
		String cmd ="cmd.exe /k start " +"Mule.jar";
		String desDir = "D:\\tmp";
		try {
			p=run.exec(cmd,null, new File(desDir));
//			String path = RunningPath+File.separator+"tmp"+File.separator+"start.bat";
//			System.out.println(path);
//			p=run.exec(path);
			
			/*String[] cmdarray = new String[4];
			cmdarray[0] = "cmd.exe";
			cmdarray[1] = "D:";
			cmdarray[2] = "cd";
			cmdarray[3] = "tmp";
			run.exec(cmdarray);
			
			String[] cmdarray1 = new String[3];
			cmdarray1[0] = "java";
			cmdarray1[1] = "-jar";
			cmdarray1[2] = "Mule.jar";
			p=run.exec(cmdarray1);*/
		
			p.getOutputStream().close();
			p.getErrorStream().close();
			p.getInputStream().close();
//			System.out.println(p.getErrorStream());
//			System.out.println(p.getInputStream());
//			System.out.println(p.getOutputStream());

		} catch (IOException e) {
			System.out.println("启动java失败");
			e.printStackTrace();
		} 
		System.out.println("启动jar文件");
	}

	public boolean getJarStat(){
		boolean flag = false;
		Ps ps = new Ps();		//show Process Status
	     try {
	         long[] pids = sigar.getProcList();
	         for(long pid : pids){
	             List<String> list = ps.getInfo(sigar, pid);
	             if(list.get(8).endsWith("com.simontuffs.onejar.Boot")){
	            	 //进程的名字是：java:com.simontuffs.onejar.Boot 或 javaw:com.simontuffs.onejar.Boot
	                 flag = true;
	                 this.pid = Long.parseLong(list.get(0));
	    			 break;
	              }

	          }
	    } catch (SigarException e) {
	            e.printStackTrace();
	    } 
	     
	    return flag;
	}
	
	public void destroyJarBySigar(){
		try {
			Runtime.getRuntime().exec("cmd.exe /c taskkill /f /pid " + this.pid);
			System.out.println("kill progress "+this.pid);
		} catch (IOException e) {
			e.printStackTrace();
		}     
	}
	
	public static void main(String[] args){
		RunJar rj = new RunJar();
		rj.run();
	}
	
}
