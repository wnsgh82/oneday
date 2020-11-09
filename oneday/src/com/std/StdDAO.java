package com.std;

import java.sql.SQLException;
import java.util.List;


public interface StdDAO {
	public int insertStd(StdDTO dto) throws SQLException;
	public int updateStd(StdDTO dto) throws SQLException;
	public int deleteStd(int classNum) throws SQLException;
	
	public int dataCount();
	public List<StdDTO> listStd(int offset,int rows);
	public List<StdDTO> listStd(String userId);
	public List<StdDTO> listStd2(String userId);
	public StdDTO readStd(String userId , int classNum);
}
