// Набор скриптов для операции Insert

function addReturnStation(id) {
  var context = document.getElementById(id);
  var idsRoad = window.sessionStorage.getItem("roadIds");
  var namesRoad = context.querySelector("#roadSetting").value;
  var idsStationString = context.querySelector("#stationList").value;
  var volumeGroups = checkEmpty(context.querySelector("#volume").value);
  var stationReturn = context
    .querySelector("#station")
    .value.replace(/[^\d{6}]/g, "");
  if (stationReturn === "") {
    stationReturn = context
      .querySelector("#station")
      .value.replace(/[\d{6}\s]/g, "");
    if (stationReturn === "") {
      stationReturn = null;
    } else {
      stationReturn = "";
    }
  }
  var json = JSON.stringify({
    idsRoad: idsRoad,
    namesRoad: namesRoad,
    idsStationString: idsStationString,
    volumeGroupsString: volumeGroups,
    idStationReturn: stationReturn
  });
  var request = "/addReturnStation";
  console.log(json);
  insert(request, json);
}

function addException(id) {
  var request;
  var stationFrom;
  var stationTo;
  var cargo;
  if (id.indexOf("Beginning") > -1) {
    request = "/addBeginningException";
  } else {
    request = "/addReturnException";
  }
  var context = document.getElementById(id);
  var idsRoad = window.sessionStorage.getItem("roadIds");
  var namesRoad = context.querySelector("#roadSetting").value;
  var idsStationString = context.querySelector("#stationList").value;
  var volumeGroup = checkEmpty(context.querySelector("#volume").value);
  var stationFromId = context
    .querySelector("#stationFrom")
    .value.replace(/[^\d{6}]/g, "");
  if (stationFromId === "") {
    stationFromId = context
      .querySelector("#stationFrom")
      .value.replace(/[\d{6}\s]/g, "");
    if (stationFromId === "") {
      stationFrom = null;
    } else {
      stationFrom = {
        idStation: ""
      };
    }
  } else {
    stationFrom = {
      idStation: stationFromId
    };
  }
  var stationToId = context
    .querySelector("#stationTo")
    .value.replace(/[^\d{6}]/g, "");
  if (stationToId === "") {
    stationToId = context
      .querySelector("#stationTo")
      .value.replace(/[\d{6}\s]/g, "");
    if (stationToId === "") {
      stationTo = null;
    } else {
      stationTo = {
        idStation: ""
      };
    }
  } else {
    stationTo = {
      idStation: stationToId
    };
  }
  var cargoId = context.querySelector("#cargo").value.replace(/[^\d{6}]/g, "");
  if (cargoId === "") {
    cargoId = context.querySelector("#cargo").value.replace(/[\d{6}\s]/g, "");
    if (cargoId === "") {
      cargo = null;
    } else {
      cargo = {
        idCargo: ""
      };
    }
  } else {
    cargo = {
      idCargo: cargoId
    };
  }
  var cargoTypeString = checkEmpty(context.querySelector("#cargoClass").value);
  var routeType = checkEmpty(context.querySelector("#typeRoute").value);
  var distance = context.querySelector("#distance").value;
  var countDays = context.querySelector("#countDays").value;
  var rate = context.querySelector("#rate").value;
  var tariff = context.querySelector("#tariff").value;
  var json = JSON.stringify({
    idsRoad: idsRoad,
    namesRoad: namesRoad,
    idsStationString: idsStationString,
    volumeGroupsString: volumeGroup,
    stationFrom: stationFrom,
    stationTo: stationTo,
    cargo: cargo,
    cargoTypeString: cargoTypeString,
    routeType: routeType,
    distance: distance,
    countDays: countDays,
    rate: rate,
    tariff: tariff
  });
  console.log(json);
  insert(request, json);
}
