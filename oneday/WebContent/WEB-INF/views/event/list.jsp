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

<style type="text/css">
.imgLayout {
	width: 290px;
	height: 330px;
	padding: 1px 1px 10px;
	margin: 5px;
	border: 1px solid #999;
	cursor: pointer;
}

.imgLayout img:hover {
	opacity: 80%;
}

.name{
	width: 270px;
	height: 25px;
	line-height: 25px;
	margin: 1px auto;
	display: inline-block;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	cursor: pointer; 
	text-align: left;
	font-size: 14px;
	font-weight: 600;
}

.classBtn{
	width: 300px;
    background: #FC3C55;
    margin: 30px 0 0;
    cursor: pointer;
    border-radius: 10px 10px 10px 10px;
    border: 0;

    text-align: center;
    color: #fff;
    line-height: 50px;
    font-size: 14px;
    font-weight: normal;
    height: 50px;
	 
}

.sticker {
    font-size: 11px;
    display: inline-block;
    padding: 0 5px;
    color: #FC3C55;
    margin: 10px 10px 0;
    box-sizing: border-box;
    border: 1px solid #FC3C55;
    font-weight: 600;
    float: left;
}
</style>

<script type="text/javascript">
function article(eNum){
	var url="${articleUrl}&num="+eNum;
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
		<div class="title-area">
            <h3>&nbsp;&nbsp;&nbsp;&nbsp;★이벤트★</h3>
		</div>
		<!-- 여기부터 자기가 만드는거임!  -->
		<div>
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
			  		<span class="name">${dto.eName}</span>
			  		<span class="sticker">진행 중</span>
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
			      <%--관리자만 이벤트 등록 가능  --%>
			      <c:if test="${sessionScope.member.userId=='admin'}">
			          <button type="button" class="classBtn" onclick="javascript:location.href='${pageContext.request.contextPath}/event/created.do';">이벤트 등록</button>
			      </c:if>  
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