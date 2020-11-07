package com.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.oneday.OnedayDTO;
import com.util.DBConn;

public class EventDAOImpl implements EventDAO {
	private Connection conn = DBConn.getConnection();
	
	@Override
	public int insertEvent(EventDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO event(eNum, eSubject, eContent, eHitCount, eIFN, eCreated) VALUES (EVENT_SEQ.NEXTVAL, ?, ?, 0, ?, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.geteSubject());
			pstmt.setString(2, dto.geteIFN());
			pstmt.setString(3, dto.geteContent());
			
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
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql = "UPDATE EVENT SET eSubject=?, eContent=? eIFN=? WHERE eNum=?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.geteSubject());
			pstmt.setString(2, dto.geteContent());
			pstmt.setString(3, dto.geteIFN());
			pstmt.setInt(4,  dto.geteNum());
			
			result=pstmt.executeUpdate();
			
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
	public int deleteEvent(int eNum) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM EVENT WHERE eNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, eNum);
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
		return result;	}

	@Override
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT COUNT(*) FROM EVENT";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
					
				}
			}
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
	public List<EventDTO> listEvevnt(int offset, int rows) {
		List<EventDTO> list = new ArrayList<EventDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT eNum, eSubject, eIFN, eHitCount, "
					+ " TO_CHAR(eCreated, 'YYYY-MM-DD') eCreated"
					+ " FROM EVENT ORDER BY eCreated DESC "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				EventDTO dto=new EventDTO();
				
				dto.seteNum(rs.getInt("eNum"));
				dto.seteSubject(rs.getString("eSubject"));
				dto.seteIFN(rs.getString("eIFN"));
				dto.seteHitCount(rs.getInt("eHitCount"));
				dto.seteCreated(rs.getString("eCreated"));
				
				list.add(dto);
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
					
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					
				}
			}
		}
		
		return list;
	}


	@Override
	public EventDTO readEvent(int eNum) {
		EventDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT eNum, eSubject, eContent, eHitCount, eIFN, eCreated "
					// 나중에 이벤트 시작, 종료일 추가 예정
					+ " FROM EVENT WHERE eNum=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, eNum);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new EventDTO();
				dto.seteNum(rs.getInt("eNum"));
				dto.seteSubject(rs.getString("eSubject"));
				dto.seteContent(rs.getString("eContent"));
				dto.seteHitCount(rs.getInt("eHitCount"));
				dto.seteIFN(rs.getString("eIFN"));
				dto.seteCreated(rs.getString("eCreated"));
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
					
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
					
				}
			}
		}
		
		return dto;
	}
	
}
