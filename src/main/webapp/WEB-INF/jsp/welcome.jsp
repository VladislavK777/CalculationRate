<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
  <title>UralTransCom|CalculateRate</title>
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
  <link href="resources/img/favicon.ico" rel="shortcut icon" type="image/x-icon">
  <link href="resources/css/basicStyle.css" rel="stylesheet" type="text/css" />
  <link href="resources/css/inputStyle.css" rel="stylesheet" type="text/css" />
  <link href="resources/css/rateStyle.css" rel="stylesheet" type="text/css" />
  <link href="resources/css/searchStyle.css" rel="stylesheet" type="text/css" />
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="resources/javascript/search.js"></script>
  <script src="resources/javascript/process.js"></script>
  <script src="resources/javascript/calculateRate.js"></script>

</head>

<body onload="init()">
  <div id="lockPane" class="lockScreenOff">
    <div class="loader" hide></div>
  </div>
  <div class="one">
    <h1>РАСЧЕТ СТАВОК</h1>
    <div class="train">
      <img src="resources/img/train.jpg" width="auto">
    </div>
  </div>
  <div>
    <!--<img class="logo" src="resources/img/logo.jpg">-->
    <div class="auth">
      <p>${user}</p>
      <c:url value="/logout" var="logoutUrl"/>
      <form action="${logoutUrl}" method="post">
        <button type="submit" class="bot1">Выход</button>
      </form>
    </div>
  </div>

  <div class="block"></div>
  <div id="divCheckMode">
    <table>
      <tr>
        <td id="mode1" class="tdOff">
          Единичный расчет
        </td>
        <td>
          <input id="switchMode" class="modeCalculate" type="checkbox" checked onclick="switchMode(this.id);">
        </td>
        <td id="mode2" class="tdOff">
          Групповой расчет
        </td>
      </tr>
    </table>
  </div>
  <div id="divModeRoot">
    <div id="divMode1" class="divModeOff">
      <table>
        <tr>
          <form enctype="multipart/form-data" method="post" id="calcRate">
              <td>
                <p class="inp">
                  <label>
                  <input type="text" autocomplete="off" id="stationFrom" placeholder="&nbsp;" name="stationFrom" onkeyup="search(this.id)"/>
                  <span class="label">Станция отправления</span>
                  <span class="border" />
                </label>
                </p>
              </td>
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
              <td>
                <p class="inp">
                  <label>
                  <input type="text" autocomplete="off" id="stationTo" placeholder="&nbsp;" name="stationTo" onkeyup="search(this.id)" />
                  <span class="label">Станция назначения</span>
                  <span class="border" />
                </label>
                </p>
              </td>
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
              <td>
                <p class="inp">
                  <label>
                  <input type="text" autocomplete="off" id="cargo" placeholder="&nbsp;" name="cargo" onkeyup="search(this.id)" />
                  <span class="label">Груз</span>
                  <span class="border" />
                </label>
                </p>
              </td>
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
              <td>
                <p class="inp">
                  <label>
                  <input type="text" autocomplete="off" placeholder="&nbsp;" name="volume" />
                  <span class="label">Объем</span>
                  <span class="border" />
                </label>
                </p>
              </td>
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
              <td>
                <p class="inp_file">
                  <label>
                  <input type="file" name="ratesFile" accept="xlsx" />
                  <span class="label">Файл ставок</span>
                  <span class="border" />
                </label>
                </p>
              </td>
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
              <td>
                <input type="button" onclick="calcRate(event)" class="bot1" value="Рассчитать ставку" />
              </td>
          </form>
          <td>
            <input type="button" onclick="reload()" class="bot1" value="Сбросить" />
          </td>
          <td>
            <form action="export" method="get" id="calc">
              <input type="image" form="calc" src="resources/img/excel.png" width="40px" height="40px" />
            </form>
          </td>
          <!--<td>
            <a href="settings"><img class="setting" src="resources/img/setting.png" /></a>
          </td>-->
          <c:if test="${role == '[ROLE_ADMIN]'}">
            <td>
             <a href="settings"><img class="setting" src="resources/img/setting.png" /></a>
            </td>
        </c:if>
        </tr>
      </table>
    </div>
    <div id="divMode2" class="divModeOff">
      <table>
        <tr>
          <form enctype="multipart/form-data" method="post" id="groupCalcRate">
              <td>
                <p class="inp_file">
                  <label>
                      <input type="file" name="ratesGroupFile" accept="xlsx" />
                      <span class="label">Файл ставок</span>
                      <span class="border" />
                    </label>
                </p>
              </td>
              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
              <td>
                <input type="button" onclick="calcGroupRate(event)" class="bot1" value="Рассчитать ставки" />
              </td>
          </form>
          <td>
            <input type="button" onclick="reload()" class="bot1" value="Сбросить" />
          </td>
          <td>
            <form action="exportGroup" method="get" id="groupCalc">
              <input type="image" form="groupCalc" src="resources/img/excel.png" width="40px" height="40px" />
            </form>
          </td>
          <td>&nbsp;&nbsp;&nbsp;</td>
          <td>
            <form action="downloadTemplate" method="get" id="template">
              <input type="image" form="template" src="resources/img/excel.png" width="40px" height="40px" />
            </form>
            <p class="temp">Шаблон</p>
          </td>
          <!--<td>
            <a href="settings"><img class="setting" src="resources/img/setting.png" /></a>
          </td>-->
          <c:if test="${role == '[ROLE_ADMIN]'}">
                <td>
                    <a href="settings"><img class="setting" src="resources/img/setting.png" /></a>
                </td>
            </c:if>
        </tr>
      </table>
    </div>
  </div>
  <div id="total"></div>
  <br><br>
  <div align="center" class="footer">
    Create by Vladislav Klochkov. All rights reserved, <span id="copy"></span>
  </div>
</body>

</html>