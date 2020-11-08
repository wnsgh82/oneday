package com.notice;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
		} else if(uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("deleteFile.do")!=-1) {
			deleteFile(req, resp); //÷�ε� ���� �ø������� �����ϴ°�
		} else if(uri.indexOf("delete.do")!=-1) {
			delete(req, resp);
		} else if(uri.indexOf("download.do")!=-1) {
			download(req, resp);
			
		}
		
	}
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//����Ʈ
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
//		
//		for(NoticeDTO ddd : list ) {
//			String d=ddd.getNoSubject();
//			System.out.println(d);
//		}
//		
		//������
		List<NoticeDTO> listNotice=null;
		listNotice=dao.listNotice();
		for(NoticeDTO dto : listNotice) {
			dto.setNoCreated(dto.getNoCreated().substring(0,10));
		}
		
		//�۹�ȣ ����� -> �߰��� ������ ������� �Ǵ°�
		int listNum=0;
		int n=0;
		
		for(NoticeDTO dto : list ) {
			listNum=dataCount-(offset+n);
			dto.setListNum(listNum); //list�� ��µ� num
			
			dto.setNoCreated(dto.getNoCreated().substring(0,10));
			//����Ʈ���� ����ϸ� ����ҰŴϱ�
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
		
		//�ѱ��
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
		//�۾�����
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		String cp=req.getContextPath();
		String rows=req.getParameter("rows"); //rows �޾ƿ�
		
		// admin�� ���� ���
		if(! info.getUserId().equals("admin")) { //admin�ƴϸ� ����Ʈ��
			resp.sendRedirect(cp+"/notice/list.do?rows="+rows);
			return;
		}
		req.setAttribute("mode", "created");
		req.setAttribute("rows", rows);
		forward(req, resp, "/WEB-INF/views/notice/created.jsp");
		
	}
	
	protected void createdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//�������ϱ�
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		NoticeDAO dao = new NoticeDAOImpl();
		String cp=req.getContextPath();
		String rows=req.getParameter("rows");
		
		try {
			NoticeDTO dto = new NoticeDTO();
			
			if(req.getParameter("notice")!=null) { //������
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
		//�ۺ���
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
			
			//���� �ٲٴ°�
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
		NoticeDAO dao=new NoticeDAOImpl();
		String cp=req.getContextPath();
		
		String page=req.getParameter("page");
		String rows=req.getParameter("rows");
		
		try {
			int noNum=Integer.parseInt(req.getParameter("noNum"));
			
			NoticeDTO dto=dao.readNotice(noNum);
			if(dto==null) {
				resp.sendRedirect(cp+"/notice/list.do?page="+page+"&rows="+rows);
				return;
			}
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("rows", rows);
			
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/notice/created.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/notice/list.do?page="+page+"&rows="+rows);
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeDAO dao=new NoticeDAOImpl();
		String cp=req.getContextPath();
		
		NoticeDTO dto=new NoticeDTO();
		
		String page=req.getParameter("page");
		String rows=req.getParameter("rows");
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/notice/list.do?page="+page+"&rows="+rows);
			return;
		}
		
		try {
			int noNum=Integer.parseInt(req.getParameter("noNum"));
			dto.setNoNum(noNum);
			
		    if(req.getParameter("notice")!=null) {
		    	dto.setNotice(Integer.parseInt(req.getParameter("notice")));
		    }
			dto.setNoSubject(req.getParameter("noSubject"));
			dto.setNoContent(req.getParameter("noContent"));
			dto.setNoSaveFileName(req.getParameter("noSaveFileName"));
			dto.setNoOrginalFileName(req.getParameter("noOrginalFileName"));
			
			Part p = req.getPart("upload");
			Map<String, String> map = doFileUpload(p, pathname);
			if(map != null) {
				if(req.getParameter("noSaveFileName").length()!=0) {
					// �������� ����
					FileManager.doFiledelete(pathname, req.getParameter("noSaveFileName"));
				}

				// ���ο� ����
				String noSaveFileName = map.get("saveFilename");
				String noOrginalFileName = map.get("originalFilename");
				
				dto.setNoSaveFileName(noSaveFileName);
				dto.setNoOrginalFileName(noOrginalFileName);

			}

			dao.updateNotice(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/notice/list.do?page="+page+"&rows="+rows);
	}
	
	private void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// �������� ���ϸ� ����
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		NoticeDAO dao=new NoticeDAOImpl();
		String cp=req.getContextPath();
	
		String page=req.getParameter("page");
		String rows=req.getParameter("rows");
		
		try {
			int noNum=Integer.parseInt(req.getParameter("noNum"));
			NoticeDTO dto=dao.readNotice(noNum);
			if(dto==null) {
				resp.sendRedirect(cp+"/notice/list.do?page="+page+"&rows="+rows);
				return;
			}
			// ���ϻ���
			FileManager.doFiledelete(pathname, dto.getNoSaveFileName());
			
			// ���ϸ�� ����ũ�� ����
			dto.setNoOrginalFileName("");
			dto.setNoSaveFileName("");
			dao.updateNotice(dto);
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("rows", rows);
			
			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/views/notice/created.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/notice/list.do?page="+page+"&rows="+rows);
	}

	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//��������
		NoticeDAO dao=new NoticeDAOImpl();
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
				query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
			}

			NoticeDTO dto=dao.readNotice(noNum);
			if(dto==null) {
				resp.sendRedirect(cp+"/notice/list.do?"+query);
				return;
			}
			

			if(dto.getNoSaveFileName()!=null && dto.getNoSaveFileName().length()!=0) {
				FileManager.doFiledelete(pathname, dto.getNoSaveFileName());
			}
			
			dao.deleteNotice(noNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/notice/list.do?"+query);
	}

	
	protected void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//�ٿ�ε�
		NoticeDAO dao=new NoticeDAOImpl();
		int noNum=Integer.parseInt(req.getParameter("noNum")); //��ȣ ��������
		
		NoticeDTO dto=dao.readNotice(noNum); //��񿡼� ������ �������� 
		boolean b=false; //�ʱⰪ false
		if(dto!=null) { //���� �����Ͱ� ������ -> �ݵ�� �ִ°� �ƴ϶�
			b = FileManager.doFiledownload(dto.getNoSaveFileName(), //->������ ����� �����̸� �˾ƾ� ���� ���� �� ���� 
					dto.getNoOrginalFileName(), pathname, resp); //getOriginalFilename Ŭ���̾�Ʈ�� �ø��̸� / 
			//pathname  : ������ / resp :Ŭ���̾�Ʈ
		}
		
		if(! b) { //���� �ٿ�ε� �����Ұ�� 
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out=resp.getWriter();
			out.print("<script>alert('���ϴٿ�ε尡 ���� �߽��ϴ�.');history.back();</script>");
			//history.back() �����·� ����(����Ʈ)
		}
	}
}
