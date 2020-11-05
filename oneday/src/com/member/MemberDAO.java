package com.member;

import java.sql.SQLException;
import java.util.List;

public interface MemberDAO {
	
	public int insertMember(MemberDTO dto) throws SQLException;
	public int updateMember(MemberDTO dto) throws SQLException;
	public int deleteMember(String userId) throws SQLException; //�����ڰ� ȸ�� Ż��
	public int deleteMember(String userId, String userPwd) throws SQLException; //ȸ���� ���� Ż��
	
	public MemberDTO readMember(String userId);
	public List<MemberDTO> listMember(); //�����ڰ� ȸ�� ��� ����
	
	
	
}
