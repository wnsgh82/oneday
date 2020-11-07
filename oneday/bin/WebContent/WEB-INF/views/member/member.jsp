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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/signup.css">
<script type="text/javascript">

function membercreated() {
	var f = document.memberF;
	
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
	
	f.action = "${pageContext.request.contextPath}/member/member_ok.do";
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
<form name="memberF" method="post" enctype="multipart/form-data">
<div class="body_tem">
	<div class="content_tem">
		 <div class="title-area">
            <h3>&nbsp;&nbsp;&nbsp;&nbsp;회원가입</h3>
		</div>
		<!-- 여기부터 자기가 만드는거임!  -->
		
		<div id="wrap">
		    <section class="member signup">
		        <div class="inner">
		            <div class="signup02">                
		                <div class="sign-form-wrap">
		                   <!-- 아이디 -->
		                    <div class="sign-form-box">
		                        <dl class="both">
		                            <dt>아이디</dt>
		                            <dd class="innerbtn">
		                                <input type="text" name="userId" placeholder="아이디를 입력해주세요.">
		                                <a onclick="">중복확인</a>
		                            </dd>
		                        </dl>
		                    </div>
		                    
		                    <!-- 비밀번호 -->
		                    <div class="sign-form-box">
		                        <dl class="both">
		                            <dt>비밀번호</dt>
		                            <dd>
		                                <input type="password" name="userPwd" placeholder="8~20자(영문,숫자 조합)로 입력">
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
		                                <input type="text" name="userName" placeholder="이름을 입력해주세요.">
		                            </dd>
		                        </dl>
		                    </div>
		                     
		                    <!-- 휴대폰번호 -->
		                    <div class="sign-form-box">
		                        <dl class="both">
		                            <dt>휴대폰번호</dt>
		                            <dd>
		                                <input type="text" name="userTel" placeholder="연락처를 입력해주세요." maxlength="11">
		                            </dd>
		                        </dl>
		                    </div>
		                    
		                    <!-- 이메일 -->
		                    <div class="sign-form-box">
		                        <dl class="both">
		                            <dt>이메일</dt>
		                            <dd>
		                                <input type="text" name="userEmail"  placeholder="이메일 주소를 입력해주세요.">
		                            </dd>
		                        </dl>
		                    </div>
		                   
		                    <!-- 주소 -->
		                    <div class="sign-form-box">
		                        <dl class="both address">
		                            <dt>주소</dt>
		                            <dd class="innerbtn readonly first">
		                                <input type="text" placeholder="우편번호를 입력해주세요." readonly name="userZip" id="userZip" />
		                                <a onclick="daumPostcode();">우편번호검색</a>
		                            </dd>
		                            <dd class="readonly">
		                                <input type="text" name="userAddr1" id="userAddr1" onfocus="" placeholder="기본 주소를 입력해주세요." readonly/>
		                            </dd>
		                            <dd>
		                                <input type="text" name="userAddr2"  placeholder="상세 주소를 입력해주세요." />
		                            </dd>
		                        </dl>
		                    </div>
		     
		                    <c:if test="${userEnabled==1}">
		                    	<input type="hidden" name="userEnabled" value="1">
		                    	<input type="hidden" name="userCert" value="">
		                    </c:if>
		                    
		                    <!--자격증  --> <!--자격증  -->
		                    <c:if test="${userEnabled==100 }">
		                    <div class="sign-form-box">
		                        <dl class="both">
		                            <dt>자격증</dt>
		                            <dd>
		                                <input type="file" name="userCert" size="53" style="height: 25px;" >
		                                <input type="hidden" name="userEnabled" value="100">
		                            </dd>
		                        </dl>
		                    </div>
		                    </c:if>

		                    <div class="btn-wrap"><button type="button" onclick="membercreated();" class="signup-submit">회원가입 완료</button></div>
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
<!-- footer -->
<div class="footer_tem">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>



</body>
</html>