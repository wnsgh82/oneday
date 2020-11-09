package com.mypage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.oneday.OnedayDAO;
import com.oneday.OnedayDTO;
import com.oneday.OnedayImpl;
import com.std.StdDAO;
import com.std.StdDAOImpl;
import com.std.StdDTO;
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
		  
		  //���� ���������� - ���� Ŭ������ ������ �Ķ���� ��
		  TrmyDAO dao=new TrmyDAO();
		  List<TrmyDTO> list=dao.readClass(info.getUserId());
		  
		  req.setAttribute("list", list);
		  
	      
	      if(uri.indexOf("mypageMain.do")!= -1) {
	    	  if(info.getUserEnabled()==1) {	//�������� �� 
	    	 	stdForm(req, resp);
		      }else if(info.getUserEnabled()==100) {	//������ �� 
		    	  forward(req, resp, "/WEB-INF/views/mypage/mypage_tr.jsp");
		      }else if(info.getUserEnabled()==200) {	//�������� �� 
		    	  forward(req, resp, "/WEB-INF/views/mypage/mypage_main.jsp");
		      }
	      }else if(uri.indexOf("memberUpdate.do")!=-1) {  //ȸ�� ���� ����
	    	  updateForm(req, resp);
	      }else if(uri.indexOf("memberUpdate_ok.do")!=-1) {
	    	  updateSubmit(req, resp);
	      }else if(uri.indexOf("classList.do")!=-1) {
	    	  trclassList(req, resp);
	      }else if(uri.indexOf("stdlist.do")!=-1) {
	    	  trstdList(req, resp);
	      }else if(uri.indexOf("pwd.do")!=-1) {
	    	  pwdForm(req, resp);
	      }else if(uri.indexOf("memberDelete.do")!=-1) {
	    	  delete(req, resp);
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
	
	protected void trclassList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//���� ���������� - �� ������ ����Ʈ
		String cp=req.getContextPath();
		TrmyDAO dao=new TrmyDAO();
		
		HttpSession session=req.getSession();
	    SessionInfo info=(SessionInfo)session.getAttribute("member");
	    
	    try {
	    	//���� ���������� - ������ ������ ������ �Ķ���� ��
			List<TrmyDTO> list=dao.readClass(info.getUserId());	
			
			req.setAttribute("list", list);
			
			forward(req, resp, "/WEB-INF/views/mypage/mypage_tr_classList.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    resp.sendRedirect(cp+"/mypage/mypageMain.do");
	}
	
	protected void trstdList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//���� ���������� - �� ������ ����Ʈ
		String cp=req.getContextPath();
		TrmyDAO dao=new TrmyDAO();
		
		HttpSession session=req.getSession();
	    SessionInfo info=(SessionInfo)session.getAttribute("member");
	    
	    try {
	    	//���� ���������� - ������ ������ ������ �Ķ���� ��
			int classNum=Integer.parseInt(req.getParameter("classNum"));

			List<TrmyDTO> stdList=dao.stdList(info.getUserName(), classNum); //trName, classNum
			
			List<TrmyDTO> list=dao.listDTO(info.getUserId());
			
			req.setAttribute("classNum", classNum);
			req.setAttribute("list", list);
			req.setAttribute("stdList", stdList);
			
			forward(req, resp, "/WEB-INF/views/mypage/mypage_tr_stdlist.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    resp.sendRedirect(cp+"/mypage/mypageMain.do");
	}
	
	protected void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//�н����� Ȯ�� ��
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
			
			forward(req, resp, "/WEB-INF/views/mypage/pwd.jsp");
			return;
			
		} catch (Exception e) {
			req.setAttribute("message", "��й�ȣ�� Ʋ�Ƚ��ϴ�.");
		}
	    
	    //�Է��� ����� �ȵ����� �ٽ� ���������� â����
	    resp.sendRedirect(cp+"/mypage/mypageMain.do");
		
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//ȸ�� Ż��
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
			String userPwd=req.getParameter("userPwd");
			
			dao.deleteMember(info.getUserId(), userPwd);
			session.invalidate();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp);
	}
	
	protected void stdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session=req.getSession();
	    SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		long gap;
		Date curDate = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		String userId = info.getUserId();
		
		
		StdDAO dao = new StdDAOImpl();
		List<StdDTO> list = dao.listStd(userId);
		List<StdDTO> list2 = new ArrayList<StdDTO>();
		
		for (StdDTO dto : list) {
			
			try {
				Date date=sdf.parse(dto.getClassDate().substring(0,10));
								
				// gap = (curDate.getTime() - date.getTime()) /(1000*60*60*24); // ����
				gap = (curDate.getTime() - date.getTime()) /(1000*60*60); // ����ð� - ���� ���۳��� 
				dto.setStartgap(gap);
				
				date=sdf.parse(dto.getClassDate().substring(13));
				
				// gap = (curDate.getTime() - date.getTime()) /(1000*60*60*24); // ����
				gap = (curDate.getTime() - date.getTime()) /(1000*60*60); // ����ð� - ���� ���������� 
				dto.setEndgap(gap);
				
				if(dto.getStartgap() < 0) {
					dto.setStdstate("������");
				}else if(dto.getStartgap() > 0 && dto.getEndgap() < 0) {
					dto.setStdstate("������");
				}else {
					dto.setStdstate("�����Ϸ�");
				}
				
				dto.setClassName(dto.getClassName());
				dto.setClassDate(dto.getClassDate());
				
				list2.add(dto);
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			req.setAttribute("list2", list2);
			
		}
		
	
	
		forward(req, resp, "/WEB-INF/views/mypage/mypage_main.jsp");
	}
}
