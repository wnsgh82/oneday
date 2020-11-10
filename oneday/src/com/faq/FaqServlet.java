package com.faq;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyUploadServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/faq/*")
public class FaqServlet extends MyUploadServlet {
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
		} else if(uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		}
		
	}
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FaqDAO dao=new FaqDAOImpl();
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
		
		int rows=5;
		int total_page=util.pageCount(rows, dataCount);
		if(current_page>total_page) {
			current_page=total_page;
		}
		
		int offset=(current_page-1)*rows;
		if(offset<0) {
			offset=0;
		}
		
		List<FaqDTO> list;
		if(keyword.length()==0) {
			list=dao.listFaq(offset, rows);
		} else {
			list=dao.listFaq(offset, rows, condition, keyword);
		}
		
		String query="";
		if(keyword.length()!=0) {
			query="condition="+condition+"&keyword="
		         +URLEncoder.encode(keyword,"utf-8");
		}
		
		String listUrl=cp+"/faq/list.do";
		if(query.length()!=0) {
			listUrl+="?"+query;
		}
		String paging=util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("page", current_page);
		req.setAttribute("paging", paging);
		req.setAttribute("condition", condition);
		req.setAttribute("keyword", keyword);
		
		forward(req, resp, "/WEB-INF/views/faq/list.jsp");
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/faq/created.jsp");
	}
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		
		FaqDAO dao= new FaqDAOImpl();
		try {
			FaqDTO dto=new FaqDTO();
			dto.setbQ(req.getParameter("bQ"));
			dto.setbA(req.getParameter("bA"));
			dto.setbGroup(req.getParameter("bGroup"));
			
			System.out.println(dto.getbGroup());

			dao.insertFaq(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/faq/list.do");
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FaqDAO dao=new FaqDAOImpl();
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
			FaqDTO dto=dao.readFaq(bNum);
			if(dto==null) {
				resp.sendRedirect(cp+"/faq/list.do?"+query);
				return;
			}
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			
			forward(req, resp, "/WEB-INF/views/faq/created.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/faq/list.do?"+query);
	}
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FaqDAO dao=new FaqDAOImpl();
		
		String cp=req.getContextPath();
		String page=req.getParameter("page");
		String query="page="+page;
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/faq/list.do");
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
			
			FaqDTO dto=new FaqDTO();
			
			dto.setbNum(Integer.parseInt(req.getParameter("bNum")));
			dto.setbQ(req.getParameter("bQ"));
			dto.setbA(req.getParameter("bA"));
			dto.setbGroup(req.getParameter("bGroup"));
			
			dao.updateFaq(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/faq/list.do?"+query);
		
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FaqDAO dao=new FaqDAOImpl();
		
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
			
			FaqDTO dto=dao.readFaq(bNum);
			
			if(dto==null) {
				resp.sendRedirect(cp+"/faq/list.do?"+query);
				return;
			}
			
			dao.deleteFaq(bNum);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/faq/list.do?"+query);
		
	}
}
