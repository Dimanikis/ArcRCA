<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Prog.kiev.ua</title>
</head>
<body>
<div align="center">

        <c:url value="/encrypt" var="encrypt" />

    <form action="${encrypt}" method="POST">

        <table border="1">
            <c:forEach items="${messages}" var="messag">

                <tr>
                    <td><input type="radio" name="check" value="${messag.id}" id="check_${messag.id}"></td>
                    <td><c:out value="${messag.id}"/></td>
                    <td><c:out value="${messag.message}"/></td>
                </tr>
            </c:forEach>
        </table>

        <input type="submit" />
    </form>

    <h1>Encrypted message</h1>
    <h1>${mes}</h1>

    <p>Click here to : <a href="/">back</a></p>

</div>
</body>
</html>
