<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/login.css">

<style type="text/css">
.imgLayout {
	width: 190px;
	height: 205px;
	padding: 10px 5px 10px;
	margin: 5px;
	border: 1px solid #dad9ff;
	cursor: pointer;
}

.subject{
	width: 180px;
	height: 25px;
	line-height: 25px;
	margin: 5px auto;
	border-top: 1px solid #dad9ff;
	display: inline-block;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	cursor: pointer; 
}

</style>

<script type="text/javascript">
function article(num){
	var url="${articleUrl}&num="+num;
	location.href=url;
}
</script>

</head>
<body>

<!-- header -->
<div class="header_tem">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<!--content  -->
<div class="body_tem" style="border-top: 1px solid; ">
	<div class="content_" style="width: 960px; min-height: 500px; margin: 0 auto;" >
	
		<!-- 여기부터 자기가 만드는거임!  -->
		<div>
			<table style="width: 630px; margin: 20px auto 0px; border-spacing: 0px;">
		<c:forEach var="dto" items="${list}" varStatus="status"> <%--varStatus : forEach 구문이 몇번째 실행했는지 알 수 있는 변수명 --%>
			  <c:if test="${status.index==0}">  <%--forEach구문 처음 시작했으면 --%>
			  	<tr>
			  </c:if>
			  <c:if test="${status.index!=0 && status.index%4==0}">  <%--처음이 아니고, 3으로 나눠떨어지면 줄바꿈 --%>
			  	<c:out value="</tr><tr>" escapeXml="false"/>
			  </c:if> 	
			  <td width="210" align="center">
			  	<div class="imgLayout" onclick="article('${dto.classNum}')">
			  		<img src="${pageContext.request.contextPath}/uploads/photo/${dto.classIFN}" width="180" height="180" border="0">
			  		<span class="subject">${dto.className}</span>
			  		<%--여기다 출력할 거  --%>
			  	</div>
			  </td>
		</c:forEach>
		<c:set var="n" value="${list.size()}"/>  <%--list.size() : 리스트의 갯수 --%>
		<c:if test="${n>0 && n%4!=0}">
			<c:forEach var="i" begin="${n%4+1}" end="4"> 
				<td width="210">   <%--이미지 없는 빈 공백칸  --%>
					<div class="imgLayout" style="cursor: default;">&nbsp;</div>
				</td>
			</c:forEach>
		</c:if>
		<c:if test="${n!=0}">
			<c:out value="</tr>" escapeXml="false"/>
		</c:if>
			</table>
			 
			<table style="width: 100%; border-spacing: 0;">
			   <tr height="35">
				<td align="center">
			        ${dataCount==0 ? "등록된 게시물이 없습니다." : paging }
				</td>
			   </tr>
			</table>
			
			<table style="width: 100%; margin-top: 10px; border-spacing: 0;">
			   <tr height="40">
			      <td align="left" width="100">
			          &nbsp;
			      </td>
			      <td align="center">
			          &nbsp;
			      </td>
			      <td align="right" width="100">
			          <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/oneday/created.do';">사진올리기</button>
			      </td>
			   </tr>
			</table>
		</div>
	    
	</div>
</div>

<!-- footer -->
<div class="footer_tem">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>


</body>
</html>