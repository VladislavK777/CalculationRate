//Набор скриптов для операции Update

function updateFieldReturnStation(id) {
  var context = document.getElementById(id);
  var ide = context.querySelector("#idReturnStation").value;
  var namesRoad = checkEmpty(context.querySelector("#roadReturnStation").value);
  var idsStationString = context.querySelector("#idStationStringReturnStation")
    .value;
  var volumeGroup = checkEmpty(
    context.querySelector("#volumeGroupsStringReturnStation").value
  );
  var returnStation = context
    .querySelector("#stationReturnStation" + ide)
    .value.replace(/[^\d{6}]/g, "");
  if (returnStation === "") {
    returnStation = context
      .querySelector("#stationReturnStation" + ide)
      .value.replace(/[\d{6}\s]/g, "");
    if (returnStation === "") {
      returnStation = null;
    } else {
      returnStation = "";
    }
  }
  var array = wrapperPrepareStringToArray(
    context.querySelector("#departmentStationReturnStation" + ide).value
  );
  var idsDepartmentArray = array != null ? array[0] : null,
    namesDepartmentArray = array != null ? array[1] : null;
  var json = JSON.stringify({
    id: ide,
    namesRoad: namesRoad,
    idsStationString: idsStationString,
    idsDepartment: idsDepartmentArray,
    namesDepartment: namesDepartmentArray,
    volumeGroupsString: volumeGroup,
    idStationReturn: returnStation
  });
  var request = "/updateReturnStation";
  console.log(json);
  update(id, request, json);
}

function updateFieldBeginningException(id) {
  var stationFrom;
  var stationTo;
  var cargo;
  var context = document.getElementById(id);
  var ide = context.querySelector("#idBeginningException").value;
  var namesRoad = checkEmpty(
    context.querySelector("#roadBeginningException").value
  );
  var idsStationString = context.querySelector(
    "#idStationStringBeginningException"
  ).value;
  var volumeGroup = checkEmpty(
    context.querySelector("#volumeGroupsStringBeginningException").value
  );
  var stationFromId = context
    .querySelector("#stationFromBeginningException" + ide)
    .value.replace(/[^\d{6}]/g, "");
  if (stationFromId == "") {
    stationFromId = context
      .querySelector("#stationFromBeginningException" + ide)
      .value.replace(/[\d{6}\s]/g, "");
    if (stationFromId == "") {
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
    .querySelector("#stationToBeginningException" + ide)
    .value.replace(/[^\d{6}]/g, "");
  if (stationToId == "") {
    stationToId = context
      .querySelector("#stationToBeginningException" + ide)
      .value.replace(/[\d{6}\s]/g, "");
    if (stationToId == "") {
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
  var cargoId = context
    .querySelector("#cargoBeginningException" + ide)
    .value.replace(/[^\d{6}]/g, "");
  if (cargoId == "") {
    cargoId = context
      .querySelector("#cargoBeginningException" + ide)
      .value.replace(/[\d{6}\s]/g, "");
    if (cargoId == "") {
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
  var cargoTypeString = checkEmpty(
    context.querySelector("#cargoTypeStringBeginningException").value
  );
  var routeType = checkEmpty(
    context.querySelector("#routeTypeBeginningException" + ide).value
  );
  var distance = context.querySelector("#distanceBeginningException").value;
  var countDays = context.querySelector("#countDaysBeginningException").value;
  var rate = context.querySelector("#rateBeginningException").value;
  var tariff = context.querySelector("#tariffBeginningException").value;
  var array = wrapperPrepareStringToArray(
    context.querySelector("#departmentStationBeginningException" + ide).value
  );
  var idsDepartmentArray = array != null ? array[0] : null,
    namesDepartmentArray = array != null ? array[1] : null;
  var json = JSON.stringify({
    id: ide,
    namesRoad: namesRoad,
    idsStationString: idsStationString,
    idsDepartment: idsDepartmentArray,
    namesDepartment: namesDepartmentArray,
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
  var request = "/updateBeginningException";
  console.log(json);
  update(id, request, json);
}

function updateFieldReturnException(id) {
  var stationFrom;
  var stationTo;
  var cargo;
  var context = document.getElementById(id);
  var ide = context.querySelector("#idReturnException").value;
  var namesRoad = checkEmpty(
    context.querySelector("#roadReturnException").value
  );
  var idsStationString = context.querySelector(
    "#idStationStringReturnException"
  ).value;
  var volumeGroup = checkEmpty(
    context.querySelector("#volumeGroupsStringReturnException").value
  );
  var stationFromId = context
    .querySelector("#stationFromReturnException" + ide)
    .value.replace(/[^\d{6}]/g, "");
  if (stationFromId == "") {
    stationFromId = context
      .querySelector("#stationFromReturnException" + ide)
      .value.replace(/[\d{6}\s]/g, "");
    if (stationFromId == "") {
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
    .querySelector("#stationToReturnException" + ide)
    .value.replace(/[^\d{6}]/g, "");
  if (stationToId == "") {
    stationToId = context
      .querySelector("#stationToReturnException" + ide)
      .value.replace(/[\d{6}\s]/g, "");
    if (stationToId == "") {
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
  var cargoId = context
    .querySelector("#cargoReturnException" + ide)
    .value.replace(/[^\d{6}]/g, "");
  if (cargoId == "") {
    cargoId = context
      .querySelector("#cargoReturnException" + ide)
      .value.replace(/[\d{6}\s]/g, "");
    if (cargoId == "") {
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
  var cargoTypeString = checkEmpty(
    context.querySelector("#cargoTypeStringReturnException").value
  );
  var routeType = checkEmpty(
    context.querySelector("#routeTypeReturnException" + ide).value
  );
  var distance = context.querySelector("#distanceReturnException").value;
  var countDays = context.querySelector("#countDaysReturnException").value;
  var rate = context.querySelector("#rateReturnException").value;
  var tariff = context.querySelector("#tariffReturnException").value;
  var array = wrapperPrepareStringToArray(
    context.querySelector("#departmentStationReturnException" + ide).value
  );
  var idsDepartmentArray = array != null ? array[0] : null,
    namesDepartmentArray = array != null ? array[1] : null;
  var json = JSON.stringify({
    id: ide,
    namesRoad: namesRoad,
    idsStationString: idsStationString,
    idsDepartment: idsDepartmentArray,
    namesDepartment: namesDepartmentArray,
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
  var request = "/updateReturnException";
  console.log(json);
  update(id, request, json);
}

function updateFieldYield(id) {
  var context = document.getElementById(id);
  var ide = context.querySelector("#idYield").value;
  var yieldVal = context.querySelector("#yieldYield").value;
  var json = JSON.stringify({
    id: ide,
    yield: yieldVal
  });
  var request = "/updateYield";
  console.log(json);
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
  console.log(json);
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
  console.log(json);
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
  console.log(json);
  update(id, request, json);
}
