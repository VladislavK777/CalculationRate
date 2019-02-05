function cop() {
  element = document.getElementById(window.sessionStorage.getItem("tabId"));
  var url = document.referrer;
  if (element != null) {
    element.setAttribute("checked", true);
  } else if (url.indexOf("settings") != -1) {
    window.sessionStorage.setItem("tabId", "tab1");
  }
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

function createInput(id, name, isCallSearch, defaultText) {
  var p = document.createElement("p");
  p.className = "inp";
  var label = document.createElement("label");
  var input = document.createElement("input");
  input.value = typeof defaultText == "undefined" ? "" : defaultText;
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
  var table = document.createElement("table");

  var div_head = document.createElement("div");
  div_head.id = "popup";
  div_head.style =
    "position: absolute; height: 100%; width: 100%; top: 0; left: 0; display: none;";
  var div_subdiv_head1 = document.createElement("div");
  div_subdiv_head1.id = "popup_bg";
  div_subdiv_head1.style =
    "background: rgba(0, 0, 0, 0); position: absolute; z-index: 1; height: 100%; width: 100%;";
  var div_subdiv_head2 = document.createElement("div");
  div_subdiv_head2.className = "form";
  var button = document.createElement("input");
  button.type = "button";
  button.className = "bot1";
  button.value = "Сохранить";

  if (id == "btAddReturnStations") {
    div_subdiv_head2.appendChild(createInput("roadSetting", "Дорога", true));
    div_subdiv_head2.appendChild(
      createInput("stationList", "Список станций", false)
    );
    div_subdiv_head2.appendChild(
      createInput("volume", "Группа объемов", false)
    );
    div_subdiv_head2.appendChild(
      createInput("station", "Станция возврата", true)
    );
    button.addEventListener("click", function() {
      addReturnStations(context.parentNode.id);
    });
  } else {
    var tr1 = document.createElement("tr");
    var td11 = document.createElement("td");
    td11.appendChild(createInput("roadSetting", "Дорога", true));
    var td12 = document.createElement("td");
    td12.appendChild(createInput("stationList", "Список станций", false));
    var td13 = document.createElement("td");
    td13.appendChild(createInput("volume", "Группа объемов", false, "120, 138, 150"));
    tr1.appendChild(td11);
    tr1.appendChild(td12);
    tr1.appendChild(td13);
    table.appendChild(tr1);
    var tr2 = document.createElement("tr");
    var td21 = document.createElement("td");
    td21.appendChild(createInput("stationFrom", "Станция отправления", true));
    var td22 = document.createElement("td");
    td22.appendChild(createInput("stationTo", "Станция назначения", true));
    var td23 = document.createElement("td");
    td23.appendChild(createInput("cargo", "Груз", true));
    tr2.appendChild(td21);
    tr2.appendChild(td22);
    tr2.appendChild(td23);
    table.appendChild(tr2);

    var tr3 = document.createElement("tr");
    var td31 = document.createElement("td");
    td31.appendChild(createInput("cargoClass", "Класс груза", false, "1, 2, 3"));
    var td32 = document.createElement("td");
    td32.appendChild(createInput("typeRoute", "Тип рейса", false));
    var td33 = document.createElement("td");
    td33.appendChild(createInput("distance", "Расстояние", false));
    tr3.appendChild(td31);
    tr3.appendChild(td32);
    tr3.appendChild(td33);
    table.appendChild(tr3);

    var tr4 = document.createElement("tr");
    var td41 = document.createElement("td");
    td41.appendChild(createInput("countDays", "Дней", false));
    var td42 = document.createElement("td");
    td42.appendChild(createInput("rate", "Ставка", false, "0"));
    var td43 = document.createElement("td");
    td43.appendChild(createInput("tariff", "Тариф", false, "0"));
    tr4.appendChild(td41);
    tr4.appendChild(td42);
    tr4.appendChild(td43);
    table.appendChild(tr4);
    button.addEventListener("click", function() {
      addExceptions(context.parentNode.id);
    });
  }
  div_subdiv_head2.appendChild(table);

  div_subdiv_head2.appendChild(button);

  div_head.appendChild(div_subdiv_head1);
  div_head.appendChild(div_subdiv_head2);

  context.parentNode.appendChild(div_head);

  showPopup();

  $(document).ready(function() {
    $(popup_bg).click(function() {
      $(popup).remove();
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

function reload() {
  location.reload();
}

function reloadPage(id) {
  window.sessionStorage.setItem("tabId", id);
}

function testError(event) {
  input = event.target.parentNode.childNodes[1];
  value = input.value;
  $.ajax({
    url: "rate/test/" + value,
    success: function(response) {
      console.log(response);
    },
    error: function(response) {
      console.log(response.responseJSON);
      message = response.responseJSON.conflictMessage;
      alert(message);
      $(input).focus();
    }
  });
}
