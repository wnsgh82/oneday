package com.oneday;

import java.sql.SQLException;
import java.util.List;

public interface OnedayDAO {
	public int insertOneday(OnedayDTO dto) throws SQLException;
	public int updateOneday(OnedayDTO dto) throws SQLException;
	public int deleteOneday(int num) throws SQLException;
	
	public int dataCount();
	public List<OnedayDTO> listOneday(int offset,int rows);
	public OnedayDTO readOneday(int num);
}
