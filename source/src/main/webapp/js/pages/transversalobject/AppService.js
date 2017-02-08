/*
 * Cerberus  Copyright (C) 2013  vertigo17
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This file is part of Cerberus.
 *
 * Cerberus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Cerberus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Cerberus.  If not, see <http://www.gnu.org/licenses/>.
 */

function displayAppServiceLabel(doc) {


    $("[name='soapLibraryField']").html(doc.getDocLabel("appservice", "service"));
    $("[name='typeField']").html(doc.getDocLabel("appservice", "type"));
    $("[name='descriptionField']").html(doc.getDocLabel("appservice", "description"));
    $("[name='servicePathField']").html(doc.getDocLabel("appservice", "servicePath"));
    $("[name='methodField']").html(doc.getDocLabel("appservice", "method"));
    $("[name='buttonClose']").html(doc.getDocLabel("page_appservice", "close_btn"));
    $("[name='buttonAdd']").html(doc.getDocLabel("page_appservice", "save_btn"));
    $("#soapLibraryListLabel").html("<span class='glyphicon glyphicon-list'></span> " + doc.getDocLabel("appservice", "service"));
    // Tracability
    $("[name='lbl_created']").html(doc.getDocOnline("transversal", "DateCreated"));
    $("[name='lbl_creator']").html(doc.getDocOnline("transversal", "UsrCreated"));
    $("[name='lbl_lastModified']").html(doc.getDocOnline("transversal", "DateModif"));
    $("[name='lbl_lastModifier']").html(doc.getDocOnline("transversal", "UsrModif"));
    // Tracability
    $("[name='lbl_datecreated']").html(doc.getDocOnline("transversal", "DateCreated"));
    $("[name='lbl_usrcreated']").html(doc.getDocOnline("transversal", "UsrCreated"));
    $("[name='lbl_datemodif']").html(doc.getDocOnline("transversal", "DateModif"));
    $("[name='lbl_usrmodif']").html(doc.getDocOnline("transversal", "UsrModif"));
}

/***
 * Open the modal with testcase information.
 * @param {String} service - type selected
 * @returns {null}
 */
function editAppServiceClick(service) {

    var doc = new Doc();
    $("[name='editSoapLibraryField']").html(doc.getDocLabel("page_appservice", "editSoapLibrary_field"));

    $("#editSoapLibraryButton").off("click");
    $("#editSoapLibraryButton").click(function () {
        confirmAppServiceModalHandler("EDIT");
    });

    // Prepare all Events handler of the modal.
    prepareAppServiceModal();

    $('#editSoapLibraryButton').attr('class', 'btn btn-primary');
    $('#editSoapLibraryButton').removeProp('hidden');
    $('#duplicateSoapLibraryButton').attr('class', '');
    $('#duplicateSoapLibraryButton').attr('hidden', 'hidden');
    $('#addSoapLibraryButton').attr('class', '');
    $('#addSoapLibraryButton').attr('hidden', 'hidden');

    feedAppServiceModal(service, "editSoapLibraryModal", "EDIT");
}

/***
 * Open the modal with testcase information.
 * @param {String} test - type selected
 * @param {String} testCase - type selected
 * @returns {null}
 */
function duplicateAppServiceClick(service) {
    $("#duplicateSoapLibraryButton").off("click");
    $("#duplicateSoapLibraryButton").click(function () {
        confirmAppServiceModalHandler("DUPLICATE");
    });

    // Prepare all Events handler of the modal.
    prepareAppServiceModal();

    $('#editSoapLibraryButton').attr('class', '');
    $('#editSoapLibraryButton').attr('hidden', 'hidden');
    $('#duplicateSoapLibraryButton').attr('class', 'btn btn-primary');
    $('#duplicateSoapLibraryButton').removeProp('hidden');
    $('#addSoapLibraryButton').attr('class', '');
    $('#addSoapLibraryButton').attr('hidden', 'hidden');

    feedAppServiceModal(service, "editSoapLibraryModal", "DUPLICATE");
}

/***
 * Open the modal in order to create a new testcase.
 * @returns {null}
 */
function addAppServiceClick() {
    $("#addSoapLibraryButton").off("click");
    $("#addSoapLibraryButton").click(function () {
        confirmAppServiceModalHandler("ADD");
    });

    // Prepare all Events handler of the modal.
    prepareAppServiceModal();

    $('#editSoapLibraryButton').attr('class', '');
    $('#editSoapLibraryButton').attr('hidden', 'hidden');
    $('#duplicateSoapLibraryButton').attr('class', '');
    $('#duplicateSoapLibraryButton').attr('hidden', 'hidden');
    $('#addSoapLibraryButton').attr('class', 'btn btn-primary');
    $('#addSoapLibraryButton').removeProp('hidden');

    feedNewAppServiceModal("editSoapLibraryModal");
}

/***
 * Function that initialise the modal with event handlers.
 * @returns {null}
 */
function prepareAppServiceModal() {

    // when type is changed we enable / disable type field.
    $("#editSoapLibraryModal #type").off("change");
    $("#editSoapLibraryModal #type").change(function () {
        refreshDisplayOnTypeChange($(this).val());
    });

    //Highlight envelop on modal loading
    Prism.highlightElement($("#srvRequest")[0]);

    /**
     * On edition, get the caret position, refresh the envelope to have 
     * syntax coloration in real time, then set the caret position.
     */
    $('#editSoapLibraryModal #srvRequest').off("keyup");
    $('#editSoapLibraryModal #srvRequest').on("keyup", function (e) {
        //Get the position of the carret
        var pos = $(this).caret('pos');

        //On Firefox only, when pressing enter, it create a <br> tag.
        //So, if the <br> tag is present, replace it with <span>&#13;</span>
        if ($("#editSoapLibraryModal #srvRequest br").length !== 0) {
            $("#editSoapLibraryModal #srvRequest br").replaceWith("<span>&#13;</span>");
            pos++;
        }
        //Apply syntax coloration
        Prism.highlightElement($("#editSoapLibraryModal #srvRequest")[0]);
        //Set the caret position to the initia one.
        $(this).caret('pos', pos);
    });

    //On click on <pre> tag, focus on <code> tag to make the modification into this element,
    //Add class on container to highlight field
    $('#editSoapLibraryModal #srvRequestContainer').off("click");
    $('#editSoapLibraryModal #srvRequestContainer').on("click", function (e) {
        $('#editSoapLibraryModal #srvRequestContainer').addClass('highlightedContainer');
        $('#editSoapLibraryModal #srvRequest').focus();
    });

    //Remove class to stop highlight envelop field
    $('#editSoapLibraryModal #srvRequest').off("blur");
    $('#editSoapLibraryModal #srvRequest').on('blur', function () {
        $('#editSoapLibraryModal #srvRequestContainer').removeClass('highlightedContainer');
    });


}


/***
 * Function that support the modal confirmation. Will call servlet to comit the transaction.
 * @param {String} mode - either ADD, EDIT or DUPLICATE in order to define the purpose of the modal.
 * @returns {null}
 */
function confirmAppServiceModalHandler(mode) {
    clearResponseMessage($('#editSoapLibraryModal'));

    var formEdit = $('#editSoapLibraryModal #editSoapLibraryModalForm');

    showLoaderInModal('#editSoapLibraryModal');

    // Enable the test combo before submit the form.
    if (mode === 'EDIT') {
        formEdit.find("#service").removeAttr("disabled");
    }
    // Calculate servlet name to call.
    var myServlet = "UpdateAppService";
    if ((mode === "ADD") || (mode === "DUPLICATE")) {
        myServlet = "CreateAppService";
    }

    // Get the header data from the form.
    var data = convertSerialToJSONObject(formEdit.serialize());

    //Add envelope, not in the form
    data.srvRequest = encodeURIComponent($("#editSoapLibraryModal #srvRequest").text());

    showLoaderInModal('#editTestCaseModal');
    $.ajax({
        url: myServlet,
        async: true,
        method: "POST",
        data: {
            service: data.service,
            application: data.application,
            type: data.type,
            method: data.method,
            servicePath: data.servicePath,
            operation: data.operation,
            description: data.description,
            group: data.group,
            serviceRequest: data.srvRequest
        },
        success: function (data) {
            data = JSON.parse(data);
            hideLoaderInModal('#editSoapLibraryModal');
            if (getAlertType(data.messageType) === "success") {
                var oTable = $("#soapLibrarysTable").dataTable();
                oTable.fnDraw(true);
                $('#editSoapLibraryModal').data("Saved", true);
                $('#editSoapLibraryModal').modal('hide');
                showMessage(data);
            } else {
                showMessage(data, $('#editSoapLibraryModal'));
            }
        },
        error: showUnexpectedError
    });
    if (mode === 'EDIT') { // Disable back the test combo before submit the form.
        formEdit.find("#service").prop("disabled", "disabled");
    }

}

function refreshDisplayOnTypeChange(newValue) {
    console.debug("Debug.");
    if (newValue === "SOAP") {
        // If SOAP service, no need to feed the method.
        $('#editSoapLibraryModal #method').prop("disabled", true);
        $('#editSoapLibraryModal #operation').prop("readonly", false);
    } else {
        $('#editSoapLibraryModal #method').prop("disabled", false);
        $('#editSoapLibraryModal #operation').prop("readonly", true);
    }
}


/***
 * Feed the TestCase modal with all the data from the TestCase.
 * @param {String} modalId - type selected
 * @returns {null}
 */
function feedNewAppServiceModal(modalId) {
    clearResponseMessageMainPage();

    var formEdit = $('#' + modalId);

    // Feed the data to the screen and manage authorities.
    feedAppServiceData(undefined, modalId, "ADD", true);

    formEdit.modal('show');
}

/***
 * Feed the TestCase modal with all the data from the TestCase.
 * @param {String} serviceName - type selected
 * @param {String} modalId - type selected
 * @param {String} mode - either ADD, EDIT or DUPLICATE in order to define the purpose of the modal.
 * @returns {null}
 */
function feedAppServiceModal(serviceName, modalId, mode) {
    clearResponseMessageMainPage();

    var formEdit = $('#' + modalId);

    $.ajax({
        url: "ReadAppService?service=" + serviceName,
        async: true,
        method: "GET",
        success: function (data) {
            if (data.messageType === "OK") {

                // Feed the data to the screen and manage authorities.
                var service = data;
                feedAppServiceData(service, modalId, mode, data["hasPermissions"]);

                // Force a change event on method field.
                refreshDisplayOnTypeChange(service.type);

                formEdit.modal('show');
            } else {
                showUnexpectedError();
            }
        },
        error: showUnexpectedError
    });

}


function feedAppServiceData(service, modalId, mode, hasPermissionsUpdate) {
    var formEdit = $('#' + modalId);
    var doc = new Doc();

    console.debug("feedAppServiceData " + service + "-" + mode + hasPermissionsUpdate);
    // Data Feed.
    if (mode === "EDIT") {
        $("[name='editSoapLibraryField']").html(doc.getDocOnline("page_appservice", "button_edit"));
        formEdit.find("#service").prop("value", service.service);
        formEdit.find("#usrcreated").prop("value", service.UsrCreated);
        formEdit.find("#datecreated").prop("value", service.DateCreated);
        formEdit.find("#usrmodif").prop("value", service.UsrModif);
        formEdit.find("#datemodif").prop("value", service.DateModif);
    } else { // DUPLICATE or ADD
        formEdit.find("#usrcreated").prop("value", "");
        formEdit.find("#datecreated").prop("value", "");
        formEdit.find("#usrmodif").prop("value", "");
        formEdit.find("#datemodif").prop("value", "");
        if (mode === "ADD") {
            $("[name='editSoapLibraryField']").html(doc.getDocOnline("page_appservice", "button_create"));
            formEdit.find("#service").prop("value", "");
        } else { // DUPLICATE
            $("[name='editSoapLibraryField']").html(doc.getDocOnline("page_appservice", "button_duplicate"));
            formEdit.find("#service").prop("value", service.service);
        }
    }
    if (isEmpty(service)) {
        formEdit.find("#originalService").prop("value", "");
        formEdit.find("#application").prop("value", "");
        formEdit.find("#type").prop("value", "REST");
        refreshDisplayOnTypeChange("REST");
        formEdit.find("#method").prop("value", "GET");
        formEdit.find("#servicePath").prop("value", "");
        formEdit.find("#srvRequest").text("");
        formEdit.find("#group").prop("value", "");
        formEdit.find("#operation").prop("value", "");
        formEdit.find("#description").prop("value", "");
    } else {
        formEdit.find("#application").val(service.application);
        formEdit.find("#type").val(service.type);
        formEdit.find("#method").val(service.method);
        formEdit.find("#servicePath").prop("value", service.servicePath);
        formEdit.find("#srvRequest").text(service.serviceRequest);
        formEdit.find("#group").prop("value", service.group);
        formEdit.find("#operation").prop("value", service.operation);
        formEdit.find("#description").prop("value", service.description);
    }

    // Authorities
    if (mode === "EDIT") {
        formEdit.find("#service").prop("readonly", "readonly");
        console.debug("readonly");
    } else {
        formEdit.find("#service").removeAttr("readonly");
        formEdit.find("#service").removeProp("readonly");
        console.debug("Not readonly");
    }
    //We desactivate or activate the access to the fields depending on if user has the credentials to edit.
    if (!(hasPermissionsUpdate)) { // If readonly, we readonly all fields
        //Service info
        formEdit.find("#application").prop("readonly", "readonly");
        formEdit.find("#type").prop("disabled", "disabled");
        formEdit.find("#method").prop("disabled", "disabled");
        formEdit.find("#servicePath").prop("readonly", "readonly");
        formEdit.find("#srvRequest").prop("readonly", "readonly");
        formEdit.find("#description").prop("readonly", "readonly");
        // We hide Save button.
        $('#editSoapLibraryButton').attr('class', '');
        $('#editSoapLibraryButton').attr('hidden', 'hidden');
    } else {
        //test case info
        formEdit.find("#application").removeProp("readonly");
        formEdit.find("#type").removeProp("disabled");
        formEdit.find("#method").removeProp("disabled");
        formEdit.find("#servicePath").removeProp("readonly");
        formEdit.find("#srvRequest").removeProp("readonly");
        formEdit.find("#description").removeProp("disabled");
    }

}



