<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>UralTransCom|DynamicDistributionPark</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="resources/style.css" rel="stylesheet" type="text/css"/>
    <link rel="shortcut icon" href="resources/favicon.ico" type="image/x-icon">
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"/>

    <!-- Копирайт -->
    <script>
        function cop() {
            document.getElementById("copy").innerText = new Date().getFullYear();
        }
    </script>


</head>
<style>
        /* Настрйка вкладок*/
        /* Стили секций с содержанием */
        .tabs > section {
            display: none;
            max-width: 100%;
            padding: 15px;
            background: #fff;
            border: 1px solid #ddd;
        }
        .tabs > section > p {
            margin: 0 0 5px;
            line-height: 1.5;
            color: #383838;
            /* прикрутим анимацию */
            -webkit-animation-duration: 1s;
            animation-duration: 1s;
            -webkit-animation-fill-mode: both;
            animation-fill-mode: both;
            -webkit-animation-name: fadeIn;
            animation-name: fadeIn;
        }
        /* Прячем чекбоксы */
        .tabs > input {
            display: none;
            position: absolute;
        }
        /* Стили переключателей вкладок (табов) */
        .tabs > label {
            display: inline-block;
            margin: 0 0 -1px;
            padding: 15px 25px;
            font-weight: 600;
            text-align: center;
            color: #aaa;
            border: 0px solid #ddd;
            border-width: 1px 1px 1px 1px;
            background: #f1f1f1;
            border-radius: 3px 3px 0 0;
        }
        /* Шрифт-иконки от Font Awesome в формате Unicode */
        .tabs > label:before {
            font-family: fontawesome;
            font-weight: normal;
            margin-right: 10px;
        }
        /* Изменения стиля переключателей вкладок при наведении */
        .tabs > label:hover {
            color: #888;
            cursor: pointer;
        }
        /* Стили для активной вкладки */
        .tabs > input:checked + label {
            color: #555;
            border-top: 1px solid #364274;
            border-bottom: 1px solid #fff;
            background: #fff;
        }
        /* Активация секций с помощью псевдокласса :checked */
        #tab1:checked ~ #content-tab1,
        #tab2:checked ~ #content-tab2,
        #tab3:checked ~ #content-tab3,
        #tab4:checked ~ #content-tab4,
        #tab5:checked ~ #content-tab5,
        #tab6:checked ~ #content-tab6 {
            display: block;
        }
        @media screen and (max-width: 1500px) {
            .tabs > label {
                font-size: 12px;
                width: 180px;
            }
            .tabs > label:before {
                margin: 0;
                font-size: 12px;
            }
        }
</style>

<body onload="cop()">

<div>
    <div class="tabs">
		<input id="tab1" type="radio" name="tabs" checked>
		<label for="tab1" title="1">1</label>

		<input id="tab2" type="radio" name="tabs">
		<label for="tab2" title="2">2</label>

		<input id="tab3" type="radio" name="tabs">
		<label for="tab3" title="3">3</label>

		<input id="tab4" type="radio" name="tabs">
		<label for="tab4" title="4">4</label>

		<input id="tab5" type="radio" name="tabs">
		<label for="tab5" title="5">5</label>

		<input id="tab6" type="radio" name="tabs">
		<label for="tab6" title="6">6</label>

		<section id="content-tab1">
			<input type="button" id="btAddReturnStations" onclick="createField(this.parentNode.id)" value="+"/>
			<table>
				<tbody>
					<tr>
						<th>Дорога</th>
						<th>Станция</th>
						<th>Объем</th>
						<th>Станция возврата</th>
					</tr>
					<c:forEach items="${mapReturnStations}" var="total">
						<c:forEach items="${total.value}" var="setting">
						<tr id="contentReturnStations${setting.getId()}">
							<input id="idReturnStations" value='${setting.getId()}' type="hidden" />
								<td><input id="keyReturnStations" value='${total.key}' disabled /></td>
								<td><input id="idStationStringReturnStations" value='${setting.getIdStationString()}' disabled /></td>
								<td><input id="volumeGroupsStringReturnStations" value='${setting.getVolumeGroupsString()}' disabled /></td>
								<td><input id="nameStationReturnReturnStations" value='${setting.getNameStationReturn()}' disabled /></td>
								<td><input type="button" id="btEditReturnStations" onclick="editField(this.parentNode.parentNode.id)" value="Редактировать"/></td>
								<td><input type="button" id="btDeleteReturnStations" onclick="deleteField(this.parentNode.parentNode.id)" value="Удалить"/></td>
							<td></td>
						</tr>
						</c:forEach>
					</c:forEach>
				</tbody>
			</table>
		 </section>
		 <section id="content-tab2">
			<input type="button" id="btAddReturnException" onclick="createField(this.parentNode.id)" value="+"/>
			<table>
				<tbody>
					<tr>
						<th>Дорога</th>
						<th>Станция</th>
						<th>Объем</th>
						<th>Станция отправления</th>
						<th>Станция назначения</th>
						<th>Груз</th>
						<th>Класс груза</th>
						<th>Тип рейса</th>
						<th>Расстояние</th>
						<th>Дней</th>
						<th>Ставка</th>
						<th>Тариф</th>
					</tr>
					<c:forEach items="${mapReturnException}" var="total">
						<c:forEach items="${total.value}" var="setting">
							 <tr id="contentReturnException${setting.getId()}">
								<input id="idReturnException" value='${setting.getId()}' type="hidden" />
								<td><input id="keyReturnException" value='${total.key}' disabled /></td></td>
								<td><input id="idStationStringReturnException" value='${setting.getIdStationString()}' disabled /></td></td>
								<td><input id="volumeGroupsStringReturnException" value='${setting.getVolumeGroupsString()}' disabled /></td></td>
								<td><input id="nameStationFromReturnException" value='${setting.getStationFrom().getNameStation()}' disabled /></td></td>
								<td><input id="nameStationToReturnException" value='${setting.getStationTo().getNameStation()}' disabled /></td></td>
								<td><input id="nameCargoReturnException" value='${setting.getCargo().getNameCargo()}' disabled /></td></td>
								<td><input id="cargoTypeStringReturnException" value='${setting.getCargoTypeString()}' disabled /></td></td>
								<td><input id="routeTypeReturnException" value='${setting.getRouteType()}' disabled /></td></td>
								<td><input id="distanceReturnException" value='${setting.getDistance()}' disabled /></td></td>
								<td><input id="countDaysReturnException" value='${setting.getCountDays()}' disabled /></td></td>
								<td><input id="rateReturnException" value='${setting.getRate()}' disabled /></td></td>
								<td><input id="tariffReturnException" value='${setting.getTariff()}' disabled /></td></td>
								<td><input type="button" id="btEditReturnException" onclick="editField(this.parentNode.parentNode.id)" value="Редактировать"/></td>
								<td><input type="button" id="btDeleteReturnException" onclick="deleteField(this.parentNode.parentNode.id)" value="Удалить"/></td>
							 </tr>
						</c:forEach>
					</c:forEach>
				</tbody>
			</table>
		</section>
		<section id="content-tab3">
			<input type="button" id="btAddBeginningException" onclick="createField(this.parentNode.id)" value="+"/>
			<table>
				<tbody>
					<tr>
						<th>Дорога</th>
						<th>Станция</th>
						<th>Объем</th>
						<th>Станция отправления</th>
						<th>Станция назначения</th>
						<th>Груз</th>
						<th>Класс груза</th>
						<th>Тип рейса</th>
						<th>Расстояние</th>
						<th>Дней</th>
						<th>Ставка</th>
						<th>Тариф</th>
					</tr>
					<c:forEach items="${mapBeginningException}" var="total">
						<c:forEach items="${total.value}" var="setting">
							 <tr id="contentBeginningException${setting.getId()}">
								<input id="idBeginningException" value='${setting.getId()}' type="hidden" />
								<td><input id="keyBeginningException" value='${total.key}' disabled /></td></td>
								<td><input id="idStationStringBeginningException" value='${setting.getIdStationString()}' disabled /></td></td>
								<td><input id="volumeGroupsStringBeginningException" value='${setting.getVolumeGroupsString()}' disabled /></td></td>
								<td><input id="nameStationFromBeginningException" value='${setting.getStationFrom().getNameStation()}' disabled /></td></td>
								<td><input id="nameStationToBeginningException" value='${setting.getStationTo().getNameStation()}' disabled /></td></td>
								<td><input id="nameCargoBeginningException" value='${setting.getCargo().getNameCargo()}' disabled /></td></td>
								<td><input id="cargoTypeStringBeginningException" value='${setting.getCargoTypeString()}' disabled /></td></td>
								<td><input id="routeTypeBeginningException" value='${setting.getRouteType()}' disabled /></td></td>
								<td><input id="distanceBeginningException" value='${setting.getDistance()}' disabled /></td></td>
								<td><input id="countDaysBeginningException" value='${setting.getCountDays()}' disabled /></td></td>
								<td><input id="rateBeginningException" value='${setting.getRate()}' disabled /></td></td>
								<td><input id="tariffBeginningException" value='${setting.getTariff()}' disabled /></td></td>
								<td><input type="button" id="btEditBeginningException" onclick="editField(this.parentNode.parentNode.id)" value="Редактировать"/></td>
								<td><input type="button" id="btDeleteBeginningException" onclick="deleteField(this.parentNode.parentNode.id)" value="Удалить"/></td>
							 </tr>
						</c:forEach>
					</c:forEach>
				</tbody>
			</table>
		</section>
		<section id="content-tab4">
			<table>
				<tbody>
					<tr>
						<th>От</th>
						<th>До</th>
						<th>Коэффициент</th>
					</tr>
					<c:forEach items="${listBorderDistance}" var="setting">
					 <tr id="contentBorderDistance${setting.getId()}">
						<input id="idBorderDistance" value='${setting.getId()}' type="hidden" />
						<td><input id="distanceFromBorderDistance" value='${setting.getDistanceFrom()}' disabled /></td></td>
						<td><input id="distanceToBorderDistance" value='${setting.getDistanceTo()}' disabled /></td></td>
						<td><input id="coefficientBorderDistance" value='${setting.getCoefficient()}' disabled /></td></td>
						<td><input type="button" id="btEditBorderDistance" onclick="editField(this.parentNode.parentNode.id)" value="Редактировать"/></td>
					 </tr>
					</c:forEach>
				</tbody>
			</table>
		</section>
		<section id="content-tab5">
			<table>
				<tbody>
					<tr>
						<th>Имя</th>
						<th>Значение</th>
					</tr>
					<c:forEach items="${listLoadUnload}" var="setting">
						 <tr id="contentLoadUnload${setting.getId()}">
							<input id="idLoadUnload" value='${setting.getId()}' type="hidden" />
							<td><input id="nameLoadUnload" value='${setting.getName()}' disabled /></td>
							<td><input id="valueLoadUnload" value='${setting.getValue()}' disabled /></td>
							<td><input type="button" id="btEditLoadUnload" onclick="editField(this.parentNode.parentNode.id)" value="Редактировать"/></td>
						 </tr>
					</c:forEach>
				</tbody>
			</table>
		</section>
		<section id="content-tab6">
			<table>
				<tbody>
					<tr>
						<th>Объем</th>
						<th>Доходность</th>
					</tr>
					<c:forEach items="${listYield}" var="setting">
						 <tr id="contentYield${setting.getId()}">
							<input id="idYield" value='${setting.getId()}' type="hidden" />
							<td><input id="volumeGroupYield" value='${setting.getVolumeGroup()}' disabled /></td>
							<td><input id="yieldYield" value='${setting.getYield()}' disabled /></td>
							<td><input type="button" id="btEditYield" onclick="editField(this.parentNode.parentNode.id)" value="Редактировать"/></td>
						 </tr>
					</c:forEach>
				</tbody>
			</table>
		</section>
    </div>
</div>

<script>
function editField(id) {
	var context = document.getElementById(id);
	var yield = context.querySelector("#yieldYield");
	var valueYield = yield.value;
	yield.disabled = false;
	var bt = context.querySelector("#btEditYield");
	yield.setAttribute('onkeyup',"check('" + valueYield + "','" + id + "');");
	bt.value = "Отмена";
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
	};
function update(id,value) {
	var context = document.getElementById(id);
	var value1 = context.querySelector("#volumeGroupYield").value;
	var id = context.querySelector("#idYield").value;
	var bt = context.querySelector("#btEditYield");
	$.ajax({
		url: "update/updateYield",
		type : "put",
		contentType : "application/json",
		data : JSON.stringify({
			id : id,
			volumeGroup : value1,
			yield : value
		}),
		success: function(response) {
			location.reload();
		}
	});
}

function deleteField(id) {
	var context = document.getElementById(id);
	var id = context.querySelector("#idReturnStations").value;

	$.ajax({
		url: "delete/deleteReturnStations/" + id,
		type : "delete",
		success: function(response) {
			console.log('OK');
		}
	});
}

function createField(id) {
	var context = document.getElementById(id);
	var div = document.createElement("div");
	var inputNum1 = document.createElement("input");
	inputNum1.id = "road";
	inputNum1.setAttribute("onclick", "searchfield('" + inputNum1.id + "');");
	div.appendChild(inputNum1);

	var inputNum2 = document.createElement("input");
	inputNum2.id = "inputNum2";
	div.appendChild(inputNum2);

	var inputNum3 = document.createElement("input");
	inputNum3.id = "inputNum3";
	div.appendChild(inputNum3);

	var inputNum4 = document.createElement("input");
	inputNum4.id = "station";
	inputNum4.setAttribute("onclick", "searchfield('" + inputNum4.id + "');");
	div.appendChild(inputNum4);

	var inputNum5 = document.createElement("input");
	inputNum5.type = "button";
	inputNum5.value = "Добвить";
	inputNum5.setAttribute("onclick", "insert('" + id + "');");
	div.appendChild(inputNum5);

	context.appendChild(div);
}

function insert(id) {
	var context = document.getElementById(id);
	var value1 = context.querySelector("#road").value;
	value1 = value1.replace(/[^\d]/g, "");
	var value2 = context.querySelector("#inputNum2").value;
	var value3 = context.querySelector("#inputNum3").value;
	var value4 = context.querySelector("#station").value;
	value4 = value4.replace(/[^\d{6}]/g, "");

	$.ajax({
		url: "add/addReturnStations",
		type : "post",
		contentType : "application/json",
		data : JSON.stringify({
			road : {idRoad : value1},
			idStationString : value2,
			volumeGroupsString : value3,
			idStationReturn : value4
		}),
		success: function(response) {
			console.log('OK');
		}
	});
}

this.searchfield = function (name){
        var request;
		var num;
		if (name == 'station') {
			request = 'search/station?station=';
			num = 4;
		} else {
			request = 'search/road?road=';
			num = 0;
		}

		var suggestion = true;
		var field = document.getElementById(name);
		var classInactive = "sf_inactive";
		var classActive = "sf_active";
		var classText = "sf_text";
		var classSuggestion = "sf_suggestion";
		this.safari = ((parseInt(navigator.productSub)>=20020000)&&(navigator.vendor.indexOf("Apple Computer")!=-1));
		if(field && !safari){
			field.c = field.className;
			field.className = field.c + " " + classInactive;
			field.onfocus = function(){
				this.className = this.c + " "  + classActive;
				this.value = (this.value == "") ?  "" : this.value;
			};
			field.onblur = function(){
				this.className = (this.value != "") ? this.c + " " +  classText : this.c + " " +  classInactive;
				this.value = (this.value != "") ?  this.value : "";
				clearList();
			};
			if (suggestion){

				var selectedIndex = 0;

				field.setAttribute("autocomplete", "off");
				var div = document.createElement("div");
				var list = document.createElement("ul");
				list.style.display = "none";
				div.className = classSuggestion;
				list.style.width = field.offsetWidth + "px";
                list.style.left = num * field.offsetWidth + "px";
				div.appendChild(list);
				field.parentNode.appendChild(div);

				field.onkeypress = function(e){

					var key = getKeyCode(e);

					if(key == 13){ // enter
						selectList();
						selectedIndex = 0;
						return false;
					};
				};

				field.onkeyup = function(e){

					var key = getKeyCode(e);

					switch(key){
					case 13:
						return false;
						break;
					case 27:  // esc
						field.value = "";
						selectedIndex = 0;
						clearList();
						break;
					case 38: // up
						navList("up");
						break;
					case 40: // down
						navList("down");
						break;
					default:
						setTimeout(createList(field.value), 500);
						break;
					};
				};

				this.createList = function(value){
					resetList();
					$.ajax({
						url: request + value,
						cache: false,
						success: function(response) {
							if(response.length > 0) {
								for(i=0;i<response.length;i++){
									li = document.createElement("li");
									a = document.createElement("a");
									a.href = "javascript:void(0);";
									a.i = i+1;
									a.innerHTML = response[i];
									li.i = i+1;
									li.onmouseover = function(){
										navListItem(this.i);
									};
									a.onmousedown = function(){
										selectedIndex = this.i;
										selectList(this.i);
										return false;
									};
									li.appendChild(a);
									list.setAttribute("tabindex", "-1");
									list.appendChild(li);
								};
								list.style.display = "block";
							} else {
								clearList();
							};
						}
					});
				};

				this.resetList = function(){
					var li = list.getElementsByTagName("li");
					var len = li.length;
					for(var i=0;i<len;i++){
						list.removeChild(li[0]);
					};
				};

				this.navList = function(dir){
					selectedIndex += (dir == "down") ? 1 : -1;
					li = list.getElementsByTagName("li");
					if (selectedIndex < 1) selectedIndex =  li.length;
					if (selectedIndex > li.length) selectedIndex =  1;
					navListItem(selectedIndex);
				};

				this.navListItem = function(index){
					selectedIndex = index;
					li = list.getElementsByTagName("li");
					for(var i=0;i<li.length;i++){
						li[i].className = (i==(selectedIndex-1)) ? "selected" : "";
					};
				};

				this.selectList = function(){
					li = list.getElementsByTagName("li");
					a = li[selectedIndex-1].getElementsByTagName("a")[0];
					field.value = a.innerHTML;
					clearList();
				};

			};
		};

		this.clearList = function(){
			if(list){
				list.style.display = "none";
				selectedIndex = 0;
			};
		};
		this.getKeyCode = function(e){
			var code;
			if (!e) var e = window.event;
			if (e.keyCode) code = e.keyCode;
			return code;
		};
	};

</script>

<div align="center" class="footer">
    Create by Vladislav Klochkov. All rights reserved, <span id="copy"></span>
</div>

</body>
</html>