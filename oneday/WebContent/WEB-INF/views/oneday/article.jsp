<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>하루살이</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/login.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/oneday.css">

<script type="text/javascript">
function deleteBoard(classNum) {
	<c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
		if(confirm("게시물을 삭제 하시겠습니까 ?")) {
			var url="${pageContext.request.contextPath}/oneday/delete.do?classNum="+classNum+"&page=${page}";
			location.href=url;
		}
	</c:if>

	<c:if test="${sessionScope.member.userId != dto.userId && sessionScope.member.userId !='admin'}">
		alert("게시물을 삭제할 수 없습니다.");
	</c:if>
}

function apply(){
	var f= document.stdsendf;
	
	<c:if test="${empty sessionScope.member}">
		alert("로그인 후 이용하실 수 있습니다.");
		location.href="${pageContext.request.contextPath}/member/login.do";
	</c:if>
	
	<c:if test="${not empty sessionScope.member}">
		f.action="${pageContext.request.contextPath}/std/created.do";
	</c:if>
	
	f.submit();
}

function noapply(){
	var f= document.stdsendf;
	
	<%-- 기간 종료  --%> 
	<c:if test="${dto.classEnabled>0}">
		alert("진행 중인 클래스는 신청하실 수 없습니다.");
		f.action="${pageContext.request.contextPath}/oneday/list.do?page=${page}";
		
	</c:if>
	
	<%-- 정원 초과  --%> 
	<c:if test="${stdCount+1>dto.classCount}">
		alert("이미 모집이 마감된 클래스입니다.");
		f.action="${pageContext.request.contextPath}/oneday/list.do?page=${page}";
		
	</c:if>
	f.submit();
	
}

function send(){
	alert("로그인 후 이용하실 수 있습니다.");
	location.href="${pageContext.request.contextPath}/member/login.do";
	
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
            <h3>&nbsp;&nbsp;&nbsp;&nbsp;원데이클래스</h3>
		</div>
		
		<!-- 여기부터 자기가 만드는거임!  -->
		<div>
			<table style="width: 930px; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
			<tr height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="center" style="font-weight: 700;">
				   ${dto.className}
			    </td>
			</tr>
			
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td width="50%" align="left" style="padding-left: 5px;">
			       강사명 : ${dto.userName}
			    </td>
			    <td width="50%" align="right" style="padding-right: 5px;">
			        ${dto.classCreated}
			    </td>
			</tr>
			<tr height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="left" style="padding-left: 5px;">
			      클래스 장소  : ${dto.classAddr}
			    </td>
			</tr>
			<tr height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="left" style="padding-left: 5px;">
			      클래스 가격  : ${dto.classPrice}
			    </td>
			</tr>
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td width="50%" align="left" style="padding-left: 5px;">
			      클래스 기간  : ${dto.classStart}&nbsp;-&nbsp;${dto.classEnd}
			    </td>
			    <td width="50%" align="right" style="padding-right: 5px;">
			      클래스 정원  : <span style="color: #FC3C55;">${dto.classCount}명</span>
			    </td>
			</tr>
			
			<tr>
				<td colspan="2" style="padding: 10px 5px" align="center">
					<img src="${pageContext.request.contextPath}/uploads/photo/${dto.classIFN}"
					 		style="max-width: 100%; height: auto; resize: both;">
				</td> 
			</tr>
			
			<tr style="border-bottom: 1px solid #cccccc;">
			  <td colspan="2" align="center" style="padding: 10px 5px;" valign="top" height="30">
			      ${dto.classContent}
			      <br>
			      <c:if test="${sessionScope.member.userId==null }">
			      	<input type="hidden" name="classNum" value="${dto.classNum}">
			      	<input type="hidden" name="page" value="${page}">
			      </c:if>
			      <form name="stdsendf" method="post">
			      <span>
			     	<input type="hidden" name="userName" value="${dto.userName}">
			      	<input type="hidden" name="className" value="${dto.className}">
			      	<input type="hidden" name="classStart" value="${dto.classStart}">
			      	<input type="hidden" name="classEnd" value="${dto.classEnd}">
			      	<input type="hidden" name="classNum" value="${dto.classNum}">
			      	<input type="hidden" name="classAddr" value="${dto.classAddr}">
			      	<input type="hidden" name="classPrice" value="${dto.classPrice}">
			      	<input type="hidden" name="classCount" value="${dto.classCount}">
			      	
			      	<%-- 정원 여유 & 시작날짜 전 = 등록 버튼  --%> 
			      	<c:if test="${dto.classEnabled<0 && stdCount+1<=dto.classCount}">  
			      		<button type="button" class="classBtn" onclick="apply();">클래스 등록 신청</button>
			      	</c:if>


			      	<c:if test="${dto.classEnabled>=0 || stdCount+1>dto.classCount}">  
			      		<input type="hidden" name="page" value="${page}">
			      		<button type="button" class="classBtn" onclick="noapply();" style="background-color: #999">진행 중인 클래스</button>
			      	</c:if>
			      	
			      	<%-- 정원 초과 = 마감버튼  --%> 
			      	<c:if test="${stdCount+1>dto.classCount && dto.classEnabled<0 }">  
			      		<input type="hidden" name="page" value="${page}">
			      		<button type="button" class="classBtn" onclick="noapply();">클래스 등록 마감</button>
			      	</c:if>
			      	

			      </span>
			      </form>
			   </td>
			   
			</tr>
			
			<tr height="45">
			    <td>
			    	 <%--관리자랑 강사만 클래스 등록 가능하니까 굳이 userEnabled로  조건 걸지 않음--%>
			    	 <c:if test="${sessionScope.member.userId=='admin' || sessionScope.member.userId==dto.userId}">
			          <input type="hidden" name="page" value="${page}">
			          <button type="button" class="classBtn3" onclick="javascript:location.href='${pageContext.request.contextPath}/oneday/update.do?classNum=${dto.classNum}';">수정</button>
			          <button type="button" class="classBtn3" onclick="deleteBoard('${dto.classNum}');">삭제</button>
			         </c:if>
			          
			    </td>
			
			    <td align="right">
			        <button type="button" class="classBtn3" onclick="javascript:location.href='${pageContext.request.contextPath}/oneday/list.do?page=${page}';">리스트</button>
			    </td>
			</tr>
			</table>
			
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