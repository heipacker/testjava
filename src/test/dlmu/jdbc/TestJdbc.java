package test.dlmu.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestJdbc {

	private Connection conn = null;
	
	public void startUp(){
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//oracle.jdbc.driver.OracleDriver
			conn = DriverManager.getConnection("jdbc:sqlserver://192.168.0.188:1434;instance=INSTANCE;DatabaseName=HTEST", "YH3B_COMMON_USER", "COMMON_USER");
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.188:1521:DSBUS", "YH3B_VESSEL_USER", "VESSEL_USER");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void begin(){
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void commit(){
		try {
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		toggle();
	}
	
	public void toggle(){
		try {
			conn.setAutoCommit(!conn.getAutoCommit());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void rollback(){
		try {
			conn.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		toggle();
	}
	
	public void testTransaction(){
		Statement stmt = null;
		ResultSet rs = null;
		try {
			begin();
			
			stmt = conn.createStatement();
			stmt.execute("insert into T_EVENT(TITLE) VALUES('sfasdf')");
			
			rs = stmt.executeQuery("SELECT * FROM T_EVENT");
			while (rs.next()) {
				System.out.println(rs.getString("TITLE"));
			}
			/*if(System.currentTimeMillis()>1){
				throw new SQLException();
			}*/
			commit();
		} catch (SQLException e) {
			e.printStackTrace();
			rollback();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestJdbc test = new TestJdbc();
		test.startUp();
		
		test.testTransaction();
		
		test.close();
	}

	public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testDateType(){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("SELECT * FROM T_VESSEL_REGIST WHERE REG_NO IN (?,?)");
			//stmt = conn.prepareStatement("SELECT * FROM T_VESSEL_REGIST WHERE LAST_UPDATE_TIME < ?");
			java.util.Date utilDate = new java.util.Date();  
            java.sql.Date date = new java.sql.Date(utilDate.getTime());
            date.setYear(112);
            stmt.setDate(1, date);
            //Types.INTEGER;
            //stmt.setString(1, "420810000054");
            //stmt.setString(2, "420303000081");
			rs = stmt.executeQuery();
			int count = 0;
			while (rs.next()) {
				//System.out.println(rs.getInt("ID") + "	" + rs.getDouble("SHIP_VALUE"));
				System.out.println(rs.getInt("ID") + "	" + rs.getDate("LAST_UPDATE_TIME").toGMTString());
				++count;
			}
			System.out.println(count);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
