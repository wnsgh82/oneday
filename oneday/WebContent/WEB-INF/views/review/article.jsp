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
            <h3>&nbsp;&nbsp;&nbsp;&nbsp;수강 후기</h3>
		</div>
		
		<!-- 여기부터 자기가 만드는거임!  -->
		<div>
			<table style="width: 930px; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
			<tr height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="center" style="font-weight: 700;">
				   평정 :<c:forEach var="1" begin="1" end="${dto.rvScore}">
				   ★&nbsp;
				   </c:forEach>
			    </td>

			</tr>
			<tr height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="center" style="font-weight: 700;">
				   ${dto.rvClassName}
			    </td>

			</tr>
			
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td width="50%" align="left" style="padding-left: 5px;">
			      작성자 : ${dto.userId}
			    </td>
			    <td width="50%" align="right" style="padding-right: 5px;">
			        ${dto.rvCreated}&nbsp;&nbsp;&nbsp;&nbsp; 조회수 : ${dto.rvHitcount}
			    </td>

			</tr>
			
			<tr>
				<td colspan="2" style="padding: 10px 5px" align="center">
				</td>
			</tr>
			
			<tr style="border-bottom: 1px solid #cccccc;">
			  <td colspan="2" align="center" style="padding: 10px 5px;" valign="top" height="30">
			      ${dto.rvContent}
			   </td>
			</tr>
			
			<tr height="45">
			    <td>
			    	 <%--관리자랑 강사만 클래스 등록 가능하니까 굳이 userEnabled로  조건 걸지 않음--%>
			    	 <c:if test="${sessionScope.member.userId=='admin' || sessionScope.member.userId==dto.userId}">
			          <button type="button" class="classBtn3" onclick="javascript:location.href='${pageContext.request.contextPath}/review/update.do?rvNum=${dto.rvNum}&page=${page}';">수정</button>
			          <button type="button" class="classBtn3" onclick="deleteBoard('${dto.rvNum}');">삭제</button>
			         </c:if>
			          
			    </td>
			
			    <td align="right">
			        <button type="button" class="classBtn3" onclick="javascript:location.href='${pageContext.request.contextPath}/review/list.do?page=${page}';">리스트</button>
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