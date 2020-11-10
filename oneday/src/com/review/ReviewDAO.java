package com.review;

import java.sql.SQLException;
import java.util.List;

import com.notice.NoticeDTO;



public interface ReviewDAO {
	
	public int insertReview(ReviewDTO dto) throws SQLException;
	public int updateReview(ReviewDTO dto) throws SQLException;
	public int deleteReview(int num) throws SQLException;
	public ReviewDTO readReview(int num);
	public ReviewDTO readReview(String userId , int classNum);
	public ReviewDTO readReview(String userId);
	
	
	public int reviewcount(String userId);
	
	public int updateHitCount(int num) throws SQLException;
	public int dataCount();
	public int dataCount(String condition, String keyword);
	public List<ReviewDTO> listReview(int offset, int rows);
	public List<ReviewDTO> listReview(int offset, int rows, String condition, String keyword);
	public List<ReviewDTO> readlist(String userId);
	
	
	
	public ReviewDTO preReadNotice(int num, String condition, String keyword);
	public ReviewDTO nextReadNotice(int num, String condition, String keyword);
	
	
}
