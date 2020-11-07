package com.oneday;

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
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@MultipartConfig
@WebServlet("/oneday/*")
public class OnedayServlet extends MyUploadServlet{
	private static final long serialVersionUID = 1L;
	private String pathname;
	
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri=req.getRequestURI();
		String cp=req.getContextPath();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		/*
		if(info==null) {
			resp.sendRedirect(cp+"/member/login.do");
			return;
		} //���� �α��� �� �� ���¿��� ������ Ŭ���� Ŭ�� -> �α��� -> ����ȭ������ ���ư�  => ����!!!!
		*/
		
		//�̹��� ������ ���
		String root=session.getServletContext().getRealPath("/");
		pathname=root+"uploads"+File.separator+"photo";
		
		if(uri.indexOf("list.do")!=-1) {
			list(req, resp);
		}else if(uri.indexOf("created.do")!=-1) {
			createdForm(req, resp);
		}else if(uri.indexOf("created_ok.do")!=-1) {
			createdSubmit(req, resp);
		}else if(uri.indexOf("article.do")!=-1) {
			article(req, resp);
		}else if(uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		}else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		}else if(uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		}
		
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OnedayDAO dao=new OnedayImpl();
		MyUtil util=new MyUtil();
		String cp=req.getContextPath();
		
		String page=req.getParameter("page");
		int current_page=1;
		if(page!=null) {
			current_page=Integer.parseInt(page);
		}
		
		int dataCount=dao.dataCount();
		
		int rows=6;
		int total_page=util.pageCount(rows, dataCount);
		if(current_page>total_page) {
			current_page=total_page;
		}
		
		int offset=(current_page-1)*rows;
		if(offset<0) offset=0;
		
		List<OnedayDTO> list=dao.listOneday(offset, rows);
		
		String listUrl=cp+"/oneday/list.do";
		String articleUrl=cp+"/oneday/article.do?page="+current_page;
		String paging=util.paging(current_page, total_page, listUrl);
		
		req.setAttribute("list", list);
		req.setAttribute("page", current_page);
		req.setAttribute("paging", paging);
		req.setAttribute("dataCount", dataCount);
		req.setAttribute("total_page", total_page);
		req.setAttribute("articleUrl", articleUrl);
		
		forward(req, resp, "/WEB-INF/views/oneday/list.jsp");
	}
	
	protected void createdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("mode", "created");
		forward(req, resp, "/WEB-INF/views/oneday/created.jsp");
	}
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		OnedayDAO dao=new OnedayImpl();
		
		try {
			OnedayDTO dto=new OnedayDTO();
			
			
			dto.setClassName(req.getParameter("className"));
			dto.setClassAddr(req.getParameter("classAddr"));
			dto.setClassCount(Integer.parseInt(req.getParameter("classCount")));
			
			int price=Integer.parseInt(req.getParameter("classPrice"));
			String classPrice=String.format("%,d", price);
			classPrice+="��";
			dto.setClassPrice(classPrice);
			
			dto.setClassContent(req.getParameter("classContent"));
			dto.setClassStart(req.getParameter("classStart"));
			dto.setClassEnd(req.getParameter("classEnd"));
			dto.setUserId(info.getUserId());
			dto.setUserName(info.getUserName());
			
			String filename=null;
			Part p = req.getPart("selectFile");
			Map<String, String> map=doFileUpload(p, pathname);
			if(map!=null) {
				filename=map.get("saveFilename");
				dto.setClassIFN(filename);
				
				dao.insertOneday(dto);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/oneday/list.do");
	}
	
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		OnedayDAO dao=new OnedayImpl();
		String page=req.getParameter("page");
		
		try {
			int classNum=Integer.parseInt(req.getParameter("classNum"));
			OnedayDTO dto=dao.readOneday(classNum);
			if(dto==null) {
				resp.sendRedirect(cp+"/oneday/list.do?page="+page);
				return;
			}
			
			dto.setClassContent(dto.getClassContent().replaceAll("\n", "<br>"));
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			
			forward(req, resp, "/WEB-INF/views/oneday/article.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/oneday/list.do?page="+page);
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		OnedayDAO dao=new OnedayImpl();
		String page=req.getParameter("page");
		
		try {
			int classNum=Integer.parseInt(req.getParameter("classNum"));
			OnedayDTO dto=dao.readOneday(classNum);
			if(dto==null || ! info.getUserId().equals(dto.getUserId())) {
				resp.sendRedirect(cp+"/oneday/list.do?page="+page);
				return;
			}
			
			req.setAttribute("page", page);
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/oneday/created.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/oneday/list.do?page="+page);
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		OnedayDAO dao=new OnedayImpl();
		OnedayDTO dto=new OnedayDTO();
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			//GET������� ������ ���
			resp.sendRedirect(cp+"/oneday/list.do");
			return;
		}
		
		String page=req.getParameter("page");
		
		try {
			String classIFN=req.getParameter("classIFN");
			
			dto.setClassNum(Integer.parseInt(req.getParameter("classNum")));
			dto.setClassName(req.getParameter("className"));
			dto.setClassAddr(req.getParameter("classAddr"));
			dto.setClassCount(Integer.parseInt(req.getParameter("classCount")));
			dto.setClassPrice(req.getParameter("classPrice"));
			dto.setClassContent(req.getParameter("classContent"));
			dto.setClassStart(req.getParameter("classStart"));
			dto.setClassEnd(req.getParameter("classEnd"));
			
			Part p=req.getPart("selectFile");
			Map<String, String> map=doFileUpload(p, pathname);
			if(map!=null) {
				//���ο� �̹����� ����� ���
				String filename=map.get("saveFilename");  
				dto.setClassIFN(filename);
				
				//���� ���� �����
				FileManager.doFiledelete(classIFN);
				
			}
			
			dao.updateOneday(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/oneday/list.do?page="+page);
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp=req.getContextPath();
		OnedayDAO dao=new OnedayImpl();
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		
		String page=req.getParameter("page");
		try {
			int classNum=Integer.parseInt(req.getParameter("classNum"));
			OnedayDTO dto=dao.readOneday(classNum);
			if(dto==null) {
				resp.sendRedirect(cp+"/oneday/list.do?page="+page);
				return;
			}
			
			//�Խñ� �ۼ��� �Ǵ� �����ڰ� �ƴϸ�
			if(! dto.getUserId().equals(info.getUserId()) && ! info.getUserId().equals("admin")) {
				resp.sendRedirect(cp+"/oneday/list.do?page="+page);
				return;
			}
			
			//���������
			FileManager.doFiledelete(pathname, dto.getClassIFN());
			
			//���������
			dao.deleteOneday(classNum);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/oneday/list.do?page="+page);
	}
	
	
}
