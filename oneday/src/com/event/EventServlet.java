package com.event;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.member.SessionInfo;
import com.oneday.OnedayDAO;
import com.oneday.OnedayDTO;
import com.oneday.OnedayImpl;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/event/*")
public class EventServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	private String pathname;

	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri=req.getRequestURI();
		String cp=req.getContextPath();
		
		
		HttpSession session=req.getSession();
		/*
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		*/
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "photo";
		
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
		EventDAO dao = new EventDAOImpl();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		int current_page = 1;
		if(page!=null) {
			current_page = Integer.parseInt(page);
		}
		
		int dataCount = dao.dataCount();
		
		int rows = 3;
		int total_page = util.pageCount(rows, dataCount);
		if(current_page > total_page) {
			current_page = total_page;
		}
		
		int offset = (current_page - 1) * rows;
		if(offset < 0) offset = 0;
		
		List<EventDTO> list = dao.listEvevnt(offset, rows);
		
		String listUrl = cp + "/event/list.do";
		String articleUrl = cp + "/evnet/article.do?page=" + current_page;
		String paging = util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("page", current_page);
		req.setAttribute("paging", paging);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("articleUrl", articleUrl);
		
		forward(req, resp, "/WEB-INF/views/event/list.jsp");
	}

	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/event/created.jsp");
	}

	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		EventDAO dao = new EventDAOImpl();
		
		try {
			EventDTO dto = new EventDTO();
		
			dto.seteName(req.getParameter("eName"));
			dto.seteContent(req.getParameter("eContent"));
			// dto.setStart(req.getParameter("eStart"));
			// dto.setEnd(req.getParameter("eEnd"));
			dto.seteName(info.getUserName());
			
			String filename=null;
			Part p = req.getPart("selectFile");
			Map<String, String> map=doFileUpload(p, pathname);
			if(map!=null) {
				filename=map.get("saveFilename");
				dto.seteFIN(filename);
				
				dao.insertEvent(dto);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/event/list.do");
	}

	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		EventDAO dao = new EventDAOImpl();
		String page = req.getParameter("page");
		
		try {
			int eNum = Integer.parseInt(req.getParameter("eNum"));
			EventDTO dto = dao.readEvent(eNum);
			
			if(dto==null) {
				resp.sendRedirect(cp+"/event/list.do?page="+page);
				return;			
			}
			
			dto.seteContent(dto.geteContent().replaceAll("\n", "<br>"));
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			
			forward(req, resp, "/WEB-INF/views/event/article.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/event/list.do?page="+page);
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		EventDAO dao = new EventDAOImpl();
		String page = req.getParameter("page");
		
		try {
			int eNum = Integer.parseInt(req.getParameter("eNum"));
			EventDTO dto = dao.readEvent(eNum);
			if(dto==null || ! info.getUserId().equals(dto.getUserId())) {
				resp.sendRedirect(cp+"/event/list.do?page="+page);
				return;
			}
			
			req.setAttribute("page", page);
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/event/created.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/event/created.jsp");
		
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		EventDAO dao = new EventDAOImpl();
		EventDTO dto = new EventDTO();
		
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	
	
	
	
	
}
