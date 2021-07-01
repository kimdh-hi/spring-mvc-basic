<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<!-- 방법 1
     getAttribute()로 받은 값이 object 타입이기 때문에 캐스팅이 필요함 .. 조금 번거로움-->
<%--<ul>--%>
<%--  <li>id=<%=((Member)request.getAttribute("member")).getId()%></li>--%>
<%--  <li>username=<%=((Member)request.getAttribute("member")).getUsername()%></li>--%>
<%--  <li>age=<%=((Member)request.getAttribute("member")).getAge()%></li>--%>
<%--</ul>--%>

<ul>
  <li>id=${member.id}</li>
  <li>username=${member.username}</li>
  <li>age=${member.age}</li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>