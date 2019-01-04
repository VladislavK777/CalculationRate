<%--
  Created by IntelliJ IDEA.
  User: Vladislav.Klochkov
  Date: 15.01.2018
  Time: 12:47
  To change this template use File | Settings | File Templates.
--%>
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

<div class="one">
    <h1>ДИНАМИЧЕСКОЕ РАСПРЕДЕЛЕНИЕ ВАГОНОВ</h1>
    <div class="train">
    		<img class="image" src="resources/train.jpg">
    </div>
</div>

<div>
    <img class="logo" src="resources/logo.jpg">
</div>

<div>
    <table class="table_calculate">
        <tbody>
            <tr>
                <td class="td_table1" rowspan="3">Станция отправления</td>
                <td class="td_table1" rowspan="3">Станция назначения</td>
                <td class="td_table1" rowspan="3">Наименование груза</td>
                <td class="td_table1" rowspan="3">Расст., км</td>
                <td class="td_table1" rowspan="3">Время в пути, сут</td>
                <td class="td_table1" rowspan="3">Погр. / выгр.</td>
                <td class="td_table1" rowspan="3">Оборот, сут.</td>
                <td class="td_table1" rowspan="3">ВО</td>
                <td class="td_table1" rowspan="2">ДОХОД</td>
                <td class="td_table1">РАСХОД</td>
                <td class="td_table1" colspan="2">ПРИБЫЛЬ</td>
            </tr>
            <tr>
                <td class="td_table1">Тариф в собств. вагонах</td>
                <td class="td_table1">За нахождение в пути</td>
                <td class="td_table1">В сутки</td>
            </tr>
            <tr>
                <td class="td_table1">руб/ваг.</td>
                <td class="td_table1">руб/ваг.</td>
                <td class="td_table1">руб/ваг.</td>
                <td class="td_table1">руб/ваг/сут.</td>
            </tr>
            <c:forEach items="${list}" var="route">
                <tr>
                    <td class="td_table2">${route.getStationDeparture().getNameStation()} (${route.getStationDeparture().getRoad().getNameRoad()})</td>
                    <td class="td_table2">${route.getStationDestination().getNameStation()} (${route.getStationDestination().getRoad().getNameRoad()})</td>
                    <td class="td_table2">${route.getCargo.getNameCargo()}</td>
                    <td class="td_table2">${route.getDistance()}</td>
                    <td class="td_table2">${route.getCountDays()}</td>
                    <td class="td_table2"></td>
                    <td class="td_table2">${route.countDaysLoadUnload()}</td>
                    <td class="td_table2">поваг</td>
                    <c:choose>
                        <c:when test="${route.getRouteType().getCode() == 'ГРУЖ'}">
                            <td class="td_table2">${route.getRate()}</td>
                            <td class="td_table2"></td>
                            <td class="td_table2">${route.getRate()}</td>
                            <td class="td_table2"></td>
                        </c:when>
                        <c:otherwise>
                            <td class="td_table2"></td>
                            <td class="td_table2">${route.getTariff() * (-1)}</td>
                            <td class="td_table2">${route.getTariff()}</td>
                            <td class="td_table2"></td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            <tr>
                <td class="td_table3" colspan="3"></td>
                <td class="td_table3"></td>
                <td class="td_table3"></td>
                <td class="td_table3"></td>
                <td class="td_table3"></td>
                <td class="td_table3"></td>
                <td class="td_table3"></td>
                <td class="td_table3"></td>
                <td class="td_table3"></td>
                <td class="td_table3">${rate}</td>
            </tr>
        </tbody>
    </table>
</div>

<br><br><br>

<div align="center" class="footer">
    Create by Vladislav Klochkov. All rights reserved, <span id="copy"></span>
</div>

</body>
</html>