package com.event;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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

import com.member.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/event/*")
public class EventServlet extends MyUploadServlet {
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


	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri=req.getRequestURI();
		// String cp=req.getContextPath();
		
		
		HttpSession session=req.getSession();
		/*
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		}
		*/
		String root = session.getServletContext().getRealPath("/");
		pathname=root+"uploads"+File.separator+"photo";
		
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
		} else if(uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		} else if(uri.indexOf("apply.do")!=-1) {
			apply(req, resp);
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
		
		List<EventDTO> list = dao.listEvent(offset, rows);
		
		// 이벤트 진행중 or 종료 관련
		long eEnabled;
		Date curDate = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		for(EventDTO dto:list) {
			try {
				Date date=sdf.parse(dto.geteEnd());
				System.out.println(date);
				System.out.println(curDate.getTime());
				eEnabled = (curDate.getTime() - date.getTime()) /(1000*60*60*24); // 일자
				// eEnabled = (curDate.getTime() - date.getTime()) /(1000*60*60); // 시간 
				
				dto.seteEnabled(eEnabled);
				System.out.println(eEnabled);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	
		
		String listUrl=cp+"/event/list.do";
		String articleUrl=cp+"/event/article.do?page="+current_page;
		String paging=util.paging(current_page, total_page, listUrl);
		
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
//		HttpSession session=req.getSession();
//		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		EventDAO dao = new EventDAOImpl();
		
		try {
			EventDTO dto = new EventDTO();
		
			dto.seteContent(req.getParameter("eContent"));
			dto.seteSubject(req.getParameter("eSubject"));
			dto.seteStart(req.getParameter("eStart"));
			dto.seteEnd(req.getParameter("eEnd"));
			
			String filename=null;
			Part p = req.getPart("selectFile");
			Map<String, String> map=doFileUpload(p, pathname);
			if(map!=null) {
				filename=map.get("saveFilename");
				dto.seteIFN(filename);
				
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

			dao.updateHitCount(eNum);
			
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
			if(dto==null || ! info.getUserId().equals("admin")) {
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
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/event/list.do");
			return;
		}
		
		String page = req.getParameter("page");
		
		try {
			String eIFN = req.getParameter("eIFN");
			
			dto.seteNum(Integer.parseInt(req.getParameter("eNum")));
			dto.seteSubject(req.getParameter("eSubject"));
			dto.seteContent(req.getParameter("eContent"));
			dto.seteStart(req.getParameter("eStart"));
			dto.seteEnd(req.getParameter("eEnd"));
			
			Part p = req.getPart("selectFile");
			Map<String, String> map = doFileUpload(p, pathname);
			if(map!=null) {
				String filename = map.get("saveFilename");
				dto.seteIFN(filename);
				
				FileManager.doFiledelete(pathname, eIFN);
			} else {
				dto.seteIFN(eIFN);
			}
			
			dao.updateEvent(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/event/list.do?page="+page);
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		EventDAO dao = new EventDAOImpl();
		String page = req.getParameter("page");
		
		try {
			int eNum = Integer.parseInt(req.getParameter("eNum"));
			EventDTO dto = dao.readEvent(eNum);
			if(dto==null) {
				resp.sendRedirect(cp+"/event/list.do?page="+page);
				return;
				
			}
			
			// 관리자가 아니면
			if(! info.getUserId().equals("admin")) {
				resp.sendRedirect(cp+"/event/list.do?page="+page);
				return;
			}
			
			// 파일 삭제
			FileManager.doFiledelete(pathname, dto.geteIFN());
			
			dao.deleteEvent(eNum);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/event/list.do?page="+page);
	}
	
	protected void apply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		EventDAO dao = new EventDAOImpl();
	
		try {
			if(info==null) { // 로그인이 되어 있지 않으면
				resp.sendRedirect(cp+"/member/login.do");
				return;
			}
			int eNum = Integer.parseInt(req.getParameter("eNum"));
		
			dao.applyEvent(eNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

