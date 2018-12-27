<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="user" class="main.java.ua.nure.kn.yesipov.User" scope="session"/>
<html>
<head><title>User management. Details</title></head>
<body>
<form action="<%=request.getContextPath()%>/details" method="post">
    <input type="hidden" name="id" value="${user.id}"><br>
    First name <input name="firstName" value="${user.firstName}" readonly><br>
    Last name <input name="lastName" value="${user.lastName}" readonly><br>
    Date of birth <input name="date"
                         value="<fmt:formatDate value="${user.dateOfBirth}" type="date" dateStyle="medium"/>" readonly><br>
    <input type="submit" name="okButton" value="Ok">
    <input type="submit" name="cancelButton" value="Cancel">
</form>
<c:if test="${requestScope.error != null}">
    <script>
        alert('${requestScope.error}');
    </script>
</c:if>
</body>
</html>