function init() {
  getCache();
  window.sessionStorage.setItem("tabId", "tab1");
  document.getElementById("copy").innerText = new Date().getFullYear();
}

function errorCodes(code) {
  var result = [];
  var codes = {
    namesRoad: "Дорога",
    volumeGroupsString: "Группа объемов",
    idStationReturn: "Станция возврата",
    stationFrom: "Станция отправления",
    stationTo: "Станция назначения",
    cargo: "Груз",
    cargoTypeString: "Класс груза",
    routeType: "Тип рейса",
    distance: "Расстояние",
    countDays: "Дней",
    rate: "Ставка",
    tariff: "Тариф",
    volume: "Объем",
    paramIsExist: "Парметр существует",
    stationIsNotExist: "Станция не существует"
  };
  if (toString.call(code) == "[object Array]") {
    for (var i = 0; i < code.length; i++) {
      result.push(codes[code[i]]);
    }
    return result;
  } else {
    return codes[code];
  }
}

function initSetting() {
  window.sessionStorage.setItem("roadIds", "");
  element = document.getElementById(window.sessionStorage.getItem("tabId"));
  element.setAttribute("checked", true);
  document.getElementById("copy").innerText = new Date().getFullYear();
}

function getCache() {
  $.ajax({
    url: "search/station",
    cache: false,
    success: function(response) {
      window.sessionStorage.setItem("stationSearch", response);
    }
  });
  $.ajax({
    url: "search/road",
    cache: false,
    success: function(response) {
      window.sessionStorage.setItem("roadSearch", response);
    }
  });
  $.ajax({
    url: "search/cargo",
    cache: false,
    success: function(response) {
      window.sessionStorage.setItem("cargoSearch", response);
    }
  });
}

function showPopup() {
  $(popup).fadeIn(800);
}

function showList(id) {
  var input = document.getElementById(id);
  input.value = "";
  var datalist = document.createElement("datalist");
  datalist.id = "list";
  var option1 = new Option("ГРУЖ");
  var option2 = new Option("ПОР");
  datalist.appendChild(option1);
  datalist.appendChild(option2);
  input.parentNode.appendChild(datalist);
}

function hiddenList() {
  var datalist = document.getElementById("list");
  datalist.parentNode.removeChild(datalist);
}

function createInput(id, name, isCallSearch, defaultText, selectList) {
  var p = document.createElement("p");
  p.className = "inp";
  var label = document.createElement("label");
  var input = document.createElement("input");
  input.value = typeof defaultText == "undefined" ? "" : defaultText;
  input.type = "text";
  input.id = id;
  input.setAttribute("autocomplete", "off");
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
  if (selectList) {
    var div = document.createElement("div");
    div.className = "col-3__list";
    var div2 = document.createElement("div");
    div2.className = "wrapper";
    input.className = "input__form";
    var div_checkList = document.createElement("div");
    div_checkList.className = "check-list__form";
    var s_array = defaultText.split(",");
    var ul = document.createElement("ul");
    for (var i = 0; i < s_array.length; i++) {
      var li = document.createElement("li");
      var input_li =
        '<input type="checkbox" class="check-list__checkbox" value="' +
        s_array[i] +
        '" />';
      li.innerHTML = input_li + s_array[i];
      ul.appendChild(li);
    }
    div_checkList.appendChild(ul);
    label.appendChild(input);
    label.appendChild(span_first);
    label.appendChild(span_second);
    p.appendChild(label);
    div2.appendChild(p);
    div2.appendChild(div_checkList);
    div.appendChild(div2);
    return div;
  } else {
    label.appendChild(input);
    label.appendChild(span_first);
    label.appendChild(span_second);
    p.appendChild(label);
    return p;
  }
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

  if (id == "btAddReturnStation") {
    var tr1 = document.createElement("tr");
    var td11 = document.createElement("td");
    td11.appendChild(createInput("roadSetting", "Дорога", true));
    tr1.appendChild(td11);
    table.appendChild(tr1);
    var tr2 = document.createElement("tr");
    var td21 = document.createElement("td");
    td21.appendChild(createInput("stationList", "Список станций", false));
    tr2.appendChild(td21);
    table.appendChild(tr2);
    var tr3 = document.createElement("tr");
    var td31 = document.createElement("td");
    td31.appendChild(
      createInput("volume", "Группа объемов", false, "120,138,150", true)
    );
    tr3.appendChild(td31);
    table.appendChild(tr3);
    var tr4 = document.createElement("tr");
    var td41 = document.createElement("td");
    td41.appendChild(createInput("station", "Станция возврата", true));
    tr4.appendChild(td41);
    table.appendChild(tr4);
    button.addEventListener("click", function() {
      addReturnStation(context.parentNode.id);
    });
  } else {
    var tr1 = document.createElement("tr");
    var td11 = document.createElement("td");
    td11.appendChild(createInput("roadSetting", "Дорога", true));
    var td12 = document.createElement("td");
    td12.appendChild(createInput("stationList", "Список станций", false));
    var td13 = document.createElement("td");
    td13.appendChild(
      createInput("volume", "Группа объемов", false, "120,138,150", true)
    );
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
    td31.appendChild(
      createInput("cargoClass", "Класс груза", false, "1,2,3", true)
    );
    var td32 = document.createElement("td");
    var field = createInput("typeRoute", "Тип рейса", false);
    var input = field.querySelector("#typeRoute");
    input.setAttribute("list", "list");
    input.onfocus = function() {
      showList(input.id);
    };
    input.onblur = function() {
      hiddenList();
    };
    td32.appendChild(field);
    var td33 = document.createElement("td");
    td33.appendChild(createInput("distance", "Расстояние", false, "0"));
    tr3.appendChild(td31);
    tr3.appendChild(td32);
    tr3.appendChild(td33);
    table.appendChild(tr3);

    var tr4 = document.createElement("tr");
    var td41 = document.createElement("td");
    td41.appendChild(createInput("countDays", "Дней", false, "0"));
    var td42 = document.createElement("td");
    td42.appendChild(createInput("rate", "Ставка", false, "0"));
    var td43 = document.createElement("td");
    td43.appendChild(createInput("tariff", "Тариф", false, "0"));
    tr4.appendChild(td41);
    tr4.appendChild(td42);
    tr4.appendChild(td43);
    table.appendChild(tr4);
    button.addEventListener("click", function() {
      addException(context.parentNode.id);
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
    },
    error: function(response) {
      var message = response.responseJSON.conflictMessage;
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
    },
    error: function(response) {
      var message = response.responseJSON.conflictMessage;
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

function reload() {
  location.reload();
}

function reloadPage(id) {
  window.sessionStorage.setItem("tabId", id);
}

// Выпадающий список
$(document).on("focus", ".effect-1__list", function() {
  var $input = $(this);
  var $checkList = $input.siblings(".check-list"),
    $checkBoxes = $checkList.find(".check-list__checkbox");

  if ($input.val() != "") {
    var $split = $input.val().split(",");
    for (var i = 0; i < $checkBoxes.length; i++) {
      for (var j = 0; j < $split.length; j++) {
        if ($checkBoxes.eq(i).val() == $split[j]) {
          $checkBoxes.eq(i).prop("checked", true);
        }
      }
    }
  }

  $checkList.show();

  $(document).bind("click", function(e) {
    var $clicked = $(e.target);
    if (!$clicked.parents().hasClass("col-3__list")) {
      $checkList.hide();
    }
  });

  $checkBoxes.on("change", function() {
    var inputText = "",
      checkStatus = 0;

    for (var i = 0; i < $checkBoxes.length; i++) {
      if ($checkBoxes.eq(i).is(":checked")) {
        checkStatus++;

        if (inputText === "") {
          inputText = $checkBoxes.eq(i).val();
        } else {
          inputText += "," + $checkBoxes.eq(i).val();
        }

        $input.val(inputText);
      } else if (checkStatus === 0) {
        $input.val("");
      }
    }
  });
});

// Выпадающий список для формы
$(document).on("focus", ".input__form", function() {
  var $input = $(this);
  var $checkList = $input
      .parents()
      .parents()
      .siblings(".check-list__form"),
    $checkBoxes = $checkList.find(".check-list__checkbox");

  if ($input.val() != "") {
    var $split = $input.val().split(",");
    for (var i = 0; i < $checkBoxes.length; i++) {
      for (var j = 0; j < $split.length; j++) {
        if ($checkBoxes.eq(i).val() == $split[j]) {
          $checkBoxes.eq(i).prop("checked", true);
        }
      }
    }
  }

  $checkList.show();

  $(document).bind("click", function(e) {
    var $clicked = $(e.target);
    if (!$clicked.parents().hasClass("col-3__list")) {
      $checkList.hide();
    }
  });

  $checkBoxes.on("change", function() {
    var inputText = "",
      checkStatus = 0;

    for (var i = 0; i < $checkBoxes.length; i++) {
      if ($checkBoxes.eq(i).is(":checked")) {
        checkStatus++;

        if (inputText === "") {
          inputText = $checkBoxes.eq(i).val();
        } else {
          inputText += "," + $checkBoxes.eq(i).val();
        }

        $input.val(inputText);
      } else if (checkStatus === 0) {
        $input.val("");
      }
    }
  });
});

function cleanField(event) {
  var element = event.target;
  element.placeholder = element.value;
  element.value = "";
  element.addEventListener("blur", function() {
    element.value = element.placeholder;
  });
}

function checkEmpty(value) {
  if (value === "") {
    return null;
  } else {
    return value;
  }
}
