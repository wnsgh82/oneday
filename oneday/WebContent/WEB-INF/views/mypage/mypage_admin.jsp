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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/mypage.css">
<style type="text/css">
.faqBtn {
	float: right;
	width: 80px;
	height: 30px;
	background: #f1f1f1;
    border-radius: 9px;
    border: 1px solid #ccc;
    margin-top: 5px;
    margin-right: 5px;
}
</style>
<script type="text/javascript">
function deleteMemberA(userId) {
	if(confirm("강제 탈퇴처리하시겠습니까?")) {
		var url="${pageContext.request.contextPath}/mypage/deleteMemberA.do?userId="+userId+"&userEnabled="+${userEnabled};
		location.href=url;
	}
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
	<form name="adminmemberForm">
    <div id="mypage">
        <div id="my_left">
            <div id="myl_title">
                <h3>관리자페이지</h3>
            </div>
            <ul id="myl_list">
                <li><a href="${pageContext.request.contextPath}/mypage/mypageMain.do?userEnabled=100"> 강사회원관리</a></li>
                <li><a href="${pageContext.request.contextPath}/mypage/mypageMain.do?userEnabled=1"> 수강생회원관리</a></li>
                <li><a href="${pageContext.request.contextPath}/mypage/classAdmin.do?"> 클래스삭제</a></li>
            </ul>
        </div>


        <div id="my_right">
            <div id="myr_profile">
                <div id="profile_box">
                    <div id="imageee">
                        <img src="${pageContext.request.contextPath}/resource/images/mypage.png" alt="">
                    </div>
                    <div id="informantion">
                        <p style="font-size: 13px;">관리자</p>
                        <p style="font-size: 20px; font-weight: 600;"> ${sessionScope.member.userName} (${sessionScope.member.userId})</p>
                    </div>

                </div>
            </div>
            <div id="myr_title">
                <h3>회원정보(강사)</h3>
            </div>
            <div id="myr_table">
                <div id=table_title>
                    <!-- 이거 내용 맞춰서 알아서 수정해서 쓰세유 -->
                    <ul>
                        <li style="width: 6%;"><p>&nbsp;</p></li>
                        <li style="width: 13%; text-align: left; padding-left: 10px;"><p>아이디</p></li>
                        <li style="width: 13%;"><p>이름</p></li>
                        <li style="width: 30%;"><p>전화번호</p></li>
                        <li style="width: 30%;"><p>이메일</p></li>
                        <li style="width: 14%;"><p>&nbsp;</p></li>
                    </ul>
                </div>
                <div id="table_content">
                      <!-- 여기 포문돌려서 값 불러왕 -->
                      <c:forEach var="dto" items="${list}">
                        <ul style="background: white; border-bottom: 1px solid #e0e0e0;"> 
                            <li style="width: 6%;"><p>&nbsp;</p></li>
                            <li style="width: 13%; text-align: left; padding-left: 10px;"><p>${dto.userId }</p></li>
                            <li style="width: 13%;"><p>${dto.userName }</p></li>
                            <li style="width: 30%;"><p>${dto.userTel }</p></li>
                            <li style="width: 30%;"><p>${dto.userEmail }</p></li>
                            <li style="width: 14%;"><button type="button" class="faqBtn" onclick="deleteMemberA('${dto.userId}');">강제탈퇴</button></li>
                        </ul>
                      </c:forEach>
                      
                </div>
            </div>

        </div>
    </div>
        </form>
 </div>
</div>

<!-- footer -->
<div class="footer_tem">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>




</body>
</html>