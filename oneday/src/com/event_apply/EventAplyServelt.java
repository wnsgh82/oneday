package com.event_apply;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;

@WebServlet("/event_apply/*")
public class EventAplyServelt extends HttpServlet {
	private static final long serialVersionUID = 1L;


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
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { // 로그인이 되어 있지 않으면
			String cp=req.getContextPath();
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		String uri = req.getRequestURI();
		if(uri.indexOf("apply.do")!=-1) {
			apply(req, resp);
		}
	}
	
	protected void apply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EventAplyDAO dao = new EventAplyDAOImpl();
		EventAplyDTO dto = new EventAplyDTO();
		String cp = req.getContextPath();
		
		try {
			HttpSession session = req.getSession();
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			
			dto.setUserId(info.getUserId()); // 이벤트 참가하려는 사람(로그인한 사람)
			dto.setApplyEnabled(Integer.parseInt(req.getParameter("applyEvntEnabled"))); // 중복참여 방지
			
			dao.applyEvent(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp);
	}
}
