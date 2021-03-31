<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html><html><head><meta charset="EUC-KR"><title>회원 목록 조회</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/main.css"></head>
<script type="text/javascript">
   function del(id) {
	    if (confirm(id + "님을 탈퇴하시겠습니까?")) {
	       location.href="delete?id="+id;	     	
	    }   }  </script>
<body><table>

 <caption>MODEL1 회원 목록</caption>
 <tr><th>사진</th><th>아이디</th><th>이름</th><th>성별</th><th>전화</th><th>&nbsp;</th></tr>
<c:forEach var="m"   items="${list}">

  <tr><td>
  <img src="img/${m.picture}" width="30" height="32"></td>
  <td><a href="memberInfo?id=${m.id}">${m.id}</a></td>
  <td>${m.name}</td>
  <td>${m.gender==1?"남자":"여자"}</td><td>${m.tel}</td>
  <td><a href="updateForm?id=${m.id}">[수정]</a>
  <c:if test="${m.getId() ne 'admin'}">
  <a href="javascript:del('${m.id}')">[강제탈퇴]</a>
  </c:if>  
  </td></tr>
  </c:forEach>
  
  </table></body></html> 
  