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

				<tr>
					<td colspan="2" style="padding: 10px 5px" align="center">
						<img src="${pageContext.request.contextPath}/resource/images/event_ok.png" width="290" height="180" border="0"
						 		style="max-width: 100%; height: auto; resize: both;">
					</td> 
				</tr>
				
				<tr style="border-bottom: 1px solid #cccccc;">
					<td colspan="2" align="center" style="padding: 10px 5px; font-size: 16px;" valign="top" height="30">
							${dto.randomPoint}포인트 당첨!!&nbsp;${dto.userId}님의 현재 포인트는 ${dto.pointSum}포인트 입니다.
						<br>
						<span>
			      			<button type="button" class="eBtn" onclick="javascript:location.href='${pageContext.request.contextPath}/event/list.do';">목록 돌아가기</button>
				      	</span>
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