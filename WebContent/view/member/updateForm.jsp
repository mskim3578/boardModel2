
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>회원 정보 수정</title>
<link rel="stylesheet"
	href="<%=request.getContextPath() %>/css/main.css">
<script>
	function input_check(f) {
		<c:if test="${login ne 'admin'}">
		if (f.pass.value == "") {
			alert("비밀번호를 입력하세요");
			f.pass.focus();
			return false;
		}
		</c:if>
		return true;
	}
	function passchg_winopen() {
		var op = "width=500, height=230, left=50,top=150";
		open("passwordForm.jsp", "", op);
	}
	function win_upload() {
		var op = "width=500, height=150, left=50, top=150";
		window.open("pictureimgForm.jsp", "", op);
	}
</script>
</head>
<body>
	<form action="<%=request.getContextPath() %>/member/update" name="f"
		method="post" onsubmit="return input_check(this)">
		<input type="hidden" name="picture">
		<table>
			<caption>MODEL1 회원 정보 수정</caption>
			<tr>
				<td rowspan="4" valign="bottom"><img
					src="img/${mem.picture}" width="100" height="120" id="pic"><br>
					<font size="1"><a href="javascript:win_upload()">사진수정</a></font></td>
				<td>아이디</td>
				<td><input type="text" name="id" value="${mem.id}"
					readonly></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="pass"></td>
			</tr>
			<tr>
				<td>이름</td>
				<td><input type="text" name="name" value="${mem.name}"></td>
			</tr>
			<tr>
				<td>성별</td>
				<td><input type="radio" name="gender"
					${mem.gender == 1 ? "checked" : ""}  value="1">남 <input
					type="radio" name="gender"
					${mem.gender == 2 ? "checked" : ""} value="2">여</td>
			</tr>
			<tr>
				<td>전화번호</td>
				<td colspan="2"><input type="text" name="tel"
					value="${mem.tel}"></td>
			</tr>
			<tr>
				<td>이메일</td>
				<td colspan="2"><input type="text" name="email"
					value="${mem.email}"></td>
			</tr>
			<tr>
				<td colspan="3"><input type="submit" value="회원수정"> <input
					type="button" value="비밀번호수정" onclick="passchg_winopen()"></td>
			</tr>
		</table>
	</form>
</body>
</html>

