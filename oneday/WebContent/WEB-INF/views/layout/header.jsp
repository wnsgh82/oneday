<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

  <div id="wrapper">
        <div id="header-top">
            <div id="login">
                <div style="float: right;">
                <ul>
                  <c:if test="${empty sessionScope.member}">  <%--로그인 안된 상태 --%>	
                    <li class="login2" style="padding-left: 70px"><a href="${pageContext.request.contextPath}/member/login.do">로그인&nbsp; &nbsp; &nbsp; &nbsp; |</a></li>
                    <li class="login2"><a href="${pageContext.request.contextPath}/member/selectlog.do">회원가입&nbsp; &nbsp; &nbsp; </a></li>
                  </c:if>
                  <c:if test="${not empty sessionScope.member}">   <%--로그인 된 상태 --%>
                    <li class="login2" style="color: #fff;"><span style="color: #fff;">${sessionScope.member.userName}</span>님
                    &nbsp;&nbsp;&nbsp;| </li>
                    <li class="login2" style="color: #fff;"><a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
                    &nbsp;&nbsp;&nbsp;| </li>
                    <li class="login2"><a href="${pageContext.request.contextPath}/mypage/mypageMain.do">마이페이지</a></li>
                  </c:if>
                </ul>
            </div>
        </div>
        </div>
        <div id="header">
            <div id="logo">
                <h1><%-- <img alt="" src="${pageContext.request.contextPath}/resource/css/images/haru2.png"> --%><a href="#"></a></h1>
            </div>
        </div>
        
        <div id="menu">
            <ul>
                <li><a href="#">소개</a></li>
                <li><a href="${pageContext.request.contextPath}">원데이클래스</a></li>
                <li><a href="#">이벤트</a></li>
                <li><a href="#">수강후기</a></li>
                <li><a href="#">공지사항</a></li>
                <li>
                    <a href="#">문의하기</a>
                </li>
            </ul>
        </div>
    </div>