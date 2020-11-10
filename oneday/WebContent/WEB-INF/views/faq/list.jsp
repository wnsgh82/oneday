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
<script type="text/javascript">
function searchList() {
	var f=document.QnaForm;
	f.submit();
}
</script>
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
			 		<span style="width: 45%;">
			 			<a href="${pageContext.request.contextPath}/qna/list.do">문의하기</a>
			 		</span>
			 		<span style="width: 45%; float: right; border-bottom: 3px solid; 	font-weight: 600;">
			 			<a href="${pageContext.request.contextPath}/faq/list.do">자주하는 질문</a>
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
			            
			            <li class="item title Q" >
			               <span id="group" style="width: 80px; float: left;">신청</span>
			               <span style="width: 800px; float: left; text-align: left">신청 취소는 언제까지 가능한가요?</span>
			            </li>
			            <li class="item A">
			               <span style="width: 10%; float: left; font-size: 20px; font-weight: 900">A</span>
			               <span id="" style="width: 90%; float: left; text-align: left; margin: auto 0px;">신청 취소는 언제까지 가능한가요?</span>
			            </li>
			
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
				          <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/qna/list.do';">새로고침</button>
				      </td>
				      <td id="selectsearch" align="center">
				              <select name="condition" class="selectField">
				                  <option value="all"     ${condition=="all"?"selected='selected'":"" }>제목+내용</option>
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