import java.util.HashMap;


public class paramParser extends Parser{
	public config params;
	paramParser(String filename) {
		super(filename);
		params = new config();
		// TODO Auto-generated constructor stub
	}
	
	public void parseDataByDelim(String delim){
		for (String line : lines){		
			if (line != null ){
				String[] tokens = line.split(delim);
				if (tokens.length >=2){
					for(int index = 0; index < tokens.length; index++)
					{
						if (tokens[0].trim().equals("SDC"))
							params.SDC = Float.parseFloat(tokens[1]);
						else{
							String a = tokens[0].substring(tokens[0].indexOf("(")+1, tokens[0].indexOf(")"));
							params.MIS.put(a, tokens[1].trim());
							//System.out.println(a);
						}
					}
				}		
			}
		}
		//printConfig();
	}
	
	public config getConfig(){
		return params;
	}
	
	public void printConfig(){
		System.out.println("SDC: "+ params.SDC);
		System.out.println("MIS: "+"\n"+params.MIS.keySet().toString()+"\n"+params.MIS.values().toString());
		
	}
}
