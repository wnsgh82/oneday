package com.std;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.util.DBConn;

public class StdDAOImpl implements StdDAO{
	private Connection conn=DBConn.getConnection();
	
	@Override
	public int insertStd(StdDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		int result =0;
		String sql ;
		
		try {
			sql= "INSERT INTO std (classNum , userId , className, trName, userName , userEmail , classDate , stdEnabled)"
					+ " VALUES(? , ? , ? , ? , ? , ? ,? ,0)";		
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getClassNum());
			pstmt.setString(2,dto.getUserId());
			pstmt.setString(3,dto.getClassName());
			pstmt.setString(4,dto.getTrName());
			pstmt.setString(5,dto.getUserName());
			pstmt.setString(6,dto.getUserEmail());
			pstmt.setString(7,dto.getClassDate());
			
			result = pstmt.executeUpdate();
				
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
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
	public int updateStd(StdDTO dto) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteStd(int classNum) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int dataCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<StdDTO> listStd(int offset, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StdDTO readStd(int num) {
		// TODO Auto-generated method stub
		return null;
	}

}
