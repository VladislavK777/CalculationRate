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

    <!-- Скрипт всплывающего окна -->
    <script type="text/javascript">
        $(document).ready(function () {
            $(popup_bg).click(function () {
                $(popup).fadeOut(800);
            });
        });
        function showPopup() {
            $(popup).fadeIn(800);
        }
    </script>

    <!-- Копирайт -->
    <script>
        function cop() {
            document.getElementById("copy").innerText = new Date().getFullYear();
        }
    </script>

    <!-- Блокировка экрана -->
    <script type="text/javascript">
        function lockScreen() {
            var lock = document.getElementById('lockPane');
            if (lock)
                lock.className = 'lockScreenOn';
                $('body').addClass('stop-scrolling');
                document.body.scrollTop = document.documentElement.scrollTop = 0;
        }
    </script>

    <style>
        /* Настрйка вкладок*/
        /* Стили секций с содержанием */
        .tabs > section {
            display: none;
            max-width: 100%;
            padding: 15px;
            background: #fff;
            border: 1px solid #ddd;
        }
        .tabs > section > p {
            margin: 0 0 5px;
            line-height: 1.5;
            color: #383838;
            /* прикрутим анимацию */
            -webkit-animation-duration: 1s;
            animation-duration: 1s;
            -webkit-animation-fill-mode: both;
            animation-fill-mode: both;
            -webkit-animation-name: fadeIn;
            animation-name: fadeIn;
        }
        /* Прячем чекбоксы */
        .tabs > input {
            display: none;
            position: absolute;
        }
        /* Стили переключателей вкладок (табов) */
        .tabs > label {
            display: inline-block;
            margin: 0 0 -1px;
            padding: 15px 25px;
            font-weight: 600;
            text-align: center;
            color: #aaa;
            border: 0px solid #ddd;
            border-width: 1px 1px 1px 1px;
            background: #f1f1f1;
            border-radius: 3px 3px 0 0;
        }
        /* Шрифт-иконки от Font Awesome в формате Unicode */
        .tabs > label:before {
            font-family: fontawesome;
            font-weight: normal;
            margin-right: 10px;
        }
        /* Изменения стиля переключателей вкладок при наведении */
        .tabs > label:hover {
            color: #888;
            cursor: pointer;
        }
        /* Стили для активной вкладки */
        .tabs > input:checked + label {
            color: #555;
            border-top: 1px solid #364274;
            border-bottom: 1px solid #fff;
            background: #fff;
        }
        /* Активация секций с помощью псевдокласса :checked */
        #tab1:checked ~ #content-tab1,
        #tab2:checked ~ #content-tab2,
        #tab3:checked ~ #content-tab3 {
            display: block;
        }
        @media screen and (max-width: 1500px) {
            .tabs > label {
                font-size: 12px;
                width: 180px;
            }
            .tabs > label:before {
                margin: 0;
                font-size: 12px;
            }
        }
        /* Блокировка экрана */
        .lockScreenOff {
            display: none;
            visibility: hidden;
        }
        .lockScreenOn {
            display: block;
            visibility: visible;
            position: absolute;
            z-index: 999;
            top: 0px;
            left: 0px;
            width: 100%;
            height: 100%;
            background-color: #ccc;
            text-align: center;
            filter: alpha(opacity=75);
            opacity: 0.75;
        }
        .stop-scrolling {
            height: 100%;
            overflow: hidden;
        }
        /* Стили лоадера */
        .hide {
            display: none;
        }
        .loader {
            border: 16px solid #f3f3f3;
            border-top: 16px solid #364274;
            border-radius: 50%;
            width: 120px;
            height: 120px;
            animation: spin 2s linear infinite;
            position: relative;
            top: 40%;
            left: 45%;
        }
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    </style>
</head>

<body onload="cop()">

<div id="lockPane" class="lockScreenOff">
    <div class="loader" hide></div>
</div>

<div class="one">
    <h1>ДИНАМИЧЕСКОЕ РАСПРЕДЕЛЕНИЕ ВАГОНОВ</h1>
    <div class="train">
    		<img class="image" src="resources/train.jpg">
    </div>
</div>

<div>
    <img class="logo" src="resources/logo.jpg">
</div>

<br><br><br><br><br>
<div>
    <c:if test="${empty finalWagonList}">
        <input type="button" value="Создать процесс" onclick="showPopup()" class="bot1" style="visibility:visible">
        <br><br>
    </c:if>

    <c:if test="${!empty finalWagonList}">
        <form action="/dynamicdistributionpark" method="get" style="visibility:visible">
            <input type="submit" value="Очистить форму" class="bot1">
        </form>
    </c:if>
    <table class="table_report">
        <tr>
            <td class="td_report">
                <div id="popup"
                     style="position: absolute; height: 100%; width: 100%; top: 0; left: 0; display: none;">
                    <div id="popup_bg"
                         style="background: rgba(0, 0, 0, 0.2); position: absolute; z-index: 1; height: 100%; width: 100%;">
                    </div>
                    <div class="form">
                        <form enctype="multipart/form-data" method="post" action="reports">
                            <p>
                                Файл заявок: <input type="file" name="routesFile" multiple accept="xlsx">
                            </p>
                            <p>
                                Файл ставок: <input type="file" name="ratesFile" multiple accept="xlsx">
                            </p>
                            <p>
                                Файл порожних вагонов: <input type="file" name="emptyRoutesFile" multiple accept="xlsx">
                            </p>
                            <p>
                                Файл дислокации вагонов: <input type="file" name="wagonsFile" multiple accept="xlsx">
                            </p>
                            <p>
                                <input type="submit" value="Загрузить" class="bot1" id="input_form" onclick="lockScreen();">
                            </p>
                        </form>
                    </div>
                </div>
            </td>
        </tr>
    </table>

    <div>
        <div class="tabs">
            <input id="tab1" type="radio" name="tabs" checked>
            <label for="tab1" title="Распределенные рейсы">Распределенные заявки</label>

            <input id="tab2" type="radio" name="tabs">
            <label for="tab2" title="Итоговые показатели">Итоговые показатели</label>

            <input id="tab3" type="radio" name="tabs">
            <label for="tab3" title="Ошибки">Ошибки в кодах станций</label>

            <section id="content-tab1">
             <div>
                <table class="table_report">
                    <tr>
                        <th>Номер вагона</th>
                        <th>Из под груза</th>
                        <th>Класс груза</th>
                        <th>Станция распределения</th>
                        <th>Порожнее расстояние</th>
                        <th>Следующий рейс</th>
                        <th>Оборот дней</th>
                    </tr>
                     <c:if test="${!empty finalWagonList}">
                         <c:forEach items="${finalWagonList}" var="report">
                             <c:forEach var="i" begin="0" end="${report.value.getSizeArray()}">
                                <tr>
                                    <c:choose>
                                        <c:when test="${report.value.getListRouteInfo().get(i).getCargo().getNameCargo() == 'СБ.ПОВАГ.ОТП'}">
                                            <td style="background: #364274; color: #ffffff;">${report.value.getNumberOfWagon()}</td>
                                            <td style="background: #364274; color: #ffffff;">${report.value.getListRouteInfo().get(i).getCargo().getNameCargo()}</td>
                                            <td style="background: #364274; color: #ffffff;">${report.value.getListRouteInfo().get(i).getCargoType()}</td>
                                            <td style="background: #364274; color: #ffffff;">${report.value.getListRouteInfo().get(i).getNameOfStationDepartureOfWagon()}</td>
                                            <td style="background: #364274; color: #ffffff;">${report.value.getListRouteInfo().get(i).getDistanceEmpty()}</td>
                                            <td style="background: #364274; color: #ffffff;">${report.value.getListRouteInfo().get(i).getRoute().getNameOfStationDeparture()} - ${report.value.getListRouteInfo().get(i).getRoute().getNameOfStationDestination()}</td>
                                            <td style="background: #364274; color: #ffffff;">${report.value.getListRouteInfo().get(i).getCountCircleDays()}</td>
                                        </c:when>
                                        <c:when test="${report.value.getListRouteInfo().get(i).getCountCircleDays() > 30}">
                                            <td style="background: #ff0000; color: #ffffff;">${report.value.getNumberOfWagon()}</td>
                                            <td style="background: #ff0000; color: #ffffff;">${report.value.getListRouteInfo().get(i).getCargo().getNameCargo()}</td>
                                            <td style="background: #ff0000; color: #ffffff;">${report.value.getListRouteInfo().get(i).getCargoType()}</td>
                                            <td style="background: #ff0000; color: #ffffff;">${report.value.getListRouteInfo().get(i).getNameOfStationDepartureOfWagon()}</td>
                                            <td style="background: #ff0000; color: #ffffff;">${report.value.getListRouteInfo().get(i).getDistanceEmpty()}</td>
                                            <td style="background: #ff0000; color: #ffffff;">${report.value.getListRouteInfo().get(i).getRoute().getNameOfStationDeparture()} - ${report.value.getListRouteInfo().get(i).getRoute().getNameOfStationDestination()}</td>
                                            <td style="background: #ff0000; color: #ffffff;">${report.value.getListRouteInfo().get(i).getCountCircleDays()}</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td style="background: #ffffff; color: #364274;">${report.value.getNumberOfWagon()}</td>
                                            <td style="background: #ffffff; color: #364274;">${report.value.getListRouteInfo().get(i).getCargo().getNameCargo()}</td>
                                            <td style="background: #ffffff; color: #364274;">${report.value.getListRouteInfo().get(i).getCargoType()}</td>
                                            <td style="background: #ffffff; color: #364274;">${report.value.getListRouteInfo().get(i).getNameOfStationDepartureOfWagon()}</td>
                                            <td style="background: #ffffff; color: #364274;">${report.value.getListRouteInfo().get(i).getDistanceEmpty()}</td>
                                            <td style="background: #ffffff; color: #364274;">${report.value.getListRouteInfo().get(i).getRoute().getNameOfStationDeparture()} - ${report.value.getListRouteInfo().get(i).getRoute().getNameOfStationDestination()}</td>
                                            <td style="background: #ffffff; color: #364274;">${report.value.getListRouteInfo().get(i).getCountCircleDays()}</td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                             </c:forEach>
                         </c:forEach>
                     </c:if>
                </table>
             </div>
             </section>
             <section id="content-tab2">
                <c:if test="${!empty yield}">
                    <table class="table_report">
                        <tr>
                            <td>Средняя доходность за 30 суток: </td>
                            <td>${yield}</td>
                        </tr>
                        <tr>
                            <td>Факт заявок за 30 дней: </td>
                            <td>${count30Days}</td>
                        </tr>
                        <tr>
                            <td>Факт заявок за 30 дней и декаду: </td>
                            <td>${count45Days}</td>
                        </tr>
                            <td>Общие количество заявок: </td>
                            <td>${count}</td>
                        </tr>
                        <tr>
                            <form action="export" method="get">
                                <td><input type="submit" value="Скачать базу заявок" class="bot1" /></td>
                            </form>
                        </tr>
                    </table>
                </c:if>
            </section>
            <section id="content-tab3">
                <c:if test="${!empty reportListOfError}">
                    <table class="table_report">
                        <c:forEach items="${reportListOfError}" var="error">
                            <tr>
                                <td>${error}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </section>
        </div>
    </div>
    <br>
</div>

<br><br><br>

<div align="center" class="footer">
    Create by Vladislav Klochkov. All rights reserved, <span id="copy"></span>
</div>

</body>
</html>