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
		//�ۿø���
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
		//�ۼ���
		PreparedStatement pstmt=null;
		int result=0;
		String sql;
		
		try {
			sql="UPDATE notice SET notice=?, noSubject=?, noContent=?, noSFN=?, noOFN=? "
					+ "   WHERE noNum=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getNotice());
			pstmt.setString(2, dto.getNoSubject());
			pstmt.setString(3, dto.getNoContent());
			pstmt.setString(4, dto.getNoSaveFileName());
			pstmt.setString(5, dto.getNoOrginalFileName());
			pstmt.setInt(6, dto.getNoNum());
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

	@Override
	public int deleteNotice(int num) throws SQLException {
		//�ۻ��� (�Խñ۾ȿ���)
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="DELETE FROM notice WHERE noNum = ? ";
			
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

	@Override
	public int deleteList(int[] nums) throws SQLException {
		//�Խñۻ���(����Ʈ���� üũ�� )
		int result=0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql = "DELETE FROM notice WHERE noNum IN (";
			for(int i=0; i<nums.length; i++) {
				sql += "?,";
			}
			sql = sql.substring(0, sql.length()-1) + ")";
			
			pstmt=conn.prepareStatement(sql);
			for(int i=0; i<nums.length; i++) {
				pstmt.setInt(i+1, nums[i]);
			}
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}
		
		return result;
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
			sql="SELECT NVL(COUNT(*), 0) FROM notice";
			if(condition.equalsIgnoreCase("created")) {
				//��¥�� �˻������� ���� �ٲ��ֱ�
				keyword=keyword.replaceAll("(\\-||\\/||\\.)", "");
        		sql+="   WHERE TO_CHAR(noCreated, 'YYYYMMDD') = ?  ";
        	} else if(condition.equals("all")) {
        		sql+="   WHERE INSTR(noSubject, ?) >= 1 OR INSTR(noContent, ?) >= 1 ";
        	} else {
        		sql+="   WHERE INSTR(" + condition + ", ?) >= 1 ";
        	}
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
            if(condition.equals("all")) {
                pstmt.setString(2, keyword);
            }
            
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
		//�۸���Ʈ
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
		//�۸���Ʈ(�˻���)
		List<NoticeDTO> list=new ArrayList<NoticeDTO>();
		PreparedStatement pstmt=null;
		StringBuilder sb=new StringBuilder();
		ResultSet rs=null;
		
		try {
			sb.append("SELECT noNum, noSubject, noSFN, noHitCount, noCreated");
			sb.append("   FROM notice");
			if(condition.equalsIgnoreCase("created")) {
				keyword=keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append("   WHERE TO_CHAR(noCreated, 'YYYYMMDD') = ?");
			} else if(condition.equals("all")) {
				sb.append(" WHERE INSTR(noSubject, ?) >= 1 OR INSTR(noContent, ?) >= 1 ");
			} else {
				sb.append("   WHERE INSTR(" +condition+ ", ?) > =1");
			}
			sb.append("	  ORDER BY noNum DESC");
			sb.append("   OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
			
			pstmt=conn.prepareStatement(sb.toString());
            if(condition.equals("all")) {
    			pstmt.setString(1, keyword);
    			pstmt.setString(2, keyword);
    			pstmt.setInt(3, offset);
    			pstmt.setInt(4, rows);
            } else {
    			pstmt.setString(1, keyword);
    			pstmt.setInt(2, offset);
    			pstmt.setInt(3, rows);
            }
            
			
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
	public List<NoticeDTO> listNotice() {
		//������ �ö���� ������
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
		//��������
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
