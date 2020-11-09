package com.review;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.ha.backend.Sender;

import com.member.SessionInfo;
import com.notice.NoticeDAO;
import com.notice.NoticeDAOImpl;
import com.notice.NoticeDTO;
import com.std.StdDAO;
import com.std.StdDAOImpl;
import com.std.StdDTO;
import com.util.MyUploadServlet;
import com.util.MyUtil;

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
		}else if(uri.indexOf("article.do")!=-1) {
			article(req, resp);
		}
			
	}
	
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ReviewDAO dao= new ReviewDAOImpl();
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
		
       
        int rows = 10;
        
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
		
		List<ReviewDTO> list;
		if(keyword.length()!=0) {
			list= dao.listReview(offset, rows, condition, keyword);
		} else {
			list= dao.listReview(offset, rows);
		}
			
		
		//글번호 만들기 -> 중간에 지워도 순서대로 되는거
		int listNum=0;
		int n=0;
		
		for(ReviewDTO dto : list ) {
			listNum=dataCount-(offset+n);
			dto.setListNum(listNum); //list에 출력된 num
			
			n++;
		}
		
		String query="";
		String listUrl;
		String articleUrl;
		
		listUrl=cp+"/review/list.do?rows="+rows;
		articleUrl=cp+"/review/article.do?page="+current_page+"&rows="+rows;
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
		req.setAttribute("articleUrl", articleUrl);
		req.setAttribute("paging", paging);
		req.setAttribute("condition",condition);
		req.setAttribute("keyword", keyword);
		req.setAttribute("dataCount", dataCount);
				
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
			dto.setClassNum(Integer.parseInt(req.getParameter("classNum1")));
			dto.setRvContent(req.getParameter("rvContent"));
			dto.setRvClassName(req.getParameter("className"));
			dto.setRvScore(req.getParameter("rvscore"));
			dto.setUserId(req.getParameter("userId"));
			
			dao.insertReview(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/review/list.do");
	}
	protected void MycreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		StdDAO dao = new StdDAOImpl();
		String userId = info.getUserId();
		ReviewDAO rao = new ReviewDAOImpl();
		
		List<StdDTO> list;
		
		int count = rao.reviewcount(userId);
		
		if(count > 0) {
			list= dao.listStd2(userId);
		}else{
			list= dao.listStd(userId);
		}
		
			
		req.setAttribute("list", list);
		req.setAttribute("info", info);
		req.setAttribute("mode", "created");
		
		
		forward(req, resp, "/WEB-INF/views/review/mycreated.jsp");
	}
	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ReviewDAO dao=new ReviewDAOImpl();
		String cp=req.getContextPath();
		
		
		String page=req.getParameter("page");
		String rows=req.getParameter("rows");
		String query="page="+page+"&rows="+rows;
		
		try {
			int num=Integer.parseInt(req.getParameter("rvNum"));
			
			String condition=req.getParameter("condition");
			String keyword=req.getParameter("keyword");
			if(condition==null) {
				condition="subject";
				keyword="";
			}
			keyword=URLDecoder.decode(keyword, "utf-8");

			if(keyword.length()!=0) {
				query+="&condition="+condition+"&keyword="+URLEncoder.encode(keyword, "UTF-8");
			}
			
			// 조회수
			dao.updateHitCount(num);
			
			// 게시물 가져오기
			ReviewDTO dto= dao.readReview(num);
			
			if(dto==null) {
				resp.sendRedirect(cp+"/review/list.do?"+query);
				return;
			}
			
			dto.setRvContent(dto.getRvContent().replaceAll("\n", "<br>"));
			
			// 이전글/다음글
			ReviewDTO preReadDto = dao.preReadNotice(dto.getRvNum(), condition, keyword);
			ReviewDTO nextReadDto = dao.nextReadNotice(dto.getRvNum(), condition, keyword);
			
			req.setAttribute("dto", dto);
			req.setAttribute("preReadDto", preReadDto);
			req.setAttribute("nextReadDto", nextReadDto);
			req.setAttribute("query", query);
			req.setAttribute("page", page);
			req.setAttribute("rows", rows);
			
			forward(req, resp, "/WEB-INF/views/review/article.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/review/list.do?"+query);
	}
}
