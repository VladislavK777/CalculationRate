<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
      <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <html>

        <head>
          <title>UralTransCom|DynamicDistributionPark</title>
          <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
          <link href="resources/css/style.css" rel="stylesheet" type="text/css" />
          <link href="resources/favicon.ico" rel="shortcut icon" type="image/x-icon">
          <script async src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
          <script src="https://unpkg.com/react@16/umd/react.production.min.js"></script>
          <script src="https://unpkg.com/react-dom@16/umd/react-dom.production.min.js"></script>
          <script src="https://cdnjs.cloudflare.com/ajax/libs/babel-standalone/6.26.0/babel.js"></script>
          <script src="resources/javascript/react.js" type="text/babel"></script>
		  <script src="resources/javascript/operationUpdate.js"></script>
		  <script src="resources/javascript/operationDelete.js"></script>
          <script src="resources/javascript/process.js"></script>

        </head>
        <style>
          /* Настрйка вкладок*/

          /* Стили секций с содержанием */

          .tabs>section {
            display: none;
            max-width: 100%;
            padding: 15px;
            background: #fff;
            border: 1px solid #ddd;
          }

          .tabs>section>p {
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

          .tabs>input {
            display: none;
            position: absolute;
          }

          /* Стили переключателей вкладок (табов) */

          .tabs>label {
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

          .tabs>label:before {
            font-family: fontawesome;
            font-weight: normal;
            margin-right: 10px;
          }

          /* Изменения стиля переключателей вкладок при наведении */

          .tabs>label:hover {
            color: #888;
            cursor: pointer;
          }

          /* Стили для активной вкладки */

          .tabs>input:checked+label {
            color: #555;
            border-top: 1px solid #364274;
            border-bottom: 1px solid #fff;
            background: #fff;
          }

          /* Активация секций с помощью псевдокласса :checked */

          #tab1:checked~#content-tab1,
          #tab2:checked~#content-tab2,
          #tab3:checked~#content-tab3,
          #tab4:checked~#content-tab4,
          #tab5:checked~#content-tab5,
          #tab6:checked~#content-tab6 {
            display: block;
          }

          @media screen and (max-width: 1500px) {
            .tabs>label {
              font-size: 12px;
              width: 180px;
            }
            .tabs>label:before {
              margin: 0;
              font-size: 12px;
            }
          }
        </style>

        <body onload="cop()">

          <div>
            <div class="tabs">
              <input id="tab1" type="radio" name="tabs" checked>
              <label for="tab1" title="1">1</label>

              <input id="tab2" type="radio" name="tabs">
              <label for="tab2" title="2">2</label>

              <input id="tab3" type="radio" name="tabs">
              <label for="tab3" title="3">3</label>

              <input id="tab4" type="radio" name="tabs">
              <label for="tab4" title="4">4</label>

              <input id="tab5" type="radio" name="tabs">
              <label for="tab5" title="5">5</label>

              <input id="tab6" type="radio" name="tabs">
              <label for="tab6" title="6">6</label>

              <section id="content-tab1">
                <!--<div id="root"></div>-->
                <input type="button" id="btAddReturnStations" onclick="createField()" value="Добавить условие" class="bot1" />
                <table class="table_report">
                  <tbody>
                    <tr>
                      <th>Дорога</th>
                      <th>Список станций</th>
                      <th>Объем</th>
                      <th>Станция возврата</th>
                    </tr>
                    <c:forEach items="${mapReturnStations}" var="total">
                      <c:forEach items="${total.value}" var="setting">
                        <tr id="contentReturnStations${setting.getId()}">
                          <input id="idReturnStations" value='${setting.getId()}' type="hidden" />
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="road${setting.getId()}" value='${total.key}' onkeyup="searchfield(this.id)" />
                              <span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="idStationStringReturnStations" value='${setting.getIdStationString()}' />
                              <span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="volumeGroupsStringReturnStations" value='${setting.getVolumeGroupsString()}' />
                              <span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="station${setting.getId()}" value='${setting.getNameStationReturn()}' onkeyup="searchfield(this.id)" />
                              <span class="focus-border"></span></div>
                          </td>
                          <td><input type="button" id="btEditReturnStations" onclick="updateFieldReturnStations(this.parentNode.parentNode.id)" value="Сохранить" class="bot1"></td>
                          <td><input type="button" id="btDeleteReturnStations" onclick="deleteFieldReturnStations(this.parentNode.parentNode.id)" value="Удалить" class="bot1" /></td>
                        </tr>
                      </c:forEach>
                    </c:forEach>
                  </tbody>
                </table>
              </section>
              <section id="content-tab2">
                <input type="button" id="btAddReturnException" onclick="createField(this.parentNode.id)" value="+" class="bot1" />
                <table class="table_report">
                  <tbody>
                    <tr>
                      <th>Дорога</th>
                      <th>Станция</th>
                      <th>Объем</th>
                      <th>Станция отправления</th>
                      <th>Станция назначения</th>
                      <th>Груз</th>
                      <th>Класс груза</th>
                      <th>Тип рейса</th>
                      <th>Расстояние</th>
                      <th>Дней</th>
                      <th>Ставка</th>
                      <th>Тариф</th>
                    </tr>
                    <c:forEach items="${mapReturnException}" var="total">
                      <c:forEach items="${total.value}" var="setting">
                        <tr id="contentReturnException${setting.getId()}">
                          <input id="idReturnException" value='${setting.getId()}' type="hidden" />
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="keyReturnException" value='${total.key}' />
                              <span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="idStationStringReturnException" value='${setting.getIdStationString()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="volumeGroupsStringReturnException" value='${setting.getVolumeGroupsString()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="nameStationFromReturnException" value='${setting.getStationFrom().getNameStation()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="nameStationToReturnException" value='${setting.getStationTo().getNameStation()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="nameCargoReturnException" value='${setting.getCargo().getNameCargo()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="cargoTypeStringReturnException" value='${setting.getCargoTypeString()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="routeTypeReturnException" value='${setting.getRouteType()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="distanceReturnException" value='${setting.getDistance()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="countDaysReturnException" value='${setting.getCountDays()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="rateReturnException" value='${setting.getRate()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="tariffReturnException" value='${setting.getTariff()}' /><span class="focus-border"></span></div>
                          </td>
                          <td><input type="button" onclick="updateFieldReturnException(this.parentNode.parentNode.id)" value="Сохраниеть" class="bot1" /></td>
                          <td><input type="button" id="btDeleteReturnException" onclick="deleteFieldReturnExceptions(this.parentNode.parentNode.id)" value="Удалить" / class="bot1"></td>
                        </tr>
                      </c:forEach>
                    </c:forEach>
                  </tbody>
                </table>
              </section>
              <section id="content-tab3">
                <input type="button" id="btAddBeginningException" onclick="createField(this.parentNode.id)" value="+" class="bot1" />
                <table class="table_report">
                  <tbody>
                    <tr>
                      <th>Дорога</th>
                      <th>Станция</th>
                      <th>Объем</th>
                      <th>Станция отправления</th>
                      <th>Станция назначения</th>
                      <th>Груз</th>
                      <th>Класс груза</th>
                      <th>Тип рейса</th>
                      <th>Расстояние</th>
                      <th>Дней</th>
                      <th>Ставка</th>
                      <th>Тариф</th>
                    </tr>
                    <c:forEach items="${mapBeginningException}" var="total">
                      <c:forEach items="${total.value}" var="setting">
                        <tr id="contentBeginningException${setting.getId()}">
                          <input id="idBeginningException" value='${setting.getId()}' type="hidden" />
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="keyBeginningException" value='${total.key}' />
                              <span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="idStationStringBeginningException" value='${setting.getIdStationString()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="volumeGroupsStringBeginningException" value='${setting.getVolumeGroupsString()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="nameStationFromBeginningException" value='${setting.getStationFrom().getNameStation()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="nameStationToBeginningException" value='${setting.getStationTo().getNameStation()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="nameCargoBeginningException" value='${setting.getCargo().getNameCargo()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="cargoTypeStringBeginningException" value='${setting.getCargoTypeString()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="routeTypeBeginningException" value='${setting.getRouteType()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="distanceBeginningException" value='${setting.getDistance()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="countDaysBeginningException" value='${setting.getCountDays()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="rateBeginningException" value='${setting.getRate()}' /><span class="focus-border"></span></div>
                          </td>
                          <td>
                            <div class="col-3"><input class="effect-1" type="text" id="tariffBeginningException" value='${setting.getTariff()}' /><span class="focus-border"></span></div>
                          </td>
                          <td><input type="button" onclick="updateFieldBeginningException(this.parentNode.parentNode.id)" value="Сохраниеть" class="bot1" /></td>
                          <td><input type="button" id="btDeleteBeginningException" onclick="deleteFieldBeginningExceptions(this.parentNode.parentNode.id)" value="Удалить" / class="bot1"></td>
                        </tr>
                      </c:forEach>
                    </c:forEach>
                  </tbody>
                </table>
              </section>
              <section id="content-tab4">
                <table class="table_report">
                  <tbody>
                    <tr>
                      <th>От</th>
                      <th>До</th>
                      <th>Коэффициент</th>
                    </tr>
                    <c:forEach items="${listBorderDistance}" var="setting">
                      <tr id="contentBorderDistance${setting.getId()}">
                        <input id="idBorderDistance" value='${setting.getId()}' type="hidden" />
                        <td>
                          <div class="col-3">
                            <input class="effect-1" type="text" id="distanceFromBorderDistance" value='${setting.getDistanceFrom()}' />
                            <span class="focus-border"></span>
                          </div>
                        </td>
                        <td>
                          <div class="col-3">
                            <input class="effect-1" type="text" id="distanceToBorderDistance" value='${setting.getDistanceTo()}' />
                            <span class="focus-border"></span>
                          </div>
                        </td>
                        <td>
                          <div class="col-3"><input class="effect-1" type="text" id="coefficientBorderDistance" value='${setting.getCoefficient()}' />
                            <span class="focus-border"></span></div>
                        </td>
                        <td><input type="button" onclick="updateFieldBorderDistance(this.parentNode.parentNode.id)" value="Сохранить" class="bot1" /></td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </section>
              <section id="content-tab5">
                <table class="table_report">
                  <tbody>
                    <tr>
                      <th>Имя</th>
                      <th>Значение</th>
                    </tr>
                    <c:forEach items="${listLoadUnload}" var="setting">
                      <tr id="contentLoadUnload${setting.getId()}">
                        <input id="idLoadUnload" value='${setting.getId()}' type="hidden" />
                        <td>
                          <div class="col-3"><input class="effect-1" type="text" id="nameLoadUnload" value='${setting.getName()}' readonly />
                            <span class="focus-border"></span></div>
                        </td>
                        <td>
                          <div class="col-3"><input class="effect-1" type="text" id="valueLoadUnload" value='${setting.getValue()}' />
                            <span class="focus-border"></span></div>
                        </td>
                        <td><input type="button" onclick="updateFieldLoadUnload(this.parentNode.parentNode.id)" value="Сохранить" class="bot1" /></td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </section>
              <section id="content-tab6">
                <table class="table_report">
                  <tbody>
                    <tr>
                      <th>Объем</th>
                      <th>Доходность</th>
                    </tr>
                    <c:forEach items="${listYield}" var="setting">
                      <tr id="contentYield${setting.getId()}">
                        <input id="idYield" value='${setting.getId()}' type="hidden" />
                        <td>
                          <div class="col-3"><input class="effect-1" type="text" id="volumeGroupYield" value='${setting.getVolumeGroup()}' readonly />
                            <span class="focus-border"></span></div>
                        </td>
                        <td>
                          <div class="col-3"><input class="effect-1" type="text" id="yieldYield" value='${setting.getYield()}' />
                            <span class="focus-border"></span></div>
                        </td>
                        <td><input type="button" onclick="updateFieldYield(this.parentNode.parentNode.id)" value="Сохранить" class="bot1" /></td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </section>
            </div>
          </div>

          <div align="center" class="footer">
            Create by Vladislav Klochkov. All rights reserved, <span id="copy"></span>
          </div>

        </body>

        </html>