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

			
			if(enable==0) {
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteMember(String userId) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteMember(String userId, String userPwd) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MemberDTO readMember(String userId) {
		MemberDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="select userId, userPwd, userName, userTel,"
					+ " userZip, userAddr1, userAddr2, userEmail, userEnabled, userCert"
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
				
				//enabled�� 1�̸� ������, 100�̸� ����, 200�̸� ������
				dto.setUserEnabled(rs.getInt("userEnabled"));
				
				dto.setUserCert(rs.getString("userCert"));
				
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
	public List<MemberDTO> listMember() { //offset, rows �ؾߵǳ� ���ƾߵǳ� ����
		List<MemberDTO> list=new ArrayList<MemberDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="select userId, userName, userBirth, userTel,"
					+ " userZip, userAddr1, userAddr2, userEmail, userEnabled, userCert"
					+ " from member1";
			
					//order by userEnable(������, ����)�� �������� �����~
			
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

}
