//Anagha Sarmalkar
//asarmalk@uncc.edu

import java.io.IOException;
import java.util.regex.Pattern;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.log4j.Logger;
import java.util.*;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class FullIndex extends Configured implements Tool {

  private static final Logger LOG = Logger.getLogger(FullIndex.class);

  public static void main(String[] args) throws Exception {
    int res = ToolRunner.run(new FullIndex(), args);
    System.exit(res);
  }

//  SET CONFIGURATIONS
  public int run(String[] args) throws Exception { 
    getConf().set(TextOutputFormat.SEPERATOR, ":	");
    Job job = Job.getInstance(getConf(), "fullindex");
    job.setJarByClass(FullIndex.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    job.setMapperClass(Map.class);
    job.setReducerClass(Reduce.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    return job.waitForCompletion(true) ? 0 : 1;
  }

//  MAPPER CLASS
  public static class Map extends Mapper<LongWritable, Text, Text, Text> {
    private Text word = new Text();
    private long numRecords = 0;    
    private static final Pattern WORD_BOUNDARY = Pattern.compile("\\s*\\b\\s*");

    public void map(LongWritable offset, Text lineText, Context context)
        throws IOException, InterruptedException {
      FileSplit fileSplit = (FileSplit)context.getInputSplit();
      String fileName = fileSplit.getPath().getName();
      String line = lineText.toString();
      String loc=offset.toString();
      Text currentWord = new Text();
      for (String word : WORD_BOUNDARY.split(line)) {
        if (word.isEmpty()) {
            continue;
        }
            currentWord = new Text(word);
//            APPEND THE LOCATION OF THE WORD ALONG WITH THE DOCUMENT NAME
            context.write(currentWord,new Text(fileName+'@'+loc));
        }
    }
  }

  public static class Reduce extends Reducer<Text, Text, Text, Text> {
    @Override
    public void reduce(Text word, Iterable<Text> docs, Context context)
        throws IOException, InterruptedException {
      StringBuilder sb = new StringBuilder();
//      ARRAYLIST TO STORE ALL THE DOCUMENT AND LOCATION PATTERNS
      ArrayList<String> plist = new ArrayList<String>();
      for (Text doc : docs) {
    	  String docu = doc.toString();
    	  plist.add(docu);

      }
      Collections.sort(plist);
      for (String s : plist)
      {
          sb.append(s);
          sb.append("+");
      }
      context.write(word, new Text(sb.toString()));
    }
  }
}
