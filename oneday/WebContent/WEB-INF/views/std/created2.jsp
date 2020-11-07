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


</script>

</head>
<body>

<!-- header -->
<div class="header_tem">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<!--content  -->
<div class="body_tem">
	<div class="content_tem" style="text-align: center;" >
		<div class="title-area">
            <h3>&nbsp;&nbsp;&nbsp;&nbsp;원데이클래스</h3>
		</div>
		
		<img src="${pageContext.request.contextPath}/resource/images/iiii.png" 
			style=" width: 800px; height: 500px; margin: 10px auto;">

		
	    
	</div>
</div>

<!-- footer -->
<div class="footer_tem">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>


</body>
</html>