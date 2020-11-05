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
			<table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
			<tr height="35" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;">
			    <td colspan="2" align="center">
				   ${dto.className}
			    </td>
			</tr>
			
			<tr height="35" style="border-bottom: 1px solid #cccccc;">
			    <td width="50%" align="left" style="padding-left: 5px;">
			       이름 : ${dto.userName}
			    </td>
			    <td width="50%" align="right" style="padding-right: 5px;">
			        ${dto.classCreated}
			    </td>
			</tr>
			
			<tr>
				<td colspan="2" style="padding: 10px 5px">
					<img src="${pageContext.request.contextPath}/uploads/photo/${dto.classIFN}"
					 		style="max-width: 100%; height: auto; resize: both;">
				</td>
			</tr>
			
			<tr style="border-bottom: 1px solid #cccccc;">
			  <td colspan="2" align="left" style="padding: 10px 5px;" valign="top" height="30">
			      ${dto.classContent}
			   </td>
			</tr>
			
			<tr height="45">
			    <td>
			    	 <c:if test="${sessionScope.member.userId==dto.userId}">
			          <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/oneday/update.do?num=${dto.classNum}&page=${page}';">수정</button>
			         </c:if>
			          <button type="button" class="btn" onclick="deleteBoard('${dto.classNum}');">삭제</button>
			    </td>
			
			    <td align="right">
			        <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/oneday/list.do?page=${page}';">리스트</button>
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