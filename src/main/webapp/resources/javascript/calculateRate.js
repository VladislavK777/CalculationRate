function calcRate() {
  volumeSet = ["114", "120", "122", "138", "140", "150", "158", "161"];
  input1 = $('input[name="station_from"]').val();
  if (input1 == "") {
    alert('Поле "Станция отправления" должно быть заполнено.');
    $('input[name="station_from"]').focus();
  } else {
    input2 = $('input[name="station_to"]').val();
    if (input2 == "") {
      alert('Поле "Станция назначения" должно быть заполнено.');
      $('input[name="station_to"]').focus();
    } else {
      input3 = $('input[name="cargo"]').val();
      if (input3 == "") {
        alert('Поле "Груз" должно быть заполнено.');
        $('input[name="cargo"]').focus();
      } else {
        input4 = $('input[name="volume"]').val();
        if (input4 == "") {
          alert('Поле "Объем" должно быть заполнено.');
          $('input[name="volume"]').focus();
        } else {
          if (volumeSet.indexOf(input4) == -1) {
            alert("Некорректный объем, должен быть: " + volumeSet);
          } else {
            $.ajax({
              url: "rate/info",
              datatype: "json",
              type: "post",
              contentType: "application/json",
              data: JSON.stringify({
                stationFrom: input1,
                stationTo: input2,
                cargo: input3,
                volume: input4
              }),
              success: function(response) {
                $table = $('<table class="table_calculate">');
                $head1 = $("<tr>").append(
                  $('<td class="td_table1" rowspan="3">').text(
                    "Станция отправления"
                  ),
                  $('<td class="td_table1" rowspan="3">').text(
                    "Дорога отправления"
                  ),
                  $('<td class="td_table1" rowspan="3">').text(
                    "Станция назначения"
                  ),
                  $('<td class="td_table1" rowspan="3">').text(
                    "Дорога назначения"
                  ),
                  $('<td class="td_table1" rowspan="3">').text(
                    "Наименование груза"
                  ),
                  $('<td class="td_table1" rowspan="3">').text("Расст., км"),
                  $('<td class="td_table1" rowspan="3">').text(
                    "Время в пути, сут"
                  ),
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
                for (var i in response.totalList) {
                  $content = $("<tr>").append(
                    $('<td class="td_table2">').text(
                      response.totalList[i].stationDeparture.nameStation
                    ),
                    $('<td class="td_table2">').text(
                      response.totalList[i].stationDeparture.road.nameRoad
                    ),
                    $('<td class="td_table2">').text(
                      response.totalList[i].stationDestination.nameStation
                    ),
                    $('<td class="td_table2">').text(
                      response.totalList[i].stationDestination.road.nameRoad
                    ),
                    $('<td class="td_table2">').text(
                      response.totalList[i].cargo.nameCargo
                    ),
                    $('<td class="td_table2">').text(
                      response.totalList[i].distance
                    ),
                    $('<td class="td_table2">').text(
                      response.totalList[i].countDays
                    ),
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
                          $(this).css({ backgroundColor: "#ffa07a" });
                        }
                        return response.totalList[i].rate;
                      }
                    }),
                    $('<td class="td_table2">').text(function() {
                      if (response.totalList[i].tariff == 0) {
                        return "";
                      } else {
                        return response.totalList[i].tariff;
                      }
                    }),
                    $('<td class="td_table2">').text(function() {
                      return (
                        response.totalList[i].rate + response.totalList[i].tariff
                      );
                    }),
                    $('<td class="td_table2">').text("")
                  );
                  $table.append($content);
                }

                $sum = $("<tr>").append(
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

                $table.append($sum);
                $("#total").append($("<br>"));
                $("#total").append($table);
              }
            });
          }
        }
      }
    }
  }
}