package com.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class QnaDAOImpl implements QnaDAO {
	private Connection conn=DBConn.getConnection();
	
	@Override
	public int insertQna(QnaDTO dto, String mode) throws SQLException {
		PreparedStatement pstmt=null;
		int result=0;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT board2_seq.NEXTVAL FROM dual";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			int seq=0;
			if(rs.next()) {
				seq=rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			
			dto.setbNum(seq); //고유번호?
			if(mode.equals("created")) { //리플아닌 그냥글
				dto.setGroupNum(seq); //자기번호
				dto.setOrderNo(0);
				dto.setDepth(0);
				dto.setParent(0); //부모없음
				dto.setbEnabled(1);
			} else if(mode.equals("reply")) { //답글일때
				updateOrderNo(dto.getGroupNum(), dto.getOrderNo());
				updateEnabled(dto.getParent());
				
				dto.setDepth(dto.getDepth()+1); //하나 뒤로 들어가게
				
				dto.setOrderNo(dto.getOrderNo()+1);
			}
			
			sql="INSERT INTO board2(bNum, bSubject, bContent, bCreated, userId,"
					+ "  groupNum, orderNo, depth, parent, bHitCount, bEnabled)"
					+ "  VALUES (?,?,?,SYSDATE,?,?,?,?,?,0,?)";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getbNum());
			pstmt.setString(2, dto.getbSubject());
			pstmt.setString(3, dto.getbContent());
			pstmt.setString(4, dto.getUserId());
			pstmt.setInt(5, dto.getGroupNum());
			pstmt.setInt(6, dto.getOrderNo());
			pstmt.setInt(7, dto.getDepth());
			pstmt.setInt(8, dto.getParent());
			pstmt.setInt(9, dto.getbEnabled());
			
			
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
	public int updateOrderNo(int groupNum, int orderNo) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="UPDATE board2 SET orderNo=orderNo+1 WHERE bNum=? AND orderNo>?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, groupNum);
			pstmt.setInt(2, orderNo);
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
	public int updateQna(QnaDTO dto) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="UPDATE board2 SET bSubject=?, bContent=? WHERE bNum=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getbSubject());
			pstmt.setString(2, dto.getbContent());
			pstmt.setInt(3, dto.getbNum());
			
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
	public int deleteQna(int bNum) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="DELETE FROM board2 WHERE bNum IN"
					+"   (SELECT bNum FROM board2 START WITH bNum=? CONNECT BY PRIOR bNum=parent) ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bNum);
			
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
	public int updateHitCount(int bNum) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql = "UPDATE board2 SET bHitCount = bHitCount+1 WHERE bNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bNum);
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
	public int updateEnabled(int parent) throws SQLException {
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql = "UPDATE board2 SET bEnabled=0 WHERE bNum = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parent);
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
	public int dataCount() {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT COUNT(*) FROM board2";
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
				} catch (SQLException e2) {
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
        	sql="SELECT COUNT(*) FROM board2 b JOIN member1 m ON b.userId=m.userId ";
        	if(condition.equals("created")) {
        		keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
        		sql+="  WHERE TO_CHAR(created, 'YYYYMMDD') = ? ";
        	} else if(condition.equals("all")) {
        		sql+="  WHERE INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ";
        	} else {
        		sql+="  WHERE INSTR(" + condition + ", ?) >= 1 ";
        	}
        	
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, keyword);
            if(condition.equals("all")) {
                pstmt.setString(2, keyword);
            }

            rs=pstmt.executeQuery();

            if(rs.next())
                result=rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
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
	public List<QnaDTO> listQna(int offset, int rows) {
		List<QnaDTO> list=new ArrayList<QnaDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append(" SELECT bNum, b.userId, userName, bSubject, ");
			sb.append("    groupNum, orderNo, depth, bHitCount, ");
			sb.append("    TO_CHAR(bCreated, 'YYYY-MM-DD') bCreated, bEnabled ");
			sb.append(" FROM board2 b ");
			sb.append(" JOIN member1 m ON b.userId = m.userId ");
			sb.append(" ORDER BY groupNum DESC, orderNo ASC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs=pstmt.executeQuery();
			while(rs.next()) {
				QnaDTO dto=new QnaDTO();
				
				dto.setbNum(rs.getInt("bNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setbSubject(rs.getString("bSubject"));
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setDepth(rs.getInt("depth"));
				dto.setbHitCount(rs.getInt("bHitCount"));
				dto.setbCreated(rs.getString("bCreated"));
				dto.setbEnabled(rs.getInt("bEnabled"));
				

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
	public List<QnaDTO> listQna(int offset, int rows, String condition, String keyword) {
		List<QnaDTO> list=new ArrayList<QnaDTO>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append(" SELECT bNum, b.userId, userName, bSubject, ");
			sb.append("    groupNum, orderNo, depth, bHitCount, ");
			sb.append("    TO_CHAR(bCreated, 'YYYY-MM-DD') bCreated ");
			sb.append(" FROM board2 b ");
			sb.append(" JOIN member1 m ON b.userId = m.userId ");
			if(condition.equals("created")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" WHERE TO_CHAR(bCreated, 'YYYYMMDD') = ?  ");
			} else if(condition.equals("all")) {
				sb.append(" WHERE INSTR(bSubject, ?) >= 1 OR INSTR(bContent, ?) >= 1 ");
			} else {
				sb.append(" WHERE INSTR(" + condition + ", ?) >= 1  ");
			}
			sb.append(" ORDER BY groupNum DESC, orderNo ASC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt=conn.prepareStatement(sb.toString());
			if(condition.equals("all")) {
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, rows);
			} else { 
				pstmt.setInt(1, offset);
				pstmt.setInt(2, rows);
			}
			rs=pstmt.executeQuery();
			while(rs.next()) {
				QnaDTO dto=new QnaDTO();
				
				dto.setbNum(rs.getInt("bNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setbSubject(rs.getString("bSubject"));
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setDepth(rs.getInt("depth"));
				dto.setbHitCount(rs.getInt("bHitCount"));
				dto.setbCreated(rs.getString("bCreated"));

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
	public QnaDTO readQna(int bNum) {
		QnaDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT bNum, b.userId, userName, bSubject, bContent, bCreated, bHitCount,"
					+ "  groupNum, orderNo, depth, parent, bEnabled"
					+ "  FROM board2 b JOIN member1 m ON b.userId=m.userId"
					+ "  WHERE bNum=?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, bNum);
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				dto=new QnaDTO();
				dto.setbNum(rs.getInt("bNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));
				dto.setbSubject(rs.getString("bSubject"));
				dto.setbContent(rs.getString("bContent"));
				dto.setbCreated(rs.getString("bCreated"));
				dto.setbHitCount(rs.getInt("bHitCount"));
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setDepth(rs.getInt("depth"));
				dto.setParent(rs.getInt("parent"));
				dto.setbEnabled(rs.getInt("bEnabled"));
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
	public QnaDTO preReadQna(int groupNum, int orderNo, String condition, String keyword) {
		
		return null;
	}

	@Override
	public QnaDTO nextReadQna(int groupNum, int orderNo, String condition, String keyword) {
		
		return null;
	}


}
