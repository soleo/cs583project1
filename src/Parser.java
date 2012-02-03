import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Parser {
	
	public String fileName;
	public ArrayList<String> lines;
	Parser(String filename){
	//	System.out.println("It is a Parser for us to parse data files and params");
		this.fileName = filename;
		lines = new ArrayList<String>();
	}
	
	public void readFile(){
		try {
    		FileReader input = new FileReader("bin/"+fileName);
    		
    		BufferedReader bufRead = new BufferedReader(input);
    		
    		String line;
    		int count = 0;
    		
    		line = bufRead.readLine();
    		lines.add(line);
    		count++;
    		
    		while (line != null){
    			//System.out.println(count+": "+line);
    			line = bufRead.readLine();
    			lines.add(line);
    			count++;
    		}
    		bufRead.close();
    	}catch (IOException e){
    		e.printStackTrace();
    	}
    	
    	//System.out.println("Filename:"+fileName+" \n"+lines.toString());
	}
	
	public void writeFile(){
		
	}
	
	
	
}
