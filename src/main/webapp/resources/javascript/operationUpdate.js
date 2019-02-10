//Набор скриптов для операции Update

function updateFieldReturnStation(id) {
  var context = document.getElementById(id);
  var ide = context.querySelector("#idReturnStation").value;
  var idsRoad = window.sessionStorage.getItem("roadIds");
  var namesRoad = context.querySelector("#roadReturnStationSetting" + id).value;
  var idsStationString = context.querySelector("#idStationStringReturnStation").value;
  var volumeGroup = context.querySelector("#volumeGroupsStringReturnStation")
    .value;
  var returnStation = context
    .querySelector("#stationReturnStation" + id)
    .value.replace(/[^\d{6}}]/g, "");
  var json = JSON.stringify({
    id: ide,
    idsRoad: idsRoad,
    namesRoad: namesRoad,
    idsStationString: idsStationString,
    volumeGroupsString: volumeGroup,
    idStationReturn: returnStation
  });
  var request = "/updateReturnStation";
  console.log(json);
  update(id, request, json);
}

function updateFieldYield(id) {
  var context = document.getElementById(id);
  var ide = context.querySelector("#idYield").value;
  var yield = context.querySelector("#yieldYield").value;
  var json = JSON.stringify({
    id: ide,
    yield: yield
  });
  var request = "/updateYield";
  update(id, request, json);
}

function updateFieldOther(id) {
  var context = document.getElementById(id);
  var ide = context.querySelector("#idOther").value;
  var value = context.querySelector("#valueOther").value;
  var json = JSON.stringify({
    id: ide,
    value: value
  });
  var request = "/updateOther";
  update(id, request, json);
}

function updateFieldLoadUnload(id) {
  var context = document.getElementById(id);
  var ide = context.querySelector("#idLoadUnload").value;
  var value = context.querySelector("#valueLoadUnload").value;
  var json = JSON.stringify({
    id: ide,
    value: value
  });
  var request = "/updateLoadUnload";
  update(id, request, json);
}

function updateFieldBorderDistance(id) {
  var context = document.getElementById(id);
  var ide = context.querySelector("#idBorderDistance").value;
  var distanceFrom = context.querySelector("#distanceFromBorderDistance").value;
  var distanceTo = context.querySelector("#distanceToBorderDistance").value;
  var coefficient = context.querySelector("#coefficientBorderDistance").value;
  var json = JSON.stringify({
    id: ide,
    distanceFrom: distanceFrom,
    distanceTo: distanceTo,
    coefficient: coefficient
  });
  var request = "/updateBorderDistance";
  update(id, request, json);
}

function updateFieldBeginningException(id) {
  var context = document.getElementById(id);
  var ide = context.querySelector("#idBeginningException").value;
  var idsRoad = window.sessionStorage.getItem("roadIds");
  var namesRoad = context.querySelector("#roadBeginningExceptionSetting" + id).value;
  var idsStationString = context.querySelector("#idStationStringBeginningException")
    .value;
  var volumeGroup = context.querySelector(
    "#volumeGroupsStringBeginningException"
  ).value;
  var stationFrom = context
    .querySelector("#stationFromBeginningException" + id)
    .value.replace(/[^\d{6}}]/g, "");
  var stationTo = context
    .querySelector("#stationToBeginningException" + id)
    .value.replace(/[^\d{6}}]/g, "");
  var cargo = context
    .querySelector("#cargoBeginningException" + id)
    .value.replace(/[^\d{6}}]/g, "");
  var cargoTypeString = context.querySelector(
    "#cargoTypeStringBeginningException"
  ).value;
  var routeType = context.querySelector("#routeTypeBeginningException" + id).value;
  var distance = context.querySelector("#distanceBeginningException").value;
  var countDays = context.querySelector("#countDaysBeginningException").value;
  var rate = context.querySelector("#rateBeginningException").value;
  var tariff = context.querySelector("#tariffBeginningException").value;
  var json = JSON.stringify({
    id: ide,
    idsRoad: idsRoad,
    namesRoad: namesRoad,
    idsStationString: idsStationString,
    volumeGroupsString: volumeGroup,
    stationFrom: { idStation: stationFrom },
    stationTo: { idStation: stationTo },
    cargo: { idCargo: cargo },
    cargoTypeString: cargoTypeString,
    routeType: routeType,
    distance: distance,
    countDays: countDays,
    rate: rate,
    tariff: tariff
  });
  var request = "/updateBeginningException";
  update(id, request, json);
}

function updateFieldReturnException(id) {
  var context = document.getElementById(id);
  var ide = context.querySelector("#idReturnException").value;
  var idsRoad = window.sessionStorage.getItem("roadIds");
  var namesRoad = context.querySelector("#roadReturnExceptionSetting" + id).value;
  var idsStationString = context.querySelector("#idStationStringReturnException").value;
  var volumeGroup = context.querySelector("#volumeGroupsStringReturnException")
    .value;
  var stationFrom = context
    .querySelector("#stationFromReturnException" + id)
    .value.replace(/[^\d{6}}]/g, "");
  var stationTo = context
    .querySelector("#stationToReturnException" + id)
    .value.replace(/[^\d{6}}]/g, "");
  var cargo = context
    .querySelector("#cargoReturnException" + id)
    .value.replace(/[^\d{6}}]/g, "");
  var cargoTypeString = context.querySelector("#cargoTypeStringReturnException")
    .value;
  var routeType = context.querySelector("#routeTypeReturnException" + id).value;
  var distance = context.querySelector("#distanceReturnException").value;
  var countDays = context.querySelector("#countDaysReturnException").value;
  var rate = context.querySelector("#rateReturnException").value;
  var tariff = context.querySelector("#tariffReturnException").value;
  var json = JSON.stringify({
    id: ide,
    idsRoad: idsRoad,
    namesRoad: namesRoad,
    idsStationString: idsStationString,
    volumeGroupsString: volumeGroup,
    stationFrom: { idStation: stationFrom },
    stationTo: { idStation: stationTo },
    cargo: { idCargo: cargo },
    cargoTypeString: cargoTypeString,
    routeType: routeType,
    distance: distance,
    countDays: countDays,
    rate: rate,
    tariff: tariff
  });
  var request = "/updateReturnException";
  update(id, request, json);
}
