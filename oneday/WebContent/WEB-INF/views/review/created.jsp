<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/login.css">

<style type="text/css">
.boxTF {
    border:1px solid #999999;
    padding:3px 5px 5px;
    border-radius:4px;
    background-color:#ffffff;
    font-family:"Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
}

.boxTA {
    border:1px solid #999999;
    height:150px;
    padding:3px 5px;
    border-radius:4px;
    background-color:#ffffff;
    font-family:"Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
}

.btn {
    color:#333333;
    font-weight:500;
    font-family:"Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
    border:1px solid #cccccc;
    background-color:#fff;
    text-align:center;
    cursor:pointer;
    padding:3px 10px 5px;
    border-radius:4px;
}
.btn:active, .btn:focus, .btn:hover {
    background-color:#e6e6e6;
    border-color: #adadad;
    color: #333333;
}
.btn[disabled], fieldset[disabled] .btn {
    pointer-events: none;
    cursor: not-allowed;
    filter: alpha(opacity=65);
    -webkit-box-shadow: none;
    box-shadow: none;
    opacity: .65;
}
</style>

<script type="text/javascript">
function sendOk() {
    var f = document.noticeForm;

	var str = f.rvscore.value;
    if(!str) {
        alert("점수를 입력하세요. ");
        f.noSubject.focus();
        return;
    }

	str = f.rvContent.value;
    if(!str) {
        alert("내용을 입력하세요. ");
        f.noContent.focus();
        return;
    }

		f.action="${pageContext.request.contextPath}/review/${mode}_ok.do";
	
    	f.submit();
}

<c:if test="${mode=='update'}">
function deleteFile(num) {
	  var url="${pageContext.request.contextPath}/notice/deleteFile.do?num="+num+"&page=${page}&rows=${rows}";
	  location.href=url;
}
</c:if>
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
		<div class="title-area">
            <h3>&nbsp;&nbsp;&nbsp;&nbsp;리뷰 쓰기</h3>
		</div>
		
		<!-- 여기부터 자기가 만드는거임!  -->
		<div style="width: 960px; margin: 0 auto" >
			
			<div>
			<form name="noticeForm" method="post" enctype="multipart/form-data" >
			  <table style="width: 100%; margin: 20px auto 0px; border-spacing: 0px; border-collapse: collapse;">
			  <tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">클래스 명 :</td>
			      <td style="padding-left:10px;"> 
			         	${sdto.className}
			         	<input type="hidden" name="className" value="${sdto.className}">
			      </td>
			  </tr>

			  <tr align="left" height="40" style="border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">아이디 :</td>
			      <td style="padding-left:10px;"> 
			          ${sdto.userId}
			          <input type="hidden" name="userId" value="${sdto.userId}">
			          <input type="hidden" name="classNum1" value="${sdto.classNum}">
			          
			      </td>
			  </tr>
			  
			  <tr align="left" height="40" style="border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">공지여부</td>
			      <td style="padding-left:10px;"> 
			        <input type="radio" name="rvscore" value="1"> 별 1개 &nbsp;
			        <input type="radio" name="rvscore" value="2"> 별 2개 &nbsp;
			        <input type="radio" name="rvscore" value="3"> 별 3개 &nbsp;
			        <input type="radio" name="rvscore" value="4"> 별 4개 &nbsp;
			        <input type="radio" name="rvscore" value="5"> 별 5개
			      </td>
			  </tr>
			  
			  <tr align="left" style="border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center; padding-top:5px;" valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
			      <td valign="top" style="padding:5px 0px 5px 10px;"> 
			          <textarea name="rvContent" rows="12" class="boxTA" style="width: 95%;"></textarea>
			      </td>
			  </tr>
			  			  
			  </table>
			
			  <table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			     <tr height="45"> 
			      <td align="center" >
			        <button type="button" class="btn" onclick="sendOk();">${mode=='update'?'수정완료':'리뷰작성'}</button>
			        <button type="reset" class="btn">다시입력</button>
			        <button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/notice/list.do?rows${rows }';">${mode=='update'?'수정취소':'등록취소'}</button>

				  </td>
			    </tr>
			  </table>
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