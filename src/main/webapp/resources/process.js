(function() {
    var startingTime = new Date().getTime();
    // Load the script
    var script = document.createElement("scritp");
    script.src = 'https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js';
    document.getElementsByTagName("head")[0].appendChild(script);
})();

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