package com.mypage;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.member.MemberDAO;
import com.member.MemberDAOImpl;
import com.member.MemberDTO;
import com.member.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;

@MultipartConfig
@WebServlet("/mypage/*")
public class MypageServlet extends MyUploadServlet{
	private static final long serialVersionUID = 1L;
	private String pathname;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
	      String uri=req.getRequestURI();
	      
	      HttpSession session=req.getSession();
	      SessionInfo info=(SessionInfo)session.getAttribute("member");
	      
	      //�̹��� ������ ���
		  String root=session.getServletContext().getRealPath("/");
		  pathname=root+"uploads"+File.separator+"photo";
	      
	      if(uri.indexOf("mypageMain.do")!= -1) {
	    	  if(info.getUserEnabled()==1) {	//�������� �� 
		    	  forward(req, resp, "/WEB-INF/views/mypage/mypage_main.jsp");
		      }else if(info.getUserEnabled()==100) {	//������ �� 
		    	  forward(req, resp, "/WEB-INF/views/mypage/mypage_tr.jsp");
		      }else if(info.getUserEnabled()==200) {	//�������� �� 
		    	  forward(req, resp, "/WEB-INF/views/mypage/mypage_main.jsp");
		      }
	      }else if(uri.indexOf("memberUpdate.do")!=-1) {
	    	  updateForm(req, resp);
	      }else if(uri.indexOf("memberUpdate_ok.do")!=-1) {
	    	  updateSubmit(req, resp);
	      }

	      
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//ȸ�� ���� ���� ��
		MemberDAO dao=new MemberDAOImpl();
		String cp=req.getContextPath();
		
		HttpSession session=req.getSession();
	    SessionInfo info=(SessionInfo)session.getAttribute("member");
		
	    try {
			MemberDTO dto=dao.readMember(info.getUserId());
			if(dto==null || ! info.getUserId().equals(dto.getUserId())) {
				resp.sendRedirect(cp+"/mypage/mypageMain.do");
				return;
			}
			
			req.setAttribute("dto", dto);
			
			forward(req, resp, "/WEB-INF/views/mypage/update.jsp");
			return;
			
		} catch (Exception e) {
			req.setAttribute("message", "���� ������ �����߽��ϴ�.");
		}
	    
	    //�Է��� ����� �ȵ����� �ٽ� ���������� â����
	    resp.sendRedirect(cp+"/mypage/mypageMain.do");
		
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//ȸ�� ���� ���� ó��
		String cp=req.getContextPath();
		MemberDAO dao=new MemberDAOImpl();
		
		HttpSession session=req.getSession();
	    SessionInfo info=(SessionInfo)session.getAttribute("member");
	    
		if(req.getMethod().equalsIgnoreCase("GET")) {
			//GET������� ������ ���
			resp.sendRedirect(cp+"/mypage/mypageMain.do");
			return;
		}
		
		try {
			MemberDTO dto=new MemberDTO();
			
			dto.setUserId(req.getParameter("userId"));
			dto.setUserPwd(req.getParameter("userPwd"));
			dto.setUserName(req.getParameter("userName"));
			dto.setUserTel(req.getParameter("userTel"));
			dto.setUserZip(req.getParameter("userZip"));
			dto.setUserAddr1(req.getParameter("userAddr1"));
			dto.setUserAddr2(req.getParameter("userAddr2"));
			dto.setUserEmail(req.getParameter("userEmail"));
			dto.setUserEnabled(Integer.parseInt(req.getParameter("userEnabled")));
			
			if(dto.getUserEnabled()==100) {
				String userCert=req.getParameter("userCert");
				
				Part p = req.getPart("userCert");
				Map<String, String> map=doFileUpload(p, pathname);
				if(map!=null) {
					String filename=map.get("saveFilename");
					dto.setUserCert(filename);
					
					FileManager.doFiledelete(userCert);					
				}
			}
			
			dao.updateMember(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/mypage/mypageMain.do");

	}
}
