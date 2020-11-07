package com.std;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.oneday.OnedayDTO;
import com.util.MyUploadServlet;

@WebServlet("/std/*")
public class StdServlet extends MyUploadServlet{
	private static final long serialVersionUID = 1L;


	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri=req.getRequestURI();
		String cp=req.getContextPath();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		if(uri.indexOf("created.do")!=-1) {
			createFrom(req, resp);
		}else if(uri.indexOf("created_ok.do")!=-1) {
			createSubmit(req, resp);
		}
		
		
	}
	protected void createFrom(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		OnedayDTO dto = new OnedayDTO();
		
		try {
			dto.setUserName(req.getParameter("userName"));
			dto.setClassName(req.getParameter("className"));
			dto.setClassStart(req.getParameter("classStart"));
			dto.setClassEnd(req.getParameter("classEnd"));
			dto.setClassNum(Integer.parseInt(req.getParameter("classNum")));
			dto.setClassAddr(req.getParameter("classAddr"));
			dto.setClassPrice(req.getParameter("classPrice"));
			dto.setClassCount(Integer.parseInt(req.getParameter("classCount")));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		req.setAttribute("dto", dto);
		req.setAttribute("info", info);
		
		forward(req, resp, "/WEB-INF/views/std/created.jsp");
	}
	
	protected void createSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		StdDTO dto = new StdDTO();
		StdDAO dao = new StdDAOImpl();
		
		
		try {
			dto.setClassNum(Integer.parseInt(req.getParameter("classNum")));
			dto.setUserId(info.getUserId());
			dto.setClassName(req.getParameter("className"));
			dto.setTrName(req.getParameter("trName"));
			dto.setUserName(info.getUserName());
			dto.setUserEmail(info.getEmail());
			dto.setClassDate(req.getParameter("classStart")+" ~ " + req.getParameter("classEnd"));
			
			dao.insertStd(dto);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp);
	}
	
}
