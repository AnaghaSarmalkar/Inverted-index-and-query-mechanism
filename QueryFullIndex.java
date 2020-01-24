//Anagha Sarmalkar
//asarmalk@uncc.edu

import java.io.*;
import java.util.*;
import java.nio.*;
import java.nio.file.Files;

public class QueryFullIndex {
    public static void main(String[] args) throws Exception { 
    	List<String> arguments = new ArrayList<String>();
    	List<String> query_list= new ArrayList<>();
    	HashMap<String,List<String>> index_map= new HashMap<String,List<String>>();
    	
//    	check if 3 arguments are passed. code will progress only then.
        if (0 < args.length && args.length< 3) { 
            System.out.println("Insufficient arguments passed. Please pass arguments in the following order QueryFileName IndexFileName OutputFileName"); 
            
        } 
        else {
        	for (String val:args){
        		arguments.add(val);
        	}
//        	get arguments in strings
        	String QueryFileName=arguments.get(0);
            String IndexFileName=arguments.get(1);
            String OutputFileName=arguments.get(2);

            File file= new File(QueryFileName);
            BufferedReader br1 = new BufferedReader(new FileReader(file));

            String st;
            while ((st= br1.readLine())!= null){
            	query_list.add(st);
            }
            
            File file2= new File(IndexFileName);
            BufferedReader br2 = new BufferedReader(new FileReader(file2));
            String line;
            
//			store documents separated by + in an arraylist referencing each query word.
            while((line = br2.readLine())!=null){
            	String[] eachLine = line.split(":");
            	String[] items = eachLine[1].trim().split("\\+");
            	List<String> doc_list= new ArrayList<>();
            	for (String item: items){
            		if(item!=null){
            			doc_list.add(item);
            		}
            	}
            	index_map.put(eachLine[0], doc_list);
            }
            
//          THE QUERIES WERE MATCHED FOR THEIR RESPECTIVE DOCUMENTS AND THEIR LINES WERE OUTPUTTED USING RANDOM
//            ACCESS FILE.
            final BufferedWriter writer1 = new BufferedWriter(new FileWriter(OutputFileName)); 
            for(String q: query_list){
            	if(index_map.containsKey(q)){
            		String dest="";
            		for (String doc: index_map.get(q)){
            			String[] loc = doc.split("@");
            			File f = new File(loc[0]);
            			RandomAccessFile rf = new RandomAccessFile(f,"r");
            			rf.seek(Long.parseLong(loc[1]));
            			dest=dest+ ":::"+loc[0]+"-->"+rf.readLine();
            		}
                	writer1.write(q+" : "+ dest);
                	writer1.newLine();
                }
            	else{
            		writer1.write(q+" : This query is not present.");
            		writer1.newLine();
            	}
            	
            }
            writer1.close();
            
            
            
        }   
    }
}