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
		} else if(uri.indexOf("userIdCheck.do")!=-1) {
			userIdCheck(req, resp);
		} else if(uri.indexOf("selectlog.do")!=-1) {
			selectlogin(req, resp);
		} else if(uri.indexOf("idForm.do")!=-1) {
			idForm(req, resp);
		} else if(uri.indexOf("idForm_ok.do")!=-1) {
			idSubmit(req, resp);
		} else if(uri.indexOf("pwdForm.do")!=-1) {
			pwdForm(req, resp);
		} else if(uri.indexOf("pwdForm_ok.do")!=-1) {
			pwdSubmit(req, resp);
		} else if(uri.indexOf("idCheck.do")!=-1) {
			idCheck(req, resp);
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
					info.setEmail(dto.getUserEmail());
					info.setTel(dto.getUserTel());
					info.setBirth(dto.getUserBirth());
					info.setUserEnabled(dto.getUserEnabled());
					
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
			
			String enableck  = (req.getParameter("userEnabled"));
			
			if(enableck==null) {
				enableck="1";
			}
			int enable=Integer.parseInt(enableck);
				
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
			e.printStackTrace();
		}
		
		//�Է��� ����� �ȵ����� �ٽ� ȸ������ â���� 
		req.setAttribute("mode", "member");
		req.setAttribute("title", "ȸ�� ����");
		String path="/WEB-INF/views/member/member.jsp";
		forward(req, resp, path);
	}
	
	protected void userIdCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//ȸ�� ���̵� �ߺ� �˻�
	}
	
	protected void selectlogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//ȸ������ ���� 
	
		String path="/WEB-INF/views/member/selectlogin.jsp";
		forward(req, resp, path);
		
	}
	
	protected void idForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//���̵� ã�� ��
		String path="/WEB-INF/views/member/id.jsp";
		forward(req, resp, path);
	}
	
	protected void idSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao = new MemberDAOImpl();
		String cp=req.getContextPath();
		
		try {
			String userName=req.getParameter("userName");
			String userEmail=req.getParameter("userEmail");
			
			MemberDTO dto=dao.searchId(userName, userEmail);
			
			if(dto!=null) {
				req.setAttribute("dto", dto);
				forward(req, resp, "/WEB-INF/views/member/id_ok.jsp");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//ã�� ������ ���	
		req.setAttribute("message", "�̸� �Ǵ� �н����尡 ��ġ���� �ʽ��ϴ�.");
		forward(req, resp, "/WEB-INF/views/member/id.jsp");		
	}
	
	protected void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//��й�ȣ ã�� ��
		String path="/WEB-INF/views/member/pwd.jsp";
		forward(req, resp, path);
	}
	
	protected void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao = new MemberDAOImpl();
		String cp=req.getContextPath();
		
		try {
			String userName=req.getParameter("userName");
			String userId=req.getParameter("userId");
			
			MemberDTO dto=dao.searchPwd(userId, userName);
			
			if(dto!=null) {
				//�н����� �Ϻθ� ���
				String userPwd=dto.getUserPwd().substring(0,4);
				for(int i=4; i<dto.userPwd.length();i++) {
					userPwd+="*";
				}
				
				req.setAttribute("userPwd", userPwd);
				req.setAttribute("dto", dto);
				forward(req, resp, "/WEB-INF/views/member/pwd_ok.jsp");
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//ã�� ������ ���	
		req.setAttribute("message", "���̵� �Ǵ� �̸��� ��ġ���� �ʽ��ϴ�.");
		forward(req, resp, "/WEB-INF/views/member/pwd.jsp");	
	}
	protected void idCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId=req.getParameter("userId");
		String cp=req.getContextPath();
		
		MemberDAO dao=new MemberDAOImpl();
		
		try {

			MemberDTO dto= dao.readMember(userId);			
			
			if(dto==null) {
				req.setAttribute("message", "��밡���� ���̵��Դϴ�.");
				req.setAttribute("userId", userId);
				req.setAttribute("mode", "idCkOk");
				forward(req, resp, "/WEB-INF/views/member/member.jsp");	
				return;
			} else {
				req.setAttribute("message", "�̹� ������� ���̵��Դϴ� .");
				forward(req, resp, "/WEB-INF/views/member/member.jsp");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
