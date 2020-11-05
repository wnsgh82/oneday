<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/selectlog.css">
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

	<div class="signbox">
        
        <h3 style="text-align: left;">&nbsp;</h3>
        <h3 style="text-align: left;">&nbsp;</h3>
        <h3 style="text-align: left;">&nbsp;</h3>
        <h3 style="text-align: left;">&nbsp;</h3>
        <div class="userbox">
            <div class="userbox1" style="">
            <div class="signimg">
                <img style="float: left;" src="${pageContext.request.contextPath}/resource/images/sign_icon.png">
                <div style="float: left; margin-left: 50px; margin-top: 50px;"><h3>개인 회원가입</h3></div>
            </div>
            </div>	
                <div style="clear :both;"><button onclick="location.href='${pageContext.request.contextPath}/member/member.do';" class="signbt">일반 회원가입 </button>
                <div class="signtext">
                    <p style="font-weight: 700; text-align: left;">※ 개인 회원이란?</p>
                    <P style="color: #252525; font-weight: 600; text-align: left;">클래스의 교육을 듣기위한 학생회원</P>
                </div>
            </div>		
            
        </div>
        <div style="display: inline-block; width: 40px;">
        </div>
        <div class="trbox">
            <div class="userbox1">
                <div class="signimg">
                    <img style="float: left;" src="${pageContext.request.contextPath}/resource/images/tr_icon.png">
                    <div style="float: left;  margin-left: 50px; margin-top: 50px;"><h3>강사 회원가입</h3></div>
                </div>
                </div>	
                    <div style="clear :both;"><button onclick="location.href='${pageContext.request.contextPath}/member/member.do';" class="signbt">강사 회원가입 </button>
                    <div class="signtext">
                        <p style="font-weight: 700; text-align: left;">※ 강사 회원이란?</p>
                        <P style="color: #252525; font-weight: 600; text-align: left;">클래스 교육의 전문적인 지식을 가지고 있으며, 교육 진행이 목적인 회원</P>
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