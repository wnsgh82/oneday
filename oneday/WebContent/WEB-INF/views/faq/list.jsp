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

<script type="text/javascript" src="${pageContext.request.contextPath}/resource/jquery/js/jquery.min.js"></script>

<script type="text/javascript">
function searchList() {
	var f=document.faqForm;
	f.submit();
}
function deleteBoard(bNum) {
	if(confirm("게시물을 삭제 하시겠습니까 ?")) {
		var url="${pageContext.request.contextPath}/faq/delete.do?page=${page}&rows=${rows}&bNum="+bNum;
		location.href=url;
	}
}
</script>
<script type="text/javascript">
$(function(){
	$(".itemA").hide();
	
	$(".itemQ").click(function(){
		var h = $(this).next(".itemA").is(":hidden");

		if(h) {
			$(".itemA").hide(300);
			$(this).next(".itemA").show(300);
		} else {
			$(this).next(".itemA").hide(300);
		}
		
	});
});

$(function(){
	$("#search1").change(function(){
		var v = $(this).val();
		if(v==="all") {
			$("#search2").hide();
			$("#search3").show();
			
			$("#search2").prop("disabled", true);
			$("#search3").prop("disabled", false);		
		} else {
			$("#search2").show();
			$("#search3").hide();
			
			$("#search2").prop("disabled", false);
			$("#search3").prop("disabled", true);		
		}
	});
});
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
			
			<form name="faqForm" method="post">
			
			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px;">
			   <tr height="35">
			      <td align="left" width="50%" id="delbut">
			          	${dataCount}개(${page}/${total_page} 페이지)
			      </td>
			   </tr>
			</table>
			
			      <div class="board-notice">
			         <ul class="article-table">
			            <c:forEach var="dto" items="${list}">
			            <li class="item title itemQ" style="cursor: pointer;">
			               <span id="group" style="width: 80px; float: left;">${dto.bGroup }</span>
			               <span style="width: 800px; float: left; text-align: left">${dto.bQ}</span>
			            </li>
			            <li class="item itemA">
			               <span style="width: 10%; float: left; font-size: 20px; font-weight: 900">A</span>
			               <span id="" style="width: 550px; float: left; text-align: left; margin: auto 0px;">${dto.bA }</span>
			               <c:if test="${sessionScope.member.userId=='admin'}">
			               <span>
			               		<button type="button" class="faqBtn" onclick="javascript:location.href='${pageContext.request.contextPath}/faq/update.do?page=${page}&rows=${rows}&bNum=${dto.bNum}';">수정</button>
			               		<button type="button" class="faqBtn" onclick="deleteBoard('${dto.bNum}');">삭제</button>
			               </span>
			               </c:if>
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
				          <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/faq/list.do';">새로고침</button>
				      </td>
				      <td id="selectsearch" align="center">
				              <select name="condition" class="selectField" id="search1">
				                  <option value="all"     ${condition=="all"?"selected='selected'":"" }>제목+내용</option>
				                  <option value="bGroup"     ${condition=="bGroup"?"selected='selected'":"" }>구분</option>
				            </select>
				            
					          <select name="keyword" class="selectField" id="search2" style="display: none;" disabled="disabled">
					                  <option>기타</option>
					                  <option>회원관련</option>
					                  <option>결제</option>
					                  <option>취소/환불</option>
					                  <option>클래스신청</option>
					                  <option>클래스등록</option>
					          </select>
				            <input type="text" name="keyword" class="boxTF" value="${keyword}" id="search3">
				            <input type="hidden" name="rows" value="${rows}">
				            <input type="hidden" name="page" value="${page}">
				            <button type="button" class="btn" onclick="searchList()">검색</button>
				      </td>
				      <td align="right" width="100">
				      	<c:if test="${sessionScope.member.userId=='admin'}">
				              <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/faq/created.do?';">글올리기</button>
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