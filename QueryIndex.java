//Anagha Sarmalkar
//asarmalk@uncc.edu

import java.io.*;
import java.util.*;
import java.nio.*;
import java.nio.file.Files;

public class QueryIndex {
    public static void main(String[] args) throws Exception { 
    	List<String> arguments = new ArrayList<String>();
    	HashMap<String,String> index_map= new HashMap<String,String>();
    	List<String> query_list= new ArrayList<>();
    	
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
            
//			create an index list for words and their document names for easy lookup.
            while((line = br2.readLine())!=null){
            	String[] eachLine = line.split(":");
            	index_map.put(eachLine[0], eachLine[1]);
            }
//            write the query and its document name to a file which is passed as a parameter in cmd
            final BufferedWriter writer1 = new BufferedWriter(new FileWriter(OutputFileName)); 
            for(String q: query_list){
            	if(index_map.containsKey(q)){
                	writer1.write(q+" : "+index_map.get(q));
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