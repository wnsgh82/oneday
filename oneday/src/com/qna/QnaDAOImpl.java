package com.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			} else if(mode.equals("reply")) { //답글일때
				updateOrderNo(dto.getGroupNum(), dto.getOrderNo());
				
				dto.setDepth(dto.getDepth()+1); //하나 뒤로 들어가게
				
				dto.setOrderNo(dto.getOrderNo()+1);
			}
			
			sql="INSERT INTO board2(bNum, bSubject, bContent, bCreated, userId,"
					+ "  groupNum, orderNo, depth, parent, bHitCount)"
					+ "  VALUES (?,?,?,SYSDATE,?,?,?,?,?,0)";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getbNum());
			pstmt.setString(2, dto.getbSubject());
			pstmt.setString(3, dto.getbContent());
			pstmt.setString(4, dto.getUserId());
			pstmt.setInt(5, dto.getGroupNum());
			pstmt.setInt(6, dto.getOrderNo());
			pstmt.setInt(7, dto.getDepth());
			pstmt.setInt(8, dto.getParent());
			
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
		
		return 0;
	}

	@Override
	public int deleteQna(int bNum) throws SQLException {
		
		return 0;
	}

	@Override
	public int updateHitCount(int bNum) throws SQLException {
		
		return 0;
	}

	@Override
	public int dataCount() {
		
		return 0;
	}

	@Override
	public int dataCount(String condition, String keyword) {
		
		return 0;
	}

	@Override
	public List<QnaDTO> listQna(int offset, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<QnaDTO> listQna(int offset, int rows, String condition, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QnaDTO readQna(int bNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QnaDTO preReadQna(int groupNum, int orderNo, String condition, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QnaDTO nextReadQna(int groupNum, int orderNo, String condition, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
