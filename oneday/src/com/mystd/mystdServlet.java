package com.mystd;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.MemberDAO;
import com.member.MemberDAOImpl;
import com.member.MemberDTO;
import com.member.SessionInfo;
import com.review.ReviewDAO;
import com.review.ReviewDAOImpl;
import com.review.ReviewDTO;
import com.util.MyUploadServlet;

@MultipartConfig
@WebServlet("/mystd/*")
public class mystdServlet extends MyUploadServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();
		
		
		if(uri.indexOf("member.do")!=-1) {
			memberForm(req, resp);
		}else if(uri.indexOf("update.do")!=-1) {
			updateSubmit(req, resp);	
		}else if(uri.indexOf("mypagereview.do")!=-1) {
			myReview(req, resp);
		}
		
	}
	protected void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		MemberDAO dao = new MemberDAOImpl();
		MemberDTO dto = new MemberDTO();
		
		try {
			
			String userId = info.getUserId();
			
			dto =dao.readMember(userId);
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		req.setAttribute("dto", dto);
		
		
		forward(req, resp, "/WEB-INF/views/mystd/member.jsp");
	}
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String cp = req.getContextPath();
		
		MemberDAO dao = new MemberDAOImpl();
		MemberDTO dto = new MemberDTO();

		try {
			dto.setUserId(req.getParameter("userId"));
			dto.setUserPwd(req.getParameter("userPwd"));
			dto.setUserName(req.getParameter("userName"));
			dto.setUserTel(req.getParameter("userTel"));
			dto.setUserEmail(req.getParameter("userEmail"));
			dto.setUserZip(req.getParameter("userZip"));
			dto.setUserAddr1(req.getParameter("userAddr1"));
			dto.setUserAddr2(req.getParameter("userAddr2"));
	
			dao.updateMember(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp);
	}
	
	protected void myReview(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		ReviewDAO dao = new ReviewDAOImpl();
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		
		String userId = info.getUserId();
		
		try {
			
			list = dao.readlist(userId);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		req.setAttribute("list", list);
		
		forward(req, resp, "/WEB-INF/views/mystd/mypagereview.jsp");
	}
}
