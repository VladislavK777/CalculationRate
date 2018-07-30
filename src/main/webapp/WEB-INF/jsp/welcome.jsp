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
        body {
            font: 14px/1 "Open Sans", sans-serif;
        }
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
        /* Убираем текст с переключателей и оставляем иконки на малых экранах*/
        @media screen and (max-width: 680px) {
            .tabs > label {
                font-size: 0;
            }
            .tabs > label:before {
                margin: 0;
                font-size: 18px;
            }
        }
        /* Изменяем внутренние отступы переключателей для малых экранов */
        @media screen and (max-width: 400px) {
            .tabs > label {
                padding: 15px;
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
    <h1>Сервис распределения вагонов</h1>
    <div class="train">
    		<img src="resources/train.jpg">
    </div>
</div>

<div>
    <img class="logo" src="resources/logo.jpg">
</div>

<br><br><br><br><br>

<div>
    <c:if test="${empty finalWagonList}">
        <input type="button" value="Создать процесс" onclick="showPopup()" class="bot1" style="visibility:visible">
    </c:if>

    <c:if test="${!empty finalWagonList}">
        <form action="/dynamicdistributionpark" method="get" style="visibility:visible">
            <input type="submit" value="Очистить форму" class="bot1">
        </form>
        <form action="export" method="post" style="visibility:visible">
            <input type="submit" value="Скачать отчет" class="bot1">
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
                                Файл заявок: <input type="file" name="routes" multiple accept="xlsx">
                            </p>
                            <p>
                                Файл ставок: <input type="file" name="rates" multiple accept="xlsx">
                            </p>
                            <p>
                                Файл порожних вагонов: <input type="file" name="emptyroutes" multiple accept="xlsx">
                            </p>
                            <p>
                                Файл дислокации вагонов: <input type="file" name="wagons" multiple accept="xlsx">
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
            <label for="tab2" title="Ошибки">Ошибки в кодах станций</label>

            <input id="tab3" type="radio" name="tabs">
            <label for="tab3" title="Итоговые показатели">Доходность</label>

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
                <p>
                    <c:if test="${!empty reportListOfError}">
                <table>
                    <c:forEach items="${reportListOfError}" var="error">
                        <tr>
                            <td style="background: #ffffff; color: #364274;">${error}</td>
                        </tr>
                    </c:forEach>
                </table>
                </c:if>
                </p>
            </section>
            <section id="content-tab3">
                <p>
                <table>
                    <tr>
                        <th>Доходность за 30 суток</th>
                        <th>Факт заявок за 30 дней</th>
                        <th>Факт заявок за 30 дней и декаду</th>
                        <th>Общие количество заявок</th>
                        <th>Открыть невостребованные заявки</th>
                    </tr>
                    <tr>
                        <td style="background: #ffffff; color: #364274;">${yield}</td>
                    </tr>
                </table>
                </p>
            </section>
        </div>
    </div>
    <br>
</div>

<br><br><br>

<div align="center" id="footer">
    Create by Vladislav Klochkov. All rights reserved, <span id="copy"></span>
</div>

</body>
</html>