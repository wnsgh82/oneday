package com.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
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
			sql = "INSERT INTO event(eNum, eSubject, eContent, eHitCount, eIFN, eCreated, eStart, eEnd) VALUES (EVENT_SEQ.NEXTVAL, ?, ?, 0, ?, SYSDATE, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.geteSubject());
			pstmt.setString(2, dto.geteContent());
			pstmt.setString(3, dto.geteIFN());
			pstmt.setString(4, dto.geteStart());
			pstmt.setString(5, dto.geteEnd());
			
			
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
			sql = "UPDATE EVENT SET eSubject=?, eContent=?, eIFN=?, eStart=?, eEnd=? WHERE eNum=?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.geteSubject());
			pstmt.setString(2, dto.geteContent());
			pstmt.setString(3, dto.geteIFN());
			pstmt.setString(4, dto.geteStart());
			pstmt.setString(5, dto.geteEnd());
			pstmt.setInt(6, dto.geteNum());
			
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
	public int deleteEvent(int num) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM EVENT WHERE eNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
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
			
		} catch (SQLException e) {
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
	public List<EventDTO> listEvent(int offset, int rows) {
		List<EventDTO> list = new ArrayList<EventDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		String sql;
		
		try {			
			sql="SELECT eNum, eSubject, eIFN, eHitCount, "
					+ " TO_CHAR(eCreated, 'YYYY-MM-DD') eCreated, TO_CHAR(eStart, 'YYYY-MM-DD') eStart, TO_CHAR(eEnd, 'YYYY-MM-DD') eEnd "
					+ " FROM EVENT ORDER BY eEnd DESC "
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
				dto.seteStart(rs.getString("eStart"));
				dto.seteEnd(rs.getString("eEnd"));
				
				list.add(dto);
			}			
			
		} catch (SQLException e) {
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
		dto = new EventDTO();
		
		try {
			sql = "SELECT eNum, eSubject, eContent, eHitCount, eIFN, eCreated, "
					+ " TO_CHAR(eStart, 'YYYY-MM-DD') eStart, TO_CHAR(eEnd, 'YYYY-MM-DD') eEnd "
					+ " FROM EVENT WHERE eNum=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, eNum);
			rs=pstmt.executeQuery();
//			pstmt.close();
//			rs.close();
			if(rs.next()) {
				dto.seteNum(rs.getInt("eNum"));
				dto.seteSubject(rs.getString("eSubject"));
				dto.seteContent(rs.getString("eContent"));
				dto.seteHitCount(rs.getInt("eHitCount"));
				dto.seteIFN(rs.getString("eIFN"));
				dto.seteCreated(rs.getString("eCreated"));
				dto.seteStart(rs.getString("eStart"));
				dto.seteEnd(rs.getString("eEnd"));
			}
/*			
			sql = "SELECT eventApplyEnabled, userId FROM EVENTAPPLY WHERE eNum = ?";
			if(rs.next()) {
				dto.setEventApplyEnabled(rs.getInt("setEventApplyEnabled"));
				dto.setUserId(rs.getString("userId"));
			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, eNum);
			rs = pstmt.executeQuery();
*/			
		} catch (SQLException e) {
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

	@Override
	public int updateHitCount(int eNum) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		// 글보기에서 조회수 증가
		try {
			sql = "UPDATE EVENT SET eHitCount=eHitCount+1 WHERE eNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, eNum);
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}
		
		return result;
	}

	@Override
	public int applyEvent(EventDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO EVENTAPPLY (userId, eNum) VALUES (?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
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

}
