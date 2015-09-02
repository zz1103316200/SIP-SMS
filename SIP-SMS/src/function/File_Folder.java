package function;

import java.io.File;
import java.io.IOException;

public class File_Folder {
	
	public boolean createFolder(String path){
		boolean ret = true;
		File file=new File(path);
		if(!file.exists()){
			boolean flag = file.mkdirs();
			if(flag){
				System.out.println("Ŀ¼�ɹ��崴��");
				ret = true;
			}
			else{ 
				System.out.println("Ŀ¼����ʧ��");
				ret = false;
			}
		}
		else{
			System.out.println("���ļ��Ѿ�����");
			ret = true;
		}
		return ret;
		
	}
	
	public void deleteFolder(String path){
		//ɾ�����ļ����ڵ���������
		deleteAllFile(path);
		File file=new File(path);
		//ɾ�����ļ���
		file.delete();
		System.out.println("ɾ���ļ��гɹ�");
	
	}
	
	public void deleteAllFile(String path){
		File file=new File(path);
		if(!file.exists()){
			System.out.println("�ļ��в�����");
			return;
		}
		if(!file.isDirectory()){
			System.out.println("�ļ��в���Ŀ¼");
			return;
		}
		
		String[] tempList =file.list();
		File temp=null;
		for(int i=0;i<tempList.length;i++){
			if(path.endsWith(File.separator)){
				temp=new File(path+tempList[i]);
			}
			else{
				temp=new File(path+File.separator+tempList[i]);
			}
			if(temp.isFile()){
				temp.delete();
			}
			if(temp.isDirectory()){
				//��ɾ���ļ���������ļ�
				deleteAllFile(path+"/"+tempList[i]);
				//��ɾ�����ļ���
				deleteFolder(path+"/"+tempList[i]);
			}
		}
	}
}
