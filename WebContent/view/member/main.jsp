<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html><html><head><meta charset="EUC-KR">
<title>회원관리 프로그램</title></head><body>
<h3>${login}회원님 반갑습니다.</h3>
<h3><a href="<%=request.getContextPath()%>/member/logout">로그아웃</a></h3>
<h3><a href="<%=request.getContextPath()%>/member/memberInfo?id=${login}">회원정보보기</a></h3>
<c:if test="${login eq 'admin'}">
<h3><a href="<%=request.getContextPath()%>/member/memberList">회원목록보기</a></h3>
</c:if></body></html>