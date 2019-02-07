  // Набор скриптов для операции Insert

  function addReturnStation(id) {
      var context = document.getElementById(id);
      var idsRoad = window.sessionStorage.getItem("roadIds");
      var namesRoad = context.querySelector("#roadSetting").value;
      var idsStationString = context.querySelector("#stationList").value;
      var volumeGroups = context.querySelector("#volume").value;
      var stationReturn = context
        .querySelector("#station")
        .value.replace(/[^\d{6}]/g, "");
      var json = JSON.stringify({
        idsRoad: idsRoad,
        namesRoad: namesRoad,
        idsStationString: idsStationString,
        volumeGroupsString: volumeGroups,
        idStationReturn: stationReturn
      });
      var request = "/addReturnStation";
      insert(request, json);
    }

  function addException(id) {
    var request;
    if (id.indexOf("Beginning") > -1) {
      request = "/addBeginningException";
    } else {
      request = "/addReturnException";
    }
    var context = document.getElementById(id);
    var idsRoad = window.sessionStorage.getItem("roadIds");
    var namesRoad = context.querySelector("#roadSetting").value;
    var idsStationString = context.querySelector("#stationList").value;
    var volumeGroup = context.querySelector("#volume").value;
    var stationFrom = context
      .querySelector("#stationFrom")
      .value.replace(/[^\d{6}}]/g, "");
    var stationTo = context
      .querySelector("#stationTo")
      .value.replace(/[^\d{6}}]/g, "");
    var cargo = context
      .querySelector("#cargo")
      .value.replace(/[^\d{6}}]/g, "");
    var cargoTypeString = context.querySelector("#cargoClass").value;
    var routeType = context.querySelector("#typeRoute").value;
    var distance = context.querySelector("#distance").value;
    var countDays = context.querySelector("#countDays").value;
    var rate = context.querySelector("#rate").value;
    var tariff = context.querySelector("#tariff").value;
    var json = JSON.stringify({
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
    insert(request, json);
  }
