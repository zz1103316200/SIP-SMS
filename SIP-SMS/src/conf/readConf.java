package conf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class readConf {
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		readConf rc = new readConf();
		System.out.println(rc.read("c:/TransfConfg.xml", "TransfPath"));
	}
	public String read(String filepath,String key){
		
		File filename = new File(filepath); 
        InputStreamReader reader;
        String value = null;
        try {
			reader = new InputStreamReader(new FileInputStream(filename));
		 
		BufferedReader br = new BufferedReader(reader);
		String str = null;
		
		str = br.readLine();
//		System.out.println(str);
        while(str != null){
//        	System.out.println(str);
        	String[] conf = str.split("@");
        	if(conf[0].equals(key))
        		value = conf[1];
        	
        	str = null;
        	str = br.readLine();
        		
        }
        reader.close();
        }
        catch (FileNotFoundException e1) {
    		// TODO Auto-generated catch block
    		e1.printStackTrace();
    	}
    	catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
        return value;
	}

}
