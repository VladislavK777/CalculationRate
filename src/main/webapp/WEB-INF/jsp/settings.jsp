<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
  <title>UralTransCom|CalculateRate|Setting</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <link href="resources/css/style.css" rel="stylesheet" type="text/css" />
  <link href="resources/img/favicon.ico" rel="shortcut icon" type="image/x-icon">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://unpkg.com/react@16/umd/react.production.min.js"></script>
  <script src="https://unpkg.com/react-dom@16/umd/react-dom.production.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/babel-standalone/6.26.0/babel.js"></script>
  <script src="resources/javascript/operationUpdate.js"></script>
  <script src="resources/javascript/operationDelete.js"></script>
  <script src="resources/javascript/operationInsert.js"></script>
  <script src="resources/javascript/operationClone.js"></script>
  <script src="resources/javascript/search.js"></script>
  <script src="resources/javascript/process.js"></script>

</head>

<body onload="initSetting()">
  <div class="one">
    <h1>РАСЧЕТ СТАВОК|НАСТРОЙКИ</h1>
    <div class="train">
      <img src="resources/img/train.jpg" width="auto">
    </div>
  </div>
  <div>
    <img class="logo" src="resources/img/logo.jpg">
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

      <input id="tab7" type="radio" name="tabs" onclick="reloadPage(this.id)">
      <label for="tab7" title="Другое">Другое</label>

      <section id="contentReturnStations">
        <input type="button" id="btAddReturnStation" onclick="createField(this.id)" value="Добавить условие" class="bot1" />
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
                <tr id="contReturnStation${setting.getId()}">
                  <input id="idReturnStation" value='${setting.getId()}' type="hidden" />
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="roadReturnStationSetting${setting.getId()}" value='${total.key}' onkeyup="search(this.id)" />
                      <span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="idStationStringReturnStation" value='${setting.getIdsStationString()}' />
                      <span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3__list">
                      <div class="wrapper">
                        <input class="effect-1__list" type="text" autocomplete="off" id="volumeGroupsStringReturnStation" value='${setting.getVolumeGroupsString()}' />
                        <span class="focus-border"></span>
                        <div class="check-list">
                          <ul>
                            <li>
                              <input type="checkbox" value="120" class="check-list__checkbox" />120</li>
                            <li>
                              <input type="checkbox" value="138" class="check-list__checkbox" />138</li>
                            <li>
                              <input type="checkbox" value="150" class="check-list__checkbox" />150</li>
                          </ul>
                        </div>
                      </div>
                    </div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="stationReturnStation${setting.getId()}" value='${setting.getNameStationReturn()}' onkeyup="search(this.id)" />
                      <span class="focus-border"></span></div>
                  </td>
                  <td><input type="button" id="btCloneReturnStation" onclick="cloneFieldReturnStation(this.parentNode.parentNode.id)" value="Клонировать" class="bot1"></td>
                  <td><input type="button" id="btEditReturnStation" onclick="updateFieldReturnStation(this.parentNode.parentNode.id)" value="Сохранить" class="bot1"></td>
                  <td><input type="button" id="btDeleteReturnStation" onclick="deleteFieldReturnStation(this.parentNode.parentNode.id)" value="Удалить" class="bot1" /></td>
                </tr>
              </c:forEach>
            </c:forEach>
          </tbody>
        </table>
      </section>
      <section id="contentReturnExceptions">
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
            <c:forEach items="${mapReturnExceptions}" var="total">
              <c:forEach items="${total.value}" var="setting">
                <tr id="contReturnException${setting.getId()}">
                  <input id="idReturnException" value='${setting.getId()}' type="hidden" />
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="roadReturnExceptionSetting${setting.getId()}" value='${total.key}' onkeyup="search(this.id)" />
                      <span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="idStationStringReturnException" value='${setting.getIdsStationString()}' /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3__list">
                      <div class="wrapper">
                        <input class="effect-1__list" type="text" autocomplete="off" id="volumeGroupsStringReturnException" value='${setting.getVolumeGroupsString()}' />
                        <span class="focus-border"></span>
                        <div class="check-list">
                          <ul>
                            <li>
                              <input type="checkbox" value="120" class="check-list__checkbox" />120</li>
                            <li>
                              <input type="checkbox" value="138" class="check-list__checkbox" />138</li>
                            <li>
                              <input type="checkbox" value="150" class="check-list__checkbox" />150</li>
                          </ul>
                        </div>
                      </div>
                    </div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="stationFromReturnException${setting.getId()}" value='${setting.getStationFrom().getNameStation()}' onkeyup="search(this.id)" /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="stationToReturnException${setting.getId()}" value='${setting.getStationTo().getNameStation()}' onkeyup="search(this.id)" /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="cargoReturnException${setting.getId()}" value='${setting.getCargo().getNameCargo()}' onkeyup="search(this.id)" /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3__list">
                      <div class="wrapper">
                        <input class="effect-1__list" type="text" autocomplete="off" id="cargoTypeStringReturnException" value='${setting.getCargoTypeString()}' />
                        <span class="focus-border"></span>
                        <div class="check-list">
                          <ul>
                            <li>
                              <input type="checkbox" value="1" class="check-list__checkbox" />1</li>
                            <li>
                              <input type="checkbox" value="2" class="check-list__checkbox" />2</li>
                            <li>
                              <input type="checkbox" value="3" class="check-list__checkbox" />3</li>
                          </ul>
                        </div>
                      </div>
                    </div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="routeTypeReturnException${setting.getId()}" value='${setting.getRouteType()}' onfocus="showList(this.id)" onblur="hiddenList()" list="list" /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="distanceReturnException" value='${setting.getDistance()}' /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="countDaysReturnException" value='${setting.getCountDays()}' /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="rateReturnException" value='${setting.getRate()}' /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="tariffReturnException" value='${setting.getTariff()}' /><span class="focus-border"></span></div>
                  </td>
                  <td><input type="button" id="btCloneReturnException" onclick="cloneFieldReturnException(this.parentNode.parentNode.id)" value="Клонировать" class="bot1"></td>
                  <td><input type="button" id="btEditReturnException" onclick="updateFieldReturnException(this.parentNode.parentNode.id)" value="Сохранить" class="bot1" /></td>
                  <td><input type="button" id="btDeleteReturnException" onclick="deleteFieldReturnException(this.parentNode.parentNode.id)" value="Удалить" / class="bot1"></td>
                </tr>
              </c:forEach>
            </c:forEach>
          </tbody>
        </table>
      </section>
      <section id="contentBeginningExceptions">
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
            <c:forEach items="${mapBeginningExceptions}" var="total">
              <c:forEach items="${total.value}" var="setting">
                <tr id="contBeginningException${setting.getId()}">
                  <input id="idBeginningException" value='${setting.getId()}' type="hidden" />
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="roadBeginningExceptionSetting${setting.getId()}" value='${total.key}' onkeyup="search(this.id)" />
                      <span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="idStationStringBeginningException" value='${setting.getIdsStationString()}' /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3__list">
                      <div class="wrapper">
                        <input class="effect-1__list" type="text" autocomplete="off" id="volumeGroupsStringBeginningException" value='${setting.getVolumeGroupsString()}' />
                        <span class="focus-border"></span>
                        <div class="check-list">
                          <ul>
                            <li>
                              <input type="checkbox" value="120" class="check-list__checkbox" />120</li>
                            <li>
                              <input type="checkbox" value="138" class="check-list__checkbox" />138</li>
                            <li>
                              <input type="checkbox" value="150" class="check-list__checkbox" />150</li>
                          </ul>
                        </div>
                      </div>
                    </div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="stationFromBeginningException${setting.getId()}" value='${setting.getStationFrom().getNameStation()}' onkeyup="search(this.id)" /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="stationToBeginningException${setting.getId()}" value='${setting.getStationTo().getNameStation()}' onkeyup="search(this.id)" /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="cargoBeginningException${setting.getId()}" value='${setting.getCargo().getNameCargo()}' onkeyup="search(this.id)" /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3__list">
                      <div class="wrapper">
                        <input class="effect-1__list" type="text" autocomplete="off" id="cargoTypeStringBeginningException" value='${setting.getCargoTypeString()}' />
                        <span class="focus-border"></span>
                        <div class="check-list">
                          <ul>
                            <li>
                              <input type="checkbox" value="1" class="check-list__checkbox" />1</li>
                            <li>
                              <input type="checkbox" value="2" class="check-list__checkbox" />2</li>
                            <li>
                              <input type="checkbox" value="3" class="check-list__checkbox" />3</li>
                          </ul>
                        </div>
                      </div>
                    </div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="routeTypeBeginningException${setting.getId()}" value='${setting.getRouteType()}' onfocus="showList(this.id)" onblur="hiddenList()" list="list" /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="distanceBeginningException" value='${setting.getDistance()}' /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="countDaysBeginningException" value='${setting.getCountDays()}' /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="rateBeginningException" value='${setting.getRate()}' /><span class="focus-border"></span></div>
                  </td>
                  <td>
                    <div class="col-3"><input class="effect-1" type="text" autocomplete="off" id="tariffBeginningException" value='${setting.getTariff()}' /><span class="focus-border"></span></div>
                  </td>
                  <td><input type="button" id="btCloneBeginningException" onclick="cloneFieldBeginningException(this.parentNode.parentNode.id)" value="Клонировать" class="bot1"></td>
                  <td><input type="button" id="btEditBeginningException" onclick="updateFieldBeginningException(this.parentNode.parentNode.id)" value="Сохранить" class="bot1" /></td>
                  <td><input type="button" id="btDeleteBeginningException" onclick="deleteFieldBeginningException(this.parentNode.parentNode.id)" value="Удалить" / class="bot1"></td>
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
                    <input class="effect-1" type="text" autocomplete="off" id="distanceFromBorderDistance" value='${setting.getDistanceFrom()}' />
                    <span class="focus-border"></span>
                  </div>
                </td>
                <td>
                  <div class="col-3">
                    <input class="effect-1" type="text" autocomplete="off" id="distanceToBorderDistance" value='${setting.getDistanceTo()}' />
                    <span class="focus-border"></span>
                  </div>
                </td>
                <td>
                  <div class="col-3">
                    <input class="effect-1" type="text" autocomplete="off" id="coefficientBorderDistance" value='${setting.getCoefficient()}' />
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
                  <div class="col-3"><input class="effect-1" autocomplete="off" type="text" id="nameLoadUnload" value='${setting.getName()}' readonly />
                    <span class="focus-border"></span></div>
                </td>
                <td>
                  <div class="col-3"><input class="effect-1" autocomplete="off" type="text" id="valueLoadUnload" value='${setting.getValue()}' />
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
                  <div class="col-3"><input class="effect-1" autocomplete="off" type="text" id="volumeGroupYield" value='${setting.getVolumeGroup()}' readonly />
                    <span class="focus-border"></span></div>
                </td>
                <td>
                  <div class="col-3"><input class="effect-1" autocomplete="off" type="text" id="yieldYield" value='${setting.getYield()}' />
                    <span class="focus-border"></span></div>
                </td>
                <td><input type="button" onclick="updateFieldYield(this.parentNode.parentNode.id)" value="Сохранить" class="bot1" /></td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </section>
      <section id="contentOther">
        <table class="table_report">
          <tbody>
            <tr>
              <th>Параметр1</th>
              <th>Параметр2</th>
              <th>Значение</th>
            </tr>
            <c:forEach items="${listOther}" var="setting">
              <tr id="contOther${setting.getId()}">
                <input id="idOther" value='${setting.getId()}' type="hidden" />
                <td>
                  <div class="col-3"><input class="effect-1" autocomplete="off" type="text" id="nameOther" value='${setting.getName()}' readonly />
                    <span class="focus-border"></span></div>
                </td>
                <td>
                  <div class="col-3"><input class="effect-1" autocomplete="off" type="text" id="volumeOther" value='${setting.getVolume()}' readonly />
                    <span class="focus-border"></span></div>
                </td>
                <td>
                  <div class="col-3"><input class="effect-1" autocomplete="off" type="text" id="valueOther" value='${setting.getValue()}' />
                    <span class="focus-border"></span></div>
                </td>
                <td><input type="button" onclick="updateFieldOther(this.parentNode.parentNode.id)" value="Сохранить" class="bot1" /></td>
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