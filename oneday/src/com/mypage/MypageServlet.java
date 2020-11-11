package com.mypage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.member.MemberDAO;
import com.member.MemberDAOImpl;
import com.member.MemberDTO;
import com.member.SessionInfo;
import com.std.StdDAO;
import com.std.StdDAOImpl;
import com.std.StdDTO;
import com.util.FileManager;
import com.util.MyUploadServlet;

@MultipartConfig
@WebServlet("/mypage/*")
public class MypageServlet extends MyUploadServlet{
	private static final long serialVersionUID = 1L;
	private String pathname;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  req.setCharacterEncoding("utf-8");
	      String uri=req.getRequestURI();
	      
	      HttpSession session=req.getSession();
	      SessionInfo info=(SessionInfo)session.getAttribute("member");
	      
	      //이미지 저장할 경로
		  String root=session.getServletContext().getRealPath("/");
		  pathname=root+"uploads"+File.separator+"photo";
		  
		  //강사 마이페이지 - 나의 클래스에 전달할 파라미터 값
		  TrmyDAO dao=new TrmyDAO();
		  List<TrmyDTO> list=dao.readClass(info.getUserId());

		  long sEnabled, eEnabled;
		  
		  Date curDate=new Date();
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		  
		  for(TrmyDTO dto:list) {
			  try {
					Date edate=sdf.parse(dto.getClassEnd());
					Date sdate=sdf.parse(dto.getClassStart());
					
					eEnabled= (curDate.getTime() - edate.getTime()) /(1000*60*60*24);
					sEnabled= (curDate.getTime() - sdate.getTime()) /(1000*60*60*24);
					
					dto.setEclassEnabled(eEnabled);
					dto.setSclassEnabled(sEnabled);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
		  }		  
		  
		  req.setAttribute("list", list);
		  
	      
	      if(uri.indexOf("mypageMain.do")!= -1) {
	    	  if(info.getUserEnabled()==1) {	//수강생일 때 
	    	 	stdForm(req, resp);
		      }else if(info.getUserEnabled()==100) {	//강사일 때 
		    	  forward(req, resp, "/WEB-INF/views/mypage/mypage_tr.jsp");
		      }else if(info.getUserEnabled()==200) {	//관리자일 때 
		    	  adminMemberList(req, resp);
		      }
	      }else if(uri.indexOf("memberUpdate.do")!=-1) { //공통(정보수정 , 탈퇴)
	    	  updateForm(req, resp);
	      }else if(uri.indexOf("memberUpdate_ok.do")!=-1) {
	    	  updateSubmit(req, resp);
	      }else if(uri.indexOf("pwd.do")!=-1) {
	    	  pwdForm(req, resp);
	      }else if(uri.indexOf("memberDelete.do")!=-1) {
	    	  delete(req, resp);
	      }else if(uri.indexOf("classList.do")!=-1) { //강사(상명)
	    	  trclassList(req, resp);
	      }else if(uri.indexOf("stdlist.do")!=-1) {
	    	  trstdList(req, resp);
	      }else if(uri.indexOf("stdDelete.do")!=-1) {
	    	  stdDelete(req, resp);
	      }else if(uri.indexOf("deleteMemberA.do")!=-1) {
	    	  deleteMemberA(req, resp);
	      }else if(uri.indexOf("classAdmin.do")!=-1) {
	    	  classAdmin(req, resp);
	      }else if(uri.indexOf("deleteClassA.do")!=-1) {
	    	  deleteClassA(req, resp);
	      }
	      
	}
	
	/**
	 *  강사, 수강생 공용 (정보수정, 탈퇴)
	 */
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//회원 정보 수정 폼
		MemberDAO dao=new MemberDAOImpl();
		String cp=req.getContextPath();
		
		HttpSession session=req.getSession();
	    SessionInfo info=(SessionInfo)session.getAttribute("member");
		
	    try {
			MemberDTO dto=dao.readMember(info.getUserId());
			if(dto==null || ! info.getUserId().equals(dto.getUserId())) {
				resp.sendRedirect(cp+"/mypage/mypageMain.do");
				return;
			}
			
			req.setAttribute("dto", dto);
			
			forward(req, resp, "/WEB-INF/views/mypage/update.jsp");
			return;
			
		} catch (Exception e) {
			req.setAttribute("message", "정보 수정이 실패했습니다.");
		}
	    
	    //입력이 제대로 안됐으면 다시 마이페이지 창으로
	    resp.sendRedirect(cp+"/mypage/mypageMain.do");
		
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//회원 정보 수정 처리
		String cp=req.getContextPath();
		MemberDAO dao=new MemberDAOImpl();
		
		HttpSession session=req.getSession();
	    SessionInfo info=(SessionInfo)session.getAttribute("member");
	    
		if(req.getMethod().equalsIgnoreCase("GET")) {
			//GET방식으로 접근한 경우
			resp.sendRedirect(cp+"/mypage/mypageMain.do");
			return;
		}
		
		try {
			MemberDTO dto=new MemberDTO();
			
			dto.setUserId(req.getParameter("userId"));
			dto.setUserPwd(req.getParameter("userPwd"));
			dto.setUserName(req.getParameter("userName"));
			dto.setUserTel(req.getParameter("userTel"));
			dto.setUserZip(req.getParameter("userZip"));
			dto.setUserAddr1(req.getParameter("userAddr1"));
			dto.setUserAddr2(req.getParameter("userAddr2"));
			dto.setUserEmail(req.getParameter("userEmail"));
			dto.setUserEnabled(Integer.parseInt(req.getParameter("userEnabled")));
			
			if(dto.getUserEnabled()==100) {
				String userCert=req.getParameter("userCert");
				
				Part p = req.getPart("userCert");
				Map<String, String> map=doFileUpload(p, pathname);
				if(map!=null) {
					String filename=map.get("saveFilename");
					dto.setUserCert(filename);
					
					FileManager.doFiledelete(userCert);					
				}
			}
			
			dao.updateMember(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/mypage/mypageMain.do");

	}
	protected void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//패스워드 확인 폼
		MemberDAO dao=new MemberDAOImpl();
		String cp=req.getContextPath();
		
		HttpSession session=req.getSession();
	    SessionInfo info=(SessionInfo)session.getAttribute("member");
		
	    try {
			MemberDTO dto=dao.readMember(info.getUserId());
			if(dto==null || ! info.getUserId().equals(dto.getUserId())) {
				resp.sendRedirect(cp+"/mypage/mypageMain.do");
				return;
			}
			
			req.setAttribute("dto", dto);
			
			forward(req, resp, "/WEB-INF/views/mypage/pwd.jsp");
			return;
			
		} catch (Exception e) {
			req.setAttribute("message", "비밀번호가 틀렸습니다.");
		}
	    
	    //입력이 제대로 안됐으면 다시 마이페이지 창으로
	    resp.sendRedirect(cp+"/mypage/mypageMain.do");
		
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//회원 탈퇴
		String cp=req.getContextPath();
		MemberDAO dao=new MemberDAOImpl();
		
		HttpSession session=req.getSession();
	    SessionInfo info=(SessionInfo)session.getAttribute("member");
	    
		if(req.getMethod().equalsIgnoreCase("GET")) {
			//GET방식으로 접근한 경우
			resp.sendRedirect(cp+"/mypage/mypageMain.do");
			return;
		}
		
		try {
			String userPwd=req.getParameter("userPwd");
			
			dao.deleteMember(info.getUserId(), userPwd);
			session.invalidate();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp);
	}
	
	/**
	 *  강사(상명)
	 */
	protected void trclassList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//강사 마이페이지 - 수강생관리
		String cp=req.getContextPath();
		TrmyDAO dao=new TrmyDAO();
		
		HttpSession session=req.getSession();
	    SessionInfo info=(SessionInfo)session.getAttribute("member");
	    
	    try {
	    	//강사 마이페이지 - 수강생 관리에 전달할 파라미터 값
			List<TrmyDTO> list=dao.readClass(info.getUserId());	
			
			 long sEnabled, eEnabled;
			  
			  Date curDate=new Date();
			  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			  
			  for(TrmyDTO dto:list) {
				  try {
						Date edate=sdf.parse(dto.getClassEnd());
						Date sdate=sdf.parse(dto.getClassStart());
						
						eEnabled= (curDate.getTime() - edate.getTime()) /(1000*60*60*24);
						sEnabled= (curDate.getTime() - sdate.getTime()) /(1000*60*60*24);
						
						dto.setEclassEnabled(eEnabled);
						dto.setSclassEnabled(sEnabled);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
			  }
			
			req.setAttribute("list", list);
			
			forward(req, resp, "/WEB-INF/views/mypage/mypage_tr_classList.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    resp.sendRedirect(cp+"/mypage/mypageMain.do");
	}
	
	protected void trstdList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//강사 마이페이지 - 내 수강생 리스트
		String cp=req.getContextPath();
		TrmyDAO dao=new TrmyDAO();
		
		HttpSession session=req.getSession();
	    SessionInfo info=(SessionInfo)session.getAttribute("member");
	    
	    try {
	    	//강사 마이페이지 - 수강생 관리에 전달할 파라미터 값
			int classNum=Integer.parseInt(req.getParameter("classNum"));
			
			List<TrmyDTO> stdList=dao.stdList(info.getUserName(), classNum); //trName, classNum
			
			List<TrmyDTO> list=dao.listDTO(info.getUserId());
			
			  long sEnabled, eEnabled;
			  
			  Date curDate=new Date();
			  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			  
			  for(TrmyDTO dto:stdList) {
				  try {
						Date edate=sdf.parse(dto.getClassEnd());
						Date sdate=sdf.parse(dto.getClassStart());
						
						eEnabled= (curDate.getTime() - edate.getTime()) /(1000*60*60*24);
						sEnabled= (curDate.getTime() - sdate.getTime()) /(1000*60*60*24);
						
						dto.setEstdEnabled(eEnabled);
						dto.setSstdEnabled(sEnabled);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
			  }
				
			req.setAttribute("classNum", classNum);
			req.setAttribute("list", list);
			req.setAttribute("stdList", stdList);
			
			forward(req, resp, "/WEB-INF/views/mypage/mypage_tr_stdlist.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    resp.sendRedirect(cp+"/mypage/mypageMain.do");
	}
	
	protected void stdDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//수강생 추방
		String cp=req.getContextPath();
		TrmyDAO dao=new TrmyDAO();
		
		int classNum=Integer.parseInt(req.getParameter("classNum"));
		
		try {
			String userId=req.getParameter("userId");
			System.out.println(classNum);
			
			dao.deleteClassB(classNum,userId);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/mypage/stdlist.do?classNum="+classNum);
		
	}
	/**
	 *  수강생(선성)
	 */
	
	protected void stdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session=req.getSession();
	    SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		long gap;
		Date curDate = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		String userId = info.getUserId();
		int userPoint = info.getUserPoint();
		
		req.setAttribute("userPoint", userPoint);
		
		StdDAO dao = new StdDAOImpl();
		List<StdDTO> list = dao.listStd(userId);
		List<StdDTO> list2 = new ArrayList<StdDTO>();
		
		for (StdDTO dto : list) {
			
			try {
				Date date=sdf.parse(dto.getClassDate().substring(0,10));
								
				 gap = (curDate.getTime() - date.getTime()) /(1000*60*60*24); // 일자
				//gap = (curDate.getTime() - date.getTime()) /(1000*60*60); // 현재시간 - 수강 시작날자 
				dto.setStartgap(gap);
				
				date=sdf.parse(dto.getClassDate().substring(13));
				
				 gap = (curDate.getTime() - date.getTime()) /(1000*60*60*24); // 일자
				//gap = (curDate.getTime() - date.getTime()) /(1000*60*60); // 현재시간 - 수강 마지막날자 
				dto.setEndgap(gap);
				
				if(dto.getStartgap() < 0 &&  dto.getEndgap() < 0) {
					dto.setStdstate("수강전");
				}else if(dto.getStartgap() >= 0 && dto.getEndgap() <= 0) {
					dto.setStdstate("진행중");
				}else {
					dto.setStdstate("수강완료");
				}
				
				dto.setClassName(dto.getClassName());
				dto.setClassDate(dto.getClassDate());
				
				
				list2.add(dto);
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			req.setAttribute("list2", list2);
			
		}
		forward(req, resp, "/WEB-INF/views/mypage/mypage_main.jsp");
	}
	
	/**
	 *  관리자(지엉)
	 */
	protected void adminMemberList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<TrmyDTO> list=null;
		String cp=req.getContextPath();
		TrmyDAO dao=new TrmyDAO();
		
		HttpSession session=req.getSession();
	    SessionInfo info=(SessionInfo)session.getAttribute("member");
	    
	    
	    try {

	    	int userEnabled=Integer.parseInt(req.getParameter("userEnabled"));
			
	    	list=dao.memberList(userEnabled);
	    	
			req.setAttribute("list", list);
			req.setAttribute("userEnabled", userEnabled);
			
			forward(req, resp, "/WEB-INF/views/mypage/mypage_admin.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    resp.sendRedirect(cp+"/mypage/mypageMain.do");
	}
	
	protected void deleteMemberA(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TrmyDAO dao= new TrmyDAO();
		String cp=req.getContextPath();
		String userEnabled=req.getParameter("userEnabled");
		
		try {
			String userId=req.getParameter("userId");
			dao.deleteMemberA(userId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/mypage/mypageMain.do?userEnabled="+userEnabled);

	}
	
	protected void classAdmin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<TrmyDTO> list=null;
		String cp=req.getContextPath();
		TrmyDAO dao=new TrmyDAO();
		
		HttpSession session=req.getSession();
	    SessionInfo info=(SessionInfo)session.getAttribute("member");
	    
	    
	    try {
	    	list=dao.classAdmin();
	    	
			req.setAttribute("list", list);
			forward(req, resp, "/WEB-INF/views/mypage/mypage_classAdmin.jsp");
			
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    resp.sendRedirect(cp+"/mypage/mypageMain.do");
	}
	
	protected void deleteClassA(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TrmyDAO dao= new TrmyDAO();
		String cp=req.getContextPath();
		
		try {
			int classNum=Integer.parseInt(req.getParameter("classNum"));
			dao.deleteClassA(classNum);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/mypage/classAdmin.do");

	}
}
