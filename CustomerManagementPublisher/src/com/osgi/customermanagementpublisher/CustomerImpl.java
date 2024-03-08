package com.osgi.customermanagementpublisher;

import com.osgi.clothingstoredb.DbContext;
import com.osgi.clothingstoredb.DbContextImpl;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class CustomerImpl implements CustomerService {

	private Connection connection = null; 
	private Statement statement;
	private ResultSet  resultSet;
	private DbContext  dbContext;
	private static PreparedStatement preparedStatement = null;
	Scanner sc = new Scanner(System.in);
	ProductOrderModel order =new ProductOrderModel();

	public CustomerImpl() {
		super();
		this.dbContext = new DbContextImpl();
		this.connection = dbContext.getDatabaseConnection();
	}

	@Override
	public void addNewCustomer() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);

		CustomerModel customerModel = new CustomerModel();

		System.out.println("****************************************************************************");
		System.out.println("****************************************************************************");
		System.out.println("*********************<<<<<<<<<< Customer Signup >>>>>>>>>>>*****************");
		System.out.println("****************************************************************************");
		System.out.println("****************************************************************************\n\n");

		System.out.println("Enter Your First Name ::");
		customerModel.setFirstName(sc.nextLine().trim());

		System.out.println("Enter Your Last Name ::");
		customerModel.setLastName(sc.nextLine().trim());

		System.out.println("Enter Your Email ::");
		customerModel.setEmail(sc.nextLine().trim());

		System.out.println("Enter Your Phone Number ::");
		customerModel.setPhone(sc.nextLine().trim());

		System.out.println("Enter Your Password ::");
		customerModel.setPassword(sc.nextLine().trim());

		try {

			String query = "insert into user(firstName,lastName,email,phone,password,isActive) values(?,?,?,?,?,'1') ";

			preparedStatement = connection.prepareStatement(query); 

			preparedStatement.setString(1,customerModel.getFirstName());
			preparedStatement.setString(2,customerModel.getLastName());
			preparedStatement.setString(3,customerModel.getEmail());
			preparedStatement.setString(4,customerModel.getPhone());
			preparedStatement.setString(5,customerModel.getPassword());

			int isSuccess = preparedStatement.executeUpdate();

			if(isSuccess > 0) {

				System.out.println("User Signup  Has Been Successfully");

			}else {

				System.out.println("Error has been orccured please try again");

			}


		}catch(Exception ex) {

			System.out.println("customerSaveError : " + ex.getMessage());
		}


	}
	
	//view all products
	@Override
        public void viewAllProducts() {
		
		try {
			
			String query = "SELECT productid, productName, brand, unitPrice FROM product WHERE isActive =1";
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			System.out.println("\n*****************************************************************************************************");
			System.out.println("*******************************************************************************************************");
			System.out.println("************************************ Welcome Hemara Fashion Store**************************************");
			System.out.println("*******************************************************************************************************");
			System.out.println("*******************************************************************************************************");
			
			System.out.println
			(
					String.format
					(
							"%20s %20s %20s %20s\n", 
							"ProdcutId", "Prodcut Name", "Brand", "Price"  
					)
			);
			
			System.out.println("--------------------------------------------------------------------------------------------------------");
			
			
			while(resultSet.next()) {
				
				System.out.printf
				(
						"%20d %20s %20s %20s\n", 
						resultSet.getInt("productid"),
						resultSet.getString("productName"),
						resultSet.getString("brand"),
						resultSet.getString("unitPrice")
						
						
				);
				
				System.out.println("--------------------------------------------------------------------------------------------------------");
			}
			
			
			
			
			
		}catch(Exception ex) {
			
			System.out.println("viewAllProductsException:" + ex.getMessage());
			
			
		}
		
		
		
	}
	

	
	
	

	@Override
	public void orderProduct() {
		
		Integer productid;
		
		System.out.println("***************************** place Your  Order Here***********************************");
		
		System.out.println("Enter Your Delivery Address ::");
 		order.setDeliveryaddress(sc.nextLine());
		System.out.println("Please Enter product ID: ");
		productid = (sc.nextInt());
		
		

		

		
		System.out.println("\nProcessing ..................................\n\n");
		
             try {
			
			String query = "SELECT productid,productName,brand,unitPrice  FROM product WHERE productid = '"+ productid+"' && isActive = 1";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			System.out.println("======================Your Order is ready==========================");
			
			while (resultSet.next()) {  
				
		    	  System.out.println("Product ID :" + resultSet.getInt("productid"));
		    	  System.out.println("Product Name :" + resultSet.getString("productName"));
		    	  System.out.println("Brand :" + resultSet.getString("brand"));
		    	  System.out.println("Price :" + resultSet.getString("unitPrice"));
		    	  
		    	 
		    	 
		    	 order.setPrice(resultSet.getString("unitPrice"));
		    	  order.setProductName(resultSet.getString("productName"));
		    	  order.setProductid(resultSet.getInt("productid"));
		    	  
		    	 
		      }	
			System.out.println("==================================================================\n\n");
		}catch(Exception ex) {
			
			System.out.println("orderProduct:" + ex.getMessage());
			
		}
            setOrder();
		
		
	}
	
	
	public void setOrder() {
         
         String query = "insert into clothorders(productid,productName,deliveryaddress,price,isActive)values(?,?,?,?,'1')";
         try {
 			
 			preparedStatement = connection.prepareStatement(query); 
 			
 			preparedStatement.setInt(1,order.getProductid());
 			preparedStatement.setString(2,order.getProductName());
 			preparedStatement.setString(3,order.getDeliveryaddress());
 			preparedStatement.setString(4,order.getPrice());
 			
 			int isSuccess = preparedStatement.executeUpdate();
 			
 			if(isSuccess > 0) {
 				
 				System.out.println("Your  Order has been successfully thnak you!! ");
 				
 			}else {
 				
 				System.out.println("Error has been orccured please try again");
 				
 			}
 			
 		}catch (Exception ex) {
 			
 			System.out.println("order : " + ex.getMessage());
 			
 		}
         
           	 
   	 
    }
	
	
	
	
	@Override
    public void removeCustomer() {
		
		
		
		
     Integer id;
		
		System.out.print("\nPlease enter Customer id : ");
		id = (sc.nextInt());
		
		try {
			
			String query = "UPDATE user SET isActive = 0 WHERE id = ?";
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1,id);
			
			int isSuccess = preparedStatement.executeUpdate();
			
			if(isSuccess > 0) {
				
				System.out.println("Delete Customer has been successfully..");
				
			}else {
				
				System.out.println("Cannot find Customer..");
				
			}
			
		}catch (Exception ex) {
			
			System.out.println("userDeleteException : " + ex.getMessage());
			
		}
   	 
   	 
   	 
   	 
    }

	@Override
	public void getOrderReport() {
		try {
			
			String query = "SELECT id, productid,productName ,deliveryaddress, price FROM clothorders";
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			File directory = new File("F:\\SA assignment1\\clothing-store-OSGI");
			
			directory.mkdirs();
			
			File file = new File(directory,"OrderProductReport.txt");
			FileWriter fileWriter = new FileWriter(file);
			
			fileWriter.write(String.format("++++++++++++++++++++++++++++++++++++++++++++++++++++++ Order Report +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n"));
			fileWriter.write(
					
					String.format
					(
							"%30s %30s %30s %30s %30s\n", 
							"Order Id","product Id", "Product Name", "Address","Price list"
					)
			);
			
			fileWriter.write(String.format("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n"));
			
			while(resultSet.next()) {
				
				fileWriter.write(
						
						String.format(
								
								"%30d %30d %30s %30s %30s \n", 
								resultSet.getInt("id"),
								resultSet.getInt("productid"),
								resultSet.getString("productName"),
								resultSet.getString("deliveryaddress"),
								resultSet.getString("price")
								
								
						)
				);
				
				fileWriter.write(String.format("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n"));
			}
			
			fileWriter.flush();
			fileWriter.close();
			
			
			System.out.println("Report genaration has been successfully");
				
			
		}catch (Exception ex) {
			
			System.out.println("orderBookDetailsReportException:" + ex.getMessage());
			
			
		}
	}









}
