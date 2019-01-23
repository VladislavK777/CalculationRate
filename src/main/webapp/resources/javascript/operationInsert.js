  // Набор скриптов для операции Insert

  function addReturnStations(id) {
    var context = document.getElementById(id);
    var road = context.querySelector("#road").value.replace(/[^\d]/g, "");
    var stations = context.querySelector("#stationList").value;
    var volumeGroups = context.querySelector("#volume").value;
    var stationReturn = context
      .querySelector("#station")
      .value.replace(/[^\d{6}]/g, "");
    var json = JSON.stringify({
      road: { idRoad: road },
      idStationString: stations,
      volumeGroupsString: volumeGroups,
      idStationReturn: stationReturn
    });
    var request = "/addReturnStations";
    insert(request, json);
  }

  function addExceptions(id) {
    var request;
    if (id.indexOf("Beginning") > -1) {
      request = "/addBeginningExceptions";
    } else {
      request = "/addReturnExceptions";
    }
    var context = document.getElementById(id);
    var road = context.querySelector("#road").value.replace(/[^\d]/g, "");
    var stations = context.querySelector("#stationList").value;
    var volumeGroup = context.querySelector("#volume").value;
    var stationFrom = context
      .querySelector("#stationFtom" + id)
      .value.replace(/[^\d{6}}]/g, "");
    var stationTo = context
      .querySelector("#stationTo" + id)
      .value.replace(/[^\d{6}}]/g, "");
    var cargo = context
      .querySelector("#cargo" + id)
      .value.replace(/[^\d{6}}]/g, "");
    var cargoTypeString = context.querySelector("#cargoClass").value;
    var routeType = context.querySelector("#typeRoute").value;
    var distance = context.querySelector("#distance").value;
    var countDays = context.querySelector("#countDays").value;
    var rate = context.querySelector("#rate").value;
    var tariff = context.querySelector("#tariff").value;
    var json = JSON.stringify({
      road: { idRoad: road },
      idStationString: stations,
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
