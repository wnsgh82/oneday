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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/event.css">

<script type="text/javascript">
function article(eNum){
	var url="${articleUrl}&eNum="+eNum;
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
<div class="body_tem">
	<div class="content_tem">

		<!-- 여기부터 자기가 만드는거임!  -->
		<section class="event-sec">
			<div class="inner">
			 <ul class="event-sel">
			 	<li class="event-sel-inner">
			 		<span style="width: 45%;">
			 			<a href="${pageContext.request.contextPath}/event/list.do">진행중인 이벤트</a>
			 		</span>
			 		<span style="width: 45%; float: right; border-bottom: 3px solid; 	font-weight: 600;">
			 			<a href="${pageContext.request.contextPath}/event_end/list.do">종료된 이벤트</a>
			 		</span>
			 	</li>
			 </ul>
			
				<table style="width: 930px; margin: 20px auto 0px; border-spacing: 0px;">
			<c:forEach var="dto" items="${list}" varStatus="status"> <%--varStatus : forEach 구문이 몇번째 실행했는지 알 수 있는 변수명 --%>
				  <c:if test="${status.index==0}">  <%--forEach구문 처음 시작했으면 --%>
				  	<tr>
				  </c:if>
				  <c:if test="${status.index!=0 && status.index%4==0}">  <%--처음이 아니고, 3으로 나눠떨어지면 줄바꿈 --%>
				  	<c:out value="</tr><tr>" escapeXml="false"/>
				  </c:if> 
				  	
				  <td width="300" align="center">
				  	<div class="imgLayout" onclick="article('${dto.eNum}')">
				  		<img src="${pageContext.request.contextPath}/uploads/photo/${dto.eIFN}" width="290" height="180" border="0">
				  		<span class="event-term">${dto.eStart}&nbsp;~&nbsp;${dto.eEnd}</span>
				  		<span class="eSubject">${dto.eSubject}</span>
				  		<div class="article-bottom-wrap">
					  		<c:if test="${dto.eEnabled<=0}">
					  			<span class="sticker blinking">진행 중</span>
					  		</c:if>
						  	<c:if test="${dto.eEnabled>0}">
						  		<span class="sticker-end">종료</span>
						  	</c:if>
						  	
						  	<span class="eHitCount">조회수 : ${dto.eHitCount}</span>
				  		</div>
				  		<%--여기다 출력할 거  --%>
				  	</div>
				  </td>
			</c:forEach>
			<c:set var="n" value="${list.size()}"/>  <%--list.size() : 리스트의 갯수 --%>
			<c:if test="${n>0 && n%3!=0}">
				<c:forEach var="i" begin="${n%3+1}" end="3"> 
					<td width="300">   <%--이미지 없는 빈 공백칸  --%>
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
				        ${dataCount!=0?paging:"등록된 게시물이 없습니다."}
					</td>
				   </tr>
				</table>
				
				<table style="width: 100%; margin-top: 10px; border-spacing: 0;">
				   <tr height="40">
				      <td align="center" width="100">
				      <%--관리자만 이벤트 등록 가능  --%>
				      <c:if test="${sessionScope.member.userId=='admin'}">
				          <button type="button" class="eBtn" onclick="javascript:location.href='${pageContext.request.contextPath}/event/created.do';">이벤트 등록</button>
				      </c:if>  
				      </td>
				   </tr>
				</table>
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