<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

  <div class="one">
    <h1>РАСЧЕТ СТАВОК</h1>
    <div class="train">
      <img src="resources/img/train.jpg" width="auto">
    </div>
  </div>
  <div>
    <img class="logo" src="resources/img/logo.jpg">
  </div>
  <div class="block"></div>
  <div>
    <table>
      <tr>
        <td>
          <p class="inp">
            <label>
              <input type="text" autocomplete="off" id="stationFrom" placeholder="&nbsp;" name="station_from" onkeyup="search(this.id)"/>
              <span class="label">Станция отправления</span>
              <span class="border" />
            </label>
          </p>
        </td>
        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td>
          <p class="inp">
            <label>
              <input type="text" autocomplete="off" id="stationTo" placeholder="&nbsp;" name="station_to" onkeyup="search(this.id)" />
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
              <input type="file" name="ratesFile" multiple accept="xlsx" />
              <span class="label">Файл ставок</span>
              <span class="border" />
            </label>
          </p>
        </td>
        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td>
          <input type="button" onclick="calcRate()" class="bot1" value="Рассчитать ставку" />
        </td>
        <td>
          <input type="button" onclick="reload()" class="bot1" value="Сбросить" />
        </td>
        <td>
          <form action="export" method="get" id="calc">
            <input type="image" form="calc" src="resources/img/excel.png" width="40px" height="40px" />
          </form>
        </td>
        <td>
          <a href="settings"><img class="setting" src="resources/img/setting.png" /></a>
        </td>
      </tr>
    </table>
  </div>
  <div id="total"></div>
  <br><br>
  <div align="center" class="footer">
    Create by Vladislav Klochkov. All rights reserved, <span id="copy"></span>
  </div>
</body>

</html>
