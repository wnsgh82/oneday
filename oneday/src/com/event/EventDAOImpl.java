package com.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.util.DBConn;

public class EventDAOImpl implements EventDAO {
	private Connection conn = DBConn.getConnection();
	
	@Override
	public int insertEvent(EventDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO event(userName, eSubject, eContent, eIFN, eHitCount, eDate) VALUES (?, ?, ?, ?, 0, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserName());
			pstmt.setString(2, dto.geteSubject());
			pstmt.setString(3, dto.geteContent());
			pstmt.setString(4, dto.geteFIN());
			pstmt.setInt(5, dto.geteHitCount());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}

	@Override
	public int updateEvent(EventDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEvent(int eNum) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int dataCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<EventDTO> listEvevnt(int offset, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventDTO readEvent(int eNum) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
