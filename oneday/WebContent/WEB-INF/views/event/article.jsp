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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/event.css">

<script type="text/javascript">
function deleteBoard(eNum) {
	<c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
		if(confirm("게시물을 삭제 하시겠습니까 ?")) {
			var url="${pageContext.request.contextPath}/event/delete.do?eNum="+eNum+"&page=${page}";
			location.href=url;
		}
	</c:if>

	<c:if test="${sessionScope.member.userId != dto.userId && sessionScope.member.userId !='admin'}">
		alert("게시물을 삭제할 수 없습니다.");
	</c:if>
}

function eventApply(){
/* 	<c:if test="${memberScope.member.userId==null}">
		alert("로그인 후 이용하실 수 있습니다.");
		location.href="${pageContext.request.contextPath}/member/login.do";
	</c:if>
	 */
	var f= document.stdsendf;
	
	f.action="${pageContext.request.contextPath}/event_apply/created.do";
	f.submit();
}
</script>
</head>
<body>


<!-- header -->
<div class="header_tem">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<!--content  -->
<div class="body_tem">
	<div class="content_tem" >
		<div class="title-area">
            <h3>&nbsp;&nbsp;&nbsp;&nbsp;★이벤트★</h3>
		</div>
		
		<!-- 여기부터 자기가 만드는거임!  -->
		<div>
			<table style="width: 930px; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
			<tr height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="center" style="font-weight: 700;">
				   ${dto.eSubject}
			    </td>
			</tr>
			
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td width="50%" align="left" style="padding-left: 5px;">
			       관리자
			    </td>
			    <td width="50%" align="right" style="padding-right: 5px;">
			        ${dto.eCreated}
			    </td>
			</tr>
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td width="50%" align="left" style="padding-left: 5px;">    
			    	  이벤트 기간  : ${dto.eStart}&nbsp;~&nbsp;${dto.eEnd}
			    </td>
			</tr>
			
			<tr>
				<td colspan="2" style="padding: 10px 5px" align="center">
					<img src="${pageContext.request.contextPath}/uploads/photo/${dto.eIFN}"
					 		style="max-width: 100%; height: auto; resize: both;">
				</td> 
			</tr>
			
			<tr style="border-bottom: 1px solid #cccccc;">
			  <td colspan="2" align="center" style="padding: 10px 5px;" valign="top" height="30">
			      ${dto.eContent}
			      <br>
			      <c:if test="${sessionScope.member.userId==null}">
			      	<input type="hidden" name="eNum" value="${dto.eNum}">
			      	<input type="hidden" name="page" value="${page}">
			      </c:if>
			      <form name="stdsendf" method="post">
			      <span>
			      	<input type="hidden" name="eSubject" value="${dto.eSubject}">
			      	<input type="hidden" name="eNum" value="${dto.eNum}">
			      	<input type="hidden" name="eStart" value="${dto.eStart}">
			      	<input type="hidden" name="eEnd" value="${dto.eEnd}">
			      	<button type="button" class="eBtn" onclick="eventApply();">이벤트 참가</button>
			      </span>
			      </form>
			   </td>
			   
			</tr>
			
			<tr height="45">
			    <td>
			    	 <%--관리자만 이벤트 수정, 삭제 가능--%>
			    	 <c:if test="${sessionScope.member.userId=='admin'}">
			          <button type="button" class="eBtn3" onclick="javascript:location.href='${pageContext.request.contextPath}/event/update.do?eNum=${dto.eNum}&page=${page}';">수정</button>
			          <button type="button" class="eBtn3" onclick="deleteBoard('${dto.eNum}');">삭제</button>
			         </c:if>
			          
			    </td>
			
			    <td align="right">
			        <button type="button" class="eBtn3" onclick="javascript:location.href='${pageContext.request.contextPath}/event/list.do?page=${page}';">목록</button>
			    </td>
			</tr>
			</table>
			
			<%--일대일 댓글문의 해볼 것  --%>
			<div align="center">
		<form name="commentForm" method="post">
			<table style="width: 930px; margin-top: 20px; border-spacing: 0; border-collapse: collapse;">
				<tr height="40" style="border-bottom: 1px solid #ccc; border-top: 1px solid #ccc;">
					<td style="width: 100px; " align= "center" bgcolor="#eee">작성자</td>
					<td style="padding-left: 10px;" >
						관리자
					</td>
				</tr>
				<tr style="border-bottom: 1px solid #ccc;">
					<td style="width: 100px;" align= "center" bgcolor="#eee" valign="top">내&nbsp;용</td>
					<td valign="top" style="padding:5px 0px 5px 10px;"> 
		        		<textarea name="content" cols="72" class="boxTA" style="width:97%; height: 70px;"></textarea>
		     		 </td>
				</tr>
				<tr height="40">
					<td colspan="2" align="right">
						<button type="button" class="eBtn3" onclick="send();">등록하기</button>
						<button type="reset" class="eBtn3">다시입력</button>
					</td>
				</tr>
			</table>
		</form>
			</div>
		</div>
	    
	</div>
</div>

<!-- footer -->
<div class="footer_tem">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

</body>
</html>