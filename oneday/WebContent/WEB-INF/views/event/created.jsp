<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/event.css">

<script type="text/javascript">
function sendOk() {
    var f = document.onedayForm;

	var str = f.eventName.value;
    if(!str) {
        alert("이벤트 제목을 입력하세요. ");
        f.eventName.focus();
        return;
    }

	str = f.eventContent.value;
    if(!str) {
        alert("내용을 입력하세요. ");
        f.eventContent.focus();
        return;
    }
    
    str = f.eventStart.value;
    if(!str) {
        alert("이벤트 기간 시작일을 입력하세요. ");
        f.eventStart.focus();
        return;
    }
    
    str = f.eventEnd.value;
    if(!str) {
        alert("이벤트 기간 종료일을 입력하세요. ");
        f.eventEnd.focus();
        return;
    }
    
    var mode="${mode}";
    if(mode=="created" && ! f.selectFile.value){
    	alert("이미지 파일을 선택하세요.");
    	f.selectFile.focus();
    	return;
    }
    
	f.action="${pageContext.request.contextPath}/event/${mode}_ok.do";
	
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
		<div class="title-area">
            <h3>&nbsp;&nbsp;&nbsp;&nbsp;★이벤트★</h3>
		</div>
		
		<!-- 여기부터 자기가 만드는거임!  -->
		<div>
			
			<div>
			<form name="onedayForm" method="post" enctype="multipart/form-data">
			  <table style="width: 930px; margin: 10px auto 0px; border-spacing: 0px; border-collapse: collapse;">
			  <tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">이벤트 제목</td>
			      <td style="padding-left:10px;"> 
			          <input type="text" name="className" maxlength="100" class="boxTF" style="width: 95%;" value="${dto.eventName}">
			      </td>
			  </tr>

			  <tr align="left" height="40" style="border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">작성자</td>
			      <td style="padding-left:10px;"> 
			          ${sessionScope.member.userName}
			      </td>
			  </tr>
			  
			  <tr align="left" height="40" style="border-top: 1px solid #cccccc; border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">이벤트 기간</td>
			      <td style="padding-left:10px;"> 
			          <span><input type="date" name="classStart" maxlength="100" class="boxTF" style="width: 40%;" value="${dto.eventStart}">&nbsp;&nbsp;
			          <input type="date" name="classEnd" maxlength="100" class="boxTF" style="width: 40%;" value="${dto.eventEnd}"></span>
			      </td>
			  </tr>
			  
			  <tr align="left" style="border-bottom: 1px solid #cccccc;"> 
			      <td width="100" bgcolor="#eeeeee" style="text-align: center; padding-top:5px;" valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
			      <td valign="top" style="padding:5px 0px 5px 10px;"> 
			          <textarea name="classContent" rows="30" class="boxTA" style="width: 95%;">${dto.eventContent}</textarea>
			      </td>
			  </tr>
			  
			  <tr align="left" height="40" style="border-bottom: 1px solid #cccccc;">
			      <td width="100" bgcolor="#eeeeee" style="text-align: center;">이미지</td>
			      <td style="padding-left:10px;"> 
			      	   <!-- file 객체는 value 속성으로 초기화가 불가능하다 -->
			          <input type="file" name="selectFile" accept="image/*" class="boxTF" size="53" style="height: 25px;">
			       </td>
			  </tr> 
			  
			  <c:if test="${mode=='update'}">
				  <tr align="left" height="40" style="border-bottom: 1px solid #cccccc;">
				      <td width="100" bgcolor="#eeeeee" style="text-align: center;">등록이미지</td>
				      <td style="padding-left:10px;"> 
				          <img id="myPhoto" src="${pageContext.request.contextPath}/uploads/photo/${dto.eventIFN}" width="30" height="30" style="cursor: pointer;">
				       </td>
				  </tr> 
			  
			  </c:if>
			  
			  </table>
			
			  <table style="width: 100%; margin: 0px auto; border-spacing: 0px;">
			     <tr height="45"> 
			      <td align="center" >
			      	<c:if test="${mode=='update'}">
			      		<input type="hidden" name="page" value="${page}">
			      		<input type="hidden" name="classNum" value="${dto.eventNum}">
			      		<input type="hidden" name="classIFN" value="${dto.eventIFN}"> 
			      			
			      	</c:if>
			        <button type="button" class="classBtn3" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}</button>
			        <button type="reset" class="classBtn3">다시입력</button>
			        <button type="button" class="classBtn3" onclick="javascript:location.href='${pageContext.request.contextPath}/event/list.do';">${mode=='update'?'수정취소':'등록취소'}</button>

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