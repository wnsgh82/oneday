package com.review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import com.notice.NoticeDTO;
import com.util.DBConn;

public class ReviewDAOImpl implements ReviewDAO{
	private Connection conn = DBConn.getConnection();
	
	@Override
	public int insertReview(ReviewDTO dto) throws SQLException {
		int result=0;
		String sql;
		PreparedStatement pstmt=null;
		
		try {
			sql="INSERT INTO review (rvNum, rvContent, rvClassname, rvScore, classNum , userId, rvhitcount , rvcreated ) "
					+ "  VALUES (REVIEW_SEQ.NEXTVAL, ? ,? , ? , ? , ?,0 ,SYSDATE )";
	
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, dto.getRvContent());
			pstmt.setString(2, dto.getRvClassName());
			pstmt.setString(3, dto.getRvScore());
			pstmt.setInt(4, dto.getClassNum());
			pstmt.setString(5, dto.getUserId());
			
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
	public int updateReview(ReviewDTO dto) throws SQLException {
		int result= 0;
		PreparedStatement pstmt= null;
		String sql ;
		
		try {
			sql = " UPDATE review SET rvContent= ? , rvScore = ? WHERE rvNum = ? ";
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, dto.getRvContent());
			pstmt.setString(2, dto.getRvScore());
			pstmt.setInt(3, dto.getRvNum());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		
		
		return result;
	}

	@Override
	public int deleteReview(int num) throws SQLException {
		PreparedStatement pstmt= null;
		int result = 0;
		String sql;
		
		try {
			sql="DELETE FROM review WHERE rvNum = ? ";
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			result = pstmt.executeUpdate();
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!= null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}

	@Override
	public ReviewDTO readReview(int num) {
		ReviewDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		sql = "SELECT rvNum , rvContent , rvclassname , rvscore , classNum , userId , rvhitcount , rvcreated ";
		sql+= "  FROM review WHERE rvNum = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if( rs.next()) {
				dto = new ReviewDTO();
				
				dto.setRvNum(rs.getInt("rvNum"));
				dto.setRvContent(rs.getString("rvContent"));
				dto.setRvClassName(rs.getString("rvclassname"));
				dto.setRvScore(rs.getString("rvscore"));
				dto.setClassNum(rs.getInt("classNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setRvHitcount(rs.getInt("rvhitcount"));
				dto.setRvCreated(rs.getString("rvcreated"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return dto;
	}


	@Override
	public int dataCount() {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM review";
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
			
			sql="SELECT NVL(COUNT(*), 0) FROM review WHERE INSTR(" + condition + ", ?) >= 1 ";
        			
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
	public List<ReviewDTO> listReview(int offset, int rows) {
		List<ReviewDTO> list = new ArrayList<>();
		PreparedStatement pstmt=null;
		String sql;
		ResultSet rs=null;
		
		try {
			sql="SELECT rvNum , rvContent , rvclassname , rvscore , classNum , userId , rvhitcount , rvcreated "
					+ "   FROM review "
					+ "	  ORDER BY rvNum DESC "
					+ "   OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				
				ReviewDTO dto= new ReviewDTO();
				
				dto.setRvNum(rs.getInt("rvNum"));
				dto.setRvContent(rs.getString("rvContent"));
				dto.setRvClassName(rs.getString("rvclassname"));
				dto.setRvScore(rs.getString("rvscore"));
				dto.setClassNum(rs.getInt("classNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setRvHitcount(rs.getInt("rvhitcount"));
				dto.setRvCreated(rs.getString("rvcreated"));
							
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
	public List<ReviewDTO> listReview(int offset, int rows, String condition, String keyword) {
		List<ReviewDTO> list=new ArrayList<ReviewDTO>();
		PreparedStatement pstmt=null;
		StringBuilder sb=new StringBuilder();
		ResultSet rs=null;
		
		try {
			sb.append("SELECT rvNum , rvContent , rvclassname , rvscore , classNum , userId , rvhitcount , rvcreated ");
			sb.append("   FROM review");
			sb.append("   WHERE INSTR(" +condition+ ", ?)>=1"); //컨디션을 클래스네임으로 
			sb.append("	  ORDER BY rvNum DESC");
			sb.append("   OFFSET ? ROWS FETCH FIRST ? ROWS ONLY");
			
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setString(1, keyword);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				ReviewDTO dto=new ReviewDTO();
				dto.setRvNum(rs.getInt("rvNum"));
				dto.setRvContent(rs.getString("rvContent"));
				dto.setRvClassName(rs.getString("rvclassname"));
				dto.setRvScore(rs.getString("rvscore"));
				dto.setClassNum(rs.getInt("classNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setRvHitcount(rs.getInt("rvhitcount"));
				dto.setRvCreated(rs.getString("rvcreated"));
				
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
	public int updateHitCount(int num) throws SQLException {
		int result=0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE review SET rvhitCount=rvhitCount+1 WHERE rvNum=?";
			
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
	
    public ReviewDTO preReadNotice(int num, String condition, String keyword) {
    	ReviewDTO dto=null;

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuilder sb=new StringBuilder();

        try {
            if(keyword.length() != 0) {
                sb.append("SELECT rvNum, rvclassName FROM review ");
                sb.append(" WHERE (INSTR(" + condition + ", ?) >= 1)  ");
              
                sb.append("         AND (rvNum > ? )  ");
                sb.append(" ORDER BY rvNum ASC  ");
                sb.append(" FETCH  FIRST  1  ROWS  ONLY");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setString(1, keyword);
                pstmt.setInt(2, num);
			} else {
                sb.append("SELECT rvNum, rvclassName FROM review ");                
                sb.append(" WHERE rvNum > ?  ");
                sb.append(" ORDER BY rvNum ASC  ");
                sb.append(" FETCH  FIRST  1  ROWS  ONLY");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setInt(1, num);
			}

            rs=pstmt.executeQuery();

            if(rs.next()) {
                dto=new ReviewDTO();
                dto.setRvNum(rs.getInt("rvNum"));
                dto.setRvClassName(rs.getString("rvclassName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
    
        return dto;
    }

    // 다음글
    public ReviewDTO nextReadNotice(int num, String condition, String keyword) {
    	ReviewDTO dto=null;

        PreparedStatement pstmt=null;
        ResultSet rs=null;
        StringBuilder sb=new StringBuilder();

        try {
            if(keyword.length() != 0) {
                sb.append("SELECT rvNum, rvclassName FROM review ");
                sb.append(" WHERE (INSTR(" + condition + ", ?) >= 1)  ");
                sb.append("         AND (rvNum < ? )  ");
                sb.append(" ORDER BY rvNum DESC  ");
                sb.append(" FETCH  FIRST  1  ROWS  ONLY");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setString(1, keyword);
                pstmt.setInt(2, num);
			} else {
                sb.append("SELECT rvNum, rvclassName FROM review ");
                sb.append(" WHERE rvNum < ?  ");
                sb.append(" ORDER BY rvNum DESC  ");
                sb.append(" FETCH  FIRST  1  ROWS  ONLY");

                pstmt=conn.prepareStatement(sb.toString());
                pstmt.setInt(1, num);
			}

            rs=pstmt.executeQuery();

            if(rs.next()) {
                dto=new ReviewDTO();
                dto.setRvNum(rs.getInt("rvNum"));
                dto.setRvClassName(rs.getString("rvclassName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
    
        return dto;
    }

	@Override
	public ReviewDTO readReview(String userId, int classNum) {
		ReviewDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		sql = "SELECT rvNum , rvContent , rvclassname , rvscore , classNum , userId , rvhitcount , rvcreated ";
		sql+= "  FROM review WHERE userId = ? AND classNum = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setInt(2, classNum);
			
			rs = pstmt.executeQuery();
			
			if( rs.next()) {
				dto = new ReviewDTO();
				
				dto.setRvNum(rs.getInt("rvNum"));
				dto.setRvContent(rs.getString("rvContent"));
				dto.setRvClassName(rs.getString("rvclassname"));
				dto.setRvScore(rs.getString("rvscore"));
				dto.setClassNum(rs.getInt("classNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setRvHitcount(rs.getInt("rvhitcount"));
				dto.setRvCreated(rs.getString("rvcreated"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return dto;
	}

	@Override
	public ReviewDTO readReview(String userId) {
		ReviewDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		sql = "SELECT rvNum , rvContent , rvclassname , rvscore , classNum , userId , rvhitcount , rvcreated ";
		sql+= "  FROM review WHERE userId = ? ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			
			rs = pstmt.executeQuery();
			
			if( rs.next()) {
				dto = new ReviewDTO();
				
				dto.setRvNum(rs.getInt("rvNum"));
				dto.setRvContent(rs.getString("rvContent"));
				dto.setRvClassName(rs.getString("rvclassname"));
				dto.setRvScore(rs.getString("rvscore"));
				dto.setClassNum(rs.getInt("classNum"));
				dto.setUserId(rs.getString("userId"));
				dto.setRvHitcount(rs.getInt("rvhitcount"));
				dto.setRvCreated(rs.getString("rvcreated"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return dto;
	}

	@Override
	public int reviewcount(String userId) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT COUNT(*) FROM review WHERE userId= ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			
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


}
