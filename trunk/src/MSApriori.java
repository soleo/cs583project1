import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;




public class MSApriori {
	public config cfg;
	public ArrayList<itemSet> sets;
	public ArrayList<itemSet> sortedItemsets;
	public ArrayList<String> sortedItems_M;
	public HashMap<String, Integer> support;
	public int totalTrans;
	public Vector<String> candidates;
	public HashMap<Integer, Vector<ArrayList<String>>> finalsets;
	private boolean quiet;
	Vector<ArrayList<String>> Fk;
	HashMap<ArrayList<String>, Integer> candidatesCnt;
	public HashMap<ArrayList<String>, Integer> finalsetsCnt;
	
	MSApriori(){
		System.out.println("This is our main algorithm called MS-Apriori");
	}
	
	MSApriori(ArrayList<itemSet> s, config cfg){
		this.cfg = cfg;
		this.sets = s;
		totalTrans = 0;
		sortedItemsets = new ArrayList<itemSet>();
		support = new HashMap<String, Integer>();
		finalsets = new HashMap<Integer, Vector<ArrayList<String>>>();
		
	}
	
	public void run(boolean quiet){
		this.quiet = quiet;
		if (!quiet)
			System.out.println("begin processing");
		
		finalsetsCnt = new HashMap<ArrayList<String>, Integer>();
		Vector<ArrayList<String>> candidatesK = null;
		
		sort();
		init_pass();
		
		for (int k = 2; !finalsets.get(k-1).isEmpty(); k++){
			if (k == 2)
				candidatesK = level2_candidate_gen();
			else
				candidatesK = MScandidate_gen(k-1, Fk);
			
			candidatesCnt = new HashMap<ArrayList<String>, Integer>();
			
			for (itemSet s: sortedItemsets){
				for (ArrayList<String> candidates: candidatesK){
						//System.out.println("test "+ candidates);
					int matchcnt = 0;
					for(int index = 0; index < candidates.size(); index++ )
					{
						if( s.item.toString().indexOf(candidates.get(index)) > 0)
							matchcnt++;
					}
					if (matchcnt == candidates.size()){
						//System.out.println(candidates.toString());
						if (!candidatesCnt.containsKey(candidates))
							candidatesCnt.put(candidates, 1);
						else{
							int cnt = candidatesCnt.get(candidates);
							cnt++;
							candidatesCnt.put(candidates, cnt);
						}
					}
					
					// get rid of first element of the candidates to check the count again
					// this part is used for generate rules, ToDo: counting Soleo shao
					//for(int index = 1; index < candidates.size(); index++){
					//	
					//}
				}
			}
			
			Fk = new Vector<ArrayList<String>>();
			Set<ArrayList<String>> candidates = candidatesCnt.keySet();
			Iterator<ArrayList<String>> i = candidates.iterator();
			//System.out.println(candidatesCnt.entrySet().toString());
			while (i.hasNext()){
				ArrayList<String> me = (ArrayList<String>)i.next();
				//System.out.println("ddd"+me.toString());
			    if (
			    		candidatesCnt.get(me)/(float)totalTrans 
			    		>= Float.parseFloat(cfg.MIS.get(me.get(0)))
			    	){
			    	  Fk.add(me);  
					  finalsetsCnt.put(me, candidatesCnt.get(me));
			    } 
			}
			//System.out.println(candidatesCnt.entrySet().toString());
			finalsets.put(k, Fk);
			if (k == 2)
			{
				System.out.println("k==2"+finalsetsCnt.entrySet().toString());
				System.out.println("k==2"+finalsets.entrySet().toString());
			}
		}
		
	}
	
	private void sort(){
		// sort the itemsets by using MIS value for each itemset
		int cnt = 0;
		
		// MS-Apriori() : line 1, creating list M
		MapValueComparator vcmp = new MapValueComparator(cfg.MIS);
		TreeMap<String,String> sortedItems = new TreeMap<String, String>(vcmp);
		sortedItems.putAll(cfg.MIS);
		sortedItems_M = new ArrayList<String>();
		for (String key: sortedItems.keySet())
		{
			sortedItems_M.add(key);
			System.out.print(key + ":" + sortedItems.get(key) + "--");
		}
		System.out.println();
		for (itemSet itemset: sets){
			
			int[] itemindexes = new int[itemset.item.size()]; 
			for (int index = 0; index < itemset.item.size(); index++){
				//System.out.println(cfg.MIS.get(itemset.item.get(index))+ " item:"+ itemset.item.get(index) + " transaction:"+ itemset.transaction );
				itemindexes[index] = (int)(Float.parseFloat(cfg.MIS.get(itemset.item.get(index)))*100);
			}
			
			quickSort qs = new quickSort();
			int[] indexes = new int[itemindexes.length];
			qs.sort(itemindexes, indexes);
			itemSet s = new itemSet();
			//System.out.print("\n\n======"+indexes.length+"\n");
			for (int i = 0; i < itemset.item.size(); i++)
			{
				//System.out.print(itemindexes[i]+" "+ indexes[i]+ ":");
				s.item.add(itemset.item.get(indexes[i]));
			}
			s.transaction = cnt;
			sortedItemsets.add(s);
			cnt++;
			//System.out.print("\n");
		}
		totalTrans = cnt;
		
		for (itemSet qs: sortedItemsets){
			//for (int index = 0; index < qs.item.size(); index++){
				System.out.println("transaction: "+ qs.transaction+ " item: "+ qs.item.toString());	
			//}
		}
		
	}
	
	private void init_pass(){
		// compute support
    	for (itemSet s: sortedItemsets){
    		for (int index = 0; index < s.item.size(); index++){
    			if (!support.containsKey(s.item.get(index)))
    				support.put(s.item.get(index), 1);
    			else
    			{
    				int value = support.get(s.item.get(index));
    				value++;
    				support.put(s.item.get(index), value);
    			}
    		}
    	}
    	
    	//System.out.println(support.toString()+" "+ totalTrans);
    	Vector<ArrayList<String>> F1 = new Vector<ArrayList<String>>();
    	candidates = new Vector<String>();
    	//Set set = support.entrySet();

	    Iterator i = sortedItems_M.iterator();//set.iterator();
	    
	    while(i.hasNext()){
	      ArrayList<String> value = new ArrayList<String>();
	      //Map.Entry me = (Map.Entry)i.next();
	      String item = (String)i.next();
	      //candidates.add((String)me.getKey());
	      candidates.add(item);
	      //if (Integer.parseInt(me.getValue().toString())/(double)totalTrans >= Float.parseFloat(cfg.MIS.get(me.getKey()))){
	      if (Integer.parseInt(support.get(item).toString())/(float)totalTrans >= Float.parseFloat(cfg.MIS.get(item))){
		      //value.add((String)me.getKey());
	    	  value.add(item);  
	    	  F1.add(value);
	    	  finalsetsCnt.put(value, Integer.parseInt(support.get(item).toString()));
	      } 
	      
	      if (!quiet)
	    	  System.out.println(item + " : " + Integer.parseInt(support.get(item).toString())/(double)totalTrans + " :"+cfg.MIS.get(item));
	    	  //System.out.println(me.getKey() + " : " + Integer.parseInt(me.getValue().toString())/(double)totalTrans + " :"+cfg.MIS.get(me.getKey()));
	    }
	    //F1.add(value);
	    finalsets.put(1, F1);	
	    //System.out.println(finalsets.entrySet().toString());
    }
    
	private Vector<ArrayList<String>> level2_candidate_gen(){
		Vector<ArrayList<String>> candidates2 = new Vector<ArrayList<String>>();

		for(int i = 0; i < candidates.size(); i++){
			if (support.get(candidates.get(i))/(float)totalTrans >= Float.parseFloat(cfg.MIS.get(candidates.get(i)))){
				
				for (int j = i+1; j< candidates.size(); j++) {
					if (support.get(candidates.get(j))/(float)totalTrans >= Float.parseFloat(cfg.MIS.get(candidates.get(i)))
							&& Math.abs((support.get(candidates.get(j))-support.get(candidates.get(i)))/(double)totalTrans) <= cfg.SDC){
						//System.out.println("["+candidates.get(i)+", "+ candidates.get(j)+"]");
						ArrayList<String> a = new ArrayList<String>();
						a.add(candidates.get(i));
						a.add(candidates.get(j));
						candidates2.add(a);
					}
				}
			}	
		}
		System.out.println(" CAN 2"+candidates2.toString());
		return candidates2;	
	}
	
	private Vector<ArrayList<String>> MScandidate_gen(int k, Vector<ArrayList<String>> Fk){
		Vector<ArrayList<String>> candidatesK = new Vector<ArrayList<String>>();
		//System.out.println("Fk: "+Fk.toString());
		
		
			for (int i = 0; i < Fk.size(); i++)
				for (int j = 0; j < Fk.size(); j++){
					
					int matchcnt = 0;
					for (int index = 0; index < k-1 ; index++) {
						if (Fk.get(i).get(index).equals(Fk.get(j).get(index)) && i!=j ) {
							matchcnt++;	
						}
					}
					if (matchcnt == k-1){
						ArrayList<String> candidates = new ArrayList<String>();
						if (Float.parseFloat(cfg.MIS.get(Fk.get(i).get(k-1))) < Float.parseFloat(cfg.MIS.get(Fk.get(j).get(k-1)))
								&& Math.abs((support.get(Fk.get(i).get(k-1))-support.get(Fk.get(j).get(k-1)))/(double)totalTrans) <= cfg.SDC) {
							
							for (String s : Fk.get(i)) 
								candidates.add(s);
							candidates.add(Fk.get(j).get(k-1));
							candidatesK.add(candidates);
						}
						/*else if (Float.parseFloat(cfg.MIS.get(Fk.get(i).get(k-1))) >= Float.parseFloat(cfg.MIS.get(Fk.get(j).get(k-1))) 
								&& Math.abs((support.get(Fk.get(i).get(k-1))-support.get(Fk.get(j).get(k-1)))/(double)totalTrans) < cfg.SDC){
							for (String s : Fk.get(j)) 
								candidates.add(s);
							candidates.add(Fk.get(i).get(k-1));
							candidatesK.add(candidates);
						}*/	
						for (int l=0; l < candidates.size(); l++)
						{
							ArrayList<String> s = (ArrayList<String>) candidates.clone();
							s.remove(l);	// so the k-1 subset s with the l-th element out
							if ( l != 0	// so c[1] is not removed, c[1] is in s
									||
									Float.parseFloat(cfg.MIS.get(candidates.get(0))) 
									== Float.parseFloat(cfg.MIS.get(candidates.get(1)))
									)
								if (!Fk.contains(s))
									candidatesK.remove(candidates);
						}
						// add subset code here
						
					}
				}
		System.out.println("combination: "+ candidatesK.toString());
		return candidatesK;
	}
	
	//http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
	class MapValueComparator implements Comparator {

		Map base;
		public MapValueComparator(Map base) {
			this.base = base;
		}

		public int compare(Object a, Object b) {
			//return ((Comparable)base.get(a)).compareTo(((Comparable)base.get(b)));
			if(Double.parseDouble((String)base.get(a)) >= Double.parseDouble((String)base.get(b))) {
				return 1;
			} /*else if(Double.parseDouble((String)base.get(a)) == Double.parseDouble((String)base.get(b)))  {
			      return 0;
			    }*/ else {
			    	return -1;
			    }
		}
	}
}
