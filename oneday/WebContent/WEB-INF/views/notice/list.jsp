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
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.1/css/all.css" integrity="sha384-vp86vTRFVJgpjF9jiIGPEEqYqlDwgyBgEF109VFjmqGmIY/Y4HV4d3Gp2irVfcrp" crossorigin="anonymous">
<style type="text/css">
#delbut button {
	width: 70px;
	height: 30px;
	background: #f1f1f1;
	border-radius: 9px;
	border: 1px solid #ccc;
}
</style>
<script type="text/javascript">
function searchList() {
	
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
	            <h3>&nbsp;&nbsp;&nbsp;&nbsp;공지사항</h3>
			</div>
			
			<form action="noticeListForm" method="post" >
			
			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px;">
			   <tr height="35">
			      <td align="left" width="50%" id="delbut">
			      	  <c:if test="${sessionScope.member.userId=='admin'}">
			          	<button type="button" class="btn" id="btnDeleteList">삭제</button>
			          </c:if>
			      	  <c:if test="${sessionScope.member.userId!='admin'}">
			          	${dataCount}개(${page}/${total_page} 페이지)
			          </c:if>
			      </td>
			   </tr>
			</table>
			
			      <div class="board-notice">
			         <ul class="article-table">
			            <li class="item title">
			    	        <span class="checkdel">
			    	        
			    	        
				    	        <c:if test="${sessionScope.member.userId=='admin'}">
					    	        	<input type="checkbox" name="chkAll" id="chkAll" style="margin-top: 3px;">
				            	</c:if>				  	      	 	
			    	        </span>
			               <span class="number">번호</span>
			               <span class="subject">제목</span>
			               <span class="date">작성일</span>
			               <span class="hit">조회수</span>
			               <span class="no_file">파일첨부</span>
			            </li>
				
				
					<!-- 공지딱지 붙는거 -->
					<c:forEach var="dto" items="${listNotice}">
			            <li class="item">
		    	        	<span class="checkdel">
				            	<c:if test="${sessionScope.member.userId=='admin'}">
				   	  					<input type="checkbox" name="nums" value="${dto.noNum}" style="margin-top: 3px;" data-filename="${dto.noSaveFileName}">
				            	</c:if>		
		    	        	</span>
			               <span class="number"> <span  style="background-color: red; display: inline-block; padding:1px 3px; color: white;">공지</span></span>
			               <span class="subject"><a href="${articleUrl}&noNum=${dto.noNum}">${dto.noSubject }</a></span>
			               <span class="date">${dto.noCreated} </span>
			               <span class="hit">${dto.noHitCount }</span>
			               <span class="no_file"> 
         					<c:if test="${not empty dto.noSaveFileName}">
						      <a href="${pageContext.request.contextPath}/notice/download.do?noNum="${dto.noNum }><i class="fas fa-save"></i></a>
							</c:if> 
			               </span>
			            </li>
					</c:forEach>
					
					<!-- 그냥공지 -->
					<c:forEach var="dto" items="${list}">
			            <li class="item">
		    	        	<span class="checkdel">
				            	<c:if test="${sessionScope.member.userId=='admin'}">
				   	  					<input type="checkbox" name="nums" value="${dto.noNum}" style="margin-top: 3px;" data-filename="${dto.noSaveFileName}">
				            	</c:if>		
		    	        	</span>
			               <span class="number">${dto.listNum }</span>
			               <span class="subject"><a href="${articleUrl}&noNum=${dto.noNum }">${dto.noSubject }</a></span>
			               <span class="date">${dto.noCreated} </span>
			               <span class="hit">${dto.noHitCount }</span>
			               <span class="no_file"> 
         					<c:if test="${not empty dto.noSaveFileName}">
						      <a href="${pageContext.request.contextPath}/notice/download.do?num="${dto.noNum }><i class="fas fa-save"></i></a>
							</c:if> 
			               </span>
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
				                  <option value="all"  ${condition=="all"?"selected='selected'":"" }>제목+내용</option>
				                  <option value="subject"  ${condition=="subject"?"selected='selected'":"" }>제목</option>
				                  <option value="userName" ${condition=="userName"?"selected='selected'":"" }>작성자</option>
				                  <option value="content"  ${condition=="content"?"selected='selected'":"" }>내용</option>
				                  <option value="created"  ${condition=="created"?"selected='selected'":"" }>등록일</option>
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