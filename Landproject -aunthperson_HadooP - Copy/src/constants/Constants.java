/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constants;

/**
 *
 * @author Arun kumar
 */
public interface Constants {
  
    public String[] CASE_KEYWORDS = {"Land", "Property"};
    
    public String[] PROCESS_STATUS = {"LAND", "BANK","CRIME"};
    
    final String[] STOP_WORDS = {"of", "i", "am", "a", "is"};
    
    final String CASEDETAIL = "CRIME";
    
    final String BANKDETAIL = "BANK";
    
    final int THRESHOLD = 80;
    
    //File upload status
    public String LOG_FILE_NAME = "/constants/log.properties";

    public static final String FILE_LOCATION = "D:\\";

    public static final String FILE_DOWNLOAD_LOCATION = "D:\\TEMP\\";

    public static final String FILE_HADOOP_OUT_LOCATION = "D:\\TEMP\\result.csv";
    
    public static final String FILE_HADOOP_IN_LOCATION = "D:\\TEMP\\in.csv";

    public static final String MAX_AMT = "50000";

    public static final String[] ALLOWED = {"TRUE", "FALSE"};

    public static final String[] IS_VULGAR_STATUS = {"TRUE", "FALSE"};

    String[] VULGAR_WORDS = {"sex", "sexy", "fuck"};

}
