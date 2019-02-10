// Набор скриптов для операции Delete

function deleteFieldReturnStation(id) {
  var del = confirm("Вы хотите удалить настройку?");
  if (del) {
    var context = document.getElementById(id);
    var ide = context.querySelector("#idReturnStation").value;
    var request = "/deleteReturnStation/" + ide;
    deleteField(request);
  } else {
    return;
  }
}

function deleteFieldReturnException(id) {
  var del = confirm("Вы хотите удалить настройку?");
  if (del) {
    var context = document.getElementById(id);
    var ide = context.querySelector("#idReturnException").value;
    var request = "/deleteReturnException/" + ide;
    deleteField(request);
  } else {
    return;
  }
}

function deleteFieldBeginningException(id) {
  var del = confirm("Вы хотите удалить настройку?");
  if (del) {
    var context = document.getElementById(id);
    var ide = context.querySelector("#idBeginningException").value;
    var request = "/deleteBeginningException/" + ide;
    deleteField(request);
  } else {
    return;
  }
}
