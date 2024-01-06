/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.data.implemnts;


import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import com.landbinami.dbconnection.DBConnection;
import com.nura.sms.SendSMS;

/**
 *
 * @author Allen
 */
public class Implemts_dtls {
  
	com.landbinami.dbconnection.DBConnection db=new com.landbinami.dbconnection.DBConnection();
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
        int i=0;
        String Records="";
	byte[ ] imgData = null ;
        Blob image = null;
        boolean status=false;
        String message="";
       
	public int InsertUser_details(String name,String aadhaar_no,String pan_no,String bank,String amount) {
		try{Connection con = db.getConnection();if(con == null){System.out.println("DB Not Configured");}
			Statement stmt = con.createStatement();
				i = stmt.executeUpdate("insert into bank_details (name, aadhaar_no, pan_no, bank, amount) values('"+name+"','"+aadhaar_no+"','"+pan_no+"','"+bank+"','"+amount+"')");
				if(i>0){System.out.println("Values are successfully inserted...");}			
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex);
		}return i;
	}
        public int InsertCorporation_details(String certificate_no,String name,String aadhaar_no,String age,String voter_id,String gender,String dob,String certificate,String certifi_number,String eventdate,String event_time,String legal_name) {
		try{Connection con = db.getConnection();if(con == null){System.out.println("DB Not Configured");}
			Statement stmt = con.createStatement();
				i = stmt.executeUpdate("insert into corporation_details (certificate_no, name, aadhaar_no, age, voter_id, gender, dob, certificate, certifi_number, eventdate, event_time, legal_name) values('"+certificate_no+"','"+name+"','"+aadhaar_no+"','"+age+"','"+voter_id+"','"+gender+"','"+dob+"','"+certificate+"','"+certifi_number+"','"+eventdate+"','"+event_time+"','"+legal_name+"')");
				if(i>0){System.out.println("Values are successfully inserted...");
                                if(certificate.startsWith("Death")){
                                    i=-stmt.executeUpdate("insert into doc_detail(aadhaarno, legal_certifi) values('"+aadhaar_no+"','"+certifi_number+"')");
                                    if(i>0){
                                      System.out.println("Values are successfully inserted...");  
                                    }
                                }}			
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex);
		}return i;
	}
         public int InsertPolice_details(String name,String age,String aadhaar,String vote,String complaint,String date,String crime,String mobile) {
		try{Connection con = db.getConnection();if(con == null){System.out.println("DB Not Configured");}
			Statement stmt = con.createStatement();
				i = stmt.executeUpdate("insert into police_details (name, age, aadhaar, vote, complaint, date, crime, mobile) values('"+name+"','"+age+"','"+aadhaar+"','"+vote+"','"+complaint+"','"+date+"','"+crime+"','"+mobile+"')");
				if(i>0){System.out.println("Values are successfully inserted...");}			
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex);
		}return i;
	}
           public int InsertTransaction_details(String Doc_No,String To_Aadhaar,String legal_doc,String amount,String from_Aadhaar,String Name) {
		try{Connection con = db.getConnection();if(con == null){System.out.println("DB Not Configured");}
			Statement stmt = con.createStatement();
				i = stmt.executeUpdate("insert into hadoop_table (docno, toaadhaar, legalcertificate, amount, fromaadhaar, name, status) values('"+Doc_No+"','"+To_Aadhaar+"','"+legal_doc+"','"+amount+"','"+from_Aadhaar+"','"+Name+"','PENDING')");
				if(i>0){System.out.println("Values are successfully inserted...");}			
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex);
		}return i;
	}
           public boolean getCorp_details(String AadhaarNo,String DocNo,String LegalDoc) {boolean result=false;
		try{Connection con = db.getConnection();if(con == null){System.out.println("DB Not Configured");}
			Statement stmt = con.createStatement();
				rs = stmt.executeQuery("select certificate from corporation_details where certificate_no='"+DocNo+"' and aadhaar_no='"+AadhaarNo+"'");
				if(rs.next()){
                                    System.out.println("FIRST VERIFIVATION TRUE");
                                    String alivestatus=rs.getString(1);
                                    System.out.println("ALIVE STATUS"+alivestatus);
                                    if(alivestatus.equals("Death")){
                                       rs = stmt.executeQuery("select * from doc_detail where aadhaarno='"+AadhaarNo+"' and legal_certifi='"+LegalDoc+"'"); 
                                       if(rs.next()){
                                           System.out.println("DOC TABLE VERIFY");
                                           result=true;}
                                    }
                                    else{
                                        result=true;
                                    }} else{
                                    rs=stmt.executeQuery("select cno from aadhaardetails where aadharno='"+AadhaarNo+"'");
                                    if(rs.next()){
                                        String ContactNofromAadhaar=rs.getString(1);
                                        
                                        System.out.println("Doc else");
                                        SendSMS sendSMS=new SendSMS();
                                        sendSMS.sendSMS(ContactNofromAadhaar, "Some One Used your Aadhaar Card For Land Regiteration");
                                    }
                                }			
		}catch(Exception ex){ex.printStackTrace();System.out.println(ex);}return result;}
         public String getpolice_details(String AadhaarNo,String DocNo,String LegalDoc) {String result="",Complaints="";
		try{Connection con = db.getConnection();if(con == null){System.out.println("DB Not Configured");}
			Statement stmt = con.createStatement();
				rs = stmt.executeQuery("select id,complaint from police_details where aadhaar='"+AadhaarNo+"'");
				while(rs.next()){
                                    Complaints+=rs.getString(1)+"$"+rs.getString(2)+"$"+constants.Constants.CASEDETAIL+",";
                                   }
                                System.out.println("DB RESULT"+Complaints+"$"+constants.Constants.CASEDETAIL);
                                result=Complaints;
		}catch(Exception ex){ex.printStackTrace();System.out.println(ex);}return result;}
        
           public String getBank_details(String AadhaarNo,String DocNo,String LegalDoc) {String result="",Amounts="";
		try{Connection con = db.getConnection();if(con == null){System.out.println("DB Not Configured");}
			Statement stmt = con.createStatement();
				rs = stmt.executeQuery("select id,sum(amount) from transaction_details where aadhaar_no='"+AadhaarNo+"'");
				while(rs.next()){
                                    Amounts+=rs.getString(1)+"$"+rs.getString(2)+"$"+constants.Constants.BANKDETAIL+",";
                                   }
                                System.out.println("DB RESULT"+Amounts);
                                result=Amounts;
		}catch(Exception ex){ex.printStackTrace();System.out.println(ex);}return result;}
          public boolean UpdateCorp_details(String ToAadhaarNo,String FromAadhaarNo,String Name,String DocNo) {
              boolean result=false;
             
		try{Connection con = db.getConnection();if(con == null){System.out.println("DB Not Configured");}
			Statement stmt = con.createStatement();
                        
                        //id, certificate_no, name, aadhaar_no, age, voter_id, gender, dob, certificate, certifi_number, eventdate, event_time, legal_name
				int i = stmt.executeUpdate("update corporation_details set aadhaar_no='"+ToAadhaarNo+"' , name='"+Name+"' where certificate_no='"+DocNo+"'");
			if(i>0){
                                   
                                   
                                       i = stmt.executeUpdate("delete  from doc_detail where aadhaarno='"+FromAadhaarNo+"'"); 
                                        if(i>0){

                                           i=stmt.executeUpdate("update hadoop_table set status='PROCESSED' where  toaadhaar='"+FromAadhaarNo+"'");
                                           if(i>0){
                                              
                                              result=true; 
                                              
                                              System.out.println("Updated");
                                           }
                                           }}else{
                                    System.out.println("Not updated");
                                }			
		}catch(Exception ex){ex.printStackTrace();System.out.println(ex);}return result;}
           public String getHadoop_details() {String result="",Amounts="";
		try{Connection con = db.getConnection();if(con == null){System.out.println("DB Not Configured");}
			Statement stmt = con.createStatement();
				rs = stmt.executeQuery("select docno, toaadhaar, legalcertificate, amount, fromaadhaar, name from hadoop_table where status='PENDING'");
				while(rs.next()){
                                    Amounts+=rs.getString(1)+"$"+rs.getString(2)+"$"+rs.getString(3)+"$"+rs.getString(4)+"$"+rs.getString(5)+"$"+rs.getString(6);
                                   }
                                System.out.println("DB RESULT"+Amounts);
                                result=Amounts;
		}catch(Exception ex){ex.printStackTrace();System.out.println(ex);}return result;}
           
            public String getBank_login(String AadhaarNo,String Panno) {String result="",Amounts="";
		try{Connection con = db.getConnection();if(con == null){System.out.println("DB Not Configured");}
			Statement stmt = con.createStatement();
				rs = stmt.executeQuery("select bank from bank_details where aadhaar_no='"+AadhaarNo+"' and pan_no='"+Panno+"'");
				while(rs.next()){
                                    Amounts+=rs.getString(1)+"$";
                                   }
                                System.out.println("DB RESULT"+Amounts);
                                result=Amounts;
		}catch(Exception ex){ex.printStackTrace();System.out.println(ex);}return result;}
            
                        public String getBank_Trasn(String AadhaarNo,String Bank,String Amount) {String result="",Amounts="";
		try{Connection con = db.getConnection();if(con == null){System.out.println("DB Not Configured");}
			Statement stmt = con.createStatement();
				int i = stmt.executeUpdate("update bank_details set amount=amount-'"+Amount+"' where aadhaar_no='"+AadhaarNo+"' and bank='"+Bank+"'");
				if(i>0){
                                   int j=stmt.executeUpdate("insert into transaction_details(aadhaar_no, bank, amount) values('"+AadhaarNo+"','"+Bank+"','"+Amount+"') ");
                                   Amounts="VALID";
                                   }
                                System.out.println("DB RESULT"+Amounts);
                                result=Amounts;
		}catch(Exception ex){ex.printStackTrace();System.out.println(ex);}return result;}
              
    private int executeUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
