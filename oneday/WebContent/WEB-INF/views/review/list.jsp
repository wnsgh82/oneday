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
 var f = document.reviewf;
 
 f.action="${pageContext.request.contextPath}/review/list.do"
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
			<div class="title-area">
	            <h3>&nbsp;&nbsp;&nbsp;&nbsp;후기 게시판</h3>
			</div>
			
			<form action="" name="reviewf">
			      <div class="board-notice">
			         <ul class="article-table">
			            <li class="item title">
			               <span class="number" style="width:10%">번호</span>
			               <span class="subject" style="width:50%">클래스 이름</span>
			               <span class="date" style="width:20%">작성일</span>
			               <span class="hit" style="width:10%">조회수</span>
			               <span class="number" style="width:10%">점수</span>
			            </li>
						<c:forEach var="dto" items="${list}">
			            <li class="item">
			               <span class="number" style="width:10%">${dto.listNum}</span>
			               <span class="subject" style="width:50%"><a href="${articleUrl}&rvNum=${dto.rvNum}">${dto.rvClassName}</a></span>
			               <span class="date" style="width:20%">${dto.rvCreated }</span>
			               <span class="hit" style="width:10%">${dto.rvHitcount }</span>
			               <span class="number" style="width:10%">${dto.rvScore}&nbsp;/&nbsp;5</span>
			            </li>
			            </c:forEach>
			          </ul>
			      </div>
			   
			
			  <div class="pagenation">
			       <ul><li class="number select">${dataCount==0?"등록된 게시물이 없습니다.":paging}</a></li></ul>
			   </div>
			   
			   <div id="list2">
			   <table id="listfooter">
				   <tr height="40" style="width: 100%">
				      <td id="resetbutton" align="left">
				          <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/review/list.do';">새로고침</button>
				      </td>
				      <td id="selectsearch" align="center">
				              <select name="condition" class="selectField">
				                  <option value="rvclassname"     ${condition=="rvclassname"?"selected='selected'":"" }>제목</option>
				                  <option value="userId" ${condition=="userId"?"selected='selected'":"" }>작성자</option>
				                  <option value="rvContent"     ${condition=="rvContent"?"selected='selected'":"" }>내용</option>
				     
				            </select>
				            <input type="text" name="keyword" class="boxTF" value="${keyword}">
				            <input type="hidden" name="rows" value="${rows}">
				            <button type="button" class="btn" onclick="searchList()">검색</button>
				      </td>
				      <td align="right" width="100">
				      		<c:if test="${not empty sessionScope.member.userId}">
				              <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/review/mycrt.do';">리뷰작성</button>
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