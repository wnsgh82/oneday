package com.mypage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;

@WebServlet("/mypage/*")
public class MypageServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
	      String uri=req.getRequestURI();
	      
	      HttpSession session=req.getSession();
	      SessionInfo info=(SessionInfo)session.getAttribute("member");
	      
	      if(uri.indexOf("mypageMain.do")!= -1) {
	    	  if(info.getUserEnabled()==1) {	//수강생일 때 
		    	  forward(req, resp, "/WEB-INF/views/mypage/mypage_main.jsp");
		      }else if(info.getUserEnabled()==100) {	//강사일 때 
		    	  forward(req, resp, "/WEB-INF/views/mypage/mypage_tr.jsp");
		      }else if(info.getUserEnabled()==200) {	//관리자일 때 
		    	  forward(req, resp, "/WEB-INF/views/mypage/mypage_main.jsp");
		      }
	      }else if(uri.indexOf("login_ok.do")!=-1) {
	    	  
	      }
	      
	      
	      
	      
	}


}
