<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
  <title>Title</title>
</head>
<body>
<a href="/index.html">메인</a>
<table>
  <thead>
  <th>id</th>
  <th>username</th>
  <th>age</th>
  </thead>
  <tobdy>
  <c:forEach var="member" items="${members}">
    <tr>
      <td>${member.id}</td>
      <td>${member.username}</td>
      <td>${member.age}</td>
    </tr>
  </c:forEach>
  </tobdy>
</table>
</body>
</html>