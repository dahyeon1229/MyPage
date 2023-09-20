package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import dto.MembersDTO;

public class MembersDAO {
	private BasicDataSource dataSource = new BasicDataSource();
	// singleton
	private static MembersDAO instance;

	public static MembersDAO getInstance() {
		if (instance == null) {
			instance = new MembersDAO();
		}
		return instance;
	}

	private MembersDAO() {
		dataSource.setInitialSize(10);
		dataSource.setUsername("java");
		dataSource.setPassword("java");
		dataSource.setUrl("jdbc:mysql://localhost/java");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	}

	private Connection getConnection() throws Exception {
		return dataSource.getConnection();
	}

	public boolean isIdExist(String id) throws Exception {
		String sql = "select * from members where id = ?";

		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql)) {
			pstat.setString(1, id);
			try (ResultSet rs = pstat.executeQuery()) {
				return rs.next();
			}
		}
	}

	public int memberOut(String id) throws Exception {
		String sql = "delete from members where id=?";
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql)) {
			pstat.setString(1, id);
			
			return pstat.executeUpdate();
		}
	}
	
	public MembersDTO mypage(String id) throws Exception {
		String sql = "select * from members where id=?";

		try (Connection con = this.getConnection();
			PreparedStatement pstat = con.prepareStatement(sql)) {
			pstat.setString(1, id);
			try (ResultSet rs = pstat.executeQuery()) {
				while(rs.next()) {
					String ids = rs.getString("id");
					String pw = rs.getString("pw");
					String name = rs.getString("name");
					String phone = rs.getString("phone");
					String email = rs.getString("email");
					String zipcode = rs.getString("zipcode");
					String address1 = rs.getString("address1");
					String address2 = rs.getString("address2");
					Timestamp signup_date = rs.getTimestamp("signup_date");
					return new MembersDTO(ids,pw,name,phone,email,zipcode,address1,address2,signup_date);
				}
				
			}return new MembersDTO("0","0","0","0","0","0","0","0",null);
		}
	}

	public int update(String id, String name, String phone, String email, String zipcode, String address1, String address2) throws Exception{
		String sql = "update members set name=? , phone=? , email=? , zipcode=? ,address1=?, address2=? where id=?";
		try (Connection con = this.getConnection(); PreparedStatement pstat = con.prepareStatement(sql)) {
			pstat.setString(1, name);
			pstat.setString(2, phone);
			pstat.setString(3, email);
			pstat.setString(4, zipcode);
			pstat.setString(5, address1);
			pstat.setString(6, address2);
			pstat.setString(7, id);
			
			return pstat.executeUpdate();
		}
	}
	
	
}
