// Набор скриптов для операции Delete 

function deleteFieldReturnStations(id) {
	var context = document.getElementById(id);
	var id = context.querySelector("idReturnStations");
	var request = "/deleteReturnStations/" + id;
	deleteField(request);
}

function deleteFieldReturnExceptions(id) {
	var context = document.getElementById(id);
	var id = context.querySelector("idReturnExceptions");
	var request = "/deleteReturnExceptions/" + id;
	deleteField(request);
}

function deleteFieldBeginningExceptions(id) {
	var context = document.getElementById(id);
	var id = context.querySelector("idBeginningExceptions");
	var request = "/deleteBeginningExceptions/" + id;
	deleteField(request);
}
