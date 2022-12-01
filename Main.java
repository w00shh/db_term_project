import java.sql.*;
import java.util.Scanner;

import javax.swing.text.DateFormatter;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Main {

    static String URL = "jdbc:mysql://localhost:3306/term_project";
    static String Usr_ID;
    
    public static int Login(Connection conn,Scanner sc) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
    	while(true) {
    		System.out.println("----< Login menu >");
        	System.out.println("----(1) Login");
        	System.out.println("----(2) Sign Up");
        	System.out.println("----(3) Login as Administrator (Manager of Auction System)");
        	System.out.println("----(4) Quit");
        	System.out.print("    Your Choice: ");
        	int Select = sc.nextInt();
        	
        	if(Select == 1) { // Login
        		System.out.println("----< Login >");
        		System.out.print("---- ID : ");
        		String ID = sc.next();
        		System.out.print("---- password : ");        		
        		String Password = sc.next();
        		
                PreparedStatement pStmt = conn.prepareStatement("select ID,Password,Position from user where ID = ?");
                pStmt.setString(1,ID);
                ResultSet rs = pStmt.executeQuery();                
                if(rs.next()){
                    if(rs.getString("Password").equals(Password)){
                    	if(rs.getString("Position").equals("Banned")) {
                    		System.out.println("You are Banned!");
                    		pStmt.close();
                    		rs.close();
                    		return 0;
                    	}
                    	Usr_ID = ID;
                    	
                    	pStmt.close();
                    	rs.close();
                    	return 1;
                    }
                    else {
                    	System.out.println("Wrong Password!");
                    }
                }
                else {
                    System.out.println("There is no ID "+ID+"!");                	
                }
            	pStmt.close();
            	rs.close();
                //end of login
        	}
        	else if(Select == 2) { // Sign Up
        		System.out.println("----<Sign up >");
        		System.out.print("---- first name: ");
        		String FirstName = sc.next();
        		System.out.print("---- last name: ");
        		String LastName = sc.next();
        		System.out.print("---- enter ID: ");
        		String ID = sc.next();
        		System.out.print("---- enter PW: ");
        		String Password = sc.next();
        		System.out.print("---- enter email: ");
        		String email = sc.next();
        		PreparedStatement pStmt = conn.prepareStatement("select ID,Password from user where ID = ?");
                pStmt.setString(1,ID);
                ResultSet rs = pStmt.executeQuery();
                if(rs.next()) {
                	System.out.println(ID+" is already used. Please use another ID");
                }
                else {
                	PreparedStatement pStmt2 = conn.prepareStatement("insert into user(ID,Password,First_Name,Last_Name,Email) values(?,?,?,?,?)");
            		pStmt2.setString(1,ID);
            		pStmt2.setString(2,Password);
            		pStmt2.setString(3,FirstName);
            		pStmt2.setString(4,LastName);
            		pStmt2.setString(5, email);
            		pStmt2.executeUpdate();
            		System.out.println("Sign Up Complete. Please run program again.");                	
                }
                
            	pStmt.close();
            	rs.close();
                // end of register
        	}
        	else if(Select == 3) {
        		System.out.println("----< Login >");
        		System.out.print("---- ID : ");
        		String ID = sc.next();
        		System.out.print("---- password : ");        		
        		String Password = sc.next();
        		
        		//Compare ID,Password by JDBC -> Select ID,Password from user where ID=? and Password=?으로 수정해야 
                PreparedStatement pStmt = conn.prepareStatement("select ID,Password,Position from user where ID = ?");
                pStmt.setString(1,ID);
                ResultSet rs = pStmt.executeQuery();                
                if(rs.next()){
                    if(rs.getString("Password").equals(Password)){
                    	if(!rs.getString("Position").equals("Admin")) {
                    		System.out.println("You are Not Admin!");
                    		return 0;
                    	}
                    	Usr_ID = ID;
                    	
                    	return 2;
                    }
                    else {
                    	System.out.println("Wrong Password!");
                    }
                }
                else {
                    System.out.println("There is no ID "+ID+"!");                	
                }
            	pStmt.close();
            	rs.close();
        	}
        	else if(Select == 4) {
        		System.out.println("Bye");
        		return 0;
        	}
        	else {
        		System.out.println("Wrong Input!");
        	}
    	}
    }
    
    public static int Main_Menu(Connection conn,Scanner sc) throws SQLException{
    	System.out.println("----< Main Menu > ");    	
    	System.out.println("     (1) Sell item");
    	System.out.println("     (2) Status of Your Item Listed on Auction");
    	System.out.println("     (3) Search item");
    	System.out.println("     (4) Check Status of your Bid");
    	System.out.println("     (5) Check your Account");
    	System.out.println("     (6) Quit");
    	System.out.print("---- Enter the Number : ");
    	
    	int Category = sc.nextInt();
    	
    	if(Category == 1) {
    		Sell_Item(conn,sc);
    	}
    	else if(Category == 2) {
    		Show_My_Item(conn,sc);
    		// Show my item in auction
    		// 테이블 보기만 하면 
    	}
    	else if(Category == 3) {
    		Search_Item(conn,sc);
    		// Search item
    		// 카테고리만들어서 item 검
    	}
    	else if(Category == 4) {
    		Check_Bid(conn,sc);
    		//자신의 비드 검색
    	}
    	else if(Category == 5) {
    		Check_Account(conn,sc);
    		// Check your Account
    	}
    	else if(Category == 6) {
    		System.out.println("You Selected Quit.");
    	}
    	
    	return -1;
    }
    
    public static void Sell_Item(Connection conn,Scanner sc) throws SQLException {
    	System.out.println("----< Sell item > ");    	
    	System.out.println("---- select from the following category : ");    	
    	System.out.println("    (1) Electronics");    	
    	System.out.println("    (2) Books");
    	System.out.println("    (3) Home");
    	System.out.println("    (4) Clothing");
    	System.out.println("    (5) Sporting Goods");
    	System.out.print("---- Enter your category : ");
    	int category = sc.nextInt();
    	while(category < 1 || category > 5) {
    		System.out.print("---- Wrong input! Enter the number again : ");
    		category = sc.nextInt();
    	}
    	
    	String category_string;
    	if(category == 1)
    		category_string = "Electronics";
    	else if(category == 2)
    		category_string = "Books";
    	else if(category == 3)
    		category_string = "Home";
    	else if(category == 4)
    		category_string = "Clothing";
    	else if(category == 5)
    		category_string = "Sporting_Goods";
    	else {
    		category_string = "";
    		System.out.println("Your input is Wrong!");
			Sell_Item(conn,sc);
    	}
    	
    	System.out.println("---- condition : ");    	
    	System.out.println("    (1) New");
    	System.out.println("    (2) Like-New");
    	System.out.println("    (3) Used (Good)");
    	System.out.println("    (4) Used (Acceptable)");
    	System.out.print("---- Enter your condition : ");
    	int condition = sc.nextInt();
    	while(condition < 1 || condition > 4) {
    		System.out.print("---- Wrong input! Enter the number again : ");
    		condition = sc.nextInt();
    	}
    	
    	String condition_string;
    	if(condition == 1)
    		condition_string = "New";
    	else if(condition == 2)
    		condition_string = "Like-New";
    	else if(condition == 3)
    		condition_string = "Used(Good)";
    	else if(condition == 4)
    		condition_string = "Used(Acceptable)";
    	else {
    		condition_string = "";
    		System.out.println("Your input is Wrong!");
			Sell_Item(conn,sc);
    	}
    	    	
    	System.out.print("---- description: ");
    	String description = sc.next();
    	
    	PreparedStatement pStmt_des = conn.prepareStatement("select * from bid_history where Description = ?");
    	pStmt_des.setString(1, description);
    	ResultSet rs_des = pStmt_des.executeQuery();
    	while(rs_des.next()) {
    		System.out.println("---- Sorry. Please use another description : ");    		
    		description = sc.next();
        	pStmt_des.setString(1, description);
    		rs_des = pStmt_des.executeQuery();
    	}
    	while(description.length()>20) {
    		System.out.print("---- Write description under 20 letters : ");
    		description = sc.next();
    	}
    	
    	rs_des.close();
    	pStmt_des.close();
    	
    	System.out.print("---- buy-it-now price: ");
    	int buy_it_now = sc.nextInt();
    	
    	System.out.print("---- bid ending date (in format YYYY-MM-DD HH:MM:SS): ");
    	sc.nextLine();
    	String ending_date = sc.nextLine();
    	LocalDateTime now = LocalDateTime.now();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	LocalDateTime inputDate = LocalDateTime.parse(ending_date,formatter);

    	while(inputDate.isBefore(now)) {
    		System.out.print("---- Please Write ending date after now : ");
    		ending_date = sc.nextLine();
    		inputDate = LocalDateTime.parse(ending_date,formatter);
    	}
    	// yyyy-mm-dd형식으로 받게 조건 걸어주기.
    	
    	
    	
    	PreparedStatement pStmt = conn.prepareStatement("insert into item(`Category`,`Condition`,`Description`,`Buy_It_Now`,`Bid_Ending_Date`,`Seller`) values(?,?,?,?,?,?)");
		pStmt.setString(1,category_string);
		pStmt.setString(2,condition_string);
		pStmt.setString(3, description);
		pStmt.setInt(4,buy_it_now);
		pStmt.setString(5,ending_date);
		pStmt.setString(6,Usr_ID);
		pStmt.executeUpdate();
		System.out.println("Sell item Success.");
    	pStmt.close();
		Main_Menu(conn,sc);
    	return;
    }
    
    public static void Print_Search(Connection conn,Scanner sc,String query) throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
    	System.out.println("----< Status of Your Item Listed on Auction >");
    	int idx = 1;
        while(rs.next()){
        	System.out.println("[Item "+idx+"]");
    		System.out.println("   description: "+rs.getString("Description"));
        	System.out.println("   status : "+rs.getInt("Bid_Num")+" bids");
        	System.out.println("   current bidding price: "+ rs.getInt("Bidding_Price"));
        	System.out.println("   current highest bidder: "+ rs.getString("Bidder"));
        	System.out.println("   date posted: "+ rs.getString("Date_Posted"));
        	// + Bid Ending date
        	idx++;
        }
        if(idx == 1) {
        	System.out.println("Nothing searched.");
        	rs.close();
        	Search_Item(conn,sc);
        	return;
        }
        
    	System.out.print("--- Which item do you want to bid? (Enter the number or 'B' to go back to the previous menu): ");
    	String num_string = sc.next();
    	
		rs.close();
		
    	if(num_string.equals("B")) {
    		Search_Item(conn,sc);
    		return;
    	}
    	else {
    		int num = Integer.parseInt(num_string);
            ResultSet rs2 = stmt.executeQuery(query);
            for(int i = 0;i<num;i++) {
        		rs2.next();
            }
            System.out.println("You Choosed " + rs2.getString("Description")+".");
        	System.out.print("--- Bidding price? (Enter the price or 'buy' to pay for the buy-it-now price) : ");
        	String price_string = sc.next();
        	if(price_string.equals("buy")) {
            	PreparedStatement pStmt = conn.prepareStatement("insert into bid_history(Description,Price,Bidder) values(?,?,?)");
        		pStmt.setString(1,rs2.getString("Description"));
        		pStmt.setInt(2,rs2.getInt("Buy_It_Now"));
        		pStmt.setString(3,Usr_ID);
        		pStmt.executeUpdate();
        		
        		pStmt.clearParameters();
        		pStmt = conn.prepareStatement("insert into sold_item(Description,Sold_Price,Buyer) values(?,?,?)");
        		pStmt.setString(1, rs2.getString("Description"));
        		pStmt.setInt(2, rs2.getInt("Buy_It_Now"));
        		pStmt.setString(3,Usr_ID);
        		pStmt.executeUpdate();
        		
        		pStmt.clearParameters();
        		pStmt = conn.prepareStatement("update item set status='Sold' where Description = ?");
        		pStmt.setString(1, rs2.getString("Description"));
        		pStmt.executeUpdate();
        		
        		pStmt.close();
        		System.out.println("You Bought Item in buy-it-now price.");
        	}
        	else {
        		int price = Integer.parseInt(price_string);
        		System.out.println("You bidded item in price "+price);
            	PreparedStatement pStmt = conn.prepareStatement("insert into bid_history(Description,Price,Bidder) values(?,?,?)");
        		pStmt.setString(1,rs2.getString("Description"));
        		pStmt.setInt(2,price);
        		pStmt.setString(3,Usr_ID);
        		pStmt.executeUpdate();        	
        		pStmt.clearParameters();
        		
        		pStmt = conn.prepareStatement("select * from bid_history where Description = ? order by Price Desc");
                pStmt.setString(1,rs2.getString("Description"));
                ResultSet rs3 = pStmt.executeQuery();
                int cnt;
                boolean is_Highest_Price = false;
                String High_Date ="";
                for(cnt=0;rs3.next();cnt++) {
                	if(cnt==0) {
                		if(rs3.getInt("Price")==price) {
                			is_Highest_Price = true;
                			High_Date = rs3.getString("Post_Date");
                		}
                	}
                }
                pStmt.clearParameters();
                if(is_Highest_Price) {
        			pStmt = conn.prepareStatement("update selling_item set Bidding_Price=?, Bidder=?, Bid_Posted=?, Bid_Num=? where Description=?");
        			pStmt.setInt(1,price);
        			pStmt.setString(2, Usr_ID);
        			pStmt.setString(3, High_Date);
        			pStmt.setInt(4,cnt);
        			pStmt.setString(5, rs2.getString("Description"));
        			pStmt.executeUpdate();
        			pStmt.clearParameters();
                }
                else{
        			pStmt = conn.prepareStatement("update selling_item set Bid_Num=? where Description=?");
        			pStmt.setInt(1,cnt);
        			pStmt.setString(2, rs2.getString("Description"));
        			pStmt.executeUpdate();
        			pStmt.clearParameters();                	
                }
                
            	rs3.close();
            	pStmt.close();
        	}
        	rs2.close();
        	stmt.close();
    	}
		System.out.print("Input and enter Any key to return : ");
		String return_key = sc.next();
		Main_Menu(conn,sc);
    }

    public static void Show_My_Item(Connection conn,Scanner sc) throws SQLException {
    	String query = "select * from item natural left join sold_item natural left join selling_item where seller = '" + Usr_ID + "'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
    	System.out.println("----< Status of Your Item Listed on Auction >");
    	int idx = 1;
        while(rs.next()){
        	System.out.println("[Item "+idx+"]");
    		System.out.println("   description: "+rs.getString("Description"));

    		if(rs.getString("Status").equals("Selling")) {
        		System.out.println("   status : "+rs.getInt("Bid_Num")+" bids");
        		System.out.println("   current bidding price: "+ rs.getInt("Bidding_Price"));
        		System.out.println("   current highest bidder: "+ rs.getString("Bidder"));
        		System.out.println("   date posted: "+ rs.getString("Date_Posted"));
        		// + Bid Ending date
        		idx++;
        	}
        	else if(rs.getString("Status").equals("Sold")) {
        		System.out.println("   status : sold");
        		System.out.println("   sold price : " + rs.getString("Sold_Price"));
        		System.out.println("   buyer : " + rs.getString("Buyer"));
        		System.out.println("   sold date : " + rs.getString("Sold_Date"));
        		
        		idx++;
        	}
        }    	
        
        stmt.close();
        rs.close();
		System.out.print("Input and enter Any key to return : ");
		String return_key = sc.next();
		Main_Menu(conn,sc);
    	return;    	
    }
    
    public static void Search_Item(Connection conn,Scanner sc) throws SQLException {
    	System.out.println("----< Search item > : ");
    	System.out.println("----(1) Search items by category");
    	System.out.println("----(2) Search items by description keyword");
    	System.out.println("----(3) Search items by seller");
    	System.out.println("----(4) Search items by date posted");
    	System.out.println("----(5) Go Back");
    	System.out.println("----(6) Quit");
    	System.out.print("---- Enter the Number : ");
    	int Selection = sc.nextInt();
    	
    	if(Selection == 1) {
        	System.out.println("----< Search items by category > : ");
        	System.out.println("----(1) Electronics");
        	System.out.println("----(2) Books");
        	System.out.println("----(3) Home");
        	System.out.println("----(4) Clothing");
        	System.out.println("----(5) Sporting Goods");
    		int Category = sc.nextInt();
    		String Category_String = "";
    		if(Category == 1)
    			Category_String = "Electronics";
    		else if(Category == 2)
    			Category_String = "Books";
    		else if(Category == 3)
    			Category_String = "Home";
    		else if(Category == 4)
    			Category_String = "Clothing";
    		else if(Category == 5)
    			Category_String = "Sporting_Goods";
    		else {
    			System.out.println("Wrong Input!");
				Main_Menu(conn,sc);
				return;
    		}
        	String query = "select * from item natural left join selling_item where Category = '" + Category_String + "' and Status = 'Selling'";
        	Print_Search(conn,sc,query);
    	}
    	else if(Selection == 2) {
        	System.out.print("----< Search items by description keyword > : ");    	
    		String Description = sc.next();
        	String query = "select * from item natural left join selling_item where Description = '" + Description + "' and Status = 'Selling'";
        	Print_Search(conn,sc,query);
    	}
    	else if(Selection == 3) {
    		System.out.print("----< Search items by seller > : ");
    		String Seller = sc.next();
        	String query = "select * from item natural left join selling_item where Seller = '" + Seller + "' and Status = 'Selling'";
        	Print_Search(conn,sc,query);
    	}
    	else if(Selection == 4) {
        	System.out.print("----< Search items by date posted > : ");
        	sc.nextLine(); 
        	String Date = sc.nextLine();
        	String query = "select * from item natural left join selling_item where Date_Posted = '" + Date + "' and Status = 'Selling'";
        	Print_Search(conn,sc,query);
    	}
    	else if(Selection == 5) {
    		System.out.println("----< Selected Go Back >");
    		Main_Menu(conn,sc);
    		return;
    	}
    	else if(Selection == 6) {
    		System.out.println("----< Selected Go Quit >");
    		return;
    	}
    	
    }
    
    public static void Check_Bid(Connection conn,Scanner sc) throws SQLException{
    	System.out.println("----<Check Status of your Bid > ");
    	String query = "select * from bid_history natural left join selling_item natural left join sold_item natural join item where Bidder = '"+ Usr_ID + "' order by Description";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        int idx = 1;
        
        while(rs.next()){
        	System.out.println("[Item "+idx+"]");
        	System.out.println("   description: "+rs.getString("Description"));
        	if(rs.getString("Status").equals("Selling")) {
            	if(rs.getInt("Price")==rs.getInt("Bidding_Price")) {
            		System.out.println("   status: You are the highest bidder");
            		System.out.println("   your bidding price: "+rs.getInt("Price"));
            		System.out.println("   current highest bidding price: "+rs.getInt("Price"));
            		System.out.println("   bid ending date: "+rs.getString("Bid_Ending_Date"));
            	}
            	else {
            		System.out.println("   status: You are outbidded");
            		System.out.println("   your bidding price: "+rs.getInt("Price"));
            		System.out.println("   current highest bidding price: "+rs.getInt("Bidding_Price"));
            		System.out.println("   bid ending date: "+rs.getString("Bid_Ending_Date"));            		
            	}
        	}
        	else if(rs.getString("Status").equals("Sold")) {
            	if(rs.getString("Buyer").equals(Usr_ID)) {
            		System.out.println("   status: You won the item");
            		System.out.println("   sold price: "+rs.getInt("Sold_Price"));
            		System.out.println("   sold date: "+rs.getString("Sold_Date"));
            	}
            	else {
            		System.out.println("   status: Your are outbidded and the item is sold");
            		System.out.println("   sold price: "+rs.getInt("Sold_Price"));
            		System.out.println("   sold date: "+rs.getString("Sold_Date"));
            	}
        	}
        	idx++;
        }
		System.out.print("Input and enter Any key to return : ");
		String return_key = sc.next();
		Main_Menu(conn,sc);
		return;        
    }
            
    public static void Check_Account(Connection conn,Scanner sc) throws SQLException{
    	System.out.println("----< Check your Account >");
    	String query = "select * from item natural left join sold_item where Seller = '"+Usr_ID+"'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        int idx = 1;
        int sold = 0;
        int purchase = 0;
        
        while(rs.next()){
    		System.out.println("[Sold Item "+idx+"]");
    		System.out.println("   description: "+rs.getString("Description"));
    		System.out.println("   sold price: "+rs.getInt("Sold_Price"));
    		sold += rs.getInt("Sold_Price");
    		idx++;
        }
        
    	query = "select * from item natural left join sold_item where Buyer = '"+Usr_ID+"'";
    	Statement stmt2 = conn.createStatement();
    	ResultSet rs2 = stmt2.executeQuery(query);
        idx = 1;
        
        while(rs2.next()){
    		System.out.println("[Purchased Item "+idx+"]");
    		System.out.println("   description: "+rs2.getString("Description"));
    		System.out.println("   purchase price: "+rs2.getInt("Sold_Price"));
    		purchase += rs2.getInt("Sold_Price");
    		idx++;
        }
        
        int commision = (int)(sold * 0.02);
        int total = sold-commision-purchase;
        System.out.println("[Your Balance Summary]");
        System.out.println("   sold: "+sold+" won");
        System.out.println("   commission: "+commision+" won");
        System.out.println("   purchased: "+purchase+" won");
        System.out.println("   Total balance: "+total+" won");
        
        stmt.close();
        stmt2.close();
        rs.close();
        rs2.close();
		System.out.print("Input and enter Any key to return : ");
		String return_key = sc.next();
		Main_Menu(conn,sc);
		return;        
    }
    
    public static void Admin_Menu(Connection conn, Scanner sc) throws SQLException{
    	System.out.println("----< Admin Menu > ");    	
    	System.out.println("     (1) Ban User");
    	System.out.println("     (2) Change User Information");
    	System.out.println("     (3) Show Bid History");
    	System.out.println("     (4) Show Selling items");
    	System.out.println("     (5) Show Sold items");
    	System.out.println("     (6) Show Seller Rating");
    	System.out.println("     (7) Give Admin Authority");    	
    	System.out.println("     (8) Quit");
    	System.out.print("---- Enter the Number : ");
    	
    	int Category = sc.nextInt();
    	
    	if(Category == 1) {
    		Ban_User(conn,sc);
    	}
    	else if(Category == 2) {
    		Change_User_Information(conn,sc);
    	}
    	else if(Category == 3) {
    		Show_Bid_History(conn,sc);
    	}
    	else if(Category == 4) {
    		Show_Selling_Items(conn,sc);
    	}
    	else if(Category == 5) {
    		Show_Sold_Items(conn,sc);
    	}
    	else if(Category == 6) {
    		Show_Seller_Rating(conn,sc);
    	}
    	else if(Category == 7) {
    		Give_Admin(conn,sc);
    	}
    	else if(Category == 8) {
    		System.out.println("You Selected Quit.");    		
    	}
    	else {
    		System.out.println("Wrong Input.");
    		Admin_Menu(conn,sc);
    	}
    	
    	return;
    }
    
    public static void Give_Admin(Connection conn, Scanner sc) throws SQLException{
    	System.out.println("----< Give Admin > ");
    	System.out.print("Write The User ID : ");
		String ID = sc.next();
        PreparedStatement pStmt = conn.prepareStatement("select * from user where ID = ?");    	
        pStmt.setString(1,ID);
        ResultSet rs = pStmt.executeQuery();                
        if(!rs.next()){
            System.out.println("There is no ID "+ID+"!");                	
			Admin_Menu(conn,sc);
            return;
        }
        pStmt.clearParameters();
        pStmt = conn.prepareStatement("update user set Position = 'Admin' where ID = ?");
        pStmt.setString(1,ID);
        pStmt.executeUpdate();
        System.out.println("Successfully give Admin Authorization User "+ID);
		Admin_Menu(conn,sc);
        return;
    }
    
    public static void Show_Seller_Rating(Connection conn, Scanner sc) throws SQLException{
    	System.out.println("----< Show Seller Rating > ");
    	System.out.println("Which Rating Do you want to see?");
    	System.out.println("---- (1) Seller Rating in Sold Count");
    	System.out.println("---- (2) Seller Rating in total Sold_Price");
    	System.out.println("---- (3) Seller Rating in Sold_Price for one item");
    	System.out.println("---- (4) Exit");
    	System.out.print("     Your Choice : ");
    	int Select = sc.nextInt();
    	if(Select == 4){
			Admin_Menu(conn,sc);
    		return;
		}
        
    	String query = "";
        if(Select == 1) 
        	query = "select Buyer,count(Buyer),sum(Sold_Price),max(Sold_Price) from sold_item where Sold_Price != 0 group by Buyer order by count(Buyer) desc";
        else if(Select == 2) 
        	query = "select Buyer,count(Buyer),sum(Sold_Price),max(Sold_Price) from sold_item where Sold_Price != 0 group by Buyer order by sum(Sold_Price) desc";
        else if(Select == 3) 
        	query = "select Buyer,count(Buyer),sum(Sold_Price),max(Sold_Price) from sold_item where Sold_Price != 0 group by Buyer order by max(Sold_Price) desc";
        else {
        	System.out.println("Wrong Input.");
			Admin_Menu(conn,sc);
        	return;
        }
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        int idx = 1;
        System.out.println("[Ranking] ID");
        while(rs.next()) {
        	System.out.println("[ Rank #"+idx+"] "+rs.getString("Buyer"));
        	idx++;
        }
        stmt.close();
        rs.close();
   		System.out.print("Input and enter Any key to return : ");
		String return_key = sc.next();
		Admin_Menu(conn,sc);
        return;
    }
    
    public static void Ban_User(Connection conn, Scanner sc) throws SQLException{
    	System.out.println("----< Ban User > ");
    	System.out.print("Write The User ID : ");
		String ID = sc.next();
        PreparedStatement pStmt = conn.prepareStatement("select * from user where ID = ?");    	
        pStmt.setString(1,ID);
        ResultSet rs = pStmt.executeQuery();                
        if(!rs.next()){
            System.out.println("There is no ID "+ID+"!");  
			Admin_Menu(conn,sc);              	
            return;
        }
        pStmt.clearParameters();
        pStmt = conn.prepareStatement("update user set Position = 'Banned' where ID = ?");
        pStmt.setString(1,ID);
        pStmt.executeUpdate();
        System.out.println("Successfully banned User "+ID);
		System.out.print("Input and enter Any key to return : ");
		String return_key = sc.next();
		Admin_Menu(conn,sc);
        return;
    }
    public static void Change_User_Information(Connection conn, Scanner sc) throws SQLException{
    	System.out.println("----< Change User Information > ");
    	System.out.print("Write The User ID : ");
		String ID = sc.next();
        PreparedStatement pStmt = conn.prepareStatement("select * from user where ID = ?");    	
        pStmt.setString(1,ID);
        ResultSet rs = pStmt.executeQuery();                
        if(!rs.next()){
            System.out.println("There is no ID "+ID+"!");
			Admin_Menu(conn,sc);                	
            return;
        }
        pStmt.clearParameters();
        System.out.println("Which information Do you want to change?");
        System.out.println("----(1) Password");
        System.out.println("----(2) First Name");
        System.out.println("----(3) Last Name");
        System.out.print("    Your Choice : ");
        int input = sc.nextInt();
        System.out.print("Please text the input you want to change to : ");
        String alter = sc.next();
        //UPDATE user SET ?=? where ID=?;
        if(input == 1) {
    		pStmt = conn.prepareStatement("UPDATE user SET Password=? where ID = ?");
        }
        else if(input == 2) {
    		pStmt = conn.prepareStatement("UPDATE user SET First_Name=? where ID = ?");
        	
        }
        else if(input == 3) {
    		pStmt = conn.prepareStatement("UPDATE user SET Last_Name=? where ID = ?");
        }
        else {
        	System.out.println("Wrong Input");
			Admin_Menu(conn,sc);
        	return;
        }
    	pStmt.setString(1,alter);
    	pStmt.setString(2,ID);
    	pStmt.executeUpdate();
    	System.out.println("Update Complete");
        
    	pStmt.close();
    	rs.close();

		System.out.print("Input and enter Any key to return : ");
		String return_key = sc.next();
		Admin_Menu(conn,sc);
        return;
    }
    
    public static void Show_Bid_History(Connection conn, Scanner sc) throws SQLException{
    	System.out.println("----< Show Bid History > ");
    	String query = "select * from bid_history natural left join selling_item natural left join sold_item natural join item order by Description";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        int idx = 1;
        
        while(rs.next()){
        	System.out.println("[Item "+idx+"]");
        	System.out.println("   Description: "+rs.getString("Description"));
        	System.out.println("   Bidder : "+rs.getString("Bidder"));        	
        	System.out.println("   Bid Price : "+rs.getString("Price"));
        	System.out.println("   Bid Post Date : "+rs.getString("Post_Date"));
        	if(rs.getString("Status").equals("Selling")) {
            	System.out.println("   Status : "+rs.getString("Status"));
            	System.out.println("   Highest Bidder: "+rs.getString("Bidder"));            		
            	System.out.println("   bidding price: "+rs.getInt("Price"));
            	System.out.println("   bid ending date: "+rs.getString("Bid_Ending_Date"));
        	}
        	else if(rs.getString("Status").equals("Sold")) {
            	System.out.println("   Status : "+rs.getString("Status"));
            	System.out.println("   Buyer : "+rs.getString("Buyer"));
        		System.out.println("   sold price: "+rs.getInt("Sold_Price"));
        		System.out.println("   sold date: "+rs.getString("Sold_Date"));
        	}
        	idx++;
        }
        stmt.close();
        rs.close();
		System.out.print("Input and enter Any key to return : ");
		String return_key = sc.next();
		Admin_Menu(conn,sc);
        return;
       }
    
    public static void Show_Selling_Items(Connection conn, Scanner sc) throws SQLException{
    	System.out.println("----< Show Selling Items >");
    	String query = "select * from selling_item natural join item";
    	Statement stmt = conn.createStatement();
    	ResultSet rs = stmt.executeQuery(query);
    	int idx = 1;
    	
    	while(rs.next()) {
        	System.out.println("[Item "+idx+"]");
        	System.out.println("   Description: "+rs.getString("Description"));
        	System.out.println("   Category: "+rs.getString("Category"));
        	System.out.println("   Condition: "+rs.getString("Condition"));
        	System.out.println("   Buy It Now Price: "+rs.getString("Buy_It_Now"));
        	System.out.println("   Bid Ending Date: "+rs.getString("Bid_Ending_Date"));
        	System.out.println("   Seller: "+rs.getString("Seller"));
        	System.out.println("   Current Highest Bidding Price: "+rs.getString("Bidding_Price"));
        	System.out.println("   Current Highest Bid Bidder: "+rs.getString("Bidder"));        	
        	idx++;
    	}
	 	stmt.close();
    	rs.close();
   		System.out.print("Input and enter Any key to return : ");
		String return_key = sc.next();
		Admin_Menu(conn,sc);
        return;
    }
    public static void Show_Sold_Items(Connection conn, Scanner sc) throws SQLException{
    	System.out.println("----< Show Sold Items >");
    	String query = "select * from sold_item natural join item";
    	Statement stmt = conn.createStatement();
    	ResultSet rs = stmt.executeQuery(query);
    	int idx = 1;
    	
    	while(rs.next()) {
        	System.out.println("[Item "+idx+"]");
        	System.out.println("   Description: "+rs.getString("Description"));
        	System.out.println("   Category: "+rs.getString("Category"));
        	System.out.println("   Condition: "+rs.getString("Condition"));
        	System.out.println("   Buy It Now Price: "+rs.getString("Buy_It_Now"));
        	System.out.println("   Sold Price : "+rs.getString("Sold_Price"));
        	System.out.println("   Sold Date : "+rs.getString("Sold_Date"));
        	System.out.println("   Buyer : "+rs.getString("Buyer"));
        	idx++;
    	}
    	stmt.close();
    	rs.close();
    }
    
    public static void Check_End_Bid(Connection conn, Scanner sc) throws SQLException{
    	LocalDateTime now = LocalDateTime.now();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	String formatedNow = now.format(formatter);
    	
        PreparedStatement pStmt = conn.prepareStatement("select * from item natural join selling_item where Bid_Ending_Date < ?");    	
        pStmt.setString(1,formatedNow);
        ResultSet rs = pStmt.executeQuery();                

        while(rs.next()) {    		
    		pStmt.clearParameters();
    		pStmt = conn.prepareStatement("insert into sold_item(Description,Sold_Price,Buyer) values(?,?,?)");
    		pStmt.setString(1, rs.getString("Description"));
    		pStmt.setInt(2, rs.getInt("Bidding_Price"));
    		pStmt.setString(3,rs.getString("Bidder"));
    		pStmt.executeUpdate();
    		
    		pStmt.clearParameters();
    		pStmt = conn.prepareStatement("update item set status='Sold' where Description = ?");
    		pStmt.setString(1, rs.getString("Description"));
    		pStmt.executeUpdate();
    		
        }
		pStmt.close();    	
    	return;
    }
    
    public static void main(String[] args) throws Exception{
    	URL = args[0];
    	String SQL_ID = args[1];
    	String SQL_PW = args[2];
    	
        Connection conn = DriverManager.getConnection(URL, SQL_ID, SQL_PW);
    	Scanner sc = new Scanner(System.in);   	
    	
    	int Login = Login(conn,sc);
    	if(Login == 0)return;
    	
    	Check_End_Bid(conn,sc);
    	
    	if(Login == 1)
    		Main_Menu(conn,sc);
    	else if(Login == 2)
    		Admin_Menu(conn,sc);
    	
    	sc.close();
    	conn.close();
        return;
    }
    
}