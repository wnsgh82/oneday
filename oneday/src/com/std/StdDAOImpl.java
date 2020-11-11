package com.std;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.member.MemberDTO;
import com.util.DBConn;

public class StdDAOImpl implements StdDAO{
	private Connection conn=DBConn.getConnection();
	
	@Override
	public int insertStd(StdDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		int result =0;
		String sql ;
		
		try {
			sql= "INSERT INTO std (classNum , userId , className, trName, userName , userEmail , classDate , stdEnabled)"
					+ " VALUES(? , ? , ? , ? , ? , ? ,? ,0)";		
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getClassNum());
			pstmt.setString(2,dto.getUserId());
			pstmt.setString(3,dto.getClassName());
			pstmt.setString(4,dto.getTrName());
			pstmt.setString(5,dto.getUserName());
			pstmt.setString(6,dto.getUserEmail());
			pstmt.setString(7,dto.getClassDate());
			
			result = pstmt.executeUpdate();
				
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			
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
	public int updateStd(StdDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteStd(int classNum) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int dataCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<StdDTO> listStd(int offset, int rows) {
		List<StdDTO> list=new ArrayList<StdDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="";
			
					//order by userEnable(수강생, 강사)로 할지말지 고민중~
			
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				StdDTO dto=new StdDTO();
				
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
	public StdDTO readStd(String userId , int classNum) {
		StdDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="select classNum , userId , className, trName, userName , userEmail , classDate , stdEnabled "
					+ "  from std"
					+ "  where userId = ? AND classNum = ?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, classNum);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new StdDTO();
				
				dto.setClassNum(rs.getInt("classNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setClassName(rs.getString("className"));
				dto.setTrName(rs.getString("trName"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserEmail(rs.getString("userEmail"));
				dto.setClassDate(rs.getString("classDate"));
				dto.setStdEnable(rs.getInt("stdEnabled"));
				
				
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

	@Override
	public List<StdDTO> listStd(String userId) {
		List<StdDTO> list=new ArrayList<StdDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="select classNum , userId , className, trName, userName , userEmail , classDate , stdEnabled , "
					+ "  from std"
					+ "  where userId = ?";
			
					//order by userEnable(수강생, 강사)로 할지말지 고민중~
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				StdDTO dto=new StdDTO();
				
				dto.setClassNum(rs.getInt("classNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setClassName(rs.getString("className"));
				dto.setClassDate(rs.getString("classDate"));
				dto.setTrName(rs.getString("trName"));
				

				
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
	public List<StdDTO> listStd2(String userId) {
		List<StdDTO> list=new ArrayList<StdDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT * FROM( " + 
					"  SELECT  std.classNum, std.className,std.userId,trName,classDate, NVL(rvnum, 0) rvnum " + 
					"  FROM std " + 
					"  LEFT OUTER JOIN review ON std.classNum=review.classNum " + 
					"  WHERE  std.userId= ?) " + 
					"  WHERE rvnum=0 ";
				
			
					//order by userEnable(수강생, 강사)로 할지말지 고민중~
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				StdDTO dto=new StdDTO();
				
				dto.setClassNum(rs.getInt("classNum"));
				dto.setClassName(rs.getString("className"));
				dto.setUserId(rs.getString("userId"));
				dto.setTrName(rs.getString("trName"));
				dto.setClassDate(rs.getString("classDate"));
				

				
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
	

}
