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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/mypage.css">
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
                <li><a href=""> 회원정보수정</a></li>
                <li><a href=""> 여기  수강내역 이런거</a></li>
                <li><a href=""> 이런거 수강생강사</a></li>
                <li><a href=""> 별로 알아서  </a></li>
                <li><a href=""> 넣으세요 </a></li>
            </ul>
        </div>


        <div id="my_right">
            <div id="myr_profile">
                <div id="profile_box">
                    <div id="imageee">
                        <img src="${pageContext.request.contextPath}/resource/images/mypage.png" alt="">
                    </div>
                    <div id="informantion">
                        <p style="font-size: 13px;">여기 관리자인지 수강생/강사인지</p>
                        <p style="font-size: 20px; font-weight: 600;"> 이름 (아이디)</p>
                    </div>

                </div>
            </div>
            <div id="myr_title">
                <h3>타이틀 적으삼</h3>
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
                        <ul>
                            <li style="width: 10%;"><p>&nbsp;</p></li>
                            <li style="width: 50%; text-align: left; padding-left: 10px;"><p>솰라솰라</p></li>
                            <li style="width: 20%;"><p>솰라솰라</p></li>
                            <li style="width: 20%;"><p>솰라솰라</p></li>
                        </ul>
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