package com.mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.util.DBConn;

public class TrmyDAO {
	private Connection conn=DBConn.getConnection();
	
	public TrmyDTO readDTO(String userId) {
		TrmyDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="select classNum, className, c.userId, c.userName, "
					+ " to_char(classStart,'yyyy-mm-dd') classStart,"
					+ " to_char(classEnd,'yyyy-mm-dd') classEnd, classEnabled "
					+ " from classtb c"
					+ " join member1 m on m.userId=c.userId"
					+ " where c.userId=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				dto=new TrmyDTO();
				dto.setClassNum(rs.getInt("classNum"));
				dto.setClassName(rs.getString("className"));
				
				String classEnabled = rs.getInt("classEnabled")==1 ? "진행중": "종료";
				dto.setClassEnabled(classEnabled);
				
				String classDate= rs.getString("classStart")+" ~ "+rs.getString("classEnd");
				dto.setClassDate(classDate);
				
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
	public List<TrmyDTO> listDTO(String userId) {
		List<TrmyDTO> list=new ArrayList<TrmyDTO>();
		TrmyDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="select classNum, className, c.userId, c.userName, "
					+ " to_char(classStart,'yyyy-mm-dd') classStart,"
					+ " to_char(classEnd,'yyyy-mm-dd') classEnd, classEnabled "
					+ " from classtb c"
					+ " join member1 m on m.userId=c.userId"
					+ " where c.userId=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				dto=new TrmyDTO();
				dto.setClassNum(rs.getInt("classNum"));
				dto.setClassName(rs.getString("className"));
				
				String classEnabled = rs.getInt("classEnabled")==1 ? "진행중": "종료";
				dto.setClassEnabled(classEnabled);
				
				String classDate= rs.getString("classStart")+" ~ "+rs.getString("classEnd");
				dto.setClassDate(classDate);
				
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
	
	public List<TrmyDTO> readClass(String userId) { //나의 클래스 
		List<TrmyDTO> list=new ArrayList<TrmyDTO>();	
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="select classNum, className, c.userId, c.userName, "
					+ " to_char(classStart,'yyyy-mm-dd') classStart,"
					+ " to_char(classEnd,'yyyy-mm-dd') classEnd, classEnabled "
					+ " from classtb c"
					+ " join member1 m on m.userId=c.userId"
					+ " where c.userId=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				TrmyDTO dto=new TrmyDTO();
				dto.setClassNum(rs.getInt("classNum"));
				dto.setClassName(rs.getString("className"));
				
				String classEnabled = rs.getInt("classEnabled")==1 ? "진행중": "종료";
				dto.setClassEnabled(classEnabled);
				
				String classDate= rs.getString("classStart")+" ~ "+rs.getString("classEnd");
				dto.setClassDate(classDate);
				
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
	
	public List<TrmyDTO> stdList(String trName, int classNum){
		List<TrmyDTO> list=new ArrayList<TrmyDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="select s.classNum, s.className, s.userName, s.userId, s.userEmail, s.classDate,s.stdEnabled"
					+" from std s" 
					+" join classtb c on c.classNum=s.classNum" 
					+" where s.trName=? and s.classNum=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, trName);
			pstmt.setInt(2, classNum);
			rs=pstmt.executeQuery();
			System.out.println(":"+trName+":"+classNum+":");
			
			while(rs.next()) {
				TrmyDTO dto=new TrmyDTO();
				
				dto.setClassNum(rs.getInt("classNum"));
				dto.setClassName(rs.getString("className"));
				dto.setStdName(rs.getString("userName"));
				dto.setStdId(rs.getString("userId"));
				dto.setStdEmail(rs.getString("userEmail"));
				int enable=rs.getInt("stdEnabled"); 
				String stdEnabled = enable==0 ? "수강중" : "수강완료";  //stdEnabled : 디폴트(수강중) 0, 수강완료 1, .. 
				dto.setStdEnabled(stdEnabled);
				
				dto.setClassDate(rs.getString("classDate"));
				
				dto.setUserName(trName);
				dto.setClassNum(classNum);
				
				System.out.println(rs.getString("userName"));
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
	
	public List<TrmyDTO> memberList (int enabled) {
		List<TrmyDTO> list=new ArrayList<TrmyDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT userId, userName, userTel, userEmail"
					+ "   FROM member1"
					+ "   WHERE userEnabled=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, enabled);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				TrmyDTO dto=new TrmyDTO();
				
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserTel(rs.getString("userTel"));
				dto.setUserEmail(rs.getString("userEmail"));
				
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
	
	public int deleteMemberA(String userId) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="delete from member1 where userId=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
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
	
	
}
