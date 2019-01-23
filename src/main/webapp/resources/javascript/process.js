function cop() {
	document.getElementById("copy").innerText = new Date().getFullYear();
}

function check(valueYield,id) {
	var yield = document.getElementById(id).querySelector("#yieldYield");
	var bt = document.getElementById(id).querySelector("#btEditYield");
	if (valueYield != yield.value) {
		bt.value = 'Сохранить';
		bt.setAttribute("onclick", "update('" + id + "','" + yield.value + "');");
	} else {
		bt.value = "Отмена";
	}
}

function showPopup() {
	$(popup).fadeIn(800);
}


function createInput(id,name,isCallSearch) {
	var p = document.createElement('p');
	p.className = "inp";
	var label = document.createElement('label');
	var input = document.createElement('input');
	input.type = "text";
	input.id = id;
	input.placeholder = "\xa0";
	if (isCallSearch) {
		input.onkeyup = function() {search(input.id);}
	}
	var span_first = document.createElement('span');
	span_first.textContent = name;
	span_first.className="label";
	var span_second = document.createElement('span');
	span_second.className="border";
	label.appendChild(input);
	label.appendChild(span_first);
	label.appendChild(span_second);
	p.appendChild(label);
	return p;
}

function createField(id) {
	var context = document.getElementById(id);
	var div_head = document.createElement('div');
	div_head.id = "popup";
	div_head.style = "position: absolute; height: 100%; width: 100%; top: 0; left: 0; display: none;"
	var div_subdiv_head1 = document.createElement('div');
	div_subdiv_head1.id = "popup_bg";
	div_subdiv_head1.style = "background: rgba(0, 0, 0, 0.2); position: absolute; z-index: 1; height: 100%; width: 100%;"
	var div_subdiv_head2 = document.createElement('div');
	div_subdiv_head2.className = "form";
	var button = document.createElement('input');
	button.type = 'button';
	button.className = 'bot1';
	button.value = 'Сохранить';

	if (id == "btAddReturnStations") {
		div_subdiv_head2.appendChild(createInput("road", "Дорога", true));
		div_subdiv_head2.appendChild(createInput("stationList", "Список станций", false));
		div_subdiv_head2.appendChild(createInput("volume", "Группа объемов", false));
		div_subdiv_head2.appendChild(createInput("station", "Станция возврата", true));
		button.onclick = function() {addReturnStations(context.parentNode.id);}
	} else {
		div_subdiv_head2.appendChild(createInput("road", "Дорога", true));
		div_subdiv_head2.appendChild(createInput("stationList", "Список станций", false));
		div_subdiv_head2.appendChild(createInput("volume", "Группа объемов", false));
		div_subdiv_head2.appendChild(createInput("stationFrom", "Станция отправления", true));
		div_subdiv_head2.appendChild(createInput("stationTo", "Станция назначения", true));
		div_subdiv_head2.appendChild(createInput("cargo", "Груз", true));
		div_subdiv_head2.appendChild(createInput("cargoClass", "Класс груза", false));
		div_subdiv_head2.appendChild(createInput("typeRoute", "Тип рейса", false));
		div_subdiv_head2.appendChild(createInput("distance", "Расстояние", false));
		div_subdiv_head2.appendChild(createInput("countDays", "Дней", false));
		div_subdiv_head2.appendChild(createInput("rate", "Ставка", false));
		div_subdiv_head2.appendChild(createInput("tariff", "Тариф", false));
		button.onclick = function() {addExceptions(context.parentNode.id);}
	}

	div_subdiv_head2.appendChild(button);

	div_head.appendChild(div_subdiv_head1);
	div_head.appendChild(div_subdiv_head2);

	context.parentNode.appendChild(div_head);

	showPopup();

	$(document).ready(function () {
		$(popup_bg).click(function () {
			$(popup).fadeOut(800);
		});
	});
}

function update(id,request,json) {
	$.ajax({
		url: "update" + request,
		type : "put",
		contentType : "application/json",
		data : json,
		success: function(response) {
			//location.reload();
			console.log(id);
			reloadPage(id);
		}
	});
}

function deleteField(request) {
	$.ajax({
		url: "delete" + request,
		type : "delete",
		success: function(response) {
			//location.reload();
			console.log("ok");
		}
	});
}

function insert(request,json) {
	$.ajax({
		url: "add" + request,
		type : "post",
		contentType : "application/json",
		data : json,
		success: function(response) {
			console.log('OK');
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
	context = document.getElementById(tabId);
	location.reload();
  context.checked = true;
}
