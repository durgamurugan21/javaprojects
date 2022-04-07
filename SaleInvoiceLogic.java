package SaleInvoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import DBUtil.DatabaseUtil;
import commonMailTemplate.CommonURLShortner;
import dashboarddisplay.dashboardDisplayDao;
import master.MasterDao;
import saleOrder.QueryConstants;
import saleOrder.SaleOrderJSON;
import staff.StaffJSON;

public class SaleInvoiceLogic {

	/*
	 * FUNCTION FOR GETTING NECESSARY INFORMATION FOR
	 * SALE INVOICE PAGE WHEN IT'S LOADED
	 */
	public static void SelectBasicDetails(SaleInvoiceJSON json, Connection connection) throws SQLException {
		
			
		//GET CUSTOMER DETAILS
		SaleInvoiceLogic.GetCustomerDetails(json,connection);
		
		//GET STAFF DETAILS
		SaleInvoiceLogic.GetStaffDetails(json,connection);
		
		//GET PRODUCT DETAILS
		SaleInvoiceLogic.GetProductDetails(json,connection);
		
		//GET VEHICLE DETAILS
		SaleInvoiceLogic.GetVehicleDetails(json,connection);
		
		//GET BOOKING DETAILS / JOBCARD DETAILS
		SaleInvoiceLogic.GetJobCardDetails(json,connection,"MakeInvoice");
	}

	

	/*
	 * FUNCTION FOR GEETING CUSTOMER DETAILS
	 */
	private static void GetCustomerDetails(SaleInvoiceJSON json, Connection connection) throws SQLException {

		ArrayList<SaleInvoiceJSON> customerList = new ArrayList<SaleInvoiceJSON>();
		
	String querySelect = SaleInvoiceQueryConstants.SELECT_CUSTOMER_DETAILS;
		
		PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
		// preparedStmt.setString(1,json.getProductCategory());
		preparedStmt.setString(1, json.getCompanyId());
		ResultSet rs = preparedStmt.executeQuery();
		while (rs.next()) {
			
			SaleInvoiceJSON selectCustomerNameobj = new SaleInvoiceJSON();
			selectCustomerNameobj.setCustomerName(rs.getString("customerName"));
			selectCustomerNameobj.setOrderNo(rs.getString("orderNumber"));	
			selectCustomerNameobj.setCustomerId(rs.getString("customerId"));
			selectCustomerNameobj.setCust_ContactNo(rs.getString("contactNo"));
			selectCustomerNameobj.setCust_EmailId(rs.getString("email"));
			
			selectCustomerNameobj.setCust_address(rs.getString("Address"));
			selectCustomerNameobj.setCust_gstNo(rs.getString("GSTNO"));
			selectCustomerNameobj.setCompanyName(rs.getString("CompanyName"));
			
			
			
		
			customerList.add(selectCustomerNameobj);
		
		}
		
		json.setCustomerList(customerList);
	}

	/*
	 * FUNCTION FOR GEETING STAFF DETAILS
	 */
	private static void GetStaffDetails(SaleInvoiceJSON json, Connection connection) throws SQLException {
		
		ArrayList<SaleInvoiceJSON> staffList = new ArrayList<SaleInvoiceJSON>();	
		
	
		
			String querySelect = SaleInvoiceQueryConstants.STAFF_DETAILS;

			PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
			preparedStmt.setString(1, json.getCompanyId());
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				SaleInvoiceJSON staffobj = new SaleInvoiceJSON();
				staffobj.setStaffId(rs.getString("staffId"));
				staffobj.setStaffName(rs.getString("staffName"));
				staffobj.setStaffRole(rs.getString("RoleName"));
				staffobj.setSiteHandled(rs.getString("SiteHandled"));
				staffobj.setSite(rs.getString("SiteHandled"));
				staffList.add(staffobj);
			}

			
		
			json.setStaffList(staffList);
		
			
	}

	/*
	 * FUNCTION FOR GEETING PRODUCT DETAILS
	 */
	public static void GetProductDetails(SaleInvoiceJSON json, Connection connection) throws SQLException {
		
		ArrayList<SaleInvoiceJSON> productList = new ArrayList<SaleInvoiceJSON>();	
		
	String querySelect = SaleInvoiceQueryConstants.SELECT_PRODUCT_DETAILS;
		
		PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
		preparedStmt.setString(1, json.getCompanyId());
		ResultSet rs = preparedStmt.executeQuery();
		while (rs.next()) {
			SaleInvoiceJSON selectproductobj = new SaleInvoiceJSON();
			selectproductobj.setProductName(rs.getString("productName"));
			selectproductobj.setProductType(rs.getString("productType"));
			selectproductobj.setProductRate(rs.getString("saleRate"));
			selectproductobj.setProduct_CGST_Percentage(rs.getString("cgst"));
			selectproductobj.setProduct_SGST_Percentage(rs.getString("sgst"));
			selectproductobj.setProduct_IGST_Percentage(rs.getString("igst"));
			selectproductobj.setProductQty(rs.getString("quantity"));
			selectproductobj.setProductId(rs.getString("productId"));
			selectproductobj.setProductQtyLimit(rs.getString("QuantityLimit"));
			selectproductobj.setProductMeasurementUnit(rs.getString("Unit"));
			productList.add(selectproductobj);
		}
	
		json.setProductList(productList);
	}


	
	/*
	 * FUNCTION FOR GETTING VEHICLE DETAILS
	 */
	private static void GetVehicleDetails(SaleInvoiceJSON json, Connection connection) throws SQLException {
		ArrayList<SaleInvoiceJSON> vehicleList = new ArrayList<SaleInvoiceJSON>();	
		
		String querySelect = SaleInvoiceQueryConstants.SELECT_VEHICLE_DETAILS;
			
			PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
			preparedStmt.setString(1, json.getCompanyId());
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				SaleInvoiceJSON selectvehicleobj = new SaleInvoiceJSON();
				selectvehicleobj.setVehicleId(rs.getString("vehicleId"));
				selectvehicleobj.setVehicleRegNo(rs.getString("vehicleRegistrationNo"));
				selectvehicleobj.setCustomerId(rs.getString("customerId"));
				selectvehicleobj.setVehicleMake(rs.getString("vehicleMake"));
				selectvehicleobj.setVehicleModel(rs.getString("vehicleModel"));
				selectvehicleobj.setFuelType(rs.getString("FuelType"));
				selectvehicleobj.setSite(rs.getString("Site"));
				vehicleList.add(selectvehicleobj);
			}
		
			json.setVehicleList(vehicleList);
	}

	
	/*
	 * FUNCTION FOR GETTING JOBCARD DETAILS THAT ARE YET TO BE BILLED DETAILS
	 */
	private static void GetJobCardDetails(SaleInvoiceJSON json, Connection connection,String calledFrom) throws SQLException {
		ArrayList<SaleInvoiceJSON> jobCardList = new ArrayList<SaleInvoiceJSON>();	
		
		String querySelect = SaleInvoiceQueryConstants.SELECT_JOBCARD_DETAILS;
			
		if(calledFrom.equals("UpdateInvoice")) {
			querySelect = SaleInvoiceQueryConstants.SELECT_JOBCARD_DETAILS_UPDATE;
		}
		
			PreparedStatement preparedStmt = connection.prepareStatement(querySelect);
			preparedStmt.setString(1, json.getCompanyId());
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				SaleInvoiceJSON selectjobcardobj = new SaleInvoiceJSON();
				selectjobcardobj.setCustomerId(rs.getString("customerId"));
				selectjobcardobj.setBookingId(rs.getString("bookingId"));
				selectjobcardobj.setVehicleRegNo(rs.getString("vehicleRegistrationNo"));
				selectjobcardobj.setVehicleMake(rs.getString("vehicleMake"));
				selectjobcardobj.setVehicleModel(rs.getString("vehicleModel"));
				selectjobcardobj.setFuelType(rs.getString("vehicleFuelType"));
				selectjobcardobj.setVehicleColor(rs.getString("Color"));
				selectjobcardobj.setVehicleModelYear(rs.getString("ModalYear"));
				selectjobcardobj.setEngineNo(rs.getString("Engineno"));
				selectjobcardobj.setChasisNo(rs.getString("ChasisNo"));
				selectjobcardobj.setOdometerReading(rs.getString("OdometerReading"));
				selectjobcardobj.setComments(rs.getString("Comments"));
				selectjobcardobj.setCreated_DateTime(rs.getString("CreatedTimeStamp"));
				selectjobcardobj.setStarted_DateTime(rs.getString("StartedTimeStamp"));
				selectjobcardobj.setCompleted_closed_deleted_DateTime(rs.getString("Completed_Closed_DeletedTimeStamp"));
				selectjobcardobj.setFinished_DateTime(rs.getString("FinishedTimeStamp"));
				selectjobcardobj.setServiceName(rs.getString("issues"));
				selectjobcardobj.setSite(rs.getString("Site"));
				selectjobcardobj.setStatus(rs.getString("Status"));
				
				jobCardList.add(selectjobcardobj);
			}
		
			json.setJobCardList(jobCardList);
		
	}
	
	/*
	 * FUNCTION FOR ADDING SALE INVOICE DETAILS
	 */
	public static SaleInvoiceJSON AddSaleInvoiceDetails(SaleInvoiceJSON json, Connection connection) throws JSONException, SQLException {


		ArrayList<SaleInvoiceJSON> insufficientProductList = new ArrayList<SaleInvoiceJSON>();	
		
		json.setInvoiceResponse("Failed");
				
		//SALE INVOICE TABLE INSERT QUERY 
		String INSERT_INVOICE_SaleInvoiceTable = "INSERT into SaleInvoiceTable(companyId,customerId,customerName," //CUSTOMERINFO
		   		+ "email,contactNo,gstNo,companyName,address," //CUSTOMERINFO
		   		+ "productId,productName,productType,quantity,rate," //PRODUCT INFO
		   		+ "total,discountPercentage,discountAmount,prefinalAmount,cgsta," //PRODUCT INFO
		   		+ "sgsta,igsta,finalAmount,serviceBy,staffId," //PRODUCT INFO
		   		+ "invoiceNo,orderNumber,invoiceDate,dueDate,date," //INVOICE INFO
		   		+ "totalcgst,totalsgst,totaligst,totalgst,subtotal1,"  //INVOICE INFO
		   		+ "discount,advance,balance_amount,advisor,advisorid,totalitemqty," //INVOICE INFO
		   		+ "payment_status,paymentmode,Site,"
		   		+ "BookingId,vehicleRegistrationNo,vehicleMake,vehicleModel,vehicleFuelType," //INVOICE INFO
		   		+ "cgstp,sgstp,igstp,ReferenceInvoiceNo) "  //PRODUCT INFO
		   		+ "VALUES(?,?,?,"
		   		+ "?,?,?,?,?,"
		   		+ "?,?,?,?,?,"
		   		+ "?,?,?,?,?,"
		   		+ "?,?,?,?,?,"
		   		+ "?,?,?,?,?,"
		   		+ "?,?,?,?,?,"
		   		+ "?,?,?,?,?,?,"
		   		+ "?,?,?,"
		   		+ "?,?,?,?,?,"
		   		+ "?,?,?,?)";
		

		
          PreparedStatement preparedStatementSaleInvoiceTable = connection.prepareStatement(INSERT_INVOICE_SaleInvoiceTable) ;
         // connection.setAutoCommit(false);

          //PRODUCT TABLE INVENTORY UPDATE QUERY
          String UPDATE_PRODUCT_QUANTITY="UPDATE ProductTable SET quantity=quantity-? "
          		+ "where productId = ? and companyId= ? ";
          PreparedStatement preparedStatementProductTable = connection.prepareStatement(UPDATE_PRODUCT_QUANTITY) ;
          
          connection.setAutoCommit(false);
          
		  System.out.println("CART PRODUCT DATA :"+json.getCartProduct());
		   JSONArray jsonArr = new JSONArray(json.getCartProduct());

			  System.out.println("CART PRODUCT jsonArr  DATA :"+jsonArr);
			  
		   //GET CURRENT INVOICE NO
			  /*
			   * PARAMETERS SENT ARE json,DB connection,invoicetype,table name from which invoice
			   * no is supposed to be feteched
			   */
		   String invoiceNo= SaleInvoiceLogic.GetInvoiceNumber(json,connection,"SaleInvoice","SaleInvoiceTable");
		   json.setInvoiceNo(invoiceNo);
		   
		   String orderNo=SaleInvoiceLogic.GetInvoiceOrderNumber(json,connection,"SaleInvoice","SaleInvoiceTable");
			
		   String invoiceReferenceNo=SaleInvoiceLogic.GetInvoiceReferenceNumber(json,connection,"SaleInvoice","SaleInvoiceTable");
			
	        for (int i = 1; i < jsonArr.length(); i++)
	        {
	            JSONObject jsonObj = jsonArr.getJSONObject(i);

	            
	            SaleInvoiceJSON insufficientInvoiceData=new SaleInvoiceJSON();
	            
	            //CHECK IF REQUIRED QTY OF THE PRODUCT IS PRESENT IN THE DB
	            if(jsonObj.getString("ProductType").equals("product")) {
	            	
	            	String productQty=jsonObj.get("Qty").toString();
	            	String dbQuantity="0";
	            	
	            	//GET CURRENT QTY FROM DB
	            	
	            	String querySelectGetQTY = QueryConstants.GET_QUANTITY;
					PreparedStatement preparedStmtGetQTY = connection.prepareStatement(querySelectGetQTY);
					preparedStmtGetQTY.setString(1, jsonObj.getString("ProductId"));
					preparedStmtGetQTY.setString(2, json.getCompanyId());
					ResultSet rsGetQTY = preparedStmtGetQTY.executeQuery();
					while (rsGetQTY.next()) {
						
						dbQuantity = rsGetQTY.getString("Quantity");

						if (Double.parseDouble(dbQuantity) < Double.parseDouble(productQty)) {
							json.setInvoiceResponse("InsufficientQuantity");
							productQty=dbQuantity;
						}
						
					}
					
					
					insufficientInvoiceData.setProductId(jsonObj.get("ProductId").toString());
					insufficientInvoiceData.setProductName(jsonObj.get("Item").toString());
					insufficientInvoiceData.setProductType(jsonObj.get("ProductType").toString());
					insufficientInvoiceData.setProductRate(jsonObj.get("Rate").toString());
					insufficientInvoiceData.setProductQty(productQty);
					insufficientInvoiceData.setProductTotal(jsonObj.get("Total").toString());             
					insufficientInvoiceData.setProductDiscountPercentage(jsonObj.get("DiscountPercentage").toString());                        
					insufficientInvoiceData.setProductDiscountAmount(jsonObj.get("DiscountAmt").toString());                              
					insufficientInvoiceData.setProductSubTotal(jsonObj.get("SubTotal").toString());                           
					insufficientInvoiceData.setProduct_CGST_Percentage(jsonObj.get("CGST_Percentage").toString());
					insufficientInvoiceData.setProduct_CGST_Amt(jsonObj.get("CGSTAmt").toString());                                  
					insufficientInvoiceData.setProduct_SGST_Percentage(jsonObj.get("SGST_Percentage").toString());
			        insufficientInvoiceData.setProduct_SGST_Amt(jsonObj.get("SGSTAmt").toString());                        
					insufficientInvoiceData.setProductFinalAmt(jsonObj.get("FinalAmt").toString());  
										
					//CUSTOMER INFO
					preparedStatementSaleInvoiceTable.setString(1,json.getCompanyId() );
					preparedStatementSaleInvoiceTable.setString(2, json.getCustomerId());
					preparedStatementSaleInvoiceTable.setString(3, json.getCustomerName());
					preparedStatementSaleInvoiceTable.setString(4, json.getCust_EmailId());  
					preparedStatementSaleInvoiceTable.setString(5,json.getCust_ContactNo() );
					preparedStatementSaleInvoiceTable.setString(6, json.getCust_gstNo());
					preparedStatementSaleInvoiceTable.setString(7,json.getCompanyName() );	
					preparedStatementSaleInvoiceTable.setString(8,json.getCust_address() );	
					
					
					//PRODUCT INFO
					preparedStatementSaleInvoiceTable.setString(9, jsonObj.get("ProductId").toString());
					preparedStatementSaleInvoiceTable.setString(10,jsonObj.get("Item").toString());
					preparedStatementSaleInvoiceTable.setString(11,jsonObj.get("ProductType").toString() );	
					preparedStatementSaleInvoiceTable.setString(12, jsonObj.get("Qty").toString());
					preparedStatementSaleInvoiceTable.setString(13, jsonObj.get("Rate").toString());
					preparedStatementSaleInvoiceTable.setString(14, jsonObj.get("Total").toString());
					preparedStatementSaleInvoiceTable.setString(15, jsonObj.get("DiscountPercentage").toString());
					preparedStatementSaleInvoiceTable.setString(16, jsonObj.get("DiscountAmt").toString());
					preparedStatementSaleInvoiceTable.setString(17,jsonObj.get("SubTotal").toString() );
					preparedStatementSaleInvoiceTable.setString(18,jsonObj.get("CGST_Percentage").toString() ); 
					preparedStatementSaleInvoiceTable.setString(19,jsonObj.get("SGST_Percentage").toString() );	
					preparedStatementSaleInvoiceTable.setString(20, "0"); //igsta
					preparedStatementSaleInvoiceTable.setString(21, jsonObj.get("FinalAmt").toString());
					preparedStatementSaleInvoiceTable.setString(22, json.getProduct_serviceByStaffName()); //serviceby
					preparedStatementSaleInvoiceTable.setString(23,json.getProduct_serviceByStaffId() ); //staffid
				
					//INVOICE INFO
					preparedStatementSaleInvoiceTable.setString(24, json.getInvoiceNo());
					preparedStatementSaleInvoiceTable.setString(25, orderNo);
					preparedStatementSaleInvoiceTable.setString(26, json.getInvoiceDate());
					preparedStatementSaleInvoiceTable.setString(27, json.getInvoiceDueDate());
					preparedStatementSaleInvoiceTable.setString(28, json.getDate()); //date
					preparedStatementSaleInvoiceTable.setString(29,json.getTotalCGSTAmt() );	//totalcgst
					preparedStatementSaleInvoiceTable.setString(30, json.getTotalSGSTAmt());  //totalsgst
					preparedStatementSaleInvoiceTable.setString(31, "0"); //totaligst
					preparedStatementSaleInvoiceTable.setString(32,json.getTotalGst() );	
					preparedStatementSaleInvoiceTable.setString(33,json.getInvoiceAmt() );  
					preparedStatementSaleInvoiceTable.setString(34, json.getDiscountAmt());
					preparedStatementSaleInvoiceTable.setString(35, json.getInvoiceAmt_Paid()); //advance
					preparedStatementSaleInvoiceTable.setString(36, json.getBalanceAmt()); //balanceamt
			
					preparedStatementSaleInvoiceTable.setString(37, json.getInvoiceAdvisor());
					preparedStatementSaleInvoiceTable.setString(38, json.getInvoiceAdvisorId());
					preparedStatementSaleInvoiceTable.setString(39, json.getTotal_Itemqty());
					preparedStatementSaleInvoiceTable.setString(40,json.getPaymentStatus() );
					preparedStatementSaleInvoiceTable.setString(41, json.getPaymentMode());
					preparedStatementSaleInvoiceTable.setString(42,json.getSite() );	
		
					preparedStatementSaleInvoiceTable.setString(43,json.getBookingId());
					preparedStatementSaleInvoiceTable.setString(44, json.getVehicleRegNo());
					preparedStatementSaleInvoiceTable.setString(45,json.getVehicleMake());	
					preparedStatementSaleInvoiceTable.setString(46, json.getVehicleModel());
					preparedStatementSaleInvoiceTable.setString(47,json.getFuelType());	
					
					preparedStatementSaleInvoiceTable.setString(48,jsonObj.get("CGSTAmt").toString());	
					preparedStatementSaleInvoiceTable.setString(49, jsonObj.get("SGSTAmt").toString() );
					preparedStatementSaleInvoiceTable.setString(50,"0");	
					preparedStatementSaleInvoiceTable.setString(51,invoiceReferenceNo);	
										
					
					preparedStatementSaleInvoiceTable.addBatch();
					
					
					preparedStatementProductTable.setString(1, jsonObj.get("Qty").toString() );
					preparedStatementProductTable.setString(2, jsonObj.get("ProductId").toString() );
					preparedStatementProductTable.setString(3, json.getCompanyId() );
					preparedStatementProductTable.addBatch();
					
					
	            }else if(jsonObj.getString("ProductType").equals("service")) {
	            
	            	insufficientInvoiceData.setProductId(jsonObj.get("ProductId").toString());
					insufficientInvoiceData.setProductName(jsonObj.get("Item").toString());
					insufficientInvoiceData.setProductType(jsonObj.get("ProductType").toString());
					insufficientInvoiceData.setProductRate(jsonObj.get("Rate").toString());
					insufficientInvoiceData.setProductQty(jsonObj.get("Qty").toString());
					insufficientInvoiceData.setProductTotal(jsonObj.get("Total").toString());             
					insufficientInvoiceData.setProductDiscountPercentage(jsonObj.get("DiscountPercentage").toString());                        
					insufficientInvoiceData.setProductDiscountAmount(jsonObj.get("DiscountAmt").toString());                              
					insufficientInvoiceData.setProductSubTotal(jsonObj.get("SubTotal").toString());                           
					insufficientInvoiceData.setProduct_CGST_Percentage(jsonObj.get("CGST_Percentage").toString());
					insufficientInvoiceData.setProduct_CGST_Amt(jsonObj.get("CGSTAmt").toString());                                  
					insufficientInvoiceData.setProduct_SGST_Percentage(jsonObj.get("SGST_Percentage").toString());
			        insufficientInvoiceData.setProduct_SGST_Amt(jsonObj.get("SGSTAmt").toString());                        
					insufficientInvoiceData.setProductFinalAmt(jsonObj.get("FinalAmt").toString());  
					
					
					//CUSTOMER INFO
					preparedStatementSaleInvoiceTable.setString(1,json.getCompanyId() );
					preparedStatementSaleInvoiceTable.setString(2, json.getCustomerId());
					preparedStatementSaleInvoiceTable.setString(3, json.getCustomerName());
					preparedStatementSaleInvoiceTable.setString(4, json.getCust_EmailId());  
					preparedStatementSaleInvoiceTable.setString(5,json.getCust_ContactNo() );
					preparedStatementSaleInvoiceTable.setString(6, json.getCust_gstNo());
					preparedStatementSaleInvoiceTable.setString(7,json.getCompanyName() );	
					preparedStatementSaleInvoiceTable.setString(8,json.getCust_address() );	
					
					
					//PRODUCT INFO
					preparedStatementSaleInvoiceTable.setString(9, jsonObj.get("ProductId").toString());
					preparedStatementSaleInvoiceTable.setString(10,jsonObj.get("Item").toString() );
					preparedStatementSaleInvoiceTable.setString(11,jsonObj.get("ProductType").toString() );	
					preparedStatementSaleInvoiceTable.setString(12, jsonObj.get("Qty").toString());
					preparedStatementSaleInvoiceTable.setString(13, jsonObj.get("Rate").toString());
					preparedStatementSaleInvoiceTable.setString(14, jsonObj.get("Total").toString());
					preparedStatementSaleInvoiceTable.setString(15, jsonObj.get("DiscountPercentage").toString());
					preparedStatementSaleInvoiceTable.setString(16, jsonObj.get("DiscountAmt").toString());
					preparedStatementSaleInvoiceTable.setString(17,jsonObj.get("SubTotal").toString() );
					preparedStatementSaleInvoiceTable.setString(18,jsonObj.get("CGST_Percentage").toString() ); 
					preparedStatementSaleInvoiceTable.setString(19,jsonObj.get("SGST_Percentage").toString() );	
					preparedStatementSaleInvoiceTable.setString(20, "0"); //igsta
					preparedStatementSaleInvoiceTable.setString(21, jsonObj.get("FinalAmt").toString());
					preparedStatementSaleInvoiceTable.setString(22, json.getProduct_serviceByStaffName()); //serviceby
					preparedStatementSaleInvoiceTable.setString(23,json.getProduct_serviceByStaffId() ); //staffid
				
					//INVOICE INFO
					
					preparedStatementSaleInvoiceTable.setString(24, json.getInvoiceNo());
					preparedStatementSaleInvoiceTable.setString(25,orderNo);
					preparedStatementSaleInvoiceTable.setString(26, json.getInvoiceDate());
					preparedStatementSaleInvoiceTable.setString(27, json.getInvoiceDueDate());
					preparedStatementSaleInvoiceTable.setString(28, json.getDate()); //date
					preparedStatementSaleInvoiceTable.setString(29,json.getTotalCGSTAmt() );	//totalcgst
					preparedStatementSaleInvoiceTable.setString(30, json.getTotalSGSTAmt());  //totalsgst
					preparedStatementSaleInvoiceTable.setString(31, "0"); //totaligst
					preparedStatementSaleInvoiceTable.setString(32,json.getTotalGst() );	
					preparedStatementSaleInvoiceTable.setString(33,json.getInvoiceAmt() );  
					preparedStatementSaleInvoiceTable.setString(34, json.getDiscountAmt());
					preparedStatementSaleInvoiceTable.setString(35, json.getInvoiceAmt_Paid()); //advance
					preparedStatementSaleInvoiceTable.setString(36, json.getBalanceAmt()); //balanceamt
			
					preparedStatementSaleInvoiceTable.setString(37, json.getInvoiceAdvisor()); //advisorname
					preparedStatementSaleInvoiceTable.setString(38, json.getInvoiceAdvisorId());
					preparedStatementSaleInvoiceTable.setString(39, json.getTotal_Itemqty());
					preparedStatementSaleInvoiceTable.setString(40,json.getPaymentStatus() );
					preparedStatementSaleInvoiceTable.setString(41, json.getPaymentMode());
					preparedStatementSaleInvoiceTable.setString(42,json.getSite() );	
					
								            
					preparedStatementSaleInvoiceTable.setString(43,json.getBookingId());
					preparedStatementSaleInvoiceTable.setString(44, json.getVehicleRegNo());
					preparedStatementSaleInvoiceTable.setString(45,json.getVehicleMake());	
					preparedStatementSaleInvoiceTable.setString(46, json.getVehicleModel());
					preparedStatementSaleInvoiceTable.setString(47,json.getFuelType());	
							
					preparedStatementSaleInvoiceTable.setString(48,jsonObj.get("CGSTAmt").toString());	
					preparedStatementSaleInvoiceTable.setString(49, jsonObj.get("SGSTAmt").toString() );
					preparedStatementSaleInvoiceTable.setString(50,"0");	
					preparedStatementSaleInvoiceTable.setString(51,invoiceReferenceNo);	
					
					preparedStatementSaleInvoiceTable.addBatch();
	            }
	            
	            insufficientProductList.add(insufficientInvoiceData);
	        }
	        
	        
	        if(!json.getInvoiceResponse().equals("InsufficientQuantity")) {
	        	
	        	//INSERT DATA INTO RESPECTIVE TABLES
	        	
	        	//INSERT DATA INTO SALE INVOICE TABLE
	        	
	        	int[] insertCount = preparedStatementSaleInvoiceTable.executeBatch();
	        	
	        	int[] updateProductCount = preparedStatementProductTable.executeBatch();
		       
	            connection.commit();
	            connection.setAutoCommit(true);
	        	
	        	
	        	//INSERT DATA INTO CUSTOMER STATEMENT TABLE
	            	       		
	        	String queryInsert_Cust_Statement = SaleInvoiceQueryConstants.CUSTOMER_STATEMENT_INSERT;
				PreparedStatement preparedStmtInsert_Cust_Statement = connection.prepareStatement(queryInsert_Cust_Statement);

				preparedStmtInsert_Cust_Statement.setString(1, json.getCompanyId());
				preparedStmtInsert_Cust_Statement.setString(2, json.getCustomerId());
				preparedStmtInsert_Cust_Statement.setString(3, json.getCustomerName());
				preparedStmtInsert_Cust_Statement.setString(4, json.getCust_address()); 
				preparedStmtInsert_Cust_Statement.setString(5, json.getCust_EmailId());
				preparedStmtInsert_Cust_Statement.setString(6, json.getCust_gstNo());
				preparedStmtInsert_Cust_Statement.setString(7, json.getInvoiceNo());
				preparedStmtInsert_Cust_Statement.setString(8, json.getInvoiceAmt_Paid());
				preparedStmtInsert_Cust_Statement.setString(9, json.getDiscountAmt());
				preparedStmtInsert_Cust_Statement.setString(10, json.getInvoiceAmt()); 
				preparedStmtInsert_Cust_Statement.setString(11, json.getDate()); 
				preparedStmtInsert_Cust_Statement.setString(12, json.getBalanceAmt());
				preparedStmtInsert_Cust_Statement.setString(13, json.getPaymentMode());
				preparedStmtInsert_Cust_Statement.setString(14, json.getSite());
				preparedStmtInsert_Cust_Statement.setString(15, invoiceReferenceNo);
				
				
				preparedStmtInsert_Cust_Statement.executeUpdate();
				
	        	
	        	
	        	//INSERT ENQUIRY DATA IF AVAILABLE & OPTED
	            if(json.getInvoice_withEnquiry().equals("yes")) {
	            	SaleInvoiceLogic.InsertInvoiceEnquiryData(json,connection,invoiceNo);
	            }
	            
	            
	          //UPDATE ORDER NO INTO CUSTOMER TABLE
				SaleInvoiceLogic.Update_Invoice_OrderNumber(json,connection,"CustomerTable");
				
				
			 //UPDATE THE BOOKING DETAILS STATUS IF INVOICE IS MADE FOR A JOB CARD DETAILS
				if(!json.getBookingId().equals("") && json.getBookingId()!=null) {
				SaleInvoiceLogic.Update_BookingDetails_Status(json,connection);
				}
				
				
				
			//INSERT INTO TOWEDETAILS TABLE
				if( (!json.getDealer().equals("") &&  json.getDealer()!=null )
						|| (!json.getFromAddress().equals("") && json.getFromAddress()!=null) 
						|| (!json.getToAddress().equals("") && json.getToAddress()!=null) 
						|| (!json.getRemark().equals("")  && json.getRemark()!=null )){
						  
					SaleInvoiceLogic.Insert_ToweDetails(json,connection);
					SaleInvoiceLogic.Select_Inserted_ToweDetails(json,connection);
					SaleInvoiceLogic.Update_Inserted_ToweId(json,connection);
				

			 }
				
	        	
				MasterDao.AuditReport(json.getStaffId(),json.getStaffName(),json.getStaffRole(), json.getCustomerId(),json.getCustomerName(),"Sale Invoice Generated",json.getCompanyId());

	        	json.setInvoiceResponse("Success");
	        	
	        	
	        }
	        
	        
	        SaleInvoiceLogic.SelectBasicDetails(json,connection);
	        json.setInsufficientProductList(insufficientProductList);
	        
	        return json;
	        
	}



/*
 * FUNCTION FOR GETTING INVOICE NO
 * FROM SALE INVOICE TABLE  & ESTIMATE INVOICE TABLE
 */
	public static String GetInvoiceNumber(SaleInvoiceJSON json, Connection connection,
			String invoiceTye,String invoiceTableName) throws SQLException {
		
		String invoiceNumber = "" ;
			
		if(invoiceTye.equals("SaleInvoice")) {
			invoiceNumber = "INV-0";
		}else if(invoiceTye.equals("EstimateInvoice")) {
			invoiceNumber = "EST-0";
		}
		   
			String querySelectInvoiceNo= SaleInvoiceQueryConstants.SELECT_INVOICENO_ORDERNO.replace("$InvoiceTableName",invoiceTableName);
		   PreparedStatement preparedStmtSelectInvoiceNo = connection.prepareStatement(querySelectInvoiceNo);
		   preparedStmtSelectInvoiceNo.setString(1, json.getCompanyId());
			ResultSet rsSelectInvoiceNo = preparedStmtSelectInvoiceNo.executeQuery();
			while (rsSelectInvoiceNo.next()) {
				
				if (rsSelectInvoiceNo.getString("invoiceNo") != null) {
					invoiceNumber = rsSelectInvoiceNo.getString("invoiceNo");
				}
				
			}
			
			String[] data = invoiceNumber.split("-");
			int result = Integer.parseInt(data[1]);
			int invoiceNumber1 = result + 1;
		
			
			String invoiceNo = "";
			
			if(invoiceTye.equals("SaleInvoice")) {
				invoiceNo = String.format("INV-%s", invoiceNumber1);
			}else if(invoiceTye.equals("EstimateInvoice")) {
				invoiceNo = String.format("EST-%s", invoiceNumber1);
			}
			
		
			
		return invoiceNo;
	}

	/*
	 * FUCNTION FOR GETTING ORDER NO
	 */
	public static String GetInvoiceOrderNumber(SaleInvoiceJSON json, Connection connection,
			String invoiceTye,String invoiceTableName) throws SQLException {
		

		int orderNo=0;
	
			String querySelectInvoiceNo= SaleInvoiceQueryConstants.SELECT_INVOICENO_ORDERNO.replace("$InvoiceTableName",invoiceTableName);
		   PreparedStatement preparedStmtSelectInvoiceNo = connection.prepareStatement(querySelectInvoiceNo);
		   preparedStmtSelectInvoiceNo.setString(1, json.getCompanyId());
			ResultSet rsSelectInvoiceNo = preparedStmtSelectInvoiceNo.executeQuery();
			while (rsSelectInvoiceNo.next()) {

				if (rsSelectInvoiceNo.getString("orderNumber") != null) {
					orderNo = Integer.parseInt(rsSelectInvoiceNo.getString("orderNumber"));
				}
			}
			
	System.out.println("ORDER NO FROM DB :"+orderNo);
			orderNo=orderNo+1;

		return String.valueOf(orderNo);
	}

	/*
	 * FUNCTION FOR GETTING 
	 * REFERENCE INVOICE NO
	 */
	public static String GetInvoiceReferenceNumber(SaleInvoiceJSON json, Connection connection, String invoiceTye,String invoiceTableName) throws SQLException {
	
		String siteCode=json.getSiteCode();
		
		if(json.getSiteCode().contentEquals("")) {
			siteCode="RIV";
		}
		
		System.out.println(" INVOICE BASED SITE CODE :"+siteCode);
		System.out.println(" INVOICE BASED SITE NAME :"+json.getSite());
		
		
		String recentReferenceInvoiceNumber = siteCode+"-0" ;
		
		
	   String querySelectReferenceInvoiceNo= SaleInvoiceQueryConstants.SELECT_INVOICENO_REFERENCENO.replace("$InvoiceTableName",invoiceTableName);
	   PreparedStatement preparedStmtSelectReferenceInvoiceNo = connection.prepareStatement(querySelectReferenceInvoiceNo);
	   preparedStmtSelectReferenceInvoiceNo.setString(1, json.getCompanyId());
	   preparedStmtSelectReferenceInvoiceNo.setString(2, json.getSite());
	   
		ResultSet rsSelectReferenceInvoiceNo = preparedStmtSelectReferenceInvoiceNo.executeQuery();
		while (rsSelectReferenceInvoiceNo.next()) {

			if (rsSelectReferenceInvoiceNo.getString("InvoiceReferenceNo") != null) {
				recentReferenceInvoiceNumber = rsSelectReferenceInvoiceNo.getString("InvoiceReferenceNo");
			}
		}
		
		System.out.println("OLD REFERENCE INVOICE NO :"+recentReferenceInvoiceNumber);
		String[] data = recentReferenceInvoiceNumber.split("-");
		int result = Integer.parseInt(data[1]);
		int referenceInvoiceNumber = result + 1;
	
		
		String referenceInvoiceNo = "";
		
		if( !json.getSiteCode().equals("") && json.getSiteCode()!=null) {
			referenceInvoiceNo = json.getSiteCode()+"-"+referenceInvoiceNumber;
		}else {
			referenceInvoiceNo="RIV"+"-"+String.valueOf(referenceInvoiceNumber);
		}
		
		System.out.println("NEW REFERENCE INVOICE NO :"+referenceInvoiceNo);
		
	return referenceInvoiceNo;
	}
	
	/*
	 * FUCNTION FOR INSERTING ENQUIRY DETAILS
	 * FROM SALE INVOICE/SALE INVOICE UPDATE
	 */
	public static void InsertInvoiceEnquiryData(SaleInvoiceJSON json, Connection connection, String invoiceNo) throws SQLException, JSONException {
	  	 
		
		json.setInvoiceEnquiryResponse("Failed");
		
		System.out.println("ENQUIRY MODULE CALLED \n");
		String INSERT_INVOICE_ENQUIRY = "INSERT INTO EnquiryTable(CompanyId,CustomerId,CustomerName,productName,"
            		+ "ProductType,Quantity,Date,StaffId,StaffName,Site,InvoiceId)"
            		+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        	   // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement_INVOICE_ENQUIRY  = connection.prepareStatement(INSERT_INVOICE_ENQUIRY) ;
                connection.setAutoCommit(false);
                
    		   JSONArray jsonEnquiryCartArr = new JSONArray(json.getEnquiryCartProduct());

    		   
    	        for (int i = 0; i < jsonEnquiryCartArr.length(); i++)
    	        {
    	            JSONObject jsonObj = jsonEnquiryCartArr.getJSONObject(i);

    	            System.out.println(jsonObj.get("Product"));
    	          
    	            		preparedStatement_INVOICE_ENQUIRY.setString(1, json.getCompanyId());
    	            		preparedStatement_INVOICE_ENQUIRY.setString(2, json.getCustomerId());
    	            		preparedStatement_INVOICE_ENQUIRY.setString(3, json.getCustomerName());
    	            		preparedStatement_INVOICE_ENQUIRY.setString(4,jsonObj.get("Product").toString());
    	            		preparedStatement_INVOICE_ENQUIRY.setString(5, jsonObj.get("ProductType").toString());
    	            		preparedStatement_INVOICE_ENQUIRY.setString(6, jsonObj.get("Quantity").toString());
    	            		preparedStatement_INVOICE_ENQUIRY.setString(7, json.getDate());
    	            		preparedStatement_INVOICE_ENQUIRY.setString(8, json.getStaffId());
    	            		preparedStatement_INVOICE_ENQUIRY.setString(9, json.getStaffName());
    	            		preparedStatement_INVOICE_ENQUIRY.setString(10, json.getSite());
    	            		preparedStatement_INVOICE_ENQUIRY.setString(11, invoiceNo);
    	            		preparedStatement_INVOICE_ENQUIRY.addBatch();
    	            
    	        }
    	
    	        int[] insertCount_NVOICE_ENQUIRY = preparedStatement_INVOICE_ENQUIRY.executeBatch();
    	        
    	  	        json.setInvoiceEnquiryResponse("Success");
    	        connection.commit();
              connection.setAutoCommit(true);
		
	}


	
	/*
	 * FUNCTION FOR ADDING ESTIMATE INVOICE DETAILS
	 */
	public static SaleInvoiceJSON AddEstimateInvoiceDetails(SaleInvoiceJSON json, Connection connection) throws JSONException, SQLException {


		ArrayList<SaleInvoiceJSON> insufficientProductList = new ArrayList<SaleInvoiceJSON>();	
		
		json.setInvoiceResponse("Failed");
		

		String INSERT_INVOICE_EstimateInvoiceTable = "insert into EstimateInvoiceTable(companyId,customerId,customerName," //CUSTOMERINFO
		   		+ "email,contactNo,gstNo,companyName,address," //CUSTOMERINFO
		   		+ "productId,productName,productType,quantity,rate," //PRODUCT INFO
		   		+ "total,discountPercentage,discountAmount,prefinalAmount," //PRODUCT INFO
		   		+ "finalAmount,serviceBy,staffId," //PRODUCT INFO
		   		+ "invoiceNo,orderNumber,invoiceDate,dueDate,date," //INVOICE INFO
		   		+ "subtotal1,"  //INVOICE INFO
		   		+ "discount,advance,balance_amount,advisor,advisorid,totalitemqty," //INVOICE INFO
		   		+ "payment_status,paymentmode,Site) "  //INVOICE INFO
		   		+ "VALUES(?,?,?,"
		   		+ "?,?,?,?,?,"
		   		+ "?,?,?,?,?,"
		   		+ "?,?,?,?,"
		   		+ "?,?,?,"
		   		+ "?,?,?,?,?,"
		   		+ "?,"
		   		+ "?,?,?,?,?,?,"
		   		+ "?,?,?)";
		

		
          PreparedStatement preparedStatementEstimateInvoiceTable = connection.prepareStatement(INSERT_INVOICE_EstimateInvoiceTable) ;
         // connection.setAutoCommit(false);
          
          
		  System.out.println("CART PRODUCT DATA :"+json.getCartProduct());
		   JSONArray jsonArr = new JSONArray(json.getCartProduct());

			  System.out.println("CART PRODUCT jsonArr  DATA :"+jsonArr);
		
			  //GET CURRENT INVOICE NO
			  /*
			   * PARAMETERS SENT ARE json,DB connection,invoicetype,table name from which invoice
			   * no is supposed to be feteched
			   */
		   String invoiceNo= SaleInvoiceLogic.GetInvoiceNumber(json,connection,"EstimateInvoice","EstimateInvoiceTable");
		   json.setInvoiceNo(invoiceNo);
			
	        for (int i = 1; i < jsonArr.length(); i++)
	        {
	            JSONObject jsonObj = jsonArr.getJSONObject(i);

	        
		   		
		   		
					//CUSTOMER INFO
					preparedStatementEstimateInvoiceTable.setString(1,json.getCompanyId() );
					preparedStatementEstimateInvoiceTable.setString(2, json.getCustomerId());
					preparedStatementEstimateInvoiceTable.setString(3, json.getCustomerName());
					preparedStatementEstimateInvoiceTable.setString(4, json.getCust_EmailId());  
					preparedStatementEstimateInvoiceTable.setString(5,json.getCust_ContactNo() );
					preparedStatementEstimateInvoiceTable.setString(6, json.getCust_gstNo());
					preparedStatementEstimateInvoiceTable.setString(7,json.getCompanyName() );	
					preparedStatementEstimateInvoiceTable.setString(8,json.getCust_address() );	
					
			
			  
			   		
					//PRODUCT INFO
					preparedStatementEstimateInvoiceTable.setString(9, jsonObj.get("ProductId").toString());
					preparedStatementEstimateInvoiceTable.setString(10,jsonObj.get("Item").toString() );
					preparedStatementEstimateInvoiceTable.setString(11,jsonObj.get("ProductType").toString() );	
					preparedStatementEstimateInvoiceTable.setString(12, jsonObj.get("Qty").toString());
					preparedStatementEstimateInvoiceTable.setString(13, jsonObj.get("Rate").toString());
					preparedStatementEstimateInvoiceTable.setString(14, jsonObj.get("Total").toString());
					preparedStatementEstimateInvoiceTable.setString(15, jsonObj.get("DiscountPercentage").toString());
					preparedStatementEstimateInvoiceTable.setString(16, jsonObj.get("DiscountAmt").toString());
					preparedStatementEstimateInvoiceTable.setString(17,jsonObj.get("SubTotal").toString() );
					preparedStatementEstimateInvoiceTable.setString(18, jsonObj.get("FinalAmt").toString());
					preparedStatementEstimateInvoiceTable.setString(19, json.getProduct_serviceByStaffName()); //serviceby
					preparedStatementEstimateInvoiceTable.setString(20,json.getProduct_serviceByStaffId() ); //staffid
		

					//INVOICE INFO
					preparedStatementEstimateInvoiceTable.setString(21, json.getInvoiceNo());
					preparedStatementEstimateInvoiceTable.setString(22, json.getOrderNo());
					preparedStatementEstimateInvoiceTable.setString(23, json.getInvoiceDate());
					preparedStatementEstimateInvoiceTable.setString(24, json.getInvoiceDueDate());
					preparedStatementEstimateInvoiceTable.setString(25, json.getDate()); //date
					preparedStatementEstimateInvoiceTable.setString(26,json.getInvoiceAmt() );  
					preparedStatementEstimateInvoiceTable.setString(27, json.getDiscountAmt());
					preparedStatementEstimateInvoiceTable.setString(28, json.getInvoiceAmt_Paid()); //advance
					preparedStatementEstimateInvoiceTable.setString(29, json.getBalanceAmt()); //balanceamt
			
					preparedStatementEstimateInvoiceTable.setString(30, json.getInvoiceAdvisor()); //advisorname
					preparedStatementEstimateInvoiceTable.setString(31, json.getInvoiceAdvisorId());
					preparedStatementEstimateInvoiceTable.setString(32, json.getTotal_Itemqty());
					preparedStatementEstimateInvoiceTable.setString(33,json.getPaymentStatus() );
					preparedStatementEstimateInvoiceTable.setString(34, json.getPaymentMode());
					preparedStatementEstimateInvoiceTable.setString(35,json.getSite() );	

					
					preparedStatementEstimateInvoiceTable.addBatch();

	            }
	            
	            
	        	
	        	//INSERT DATA INTO ESTIMATE INVOICE TABLE
	        	 connection.setAutoCommit(false);
	        	int[] insertCount = preparedStatementEstimateInvoiceTable.executeBatch();
	        	
		        connection.commit();
	            connection.setAutoCommit(true);
	        	
	        	
	        	//INSERT DATA INTO CUSTOMER STATEMENT TABLE       		
	            SaleInvoiceLogic.Insert_Into_EstimateStatement(json,connection);

	            
				//UPDATE ORDER NO INTO CUSTOMER TABLE
				SaleInvoiceLogic.Update_Invoice_OrderNumber(json,connection,"CustomerTable");
	        	
				MasterDao.AuditReport(json.getStaffId(),json.getInvoiceAdvisor(),json.getInvoiceAdvisorRole(), json.getCustomerId(),json.getCustomerName(),"Estimate Invoice Generated",json.getCompanyId());

	        	json.setInvoiceResponse("Success");

	        
	        SaleInvoiceLogic.SelectBasicDetails(json,connection);
	        json.setInsufficientProductList(insufficientProductList);
	        
	        return json;
	        
	}


	/*
	 * FUNCTION TO INSERT DATA INTO 
	 * ESTIMATE STATEMENT TABLE
	 */
	public static void Insert_Into_EstimateStatement(SaleInvoiceJSON json, Connection connection) throws SQLException {

		String queryInsert_Cust_Statement = SaleInvoiceQueryConstants.ESTIMATE_STATEMENT_INSERT;
		PreparedStatement preparedStmtInsert_Cust_Statement = connection.prepareStatement(queryInsert_Cust_Statement);

		preparedStmtInsert_Cust_Statement.setString(1, json.getCompanyId());
		preparedStmtInsert_Cust_Statement.setString(2, json.getCustomerId());
		preparedStmtInsert_Cust_Statement.setString(3, json.getCustomerName());
		preparedStmtInsert_Cust_Statement.setString(4, json.getCust_address()); 
		preparedStmtInsert_Cust_Statement.setString(5, json.getCust_EmailId());
		preparedStmtInsert_Cust_Statement.setString(6, json.getCust_gstNo());
		preparedStmtInsert_Cust_Statement.setString(7, json.getInvoiceNo());
		preparedStmtInsert_Cust_Statement.setString(8, json.getInvoiceAmt_Paid());
		preparedStmtInsert_Cust_Statement.setString(9, json.getDiscountAmt());
		preparedStmtInsert_Cust_Statement.setString(10, json.getInvoiceAmt()); 
		preparedStmtInsert_Cust_Statement.setString(11, json.getDate()); 
		preparedStmtInsert_Cust_Statement.setString(12, json.getBalanceAmt());
		preparedStmtInsert_Cust_Statement.setString(13, json.getPaymentMode());
		preparedStmtInsert_Cust_Statement.setString(14, json.getSite());
		preparedStmtInsert_Cust_Statement.executeUpdate();
		
	}



	/*
	 * FUCNTION FOR UPDATING INVOICE NO
	 * FOR SALEINVOICE & ESTIMATE INVOICE
	 */
	public static void Update_Invoice_OrderNumber(SaleInvoiceJSON json, Connection connection, String customerTableName) throws SQLException {

		String queryUpdate_Customer_OrderNo= SaleInvoiceQueryConstants.CUSTOMER_ORDER_NO.replace("$customerTableName",customerTableName);
		PreparedStatement preparedStmt_Customer_OrderNo = connection.prepareStatement(queryUpdate_Customer_OrderNo);
		preparedStmt_Customer_OrderNo.setString(1, json.getOrderNo());
		preparedStmt_Customer_OrderNo.setString(2, json.getCustomerId());
		preparedStmt_Customer_OrderNo.setString(3, json.getCompanyId());
		preparedStmt_Customer_OrderNo.executeUpdate();
		
	}


	/*
	 * FUNCTION USED TO UPDATE THE JOBCARD STATUS BASED ON IT'S CURRENT STATUS
	 */
	private static void Update_BookingDetails_Status(SaleInvoiceJSON json, Connection connection) throws SQLException {
					
		String dateColumnName="Completed_Closed_DeletedTimeStamp";
		
				if(json.getStatus().equals("4")) {
					dateColumnName="FinishedTimeStamp";
				}
		
		String queryUpdate_JobCard_Status= SaleInvoiceQueryConstants.BOOKINGDETAILSTABLE_STATUS_UPDATE.replace("$dateColumnName", dateColumnName);
		PreparedStatement preparedUpdate_JobCard_Status= connection.prepareStatement(queryUpdate_JobCard_Status);
		preparedUpdate_JobCard_Status.setString(1, json.getStatus());
		preparedUpdate_JobCard_Status.setString(2, json.getCompleted_closed_deleted_DateTime());
		preparedUpdate_JobCard_Status.setString(3, json.getCustomerId());
		preparedUpdate_JobCard_Status.setString(4, json.getBookingId());
		preparedUpdate_JobCard_Status.setString(5, json.getCompanyId());
		preparedUpdate_JobCard_Status.executeUpdate();
		
		
	}
	
	/*
	 * FUCNTION TO INSERT THE TOWE DETAILS
	 */
	private static void Insert_ToweDetails(SaleInvoiceJSON json, Connection connection) throws SQLException {

		String queryINS_TOWE = SaleInvoiceQueryConstants.INSERT_TOWE_DETAILS;
		PreparedStatement preparedStmt_INS_TWOE = connection.prepareStatement(queryINS_TOWE);
		preparedStmt_INS_TWOE.setString(1, json.getCompanyId());
		preparedStmt_INS_TWOE.setString(2, json.getInvoiceNo());
		preparedStmt_INS_TWOE.setString(3, json.getDealer());
		preparedStmt_INS_TWOE.setString(4, json.getFromAddress());
		preparedStmt_INS_TWOE.setString(5, json.getToAddress());
		preparedStmt_INS_TWOE.setString(6, json.getRemark());
		preparedStmt_INS_TWOE.setString(7, json.getSite());
		preparedStmt_INS_TWOE.executeUpdate();
			
		}
	
	

	/*
	 * FUNCTION TO UPDATE THE TOWEID 
	 * OF THE RECENTLY INSERTED TOWE INFO
	 */
private static void Select_Inserted_ToweDetails(SaleInvoiceJSON json, Connection connection) throws SQLException {

	String querySELECT_TOWEID = SaleInvoiceQueryConstants.SELECT_TOWE_ID;
	PreparedStatement preparedStmt_SELECT_TOWEID = connection.prepareStatement(querySELECT_TOWEID);
	preparedStmt_SELECT_TOWEID.setString(1, json.getInvoiceNo());
	preparedStmt_SELECT_TOWEID.setString(2, json.getCompanyId());
	
	ResultSet rsSELECT_TOWEID=preparedStmt_SELECT_TOWEID.executeQuery();
		while(rsSELECT_TOWEID.next()) {
			json.setToweId(rsSELECT_TOWEID.getString("ToweId"));
		}
		
	}
	
	/*
	 * FUNCTION TO SELECT THE TOWEID 
	 * OF THE RECENTLY INSERTED TOWE INFO
	 */
	private static void Update_Inserted_ToweId(SaleInvoiceJSON json, Connection connection) throws SQLException {

		
		String queryUPDATE_TOWEID = SaleInvoiceQueryConstants.UPDATE_TOWE_ID.replace("$InvoiceTableName", json.getTableName());
		PreparedStatement preparedStmt_UPDATE_TOWEID = connection.prepareStatement(queryUPDATE_TOWEID);
		preparedStmt_UPDATE_TOWEID.setString(1, json.getToweId());
		preparedStmt_UPDATE_TOWEID.setString(2, json.getCompanyId());
		preparedStmt_UPDATE_TOWEID.setString(3, json.getInvoiceNo());
		preparedStmt_UPDATE_TOWEID.executeUpdate();
		
	}
	
	/*
	 * FUNCTION TO UPDATING THE OLD TOWEID 
	 * STATUS
	 */ 	
	private static void Update_Old_ToweId_Status(SaleInvoiceJSON json, Connection connection) throws SQLException {


		System.out.println("Update_Old_ToweId_Status :"+json.getOldToweId());
		
		String queryUPDATE_OLD_TOWEID_STATUS = SaleInvoiceQueryConstants.UPDATE_OLD_TOWE_ID_STATUS;
		PreparedStatement preparedStmt_UPDATE_OLD_TOWEID_STATUS= connection.prepareStatement(queryUPDATE_OLD_TOWEID_STATUS);
		preparedStmt_UPDATE_OLD_TOWEID_STATUS.setString(1, json.getCompanyId());
		preparedStmt_UPDATE_OLD_TOWEID_STATUS.setString(2, json.getOldToweId());
		preparedStmt_UPDATE_OLD_TOWEID_STATUS.executeUpdate();
		
		
	}



	
	/*
	 * FUNCTION FOR SELECTING 
	 * BASIC INFO REQUIRED FOR UPDATING 
	 * THE INVOICE
	 */

	public static void SelectBasicDetailsUpdate(SaleInvoiceJSON json, Connection connection) throws SQLException {

		//GET INVOICE DETAILS
		SaleInvoiceLogic.GetInvoiceDetails(json,connection);
		
		//GET CUSTOMER DETAILS
		SaleInvoiceLogic.GetCustomerDetails(json,connection);
		
		//GET STAFF DETAILS
		SaleInvoiceLogic.GetStaffDetails(json,connection);
		
		//GET PRODUCT DETAILS
		SaleInvoiceLogic.GetProductDetails(json,connection);
		
		//GET VEHICLE DETAILS
		SaleInvoiceLogic.GetVehicleDetails(json,connection);
		
		//GET BOOKING DETAILS / JOBCARD DETAILS
		SaleInvoiceLogic.GetJobCardDetails(json,connection,"UpdateInvoice");
		
	}
	


	/*
	 * FUNCTION FOR SELECTING 
	 * SPECIFIC INFO REGARDING THE INVOICE
	 */
	private static void GetInvoiceDetails(SaleInvoiceJSON json, Connection connection) throws SQLException {
		
		ArrayList<SaleInvoiceJSON> invoiceDetailsList = new ArrayList<SaleInvoiceJSON>();	
		
		String queryInvoiceDetails= SaleInvoiceQueryConstants.GET_INVOICE_DETAILS.replace("$InvoiceTableName",json.getTableName());
		PreparedStatement preparedStmt_InvoiceDetails= connection.prepareStatement(queryInvoiceDetails);
		preparedStmt_InvoiceDetails.setString(1, json.getCompanyId());
		preparedStmt_InvoiceDetails.setString(2, json.getInvoiceNo());
		ResultSet rsInvoiceDetails=preparedStmt_InvoiceDetails.executeQuery();
		while(rsInvoiceDetails.next()) {
			SaleInvoiceJSON invoiceUpdateData=new SaleInvoiceJSON();
			
			//CUSTOMER INFO
			invoiceUpdateData.setCustomerId(rsInvoiceDetails.getString("CustomerId"));
			
			//BOOKING ID INFO
			invoiceUpdateData.setBookingId(rsInvoiceDetails.getString("BookingId"));
			
			//CART INFO
			invoiceUpdateData.setProductId(rsInvoiceDetails.getString("productId"));
			invoiceUpdateData.setProductName(rsInvoiceDetails.getString("productname"));
			invoiceUpdateData.setProductType(rsInvoiceDetails.getString("productType"));
			invoiceUpdateData.setProductQty(rsInvoiceDetails.getString("quantity"));
			invoiceUpdateData.setProductRate(rsInvoiceDetails.getString("rate"));
			invoiceUpdateData.setProductTotal(rsInvoiceDetails.getString("total"));
			
			invoiceUpdateData.setProductDiscountPercentage(rsInvoiceDetails.getString("discountPercentage"));
			invoiceUpdateData.setProductDiscountAmount(rsInvoiceDetails.getString("discountAmount"));
			
			invoiceUpdateData.setProductSubTotal(rsInvoiceDetails.getString("prefinalAmount"));
			
			invoiceUpdateData.setProduct_CGST_Percentage(rsInvoiceDetails.getString("cgsta"));
			invoiceUpdateData.setProduct_SGST_Percentage(rsInvoiceDetails.getString("sgsta"));
			invoiceUpdateData.setProduct_IGST_Percentage(rsInvoiceDetails.getString("igsta"));
			
			invoiceUpdateData.setProduct_CGST_Amt(rsInvoiceDetails.getString("cgstp"));
			invoiceUpdateData.setProduct_SGST_Amt(rsInvoiceDetails.getString("sgstp"));
			invoiceUpdateData.setProduct_IGST_Amt(rsInvoiceDetails.getString("igstp"));
			
			invoiceUpdateData.setProductFinalAmt(rsInvoiceDetails.getString("finalamount"));
			
			invoiceUpdateData.setProduct_serviceByStaffId(rsInvoiceDetails.getString("staffId"));
			invoiceUpdateData.setProduct_serviceByStaffName(rsInvoiceDetails.getString("serviceBy"));
			
					
			//INVOICE INFO
			invoiceUpdateData.setInvoiceDate(rsInvoiceDetails.getString("Invoicedate"));
			invoiceUpdateData.setInvoiceDueDate(rsInvoiceDetails.getString("duedate"));
			invoiceUpdateData.setTotalGst(rsInvoiceDetails.getString("totalgst"));
			invoiceUpdateData.setInvoiceAmt(rsInvoiceDetails.getString("subtotal1"));
			invoiceUpdateData.setDiscountAmt(rsInvoiceDetails.getString("discount"));
			invoiceUpdateData.setInvoiceAmt_Paid(rsInvoiceDetails.getString("advance"));
			invoiceUpdateData.setBalanceAmt(rsInvoiceDetails.getString("balance_amount"));
			
			invoiceUpdateData.setInvoiceAdvisorId(rsInvoiceDetails.getString("AdvisorId"));
			invoiceUpdateData.setInvoiceAdvisorId(rsInvoiceDetails.getString("advisor"));
			invoiceUpdateData.setPaymentStatus(rsInvoiceDetails.getString("payment_status"));
			invoiceUpdateData.setPaymentMode(rsInvoiceDetails.getString("PaymentMode"));
			invoiceUpdateData.setSite(rsInvoiceDetails.getString("Site"));
			
			//VEHICLE INFO
			invoiceUpdateData.setVehicleRegNo(rsInvoiceDetails.getString("vehicleRegistrationNo"));
			invoiceUpdateData.setVehicleMake(rsInvoiceDetails.getString("VehicleMake"));
			invoiceUpdateData.setVehicleModel(rsInvoiceDetails.getString("VehicleModel"));
			invoiceUpdateData.setFuelType(rsInvoiceDetails.getString("VehicleFuelType"));
			
			//TOWE INFO
			invoiceUpdateData.setDealer(rsInvoiceDetails.getString("DealerName"));
			invoiceUpdateData.setFromAddress(rsInvoiceDetails.getString("FromAddress"));
			invoiceUpdateData.setToAddress(rsInvoiceDetails.getString("ToAddress"));
			invoiceUpdateData.setRemark(rsInvoiceDetails.getString("Remark"));
			invoiceUpdateData.setOldToweId(rsInvoiceDetails.getString("ToweId"));
			
			invoiceDetailsList.add(invoiceUpdateData);
			
		}
		
		json.setInvoiceProductList(invoiceDetailsList);
		
	}


	/*
	 * FUNCTION FOR UPDATING THE SALE INVOICE
	 */
	public static SaleInvoiceJSON UpdateSaleInvoiceDetails(SaleInvoiceJSON json, Connection connection) throws SQLException, JSONException {
	
		ArrayList<SaleInvoiceJSON> insufficientProductList = new ArrayList<SaleInvoiceJSON>();	
		
		json.setInvoiceResponse("Failed");
		
		//SALE INVOICE TABLE INSERT QUERY 
		String INSERT_INVOICE_SaleInvoiceTable = "INSERT into SaleInvoiceTable(companyId,customerId,customerName," //CUSTOMERINFO
		   		+ "email,contactNo,gstNo,companyName,address," //CUSTOMERINFO
		   		+ "productId,productName,productType,quantity,rate," //PRODUCT INFO
		   		+ "total,discountPercentage,discountAmount,prefinalAmount,cgsta," //PRODUCT INFO
		   		+ "sgsta,igsta,finalAmount,serviceBy,staffId," //PRODUCT INFO
		   		+ "invoiceNo,orderNumber,invoiceDate,dueDate,date," //INVOICE INFO
		   		+ "totalcgst,totalsgst,totaligst,totalgst,subtotal1,"  //INVOICE INFO
		   		+ "discount,advance,balance_amount,advisor,advisorid,totalitemqty," //INVOICE INFO
		   		+ "payment_status,paymentmode,Site,Status,"
		   		+ "BookingId,vehicleRegistrationNo,vehicleMake,vehicleModel,vehicleFuelType, "  //INVOICE INFO
		   		+ "cgstp,sgstp,igstp,ReferenceInvoiceNo) "  //PRODUCT INFO
		   		+ "VALUES(?,?,?,"
		   		+ "?,?,?,?,?,"
		   		+ "?,?,?,?,?,"
		   		+ "?,?,?,?,?,"
		   		+ "?,?,?,?,?,"
		   		+ "?,?,?,?,?,"
		   		+ "?,?,?,?,?,"
		   		+ "?,?,?,?,?,?,"
		   		+ "?,?,?,?,"
		   		+ "?,?,?,?,?,"
		   		+ "?,?,?,?)";
		

		
          PreparedStatement preparedStatementSaleInvoiceTable = connection.prepareStatement(INSERT_INVOICE_SaleInvoiceTable) ;
         // connection.setAutoCommit(false);

          //PRODUCT TABLE INVENTORY UPDATE QUERY FOR ADDED ITEMS
          String UPDATE_MINUS_PRODUCT_QUANTITY="UPDATE ProductTable SET quantity=quantity-? "
          		+ "where productId = ? and companyId= ? ";
          PreparedStatement preparedStatementMinusProductTableQty = connection.prepareStatement(UPDATE_MINUS_PRODUCT_QUANTITY) ;
          
          
          //PRODUCT TABLE INVENTORY UPDATE QUERY FOR REDUCED ITEMS
          String UPDATE_ADD_PRODUCT_QUANTITY="UPDATE ProductTable SET quantity=quantity + ? "
          		+ "where productId = ? and companyId= ? ";
          PreparedStatement preparedStatementAddProductTableQty = connection.prepareStatement(UPDATE_ADD_PRODUCT_QUANTITY) ;
                  
          connection.setAutoCommit(false);
          
		  System.out.println("CART PRODUCT DATA :"+json.getCartProduct());
		   JSONArray jsonArr = new JSONArray(json.getCartProduct());
		   JSONArray jsonDeleteArr = new JSONArray(json.getDeletedProductList());

			  System.out.println("CART PRODUCT jsonArr  DATA :"+jsonArr);
			  
		   //GET CURRENT INVOICE NO
			  /*
			   * PARAMETERS SENT ARE json,DB connection,invoicetype,table name from which invoice
			   * no is supposed to be feteched
			   */
			  
			  
		//   String invoiceNo= SaleInvoiceLogic.GetInvoiceNumber(json,connection,"SaleInvoice","SaleInvoiceTable");
		//   json.setInvoiceNo(invoiceNo);
			
			String orderNo=SaleInvoiceLogic.GetInvoiceOrderNumber(json,connection,"SaleInvoice","SaleInvoiceTable");
				
		   System.out.println("ORDER NO :"+orderNo);
		   
		   //UPDATE INVOICE DELETED PRODUCT QTY INTO INVENTORY
	        for (int i = 1; i < jsonDeleteArr.length(); i++)
	        {
	        	 JSONObject jsonObj = jsonDeleteArr.getJSONObject(i);

				preparedStatementAddProductTableQty.setString(1, jsonObj.get("Qty").toString() );
				preparedStatementAddProductTableQty.setString(2, jsonObj.get("ProductId").toString() );
				preparedStatementAddProductTableQty.setString(3, json.getCompanyId() );
				preparedStatementAddProductTableQty.addBatch();	
	        	
	        }
		   
	    	int[] updateAdd_DeletedProductQtyCount = preparedStatementAddProductTableQty.executeBatch();
		    
            connection.commit();
            connection.setAutoCommit(true);
		   
            connection.setAutoCommit(false);
		   
		   
	        for (int i = 1; i < jsonArr.length(); i++)
	        {
	            JSONObject jsonObj = jsonArr.getJSONObject(i);

	            SaleInvoiceJSON insufficientInvoiceData=new SaleInvoiceJSON();
	            
	            //CHECK IF REQUIRED QTY OF THE PRODUCT IS PRESENT IN THE DB
	            if(jsonObj.getString("ProductType").equals("product")) {
	            	
	            	String productQty=jsonObj.get("NewQty").toString();
	            	String dbQuantity="0";
	            	
	            	//GET CURRENT QTY FROM DB
	            	
	            	String querySelectGetQTY = QueryConstants.GET_QUANTITY;
					PreparedStatement preparedStmtGetQTY = connection.prepareStatement(querySelectGetQTY);
					preparedStmtGetQTY.setString(1, jsonObj.getString("ProductId"));
					preparedStmtGetQTY.setString(2, json.getCompanyId());
					ResultSet rsGetQTY = preparedStmtGetQTY.executeQuery();
					while (rsGetQTY.next()) {
						
						dbQuantity = rsGetQTY.getString("Quantity");

						if (Double.parseDouble(dbQuantity) < Double.parseDouble(productQty)) {
							json.setInvoiceResponse("InsufficientQuantity");
							productQty=dbQuantity;
						}
						
					}
					
					
					insufficientInvoiceData.setProductId(jsonObj.get("ProductId").toString());
					insufficientInvoiceData.setProductName(jsonObj.get("Item").toString());
					insufficientInvoiceData.setProductType(jsonObj.get("ProductType").toString());
					insufficientInvoiceData.setProductRate(jsonObj.get("Rate").toString());
					insufficientInvoiceData.setProductQty(productQty);
					insufficientInvoiceData.setProductTotal(jsonObj.get("Total").toString());             
					insufficientInvoiceData.setProductDiscountPercentage(jsonObj.get("DiscountPercentage").toString());                        
					insufficientInvoiceData.setProductDiscountAmount(jsonObj.get("DiscountAmt").toString());                              
					insufficientInvoiceData.setProductSubTotal(jsonObj.get("SubTotal").toString());                           
					insufficientInvoiceData.setProduct_CGST_Percentage(jsonObj.get("CGST_Percentage").toString());
					insufficientInvoiceData.setProduct_CGST_Amt(jsonObj.get("CGSTAmt").toString());                                  
					insufficientInvoiceData.setProduct_SGST_Percentage(jsonObj.get("SGST_Percentage").toString());
			        insufficientInvoiceData.setProduct_SGST_Amt(jsonObj.get("SGSTAmt").toString());                        
					insufficientInvoiceData.setProductFinalAmt(jsonObj.get("FinalAmt").toString());  
										
					//CUSTOMER INFO
					preparedStatementSaleInvoiceTable.setString(1,json.getCompanyId() );
					preparedStatementSaleInvoiceTable.setString(2, json.getCustomerId());
					preparedStatementSaleInvoiceTable.setString(3, json.getCustomerName());
					preparedStatementSaleInvoiceTable.setString(4, json.getCust_EmailId());  
					preparedStatementSaleInvoiceTable.setString(5,json.getCust_ContactNo() );
					preparedStatementSaleInvoiceTable.setString(6, json.getCust_gstNo());
					preparedStatementSaleInvoiceTable.setString(7,json.getCompanyName() );	
					preparedStatementSaleInvoiceTable.setString(8,json.getCust_address() );	
					
					
					//PRODUCT INFO
					preparedStatementSaleInvoiceTable.setString(9, jsonObj.get("ProductId").toString());
					preparedStatementSaleInvoiceTable.setString(10,jsonObj.get("Item").toString());
					preparedStatementSaleInvoiceTable.setString(11,jsonObj.get("ProductType").toString() );	
					preparedStatementSaleInvoiceTable.setString(12, jsonObj.get("Qty").toString());
					preparedStatementSaleInvoiceTable.setString(13, jsonObj.get("Rate").toString());
					preparedStatementSaleInvoiceTable.setString(14, jsonObj.get("Total").toString());
					preparedStatementSaleInvoiceTable.setString(15, jsonObj.get("DiscountPercentage").toString());
					preparedStatementSaleInvoiceTable.setString(16, jsonObj.get("DiscountAmt").toString());
					preparedStatementSaleInvoiceTable.setString(17,jsonObj.get("SubTotal").toString() );
					preparedStatementSaleInvoiceTable.setString(18,jsonObj.get("CGST_Percentage").toString()); 
					preparedStatementSaleInvoiceTable.setString(19,jsonObj.get("SGST_Percentage").toString());	
					preparedStatementSaleInvoiceTable.setString(20, "0"); //igsta
					preparedStatementSaleInvoiceTable.setString(21, jsonObj.get("FinalAmt").toString());
					preparedStatementSaleInvoiceTable.setString(22, json.getProduct_serviceByStaffName()); //serviceby
					preparedStatementSaleInvoiceTable.setString(23,json.getProduct_serviceByStaffId() ); //staffid
				
					//INVOICE INFO
					preparedStatementSaleInvoiceTable.setString(24, json.getInvoiceNo());
					preparedStatementSaleInvoiceTable.setString(25, orderNo);
					preparedStatementSaleInvoiceTable.setString(26, json.getInvoiceDate());
					preparedStatementSaleInvoiceTable.setString(27, json.getInvoiceDueDate());
					preparedStatementSaleInvoiceTable.setString(28, json.getDate()); //date
					preparedStatementSaleInvoiceTable.setString(29,json.getTotalCGSTAmt() );	//totalcgst
					preparedStatementSaleInvoiceTable.setString(30, json.getTotalSGSTAmt());  //totalsgst
					preparedStatementSaleInvoiceTable.setString(31, "0"); //totaligst
					preparedStatementSaleInvoiceTable.setString(32,json.getTotalGst() );	
					preparedStatementSaleInvoiceTable.setString(33,json.getInvoiceAmt() );  
					preparedStatementSaleInvoiceTable.setString(34, json.getDiscountAmt());
					preparedStatementSaleInvoiceTable.setString(35, json.getInvoiceAmt_Paid()); //advance
					preparedStatementSaleInvoiceTable.setString(36, json.getBalanceAmt()); //balanceamt
			
					preparedStatementSaleInvoiceTable.setString(37, json.getInvoiceAdvisor());
					preparedStatementSaleInvoiceTable.setString(38, json.getInvoiceAdvisorId());
					preparedStatementSaleInvoiceTable.setString(39, json.getTotal_Itemqty());
					preparedStatementSaleInvoiceTable.setString(40,json.getPaymentStatus() );
					preparedStatementSaleInvoiceTable.setString(41, json.getPaymentMode());
					preparedStatementSaleInvoiceTable.setString(42,json.getSite() );	
					preparedStatementSaleInvoiceTable.setString(43,"3");	
					
					preparedStatementSaleInvoiceTable.setString(44,json.getBookingId());
					preparedStatementSaleInvoiceTable.setString(45, json.getVehicleRegNo());
					preparedStatementSaleInvoiceTable.setString(46,json.getVehicleMake());	
					preparedStatementSaleInvoiceTable.setString(47, json.getVehicleModel());
					preparedStatementSaleInvoiceTable.setString(48,json.getFuelType());	
				
					preparedStatementSaleInvoiceTable.setString(49,jsonObj.get("CGSTAmt").toString());	
					preparedStatementSaleInvoiceTable.setString(50, jsonObj.get("SGSTAmt").toString() );
					preparedStatementSaleInvoiceTable.setString(51,"0");	
					preparedStatementSaleInvoiceTable.setString(52,json.getReferenceInvoiceNo());	
					
					preparedStatementSaleInvoiceTable.addBatch();
					
					//SETTING UP INVENTORY DATA
					if(jsonObj.get("NewQty").toString().equals("0")) {
						
						if(jsonObj.get("Qty").toString().equals(jsonObj.get("ExistedQty").toString())) {
							//THE PRODUCT IS NOT ADDED/SUBTRACTED SO NO INVENTORY UPDATE
							
						}else if(!jsonObj.get("Qty").toString().equals(jsonObj.get("ExistedQty").toString())) {
							//THE PRODUCT IS SUBTRACTED SO DO INVENTORY UPDATE
							double quantity_ToBe_Added=Double.parseDouble(jsonObj.get("ExistedQty").toString()) 
									- Double.parseDouble(jsonObj.get("Qty").toString()) ;
							
							preparedStatementAddProductTableQty.setString(1, String.valueOf(quantity_ToBe_Added) );
							preparedStatementAddProductTableQty.setString(2, jsonObj.get("ProductId").toString() );
							preparedStatementAddProductTableQty.setString(3, json.getCompanyId() );
							preparedStatementAddProductTableQty.addBatch();	
						}

					}else if(!jsonObj.get("NewQty").toString().equals("0")) {
						//THE PRODUCT IS  ADDED SO DO INVENTORY UPDATE
						
						String quantity_ToBe_Subtracted=jsonObj.get("NewQty").toString();
						
						preparedStatementMinusProductTableQty.setString(1, jsonObj.get("NewQty").toString() );
						preparedStatementMinusProductTableQty.setString(2, jsonObj.get("ProductId").toString() );
						preparedStatementMinusProductTableQty.setString(3, json.getCompanyId() );
						preparedStatementMinusProductTableQty.addBatch();
						
					}
	
	            }else if(jsonObj.getString("ProductType").equals("service")) {
	            
	            	insufficientInvoiceData.setProductId(jsonObj.get("ProductId").toString());
					insufficientInvoiceData.setProductName(jsonObj.get("Item").toString());
					insufficientInvoiceData.setProductType(jsonObj.get("ProductType").toString());
					insufficientInvoiceData.setProductRate(jsonObj.get("Rate").toString());
					insufficientInvoiceData.setProductQty(jsonObj.get("Qty").toString());
					insufficientInvoiceData.setProductTotal(jsonObj.get("Total").toString());             
					insufficientInvoiceData.setProductDiscountPercentage(jsonObj.get("DiscountPercentage").toString());                        
					insufficientInvoiceData.setProductDiscountAmount(jsonObj.get("DiscountAmt").toString());                              
					insufficientInvoiceData.setProductSubTotal(jsonObj.get("SubTotal").toString());                           
					insufficientInvoiceData.setProduct_CGST_Percentage(jsonObj.get("CGST_Percentage").toString());
					insufficientInvoiceData.setProduct_CGST_Amt(jsonObj.get("CGSTAmt").toString());                                  
					insufficientInvoiceData.setProduct_SGST_Percentage(jsonObj.get("SGST_Percentage").toString());
			        insufficientInvoiceData.setProduct_SGST_Amt(jsonObj.get("SGSTAmt").toString());                        
					insufficientInvoiceData.setProductFinalAmt(jsonObj.get("FinalAmt").toString());  
					
					
					//CUSTOMER INFO
					preparedStatementSaleInvoiceTable.setString(1,json.getCompanyId() );
					preparedStatementSaleInvoiceTable.setString(2, json.getCustomerId());
					preparedStatementSaleInvoiceTable.setString(3, json.getCustomerName());
					preparedStatementSaleInvoiceTable.setString(4, json.getCust_EmailId());  
					preparedStatementSaleInvoiceTable.setString(5,json.getCust_ContactNo() );
					preparedStatementSaleInvoiceTable.setString(6, json.getCust_gstNo());
					preparedStatementSaleInvoiceTable.setString(7,json.getCompanyName() );	
					preparedStatementSaleInvoiceTable.setString(8,json.getCust_address() );	
					
					
					//PRODUCT INFO
					preparedStatementSaleInvoiceTable.setString(9, jsonObj.get("ProductId").toString());
					preparedStatementSaleInvoiceTable.setString(10,jsonObj.get("Item").toString() );
					preparedStatementSaleInvoiceTable.setString(11,jsonObj.get("ProductType").toString() );	
					preparedStatementSaleInvoiceTable.setString(12, jsonObj.get("Qty").toString());
					preparedStatementSaleInvoiceTable.setString(13, jsonObj.get("Rate").toString());
					preparedStatementSaleInvoiceTable.setString(14, jsonObj.get("Total").toString());
					preparedStatementSaleInvoiceTable.setString(15, jsonObj.get("DiscountPercentage").toString());
					preparedStatementSaleInvoiceTable.setString(16, jsonObj.get("DiscountAmt").toString());
					preparedStatementSaleInvoiceTable.setString(17,jsonObj.get("SubTotal").toString() );
					preparedStatementSaleInvoiceTable.setString(18,jsonObj.get("CGST_Percentage").toString() ); 
					preparedStatementSaleInvoiceTable.setString(19,jsonObj.get("SGST_Percentage").toString() );	
					preparedStatementSaleInvoiceTable.setString(20, "0"); //igsta
					preparedStatementSaleInvoiceTable.setString(21, jsonObj.get("FinalAmt").toString());
					preparedStatementSaleInvoiceTable.setString(22, json.getProduct_serviceByStaffName()); //serviceby
					preparedStatementSaleInvoiceTable.setString(23,json.getProduct_serviceByStaffId() ); //staffid
				
					//INVOICE INFO
					
					preparedStatementSaleInvoiceTable.setString(24, json.getInvoiceNo());
					preparedStatementSaleInvoiceTable.setString(25,orderNo);
					preparedStatementSaleInvoiceTable.setString(26, json.getInvoiceDate());
					preparedStatementSaleInvoiceTable.setString(27, json.getInvoiceDueDate());
					preparedStatementSaleInvoiceTable.setString(28, json.getDate()); //date
					preparedStatementSaleInvoiceTable.setString(29,json.getTotalCGSTAmt() );	//totalcgst
					preparedStatementSaleInvoiceTable.setString(30, json.getTotalSGSTAmt());  //totalsgst
					preparedStatementSaleInvoiceTable.setString(31, "0"); //totaligst
					preparedStatementSaleInvoiceTable.setString(32,json.getTotalGst() );	
					preparedStatementSaleInvoiceTable.setString(33,json.getInvoiceAmt() );  
					preparedStatementSaleInvoiceTable.setString(34, json.getDiscountAmt());
					preparedStatementSaleInvoiceTable.setString(35, json.getInvoiceAmt_Paid()); //advance
					preparedStatementSaleInvoiceTable.setString(36, json.getBalanceAmt()); //balanceamt
			
					preparedStatementSaleInvoiceTable.setString(37, json.getInvoiceAdvisor()); //advisorname
					preparedStatementSaleInvoiceTable.setString(38, json.getInvoiceAdvisorId());
					preparedStatementSaleInvoiceTable.setString(39, json.getTotal_Itemqty());
					preparedStatementSaleInvoiceTable.setString(40,json.getPaymentStatus() );
					preparedStatementSaleInvoiceTable.setString(41, json.getPaymentMode());
					preparedStatementSaleInvoiceTable.setString(42,json.getSite() );	
					preparedStatementSaleInvoiceTable.setString(43,"3");	
								            
					preparedStatementSaleInvoiceTable.setString(44,json.getBookingId());
					preparedStatementSaleInvoiceTable.setString(45, json.getVehicleRegNo());
					preparedStatementSaleInvoiceTable.setString(46,json.getVehicleMake());	
					preparedStatementSaleInvoiceTable.setString(47, json.getVehicleModel());
					preparedStatementSaleInvoiceTable.setString(48,json.getFuelType());	
					
					preparedStatementSaleInvoiceTable.setString(49,jsonObj.get("CGSTAmt").toString());	
					preparedStatementSaleInvoiceTable.setString(50, jsonObj.get("SGSTAmt").toString() );
					preparedStatementSaleInvoiceTable.setString(51,"0");	
					preparedStatementSaleInvoiceTable.setString(52,json.getReferenceInvoiceNo());	
					
					preparedStatementSaleInvoiceTable.addBatch();
	            }
	            
	            insufficientProductList.add(insufficientInvoiceData);
	        }
	        
	        
	        if(!json.getInvoiceResponse().equals("InsufficientQuantity")) {
	        	
	        	//INSERT DATA INTO RESPECTIVE TABLES
	        	
	        	/*
	        	 * UPDATE THE STATUS OF SAME PREVIOUS INVOICE INTO 2 - WHICH MEANS ITS
	        	 * UPDATED & THE NEW INSTANCE OF THE SAME OLD INVOICE 
	        	 * WITH STATUS 3 - WHICH MEANS ITS NEW UPDATED ENTRY
	        	 */
	        	
	        	SaleInvoiceLogic.UpdateInvoiceStatus(json,connection,"SaleInvoiceTable");
	        		        	
	        	//INSERT DATA INTO SALE INVOICE TABLE
	        	
	        	int[] insertCount = preparedStatementSaleInvoiceTable.executeBatch();
	        	
	        	int[] updateAddProductQtyCount = preparedStatementAddProductTableQty.executeBatch();
		       
	        	int[] updateMinusProductQtyCount = preparedStatementMinusProductTableQty.executeBatch();
			      
	            
	            connection.commit();
	            connection.setAutoCommit(true);
	        	
	            
	            if(json.getOldPaymentStatus().equals("UnPaid")) {
	            	//UPDATE THE ENTRY SINCE NO PAYMENT IS MADE
	            	String queryUpdate_Cust_Statement = SaleInvoiceQueryConstants.CUSTOMER_STATEMENT_UPDATE;
					PreparedStatement preparedStmtUpdate_Cust_Statement = connection.prepareStatement(queryUpdate_Cust_Statement);
					
					preparedStmtUpdate_Cust_Statement .setString(1, json.getInvoiceAmt_Paid());
					preparedStmtUpdate_Cust_Statement.setString(2, json.getDiscountAmt());
					preparedStmtUpdate_Cust_Statement.setString(3, json.getInvoiceAmt()); 
					preparedStmtUpdate_Cust_Statement.setString(4, json.getDate()); 
					preparedStmtUpdate_Cust_Statement.setString(5, json.getBalanceAmt());
					preparedStmtUpdate_Cust_Statement.setString(6, json.getPaymentMode());
					preparedStmtUpdate_Cust_Statement.setString(7, json.getSite());
					preparedStmtUpdate_Cust_Statement.setString(8, json.getCompanyId());
					preparedStmtUpdate_Cust_Statement.setString(9, json.getCustomerId());
					preparedStmtUpdate_Cust_Statement.setString(10, json.getInvoiceNo());
					preparedStmtUpdate_Cust_Statement.setString(11, json.getReferenceInvoiceNo());
					
					
					
					preparedStmtUpdate_Cust_Statement.executeUpdate();
					
	            	
	            }else if(json.getOldPaymentStatus().equals("Paid") || json.getOldPaymentStatus().equals("PartiallyPaid") )  {
	            	//NO NEED TO DEAL WITH IT - SEPERATE PAGE IS BEING PROVIDED
	            	
	            	//HANDLE OLD INVOICE DETAILS  - CUSTOMER STATEMENT 
	            	            	
	            	
	            	//INSERT DATA INTO CUSTOMER STATEMENT TABLE
		        	String queryInsert_Cust_Statement = SaleInvoiceQueryConstants.CUSTOMER_STATEMENT_INSERT;
					PreparedStatement preparedStmtInsert_Cust_Statement = connection.prepareStatement(queryInsert_Cust_Statement);

					preparedStmtInsert_Cust_Statement.setString(1, json.getCompanyId());
					preparedStmtInsert_Cust_Statement.setString(2, json.getCustomerId());
					preparedStmtInsert_Cust_Statement.setString(3, json.getCustomerName());
					preparedStmtInsert_Cust_Statement.setString(4, json.getCust_address()); 
					preparedStmtInsert_Cust_Statement.setString(5, json.getCust_EmailId());
					preparedStmtInsert_Cust_Statement.setString(6, json.getCust_gstNo());
					preparedStmtInsert_Cust_Statement.setString(7, json.getInvoiceNo());
				//	preparedStmtInsert_Cust_Statement.setString(8, json.getInvoiceAmt_Paid());
					preparedStmtInsert_Cust_Statement.setString(8, "0");
				//	preparedStmtInsert_Cust_Statement.setString(9, json.getDiscountAmt());
					preparedStmtInsert_Cust_Statement.setString(9, "0");
					preparedStmtInsert_Cust_Statement.setString(10, json.getInvoiceAmt()); 
					preparedStmtInsert_Cust_Statement.setString(11, json.getDate()); 
					preparedStmtInsert_Cust_Statement.setString(12, json.getBalanceAmt());
					preparedStmtInsert_Cust_Statement.setString(13, json.getPaymentMode());
					preparedStmtInsert_Cust_Statement.setString(14, json.getSite());
					preparedStmtInsert_Cust_Statement.setString(11, json.getReferenceInvoiceNo());
					
					preparedStmtInsert_Cust_Statement.executeUpdate();
					
	            	
	            }

	        	//INSERT ENQUIRY DATA IF AVAILABLE & OPTED
	            if(json.getInvoice_withEnquiry().equals("yes")) {
	            	SaleInvoiceLogic.InsertInvoiceEnquiryData(json,connection,json.getInvoiceNo());
	            }
	            
	            
	          //UPDATE ORDER NO INTO CUSTOMER TABLE
				SaleInvoiceLogic.Update_Invoice_OrderNumber(json,connection,"CustomerTable");
	        	
	
			 //UPDATE THE BOOKING DETAILS STATUS IF INVOICE IS MADE FOR A JOB CARD DETAILS
				if(!json.getBookingId().equals("") && json.getBookingId()!=null ) {
					SaleInvoiceLogic.Update_BookingDetails_Status(json,connection);
					}
							
							
							
						//INSERT INTO TOWEDETAILS TABLE
							if( (!json.getDealer().equals("") &&  json.getDealer()!=null )
									|| (!json.getFromAddress().equals("") && json.getFromAddress()!=null) 
									|| (!json.getToAddress().equals("") && json.getToAddress()!=null) 
									|| (!json.getRemark().equals("")  && json.getRemark()!=null )){
									  
								System.out.println("json.getOldToweId() :"+json.getOldToweId());
								
								if(!json.getOldToweId().equals("") && json.getOldToweId()!=null
										&& !json.getOldToweId().equals("null")) {  
									
									SaleInvoiceLogic.Update_Old_ToweId_Status(json,connection);
								}
								
								SaleInvoiceLogic.Insert_ToweDetails(json,connection);
								SaleInvoiceLogic.Select_Inserted_ToweDetails(json,connection);
								SaleInvoiceLogic.Update_Inserted_ToweId(json,connection);

						 }
							
				MasterDao.AuditReport(json.getStaffId(),json.getInvoiceAdvisor(),json.getInvoiceAdvisorRole(), json.getCustomerId(),json.getCustomerName(),"Sale Invoice Generated",json.getCompanyId());

	        	json.setInvoiceResponse("Success");
	        	
	        	
	        }
	        
	        
	        SaleInvoiceLogic.SelectBasicDetails(json,connection);
	        json.setInsufficientProductList(insufficientProductList);
	        
	        return json;
		
	}

/*
 * FUCNTION FOR UPDATING THE 
 * INVOICE STATUS WHEN ITS UPDATED/EDITED
 */
	private static void UpdateInvoiceStatus(SaleInvoiceJSON json, Connection connection, String tableName) throws SQLException {

		
		System.out.println(" UpdateInvoiceStatus :"+"CMP_ID :"+json.getCompanyId()+" INVOICE_NO :"+json.getInvoiceNo());
		
	  	String queryUpdate_InvoiceStatus = SaleInvoiceQueryConstants.UPDATE_INVOICE_STATUS.replace("$invoiceTableName", tableName);
		PreparedStatement preparedStmtUpdate_InvoiceStatus = connection.prepareStatement(queryUpdate_InvoiceStatus);

		preparedStmtUpdate_InvoiceStatus.setString(1, json.getCompanyId());
		preparedStmtUpdate_InvoiceStatus.setString(2, json.getInvoiceNo());
		preparedStmtUpdate_InvoiceStatus.executeUpdate();
		
	}

	/*
	 * FUNCTION FOR SELECTING PARTICULAR INVOICE HISTORY
	 */
		public static void SelectInvoiceHistory(SaleInvoiceJSON json, Connection connection) throws SQLException {

			ArrayList<SaleInvoiceJSON> invoiceHistoryList=new ArrayList<SaleInvoiceJSON>();
			
			invoiceHistoryList.addAll(SaleInvoiceLogic.GetInvoiceHistory(json, connection));
			
			json.setInvoiceHistoryList(invoiceHistoryList);
			
		}
/*
 * FUNCTION FOR SELECTING PARTICULAR INVOICE HISTORY
 */
	public static ArrayList<SaleInvoiceJSON> GetInvoiceHistory(SaleInvoiceJSON json, Connection connection) throws SQLException {

		ArrayList<SaleInvoiceJSON> invoiceHistoryList=new ArrayList<SaleInvoiceJSON>();
		
	 	String queryInvoiceHistory = SaleInvoiceQueryConstants.SELECT_INVOICE_HISTORY.replace("$invoiceTableName", json.getTableName());
			PreparedStatement preparedStmtInvoiceHistory= connection.prepareStatement(queryInvoiceHistory);

			preparedStmtInvoiceHistory.setString(1, json.getCompanyId());
			preparedStmtInvoiceHistory.setString(2, json.getInvoiceNo());
			ResultSet rsInvoiceHistory=preparedStmtInvoiceHistory.executeQuery();
			while(rsInvoiceHistory.next()) {
				SaleInvoiceJSON invoiceHistoryData=new SaleInvoiceJSON();
				
				//CUSTOMER INFO
				invoiceHistoryData.setCustomerId(rsInvoiceHistory.getString("CustomerId"));
				invoiceHistoryData.setCustomerName(rsInvoiceHistory.getString("CustomerName"));
				invoiceHistoryData.setCust_ContactNo(rsInvoiceHistory.getString("ContactNo"));
				
			
				//BOOKING ID INFO
				invoiceHistoryData.setBookingId(rsInvoiceHistory.getString("BookingId"));
				
				//CART INFO
				invoiceHistoryData.setProductId(rsInvoiceHistory.getString("productId"));
				invoiceHistoryData.setProductName(rsInvoiceHistory.getString("productname"));
				invoiceHistoryData.setProductType(rsInvoiceHistory.getString("productType"));
				invoiceHistoryData.setProductQty(rsInvoiceHistory.getString("quantity"));
				invoiceHistoryData.setProductRate(rsInvoiceHistory.getString("rate"));
				invoiceHistoryData.setProductTotal(rsInvoiceHistory.getString("total"));
				
				invoiceHistoryData.setProductDiscountPercentage(rsInvoiceHistory.getString("discountPercentage"));
				invoiceHistoryData.setProductDiscountAmount(rsInvoiceHistory.getString("discountAmount"));
				
				invoiceHistoryData.setProductSubTotal(rsInvoiceHistory.getString("prefinalAmount"));
				
				invoiceHistoryData.setProduct_CGST_Percentage(rsInvoiceHistory.getString("cgsta"));
				invoiceHistoryData.setProduct_SGST_Percentage(rsInvoiceHistory.getString("sgsta"));
				invoiceHistoryData.setProduct_IGST_Percentage(rsInvoiceHistory.getString("igsta"));
				
				invoiceHistoryData.setProductFinalAmt(rsInvoiceHistory.getString("finalamount"));
				
				invoiceHistoryData.setProduct_serviceByStaffId(rsInvoiceHistory.getString("staffId"));
				invoiceHistoryData.setProduct_serviceByStaffName(rsInvoiceHistory.getString("serviceBy"));
				
						
				//INVOICE INFO
				invoiceHistoryData.setInvoiceNo(rsInvoiceHistory.getString("invoiceNo"));
				invoiceHistoryData.setInvoiceDate(rsInvoiceHistory.getString("Invoicedate"));
				invoiceHistoryData.setInvoiceDueDate(rsInvoiceHistory.getString("duedate"));
				invoiceHistoryData.setDate(rsInvoiceHistory.getString("Date"));
				invoiceHistoryData.setTotalGst(rsInvoiceHistory.getString("totalgst"));
				invoiceHistoryData.setInvoiceAmt(rsInvoiceHistory.getString("subtotal1"));
				invoiceHistoryData.setDiscountAmt(rsInvoiceHistory.getString("discount"));
				invoiceHistoryData.setInvoiceAmt_Paid(rsInvoiceHistory.getString("advance"));
				invoiceHistoryData.setBalanceAmt(rsInvoiceHistory.getString("balance_amount"));
				
				invoiceHistoryData.setInvoiceAdvisorId(rsInvoiceHistory.getString("AdvisorId"));
				invoiceHistoryData.setInvoiceAdvisorId(rsInvoiceHistory.getString("advisor"));
				invoiceHistoryData.setPaymentStatus(rsInvoiceHistory.getString("payment_status"));
				invoiceHistoryData.setPaymentMode(rsInvoiceHistory.getString("PaymentMode"));
				invoiceHistoryData.setSite(rsInvoiceHistory.getString("Site"));
				invoiceHistoryData.setReferenceInvoiceNo(rsInvoiceHistory.getString("ReferenceInvoiceNo"));
				
			//	invoiceHistoryData.setinvoiceMadeBy(rsInvoiceHistory.getString("InvoiceMakeId"));
				
				
				
				//VEHICLE INFO
				invoiceHistoryData.setVehicleRegNo(rsInvoiceHistory.getString("vehicleRegistrationNo"));
				invoiceHistoryData.setVehicleMake(rsInvoiceHistory.getString("VehicleMake"));
				invoiceHistoryData.setVehicleModel(rsInvoiceHistory.getString("VehicleModel"));
				invoiceHistoryData.setFuelType(rsInvoiceHistory.getString("VehicleFuelType"));
				
				//TOWE INFO
				invoiceHistoryData.setDealer(rsInvoiceHistory.getString("DealerName"));
				invoiceHistoryData.setFromAddress(rsInvoiceHistory.getString("FromAddress"));
				invoiceHistoryData.setToAddress(rsInvoiceHistory.getString("ToAddress"));
				invoiceHistoryData.setRemark(rsInvoiceHistory.getString("Remark"));
				invoiceHistoryData.setOldToweId(rsInvoiceHistory.getString("ToweId"));
				
				//BOOKING DETAILS INFO
				invoiceHistoryData.setBookingId(rsInvoiceHistory.getString("BookingId"));
				invoiceHistoryData.setServiceName(rsInvoiceHistory.getString("Issues"));
				
				
				invoiceHistoryList.add(invoiceHistoryData);
			}
			
		
			//json.setInvoiceHistoryList(invoiceHistoryList);
			return invoiceHistoryList;
	}


	
	
	/*
	 * FUNCTION FOR SELECTING ALL INVOICE HISTORY
	 */
		public static void SelectAllInvoiceHistory(SaleInvoiceJSON json, Connection connection) throws SQLException {

			ArrayList<SaleInvoiceJSON> invoiceHistoryList=new ArrayList<SaleInvoiceJSON>();
			
		 	String queryInvoiceHistory = SaleInvoiceQueryConstants.SELECT_ALL_INVOICE_HISTORY.replace("$invoiceTableName", json.getTableName());
				PreparedStatement preparedStmtInvoiceHistory= connection.prepareStatement(queryInvoiceHistory);

				preparedStmtInvoiceHistory.setString(1, json.getCompanyId());
				preparedStmtInvoiceHistory.setString(2, json.getFromDate());
				preparedStmtInvoiceHistory.setString(3, json.getToDate());
				preparedStmtInvoiceHistory.setString(4, json.getFromDate());
				preparedStmtInvoiceHistory.setString(5, json.getToDate());
				
				ResultSet rsInvoiceHistory=preparedStmtInvoiceHistory.executeQuery();
				
				while(rsInvoiceHistory.next()) {
					
					String invoiceNo=rsInvoiceHistory.getString("InvoiceNo");
					json.setInvoiceNo(invoiceNo);
					
					
					invoiceHistoryList.addAll(SaleInvoiceLogic.GetInvoiceHistory(json, connection));

					/*
					 SaleInvoiceJSON invoiceHistoryData=new SaleInvoiceJSON();
					 
					
					//CUSTOMER INFO
					invoiceHistoryData.setCustomerId(rsInvoiceHistory.getString("CustomerId"));
					invoiceHistoryData.setCustomerName(rsInvoiceHistory.getString("CustomerName"));
					invoiceHistoryData.setCust_ContactNo(rsInvoiceHistory.getString("ContactNo"));
					
					//BOOKING ID INFO
					invoiceHistoryData.setBookingId(rsInvoiceHistory.getString("BookingId"));
					
					//CART INFO
					invoiceHistoryData.setProductId(rsInvoiceHistory.getString("productId"));
					invoiceHistoryData.setProductName(rsInvoiceHistory.getString("productname"));
					invoiceHistoryData.setProductType(rsInvoiceHistory.getString("productType"));
					invoiceHistoryData.setProductQty(rsInvoiceHistory.getString("quantity"));
					invoiceHistoryData.setProductRate(rsInvoiceHistory.getString("rate"));
					invoiceHistoryData.setProductTotal(rsInvoiceHistory.getString("total"));
					
					invoiceHistoryData.setProductDiscountPercentage(rsInvoiceHistory.getString("discountPercentage"));
					invoiceHistoryData.setProductDiscountAmount(rsInvoiceHistory.getString("discountAmount"));
					
					invoiceHistoryData.setProductSubTotal(rsInvoiceHistory.getString("prefinalAmount"));
					
					invoiceHistoryData.setProduct_CGST_Percentage(rsInvoiceHistory.getString("cgsta"));
					invoiceHistoryData.setProduct_SGST_Percentage(rsInvoiceHistory.getString("sgsta"));
					invoiceHistoryData.setProduct_IGST_Percentage(rsInvoiceHistory.getString("igsta"));
					
					invoiceHistoryData.setProductFinalAmt(rsInvoiceHistory.getString("finalamount"));
					
					invoiceHistoryData.setProduct_serviceByStaffId(rsInvoiceHistory.getString("staffId"));
					invoiceHistoryData.setProduct_serviceByStaffName(rsInvoiceHistory.getString("serviceBy"));
					
							
					//INVOICE INFO
					invoiceHistoryData.setInvoiceNo(rsInvoiceHistory.getString("invoiceNo"));
					invoiceHistoryData.setInvoiceDate(rsInvoiceHistory.getString("Invoicedate"));
					invoiceHistoryData.setInvoiceDueDate(rsInvoiceHistory.getString("duedate"));
					invoiceHistoryData.setDate(rsInvoiceHistory.getString("Date"));
					invoiceHistoryData.setTotalGst(rsInvoiceHistory.getString("totalgst"));
					invoiceHistoryData.setInvoiceAmt(rsInvoiceHistory.getString("subtotal1"));
					invoiceHistoryData.setDiscountAmt(rsInvoiceHistory.getString("discount"));
					invoiceHistoryData.setInvoiceAmt_Paid(rsInvoiceHistory.getString("advance"));
					invoiceHistoryData.setBalanceAmt(rsInvoiceHistory.getString("balance_amount"));
					
					invoiceHistoryData.setInvoiceAdvisorId(rsInvoiceHistory.getString("AdvisorId"));
					invoiceHistoryData.setInvoiceAdvisorId(rsInvoiceHistory.getString("advisor"));
					invoiceHistoryData.setPaymentStatus(rsInvoiceHistory.getString("payment_status"));
					invoiceHistoryData.setPaymentMode(rsInvoiceHistory.getString("PaymentMode"));
					invoiceHistoryData.setSite(rsInvoiceHistory.getString("Site"));
				//	invoiceHistoryData.setinvoiceMadeBy(rsInvoiceHistory.getString("InvoiceMakeId"));
					
					
					//VEHICLE INFO
					invoiceHistoryData.setVehicleRegNo(rsInvoiceHistory.getString("vehicleRegistrationNo"));
					invoiceHistoryData.setVehicleMake(rsInvoiceHistory.getString("VehicleMake"));
					invoiceHistoryData.setVehicleModel(rsInvoiceHistory.getString("VehicleModel"));
					invoiceHistoryData.setFuelType(rsInvoiceHistory.getString("VehicleFuelType"));
					
					//TOWE INFO
					invoiceHistoryData.setDealer(rsInvoiceHistory.getString("DealerName"));
					invoiceHistoryData.setFromAddress(rsInvoiceHistory.getString("FromAddress"));
					invoiceHistoryData.setToAddress(rsInvoiceHistory.getString("ToAddress"));
					invoiceHistoryData.setRemark(rsInvoiceHistory.getString("Remark"));
					invoiceHistoryData.setOldToweId(rsInvoiceHistory.getString("ToweId"));
					
					//BOOKING DETAILS INFO
					invoiceHistoryData.setBookingId(rsInvoiceHistory.getString("BookingId"));
					invoiceHistoryData.setServiceName(rsInvoiceHistory.getString("Issues"));
					
					
					invoiceHistoryList.add(invoiceHistoryData);
					*/
					}
				
				
				json.setInvoiceHistoryList(invoiceHistoryList);
				//json.setInvoiceHistoryList(invoiceHistoryList);
		}


/*
 * SMS FUNCTION TO SEND SALE INVOICE 
 * ADD & UPDATE DETAILS
 */
		public static void SendSaleInvoiceSMS(SaleInvoiceJSON json, String feedBackContent, String cust_payment_History_content,
				String invoiceType,String invoiceTypeMessage) throws UnirestException, JSONException {
			
			//System.out.println("SHORT URL OVER FEEDBACK :");
			String feedBackURL=CommonURLShortner.urlShortner(feedBackContent);
			
		//	System.out.println("SHORT URL OVER PAYMENT :");
			String paymentURL=CommonURLShortner.urlShortner(cust_payment_History_content);
			
		//	System.out.println("SHORT URL OVER :");
			
			//flow_id - id offered for the saleinvoice message template
			HttpResponse<String> response = Unirest.post("https://api.msg91.com/api/v5/flow/")
					  .header("authkey", "353401AMIyFHOE601adb68P1") //AUTHENTICATION KEY FROM MESSAGE 91
					  .header("content-type", "application/JSON")
					  .body("{\n  \"flow_id\": \"611e8a50bed34a7148185fa4\",\n  \"sender\": \"THAPPS\",\n  \"mobiles\": \"91"+json.getCust_ContactNo()+"\",\n  \"name\": \""+json.getCustomerName()+"\",\n  \"invoiceType\": \""+invoiceType+"\",\n  \"invoiceNo\":\""+json.getInvoiceNo()+ invoiceTypeMessage+"\",\n  \"companyName\":\""+json.getOrganizationName()+"\",\n  \"invoiceAmount\":\""+json.getInvoiceAmt()+"\",\n  \"feedbackURL\":\""+feedBackURL+"\",\n  \"paymentURL\":\""+paymentURL+"\"\n  \n}")
					  .asString();
			
			//System.out.println("SMS RESPOSNSE :"+response);
			//System.out.println("SMS RESPOSNSE MESSAGE:"+response.getBody());
			//System.out.println("SMS RESPOSNSE TYPE:"+response.getStatus());
			
			if(response.getStatus()==200) {
				//UPDATE THE MESSAGE COUNT
				
			}
		         
			/*
			 * SAMPLE MESSAGE TEXT
			 * Hi Arun,  Thanks for using our services! Your saleinvoice INV-1 from ThroughApps 
			 * for the amount 500 has been Billed. Please Visit Us Again! Kindly Provide Feedback at  
			 * View or Make Payment for your Invoices with us at PAYMENT URL Thanks Throughapps
			 */
			
			/*
			 * MESSAGE TEMPLATE
			 * Hi ##name##,  Thanks for using our services! Your ##invoiceType## ##invoiceNo## 
			 * from ##companyName## for the amount ##invoiceAmount## has been Billed. 
			 * Please Visit Us Again! Kindly Provide Feedback at ##feedbackURL## View or
			 *  Make Payment for your Invoices with us at ##paymentURL## Thanks Throughapps
			 */
		}


/*
 * FUCNTION TO DELETE INVOICE
 */
		public static void DeleteSaleInvoice(SaleInvoiceJSON json, Connection connection) throws SQLException {

		 	String queryDeleteInvoice= SaleInvoiceQueryConstants.DELETE_SALEINVOICE;
				PreparedStatement preparedStmtDeleteInvoice= connection.prepareStatement(queryDeleteInvoice);

				preparedStmtDeleteInvoice.setString(1, json.getCompanyId());
				preparedStmtDeleteInvoice.setString(2, json.getInvoiceNo());
		    int invoiceDeleteCount=preparedStmtDeleteInvoice.executeUpdate();
		    
			String queryDeleteCustomerStatement= SaleInvoiceQueryConstants.DELETE_CUSTOMER_STATEMENT;
			PreparedStatement preparedStmtDeleteCustomerStatement= connection.prepareStatement(queryDeleteCustomerStatement);

			preparedStmtDeleteCustomerStatement.setString(1, json.getCompanyId());
			preparedStmtDeleteCustomerStatement.setString(2, json.getInvoiceNo());
	    int invoiceDeleteCustomerStatementCount=preparedStmtDeleteCustomerStatement.executeUpdate();
				
		String queryDeleteJobCard= SaleInvoiceQueryConstants.DELETE_JOBCARD;
		PreparedStatement preparedStmtDeleteJobCard= connection.prepareStatement(queryDeleteJobCard);

		preparedStmtDeleteJobCard.setString(1, json.getCompanyId());
		preparedStmtDeleteJobCard.setString(2, json.getBookingId());
    int invoiceDeleteJobCardCount=preparedStmtDeleteJobCard.executeUpdate();
				
    
    MasterDao.AuditReport(json.getStaffId(),json.getEmployeeName(),json.getRole(), json.getId(),json.getCustomerName(),"Sale Invoice Deleted",json.getCompanyId());
	connection.close(); 
	
		}



}
