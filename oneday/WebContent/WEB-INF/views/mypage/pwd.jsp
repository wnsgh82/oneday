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

.boxTF {
    border:1px solid #999999;
    padding:3px 5px 5px;
    border-radius:4px;
    background-color:#ffffff;
    font-family:"Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
}
</style>

<script type="text/javascript">

function send(){
	var f=document.forms[0];
	
	var str;
	str=f.userPwd.value.trim();
	if(! str){
		alert("비밀번호를 입력하세요.");
		f.userPwd.focus();
		return;
	}
	
	if(confirm("회원을 탈퇴하시겠습니까?")){
		f.action="${pageContext.request.contextPath}/mypage/memberDelete.do";
		
		f.submit();
	}

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
	<div class="content_tem" >
		
		<!-- 여기부터 자기가 만드는거임!  -->
    <div id="mypage">
        <div id="my_left">
            <div id="myl_title">
                <h3>마이페이지</h3>
            </div>
            <ul id="myl_list">
            	<li><a href="${pageContext.request.contextPath}/mypage/mypageMain.do"> 나의 클래스</a></li>
            	<li><a href="${pageContext.request.contextPath}/mypage/classList.do"> 수강생 관리</a></li>
                <li><a href="${pageContext.request.contextPath}/mypage/memberUpdate.do"> 회원정보수정</a></li>
                <li><a href="${pageContext.request.contextPath}/mypage/pwd.do"> 회원 탈퇴  </a></li>
                <li><a href=""> 넣으세요 </a></li>
            </ul>
        </div>


        <div id="my_right" align="left" style="margin-top: 0">
            <div id="myr_profile" style="width: 500px; height: 300px; margin-left: 100px; margin-top: 50px;" align="left">
                <div id="profile_box" align="center">
                    
                    <div id="informantion" style="width: 100%; margin: 0" align="center" >
                    	<p style="margin-bottom: 10px;">본인 확인을 위해 비밀번호를 입력해주세요.</p>
                    	<form name="sendForm" method="post">
                   		<table style="border-collapse: collapse; border-spacing: 0; width: 300px">
                   			<tr height="40">
	                   			<td width="30%">아이디</td>
	                   			<td align="left">${sessionScope.member.userId}</td>
                   			</tr>
                   			<tr height="40">
	                   			<td width="30%">패스워드</td>
	                   			<td align="left"><input type="password" name="userPwd" class="boxTF"></td>
                   			</tr>
                   			<tr>
                   				<td colspan="2" align="center">
                   					<input type="hidden" name="userId" value="${sessionScope.member.userId}">
                   					
                   					<button type="button" class="btn" onclick="send()"> 확인 </button>
                   					<button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/mypage/mypageMain.do';"> 취소 </button>
                   				</td>
                   			</tr>
                   		</table>
                   		</form>
                    </div>

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