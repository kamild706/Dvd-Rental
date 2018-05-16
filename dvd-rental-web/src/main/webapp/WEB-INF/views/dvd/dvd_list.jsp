<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: kamil
  Date: 5/16/18
  Time: 9:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Filmy DVD</title>
</head>
<body>
<table>
    <tr>
        <th>Tytuł</th>
        <th>Autor</th>
        <th>Opis</th>
        <th>Cena</th>
        <th>Akcja</th>
    </tr>
    <c:forEach items='${dvdList}' var="dvd">
        <tr>
            <td>${dvd.title}</td>
            <td>${dvd.author}</td>
            <td>${dvd.description}</td>
            <td>${dvd.price}</td>
            <td>
                <a href="<c:url value='/dvd/remove'/>/${dvd.id}">Usuń</a>
            </td>

        </tr>
    </c:forEach>
</table>
<a href="<c:url value='/dvd/add'/>">Dodaj nowy</a>
</body>
</html>
