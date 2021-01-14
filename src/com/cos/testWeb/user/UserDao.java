package com.cos.testWeb.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cos.testWeb.config.DBC;
import com.cos.testWeb.user.dto.JoinReqDto;
import com.cos.testWeb.user.dto.LoginReqDto;



public class UserDao {

	public int save(JoinReqDto dto) {	// 회원가입
		String sql = "INSERT INTO user(username, password, email, userRole, createDate) VALUES(?,?,?,?,now())";
		
		Connection conn = DBC.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getUserRole());
			
			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {	// 무조건 실행
			DBC.close(conn, pstmt);
		}
		return -1;
	}
	
	public int findByUsername(String username) {	// 회원가입 -> 아이디 중복체크
		String sql = "SELECT * FROM user WHERE username = ?";
		Connection conn = DBC.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBC.close(conn, pstmt, rs);
		}
		return -1;
	}
	
	public User login(LoginReqDto dto) {	// 로그인
		String sql = "SELECT id, username, email, userRole FROM user WHERE username = ? AND password = ?";
		Connection conn = DBC.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUsername());
			pstmt.setString(2, dto.getPassword());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				User user = User.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.userRole(rs.getString("userRole"))
						.build();
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBC.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public List<User> findAll() {	// 회원 목록
		String sql = "SELECT * FROM user ORDER BY id ASC";
		Connection conn = DBC.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				User user = User.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.userRole(rs.getString("userRole"))
						.createDate(rs.getTimestamp("createDate"))
						.build();
				users.add(user);
			}
			return users;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBC.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public int delete(int id) {		// 회원 삭제
		String sql = "DELETE FROM user WHERE id = ?";
		Connection conn = DBC.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBC.close(conn, pstmt);
		}
		return -1;
	}
}
