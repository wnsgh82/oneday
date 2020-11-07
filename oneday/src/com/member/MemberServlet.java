package com.member;

import java.io.File;
import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.util.MyUploadServlet;

@MultipartConfig
@WebServlet("/member/*")
public class MemberServlet extends MyUploadServlet{
	private static final long serialVersionUID = 1L;
	private String pathname;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
	
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		HttpSession session=req.getSession();
		
	    String root=session.getServletContext().getRealPath("/");
	    pathname=root+"uploads"+File.separator+"photoimg";
		
		if(uri.indexOf("login.do")!=-1) {
			loginForm(req, resp);
		} else if(uri.indexOf("login_ok.do")!=-1) {
			loginSubmit(req, resp);
		} else if(uri.indexOf("logout.do")!=-1) {
			logout(req, resp);
		} else if(uri.indexOf("member.do")!=-1) {
			memberForm(req, resp);
		} else if(uri.indexOf("member_ok.do")!=-1) {
			memberSubmit(req, resp);
		} else if(uri.indexOf("pwd.do")!=-1) {
			pwdForm(req, resp);
		} else if(uri.indexOf("pwd_ok.do")!=-1) {
			pwdSubmit(req, resp);
		} else if(uri.indexOf("update.do")!=-1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do")!=-1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("userIdCheck.do")!=-1) {
			userIdCheck(req, resp);
		}  else if(uri.indexOf("selectlog.do")!=-1) {
			selectlogin(req, resp);
		}

	}
	
	protected void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//�α��� ��
		
		
		
		String path="/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
	}
	
	protected void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//�α��� ó��
		MemberDAO dao=new MemberDAOImpl();
		String cp=req.getContextPath();
		
		try {
			String userId=req.getParameter("userId");
			String userPwd=req.getParameter("userPwd");
			MemberDTO dto= dao.readMember(userId);

			if(dto!=null) {
				if(dto.getUserPwd().equals(userPwd)) {  // dto.getUserEnabled()==1 || 100 || 200 ? �ؾߵǳ� ? ����?
					//�α��� ����
					HttpSession session=req.getSession(); //���� ��ü
					
					//���� �����ð��� 25������ ����(������ �⺻ 30��)
					session.setMaxInactiveInterval(25*60);
					
					//���ǿ� ������ ����
					SessionInfo info=new SessionInfo();
					info.setUserId(dto.getUserId());
					info.setUserName(dto.getUserName());
					
					//���ǿ� ���� ����
					session.setAttribute("member", info);
					
					//���� ȭ������
					resp.sendRedirect(cp);
					return;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//�α����� ������ ���
		req.setAttribute("message", "���̵� �Ǵ� �н����尡 ��ġ���� �ʽ��ϴ�.");
		forward(req, resp, "/WEB-INF/views/member/login.jsp");
	}
	
	protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//�α׾ƿ�
		HttpSession session=req.getSession();
		
		//���ǿ� ����� ��� ������ ����� ������ �ʱ�ȭ
		session.invalidate();
		
		//Ư���� ������ ������ ���!!
		//session.removeAttribute("member");
		
		String cp=req.getContextPath();
		resp.sendRedirect(cp);
		
	}
	
	protected void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//ȸ������ ��
		String userEnabled = req.getParameter("userEnabled");
		
		if(userEnabled == null) {
			userEnabled= "1";
		}
		int enable = Integer.parseInt(userEnabled);
		
		req.setAttribute("userEnabled", enable);
		req.setAttribute("mode", "member");
		req.setAttribute("title", "ȸ�� ����");
		String path="/WEB-INF/views/member/member.jsp";
		forward(req, resp, path);
	}
	
	protected void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//ȸ������ ó��
		MemberDAO dao = new MemberDAOImpl();
		String cp=req.getContextPath();
		
		try {
	
			MemberDTO dto = new MemberDTO();
			
			dto.setUserId(req.getParameter("userId"));
			dto.setUserName(req.getParameter("userName"));
			dto.setUserPwd(req.getParameter("userPwd"));
			dto.setUserEmail(req.getParameter("userEmail"));
			dto.setUserTel(req.getParameter("userTel"));
			dto.setUserZip(req.getParameter("userZip"));
			dto.setUserAddr1(req.getParameter("userAddr1"));
			dto.setUserAddr2(req.getParameter("userAddr2"));
			
			int enable = Integer.parseInt(req.getParameter("userEnabled"));
				
			if(enable ==100) {
				String filename=null; 
				Part p=req.getPart("userCert"); // �Ϲ�ȸ���� null 
				Map<String, String> map= doFileUpload(p, pathname); 
				if(map!=null) {
					filename=map.get("saveFilename"); 
					dto.setUserCert(filename); 
					enable =100;
					dto.setUserEnabled(enable); 
				}			
			}

			dao.insertMember(dto,enable);
			
			resp.sendRedirect(cp);
			return;
			
		} catch (SQLIntegrityConstraintViolationException e) {
			req.setAttribute("message", "���̵� �ߺ� ���� ���Ἲ ���� ���� �����Դϴ�.");
		} catch (SQLDataException e) {
			req.setAttribute("message", "��¥ ���� ���� �߸� �ԷµǾ����ϴ�.");
		} catch (Exception e) {
			req.setAttribute("message", "������ �߰��� �����߽��ϴ�.");
		}
		
		//�Է��� ����� �ȵ����� �ٽ� ȸ������ â���� 
		req.setAttribute("mode", "member");
		req.setAttribute("title", "ȸ�� ����");
		String path="/WEB-INF/views/member/list.do";
		forward(req, resp, path);
	}
	
	protected void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//����, Ż�� ��� �н����� �� 
		
	}
	
	protected void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//�н����� �˻�
	}
	
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//ȸ�� ���� ���� ��
	}
	
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//ȸ�� ���� ���� ó��
	}
	
	protected void userIdCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//ȸ�� ���̵� �ߺ� �˻�
	}
	
	protected void selectlogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//ȸ������ ���� 
	
		String path="/WEB-INF/views/member/selectlogin.jsp";
		forward(req, resp, path);
		
	}
	

	
}
