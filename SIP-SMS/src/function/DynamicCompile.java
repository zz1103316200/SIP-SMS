package function;

import java.io.File;
import java.util.ArrayList;

import com.sun.tools.javac.Main;

public class DynamicCompile {

	/**
	 * @param  .class�ļ�����λ�ã�.java ����λ��
	 * ��̬����Ŀ¼�µ�java�ļ�
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
//        	JOptionPane.showMessageDialog(null, "����ʧ��", "����", JOptionPane.WARNING_MESSAGE);
            System.out.println("û�гɹ�����!"+status);
        } 
        else {
        	//JOptionPane.showMessageDialog(null, "����ɹ�", "����", JOptionPane.WARNING_MESSAGE);
        	System.out.println("�ɹ�����!"+status); 
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

