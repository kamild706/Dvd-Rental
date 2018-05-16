<%--
  Created by IntelliJ IDEA.
  User: kamil
  Date: 5/16/18
  Time: 9:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Dodaj nowe DVD</title>
</head>
<body>
<h3>Add book</h3>
<form method="post">
    <table>
        <tr>
            <td>Title:</td>
            <td><input type="text" name="title"></td>
        </tr>
        <tr>
            <td>Author:</td>
            <td><input type="text" name="author"/></td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><input type="text" name="description"/></td>
        </tr>
        <tr>
            <td>Price:</td>
            <td><input type="text" name="price"/></td>
        </tr>
    </table>
    <input type="submit" value="Save">
</form>
</body>
</html>
