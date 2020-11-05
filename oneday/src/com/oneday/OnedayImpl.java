package com.oneday;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class OnedayImpl implements OnedayDAO{
	private Connection conn=DBConn.getConnection();

	@Override
	public int insertOneday(OnedayDTO dto) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="insert into classtb(classNum, className, classAddr, classCount, classPrice,"
						+" classContent, classHitCount, classEnabled, classIFN, "
						+" classCreated, classStart, classEnd, userId, userName)"
						+" values(classtb_seq.NEXTVAL,?,?,?,?,?,0,1,?,sysdate,?,?,?,?)";
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getClassName());
			pstmt.setString(2, dto.getClassAddr());
			pstmt.setInt(3, dto.getClassCount());
			pstmt.setString(4, dto.getClassPrice());
			pstmt.setString(5, dto.getClassContent());
			pstmt.setString(6, dto.getClassIFN());
			pstmt.setString(7, dto.getClassStart());
			pstmt.setString(8, dto.getClassEnd());
			pstmt.setString(9, dto.getUserId());
			pstmt.setString(10, dto.getUserName());
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
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
	public int updateOneday(OnedayDTO dto) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="update classtb set className=?, classAddr=?, classCount=?, classPrice=?,"
					+ " classContent=?, classIFN=?, classStart=?, classEnd=? "
					+ " from classtb"
					+ " where classNum = ?";
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getClassName());
			pstmt.setString(2, dto.getClassAddr());
			pstmt.setInt(3, dto.getClassCount());
			pstmt.setString(4, dto.getClassPrice());
			pstmt.setString(5, dto.getClassContent());
			pstmt.setString(6, dto.getClassIFN());
			pstmt.setString(7, dto.getClassStart());
			pstmt.setString(8, dto.getClassEnd());
			pstmt.setInt(9, dto.getClassNum());
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
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
	public int deleteOneday(int num) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="delete from classtb where classNum=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
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
	public int dataCount() {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="select count(*) from classtb";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				result=rs.getInt(1);
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
	public List<OnedayDTO> listOneday(int offset, int rows) {
		List<OnedayDTO> list=new ArrayList<OnedayDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="select classNum, className, classAddr, classCount, classPrice,"
					+ " classContent, classHitCount, classEnabled, classIFN,"
					+ " classCreated, classStart, classEnd, c.userId, userName"
					+ " from classtb c"
					+ " join member1 m on m.userId=c.userId"
					+ " order by classNum desc"
					+ " offset ? rows fetch first ? rows only";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				OnedayDTO dto=new OnedayDTO();
				
				dto.setClassNum(rs.getInt("classNum"));
				dto.setClassName(rs.getString("className"));
				dto.setClassAddr(rs.getString("classAddr"));
				dto.setClassCount(rs.getInt("classCount"));
				dto.setClassPrice(rs.getString("classPrice"));
				dto.setClassContent(rs.getString("classContent"));
				dto.setClassHitCount(rs.getInt("classHitCount"));
				dto.setClassEnabled(rs.getInt("classEnabled"));
				dto.setClassIFN(rs.getString("classIFN"));
				dto.setClassCreated(rs.getString("classCreated"));
				dto.setClassStart(rs.getString("classStart"));
				dto.setClassEnd(rs.getString("classEnd"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));	
				
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
	public OnedayDTO readOneday(int num) {
		OnedayDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="select classNum, className, classAddr, classCount, classPrice,"
					+ " classContent, classHitCount, classEnabled, classIFN,"
					+ " classCreated, classStart, classEnd, c.userId, userName"
					+ " from classtb c"
					+ " join member1 m on m.userId=c.userId"
					+ " where classNum = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new OnedayDTO();
				dto.setClassNum(rs.getInt("classNum"));
				dto.setClassName(rs.getString("className"));
				dto.setClassAddr(rs.getString("classAddr"));
				dto.setClassCount(rs.getInt("classCount"));
				dto.setClassPrice(rs.getString("classPrice"));
				dto.setClassContent(rs.getString("classContent"));
				dto.setClassHitCount(rs.getInt("classHitCount"));
				dto.setClassEnabled(rs.getInt("classEnabled"));
				dto.setClassIFN(rs.getString("classIFN"));
				dto.setClassCreated(rs.getString("classCreated"));
				dto.setClassStart(rs.getString("classStart"));
				dto.setClassEnd(rs.getString("classEnd"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));	
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
