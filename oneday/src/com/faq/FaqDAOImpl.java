package com.faq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.notice.NoticeDTO;
import com.qna.QnaDTO;
import com.util.DBConn;

public class FaqDAOImpl implements FaqDAO {
	private Connection conn=DBConn.getConnection();

	@Override
	public int insertFaq(FaqDTO dto) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="INSERT INTO board1(bNum, bQ, bA, bGroup)"
					+ "  VALUES(board1_seq.NEXTVAL, ?, ?, ?)";
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getbQ());
			pstmt.setString(2, dto.getbA());
			pstmt.setString(3, dto.getbGroup());
			
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
	public int updateFaq(FaqDTO dto) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="UPDATE board1 SET bQ=?, bA=?, bGroup=?"
					+ "   WHERE bNum=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getbQ());
			pstmt.setString(2, dto.getbA());
			pstmt.setString(3, dto.getbGroup());
			pstmt.setInt(4, dto.getbNum());
			
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
	public int deleteFaq(int bNum) throws SQLException {
		//글삭제 (게시글안에서)
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="DELETE FROM board1 WHERE bNum = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bNum);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
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
			sql="SELECT COUNT(*) FROM board1";
			pstmt=conn.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
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
	public int dataCount(String condition, String keyword) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT COUNT(*) FROM board1";
			if(condition.equalsIgnoreCase("bGroup")) {
				sql+="   WHERE INSTR(" + condition + ", ?) >= 1 ";
        	} else if(condition.equals("all")) {
        		sql+="   WHERE INSTR(bQ, ?) >= 1 OR INSTR(bA, ?) >= 1 ";
        	}
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
            if(condition.equals("all")) {
                pstmt.setString(2, keyword);
            }
            
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
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
	public List<FaqDTO> listFaq(int offset, int rows) {
		//글리스트
		List<FaqDTO> list = new ArrayList<>();
		PreparedStatement pstmt=null;
		String sql;
		ResultSet rs=null;
		
		try {
			sql="SELECT bNum, bQ, bA, bGroup"
					+ "   FROM board1"
					+ "	  ORDER BY bNum DESC"
					+ "   OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				
				FaqDTO dto= new FaqDTO();
				
				dto.setbNum(rs.getInt("bNum"));
				dto.setbQ(rs.getString("bQ"));
				dto.setbA(rs.getString("bA"));
				dto.setbGroup(rs.getString("bGroup"));

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
	public List<FaqDTO> listFaq(int offset, int rows, String condition, String keyword) {
		List<FaqDTO> list = new ArrayList<>();
		PreparedStatement pstmt=null;
		StringBuilder sb=new StringBuilder();
		ResultSet rs=null;
		
		try {
			sb.append(" SELECT bNum, bQ, bA, bGroup ");
			sb.append("    FROM board1");
			if(condition.equals("all")) {
				sb.append(" WHERE INSTR(bQ, ?) >= 1 OR INSTR(bA, ?) >= 1 ");
			} else {
				sb.append(" WHERE INSTR(" + condition + ", ?) >= 1  ");
			}
			sb.append(" ORDER BY bNum DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt=conn.prepareStatement(sb.toString());

			if(condition.equals("all")) {
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, rows);
			} else { 
    			pstmt.setString(1, keyword);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, rows);
			}
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				FaqDTO dto= new FaqDTO();
				
				dto.setbNum(rs.getInt("bNum"));
				dto.setbQ(rs.getString("bQ"));
				dto.setbA(rs.getString("bA"));
				dto.setbGroup(rs.getString("bGroup"));

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
	public FaqDTO readFaq(int bNum) {
		FaqDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT bNum, bQ, bA, bGroup"
					+ "  FROM board1"
					+ "  WHERE bNum=?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, bNum);
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				dto=new FaqDTO();
				dto.setbNum(rs.getInt("bNum"));
				dto.setbQ(rs.getString("bQ"));
				dto.setbA(rs.getString("bA"));
				dto.setbGroup(rs.getString("bGroup"));
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
