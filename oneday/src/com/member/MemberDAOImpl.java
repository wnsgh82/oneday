package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class MemberDAOImpl implements MemberDAO{
	private Connection conn=DBConn.getConnection();
	
	@Override
	public int insertMember(MemberDTO dto , int enable) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql = "";
		
		try {

			
			if(enable==1) {
				sql="insert into member1(userId, userPwd, userName, userTel,"
						+ " userZip, userAddr1, userAddr2, userEmail, userEnabled)"
						+ " values(?,?,?,?,?,?,?,?,1)";				
			}else {
				sql="insert into member1(userId, userPwd, userName,  userTel,"
						+ " userZip, userAddr1, userAddr2, userEmail, userEnabled, userCert)"
						+ " values(?,?,?,?,?,?,?,?,100,?)";			
			}
		
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getUserPwd());
			pstmt.setString(3, dto.getUserName());
			pstmt.setString(4, dto.getUserTel());
			pstmt.setString(5, dto.getUserZip());
			pstmt.setString(6, dto.getUserAddr1());
			pstmt.setString(7, dto.getUserAddr2());
			pstmt.setString(8, dto.getUserEmail());
			if(enable==100) {
				pstmt.setString(9, dto.getUserCert());
			}
			
			result=pstmt.executeUpdate();
			

			
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
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
	public int updateMember(MemberDTO dto) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		StringBuilder sb= new StringBuilder();
		
		try {		
			sb.append("update member1 set userPwd=?, userName=?, userTel=?, userZip=?, "
					+ " userAddr1=?, userAddr2=?, userEmail=? ");
			
			if(dto.getUserEnabled()==100) {
				sb.append(", userCert=? ");
			}
			
			sb.append("where userId= ? ");
			
			pstmt=conn.prepareStatement(sb.toString());
			System.out.println("야: "+dto.userEnabled);
			System.out.println(sb.toString());
			
			if(dto.userEnabled==100) {
				pstmt.setString(1, dto.getUserPwd());
				pstmt.setString(2, dto.getUserName());
				pstmt.setString(3, dto.getUserTel());
				pstmt.setString(4, dto.getUserZip());
				pstmt.setString(5, dto.getUserAddr1());
				pstmt.setString(6, dto.getUserAddr2());
				pstmt.setString(7, dto.getUserEmail());
				pstmt.setString(8, dto.getUserCert());
				pstmt.setString(9, dto.getUserId());
			}else {
				pstmt.setString(1, dto.getUserPwd());
				pstmt.setString(2, dto.getUserName());
				pstmt.setString(3, dto.getUserTel());
				pstmt.setString(4, dto.getUserZip());
				pstmt.setString(5, dto.getUserAddr1());
				pstmt.setString(6, dto.getUserAddr2());
				pstmt.setString(7, dto.getUserEmail());
				pstmt.setString(8, dto.getUserId());
			}
			
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
	public int deleteMember(String userId) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteMember(String userId, String userPwd) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="delete from member1 where userId=? and userPwd=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
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
	public MemberDTO readMember(String userId) {
		MemberDTO dto=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="select userId, userPwd, userName, userTel,"
					+ " userZip, userAddr1, userAddr2, userEmail, userEnabled, userCert, userPoint"
					+ " from member1"
					+ " where userId = ?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new MemberDTO();
				
				dto.setUserId(rs.getString("userId"));
				dto.setUserPwd(rs.getString("userPwd"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserTel(rs.getString("userTel"));
				dto.setUserZip(rs.getString("userZip"));
				dto.setUserAddr1(rs.getString("userAddr1"));
				dto.setUserAddr2(rs.getString("userAddr2"));
				dto.setUserEmail(rs.getString("userEmail"));
				
				//enabled가 1이면 수강생, 100이면 강사, 200이면 관리자
				dto.setUserEnabled(rs.getInt("userEnabled"));
				
				dto.setUserCert(rs.getString("userCert"));
				dto.setUserPoint(rs.getInt("userPoint"));
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
	public List<MemberDTO> listMember() { //offset, rows 해야되냐 말아야되냐 보류
		List<MemberDTO> list=new ArrayList<MemberDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="select userId, userName, userBirth, userTel,"
					+ " userZip, userAddr1, userAddr2, userEmail, userEnabled, userCert"
					+ " from member1";
			
					//order by userEnable(수강생, 강사)로 할지말지 고민중~
			
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				MemberDTO dto=new MemberDTO();
				
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setUserBirth(rs.getString("userBirth"));
				dto.setUserTel(rs.getString("UserTel"));
				dto.setUserZip(rs.getString("UserZip"));
				dto.setUserAddr1(rs.getString("UserAddr1"));
				dto.setUserAddr2(rs.getString("UserAddr2"));
				dto.setUserEmail(rs.getString("UserEmail"));
				dto.setUserEnabled(rs.getInt("UserEnabled"));
				dto.setUserCert(rs.getString("UserCert"));
				
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
	public MemberDTO searchId(String userName, String userEmail) throws SQLException {
		MemberDTO dto=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="select userId from member1 where userName=? and userEmail=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, userEmail);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto= new MemberDTO();
				
				dto.setUserName(userName);
				dto.setUserId(rs.getString("userId"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
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
	public MemberDTO searchPwd(String userId, String userName) throws SQLException {
		MemberDTO dto=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="select userPwd from member1 where userName=? and userId=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setString(2, userId);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto= new MemberDTO();
				
				dto.setUserName(userName);
				dto.setUserPwd(rs.getString("userPwd"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
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
