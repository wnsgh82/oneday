package com.event_apply;

import java.sql.SQLException;

public interface EventAplyDAO {

	public int applyEvent(EventAplyDTO dto) throws SQLException;
}
