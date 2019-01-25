<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
  <title>UralTransCom|CalculateRate|Setting</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <link href="resources/css/style.css" rel="stylesheet" type="text/css" />
  <link href="resources/favicon.ico" rel="shortcut icon" type="image/x-icon">
  <script async src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://unpkg.com/react@16/umd/react.production.min.js"></script>
  <script src="https://unpkg.com/react-dom@16/umd/react-dom.production.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/babel-standalone/6.26.0/babel.js"></script>
  <script src="resources/javascript/operationUpdate.js"></script>
  <script src="resources/javascript/operationDelete.js"></script>
  <script src="resources/javascript/operationInsert.js"></script>
  <script src="resources/javascript/search.js"></script>
  <script src="resources/javascript/process.js"></script>

</head>

<body onload="cop()">
  <div class="one">
    <h1>РАСЧЕТ СТАВОК|НАСТРОЙКИ</h1>
    <div class="train">
      <img src="resources/train.jpg" width="auto">
    </div>
  </div>
  <div>
    <img class="logo" src="resources/logo.jpg">
  </div>
  <div class="block"></div>
  <div>
    <a href="/calculaterate"><input type="button" value="Закрыть настрйки" class="bot1"/></a>
    <div class="tabs">
      <input id="tab1" type="radio" name="tabs" onclick="reloadPage(this.id)" checked>
      <label for="tab1" title="Станции возврата">Станции возврата</label>

      <input id="tab2" type="radio" name="tabs" onclick="reloadPage(this.id)">
      <label for="tab2" title="Возвратные исключения">Возвратные исключения</label>

      <input id="tab3" type="radio" name="tabs" onclick="reloadPage(this.id)">
      <label for="tab3" title="Начальные исключения">Начальные исключения</label>

      <input id="tab4" type="radio" name="tabs" onclick="reloadPage(this.id)">
      <label for="tab4" title="Коэффициент дистанции">Коэффициент дистанции</label>

      <input id="tab5" type="radio" name="tabs" onclick="reloadPage(this.id)">
      <label for="tab5" title="Оборотодней">Оборотодней</label>

      <input id="tab6" type="radio" name="tabs" onclick="reloadPage(this.id)">
      <label for="tab6" title="Доходность">Доходность</label>

      <section id="contentReturnStations">
        <input type="button" id="btAddReturnStations" onclick="createField(this.id)" value="Добавить условие" class="bot1" />
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
                <tr id="contReturnStations${setting.getId()}">
                  <input id="idReturnStations" value='${setting.getId()}' type="hidden" />
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" id="road${setting.getId()}" value='${total.key}' onkeyup="search(this.id)" />
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
                    <div class="col-3"><input class="effect-1" type="text" id="station${setting.getId()}" value='${setting.getNameStationReturn()}' onkeyup="search(this.id)" />
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
      <section id="contentReturnException">
        <input type="button" id="btAddReturnException" onclick="createField(this.id)" value="Добавить условие" class="bot1" />
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
                <tr id="contReturnException${setting.getId()}">
                  <input id="idReturnException" value='${setting.getId()}' type="hidden" />
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" id="road${setting.getId()}" value='${total.key}' onkeyup="search(this.id)" />
                      <span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" id="idStationStringReturnException" value='${setting.getIdStationString()}' /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" id="volumeGroupsStringReturnException" value='${setting.getVolumeGroupsString()}' /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" id="stationFrom${setting.getId()}" value='${setting.getStationFrom().getNameStation()}' onkeyup="search(this.id)" /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" id="stationTo${setting.getId()}" value='${setting.getStationTo().getNameStation()}' onkeyup="search(this.id)" /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" id="cargo${setting.getId()}" value='${setting.getCargo().getNameCargo()}' onkeyup="search(this.id)" /><span class="focus-border"></span></div>
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
                  <td><input type="button" onclick="updateFieldReturnException(this.parentNode.parentNode.id)" value="Сохранить" class="bot1" /></td>
                  <td><input type="button" id="btDeleteReturnException" onclick="deleteFieldReturnExceptions(this.parentNode.parentNode.id)" value="Удалить" / class="bot1"></td>
                </tr>
              </c:forEach>
            </c:forEach>
          </tbody>
        </table>
      </section>
      <section id="contentBeginningException">
        <input type="button" id="btAddBeginningException" onclick="createField(this.id)" value="Добавить условие" class="bot1" />
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
                <tr id="contBeginningException${setting.getId()}">
                  <input id="idBeginningException" value='${setting.getId()}' type="hidden" />
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" id="road${setting.getId()}" value='${total.key}' onkeyup="search(this.id)" />
                      <span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" id="idStationStringBeginningException" value='${setting.getIdStationString()}' /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" id="volumeGroupsStringBeginningException" value='${setting.getVolumeGroupsString()}' /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" id="stationFrom${setting.getId()}" value='${setting.getStationFrom().getNameStation()}' onkeyup="search(this.id)" /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" id="stationTo${setting.getId()}" value='${setting.getStationTo().getNameStation()}' onkeyup="search(this.id)" /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" id="cargo${setting.getId()}" value='${setting.getCargo().getNameCargo()}' onkeyup="search(this.id)" /><span class="focus-border"></span></div>
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
                  <td><input type="button" onclick="updateFieldBeginningException(this.parentNode.parentNode.id)" value="Сохранить" class="bot1" /></td>
                  <td><input type="button" id="btDeleteBeginningException" onclick="deleteFieldBeginningExceptions(this.parentNode.parentNode.id)" value="Удалить" / class="bot1"></td>
                </tr>
              </c:forEach>
            </c:forEach>
          </tbody>
        </table>
      </section>
      <section id="contentBorderDistance">
        <table class="table_report">
          <tbody>
            <tr>
              <th>От</th>
              <th>До</th>
              <th>Коэффициент</th>
            </tr>
            <c:forEach items="${listBorderDistance}" var="setting">
              <tr id="contBorderDistance${setting.getId()}">
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
      <section id="contentLoadUnload">
        <table class="table_report">
          <tbody>
            <tr>
              <th>Имя</th>
              <th>Значение</th>
            </tr>
            <c:forEach items="${listLoadUnload}" var="setting">
              <tr id="contLoadUnload${setting.getId()}">
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
      <section id="contentYield">
        <table class="table_report">
          <tbody>
            <tr>
              <th>Объем</th>
              <th>Доходность</th>
            </tr>
            <c:forEach items="${listYield}" var="setting">
              <tr id="contYield${setting.getId()}">
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
  <br><br>
  <div align="center" class="footer">
    Create by Vladislav Klochkov. All rights reserved, <span id="copy"></span>
  </div>

</body>

</html>
