package com.notice;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
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
import com.util.MyUploadServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/notice/*")
public class NoticeServlet extends MyUploadServlet{
	private static final long serialVersionUID = 1L;

	private String pathname;
	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String cp=req.getContextPath();
		String uri=req.getRequestURI();

		HttpSession session = req.getSession();
		
		String root=session.getServletContext().getRealPath("/");
		pathname = root+"uploads"+File.separator+"notice";
		
		
		if(uri.indexOf("list.do")!=-1) {
			list(req, resp);
		} else if(uri.indexOf("created.do")!=-1) {
			createdForm(req, resp);
		} else if(uri.indexOf("created_ok.do")!=-1) {
			createdSubmit(req, resp);
		} else if(uri.indexOf("article.do")!=-1) {
			article(req, resp);
		} else if(uri.indexOf("updated.do")!=-1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		} else if(uri.indexOf("deleteCheck.do")!=-1) {
			deleteCheck(req, resp);
		} else if(uri.indexOf("download.do")!=-1) {
			download(req, resp);
			
		}
		
	}
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//리스트
		NoticeDAO dao= new NoticeDAOImpl();
		MyUtil util=new MyUtil();
		
		String cp=req.getContextPath();
		
		String page=req.getParameter("page");
		
		
		int current_page = 1;
		if(page!=null) {
			current_page=Integer.parseInt(page);
		}
		
		String condition=req.getParameter("condition");
		String keyword=req.getParameter("keyword");
		if(condition==null) {
			condition="all";
			keyword="";
		}
		if(req.getMethod().equalsIgnoreCase("GET")) {
			keyword=URLDecoder.decode(keyword, "utf-8");
		}
		
        String numPerPage=req.getParameter("rows");
        int rows = numPerPage == null ? 10 : Integer.parseInt(numPerPage);
        
		int dataCount, total_page;
		
		if(keyword.length()!=0) {
			dataCount=dao.dataCount(condition, keyword);
		} else {
			dataCount=dao.dataCount();
		}
		
		total_page=util.pageCount(rows, dataCount);
		
		if(current_page>total_page) {
			current_page=total_page;
		}
		
		int offset=(current_page-1)*rows; 
		
		List<NoticeDTO> list;
		if(keyword.length()!=0) {
			list= dao.listNotice(offset, rows, condition, keyword);
		} else {
			list= dao.listNotice(offset, rows);
		}
		
		//공지글
		List<NoticeDTO> listNotice=null;
		listNotice=dao.listNotice();
		for(NoticeDTO dto : listNotice) {
			dto.setNoCreated(dto.getNoCreated().substring(0,10));
		}
		
		//글번호 만들기 -> 중간에 지워도 순서대로 되는거
		int listNum=0;
		int n=0;
		
		for(NoticeDTO dto : list ) {
			listNum=dataCount-(offset+n);
			dto.setListNum(listNum); //list에 출력된 num
			
			dto.setNoCreated(dto.getNoCreated().substring(0,10));
			//리스트에는 년월일만 출력할거니까
			n++;
		}

		
		String query="";
		String listUrl;
		String articleUrl;
		
		listUrl=cp+"/notice/list.do?rows="+rows;
		articleUrl=cp+"/notice/article.do?page="+current_page+"&rows="+rows;
		if(keyword.length()!=0) {
			query="&condition="+condition+"&keyword="+keyword;
			
			listUrl+=query;
			articleUrl+=query;
		}
		
		String paging=util.paging(current_page, total_page, listUrl);
		
		//넘길거
		req.setAttribute("page", current_page);
		req.setAttribute("rows", rows);
		req.setAttribute("total_page", total_page);
		req.setAttribute("list", list);
		req.setAttribute("listNotice", listNotice);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("paging", paging);
		req.setAttribute("condition",condition);
		req.setAttribute("keyword", keyword);
		req.setAttribute("dataCount", dataCount);
		
		forward(req, resp, "/WEB-INF/views/notice/list.jsp");
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//글쓰기폼
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		String cp=req.getContextPath();
		String rows=req.getParameter("rows"); //rows 받아옴
		
		// admin만 글을 등록
		if(! info.getUserId().equals("admin")) { //admin아니면 리스트로
			resp.sendRedirect(cp+"/notice/list.do?rows="+rows);
			return;
		}
		req.setAttribute("mode", "created");
		req.setAttribute("rows", rows);
		forward(req, resp, "/WEB-INF/views/notice/created.jsp");
		
	}
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//글저장하기
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		NoticeDAO dao = new NoticeDAOImpl();
		String cp=req.getContextPath();
		String rows=req.getParameter("rows");
		
		try {
			NoticeDTO dto = new NoticeDTO();
			
			if(req.getParameter("notice")!=null) { //공지면
				dto.setNotice(Integer.parseInt(req.getParameter("notice")));
			} 
			
			dto.setNoName(info.getUserName());
			dto.setNoSubject(req.getParameter("noSubject"));
			dto.setNoContent(req.getParameter("noContent"));
			
			Part p=req.getPart("upload");
			Map<String, String> map = doFileUpload(p, pathname);
			if(map!=null) {
				String noSaveFileName = map.get("saveFilename");
				String noOrginalFileName = map.get("originalFilename");
				
				dto.setNoSaveFileName(noSaveFileName);
				dto.setNoOrginalFileName(noOrginalFileName);
			}
			
			dao.insertNotice(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/notice/list.do?rows="+rows);
		
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//글보기
		NoticeDTO dto;
		NoticeDAO dao= new NoticeDAOImpl();
		String cp=req.getContextPath();
		
		String page=req.getParameter("page");
		String rows=req.getParameter("rows");
		String query="page="+page+"&rows="+rows;
		
		try {
			int noNum=Integer.parseInt(req.getParameter("noNum"));
			
			String condition=req.getParameter("condition");
			String keyword=req.getParameter("keyword");
			if(condition==null) {
				condition="all";
				keyword="";
			}
			keyword=URLDecoder.decode(keyword, "utf-8");
			
			if(keyword.length()!=0) {
				query+="&condition="+condition+"&keyword="+keyword;
			}
			
			dao.updateHitCount(noNum);
			
			dto=dao.readNotice(noNum);
			if(dto==null) {
				resp.sendRedirect(cp+"/notice/list.do?"+query);
				return;
			}
			
			//엔터 바꾸는거
			dto.setNoContent(dto.getNoContent().replaceAll("\n", "<br>"));
			
			req.setAttribute("dto", dto);
			req.setAttribute("query", query);
			req.setAttribute("page", page);
			req.setAttribute("rows", rows);
			
			forward(req, resp, "/WEB-INF/views/notice/article.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
		resp.sendRedirect(cp+"/notice/list.do?"+query);
	}
	
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	protected void deleteCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	protected void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}
