<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
  <title>UralTransCom|DynamicDistributionPark</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <script async src="http://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="resources/javascript/search.js"></script>
  <script src="resources/javascript/process.js"></script>
  <link href="resources/css/style.css" rel="stylesheet" type="text/css" />
  <link rel="shortcut icon" href="resources/favicon.ico" type="image/x-icon">

</head>

<body>

  <script>
    function calcRate() {
      var input1 = $('input[name="station_from"]').val();
      var input2 = $('input[name="station_to"]').val();
      var input3 = $('input[name="cargo"]').val();
      var input4 = $('input[name="volume"]').val();
      $.ajax({
        url: 'rate/info',
        datatype: 'json',
        type: "post",
        contentType: "application/json",
        data: JSON.stringify({
          stationFrom: input1,
          stationTo: input2,
          cargo: input3,
          volume: input4
        }),
        success: function(response) {
          for (var i in response.totalList) {
            $content = $('<tr>').append(
              $('<td class="td_table2">').text(response.totalList[i].stationDeparture.nameStation),
              $('<td class="td_table2">').text(response.totalList[i].stationDeparture.road.nameRoad),
              $('<td class="td_table2">').text(response.totalList[i].stationDestination.nameStation),
              $('<td class="td_table2">').text(response.totalList[i].stationDestination.road.nameRoad),
              $('<td class="td_table2">').text(response.totalList[i].cargo.nameCargo),
              $('<td class="td_table2">').text(response.totalList[i].distance),
              $('<td class="td_table2">').text(response.totalList[i].countDays),
              $('<td class="td_table2">').text(response.totalList[i].countDaysLoadAndUnload),
              $('<td class="td_table2">').text(response.totalList[i].fullCountDays),
              $('<td class="td_table2">').text('поваг'),
              $('<td class="td_table2">').text(
                function() {
                  if (response.totalList[i].rate == 0) {
                    return ''
                  } else {
                    return response.totalList[i].rate
                  }
                }
              ),
              $('<td class="td_table2">').text(
                function() {
                  if (response.totalList[i].tariff == 0) {
                    return ''
                  } else {
                    return response.totalList[i].tariff
                  }
                }
              ),
              $('<td class="td_table2">').text(
                function() {
                  return response.totalList[i].rate + response.totalList[i].tariff
                }
              ),
              $('<td class="td_table2">').text("")
            );
            $('#total').append($content);
          }
          $sum = $('<tr>').append(
            $('<td class="td_table3" colspan="5">').text(""),
            $('<td class="td_table3">').text(response.sumDistance),
            $('<td class="td_table3">').text(response.sumCountDays),
            $('<td class="td_table3">').text(""),
            $('<td class="td_table3">').text(response.sumFullCountDays),
            $('<td class="td_table3">').text(""),
            $('<td class="td_table3">').text(""),
            $('<td class="td_table3">').text(""),
            $('<td class="td_table3">').text(response.sumRateOrTariff),
            $('<td class="td_table3">').text(response.yield)
          );
          $('#total').append($sum);
        }
      });
    }
  </script>
  <div class="one">
    <h1>РАСЧЕТ СТАВОК</h1>
    <div class="train">
    		<img src="resources/train.jpg" width="auto">
    </div>
</div>
<div>
    <img class="logo" src="resources/logo.jpg">
</div>
<br><br><br><br><br><br><br><br><br><br>
<div>
    <table>
      <tr>
        <td>
          <p for="inp" class="inp">
            <label>
              <input type="text" id="stationFrom" placeholder="&nbsp;" name="station_from" onkeyup="search(this.id)"/>
              <span class="label">Станция отправления</span>
              <span class="border" />
            </label>
          </p>
        </td>
        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td>
          <p for="inp" class="inp">
            <label>
              <input type="text" id="stationTo" placeholder="&nbsp;" name="station_to" onkeyup="search(this.id)"/>
              <span class="label">Станция назначения</span>
              <span class="border" />
            </label>
          </p>
        </td>
        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td>
          <p for="inp" class="inp">
            <label>
              <input type="text" id="cargo" placeholder="&nbsp;" name="cargo" onkeyup="search(this.id)"/>
              <span class="label">Груз</span>
              <span class="border" />
            </label>
          </p>
        </td>
        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td>
          <p for="inp" class="inp">
            <label>
              <input type="text" placeholder="&nbsp;" name="volume" onkeyup="search(this.id)"/>
              <span class="label">Объем</span>
              <span class="border" />
            </label>
          </p>
        </td>
        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td>
            <input type="button" onclick="calcRate()" class="bot1" value="Рассчитать ставку" />
        </td>
        <td>
            <input type="button" onclick="reloadPage()" class="bot1" value="Сбросить" />
        </td>
        <td>
            <form action="export" method="get" id="calc">
                <input type="image" form="calc" src="resources/excel.png" width="40px" height="40px" />
            </form>
        </td>
        <td>
            <a href="settings"><img class="setting" src="resources/setting.png"></a>
        </td>
      </tr>
    </table>
  </div>
  <div>
    <table class="table_calculate" id="total">
      <tbody>
        <tr>
          <td class="td_table1" rowspan="3">Станция отправления</td>
          <td class="td_table1" rowspan="3">Дорога отправления</td>
          <td class="td_table1" rowspan="3">Станция назначения</td>
          <td class="td_table1" rowspan="3">Дорога назначения</td>
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
      </tbody>
    </table>
  </div>
  <div>

</div>
<div align="center" class="footer">
  Create by Vladislav Klochkov. All rights reserved, <span id="copy"></span>
</div>
</body>

</html>
