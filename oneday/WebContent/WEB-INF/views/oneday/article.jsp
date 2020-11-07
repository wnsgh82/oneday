<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
/* 	<c:if test="${memberScope.member.userId==null}">
		alert("로그인 후 이용하실 수 있습니다.");
		location.href="${pageContext.request.contextPath}/member/login.do";
	</c:if>
	 */
	//여기서부터 ~ 신청~ 짜시면 됩니다~
	var f= document.stdsendf;
	
	f.action="${pageContext.request.contextPath}/std/created.do";
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
			      	
			      	<button type="button" class="classBtn" onclick="apply();">클래스 등록 신청</button>
			      </span>
			      </form>
			   </td>
			   
			</tr>
			
			<tr height="45">
			    <td>
			    	 <%--관리자랑 강사만 클래스 등록 가능하니까 굳이 userEnabled로  조건 걸지 않음--%>
			    	 <c:if test="${sessionScope.member.userId=='admin' || sessionScope.member.userId==dto.userId}">
			          <button type="button" class="classBtn3" onclick="javascript:location.href='${pageContext.request.contextPath}/oneday/update.do?classNum=${dto.classNum}&page=${page}';">수정</button>
			          <button type="button" class="classBtn3" onclick="deleteBoard('${dto.classNum}');">삭제</button>
			         </c:if>
			          
			    </td>
			
			    <td align="right">
			        <button type="button" class="classBtn3" onclick="javascript:location.href='${pageContext.request.contextPath}/oneday/list.do?page=${page}';">리스트</button>
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
						${dto.userName}
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
						<button type="button" class="classBtn3" onclick="send();">등록하기</button>
						<button type="reset" class="classBtn3">다시입력</button>
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