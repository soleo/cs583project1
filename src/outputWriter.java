import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class outputWriter {
	public String fileName;
	private FileWriter fstream;
	outputWriter(String fileName){
		this.fileName = fileName;
		try {
			fstream  = new FileWriter(fileName,true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeLine(String line){
		try {
			BufferedWriter out = new BufferedWriter(fstream);
			int off = 0;
			out.write(line, off, line.length());
			out.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
