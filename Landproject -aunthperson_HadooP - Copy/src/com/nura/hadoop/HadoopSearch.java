/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nura.hadoop;


import com.data.implemnts.Implemts_dtls;
import com.nura.dao.impl.JSONEntityDAOImpl;
import com.nura.dao.impl.ProductDtlsDAOImpl;
import com.nura.dao.impl.TweetsDAOImpl;
import com.nura.dao.impl.UserDtlsDAOImpl;
import com.nura.entity.JSONEntity;
import com.nura.entity.Tweets;
import com.nura.entity.UserEntity;
import constants.ServerIP;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

import javax.swing.JOptionPane;

/**
 *
 * @author ArunRamya
 */
public class HadoopSearch {
private static String result="";
private static int total=0;
private static String Endresult="";
private static String EndProduct="";
private static boolean mainstatus=false;
    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, LongWritable, Text> {

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        @Override
        public void map(LongWritable key, Text value, OutputCollector<LongWritable, Text> output, Reporter reporter)
                throws IOException {
            try {
                String line = value.toString();
                System.out.println("LINE"+line);
                String[] splitStrings = line.split("\\|");
                output.collect(new LongWritable(Long.parseLong(splitStrings[0])), new Text(line));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static class Reduce extends MapReduceBase implements Reducer<LongWritable, Text, LongWritable, Text> {

        public void reduce(LongWritable key, Iterator<Text> values, OutputCollector<LongWritable, Text> output,
                Reporter reporter) throws IOException {
            String likes = "";
            String[] prdCtg = constants.Constants.PROCESS_STATUS;
            String Pid = "";
            String prdd="";
            
           
            java.util.Set<String> prdSel = new java.util.HashSet<>();
            int count = 0;
            while (values.hasNext()) {
                String[] content = values.next().toString().split("\\|");
                String product = content[1];
                Pid = content[0];
                prdd=content[2];
                //System.out.println("Content 0"+Pid);
               // System.out.println("Content 1"+product);     
               // System.out.println("Content 2"+prdd);
                breakFor:
                for (String prd : prdCtg) {
                   // System.out.println("For"+prd);
                    if (prdd.contains(prd)) {
                        if(prd.equals(constants.Constants.BANKDETAIL)){
                            //System.out.println("Normal total"+total);
                           total=total+Integer.parseInt(product);
                          // System.out.println("LOOP TOTAL AMOUNT"+total);
                           Endresult=String.valueOf(total);
                           EndProduct=prd;
                          // System.out.println("END RESULT"+Endresult);
                          
                        }
                        else if(prd.equals(constants.Constants.CASEDETAIL)){
                            Endresult=prdd;
                            EndProduct=prd;
                        }
                        
                        break breakFor;
                    }
                    
                    
                }
                 likes = likes + "#" + EndProduct+"#"+Endresult;
            }
           // System.out.println("output.collet"+(likes));
            output.collect(key, new Text(likes));
        }
    }

    public void processFiles(File inputFile) throws Exception {

        HadoopSearch tweetAnalyze = new HadoopSearch();

        JobConf conf = new JobConf(HadoopSearch.class);

        conf.set("fs.defaultFS", "hdfs://127.0.0.1:9000");
        conf.set("mapred.job.tracker", "127.0.0.1:9001");
        conf.setJobName("hadooptrans");

        conf.setOutputKeyClass(LongWritable.class);
        conf.setOutputValueClass(Text.class);

        conf.setMapperClass(Map.class);
        //conf.setCombinerClass(Reduce.class);
        conf.setReducerClass(Reduce.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        //Code for accessing HDFS file system
        FileSystem hdfs = FileSystem.get(conf);
        Path homeDir = hdfs.getHomeDirectory();
        //Print the home directory
        System.out.println("Home folder -" + homeDir);

        //Add below code For creating and deleting directory
        Path workingDir = hdfs.getWorkingDirectory();
        Path newFolderPath = new Path("/hinput");
        newFolderPath = Path.mergePaths(workingDir, newFolderPath);
        if (hdfs.exists(newFolderPath)) {
            hdfs.delete(newFolderPath, true); //Delete existing Directory
        }
        hdfs.mkdirs(newFolderPath);     //Create new Directory

        //Code for copying File from local file system to HDFS
        String filePath = inputFile.getAbsolutePath();
        System.out.println("FilePATH"+filePath);
        filePath = filePath.substring(0, filePath.lastIndexOf(File.separator));
        Path localFilePath = new Path(inputFile.getAbsolutePath());
        Path hdfsFilePath = new Path(newFolderPath + "/" + inputFile.getName());
        hdfs.copyFromLocalFile(localFilePath, hdfsFilePath);

        hdfs.copyFromLocalFile(localFilePath, newFolderPath);

        FileInputFormat.addInputPath(conf, hdfsFilePath);
        FileSystem fs = FileSystem.get(conf);
        Path out = new Path("hdfs://127.0.0.1:9000/hout");
        fs.delete(out, true);
        FileOutputFormat.setOutputPath(conf, new Path("hdfs://127.0.0.1:9000/hout"));
        JobClient.runJob(conf);
        //Finally copying the out file to local after job has run
        fs.copyToLocalFile(new Path("hdfs://127.0.0.1:9000/hout/part-00000"),
                new Path(constants.Constants.FILE_HADOOP_OUT_LOCATION));

        System.out.println("End of the program");
    }

    public static void main(String[] args) throws Exception {
          String casedetails="";String resultstatus="";
            String Aadhaar_Details=""; String Doc_No="";String Legal_doc="";
           List<String> listOfWords = new ArrayList<String>();
         Implemts_dtls _implements=new Implemts_dtls();
         String hadoop_result=_implements.getHadoop_details();
          //Amounts+=rs.getString(1)+"$"+rs.getString(2)+"$"+rs.getString(3)+"$"+rs.getString(4)+"$"+rs.getString(5)+"$"+rs.getString(6);
         if(!hadoop_result.equals("")){
         StringTokenizer st=new StringTokenizer(hadoop_result,"$");
        // st.nextToken();
         String docno=st.nextToken();
         String toaadhaar=st.nextToken();
         String legalcertificate=st.nextToken();
         String amount=st.nextToken();
         String fromaadhaar=st.nextToken();
         String name=st.nextToken();
        //System.out.println("DOCNO"+docno);
       // System.out.println("TOAADHAR"+toaadhaar);
       // System.out.println("LEGAL"+legalcertificate);
       // System.out.println("AMOUNT"+amount);
       // System.out.println("FROM AADHAAR"+fromaadhaar);
        //System.out.println("NAME"+name);
         UserEntity.setDocNo(docno);
         UserEntity.setAadhaarno(toaadhaar);
         UserEntity.setLegalCertificate(legalcertificate);
         UserEntity.setamount(Integer.parseInt(amount));
         UserEntity.setFromAadhaar(fromaadhaar);
         UserEntity.setName(name);
         
                 }
     Aadhaar_Details=UserEntity.getFromAadhaar();
     Doc_No=UserEntity.getDocNo();
     Legal_doc=UserEntity.getLegalCertificate();
    
       boolean status=_implements.getCorp_details(Aadhaar_Details, Doc_No, Legal_doc);
       //System.out.println("CORPORATION STATUS"+status);
       if(status){           
          String stat= _implements.getpolice_details(Aadhaar_Details, Doc_No, Legal_doc);
          StringTokenizer st=new StringTokenizer(stat,",");
			       while(st.hasMoreTokens()){			          
			       listOfWords.addAll(Arrays.asList(st.nextToken()));}        
                        listOfWords.remove(constants.Constants.STOP_WORDS);
                        for(int i=0;i<listOfWords.size();i++)             
			    {casedetails=listOfWords.set(i, stat);
                                  posFor:
                                 for (String posString : constants.Constants.CASE_KEYWORDS) {
                                if (casedetails.contains(posString)) {                                        
                                        result+=casedetails+",";
                                        resultstatus=constants.Constants.CASEDETAIL;
                                        mainstatus=true;  
                                }
                                else{
                                     String dbresult=_implements.getBank_details(Aadhaar_Details, Doc_No, Legal_doc);
                                    result=dbresult;
                                    resultstatus=constants.Constants.BANKDETAIL;
                                    //System.out.println("BANK"+result);
                                   // System.out.println("BANK"+resultstatus);
                                   // mainstatus=true;
                                }
                                break posFor;}
			    }         
          
          
          
           
       }
        else{JOptionPane.showMessageDialog(null, "INVALID AADHAAR NO");                                
                                }     
      
        java.io.File hInput = new java.io.File(constants.Constants.FILE_HADOOP_IN_LOCATION);
        java.io.FileWriter fileWriter = new java.io.FileWriter(hInput);
       // System.out.println("RESULT"+result);
        if(resultstatus.equals(constants.Constants.CASEDETAIL)){
            result=result.replace(",", "#");
           // System.out.println("CASE"+result);
             
               StringTokenizer stt=new StringTokenizer(result,"#");
        while(stt.hasMoreTokens()){
            String stdata=stt.nextToken();
            StringTokenizer sttt=new StringTokenizer(stdata,"$");
            String pid=sttt.nextToken();
            String pname=sttt.nextToken();
            String keywords=sttt.nextToken();          
            fileWriter.write(pid + "|" + pname + "|" +  keywords +"|" +"\n");
            fileWriter.flush();
        
             }
        }
        else if(resultstatus.equals(constants.Constants.BANKDETAIL)){
            System.out.println("Bank Process Started Here");
        result=result.replace(",", "#");
        StringTokenizer stt=new StringTokenizer(result,"#");
        while(stt.hasMoreTokens()){
            String stdata=stt.nextToken();
            StringTokenizer sttt=new StringTokenizer(stdata,"$");
            String pid=sttt.nextToken();
            String pname=sttt.nextToken();
            String keywords=sttt.nextToken();           
            fileWriter.write(pid + "|" + pname + "|" +  keywords +"|" +"\n");
            fileWriter.flush();
        }   }
       
        fileWriter.close();
        new HadoopSearch().processFiles(hInput);
        java.io.FileInputStream fis = new java.io.FileInputStream(constants.Constants.FILE_HADOOP_OUT_LOCATION);
        java.util.Scanner scan = new java.util.Scanner(fis);
        while (scan.hasNextLine()) {
            JSONEntity _jsonEntity = new JSONEntity();         
            String data = scan.nextLine().split("\t")[1];           
            _jsonEntity.setUserLikes(data);
           // new JSONEntityDAOImpl().saveDetailss(_jsonEntity);
            if(mainstatus){
                JOptionPane.showMessageDialog(null, "Transaction Cancelled Due to FIR Problems");
            }else{
                System.out.println("FINAL METHOD CALL HERE");
                StringTokenizer sts=new StringTokenizer(data,"#");
                sts.nextToken();
                String TotalAmount=sts.nextToken();               
                int useramount=UserEntity.getamount();
                int j=(Integer.parseInt(TotalAmount));
                int kk=(useramount*100)/j;
               System.out.println("User Amount"+TotalAmount);
               System.out.println("Withdrawal:"+useramount);
               System.out.println("THreshold"+constants.Constants.THRESHOLD);
                if(kk>=constants.Constants.THRESHOLD){ 
                    System.out.println("THRESHOLD HERE");
                  
               boolean sta=  _implements.UpdateCorp_details(UserEntity.getFromAadhaar(), UserEntity.getAadhaarno(), UserEntity.getName(), UserEntity.getDocNo());
               if(sta){
                   JOptionPane.showMessageDialog(null, "Land Register successFull");
               }
                }                
                else{JOptionPane.showMessageDialog(null, "Registeration could not be processed..Until you give Proper Documents for your transctions");
                   }
            }
        }
    }
}
