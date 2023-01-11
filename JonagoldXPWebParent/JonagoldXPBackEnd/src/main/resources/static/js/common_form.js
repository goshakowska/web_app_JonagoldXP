$(document).ready(function() {
	$("#buttonCancel").on("click", function() {
		window.location = moduleURL;
	});
	
	// $("#fileImage").change(function() {
	// 	if (!checkFileSize(this)) {
	// 		return;
	// 	}
	//
	// 	showImageThumbnail(this);
	// });
});

function showModalDialog(title, message) {
	$("#modalTitle").text(title);
	$("#modalBody").text(message);
	$("#modalDialog").modal();
}

function showErrorModal(message) {
	showModalDialog("Error", message);
}

function showWarningModal(message) {
	showModalDialog("Warning", message);
}