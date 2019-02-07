// Набор скриптов для операции Delete

function deleteFieldReturnStation(id) {
  var del = confirm("Вы хотите удалить настройку?");
  if (del) {
    var context = document.getElementById(id);
    var id = context.querySelector("#idReturnStation").value;
    var request = "/deleteReturnStation/" + id;
    deleteField(request);
  } else {
    return;
  }
}

function deleteFieldReturnException(id) {
  var del = confirm("Вы хотите удалить настройку?");
  if (del) {
    var context = document.getElementById(id);
    var id = context.querySelector("#idReturnException").value;
    var request = "/deleteReturnException/" + id;
    deleteField(request);
  } else {
    return;
  }
}

function deleteFieldBeginningException(id) {
  var del = confirm("Вы хотите удалить настройку?");
  if (del) {
    var context = document.getElementById(id);
    var id = context.querySelector("#idBeginningException").value;
    var request = "/deleteBeginningException/" + id;
    deleteField(request);
  } else {
    return;
  }
}
