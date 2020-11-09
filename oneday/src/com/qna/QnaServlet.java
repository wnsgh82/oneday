package com.qna;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.List;
import java.util.concurrent.locks.Condition;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/qna/*")
public class QnaServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri=req.getRequestURI();

		if(uri.indexOf("list.do")!=-1) {
			list(req, resp);
		} else if(uri.indexOf("created.do")!=-1) {
			createdForm(req, resp);
		} else if(uri.indexOf("created_ok.do")!=-1) {
			createdSubmit(req, resp);
		} else if(uri.indexOf("article")!=-1) {
			article(req, resp);
		} else if(uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("reply.do")!=-1) {
			replyForm(req, resp);
		} else if(uri.indexOf("reply_ok.do")!=-1) {
			replySubmit(req, resp);
		} else if(uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		}
		
	}
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao=new QnaDAOImpl();
		MyUtil util=new MyUtil();
		String cp=req.getContextPath();
		
		String page=req.getParameter("page");
		int current_page=1;
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
		
		int dataCount;
		if(keyword.length()==0) {
			dataCount=dao.dataCount();
		} else {
			dataCount=dao.dataCount(condition, keyword);
		}
		
		int rows=10;
		int total_page=util.pageCount(rows, dataCount);
		if(current_page>total_page) {
			current_page=total_page;
		}
		
		int offset=(current_page-1)*rows;
		if(offset<0) {
			offset=0;
		}
		
		List<QnaDTO> list;
		if(keyword.length()==0) {
			list=dao.listQna(offset, rows);
		} else {
			list=dao.listQna(offset, rows, condition, keyword);
		}
		
		int listNum, n=0;
		for(QnaDTO dto : list){
			listNum=dataCount-(offset+n);
			dto.setListNum(listNum);
			n++;
		}
		
		String query="";
		if(keyword.length()!=0) {
			query="condition="+condition+"&keyword="
		         +URLEncoder.encode(keyword,"utf-8");
		}
		
		String listUrl=cp+"/qna/list.do";
		String articleUrl=cp+"/qna/article.do?page="+current_page;
		if(query.length()!=0) {
			listUrl+="?"+query;
			articleUrl+="&"+query;
		}
		String paging=util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("page", current_page);
		req.setAttribute("paging", paging);
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		
		forward(req, resp, "/WEB-INF/views/qna/list.jsp");
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/qna/created.jsp");
	}
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		String cp=req.getContextPath();
		
		QnaDAO dao= new QnaDAOImpl();
		try {
			QnaDTO dto=new QnaDTO();
			dto.setUserId(info.getUserId());
			dto.setbSubject(req.getParameter("bSubject"));
			dto.setbContent(req.getParameter("bContent"));
			dao.insertQna(dto, "created");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/qna/list.do");
	}
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		QnaDAO dao=new QnaDAOImpl();
		
		try {
			int bNum=Integer.parseInt(req.getParameter("bNum"));
			String page=req.getParameter("page");
			
			String condition=req.getParameter("condition");
			String keyword=req.getParameter("keyword");
			if(condition==null) {
				condition="all";
				keyword="";
			}
			keyword=URLDecoder.decode(keyword, "utf-8");
			
			String query="page="+page;
			if(keyword.length()!=0) {
				query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
			}
			dao.updateHitCount(bNum); //Á¶È¸¼ö
			
			QnaDTO dto=dao.readQna(bNum);
			if(dto==null) {
				resp.sendRedirect(cp+"/qna/list.do"+query);
				return;
			}
			dto.setbContent(dto.getbContent().replaceAll("\n", "<br>"));
			
			req.setAttribute("dto", dto);
			req.setAttribute("query", query);
			req.setAttribute("page", page);
			
			forward(req, resp, "/WEB-INF/views/qna/article.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/qna/list.do");
		
	}
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao=new QnaDAOImpl();
		String cp=req.getContextPath();
		String page=req.getParameter("page");
		String query="page="+page;
		
		try {
			String condition = req.getParameter("condition");
			String keyword=req.getParameter("keyword");
			
			if(condition==null) {
				condition="all";
				keyword="";
			}
			keyword=URLDecoder.decode(keyword, "utf-8");
			if(keyword.length()!=0) {
				query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
			}
			
			int bNum=Integer.parseInt(req.getParameter("bNum"));
			QnaDTO dto=dao.readQna(bNum);
			if(dto==null) {
				resp.sendRedirect(cp+"/qna/list.do?"+query);
				return;
			}
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			
			forward(req, resp, "/WEB-INF/views/qna/created.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/qna/list.do?"+query);
	}
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao=new QnaDAOImpl();
		
		String cp=req.getContextPath();
		String page=req.getParameter("page");
		String query="page="+page;
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/qna/list.do");
			return;
		}
		
		try {
			String condition=req.getParameter("condition");
			String keyword=req.getParameter("keyword");
			if(condition==null) {
				condition="all";
				keyword="";
			}
			keyword=URLDecoder.decode(keyword, "utf-8");
			if(keyword.length()!=0) {
				query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
			}
			
			QnaDTO dto=new QnaDTO();
			
			dto.setbNum(Integer.parseInt(req.getParameter("bNum")));
			dto.setbSubject(req.getParameter("bSubject"));
			dto.setbContent(req.getParameter("bContent"));
			
			dao.updateQna(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/qna/list.do?"+query);
		
	}
	protected void replyForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/qna/created.jsp");
	}
	protected void replySubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao=new QnaDAOImpl();
		
		String cp=req.getContextPath();
		String page=req.getParameter("page");
		String query="page="+page;
		
		try {
			String condition=req.getParameter("condition");
			String keyword=req.getParameter("keyword");
			if(condition==null) {
				condition="all";
				keyword="";
			}
			keyword=URLDecoder.decode(keyword, "utf-8");
			if(keyword.length()!=0) {
				query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
			}
			
			int bNum=Integer.parseInt(req.getParameter("bNum"));
			
			QnaDTO dto=dao.readQna(bNum);
			
			if(dto==null) {
				resp.sendRedirect(cp+"/qna/list.do?"+query);
				return;
				
			}
			
			dao.deleteQna(bNum);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/qna/list.do?"+query);
		
	}
}
