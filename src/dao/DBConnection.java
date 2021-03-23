package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection
			("jdbc:oracle:thin:@localhost:1521:xe","kic","1111");
			System.out.println("db connection ok");
		} catch (Exception e) {
			System.out.println("db error");
			e.printStackTrace();
		}
		return conn;
	}
	static void close (Connection conn, 
			Statement stmt, ResultSet rs){
		try {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) {
				conn.commit(); conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		getConnection();
	}
}