<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="css/font.css"/>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        th, td {
            padding: 5px;
        }
        th {
            text-align: left;
        }
    </style>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <div align="center">
    <table>
        <caption><h2>List of Meals</h2></caption>
        <tr>
            <th>Время/Дата</th>
            <th>Описание</th>
            <th>Калории</th>
        </tr>
            <c:forEach var="meal" items="${requestScope.mealList}">
                <fmt:parseDate value="${meal.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}" var="mealDate"/>
                <tr class="${meal.isExcess() ? 'excess' : 'normal'}">
                    <td><c:out value="${mealDate}"></c:out></td>
                    <td><c:out value="${meal.getDescription()}"></c:out></td>
                    <td><c:out value="${meal.getCalories()}"></c:out></td>
                </tr>
            </c:forEach>
    </table>
    </div>
</body>
</html>
