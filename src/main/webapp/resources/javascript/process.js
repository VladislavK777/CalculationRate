function init() {
  getCache();
    window.sessionStorage.setItem("tabId", "tab1");
    window.sessionStorage.setItem("numberTableResult", 1);
    var mode = (window.sessionStorage.getItem("switchMode") == 'true');
    document.getElementById("switchMode").checked = mode;
    switchModeNoClick(mode);
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
    stationIsNotExist: "Станция не существует",
    settingIsNotExist: "Отсутсвует настройка",
    errorCalculateRate: "Ошибка в расчете ставки"
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
  $.ajax({
    url: "search/department",
    cache: false,
    success: function(response) {
      window.sessionStorage.setItem("departmentSearch", response);
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

function createInput(
  id,
  name,
  isCallSearch,
  defaultText,
  defaultListText,
  selectList,
  marker
) {
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
    div_checkList.className = "check-list" + marker;
    var s_array = defaultListText.split(",");
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
  var roadsList = window.sessionStorage.getItem("roadSearch");
  var context = document.getElementById(id);
  var table = document.createElement("table");

  var div_head = document.createElement("div");
  div_head.id = "popup";
  div_head.style =
    "position: absolute; height: 100%; width: 100%; top: 0; left: 0; display: none;";
  var div_subdiv_head1 = document.createElement("div");
  div_subdiv_head1.id = "popup_bg";
  div_subdiv_head1.style =
    "background: rgba(0, 0, 0, 0); position: fixed; z-index: 1; height: 100%; width: 100%;";
  var div_subdiv_head2 = document.createElement("div");
  div_subdiv_head2.className = "form";
  var button = document.createElement("input");
  button.type = "button";
  button.className = "bot1";
  button.value = "Сохранить";

  if (id == "btAddReturnStation") {
    var tr1 = document.createElement("tr");
    var td11 = document.createElement("td");
    td11.appendChild(
      createInput("road", "Дорога", false, "", roadsList, true, "__form_roads")
    );
    tr1.appendChild(td11);
    table.appendChild(tr1);
    var tr2 = document.createElement("tr");
    var td21 = document.createElement("td");
    td21.appendChild(createInput("stationList", "Список станций", false));
    tr2.appendChild(td21);
    table.appendChild(tr2);
    var tr3 = document.createElement("tr");
    var td31 = document.createElement("td");
    td31.appendChild(createInput("department", "Отделение", true));
    tr3.appendChild(td31);
    table.appendChild(tr3);
    var tr4 = document.createElement("tr");
    var td41 = document.createElement("td");
    td41.appendChild(
      createInput(
        "volume",
        "Группа объемов",
        false,
        "120,138,150",
        "120,138,150",
        true,
        "__form"
      )
    );
    tr4.appendChild(td41);
    table.appendChild(tr4);
    var tr5 = document.createElement("tr");
    var td51 = document.createElement("td");
    td51.appendChild(createInput("station", "Станция возврата", true));
    tr5.appendChild(td51);
    table.appendChild(tr5);
    button.addEventListener("click", function() {
      addReturnStation(context.parentNode.id);
    });
  } else {
    var tr1 = document.createElement("tr");
    var td11 = document.createElement("td");
    td11.appendChild(
      createInput("road", "Дорога", false, "", roadsList, true, "__form_roads")
    );
    var td12 = document.createElement("td");
    td12.appendChild(createInput("stationList", "Список станций", false));
    var td13 = document.createElement("td");
    td13.appendChild(createInput("department", "Отделение", true));
    tr1.appendChild(td11);
    tr1.appendChild(td12);
    tr1.appendChild(td13);
    table.appendChild(tr1);
    var tr2 = document.createElement("tr");
    var td21 = document.createElement("td");
    td21.appendChild(
      createInput(
        "volume",
        "Группа объемов",
        false,
        "120,138,150",
        "120,138,150",
        true,
        "__form"
      )
    );
    var td22 = document.createElement("td");
    td22.appendChild(createInput("stationFrom", "Станция отправления", true));
    var td23 = document.createElement("td");
    td23.appendChild(createInput("stationTo", "Станция назначения", true));
    tr2.appendChild(td21);
    tr2.appendChild(td22);
    tr2.appendChild(td23);
    table.appendChild(tr2);

    var tr3 = document.createElement("tr");
    var td31 = document.createElement("td");
    td31.appendChild(createInput("cargo", "Груз", true));
    var td32 = document.createElement("td");
    td32.appendChild(
      createInput(
        "cargoClass",
        "Класс груза",
        false,
        "1,2,3",
        "1,2,3",
        true,
        "__form"
      )
    );
    var td33 = document.createElement("td");
    var field = createInput("typeRoute", "Тип рейса", false);
    var input = field.querySelector("#typeRoute");
    input.setAttribute("list", "list");
    input.onfocus = function() {
      showList(input.id);
    };
    input.onblur = function() {
      hiddenList();
    };
    td33.appendChild(field);
    tr3.appendChild(td31);
    tr3.appendChild(td32);
    tr3.appendChild(td33);
    table.appendChild(tr3);
    var tr4 = document.createElement("tr");
    var td41 = document.createElement("td");
    td41.appendChild(createInput("distance", "Расстояние", false, "0"));
    var td42 = document.createElement("td");
    td42.appendChild(createInput("countDays", "Дней", false, "0"));
    var td43 = document.createElement("td");
    td43.appendChild(createInput("rate", "Ставка", false, "0"));
    tr4.appendChild(td41);
    tr4.appendChild(td42);
    tr4.appendChild(td43);
    table.appendChild(tr4);
    var tr5 = document.createElement("tr");
    var td51 = document.createElement("td");
    td51.appendChild(createInput("tariff", "Тариф", false, "0"));
    tr5.appendChild(td51);
    table.appendChild(tr5);
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
      alert(errorCodes(code) + "\n" + message);
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
      alert(errorCodes(code) + "\n" + message);
    }
  });
}

function reload() {
  var host = location.protocol + location.host;
  location.replace("/calculaterate/");
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

// Выпадающий список для списка дорог
$(document).on("focus", ".effect-1__roads", function() {
  addListRoads();
  var $input = $(this);
  var $checkList = $(popup),
    $checkBoxes = $checkList.find(".check-list__checkbox");
  if ($input.val() != "") {
    var $split = $input.val().split(",");
    for (var i = 0; i < $checkBoxes.length; i++) {
      for (var j = 0; j < $split.length; j++) {
        if (
          $.trim(
            $checkBoxes
              .eq(i)
              .val()
              .match(/^\S+\s/)[0]
          ) == $split[j]
        ) {
          $checkBoxes.eq(i).prop("checked", true);
        }
      }
    }
  }

  $checkBoxes.on("change", function() {
    var inputText = "",
      checkStatus = 0;

    for (var i = 0; i < $checkBoxes.length; i++) {
      if ($checkBoxes.eq(i).is(":checked")) {
        checkStatus++;
        if (inputText === "") {
          inputText = $.trim(
            $checkBoxes
              .eq(i)
              .val()
              .match(/^\S+\s/)[0]
          );
        } else {
          inputText +=
            "," +
            $.trim(
              $checkBoxes
                .eq(i)
                .val()
                .match(/^\S+\s/)[0]
            );
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
      .siblings(".check-list__form, .check-list__form_roads"),
    $checkBoxes = $checkList.find(".check-list__checkbox");
  if ($input.val() != "") {
    var $split = $input.val().split(",");
    for (var i = 0; i < $checkBoxes.length; i++) {
      for (var j = 0; j < $split.length; j++) {
        if ($checkList.hasClass("check-list__form")) {
          if ($checkBoxes.eq(i).val() == $split[j]) {
            $checkBoxes.eq(i).prop("checked", true);
          }
        } else {
          if (
            $.trim(
              $checkBoxes
                .eq(i)
                .val()
                .match(/^\S+\s/)[0]
            ) == $split[j]
          ) {
            $checkBoxes.eq(i).prop("checked", true);
          }
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
        if ($checkBoxes.parents().hasClass("check-list__form")) {
          if (inputText === "") {
            inputText = $checkBoxes.eq(i).val();
          } else {
            inputText += "," + $checkBoxes.eq(i).val();
          }
        } else {
          if (inputText === "") {
            inputText = $.trim(
              $checkBoxes
                .eq(i)
                .val()
                .match(/^\S+\s/)[0]
            );
          } else {
            inputText +=
              "," +
              $.trim(
                $checkBoxes
                  .eq(i)
                  .val()
                  .match(/^\S+\s/)[0]
              );
          }
        }

        $input.val(inputText);
      } else if (checkStatus === 0) {
        $input.val("");
      }
    }
  });
});

function checkEmpty(value) {
  if (value == "") {
    return null;
  } else {
    return value;
  }
}

function addListRoads() {
  var body = document.body;
  var roadsList = window.sessionStorage.getItem("roadSearch");

  var div_head = document.createElement("div");
  div_head.id = "popup";
  div_head.style =
    "position: absolute; height: 100%; width: 100%; top: 0; left: 0; display: none;";

  var div_subdiv_head1 = document.createElement("div");
  div_subdiv_head1.id = "popup_bg";
  div_subdiv_head1.style =
    "background: rgba(0, 0, 0, 0); position: fixed; z-index: 1; height: 100%; width: 100%;";

  var div_subdiv_head2 = document.createElement("div");
  div_subdiv_head2.className = "form";

  var div_wrapper = document.createElement("div");
  div_wrapper.className = "wrapper";

  var div_checkList = document.createElement("div");
  div_checkList.className = "check-list__roads";

  var roadsArray = roadsList.split(",");
  var ul = document.createElement("ul");
  for (var i = 0; i < roadsArray.length; i++) {
    var li = document.createElement("li");
    var input_li =
      '<input type="checkbox" class="check-list__checkbox" value="' +
      roadsArray[i] +
      '" />';
    li.innerHTML = input_li + roadsArray[i];
    ul.appendChild(li);
  }
  div_checkList.appendChild(ul);
  div_wrapper.appendChild(div_checkList);
  div_subdiv_head2.appendChild(div_wrapper);
  div_head.appendChild(div_subdiv_head1);
  div_head.appendChild(div_subdiv_head2);

  body.appendChild(div_head);
  showPopup();

  $(document).ready(function() {
    $(popup_bg).click(function() {
      $(popup).remove();
    });
  });
}

function wrapperPrepareStringToArray(string) {
  if (string != "") {
    var idsArray = new Array(),
      namesArray = new Array();
    var stringSplit = string.split(",");
    for (var i = 0; i < stringSplit.length; i++) {
      if (stringSplit[i].length > 0) {
        idsArray.push(stringSplit[i].match(/\d+/)[0].trim());
        namesArray.push(stringSplit[i].match(/^\S+\s/)[0].trim());
      }
    }
    return [idsArray, namesArray];
  }
  return null;
}

// Всплывающая подсказка
$(document).ready(function() {
  $("[data-tooltip]")
    .mousemove(function(eventObject) {
      $data_tooltip = $(this).attr("data-tooltip");

      $("#tooltip")
        .text($data_tooltip)
        .css({
          top: eventObject.pageY + 5,
          left: eventObject.pageX + 5
        })
        .show();
    })
    .mouseout(function() {
      $("#tooltip")
        .hide()
        .text("")
        .css({
          top: 0,
          left: 0
        });
    });
});

/***
number - число
decimals - количество знаков после разделителя
dec_point - символ разделителя
separator - разделитель тысячных
***/
function numberFormat(number, decimals, decPoint, separator) {
  number = (number + "").replace(/[^0-9+\-Ee.]/g, "");
  var n = !isFinite(+number) ? 0 : +number,
    prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
    sep = typeof separator === "undefined" ? "," : separator,
    dec = typeof decPoint === "undefined" ? "." : decPoint,
    s = "",
    toFixedFix = function(n, prec) {
      var k = Math.pow(10, prec);
      return "" + (Math.round(n * k) / k).toFixed(prec);
    };
  // Фиксим баг в IE parseFloat(0.55).toFixed(0) = 0;
  s = (prec ? toFixedFix(n, prec) : "" + Math.round(n)).split(".");
  if (s[0].length > 3) {
    s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
  }
  if ((s[1] || "").length < prec) {
    s[1] = s[1] || "";
    s[1] += new Array(prec - s[1].length + 1).join("0");
  }
  return s.join(dec);
}

function switchMode(id) {
   var checkbox = document.getElementById(id);
   window.sessionStorage.setItem("switchMode", checkbox.checked);
   var contextSwitchMode = document.getElementById("divCheckMode");
   var contextDivMode = document.getElementById("divModeRoot");
   var tdMode1 = contextSwitchMode.querySelector("#mode1");
   var tdMode2 = contextSwitchMode.querySelector("#mode2");
   var divMode1 = contextDivMode.querySelector("#divMode1");
   var divMode2 = contextDivMode.querySelector("#divMode2");
   if (checkbox.checked == true) {
      tdMode1.className = "tdOff";
      tdMode2.className = "tdOn";
      divMode1.className = "divModeOff";
      divMode2.className = "divModeOn";
   } else {
      tdMode1.className = "tdOn";
      tdMode2.className = "tdOff";
      divMode1.className = "divModeOn";
      divMode2.className = "divModeOff";
   }
}

function switchModeNoClick(bool) {
   var contextSwitchMode = document.getElementById("divCheckMode");
   var contextDivMode = document.getElementById("divModeRoot");
   var tdMode1 = contextSwitchMode.querySelector("#mode1");
   var tdMode2 = contextSwitchMode.querySelector("#mode2");
   var divMode1 = contextDivMode.querySelector("#divMode1");
   var divMode2 = contextDivMode.querySelector("#divMode2");
   if (bool == true) {
      tdMode1.className = "tdOff";
      tdMode2.className = "tdOn";
      divMode1.className = "divModeOff";
      divMode2.className = "divModeOn";
   } else {
      tdMode1.className = "tdOn";
      tdMode2.className = "tdOff";
      divMode1.className = "divModeOn";
      divMode2.className = "divModeOff";
   }
}
