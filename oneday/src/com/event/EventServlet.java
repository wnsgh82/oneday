package com.event;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;

@WebServlet("/event/*")
public class EventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri=req.getRequestURI();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		String cp=req.getContextPath();
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		
		if(uri.indexOf("list.do")!=-1) {
			list(req, resp);
		} else if(uri.indexOf("created.do")!=-1) {
			createdForm(req, resp);
		} else if(uri.indexOf("created_ok.do")!=-1) {
			createdSubmit(req, resp);
		} else if(uri.indexOf("article.do")!=-1) {
			article(req, resp);
		} else if(uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("reply_ok.do")!=-1) {
			delete(req, resp);
		} 
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	
	
	
	
	
}
