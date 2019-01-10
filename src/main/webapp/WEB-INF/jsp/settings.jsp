<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>UralTransCom|DynamicDistributionPark</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="resources/style.css" rel="stylesheet" type="text/css"/>
    <link rel="shortcut icon" href="resources/favicon.ico" type="image/x-icon">
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js">
    </script>

    <!-- Копирайт -->
    <script>
        function cop() {
            document.getElementById("copy").innerText = new Date().getFullYear();
        }
    </script>


</head>

<body onload="cop()">

<div>
    <table>
        <tbody>
            <tr>
                <th>Дорога</th>
                <th>Станция</th>
                <th>Объем</th>
                <th>Станция возврата</th>
            </tr>
            <c:forEach items="${map}" var="total">


                <c:forEach items="${total.value}" var="setting">
                 <tr>
                    <td>${total.key}</td>
                    <td>${setting.getIdStationString()}</td>
                    <td>${setting.getVolumeGroupsString()}</td>
                    <td>${setting.getNameStationReturn()}</td>
                 </tr>
                </c:forEach>

            </c:forEach>
        </tbody>
    </table>
</div>

<br><br><br>

<div align="center" class="footer">
    Create by Vladislav Klochkov. All rights reserved, <span id="copy"></span>
</div>

</body>
</html>