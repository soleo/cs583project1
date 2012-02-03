
import java.util.ArrayList;


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
		p3.readFile();
		
		outputWriter p4 = new outputWriter("test.txt");
		p4.writeLine("asdhf");
		p4.writeLine("==asdf");
		MSApriori processor = new MSApriori(itemsets,cfg);
		processor.run(false);
		
	}

}
