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
				System.out.println("目录成功插创建");
				ret = true;
			}
			else{ 
				System.out.println("目录创建失败");
				ret = false;
			}
		}
		else{
			System.out.println("此文件已经存在");
			ret = true;
		}
		return ret;
		
	}
	
	public void deleteFolder(String path){
		//删除完文件夹内的所有内容
		deleteAllFile(path);
		File file=new File(path);
		//删除空文件夹
		file.delete();
		System.out.println("删除文件夹成功");
	
	}
	
	public void deleteAllFile(String path){
		File file=new File(path);
		if(!file.exists()){
			System.out.println("文件夹不存在");
			return;
		}
		if(!file.isDirectory()){
			System.out.println("文件夹不是目录");
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
				//先删除文件夹里面的文件
				deleteAllFile(path+"/"+tempList[i]);
				//再删除空文件夹
				deleteFolder(path+"/"+tempList[i]);
			}
		}
	}
}
