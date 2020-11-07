package com.review;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.std.StdDAO;
import com.std.StdDAOImpl;
import com.std.StdDTO;
import com.util.MyUploadServlet;

@MultipartConfig
@WebServlet("/review/*")
public class ReviewServlet extends MyUploadServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri=req.getRequestURI();
		String cp=req.getContextPath();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		
		if(uri.indexOf("list.do")!=-1) {
			list(req, resp);
		}else if(uri.indexOf("created.do")!=-1) {
			createForm(req, resp);
		}else if(uri.indexOf("created_ok.do")!=-1) {
			createSubmit(req, resp);
		}else if(uri.indexOf("mycrt.do")!=-1) {
			MycreateForm(req, resp);
		}
			
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		forward(req, resp, "/WEB-INF/views/review/list.jsp");
	}
	
	protected void createForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		String cp=req.getContextPath();
		
		int classNum= Integer.parseInt(req.getParameter("classNum"));
		String userId = req.getParameter("userId");
	
		StdDAO dao = new StdDAOImpl();
		
		StdDTO sdto = dao.readStd(userId,classNum);
		
		// std 에 아이디가 없으면 
		if(! info.getUserId().equals(userId)) {
			resp.sendRedirect(cp+"/review/list.do");
			return;
		}
		
		req.setAttribute("sdto", sdto);
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/review/created.jsp");
		
	}
	protected void createSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		ReviewDAO dao = new ReviewDAOImpl();
		String cp=req.getContextPath();
		String rows=req.getParameter("rows");
		
		
		
		try {
			ReviewDTO dto = new ReviewDTO();
			System.out.println(req.getParameter("classNum1"));
			dto.setClassNum(Integer.parseInt(req.getParameter("classNum1")));
			dto.setRvContent(req.getParameter("rvContent"));
			dto.setRvClassName(req.getParameter("className"));
			dto.setRvScore(req.getParameter("rvscore"));
			dto.setUserId(req.getParameter("userId"));
			
			dao.insertReview(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/notice/list.do?rows="+rows);
	}
	protected void MycreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		
		StdDAO dao = new StdDAOImpl();
		String userId = info.getUserId();
		
		List<StdDTO> list= dao.listStd(userId);
			
			
		req.setAttribute("list", list);
		req.setAttribute("info", info);
		req.setAttribute("mode", "created");
		
		
		forward(req, resp, "/WEB-INF/views/review/mycreated.jsp");
	}
}
