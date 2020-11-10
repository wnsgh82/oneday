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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/signup.css">

<script type="text/javascript">

function update(){
	var f=document.updateForm;
	
	var str;

	str = f.userId.value;
	str = str.trim();
	if(!str) {
		alert("아이디를 입력하세요. ");
		f.userId.focus();
		return;
	}
	f.userId.value = str;
	
	str = f.userPwd.value;
	str = str.trim();
	if(!str) {
		alert("비밀번호 를 입력하세요. ");
		f.userPwd.focus();
		return;
	}
	f.userPwd.value = str;
	
	if(str!=f.userPwd2.value){
		alert("비밀번호를 확인하세요 ")
		f.userPwd2.focus();
		return;
	}
	
	str = f.userName.value;
	str = str.trim();
	if(!str) {
		alert("이름 를 입력하세요. ");
		f.userName.focus();
		return;
	}
	f.userName.value = str;
	
	str = f.userTel.value;
	str = str.trim();
	if(!str) {
		alert("전화번호 를 입력하세요. ");
		f.userTel.focus();
		return;
	}
	f.userTel.value = str;
	
	str = f.userEmail.value;
	str = str.trim();
	if(!str) {
		alert("이메일 입력하세요. ");
		f.userEmail.focus();
		return;
	}
	f.userEmail.value = str;
	
	
	str = f.userZip.value;
	str = str.trim();
	if(!str) {
		alert("우편번호를 확인하세요. ");
		f.userZip.focus();
		return;
	}
	f.userZip.value = str;
	
	str = f.userAddr1.value;
	str = str.trim();
	if(!str) {
		alert("주소를 확인하세요. ");
		f.userAddr1.focus();
		return;
	}
	f.userAddr1.value = str;
	
	str = f.userAddr2.value;
	str = str.trim();
	if(!str) {
		alert("주소를 확인하세요. ");
		f.userAddr2.focus();
		return;
	}
	f.userAddr2.value = str;
	
	f.action="${pageContext.request.contextPath}/mypage/memberUpdate_ok.do";
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
<div class="body_tem" style="height: 1100px;">
	<div class="content_tem">
		
		<!-- 여기부터 자기가 만드는거임!  -->
    <div id="mypage">
        <div id="my_left">
            <div id="myl_title">
                <h3>마이페이지</h3>
            </div>
            <ul id="myl_list">
            	<c:if test="${dto.userEnabled==1}">  <%--수강생일 때 왼쪽 메뉴--%>
            		<li><a href="${pageContext.request.contextPath}/mypage/memberUpdate.do"> 회원정보수정</a></li>
	                <li><a href=""> 여기  수강내역 이런거</a></li>
	                <li><a href=""> 이런거 수강생강사</a></li>
	                <li><a href=""> 별로 알아서  </a></li>
	                <li><a href=""> 넣으세요 </a></li>
            	</c:if>
            	
            	<c:if test="${dto.userEnabled==100}">  <%--강사일 때 왼쪽 메뉴--%>
            		<li><a href="${pageContext.request.contextPath}/mypage/mypageMain.do"> 나의 클래스</a></li>
	            	<li><a href="${pageContext.request.contextPath}/mypage/classList.do"> 수강생 관리</a></li>
	                <li><a href="${pageContext.request.contextPath}/mypage/memberUpdate.do"> 회원정보수정</a></li>
	                <li><a href="${pageContext.request.contextPath}/mypage/pwd.do"> 회원 탈퇴  </a></li>
	                <li><a href=""> 넣으세요 </a></li>
            	</c:if>
            	
            	<c:if test="${dto.userEnabled==200}">  <%--관리자일 때 왼쪽 메뉴--%>
            		<li><a href="${pageContext.request.contextPath}/mypage/memberUpdate.do"> 회원정보수정</a></li>
	                <li><a href=""> 여기  수강내역 이런거</a></li>
	                <li><a href=""> 이런거 수강생강사</a></li>
	                <li><a href=""> 별로 알아서  </a></li>
	                <li><a href=""> 넣으세요 </a></li>
            	
            	</c:if>
                
            </ul>
        </div>


        <div id="my_right" style="height: 50px;">
           <div class="title-area"  style="width: 850px;" >
            <h3>&nbsp;&nbsp;&nbsp;&nbsp;회원정보수정</h3>
			</div>
			<form name="updateForm" method="post" enctype="multipart/form-data">
			<div class="body_tem">
				<div class="content_tem">
					
					<!-- 여기부터 자기가 만드는거임!  -->
					
					<div id="wrap"  style="width: 850px;">
					    <section class="member signup">
					        <div class="inner" style="width: 100%">
					            <div class="signup02">                
					                <div class="sign-form-wrap">
					                   <!-- 아이디 -->
					                    <div class="sign-form-box">
					                        <dl class="both">
					                            <dt>아이디</dt>
					                            <dd class="innerbtn">
					                                ${dto.userId}
					                            </dd>
					                        </dl>
					                    </div>
					                    
					                    <!-- 비밀번호 -->
					                    <div class="sign-form-box">
					                        <dl class="both">
					                            <dt>비밀번호</dt>
					                            <dd>
					                                <input type="password" name="userPwd">
					                            </dd> 
					                        </dl>
					                    </div>
					                    
					                    <!-- 비밀번호 확인-->
					                    <div class="sign-form-box">
					                        <dl class="both">
					                            <dt>비밀번호 확인</dt>
					                            <dd>
					                                <input type="password" name="userPwd2" placeholder="비밀번호를 다시 한 번 입력해주세요.">
					                            </dd>
					                        </dl>
					                    </div>
					                    
					                    <!-- 이름 -->
					                    <div class="sign-form-box">
					                        <dl class="both">
					                            <dt>이름</dt>
					                            <dd>
					                                <input type="text" name="userName" value="${dto.userName}">
					                            </dd>
					                        </dl>
					                    </div>
					                     
					                    <!-- 휴대폰번호 -->
					                    <div class="sign-form-box">
					                        <dl class="both">
					                            <dt>휴대폰번호</dt>
					                            <dd>
					                                <input type="text" name="userTel" maxlength="11" value="${dto.userTel}">
					                            </dd>
					                        </dl>
					                    </div>
					                    
					                    <!-- 이메일 -->
					                    <div class="sign-form-box">
					                        <dl class="both">
					                            <dt>이메일</dt>
					                            <dd>
					                                <input type="text" name="userEmail"  value="${dto.userEmail}">
					                            </dd>
					                        </dl>
					                    </div>
					                   
					                    <!-- 주소 -->
					                    <div class="sign-form-box">
					                        <dl class="both address">
					                            <dt>주소</dt>
					                            <dd class="innerbtn readonly first">
					                                <input type="text" readonly name="userZip" id="userZip" value="${dto.userZip} "/>
					                                <a onclick="daumPostcode();">우편번호검색</a>
					                            </dd>
					                            <dd class="readonly">
					                                <input type="text" name="userAddr1" id="userAddr1" onfocus="" value="${dto.userAddr1}" readonly/>
					                            </dd>
					                            <dd>
					                                <input type="text" name="userAddr2"  value="${dto.userAddr2}" />
					                            </dd>
					                        </dl>
					                    </div>
					                    
					                    <c:if test="${sessionScope.member.userEnabled==100 }">
					                    <div class="sign-form-box">
					                        <dl class="both">
					                            <dt>자격증</dt>
					                            <dd>
					                                <input type="file" name="userCert" size="53" style="height: 25px;" >
					                            </dd>
					                     
					                        </dl>
					                        <dl class="both">
					                            <dt>등록된 자격증</dt>
					                            <dd>
					                                <img id="myPhoto" src="${pageContext.request.contextPath}/uploads/photo/${dto.userCert}" width="30" height="30" style="cursor: pointer;">
					                            </dd>
					                     
					                        </dl>
					                    </div>
										</c:if>
			
					                    <div class="btn-wrap">
					                    	<input type="hidden" name="userId" value="${sessionScope.member.userId}">
					                   		<input type="hidden" name="userEnabled" value="${sessionScope.member.userEnabled}">
					                    	<input type="hidden" name="userCert" value="${dto.userCert}">
					                    	
					                    	<button type="button" onclick="update();" class="signup-submit">회원정보 수정</button>
					                    	<button type="reset" class="signup-submit" onclick="javascript:location.href='${pageContext.request.contextPath}/mypage/mypageMain.do'" style="margin-top: 5px;">회원정보 수정 취소</button>
					                    </div>
					                </div>
					            </div>
					        </div>
					    </section>
					</div>
					
					<!-- 우편번호 -->
				<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
				<script>
				    function daumPostcode() {
				        new daum.Postcode({
				            oncomplete: function(data) {
				                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
				
				                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
				                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
				                var fullAddr = ''; // 최종 주소 변수
				                var extraAddr = ''; // 조합형 주소 변수
				
				                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
				                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
				                    fullAddr = data.roadAddress;
				
				                } else { // 사용자가 지번 주소를 선택했을 경우(J)
				                    fullAddr = data.jibunAddress;
				                }
				
				                // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
				                if(data.userSelectedType === 'R'){
				                    //법정동명이 있을 경우 추가한다.
				                    if(data.bname !== ''){
				                        extraAddr += data.bname;
				                    }
				                    // 건물명이 있을 경우 추가한다.
				                    if(data.buildingName !== ''){
				                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
				                    }
				                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
				                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
				                }
				
				                // 우편번호와 주소 정보를 해당 필드에 넣는다.
				                document.getElementById('userZip').value = data.zonecode; //5자리 새우편번호 사용
				                document.getElementById('userAddr1').value = fullAddr;
				
				                // 커서를 상세주소 필드로 이동한다.
				               
				            }
				        }).open();
				    }
			
				</script>    
				</div>
			</div>
			</form>
            
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