<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>하루살이</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/signup.css">
</head>
<body>

<!-- header -->
<div class="header_tem">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>

<!--content  -->
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
		                                <input type="text" name="userid" id="userid" placeholder="아이디를 입력해주세요.">
		                                <a onclick="">중복확인</a>
		                            </dd>
		                        </dl>
		                    </div>
		                    
		                    <!-- 비밀번호 -->
		                    <div class="sign-form-box">
		                        <dl class="both">
		                            <dt>비밀번호</dt>
		                            <dd>
		                                <input type="password" name="userpw" id="userpw" placeholder="8~20자(영문,숫자 조합)로 입력">
		                            </dd> 
		                        </dl>
		                    </div>
		                    
		                    <!-- 비밀번호 확인-->
		                    <div class="sign-form-box">
		                        <dl class="both">
		                            <dt>비밀번호 확인</dt>
		                            <dd>
		                                <input type="password" name="userpw02" id="userpw02" placeholder="비밀번호를 다시 한 번 입력해주세요.">
		                            </dd>
		                        </dl>
		                    </div>
		                    
		                    <!-- 이름 -->
		                    <div class="sign-form-box">
		                        <dl class="both">
		                            <dt>이름</dt>
		                            <dd>
		                                <input type="text" name="usercpmns" id="usercpmns" placeholder="이름을 입력해주세요.">
		                            </dd>
		                        </dl>
		                    </div>
		                     
		                    <!-- 휴대폰번호 -->
		                    <div class="sign-form-box">
		                        <dl class="both">
		                            <dt>휴대폰번호</dt>
		                            <dd>
		                                <input type="text" name="usercptel01" id="usercptel01" placeholder="연락처를 입력해주세요." maxlength="11" onkeyup="onlyNumber(this);">
		                            </dd>
		                        </dl>
		                    </div>
		                    
		                    <!-- 이메일 -->
		                    <div class="sign-form-box">
		                        <dl class="both">
		                            <dt>이메일</dt>
		                            <dd>
		                                <input type="text" name="usercpemail" id="usercpemail" placeholder="이메일 주소를 입력해주세요.">
		                            </dd>
		                        </dl>
		                    </div>
		                   
		                    <!-- 주소 -->
		                    <div class="sign-form-box">
		                        <dl class="both address">
		                            <dt>주소</dt>
		                            <dd class="innerbtn readonly first">
		                                <input type="text" placeholder="우편번호를 입력해주세요." readonly name="usercpzip" id="usercpzip" />
		                                <a onclick="daumPostcode();">우편번호검색</a>
		                            </dd>
		                            <dd class="readonly">
		                                <input type="text" name="usercpaddr01" id="usercpaddr01" onfocus="" placeholder="기본 주소를 입력해주세요." readonly />
		                            </dd>
		                            <dd>
		                                <input type="text" name="usercpaddr02" id="usercpaddr02" placeholder="상세 주소를 입력해주세요." />
		                            </dd>
		                        </dl>
		                    </div>
		                    
		                    <div class="btn-wrap"><a onclick="" class="signup-submit">회원가입 완료</a></div>
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
	                document.getElementById('zip').value = data.zonecode; //5자리 새우편번호 사용
	                document.getElementById('addr1').value = fullAddr;
	
	                // 커서를 상세주소 필드로 이동한다.
	                document.getElementById('addr2').focus();
	            }
	        }).open();
	    }
	</script>    
	</div>
</div>

<!-- footer -->
<div class="footer_tem">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>



</body>
</html>