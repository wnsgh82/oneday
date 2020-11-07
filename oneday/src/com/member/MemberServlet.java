package com.member;

import java.io.File;
import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.util.MyUploadServlet;

@MultipartConfig
@WebServlet("/member/*")
public class MemberServlet extends MyUploadServlet{
	private static final long serialVersionUID = 1L;
	private String pathname;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
	
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		HttpSession session=req.getSession();
		
	    String root=session.getServletContext().getRealPath("/");
	    pathname=root+"uploads"+File.separator+"photoimg";
		
		if(uri.indexOf("login.do")!=-1) {
			loginForm(req, resp);
		} else if(uri.indexOf("login_ok.do")!=-1) {
			loginSubmit(req, resp);
		} else if(uri.indexOf("logout.do")!=-1) {
			logout(req, resp);
		} else if(uri.indexOf("member.do")!=-1) {
			memberForm(req, resp);
		} else if(uri.indexOf("member_ok.do")!=-1) {
			memberSubmit(req, resp);
		} else if(uri.indexOf("pwd.do")!=-1) {
			pwdForm(req, resp);
		} else if(uri.indexOf("pwd_ok.do")!=-1) {
			pwdSubmit(req, resp);
		} else if(uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("userIdCheck.do")!=-1) {
			userIdCheck(req, resp);
		}  else if(uri.indexOf("selectlog.do")!=-1) {
			selectlogin(req, resp);
		}

	}
	
	protected void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//로그인 폼
		
		
		
		String path="/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
	}
	
	protected void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//로그인 처리
		MemberDAO dao=new MemberDAOImpl();
		String cp=req.getContextPath();
		
		try {
			String userId=req.getParameter("userId");
			String userPwd=req.getParameter("userPwd");
			MemberDTO dto= dao.readMember(userId);

			if(dto!=null) {
				if(dto.getUserPwd().equals(userPwd)) {  // dto.getUserEnabled()==1 || 100 || 200 ? 해야되나 ? 굳이?
					//로그인 성공
					HttpSession session=req.getSession(); //세션 객체
					
					//세션 유지시간을 25분으로 설정(톰켓은 기본 30분)
					session.setMaxInactiveInterval(25*60);
					
					//세션에 저장할 정보
					SessionInfo info=new SessionInfo();
					info.setUserId(dto.getUserId());
					info.setUserName(dto.getUserName());
					
					//세션에 정보 저장
					session.setAttribute("member", info);
					
					//메인 화면으로
					resp.sendRedirect(cp);
					return;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//로그인을 실패한 경우
		req.setAttribute("message", "아이디 또는 패스워드가 일치하지 않습니다.");
		forward(req, resp, "/WEB-INF/views/member/login.jsp");
	}
	
	protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//로그아웃
		HttpSession session=req.getSession();
		
		//세션에 저장된 모든 정보를 지우고 세션을 초기화
		session.invalidate();
		
		//특정한 정보만 삭제할 경우!!
		//session.removeAttribute("member");
		
		String cp=req.getContextPath();
		resp.sendRedirect(cp);
		
	}
	
	protected void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//회원가입 폼
		String userEnabled = req.getParameter("userEnabled");
		
		if(userEnabled == null) {
			userEnabled= "1";
		}
		int enable = Integer.parseInt(userEnabled);
		
		req.setAttribute("userEnabled", enable);
		req.setAttribute("mode", "member");
		req.setAttribute("title", "회원 가입");
		String path="/WEB-INF/views/member/member.jsp";
		forward(req, resp, path);
	}
	
	protected void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//회원가입 처리
		MemberDAO dao = new MemberDAOImpl();
		String cp=req.getContextPath();
		
		try {
	
			MemberDTO dto = new MemberDTO();
			
			dto.setUserId(req.getParameter("userId"));
			dto.setUserName(req.getParameter("userName"));
			dto.setUserPwd(req.getParameter("userPwd"));
			dto.setUserEmail(req.getParameter("userEmail"));
			dto.setUserTel(req.getParameter("userTel"));
			dto.setUserZip(req.getParameter("userZip"));
			dto.setUserAddr1(req.getParameter("userAddr1"));
			dto.setUserAddr2(req.getParameter("userAddr2"));
			
			int enable = Integer.parseInt(req.getParameter("userEnabled"));
				
			if(enable ==100) {
				String filename=null; 
				Part p=req.getPart("userCert"); // 일반회원은 null 
				Map<String, String> map= doFileUpload(p, pathname); 
				if(map!=null) {
					filename=map.get("saveFilename"); 
					dto.setUserCert(filename); 
					enable =100;
					dto.setUserEnabled(enable); 
				}			
			}

			dao.insertMember(dto,enable);
			
			resp.sendRedirect(cp);
			return;
			
		} catch (SQLIntegrityConstraintViolationException e) {
			req.setAttribute("message", "아이디 중복 등의 무결성 제약 조건 위반입니다.");
		} catch (SQLDataException e) {
			req.setAttribute("message", "날짜 형식 등이 잘못 입력되었습니다.");
		} catch (Exception e) {
			req.setAttribute("message", "데이터 추가가 실패했습니다.");
		}
		
		//입력이 제대로 안됐으면 다시 회원가입 창으로 
		req.setAttribute("mode", "member");
		req.setAttribute("title", "회원 가입");
		String path="/WEB-INF/views/member/list.do";
		forward(req, resp, path);
	}
	
	protected void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//수정, 탈퇴 등에서 패스워드 폼 
		
	}
	
	protected void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//패스워드 검사
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//회원 정보 수정 폼
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//회원 정보 수정 처리
	}
	
	protected void userIdCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//회원 아이디 중복 검사
	}
	
	protected void selectlogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//회원가입 선택 
	
		String path="/WEB-INF/views/member/selectlogin.jsp";
		forward(req, resp, path);
		
	}
	

	
}
