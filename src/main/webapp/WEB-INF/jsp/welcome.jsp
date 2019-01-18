<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js" type="text/javascript"></script>
	<title>UralTransCom|DynamicDistributionPark</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="resources/style.css" rel="stylesheet" type="text/css"/>
    <link rel="shortcut icon" href="resources/favicon.ico" type="image/x-icon">
</head>
<body>

<style>
#searchfield{width: 250px; height: 18px;}


/* suggestions box */
/* js code generates unordered list */
.sf_suggestion{
	position:relative;
}
/* Это стиль выползающего блока */
.sf_suggestion ul{
    border:1px #222 solid;
	position:absolute;
	margin:0;
	padding:0;
	background:#fff;
	top:0;
	left:0;

}
/* Стиль появляющегося списка */
.sf_suggestion li{
	margin:0;
	padding:0;
	list-style:none;
}
/* Стиль сылок в блоке */
.sf_suggestion li a{
	display:block;
	text-indent:5px;
	color:#444;
	text-decoration:none;
}
/* Стиль при выборе одного из пунктов */
.sf_suggestion li.selected a{
	background:#ccc;
}
</style>
<script>
	function calcRate() {
		var input1 = $('input[name="station_from"]').val();
		var input2 = $('input[name="station_to"]').val();
		var input3 = $('input[name="cargo"]').val();
		var input4 = $('input[name="volume"]').val();
		$.ajax({
			url: 'rate/info',
			datatype : 'json',
			type : "post",
			contentType : "application/json",
			data : JSON.stringify({
				stationFrom : input1,
				stationTo : input2,
				cargo : input3,
				volume : input4
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

	this.searchfield = function(name){
		var request;
		var num;
		if (name == 'station1') {
			request = 'search/station?station=';
			num = 0;
		} else if (name == 'station2') {
			request = 'search/station?station=';
			num = 1;
		} else {
			request = 'search/cargo?cargo=';
			num = 2;
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
						setTimeout(createList(field.value), 400);
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

<div>
	<input type="text" id="station1" onkeyup="searchfield(this.id)" value="" name="station_from" />
	<input type="text" id="station2" onkeyup="searchfield(this.id)" value="" name="station_to" />
	<input type="text" id="cargo1" onkeyup="searchfield(this.id)" value="" name="cargo" />
	<input type="text" name="volume" />
	<input type="button" onclick="calcRate()" value="Рассчитать ставку" />
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

</body>
</html>