<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Prog.kiev.ua</title>
</head>
<body>
<div align="center">
    <p>Click to enter : <a href="/profile">profile</a></p>

    <c:url value="/logout" var="logoutUrl" />
    <p>Click to logout: <a href="${logoutUrl}">LOGOUT</a></p>

    <p>Click here to send: <a href="/send">message</a></p>

    <p>Click here to accept and encrypt: <a href="/accept">message</a></p>



</div>
</body>
</html>
