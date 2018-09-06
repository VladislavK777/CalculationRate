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
        #tab1:checked ~ #content-tab1 {
            display: block;
        }
        /* Убираем текст с переключателей и оставляем иконки на малых экранах*/
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
        /* Изменяем внутренние отступы переключателей для малых экранов */
        @media screen and (max-width: 400px) {
            .tabs > label {
                padding: 15px;
            }
        }
    </style>
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

<br><br><br><br><br><br><br><br>

<div>
      <form action="/dynamicdistributionpark" method="get">
          <input type="submit" value="Очистить форму" class="bot1">
      </form>
      <form action="result" method="post">
          <input type="submit" value="Отправить" class="bot1">
          <div>
              <div class="attention">
                  <p>Были загружены данные из базы данных(выделено зеленым), возможно, они уже устарели, необходимо убедиться в корректности и при необходимости обновить</p>
                  <p>Либо недостаточно данных(выделено красным), необходимо внести ставки либо тарифы</p>
                  <p>Измененные данные будут обновлены автоматически</p>
              </div>
              <div class="tabs">
                  <input id="tab1" type="radio" name="tabs" checked>
                  <label for="tab1" title="Проверка данных">Проверка данных</label>

                  <section id="content-tab1">
                      <div>
                          <table class="table_report">
                              <tr>
                                  <th>Номер вагона</th>
                                  <th>Станция выгрузки</th>
                                  <th>Из под груза</th>
                                  <th>Класс груза</th>
                                  <th>Станция распределения</th>
                                  <th>Порожнее расстояние</th>
                                  <th>Тариф</th>
                                  <th>Следующий рейс</th>
                                  <th>Следующий груз</th>
                                  <th>Класс груза</th>
                                  <th>Оборот дней</th>
                                  <th>Ставка</th>
                              </tr>
                              <c:if test="${!empty needFillRateOrTariff}">
                                  <c:forEach items="${needFillRateOrTariff}" var="report">
                                      <c:forEach var="i" begin="0" end="${report.value.getSizeArray()}">
                                          <tr>
                                              <td>${report.value.getNumberOfWagon()}</td>
                                              <td>${report.value.getListRouteInfo().get(i).getCurrentNameOfStationOfWagon()}</td>
                                              <td>${report.value.getListRouteInfo().get(i).getCargo().getNameCargo()}</td>
                                              <c:choose>
                                                  <c:when test="${report.value.getListRouteInfo().get(i).getCargoType() == -1}">
                                                      <td></td>
                                                  </c:when>
                                                  <c:otherwise>
                                                      <td>${report.value.getListRouteInfo().get(i).getCargoType()}</td>
                                                  </c:otherwise>
                                              </c:choose>
                                              <td>${report.value.getListRouteInfo().get(i).getNameOfStationDepartureOfWagon()}</td>
                                              <td>${report.value.getListRouteInfo().get(i).getDistanceEmpty()}</td>
                                              <c:choose>
                                                  <c:when test="${empty report.value.getListRouteInfo().get(i).getTariff()}">
                                                      <td><input name="tariffs" class="field_red" required></td>
                                                  </c:when>
                                                  <c:when test="${report.value.getListRouteInfo().get(i).isLoadingTariffFromDB()}">
                                                      <td><input name="tariffs" class="field_green" value="${report.value.getListRouteInfo().get(i).getTariff()}"></td>
                                                  </c:when>
                                                  <c:otherwise>
                                                      <td><input name="tariffs" class="field_normal" value="${report.value.getListRouteInfo().get(i).getTariff()}"></td>
                                                  </c:otherwise>
                                              </c:choose>
                                              <td>${report.value.getListRouteInfo().get(i).getRoute().getNameOfStationDeparture()} - ${report.value.getListRouteInfo().get(i).getRoute().getNameOfStationDestination()}</td>
                                              <td>${report.value.getListRouteInfo().get(i).getRoute().getCargo().getNameCargo()}</td>
                                              <c:choose>
                                                <c:when test="${report.value.getListRouteInfo().get(i).getRoute().getCargo().getCargoType() == -1}">
                                                    <td></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>${report.value.getListRouteInfo().get(i).getRoute().getCargo().getCargoType()}</td>
                                                </c:otherwise>
                                              </c:choose>
                                              <td>${report.value.getListRouteInfo().get(i).getCountCircleDays()}</td>
                                              <c:choose>
                                                  <c:when test="${empty report.value.getListRouteInfo().get(i).getRate()}">
                                                      <td><input name="rates" class="field_red" required></td>
                                                  </c:when>
                                                  <c:when test="${report.value.getListRouteInfo().get(i).isLoadingRateFromDB()}">
                                                      <td><input name="rates" class="field_green" value="${report.value.getListRouteInfo().get(i).getRate()}"></td>
                                                  </c:when>
                                                  <c:otherwise>
                                                      <td><input name="rates" class="field_normal" value="${report.value.getListRouteInfo().get(i).getRate()}"></td>
                                                  </c:otherwise>
                                              </c:choose>
                                              <input type="hidden" name="wagons" value="${report.value.getNumberOfWagon()}">
                                              <input type="hidden" name="routes" value="${report.value.getListRouteInfo().get(i).getRoute().getNumberOrder()}">
                                          </tr>
                                      </c:forEach>
                                  </c:forEach>
                              </c:if>
                          </table>
                      </div>
                  </section>
              </div>
          </div>
          <br>
          </form>
      </div>
<br><br><br>

<div align="center" class="footer">
    Create by Vladislav Klochkov. All rights reserved, <span id="copy"></span>
</div>

</body>
</html>