package com.mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.oneday.OnedayDTO;
import com.util.DBConn;

public class TrmyDAO {
	private Connection conn=DBConn.getConnection();
	
	public List<TrmyDTO> readClass(String userId) {
		List<TrmyDTO> list=new ArrayList<TrmyDTO>();	
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="select classNum, className, c.userId, c.userName, "
					+ " to_char(classStart,'yyyy-mm-dd') classStart,"
					+ " to_char(classEnd,'yyyy-mm-dd') classEnd, classEnabled "
					+ " from classtb c"
					+ " join member1 m on m.userId=c.userId"
					+ " where c.userId=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				TrmyDTO dto=new TrmyDTO();
				dto.setClassNum(rs.getInt("classNum"));
				dto.setClassName(rs.getString("className"));
				
				String classEnabled = rs.getInt("classEnabled")==1 ? "진행중": "진행완료";
				dto.setClassEnabled(classEnabled);
				
				String classDate= rs.getString("classStart")+" ~ "+rs.getString("classEnd");
				dto.setClassDate(classDate);
				
				dto.setUserId(rs.getString("userId"));
				dto.setUserName(rs.getString("userName"));	
				
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
