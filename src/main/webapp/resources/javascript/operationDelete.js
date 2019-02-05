// Набор скриптов для операции Delete

function deleteFieldReturnStations(id) {
  var del = confirm("Вы хотите удалить настройку?");
  if (del) {
    var context = document.getElementById(id);
    var id = context.querySelector("#idReturnStation").value;
    var request = "/deleteReturnStations/" + id;
    deleteField(request);
  } else {
    return;
  }
}

function deleteFieldReturnExceptions(id) {
  var del = confirm("Вы хотите удалить настройку?");
  if (del) {
    var context = document.getElementById(id);
    var id = context.querySelector("#idReturnException").value;
    var request = "/deleteReturnExceptions/" + id;
    deleteField(request);
  } else {
    return;
  }
}

function deleteFieldBeginningExceptions(id) {
  var del = confirm("Вы хотите удалить настройку?");
  if (del) {
    var context = document.getElementById(id);
    var id = context.querySelector("#idBeginningException").value;
    var request = "/deleteBeginningExceptions/" + id;
    deleteField(request);
  } else {
    return;
  }
}
