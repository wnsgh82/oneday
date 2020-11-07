package com.review;

import java.sql.SQLException;
import java.util.List;



public interface ReviewDAO {
	
	public int insertReview(ReviewDTO dto) throws SQLException;
	public int updateReview(ReviewDTO dto) throws SQLException;
	public int deleteReview(int num) throws SQLException;
	public ReviewDTO readReview(int num);
	
	
	public int dataCount();
	public int dataCount(String condition, String keyword);
	public List<ReviewDTO> ReviewReview(int offset, int rows);
	public List<ReviewDTO> listReview(int offset, int rows, String condition, String keyword);
	
	
	
}
