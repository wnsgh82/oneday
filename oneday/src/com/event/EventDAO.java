package com.event;

import java.sql.SQLException;
import java.util.List;

public interface EventDAO {
	public int insertEvent(EventDTO dto) throws SQLException;
	public int updateEvent(EventDTO dto) throws SQLException;
	public int deleteEvent(int eNum) throws SQLException;
	public int updateHitCount(int eNum) throws SQLException;
	public int dataCount();
	public List<EventDTO> listEvent(int offset, int rows);
	public EventDTO readEvent(int eNum);
	public int applyEvent(EventDTO dto) throws SQLException;
	public int eventNo1(EventDTO dto) throws SQLException;
	public boolean eventEnabled(int eNum, String userId);
}
