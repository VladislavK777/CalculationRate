function cop() {
  document.getElementById("copy").innerText = new Date().getFullYear();
}

function check(valueYield, id) {
  var yield = document.getElementById(id).querySelector("#yieldYield");
  var bt = document.getElementById(id).querySelector("#btEditYield");
  if (valueYield != yield.value) {
    bt.value = "Сохранить";
    bt.setAttribute("onclick", "update('" + id + "','" + yield.value + "');");
  } else {
    bt.value = "Отмена";
  }
}

function showPopup() {
  $(popup).fadeIn(800);
}

function createInput(id, name, isCallSearch) {
  var p = document.createElement("p");
  p.className = "inp";
  var label = document.createElement("label");
  var input = document.createElement("input");
  input.type = "text";
  input.id = id;
  input.placeholder = "\xa0";
  if (isCallSearch) {
    input.onkeyup = function() {
      search(input.id);
    };
  }
  var span_first = document.createElement("span");
  span_first.textContent = name;
  span_first.className = "label";
  var span_second = document.createElement("span");
  span_second.className = "border";
  label.appendChild(input);
  label.appendChild(span_first);
  label.appendChild(span_second);
  p.appendChild(label);
  return p;
}

function createField(id) {
  var context = document.getElementById(id);
  var div_head = document.createElement("div");
  div_head.id = "popup";
  div_head.style =
    "position: absolute; height: 100%; width: 100%; top: 0; left: 0; display: none;";
  var div_subdiv_head1 = document.createElement("div");
  div_subdiv_head1.id = "popup_bg";
  div_subdiv_head1.style =
    "background: rgba(0, 0, 0, 0.2); position: absolute; z-index: 1; height: 100%; width: 100%;";
  var div_subdiv_head2 = document.createElement("div");
  div_subdiv_head2.className = "form";
  var button = document.createElement("input");
  button.type = "button";
  button.className = "bot1";
  button.value = "Сохранить";

  if (id == "btAddReturnStations") {
    div_subdiv_head2.appendChild(createInput("road", "Дорога", true));
    div_subdiv_head2.appendChild(
      createInput("stationList", "Список станций", false)
    );
    div_subdiv_head2.appendChild(
      createInput("volume", "Группа объемов", false)
    );
    div_subdiv_head2.appendChild(
      createInput("station", "Станция возврата", true)
    );
    button.onclick = function() {
      addReturnStations(context.parentNode.id);
    };
  } else {
    div_subdiv_head2.appendChild(createInput("road", "Дорога", true));
    div_subdiv_head2.appendChild(
      createInput("stationList", "Список станций", false)
    );
    div_subdiv_head2.appendChild(
      createInput("volume", "Группа объемов", false)
    );
    div_subdiv_head2.appendChild(
      createInput("stationFrom", "Станция отправления", true)
    );
    div_subdiv_head2.appendChild(
      createInput("stationTo", "Станция назначения", true)
    );
    div_subdiv_head2.appendChild(createInput("cargo", "Груз", true));
    div_subdiv_head2.appendChild(
      createInput("cargoClass", "Класс груза", false)
    );
    div_subdiv_head2.appendChild(createInput("typeRoute", "Тип рейса", false));
    div_subdiv_head2.appendChild(createInput("distance", "Расстояние", false));
    div_subdiv_head2.appendChild(createInput("countDays", "Дней", false));
    div_subdiv_head2.appendChild(createInput("rate", "Ставка", false));
    div_subdiv_head2.appendChild(createInput("tariff", "Тариф", false));
    button.onclick = function() {
      addExceptions(context.parentNode.id);
    };
  }

  div_subdiv_head2.appendChild(button);

  div_head.appendChild(div_subdiv_head1);
  div_head.appendChild(div_subdiv_head2);

  context.parentNode.appendChild(div_head);

  showPopup();

  $(document).ready(function() {
    $(popup_bg).click(function() {
      $(popup).fadeOut(800);
    });
  });
}

function update(id, request, json) {
  $.ajax({
    url: "update" + request,
    type: "put",
    contentType: "application/json",
    data: json,
    success: function(response) {
      alert("Данные были обновлены");
      location.reload();
    }
  });
}

function deleteField(request) {
  $.ajax({
    url: "delete" + request,
    type: "delete",
    success: function(response) {
      alert("Данные были удалены");
      location.reload();
    }
  });
}

function insert(request, json) {
  $.ajax({
    url: "add" + request,
    type: "post",
    contentType: "application/json",
    data: json,
    success: function(response) {
      alert("Данные были добавлены");
      location.reload();
    }
  });
}

function calcRate() {
  var input1 = $('input[name="station_from"]').val();
  var input2 = $('input[name="station_to"]').val();
  var input3 = $('input[name="cargo"]').val();
  var input4 = $('input[name="volume"]').val();
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
          $('<td class="td_table2">').text(response.totalList[i].distance),
          $('<td class="td_table2">').text(response.totalList[i].countDays),
          $('<td class="td_table2">').text(
            response.totalList[i].countDaysLoadAndUnload
          ),
          $('<td class="td_table2">').text(response.totalList[i].fullCountDays),
          $('<td class="td_table2">').text("поваг"),
          $('<td class="td_table2">').text(function() {
            if (response.totalList[i].rate == 0) {
              return "";
            } else {
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
            return response.totalList[i].rate + response.totalList[i].tariff;
          }),
          $('<td class="td_table2">').text("")
        );
        $("#total").append($content);
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
      $("#total").append($sum);
    }
  });
}

function reloadPage(id) {
  var tabId;
  if (id == "contentReturnStations") {
    tabId = "tab1";
  } else if (id == "contentReturnException") {
    tabId = "tab2";
  } else if (id == "contentBeginningException") {
    tabId = "tab3";
  } else if (id == "contentBorderDistance") {
    tabId = "tab4";
  } else if (id == "contentLoadUnload") {
    tabId = "tab5";
  } else {
    tabId = "tab6";
  }
  context = document.getElementById(tabId);
  location.reload();
  context.checked = true;
}
