package com.mypage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;

@WebServlet("/mypage/*")
public class mypageServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
	      String uri=req.getRequestURI();
	      
	      if(uri.indexOf("mypageMain.do")!= -1) {
	    	  forward(req, resp, "/WEB-INF/views/mypage/mypage_main.jsp");
	      }else if(uri.indexOf("login_ok.do")!=-1) {
	    	  
	      }
	}
	protected void mypageMain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}
