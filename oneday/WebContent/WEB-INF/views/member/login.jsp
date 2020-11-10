<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/login.css">

<script type="text/javascript">


function sendLogin() { //입력 검사 후 
    var f = document.loginForm;

	var str = f.userId.value;
    if(!str) {
        alert("아이디를 입력하세요. ");
        f.userId.focus();
        return;
    }

    str = f.userPwd.value;
    if(!str) {
        alert("패스워드를 입력하세요. ");
        f.userPwd.focus();
        return;
    }
    
    f.action = "${pageContext.request.contextPath}/member/login_ok.do";
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
		
		<!--login -->
       <div class="login">
        <div id="login_text">
            <h3>로그인</h3>
        </div>
        <form name="loginForm" method="post">
            <div class="login-box">
                <input type="text" class="id" placeholder="아이디를 입력해주세요." id="loginID" name="userId">
                <input type="password" class="pw" placeholder="비밀번호를 입력해주세요." id="loginPW" name="userPwd">

                    <div class="btn-submit">
                        <a onclick="sendLogin();">로그인</a>
                    </div>
                    
                    <div class="find">
                        <ul>
                            <li><a href="#">아이디 찾기</a></li>
                            <li><a href="#">비밀번호 찾기</a></li>
                            <li><a href="#">회원가입</a></li>
                        </ul>
                        
                        <div style="font-size: 15px; font-weight: 700; color: red; width: 100%; margin-top: 50px;" align="center">${message}</div>
                    </div>
                    
                    
            </div>
        </form>
    	</div>
    	
   	</div>
</div>


<!-- footer -->
<div class="footer_tem">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>




</body>
</html>