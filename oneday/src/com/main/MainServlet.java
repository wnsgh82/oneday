package com.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oneday.OnedayDAO;
import com.oneday.OnedayDTO;
import com.oneday.OnedayImpl;
import com.util.MyServlet;

@WebServlet("/main.do")
public class MainServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		
		OnedayDAO dao=new OnedayImpl();
		String page=req.getParameter("page");
		int current_page=1;
		if(page!=null) {
			current_page=Integer.parseInt(page);
		}
		
		int rows=8;
		int offset=(current_page-1)*rows;
		if(offset<0) offset=0;
		
		List<OnedayDTO> list=dao.listOneday(offset, rows);
		
		req.setAttribute("list", list);
		
		if(uri.indexOf("main.do")!=-1) {
			forward(req, resp, "/WEB-INF/views/main/main.jsp");
				//상속해주는 MyServlet 에 포워딩생성해둬서 재정의가능
		}
		
	}

}
