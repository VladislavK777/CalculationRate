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

function createField() {
	var context = document.getElementById('root');
	context.hidden = false;

	showPopup();

	$(document).ready(function () {
		$(popup_bg).click(function () {
			$(popup).fadeOut(800);
		});
	});
}

function update(request,json) {
	$.ajax({
		url: "update" + request,
		type : "put",
		contentType : "application/json",
		data : json,
		success: function(response) {
			//location.reload();
			console.log("ok");
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

function reloadPage() {
	location.reload();
}

this.searchfield = function (name){
        var request;
		var num;
		if (name.indexOf('station') > -1) {
			request = 'search/station?station=';
			num = 0;
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
