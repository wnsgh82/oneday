package com.review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.util.DBConn;

public class ReviewDAOImpl implements ReviewDAO{
	private Connection conn = DBConn.getConnection();
	
	@Override
	public int insertReview(ReviewDTO dto) throws SQLException {
		int result=0;
		String sql;
		PreparedStatement pstmt=null;
		
		try {
			sql="INSERT INTO review (rvNum, rvContent, rvClassname, rvScore, classNum , userId, rvhitcount , rvcreated) "
					+ "  VALUES (REVIEW_SEQ.NEXTVAL, ? ,? , ? , ? , ?,0 ,SYSDATE)";
	
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getRvContent());
			pstmt.setString(2, dto.getRvClassName());
			pstmt.setString(3, dto.getRvScore());
			pstmt.setInt(4, dto.getClassNum());
			pstmt.setString(5, dto.getUserId());
			
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
	public int updateReview(ReviewDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteReview(int num) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ReviewDTO readReview(int num) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int dataCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int dataCount(String condition, String keyword) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ReviewDTO> ReviewReview(int offset, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReviewDTO> listReview(int offset, int rows, String condition, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
