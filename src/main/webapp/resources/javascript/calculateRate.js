function calcRate() {
  const volumeSet = ["114", "120", "122", "138", "140", "150", "158", "161"];
  var stationFrom = checkEmpty($('input[name="station_from"]').val());
  var stationTo = checkEmpty($('input[name="station_to"]').val());
  var cargo = checkEmpty($('input[name="cargo"]').val());
  var volume = checkEmpty($('input[name="volume"]').val());
  if (volumeSet.indexOf(volume) == -1) {
    alert("Некорректный объем, должен быть: " + volumeSet);
  } else {
    var file = $('input[name="ratesFile"]')
      .val()
      .split("\\")
      .pop();
    $.ajax({
      url: "rate/info",
      datatype: "json",
      type: "post",
      contentType: "application/json",
      data: JSON.stringify({
        stationFrom: stationFrom,
        stationTo: stationTo,
        cargo: cargo,
        volume: volume,
        file: file
      }),
      success: function(response) {
        $table = $('<table class="table_calculate">');
        $head1 = $("<tr>").append(
          $('<td class="td_table1" rowspan="3">').text("№п/п"),
          $('<td class="td_table1" rowspan="3">').text("Станция отправления"),
          $('<td class="td_table1" rowspan="3">').text("Дорога отпр."),
          $('<td class="td_table1" rowspan="3">').text("Станция назначения"),
          $('<td class="td_table1" rowspan="3">').text("Дорога назн."),
          $('<td class="td_table1" rowspan="3">').text("Наименование груза"),
          $('<td class="td_table1" rowspan="3">').text("Расст., км"),
          $('<td class="td_table1" rowspan="3">').text("Время в пути, сут"),
          $('<td class="td_table1" rowspan="3">').text("Погр. / выгр."),
          $('<td class="td_table1" rowspan="3">').text("Оборот, сут."),
          $('<td class="td_table1" rowspan="3">').text("ВО"),
          $('<td class="td_table1" rowspan="2">').text("ДОХОД"),
          $('<td class="td_table1">').text("РАСХОД"),
          $('<td class="td_table1" colspan="2">').text("ПРИБЫЛЬ")
        );
        $table.append($head1);

        $head2 = $("<tr>").append(
          $('<td class="td_table1">').text("Тариф в собств. вагонах"),
          $('<td class="td_table1">').text("За нахождение в пути"),
          $('<td class="td_table1">').text("В сутки")
        );
        $table.append($head2);

        $head3 = $("<tr>").append(
          $('<td class="td_table1">').text("руб/ваг."),
          $('<td class="td_table1">').text("руб/ваг."),
          $('<td class="td_table1">').text("руб/ваг."),
          $('<td class="td_table1">').text("руб/ваг/сут.")
        );
        $table.append($head3);
        var isNumberPut = false;
        var number = parseInt(window.sessionStorage.getItem("numberTableResult"));
        for (var i in response.totalList) {
          $content = $("<tr>").append(
            $('<td class="td_table2">').text(function() {
              if (!isNumberPut) {
                isNumberPut = true;
                return number;
              }
            }),
            $('<td class="td_table2">').text(function() {
              if (response.totalList[i].flagNeedCalc) {
                $(this).css({ backgroundColor: "#D6D6D6", fontWeight: "bold" });
              }
              return response.totalList[i].stationDeparture.nameStation
            }),
            $('<td class="td_table2">').text(function() {
              if (response.totalList[i].flagNeedCalc) {
                $(this).css({ backgroundColor: "#D6D6D6", fontWeight: "bold" });
              }
              return response.totalList[i].stationDeparture.road.nameRoad
            }),
            $('<td class="td_table2">').text(function() {
              if (response.totalList[i].flagNeedCalc) {
                $(this).css({ backgroundColor: "#D6D6D6", fontWeight: "bold" });
              }
              return response.totalList[i].stationDestination.nameStation
            }),
            $('<td class="td_table2">').text(function() {
              if (response.totalList[i].flagNeedCalc) {
                $(this).css({ backgroundColor: "#D6D6D6", fontWeight: "bold" });
              }
              return response.totalList[i].stationDestination.road.nameRoad
            }),
            $('<td class="td_table2">').text(
              response.totalList[i].cargo.nameCargo
            ),
            $('<td class="td_table2">').text(numberFormat(response.totalList[i].distance, 0, ',', ' ')),
            $('<td class="td_table2">').text(response.totalList[i].countDays),
            $('<td class="td_table2">').text(
              response.totalList[i].countDaysLoadAndUnload
            ),
            $('<td class="td_table2">').text(
              response.totalList[i].fullCountDays
            ),
            $('<td class="td_table2">').text("поваг"),
            $('<td class="td_table2">').text(function() {
              if (response.totalList[i].rate == 0) {
                return "";
              } else {
                if (response.totalList[i].flagNeedCalc) {
                  $(this).css({ backgroundColor: "#D6D6D6", fontWeight: "bold" });
                }
                return numberFormat(response.totalList[i].rate, 2, ',', ' ');
              }
            }),
            $('<td class="td_table2">').text(function() {
              if (response.totalList[i].tariff == 0) {
                return "";
              } else {
                return numberFormat(response.totalList[i].tariff, 2, ',', ' ');
              }
            }),
            $('<td class="td_table2">').text(function() {
              return numberFormat(response.totalList[i].rate - response.totalList[i].tariff, 2, ',', ' ');
            }),
            $('<td class="td_table2">').text("")
          );
          $table.append($content);
        }
        number = number + 1;
        window.sessionStorage.setItem("numberTableResult", number);

        $sum = $("<tr>").append(
          $('<td class="td_table3" colspan="6">').text(""),
          $('<td class="td_table3">').text(numberFormat(response.sumDistance, 0, ',', ' ')),
          $('<td class="td_table3">').text(response.sumCountDays),
          $('<td class="td_table3">').text(""),
          $('<td class="td_table3">').text(response.sumFullCountDays),
          $('<td class="td_table3">').text(""),
          $('<td class="td_table3">').text(""),
          $('<td class="td_table3">').text(""),
          $('<td class="td_table3">').text(numberFormat(response.sumRateOrTariff, 2, ',', ' '))
        );
        if (response.actualYield != null) {
          $sum.append(
            $('<td class="td_table3">').text(numberFormat(response.yield, 2, ',', ' ')),
            $('<td class="td_table2">').text(numberFormat(response.actualYield, 2, ',', ' '))
          );
        } else {
          $sum.append($('<td class="td_table3">').text(numberFormat(response.yield, 2, ',', ' ')));
        }

        $table.append($sum);
        $("#total").append($("<br>"));
        $("#total").append($table);
      },
      error: function(response) {
        message = response.responseJSON.conflictMessage;
        var code;
        if (response.responseJSON.conflictCode != null) {
          code = response.responseJSON.conflictCode;
        } else {
          code = response.responseJSON.conflictCodes;
        }
        alert(errorCodes(code) + '\n' + message);
      }
    });
  }
}