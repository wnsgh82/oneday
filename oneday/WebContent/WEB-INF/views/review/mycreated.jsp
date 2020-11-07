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
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/notice.css">
</head>
<body>

<!-- header -->
<div class="header_tem">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<div class="body_tem">
	<div class="content_tem">
		<!-- 여기부터 내용 -->
		<section class="notice">
			<div class="inner">
			<div class="title-area">
	            <h3>&nbsp;&nbsp;&nbsp;&nbsp;리뷰게시판</h3>
			</div>
			
			<form action="${pageContext.request.contextPath}/review/created.do" method="post">
			      <div class="board-notice">
			         <ul class="article-table">
			            <li class="item title">
			               <span class="number">번호</span>
			               <span class="subject">제목</span>
			               <span class="hit">강사이름</span>
			               <span class="">날짜</span>
			            </li>
			<c:forEach var="dto" items="${list}">
			            <li class="item">
			           
			            	<input type="hidden" name="classNum" value="${dto.classNum}">
			            	<input type="hidden" name="className" value="${dto.className}">
			            	<input type="hidden" name="userId" value="${dto.userId}">
			            	<span class="number">
			            		<button>리뷰쓰기</button>
			            	</span>
			               <span class="subject">${dto.className}</span>
			               <span class="hit">${dto.trName}</span>
			               <span class="">${dto.classDate}</span>
	               
			            </li>
			</c:forEach>          
			          </ul>
			      </div>
			   
			   <div id="list2">
			   <table id="listfooter">
				   <tr height="40" style="width: 100%">
				      <td id="resetbutton" align="left">
				          <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/review/list.do';">새로고침</button>
				      </td>
				   </tr>
				</table>
			   </div>
			   
			</form>
			
			</div>
		</section> 

	</div>
</div>

<!-- footer -->
<div class="footer_tem">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

</body>
</html>