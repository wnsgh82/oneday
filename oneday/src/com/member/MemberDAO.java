package com.member;

import java.sql.SQLException;
import java.util.List;

public interface MemberDAO {
	
	public int insertMember(MemberDTO dto) throws SQLException;
	public int updateMember(MemberDTO dto) throws SQLException;
	public int deleteMember(String userId) throws SQLException; //관리자가 회원 탈퇴
	public int deleteMember(String userId, String userPwd) throws SQLException; //회원이 직접 탈퇴
	
	public MemberDTO readMember(String userId);
	public List<MemberDTO> listMember(); //관리자가 회원 목록 열람
	
	
	
}
