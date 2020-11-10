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

<style type="text/css">

.classBtn{
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


</style>

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
    
    f.action = "${pageContext.request.contextPath}/member/pwdForm_ok.do";
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
            <h3>비밀번호 찾기</h3>
        </div>
        <form name="loginForm" method="post">
            <div class="login-box">
            	<div style="height: 100px; line-height: 100px;" align="center">
               	 	<p> ${dto.userName} 님의 패스워드는 ${userPwd}입니다.</p>
                 </div>
                    <div class="btn-submit">
                        <a href="${pageContext.request.contextPath}/member/login.do">확인</a>
                    </div>
       
                    
                    <div class="find">
                        
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