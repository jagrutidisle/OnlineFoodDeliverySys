import java.sql.Date;
import java.sql.*;
import java.util.*;


public class OFDS {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String URL="jdbc:mysql://localhost:3306/ofds?useSSL=false";
	static final String USER = "root";
    static final String PASS = "root";

	public static void main(String[] args) {
		try(Connection conn=DriverManager.getConnection(URL,USER,PASS)) {
			Class.forName(JDBC_DRIVER);
			Scanner sc=new Scanner(System.in);
			int ch;
			do {
				System.out.println("\nMENU:");
                System.out.println("1. Insert Menu");
                System.out.println("2. Display employees");
                System.out.println("3. Update employee");
                System.out.println("4. Delete employee");
                System.out.println("5. Search employee");
                System.out.println("6. insert customer");
                System.out.print("Enter your choice: ");
                ch=sc.nextInt();
                
                switch(ch) {
                case 1 : insertMenu(conn, sc);
                         break;
                case 2 : displayMenu(conn);
                         break;
                case 3 : updateMenu(conn, sc);
                         break;
                case 4 : deleteMenu(conn, sc);
                         break;
                case 5 : searchMenu(conn, sc);
                         break;
                case 6 : insertCustomer(conn, sc);
                         break;
                default : System.out.println("Invalid choice.");
                }
			} while(ch!=7);
			sc.close();
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	
//	Customer Table
//	Insert
	public static void insertCustomer(Connection conn, Scanner sc) throws SQLException {
	       System.out.println("Enter Customer Number:");
	       int c_no = sc.nextInt();
	       sc.nextLine();
	       System.out.println("Customer Name:");
	       String c_name = sc.nextLine();
	       System.out.println("Customer Mobile Number:");
	       String c_mob = sc.next();
	       System.out.print("Date of Birth (YYYY-MM-DD): ");
	       String date_of_birth = sc.next();
	       sc.nextLine();
	       System.out.print("Customer City: ");
	       String c_city = sc.nextLine();
	       System.out.print("Registration Date (YYYY-MM-DD): ");
	       String registration_date = sc.next();
	       sc.nextLine();
	       System.out.print("Address: ");
	       String c_address = sc.nextLine();
	       System.out.print("Preferred Payment Method: ");
	       String preferred_payment_method = sc.nextLine();
	       System.out.print("Status (Active/Suspended/Deleted): ");
	       String c_status = sc.nextLine();
	       System.out.print("Is this a Prime Customer or Regular Customer? (P/R): ");
	       String c_type=sc.nextLine();
	 
	       String sql = "INSERT INTO Customer (c_no, c_name, c_mob, date_of_birth, c_city, registration_date, "
	       		+ "c_address, preferred_payment_method, c_status, c_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	      
	       try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	           pstmt.setInt(1, c_no);
	           pstmt.setString(2, c_name);
	           pstmt.setString(3, c_mob);
	           pstmt.setDate(4, Date.valueOf(date_of_birth));
	           pstmt.setString(5, c_city);
	           pstmt.setDate(6, Date.valueOf(registration_date));
	           pstmt.setString(7, c_address);
	           pstmt.setString(8, preferred_payment_method);
	           pstmt.setString(9, c_status);
	           pstmt.setString(10, c_type);
	           pstmt.executeUpdate();
	       }
	      
//	       System.out.print("Is this a Prime Customer or Regular Customer? (P/R): ");
//	       char type = sc.next().charAt(0);
//	       sc.nextLine();
	       if (c_type.equalsIgnoreCase("p")) {
	           System.out.print("Enter Date of Membership (YYYY-MM-DD): ");
	           String date_of_membership = sc.next();
	           System.out.print("Enter Amount Paid for Membership: ");
	           double amount_paid = sc.nextDouble();
	           sc.nextLine();
	           System.out.print("Enter Special Discounts: ");
	           String special_discounts = sc.nextLine();
	           System.out.print("Enter Dish of the Month: ");
	           String dish_of_the_month = sc.nextLine();
	           System.out.print("Enter Membership Status (Active/Expired/Revoked): ");
	           String membership_status = sc.nextLine();
	           sql = "INSERT INTO PrimeCustomer (c_no, date_of_membership, amount_paid, special_discounts, "
	           		+ "dish_of_the_month, membership_status) VALUES (?, ?, ?, ?, ?, ?)";
	           try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	               pstmt.setInt(1, c_no);
	               pstmt.setDate(2, Date.valueOf(date_of_membership));
	               pstmt.setDouble(3, amount_paid);
	               pstmt.setString(4, special_discounts);
	               pstmt.setString(5, dish_of_the_month);
	               pstmt.setString(6, membership_status);
	               pstmt.executeUpdate();
	               System.out.println("Prime Customer added.");
	           }
	       } else if (c_type.equalsIgnoreCase("r")) {
	           System.out.print("Enter Loyalty Points: ");
	           int points = sc.nextInt();
	           System.out.print("Enter Points Redeemed: ");
	           int points_redeemed = sc.nextInt();
	           System.out.print("Enter Last Order Date (YYYY-MM-DD): ");
	           String last_order_date = sc.next();
	           sc.nextLine();
	           sql = "INSERT INTO RegularCustomer (c_no, points, points_redeemed, last_order_date) VALUES (?, ?, ?, ?, ?)";
	           try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	               pstmt.setInt(1, c_no);
	               pstmt.setInt(2, points);
	               pstmt.setInt(3, points_redeemed);
	               pstmt.setDate(4, Date.valueOf(last_order_date));
	               pstmt.executeUpdate();
	               System.out.println("Regular Customer added.");
	           }
	       }
	   }
	
	
//	Menu Table
//	Insert method
	public static void insertMenu(Connection conn, Scanner sc) throws SQLException {
		System.out.println("Enter Item Number :");
		int m_id=sc.nextInt();
		sc.nextLine();
		System.out.println("Item Name :");
		String m_name=sc.nextLine();
		System.out.println("Item Description :");
		String m_description=sc.nextLine();
		System.out.println("Item Price :");
		double m_price=sc.nextDouble();
		sc.nextLine();
		System.out.println("Veg / Non-veg ?");
		String m_category=sc.nextLine();
		System.out.println("Type : ");
		String m_type=sc.nextLine();
		System.out.println("Available ?");
		String m_availability=sc.nextLine();
		
		String sql="INSERT INTO MENU (m_id, m_name, m_description, m_price, m_category, m_type, m_availability) VALUES (?,?,?,?,?,?,?)";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, m_id);
            pstmt.setString(2, m_name);
            pstmt.setString(3, m_description);
            pstmt.setDouble(4, m_price);
            pstmt.setString(5, m_category);
            pstmt.setString(6, m_type);
            pstmt.setString(7, m_availability);
            
            int rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted + " row(s) inserted.");
		}
	}
	
//	Display Method
	public static void displayMenu(Connection conn) throws SQLException {
        String sql = "SELECT * FROM MENU";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

        	while (rs.next()) {
                System.out.printf("m_id: %d, m_name: %s, m_description: %s, m_price: %.2f, m_category: %s, m_type: %s, m_availability: %s\n",
                        rs.getInt("m_id"), rs.getString("m_name"), rs.getString("m_description"), rs.getDouble("m_price"), 
                        rs.getString("m_category"), rs.getString("m_type"), rs.getString("m_availability"));
            }
        }
    }
	
//	Update method
	public static void updateMenu(Connection conn, Scanner sc) throws SQLException {
		System.out.print("Enter Item ID to update: ");
        int id = sc.nextInt();
        sc.nextLine(); 

        System.out.print("Enter updated Price : ");
        double price = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter updated Availability : ");
        String av = sc.nextLine();

        String sql = "UPDATE Menu SET m_availability = ?, m_price = ? WHERE m_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, av);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, id);

            int rowsUpdated = pstmt.executeUpdate();
            System.out.println(rowsUpdated + " row(s) updated.");
        }
    }

//	Delete Method
	public static void deleteMenu(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE from Menu WHERE m_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            int rowsDeleted = pstmt.executeUpdate();
            System.out.println(rowsDeleted + " row(s) deleted.");
        }
    }
	
//	Search Method
	public static void searchMenu(Connection conn, Scanner sc) {
    	System.out.println("Enter Item ID :");
    	int id=sc.nextInt();
    	
    	String sql="SELECT * from Menu WHERE m_id = ?";
    	try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    		pstmt.setInt(1, id);
    		
    		try (ResultSet rs = pstmt.executeQuery()) {
    			if(rs.next()) {
    				System.out.printf("m_id: %d, m_name: %s, m_description: %s, m_price: %.2f, m_category: %s, m_type: %s, m_availability: %s\n",
                            rs.getInt("m_id"), rs.getString("m_name"), rs.getString("m_description"), rs.getDouble("m_price"), 
                            rs.getString("m_category"), rs.getString("m_type"), rs.getString("m_availability"));
    			}
    			else {
    				System.out.println("Item not found :(");
    			}
    		}
    	}
    	catch( SQLException e) {
    		System.out.println("Error occured !");
    		e.printStackTrace();
    	}
    }

}
