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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/mypage.css">

<style type="text/css">

a{
	text-decoration: none;
	color: #000000;
}

a:hover{
	text-decoration: none;
	color: tomato;
	cursor: pointer;
}


</style>

<script type="text/javascript">

function send(){
	var f=document.forms[0];
	
	f.action="${pageContext.request.contextPath}/mypage/stdlist.do";
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
	<div class="content_tem">
		
		<!-- 여기부터 자기가 만드는거임!  -->
    <div id="mypage">
        <div id="my_left">
            <div id="myl_title">
                <h3>마이페이지</h3>
            </div>
            <ul id="myl_list">
            	<li><a href="${pageContext.request.contextPath}/mypage/mypageMain.do"> 나의 클래스</a></li>
            	<li><a href="${pageContext.request.contextPath}/mypage/stdlist.do"> 수강생 관리</a></li>
                <li><a href="${pageContext.request.contextPath}/mypage/memberUpdate.do"> 회원정보수정</a></li>
                <li><a href="${pageContext.request.contextPath}/mypage/pwd.do"> 회원 탈퇴  </a></li>
                <li><a href=""> 넣으세요 </a></li>
            </ul>
        </div>


        <div id="my_right">
        	 <div class="title-area"  style="width: 850px;" >
            	<h3>&nbsp;&nbsp;&nbsp;&nbsp;수강생 관리</h3>
			</div>	
			
			<div id="myr_table">
                <div id=table_title>
                    <!-- 이거 내용 맞춰서 알아서 수정해서 쓰세유 -->
                    <ul>
                        <li style="width: 10%;"><p>&nbsp;</p></li>
                        <li style="width: 50%; text-align: left; padding-left: 10px;"><p>클래스정보</p></li>
                        <li style="width: 20%;"><p>수강기간</p></li>
                        <li style="width: 20%;"><p>진행상태</p></li>
                    </ul>
                </div>
                <div id="table_content">
                      <!-- 여기 포문돌려서 값 불러왕 -->
                      <c:forEach var="dto" items="${list}">
                        <ul style="background: white; border-bottom: 1px solid #e0e0e0;">
                            <li style="width: 10%;"><p>&nbsp;</p></li>
                            <li style="width: 50%; text-align: left; padding-left: 10px;">
								<a href="${pageContext.request.contextPath}/mypage/stdlist.do?classNum=${dto.classNum}">${dto.className}</a>
                            </li>
                            <li style="width: 20%;"><p>${dto.classDate}</p></li>
                            <li style="width: 20%; color: tomato;"><p>${dto.classEnabled}</p></li>
                        </ul>
                      </c:forEach>
                </div>
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