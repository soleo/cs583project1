import java.util.ArrayList;


public class dataParser extends Parser{
	ArrayList<itemSet> itemsets;
	
	dataParser(String filename) {
		super(filename);
		itemsets = new ArrayList<itemSet>();
		// TODO Auto-generated constructor stub
	}
	
	public void parseDataByDelim(String delim){
		int cnt = 0;
		
		for (String line : lines){
			
			if (line != null )
			{
				//System.out.println(cnt+": "+line);
				try{
					String[] tokens = line.split(delim);
					itemSet s = new itemSet();
					for(int index = 0; index < tokens.length; index++)
					{
						s.item.add(tokens[index].trim());						
						//System.out.println(s.item + " "+ s.transaction);
						//System.out.println(tokens[index].trim());		
					}
					s.transaction = cnt;
					itemsets.add(s);
				} catch(Exception e){
					e.printStackTrace();
				}
				cnt++;
			}
			
		}
		
		printItemsets();
	}
	public ArrayList<itemSet> getData(){
		return itemsets;
	}
	public void printItemsets(){
		for(itemSet s : itemsets){
			System.out.println("transaction: "+ s.transaction+" item: "+s.item.toString());
		}
	}
	
}
