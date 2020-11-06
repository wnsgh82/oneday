package com.notice;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class NoticeDAOImpl implements NoticeDAO {

	@Override
	public int insertNotice(NoticeDTO dto) throws SQLException {
		//글올리기
		int result=0;
		PreparedStatement pstmt=null;
		
		String sql;
		
		return result;
	}

	@Override
	public int updateNotice(NoticeDTO dto) throws SQLException {
		//글수정
		
		return 0;
	}

	@Override
	public int deleteNotice(int num) throws SQLException {
		//글삭제 (게시글안에서)
		
		return 0;
	}

	@Override
	public int deleteList(int[] nums) throws SQLException {
		//게시글삭제(리스트에서 체크로 )
		return 0;
	}

	@Override
	public int dataCount() {
		//총데이터갯수
		return 0;
	}

	@Override
	public int dataCount(String condition, String keyword) {
		//검색시 데이터갯수
		return 0;
	}

	@Override
	public List<NoticeDTO> listNotice(int offset, int rows) {
		//글리스트
		return null;
	}

	@Override
	public List<NoticeDTO> listNotice(int offset, int rows, String condition, String keyword) {
		//글리스트(검색시)
		
		return null;
	}

	@Override
	public List<NoticeDTO> listNotice() {
		//맨위에 올라오는 공지글
		
		return null;
	}

	@Override
	public NoticeDTO readNotice(int num) {
		//가져오기
		
		return null;
	}

}
