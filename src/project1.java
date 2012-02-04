
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;


/**
 * 
 */

/**
 * @author shaoxinjiang
 *
 */
public class project1 {
	
	static ArrayList<itemSet> itemsets;
	static config cfg;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length < 3){
			System.out.println("Usage: ");
			System.out.println("       java project1 datafile paramsfile outfile");
			System.exit(1);
		}
		
		String datafile = args[0];
		String paramsfile = args[1];
		String outfile = args[2];
		
		dataParser p1 = new dataParser(datafile);
		paramParser p2 = new paramParser(paramsfile);
		Parser p3 = new Parser(outfile);
		p1.readFile();
		p1.parseDataByDelim(",");
		itemsets = p1.getData();
		p2.readFile();
		p2.parseDataByDelim("=");
		cfg = p2.getConfig();
		//p3.readFile(); 
		
		//outputWriter p4 = new outputWriter("test.txt");
		//p4.writeLine("asdhf");
		//p4.writeLine("==asdf");
		MSApriori processor = new MSApriori(itemsets,cfg);
		processor.run(false);
		
		try {
			BufferedWriter fout = new BufferedWriter(new FileWriter(outfile));
		
			for (int i=1; i <= cfg.MIS.size(); i++) // number of items times runs
			{
				if (processor.finalsets.containsKey(new Integer(i)))
				{
					Vector<ArrayList<String>> Fk = processor.finalsets.get(i);
					if(Fk.size() == 0) break; // last set is usually empty
					System.out.println("\n\nNo. of length "+i+" frequent itemsets: "+Fk.size() + "");
					fout.write("\n\nNo. of length "+i+" frequent itemsets: "+Fk.size() + "\n");
					for (ArrayList<String> c : Fk)
					{
						System.out.print("{ ");
						fout.write("{ ");
						
						for (String item: c) 
						{
							System.out.print(item + " ");
							fout.write(item + " ");
						}
						
						System.out.print("}");
						fout.write("}");
						
						if (processor.finalsetsCnt.containsKey(c))
						{
							System.out.println(" : support-count = " + processor.finalsetsCnt.get(c));
							fout.write(" : support-count = " + processor.finalsetsCnt.get(c)+"\n");
						}
					}
				}
				else break;
			}
			fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error writing to file");
		}
	}

}
