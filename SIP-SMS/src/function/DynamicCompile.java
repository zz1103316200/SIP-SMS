package function;

import java.io.File;
import java.util.ArrayList;

import com.sun.tools.javac.Main;

public class DynamicCompile {

	/**
	 * @param  .class文件所放位置，.java 所在位置
	 * 动态编译目录下的java文件
	 */
	public int status;
	
	public DynamicCompile(String classPath,ArrayList<String> strings){
		int len = strings.size();
		String[] args = new String[len+4];
		
		args[0]="-encoding";
		args[1]="UTF-8";
		args[2]="-d";
		args[3]=classPath;
		
		for(int i=4;i<args.length;i++){
			args[i] = strings.get(i-4);
		}
		
        status=Main.compile(args);
        
        if(status != 0) {
//        	JOptionPane.showMessageDialog(null, "编译失败", "警告", JOptionPane.WARNING_MESSAGE);
            System.out.println("没有成功编译!"+status);
        } 
        else {
        	//JOptionPane.showMessageDialog(null, "编译成功", "警告", JOptionPane.WARNING_MESSAGE);
        	System.out.println("成功编译!"+status); 
        }
	}
	
	
	public static void main(String[] args){
		String classpath = "transformer" + File.separator + "bin";
		String path1 = "transformer" + File.separator + "src" + File.separator + "Data_2_To_Data_1.java";
		ArrayList javapath = new ArrayList<String>();
		javapath.add(path1);
		DynamicCompile dc = new DynamicCompile(classpath, javapath);
		System.out.println(dc.status);
	}

}

