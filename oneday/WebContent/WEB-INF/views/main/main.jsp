<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>하루살이</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/login.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/oneday.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/slide.css">
</head>
<body>

	
<!-- header -->
<div class="header_tem">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<!--content  -->
<div class="body_tem">
	<div class="content_tem" style="margin: 0px auto 80px">
	
		<!-- 여기부터 자기가 만드는거임!  -->
		<div id="" style="width: 1200px; margin: 0 auto;">
		   <div id="slide">
			  <input type="radio" name="pos" id="pos1" checked>
			  <input type="radio" name="pos" id="pos2">
			  <input type="radio" name="pos" id="pos3">
			  <input type="radio" name="pos" id="pos4">
		   <ul>
			    <li></li>
			    <li></li>
			    <li></li>
			    <li></li>
		   </ul>
		  <p class="pos">
		    <label for="pos1"></label>
		    <label for="pos2"></label>
		    <label for="pos3"></label>
		    <label for="pos4"></label>
		  </p>
		 </div>
			
			<div align="center" style="width: 100%; margin-top: 50px;">
				<p style="font-size: 25px; font-weight: 700;">어떤 취미를 배워볼래?</p>
				
				<div>
					<table style="width: 930px; margin: 20px auto 0px; border-spacing: 0px;">
					
						<c:forEach var="dto" items="${list}" varStatus="status" end="5"> <%--varStatus : forEach 구문이 몇번째 실행했는지 알 수 있는 변수명 --%>
							  <c:if test="${status.index==0}">  <%--forEach구문 처음 시작했으면 --%>
							  	<tr>
							  </c:if>
							  <c:if test="${status.index!=0 && status.index%3==0}">  <%--처음이 아니고, 3으로 나눠떨어지면 줄바꿈 --%>
							  	<c:out value="</tr><tr>" escapeXml="false"/>
							  </c:if> 	
							  <td width="300" align="center">
							  	<div class="imgLayout"  onclick="article('${dto.classNum}')" style="height: 260px;">
							  		<img src="${pageContext.request.contextPath}/uploads/photo/${dto.classIFN}" width="290" height="180" border="0">
							  		<span class="addr" style="text-align: center;">${dto.classAddr}</span>
							  		<span class="name" style="text-align: center;">${dto.className}</span>
							  		
							  	</div>
							  </td>
						</c:forEach>
						 
						 
					</table>
				
				</div>
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