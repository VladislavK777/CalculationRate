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

  <div class="one">
    <h1>РАСЧЕТ СТАВОК</h1>
    <div class="train">
      <img src="resources/img/train.jpg" width="auto">
    </div>
  </div>
  <div>
    <img class="logo" src="resources/img/logo.jpg">
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
			<td id="mode1" class="tdOn">
				Единичный расчет
			</td>
			<td>
				<input id="switchMode" class="modeCalculate" type="checkbox" onclick="switchMode(this.id);">
			</td>
			<td id="mode2" class="tdOff">
				Групповой расчет
			</td>
		</tr>
	  </table>
  </div>
  <div id="divModeRoot">
  <div id="divMode1" class="divModeOn">
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
        <c:if test="${role == '[ROLE_ADMIN]'}">
            <td>
             <a href="settings"><img class="setting" src="resources/img/setting.png" /></a>
            </td>
        </c:if>
      </tr>
    </table>
  </div>
  <div  id="divMode2" class="divModeOff" style="padding: 23 0">
		  <table>
			  <tr>
			    <td class="tdOn">
					Файл данных
				</td>
				<form enctype="multipart/form-data" method="post" action="group">
				<td>
					<input type="file" name="file" multiple accept="xlsx" />
				</td>
				<td>
					<input type="submit" class="bot1" value="Расчитать ставки" />
				</td>
				<td>
					<input type="button" onclick="reload()" class="bot1" value="Сбросить" />
				</td>
				</form>
				<td>
					<form action="exportGroup" method="get" id="groupCalc">
						<input type="image" form="groupCalc" src="resources/img/excel.png" width="40px" height="40px" />
					</form>
				</td>
				<c:if test="${role == '[ROLE_ADMIN]'}">
					<td>
						<a href="settings"><img class="setting" src="resources/img/setting.png" /></a>
					</td>
				</c:if>
			  </tr>
		   </table>
      <c:if test="${!empty error}">
		  <table>
			  <c:forEach items="${error}" var="errorList">
				  <tr>
					  <td class="tdOn">${errorList}</td>
				  </tr>
			  </c:forEach>
		  </table>
	  </c:if>
  </div>
  </div>
  <div id="total"></div>
  <br><br>
  <div align="center" class="footer">
    Create by Vladislav Klochkov. All rights reserved, <span id="copy"></span>
  </div>
</body>

</html>
