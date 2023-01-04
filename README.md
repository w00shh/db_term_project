# db_term_project
2022-2 SKKU Introduction of Database term project

# E-R Model

![Blank diagram (1).png](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/fead75c2-9fed-4c56-9001-a4a3fdcfa7f1/Blank_diagram_%281%29.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20230104%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20230104T065629Z&X-Amz-Expires=86400&X-Amz-Signature=a1a85b4a737bb67fa281b727aaf6c4e8b47832cec231851ef80055589084a044&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22Blank%2520diagram%2520%281%29.png%22&x-id=GetObject)

# Manual

before run the program. user should run 2 commands on mysql.

`source ./ddl.sql`

`source ./data.sql`

## Login

If user run the file, Login menu will show up. User can choose 1 to 4.

```bash
----< Login menu >
----(1) Login
----(2) Sign Up
----(3) Login as Administrator (Manager of Auction System)
----(4) Quit
    Your Choice: 
```

1. Login

If user Login with correct format, main menu will show up.

```bash
## Login with Correct Format
Your Choice: 1
----< Login >
---- ID : msj5531
---- password : wjddntjr

## Login with Wrong ID
Your Choice: 1
----< Login >
---- ID : wrongID
---- password : asdf
There is no ID wrongID!

## Login with Wrong Password
Your Choice: 1
----< Login >
---- ID : msj5531
---- password : wrongpassword
Wrong Password!
```

1. Sign up

If user signs up with correct format, account will be insert into database with authority of user.
Whether user sign up with correct format or not, it returns to Login menu.

```bash
## Sign up with Correct Format
Your Choice: 2
----<Sign up >
---- first name: Hong
---- last name: GilDong
---- enter ID: hgd1234
---- enter PW: 1234
---- enter email: email@email.com
Sign Up Complete.

## Sign up with duplicate ID
Your Choice: 2
----<Sign up >
---- first name: hong
---- last name: gildong
---- enter ID: hgd1234
---- enter PW: 1234
---- enter email: asdf@asdf.com
hgd1234 is already used. Please use another ID
```

1. Login as Admin

Same mechanism with login for ID,Password, but also checks it is Admin or not. if successfully login with admin account, admin menu will show up.

```bash
## Login with Admin account
Your Choice: 3
----< Login >
---- ID : admin
---- password : admin

## Login with Normal account
Your Choice: 3
----< Login >
---- ID : hgd1234
---- password : 1234
You are Not Admin!
```

1. Quit

```bash
Your Choice: 4
Bye
```

## Main Menu

after login, Main menu will show up. User can choose 1 to 6 

```bash
----< Main Menu > 
     (1) Sell item
     (2) Status of Your Item Listed on Auction
     (3) Search item
     (4) Check Status of your Bid
     (5) Check your Account
     (6) Quit
---- Enter the Number :
```

1. Sell item

Same with example format. but if user write bid ending date before current time, user should write ending date again with right format.

```bash
## Sell item Successfully.
---- Enter the Number : 1
----< Sell item > 
---- select from the following category : 
    (1) Electronics
    (2) Books
    (3) Home
    (4) Clothing
    (5) Sporting Goods
---- Enter your category : 1
---- condition : 
    (1) New
    (2) Like-New
    (3) Used (Good)
    (4) Used (Acceptable)
---- Enter your condition : 3
---- description: GoodUsedDishwasher
---- buy-it-now price: 5000
---- bid ending date (in format YYYY-MM-DD HH:MM:SS): 2022-11-29 09:11:22
Sell item success.

## Wrong ending date
---- Enter the Number : 1
----< Sell item > 
---- select from the following category : 
    (1) Electronics
    (2) Books
    (3) Home
    (4) Clothing
    (5) Sporting Goods
---- Enter your category : 4
---- condition : 
    (1) New
    (2) Like-New
    (3) Used (Good)
    (4) Used (Acceptable)
---- Enter your condition : 2
---- description: 123
---- buy-it-now price: 1234
---- bid ending date (in format YYYY-MM-DD HH:MM:SS): 2022-09-11 05:12:12
---- Write ending date after now : 2022-11-30 09:12:12
Sell item success.
```

1. Status of Your Item Listed on Auction

Same with example format.

```bash
---- Enter the Number : 2
----< Status of Your Item Listed on Auction >
[Item 1]
   description: SmartTV
   status : sold
   sold price : 23123
   buyer : msj5531
   sold date : 2022-11-23 09:27:46
[Item 2]
   description: Home_Kit
   status : 1 bids
   current bidding price: 123
   current highest bidder: msj5531
   date posted: 2022-11-21 01:22:05
[Item 10]
   description: testfortime
   status : sold
   sold price : 100
   buyer : User
   sold date : 2022-11-28 15:26:01
...
```

1. Search item

```bash
---- Enter the Number : 3
----< Search item > : 
----(1) Search items by category
----(2) Search items by description keyword
----(3) Search items by seller
----(4) Search items by date posted
----(5) Go Back
----(6) Quit
---- Enter the Number : 1
----< Search items by category > : 
----(1) Electronics
----(2) Books
----(3) Home
----(4) Clothing
----(5) Sporting Goods
1
----< Status of Your Item Listed on Auction >
[Item 1]
   description: tmptmp
   status : 0 bids
   current bidding price: 0
   current highest bidder: null
   date posted: 2022-11-28 15:34:46
[Item 2]
   description: GoodUsedDishwasher
   status : 0 bids
   current bidding price: 0
   current highest bidder: null
   date posted: 2022-11-28 17:13:19
--- Which item do you want to bid? (Enter the number or 'B' to go back to the previous menu): 2
You Choosed GoodUsedDishwasher.
## Buy in buy-it-now price
--- Bidding price? (Enter the price or 'buy' to pay for the buy-it-now price) : buy
You Bought Item in buy-it-now price.
## Bid price
--- Bidding price? (Enter the price or 'buy' to pay for the buy-it-now price) : 5
You bidded item in price 5

## Nothing searched
---- Enter the Number : 3
----< Search item > : 
----(1) Search items by category
----(2) Search items by description keyword
----(3) Search items by seller
----(4) Search items by date posted
----(5) Go Back
----(6) Quit
---- Enter the Number : 2
----< Search items by description keyword > : nasdfasdfzcxv
----< Status of Your Item Listed on Auction >
Nothing searched.
# Go back to Serach menu
```

1. Check Status of your bid

Same with example

```bash
---- Enter the Number : 4
----<Check Status of your Bid > 
[Item 1]
   description: GoodUsedDishwasher
   status: You won the item
   sold price: 5000
   sold date: 2022-11-28 17:21:33
[Item 2]
   description: Home_Kit
   status: You are the highest bidder
   your bidding price: 123
   current highest bidding price: 123
   bid ending date: 2022-12-23 11:11:11
[Item 3]
   description: PS4
   status: Your are outbidded and the item is sold
   sold price: 100
   sold date: 2022-11-28 15:26:01
...
```

1. Check your Account

Same with example

```bash
----< Check your Account >
[Sold Item 1]
   description: Dish_Washer
   sold price: 2000
[Sold Item 2]
   description: Vaccum
   sold price: 3000
[Purchased Item 1]
   description: PS4
   purchase price: 1500
[Purchased Item 2]
   description: For_Test
   purchase price: 3000
[Your Balance Summary]
   sold: 5000 won
   commission: 100 won
   purchased: 4500 won   
   Total balance: 400 won
```

1. Quit

```bash
---- Enter the Number : 6
You Selected Quit.
```

## Admin Menu

```bash
----< Admin Menu > 
     (1) Ban User
     (2) Change User Information
     (3) Show Bid History
     (4) Show Selling items
     (5) Show Sold items
     (6) Show Seller Rating
     (7) Give Admin Authority
     (8) Quit
---- Enter the Number : 
```

1. Ban User

```bash
---- Enter the Number : 1
----< Ban User > 
Write The User ID : bnam
Successfully banned User bnam

# banned user login
----< Login >
---- ID : bnam
---- password : 1234
You are Banned!
```

1. Change User Information

```bash
---- Enter the Number : 2
----< Change User Information > 
Write The User ID : bnam

Which information Do you want to change?
----(1) Password
----(2) First Name
----(3) Last Name
    Your Choice : 2
Please text the input you want to change to : kim
Update Complete
```

1. Show bid History

Similar with `Status of Your Item Listed on Auction` but it shows all of user’s bid history.

```bash
---- Enter the Number : 3
----< Show Bid History > 
[Item 1]
   Description: asdfasd
   Bidder : msj5531
   Bid Price : 100
   Bid Post Date : 2022-11-21 00:29:01
   Status : Sold
   Buyer : bnam
   sold price: 100
   sold date: 2022-11-22 12:12:12
[Item 2]
   Description: For_Test
   Bidder : bnam
   Bid Price : 3000
   Bid Post Date : 2022-11-21 00:26:05
   Status : Sold
   Buyer : msj5531
   sold price: 3000
   sold date: 2022-11-21 00:28:05
[Item 3]
   Description: Home_Kit
   Bidder : msj5531
   Bid Price : 123
   Bid Post Date : 2022-11-21 01:22:25
   Status : Selling
   Highest Bidder: bnam
   bidding price: 123
   bid ending date: 2022-12-23 11:11:11
```

1. Show Selling Items

Show all Selling items

```bash
---- Enter the Number : 4
----< Show Selling Items >
[Item 1]
   Description: Home_Kit
   Category: Home
   Condition: New
   Buy It Now Price: 12345
   Bid Ending Date: 2022-12-23 11:11:11
   Seller: msj5531
   Current Highest Bidding Price: 123
   Current Highest Bid Bidder: bnam
[Item 2]
   Description: tmptmp
   Category: Electronics
   Condition: Like-New
   Buy It Now Price: 12355
   Bid Ending Date: 2022-12-12 11:11:11
   Seller: msj5531
   Current Highest Bidding Price: 0
   Current Highest Bid Bidder: null
```

1. Show Sold Items

Show all Sold items

```bash
----< Show Sold Items >
[Item 1]
   Description: For_Test
   Category: Home
   Condition: New
   Buy It Now Price: 3000
   Sold Price : 3000
   Sold Date : 2022-11-21 00:28:05
   Buyer : msj5531
[Item 2]
   Description: asdfasd
   Category: Clothing
   Condition: Like-New
   Buy It Now Price: 100
   Sold Price : 100
   Sold Date : 2022-11-22 12:12:12
   Buyer : bnam
```

1. Show Seller Rating

Show seller rating in particular criteria

```bash
---- Enter the Number : 6
----< Show Seller Rating > 
Which Rating Do you want to see?
---- (1) Seller Rating in Sold Count
---- (2) Seller Rating in total Sold_Price
---- (3) Seller Rating in Sold_Price for one item
---- (4) Exit
     Your Choice : 1
[Ranking] ID
[ Rank #1] msj5531
[ Rank #2] bnam
```

1. Give Admin

Give user admin authority.

```bash
---- Enter the Number : 7
----< Give Admin > 
Write The User ID : bnam
Successfully give Admin Authorization User bnam
```

## Additional Description

If program runs and user login, JDBC checks the list of selling items.

And if some item exceeded bid ending date, update it to sold item.

```java
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
```

➕Additional Comment
As my program check set dates (ex. date_posted, Post_date) when user insert on sql. so my example table looks they were all transfered in same time because I used trigger for the time handling.
But when user code with JDBC, it’s fine. ⇒ In short, sample tablee transfered in same time and it is not error.

Also, as I used description for primary key and kind of ID because I don’t really think there are no much duplicate name in reused auction. So, if user try to sell item with duplicated item description, program stops it.
