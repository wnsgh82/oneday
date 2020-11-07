package com.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class NoticeDAOImpl implements NoticeDAO {
	private Connection conn=DBConn.getConnection();
	@Override
	public int insertNotice(NoticeDTO dto) throws SQLException {
		//글올리기
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="INSERT INTO notice(notice, noNum, noSubject, noContent, noCreated, noSFN, noOFN, noHitCount)"
					+ "  VALUES(?, notice_seq.NEXTVAL, ?, ?, SYSDATE, ?, ?, 0)";
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getNotice());
			pstmt.setString(2, dto.getNoSubject());
			pstmt.setString(3, dto.getNoContent());
			pstmt.setString(4, dto.getNoSaveFileName());
			pstmt.setString(5, dto.getNoOrginalFileName());
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
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
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM notice";
			pstmt=conn.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
			}
			
		} catch (SQLException e) {
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
		
		
		return result;
	}

	@Override
	public int dataCount(String condition, String keyword) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			if(condition.equalsIgnoreCase("created")) {
				//날짜로 검색했으면 형시 바꿔주기
				keyword=keyword.replaceAll("(\\-||\\/||\\.)", "");
        		sql="SELECT NVL(COUNT(*), 0) FROM notice WHERE TO_CHAR(noCreated, 'YYYYMMDD') = ?  ";
        	} else {
        		sql="SELECT NVL(COUNT(*), 0) FROM notice WHERE INSTR(" + condition + ", ?) >= 1 ";
        	}
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
			}
			
		} catch (SQLException e) {
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
		
		
		return result;
	}

	@Override
	public List<NoticeDTO> listNotice(int offset, int rows) {
		//글리스트
		List<NoticeDTO> list = new ArrayList<>();
		PreparedStatement pstmt=null;
		String sql;
		ResultSet rs=null;
		
		try {
			sql="SELECT noNum, noSubject, noSFN, noHitCount, noCreated"
					+ "   FROM notice"
					+ "	  ORDER BY noNum DESC"
					+ "   OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				
				NoticeDTO dto= new NoticeDTO();
				
				dto.setNoNum(rs.getInt("noNum"));
				dto.setNoSubject(rs.getString("noSubject"));
				dto.setNoSaveFileName(rs.getString("noSFN"));
				dto.setNoHitCount(rs.getInt("noHitCount"));
				dto.setNoCreated(rs.getString("noCreated"));

				list.add(dto);
				
			}
			
		} catch (SQLException e) {
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

	@Override
	public List<NoticeDTO> listNotice(int offset, int rows, String condition, String keyword) {
		//글리스트(검색시)
		List<NoticeDTO> list=new ArrayList<NoticeDTO>();
		PreparedStatement pstmt=null;
		StringBuilder sb=new StringBuilder();
		ResultSet rs=null;
		
		try {
			sb.append("SELECT noNum, noName, noSubject, noSFN, noHitCount, noCreated");
			sb.append("   FROM notice");
			if(condition.equalsIgnoreCase("created")) {
				keyword=keyword.replaceAll("(\\-||\\/||\\.)", "");
				sb.append("   WHERE TO_CHAR(created, 'YYYYMMDD')=?");
			} else {
				sb.append("   WHERE INSTR(" +condition+ ", ?)>=1");
			}
			sb.append("	  ORDER BY noNum DESC");
			sb.append("   OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, keyword);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeDTO dto=new NoticeDTO();
				dto.setNoNum(rs.getInt("noNum"));
				dto.setNoName(rs.getString("noName"));
				dto.setNoSubject(rs.getString("noSubject"));
				dto.setNoContent(rs.getString("noContent"));
				dto.setNoSaveFileName(rs.getString("noSFN"));
				dto.setNoHitCount(rs.getInt("noHitCount"));
				dto.setNoCreated(rs.getString("noCreated"));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
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

	@Override
	public List<NoticeDTO> listNotice() {
		//맨위에 올라오는 공지글
		List<NoticeDTO> list=new ArrayList<NoticeDTO>();
		PreparedStatement pstmt=null;
		String sql;
		ResultSet rs=null;
		
		try {
			sql="SELECT noNum, noSubject, noSFN, noHitCount, noCreated"
					+ "   FROM notice"
					+ "   WHERE notice=1"
					+ "	  ORDER BY noNum DESC";
			pstmt=conn.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeDTO dto=new NoticeDTO();
				dto.setNoNum(rs.getInt("noNum"));
				dto.setNoSubject(rs.getString("noSubject"));
				dto.setNoSaveFileName(rs.getString("noSFN"));
				dto.setNoHitCount(rs.getInt("noHitCount"));
				dto.setNoCreated(rs.getString("noCreated"));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
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

	@Override
	public NoticeDTO readNotice(int num) {
		//가져오기
		NoticeDTO dto=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="SELECT noNum, noSubject, noContent, noCreated, noHitCount, noSFN, noOFN"
					+ "   FROM notice"
					+ "   WHERE noNum=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new NoticeDTO();
				
				dto.setNoNum(rs.getInt("noNum"));
				dto.setNoSubject(rs.getString("noSubject"));
				dto.setNoContent(rs.getString("noContent"));
				dto.setNoCreated(rs.getString("noCreated"));
				dto.setNoHitCount(rs.getInt("noHitCount"));
				dto.setNoSaveFileName(rs.getString("noSFN"));
				dto.setNoOrginalFileName(rs.getString("noOFN"));
				
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
		
		return dto;
	}

	@Override
	public int updateHitCount(int num) throws SQLException {
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE notice SET noHitCount=noHitCount+1 WHERE noNum=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}

}
