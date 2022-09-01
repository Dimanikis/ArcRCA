<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Prog.kiev.ua</title>
</head>
<body>
<div align="center">
        <c:url value="/message" var="message" />

    <form action="${message}" method="POST">

        <table border="1">
            <c:forEach items="${users}" var="user">

                <tr>
                    <td><input type="radio" name="check" value="${user.id}" id="check_${user.id}"></td>
                    <td><c:out value="${user.login}"/></td>
                </tr>
            </c:forEach>
        </table>

        Message:<br/><input type="text" name="message"><br/>
        <input type="submit" />
    </form>

    <p>Click here to : <a href="/">back</a></p>

</div>
</body>
</html>
