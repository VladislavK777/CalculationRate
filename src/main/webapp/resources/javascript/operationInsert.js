// Набор скриптов для операции Insert

function addReturnStations(id) {
	var context = document.getElementById(id);
	var road = context.querySelector("#road").value.replace(/[^\d]/g, "");
	var stations = context.querySelector("#inputNum2").value;
	var volumeGroups = context.querySelector("#inputNum3").value;
	var stationReturn = context.querySelector("#station").value.replace(/[^\d{6}]/g, "");
	var json = JSON.stringify({
			road : {idRoad : road},
			idStationString : stations,
			volumeGroupsString : volumeGroups,
			idStationReturn : stationReturn
		});
	var request = "/addReturnStations";
	insert(request,json);
}

function addExceptions(id) {
	var request;
	if (name.indexOf('Beginning') > -1) {
		request = "/addBeginningExceptions";
	} else {
		request = "/addReturnExceptions";
	}
	var context = document.getElementById(id);
	var road = context.querySelector("#road").value.replace(/[^\d]/g, "");
	var stations = context.querySelector("#inputNum2").value;
	var volumeGroup = context.querySelector("#inputNum3").value;
	var stationFrom = context.querySelector("#stationFtom" + id).value.replace(/[^\d{6}}]/g, "");
	var stationTo = context.querySelector("#stationTo" + id).value.replace(/[^\d{6}}]/g, "");
	var cargo = context.querySelector("#cargo" + id).value.replace(/[^\d{6}}]/g, "");
	var cargoTypeString = context.querySelector("#inputNum4").value;
	var routeType = context.querySelector("#inputNum5").value;
	var distance = context.querySelector("#inputNum6").value;
	var countDays = context.querySelector("#inputNum7").value;
	var rate = context.querySelector("#inputNum8").value;
	var tariff = context.querySelector("#inputNum9").value;
	var json = JSON.stringify({
			road : {idRoad : road},
			idStationString : stations,
			staionFrom : {idStation : stationFrom},
			staionTo : {idStation : stationTo},
			cargo : {idCargo : cargo},
			cargoTypeString : cargoTypeString,
			routeType : routeType,
			distance : distance,
			countDays : countDays,
			rate : rate,
			tariff : tariff
		});
	insert(request,json);
}