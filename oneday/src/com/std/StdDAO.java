package com.std;

import java.sql.SQLException;
import java.util.List;


public interface StdDAO {
	public int insertStd(StdDTO dto) throws SQLException;
	public int updateStd(StdDTO dto) throws SQLException;
	public int deleteStd(int classNum) throws SQLException;
	
	public int dataCount();
	public List<StdDTO> listStd(int offset,int rows);
	public StdDTO readStd(int num);
}
