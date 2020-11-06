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
	            <h3>&nbsp;&nbsp;&nbsp;&nbsp;게시판이름</h3>
			</div>
			
			<form action="">
			      <div class="board-notice">
			         <ul class="article-table">
			            <li class="item title">
			               <span class="number">번호</span>
			               <span class="subject">제목</span>
			               <span class="date">작성일</span>
			               <span class="hit">조회수</span>
			               <span class="no_file">파일첨부</span>
			            </li>
			
			            <li class="item">
			               <span class="number">1</span>
			               <span class="subject"><a href="./notice-view.html?&Boardid=15">제목샘플1</a></span>
			               <span class="date">2020-11-03</span>
			               <span class="hit">조회수</span>
			               <span class="no_file"> <img alt="" src=""> </span>
			            </li>
			            
			          </ul>
			      </div>
			   
			
			  <div class="pagenation">
			       <ul><li class="number select"><a href="?&cCurrent=1">1 2 3</a></li></ul>
			   </div>
			   
			   <div id="list2">
			   <table id="listfooter">
				   <tr height="40" style="width: 100%">
				      <td id="resetbutton" align="left">
				          <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/notice/list.do';">새로고침</button>
				      </td>
				      <td id="selectsearch" align="center">
				              <select name="condition" class="selectField">
				                  <option value="subject"     ${condition=="subject"?"selected='selected'":"" }>제목</option>
				                  <option value="userName" ${condition=="userName"?"selected='selected'":"" }>작성자</option>
				                  <option value="content"     ${condition=="content"?"selected='selected'":"" }>내용</option>
				                  <option value="created"     ${condition=="created"?"selected='selected'":"" }>등록일</option>
				            </select>
				            <input type="text" name="keyword" class="boxTF" value="${keyword}">
				            <input type="hidden" name="rows" value="${rows}">
				            <button type="button" class="btn" onclick="searchList()">검색</button>
				      </td>
				      <td align="right" width="100">
				          <c:if test="${sessionScope.member.userId=='admin'}">
				              <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/notice/created.do?rows=${rows}';">글올리기</button>
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