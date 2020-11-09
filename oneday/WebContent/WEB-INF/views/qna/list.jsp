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
			 <ul class="qna">
			 	<li class="qna1">
			 		<span style="width: 45%; border-bottom: 3px solid; 	font-weight: 600;">
			 			<a href="${pageContext.request.contextPath}/qna/list.do">문의하기</a>
			 		</span>
			 		<span style="width: 45%; float: right;">
			 			<a>자주하는 질문</a>
			 		</span>
			 	</li>
			 </ul>
			
			<form name="QnaForm" method="post">
			
			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px;">
			   <tr height="35">
			      <td align="left" width="50%" id="delbut">
			          	${dataCount}개(${page}/${total_page} 페이지)
			      </td>
			   </tr>
			</table>
			
			      <div class="board-notice">
			         <ul class="article-table">
			            <li class="item title">
			               <span style="width: 11%; float: left;">번호</span>
			               <span style="width: 55%; float: left;">제목</span>
			               <span style="width: 9%; float: left;">작성자</span>
			               <span style="width: 15%; float: left;">작성일</span>
			               <span style="width: 9%; float: left;">조회수</span>
			            </li>
			
						<c:forEach var="dto" items="${list}">
			            <li class="item">
			               <span style="width: 11%; float: left;">${dto.listNum }</span>
			               <span style="width: 55%; float: left; text-align:left;">
				               	<c:forEach var="n" begin="1" end="${dto.depth}">&nbsp;&nbsp;</c:forEach>
				      			<c:if test="${dto.depth==0}">
				      				<c:if test="${dto.bEnabled==1}">
				      					<span  style="border : 1px solid; display: inline-block; padding:1px 3px;">답변대기</span>
				      				</c:if>
				      				<c:if test="${dto.bEnabled==0}">
				      					<span  style="background-color:black; display: inline-block; padding:1px 3px; color: white;">답변완료</span>
				      				</c:if>
				      			</c:if>
				      			${dto.depth!=0?"└&nbsp;":""}
								<a href="${articleUrl}&bNum=${dto.bNum}">${dto.bSubject}</a>
			               </span>
			               <span style="width: 9%; float: left;">${dto.userName }</span>
			               <span style="width: 15%; float: left;">${dto.bCreated }</span>
			               <span style="width: 9%; float: left;">${dto.bHitCount }</span>
			            </li>
						</c:forEach>
			          </ul>
			      </div>
			   
			
				 <table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
				   <tr height="35">
					<td align="center">
						${dataCount!=0?paging:"등록된 게시물이 없습니다."}
					</td>
				   </tr>
				</table>
				
			   <div id="list2">
			   <table id="listfooter">
				   <tr height="40" style="width: 100%">
				      <td id="resetbutton" align="left">
				          <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/notice/list.do';">새로고침</button>
				      </td>
				      <td id="selectsearch" align="center">
				              <select name="condition" class="selectField">
				                  <option value="all"     ${condition=="all"?"selected='selected'":"" }>제목</option>
				                  <option value="subject"     ${condition=="subject"?"selected='selected'":"" }>제목</option>
				                  <option value="userName" 	  ${condition=="userName"?"selected='selected'":"" }>작성자</option>
				                  <option value="content"     ${condition=="content"?"selected='selected'":"" }>내용</option>
				                  <option value="created"     ${condition=="created"?"selected='selected'":"" }>등록일</option>
				            </select>
				            <input type="text" name="keyword" class="boxTF" value="${keyword}">
				            <input type="hidden" name="rows" value="${rows}">
				            <button type="button" class="btn" onclick="searchList()">검색</button>
				      </td>
				      <td align="right" width="100">
				      	<c:if test="${not empty sessionScope.member.userId}">
				              <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/qna/created.do?';">글올리기</button>
				      	</c:if> 
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