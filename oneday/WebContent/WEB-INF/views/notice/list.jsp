<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/notice.css">
</head>
<body>

<!-- header -->
<div class="header_tem">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<section class="notice">
<div class="inner">
   <div class="title-area">
  	  <h3>&nbsp;&nbsp;&nbsp;&nbsp;공지사항</h3>
   </div>

   <div>
      <div class="board-notice">
         <ul class="article-table">
            <li class="item title">
               <span class="number">번호</span>
               <span class="subject">제목</span>
               <span class="date">작성일</span>
               <span class="hit">조회수</span>
            </li>

            <li class="item">
               <span class="number">1</span>
               <span class="subject"><a href="./notice-view.html?&Boardid=15">제목샘플1</a></span>
               <span class="date">2020-11-03</span>
               <span class="hit">조회수</span>
            </li>
            <li class="item">
               <span class="number">2</span>
               <span class="subject"><a href="./notice-view.html?&Boardid=14">제목샘플2</a></span>
               <span class="date">2020-11-03</span>
               <span class="hit">조회수</span>
            </li>
          </ul>
      </div>
   </div>
   

  <div class="pagenation">
       <ul><li class="number select"><a href="?&cCurrent=1">1</a></li></ul>
   </div>
</div>
</section> 

<!-- footer -->
<div class="footer_tem">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

</body>
</html>