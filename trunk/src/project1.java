
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
		for (int i=1; i <= cfg.MIS.size(); i++) // number of items times runs
		{
			if (processor.finalsets.containsKey(new Integer(i)))
			{
				Vector<ArrayList<String>> Fk = processor.finalsets.get(i);
				if(Fk.size() == 0) break; // last set is usually empty
				System.out.println("\n\nNo. of length "+i+" frequent itemsets: "+Fk.size() + "");
				for (ArrayList<String> c : Fk)
				{
					System.out.print("{ ");
					for (String item: c) System.out.print(item + " ");
					System.out.print("}");
					if (processor.finalsetsCnt.containsKey(c))
						System.out.println(" : support-count = " + processor.finalsetsCnt.get(c));
				}
			}
			else break;
		}
	}

}
