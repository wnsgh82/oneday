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
}

.btn{
    background: #000000;
    margin: 30px 0 0;
    cursor: pointer;
    border-radius: 4px;
    padding: 5px 10px 5px;
    border: 0;
    text-align: center;
    color: #fff;
    font-size: 12px;
    font-weight: normal; 
}

.btn:hover{
	background: #40434A;
}
</style>

<script type="text/javascript">

function send(){
	var f=document.forms[0];
	
	f.action="${pageContext.request.contextPath}/mypage/classList.do";
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
                <li><a href=""> 별로 알아서  </a></li>
                <li><a href=""> 넣으세요 </a></li>
            </ul>
        </div>


        <div id="my_right">
            <div id="myr_profile">
                <div id="profile_box">
                    <div id="informantion" style="padding-left: 50px;">
                        <p style="font-size: 13px;"> 강사  &nbsp;| &nbsp; ${sessionScope.member.userName}</p>
                        <p style="font-size: 20px; font-weight: 600;"> ${dto.className}</p>
                    </div>

                </div>
            </div>
            <div id="myr_title">
                <h3>수강생 관리</h3>
            </div>
            <div id="myr_table">
                <div id=table_title>
                    <!-- 이거 내용 맞춰서 알아서 수정해서 쓰세유 -->
                    <ul>
                        <li style="width: 25%;"><p>아이디</p></li>
                        <li style="width: 25%; text-align: left; padding-left: 10px;"><p>이름</p></li>
                        <li style="width: 35%;"><p>이메일</p></li>
                        <li style="width: 15%;"><p>수강상태</p></li>
                    </ul>
                </div>
                <div id="table_content">
                      <!-- 여기 포문돌려서 값 불러왕 -->
                      <c:forEach var="dto" items="${list}">
                        <ul style="background: white; border-bottom: 1px solid #e0e0e0;">
                            <li style="width: 25%;"><p>${dto.stdId}</p></li>
                            <li style="width: 25%; text-align: left; padding-left: 10px;"><p>${dto.stdName}</p></li>
                            <li style="width: 35%;"><p>${dto.stdEmail}</p></li>
                            <li style="width: 15%; color: tomato;"><p>${dto.stdEnabled}</p></li>
                        </ul>
                        
                      <form name="sendForm" method="post">
                      	<input type="hidden" name="classNum" value="${dto.classNum}">
                      	<input type="hidden" name="trName" value="${sessionScope.member.userName}"> 
                      	
                      	<button onclick="send();" style="margin-top: 100px;" class="btn">돌아가기</button>
                	  </form>
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