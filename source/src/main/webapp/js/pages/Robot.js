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

$.when($.getScript("js/pages/global/global.js")).then(function () {
    $(document).ready(function () {
        initPage();
    });
});

function initPage() {
    var doc = new Doc();
    displayPageLabel();
    $("[name=screensize]").append($('<option></option>').text(doc.getDocLabel("page_runtest","default_full_screen")).val(""));
    displayInvariantList("screensize", "SCREENSIZE", false);
    
    // Load the select needed in localStorage cache.
    getSelectInvariant("CAPABILITY", true);
    
    // handle the click for specific action buttons
    $("#addEntryButton").click(addEntryModalSaveHandler);
    $("#editEntryButton").click(editEntryModalSaveHandler);

    //clear the modals fields when closed
    $('#addEntryModal').on('hidden.bs.modal', {extra: "#addEntryModal"}, buttonCloseHandler);
    $('#editEntryModal').on('hidden.bs.modal', {extra: "#editEntryModal"}, buttonCloseHandler);
    
    // Adding rows in modals.
    $("#addAddCapabilitiy").click(addNewCapabilityRow.bind(null, "addCapabilitiesTableBody"));
    $("#addEditCapabilitiy").click(addNewCapabilityRow.bind(null, "editCapabilitiesTableBody"));

    //configure and create the dataTable
    var configurations = new TableConfigurationsServerSide("robotsTable", "ReadRobot", "contentTable", aoColumnsFunc("robotsTable"), [1, 'asc']);

    createDataTableWithPermissions(configurations, renderOptionsForRobot, "#robotList");
}

function displayPageLabel() {
    var doc = new Doc();

    displayHeaderLabel(doc);
    displayGlobalLabel(doc);
    $("#pageTitle").html(doc.getDocLabel("page_robot", "title"));
    $("#title").html(doc.getDocOnline("page_robot", "title"));
    $("[name='addEntryField']").html(doc.getDocLabel("page_robot", "button_create"));
    $("[name='confirmationField']").html(doc.getDocLabel("page_robot", "button_delete"));
    $("[name='editEntryField']").html(doc.getDocLabel("page_robot", "button_edit"));
    $("[name='robotField']").html(doc.getDocOnline("robot", "robot"));
    $("[name='hostField']").html(doc.getDocOnline("robot", "host"));
    $("[name='portField']").html(doc.getDocOnline("robot", "port"));
    $("[name='platformField']").html(doc.getDocOnline("robot", "platform"));
    $("[name='browserField']").html(doc.getDocOnline("robot", "browser"));
    $("[name='versionField']").html(doc.getDocOnline("robot", "version"));
    $("[name='activeField']").html(doc.getDocOnline("robot", "active"));
    $("[name='useragentField']").html(doc.getDocOnline("robot", "useragent"));
    $("[name='screensizeField']").html(doc.getDocOnline("robot", "screensize"));
    $("[name='descriptionField']").html(doc.getDocOnline("robot", "description"));
    $("[name='addCapabilityHeader']").html(doc.getDocOnline("robot", "capabilityCapability"));
    $("[name='addValueHeader']").html(doc.getDocOnline("robot", "capabilityValue"));
    $("[name='editCapabilityHeader']").html(doc.getDocOnline("robot", "capabilityCapability"));
    $("[name='editValueHeader']").html(doc.getDocOnline("robot", "capabilityValue"));
    displayInvariantList("active", "ROBOTACTIVE", false);
    displayInvariantList("browser", "BROWSER", false);
    displayInvariantList("platform", "PLATFORM", false, undefined, "");
    
    displayFooter(doc);
}

function renderOptionsForRobot(data) {
    var doc = new Doc();
    //check if user has permissions to perform the add and import operations
    if (data["hasPermissions"]) {
        if ($("#createRobotButton").length === 0) {
            var contentToAdd = "<div class='marginBottom10'><button id='createRobotButton' type='button' class='btn btn-default'>\n\
            <span class='glyphicon glyphicon-plus-sign'></span> " + doc.getDocLabel("page_robot", "button_create") + "</button></div>";

            $("#robotsTable_wrapper #robotsTable_length").before(contentToAdd);
            $('#robotList #createRobotButton').click(addEntryClick);
        }
    }
}

function deleteEntryHandlerClick() {
    var robotID = $('#confirmationModal').find('#hiddenField1').prop("value");
    var jqxhr = $.post("DeleteRobot", {robotid: robotID}, "json");
    $.when(jqxhr).then(function (data) {
        var messageType = getAlertType(data.messageType);
        if (messageType === "success") {
            //redraw the datatable
            var oTable = $("#robotsTable").dataTable();
            oTable.fnDraw(true);
            var info = oTable.fnGetData().length;

            if (info === 1) {//page has only one row, then returns to the previous page
                oTable.fnPageChange('previous');
            }

        }
        //show message in the main page
        showMessageMainPage(messageType, data.message);
        //close confirmation window
        $('#confirmationModal').modal('hide');
    }).fail(handleErrorAjaxAfterTimeout);
}

function deleteEntryClick(entry, name) {
    clearResponseMessageMainPage();
    var doc = new Doc();
    var messageComplete = doc.getDocLabel("page_global", "message_delete");
    messageComplete = messageComplete.replace("%TABLE%", doc.getDocLabel("robot", "robot"));
    messageComplete = messageComplete.replace("%ENTRY%", name);
    showModalConfirmation(deleteEntryHandlerClick, doc.getDocLabel("page_robot", "button_delete"), messageComplete, entry, "", "", "");
}

function addEntryModalSaveHandler() {
    clearResponseMessage($('#addEntryModal'));
    var formAdd = $("#addEntryModal #addEntryModalForm");

    var nameElement = formAdd.find("#robot");
    var nameElementEmpty = nameElement.prop("robot") === '';
    if (nameElementEmpty) {
        var localMessage = new Message("danger", "Please specify the name of the Robot.");
        nameElement.parents("div.form-group").addClass("has-error");
        showMessage(localMessage, $('#addEntryModal'));
    } else {
        nameElement.parents("div.form-group").removeClass("has-error");
    }

    // verif if all mendatory fields are not empty
    if (nameElementEmpty)
        return;

    // Getting Data from Capabilities TAB
    var capabilityTable = $("#addCapabilitiesTableBody tr");
    var capabilities = [];
    for (var i = 0; i < capabilityTable.length; i++) {
        var capability = $(capabilityTable[i]).data("capability");
        if (!capability.toDelete) {
            capabilities.push(capability);
        }
    }
    
    // Get the header data from the form.
    var data = convertSerialToJSONObject(formAdd.serialize());
    data.capabilities = JSON.stringify(capabilities);

    showLoaderInModal('#addEntryModal');
    saveEntry("CreateRobot", "#addEntryModal", data);

}

function addEntryClick() {
    clearResponseMessageMainPage();
    $('#addCapabilitiesTableBody tr').remove();
    $('#addEntryModal').modal('show');
}

function editEntryModalSaveHandler() {
    clearResponseMessage($('#editEntryModal'));
    var formEdit = $('#editEntryModal #editEntryModalForm');

    // Getting Data from Capabilities TAB
    var capabilityTable = $("#editCapabilitiesTableBody tr");
    var capabilities = [];
    for (var i = 0; i < capabilityTable.length; i++) {
        var capability = $(capabilityTable[i]).data("capability");
        if (!capability.toDelete) {
            capabilities.push(capability);
        }
    }
    
    // Get the header data from the form.
    var data = convertSerialToJSONObject(formEdit.serialize());
    data.capabilities = JSON.stringify(capabilities);

    showLoaderInModal('#editEntryModal');
    saveEntry("UpdateRobot", "#editEntryModal", data);
}

function editEntryClick(id) {
    clearResponseMessageMainPage();
    var jqxhr = $.getJSON("ReadRobot", "robotid=" + id);
    $.when(jqxhr).then(function (data) {
        var obj = data["contentTable"];

        var formEdit = $('#editEntryModal');

        formEdit.find("#robotid").prop("value", id);
        formEdit.find("#robot").prop("value", obj["robot"]);
        formEdit.find("#active").prop("value", obj["active"]);
        formEdit.find("#host").prop("value", obj["host"]);
        formEdit.find("#port").prop("value", obj["port"]);
        formEdit.find("#platform").prop("value", obj["platform"]);
        formEdit.find("#browser").prop("value", obj["browser"]);
        formEdit.find("#version").prop("value", obj["version"]);
        formEdit.find("#useragent").prop("value", obj["userAgent"]);
        formEdit.find("#screensize").prop("value", obj["screenSize"]);
        formEdit.find("#Description").prop("value", obj["description"]);
        
        loadCapabilitiesTable("editCapabilitiesTableBody", obj["capabilities"]);

        if (!(data["hasPermissions"])) { // If readonly, we only readonly all fields
            formEdit.find("#robot").prop("readonly", "readonly");
            formEdit.find("#active").prop("disabled", "disabled");
            formEdit.find("#host").prop("readonly", "readonly");
            formEdit.find("#port").prop("readonly", "readonly");
            formEdit.find("#platform").prop("readonly", "readonly");
            formEdit.find("#browser").prop("readonly", "readonly");
            formEdit.find("#version").prop("readonly", "readonly");
            formEdit.find("#useragent").prop("readonly", "readonly");
            formEdit.find("#screensize").prop("readonly", "readonly");
            formEdit.find("#Description").prop("readonly", "readonly");

            $('#editEntryButton').attr('class', '');
            $('#editEntryButton').attr('hidden', 'hidden');
            
            $('#addEditCapabilitiy').attr('class', '');
            $('#addEditCapabilitiy').attr('hidden', 'hidden');
        }

        formEdit.modal('show');
    });
}

function loadCapabilitiesTable(tableBody, capabilities) {
    $('#' + tableBody + ' tr').remove();
    $.each(capabilities, function (idx, capability) {
        capability.toDelete = false;
        appendCapabilityRow(tableBody, capability);
    });
}

function appendCapabilityRow(tableBody, capability) {
    var doc = new Doc();
    var deleteBtn = $("<button type=\"button\"></button>").addClass("btn btn-default btn-xs").append($("<span></span>").addClass("glyphicon glyphicon-trash"));
    var selectCapability = getSelectInvariant("CAPABILITY", false);
    var valueInput = $("<input  maxlength=\"150\" placeholder=\"-- " + doc.getDocLabel("robot", "capabilityValue") + " --\">").addClass("form-control input-sm").val(capability.value);
    var table = $("#" + tableBody);

    var row = $("<tr></tr>");
    var deleteBtnRow = $("<td></td>").append(deleteBtn);
    var cap = $("<td></td>").append(selectCapability.val(capability.capability));
    var value = $("<td></td>").append(valueInput);
    deleteBtn.click(function () {
        capability.toDelete = (capability.toDelete) ? false : true;
        if (capability.toDelete) {
            row.addClass("danger");
        } else {
            row.removeClass("danger");
        }
    });
    selectCapability.change(function () {
        capability.capability = $(this).val();
    });
    valueInput.change(function () {
        capability.value = $(this).val();
    });
    row.append(deleteBtnRow);
    row.append(cap);
    row.append(value);
    capability.capability = selectCapability.prop("value"); // Value that has been requested by dtb parameter may not exist in combo vlaues so we take the real selected value.
    row.data("capability", capability);
    table.append(row);
}

function addNewCapabilityRow(tableBody) {
    var newCapability = {
        capability: "",
        value: ""
    };
    appendCapabilityRow(tableBody, newCapability);
}

function saveEntry(servletName, modalID, data) {
    $.ajax({
        url: servletName,
        async: true,
        method: "POST",
        data: data,
        success: function (data) {
            hideLoaderInModal(modalID);
            if (getAlertType(data.messageType) === 'success') {
                var oTable = $("#robotsTable").dataTable();
                oTable.fnDraw(true);
                showMessage(data);
                $(modalID).modal('hide');
            } else {
                showMessage(data, $(modalID));
            }
        },
        error: showUnexpectedError
    });
}

function buttonCloseHandler(event) {
    var modalID = event.data.extra;
    // reset form values
    $(modalID + " " + modalID + "Form")[0].reset();
    // remove all errors on the form fields
    $(this).find('div.has-error').removeClass("has-error");
    // clear the response messages of the modal
    clearResponseMessage($(modalID));
}

function aoColumnsFunc(tableId) {
    var doc = new Doc();

    var aoColumns = [
        {"data": null,
            "title": doc.getDocLabel("page_global", "columnAction"),
            "bSortable": false,
            "bSearchable": false,
            "mRender": function (data, type, obj) {
                var hasPermissions = $("#" + tableId).attr("hasPermissions");
                var editEntry = '<button id="editEntry" onclick="editEntryClick(\'' + obj["robotID"] + '\');"\n\
                                    class="editEntry btn btn-default btn-xs margin-right5" \n\
                                    name="editEntry" title="' + doc.getDocLabel("page_robot", "button_edit") + '" type="button">\n\
                                    <span class="glyphicon glyphicon-pencil"></span></button>';
                var viewEntry = '<button id="editEntry" onclick="editEntryClick(\'' + obj["robotID"] + '\');"\n\
                                    class="editEntry btn btn-default btn-xs margin-right5" \n\
                                    name="editEntry" title="' + doc.getDocLabel("page_robot", "button_edit") + '" type="button">\n\
                                    <span class="glyphicon glyphicon-eye-open"></span></button>';
                var deleteEntry = '<button id="deleteEntry" onclick="deleteEntryClick(\'' + obj["robotID"] + '\',\'' + obj["robot"] + '\');" \n\
                                    class="deleteEntry btn btn-default btn-xs margin-right5" \n\
                                    name="deleteEntry" title="' + doc.getDocLabel("page_robot", "button_delete") + '" type="button">\n\
                                    <span class="glyphicon glyphicon-trash"></span></button>';
                if (hasPermissions === "true") { //only draws the options if the user has the correct privileges
                    return '<div class="center btn-group width150">' + editEntry + deleteEntry + '</div>';
                }
                return '<div class="center btn-group width150">' + viewEntry + '</div>';
            }
        },
        {"data": "robot",
            "sName": "robot",
            "title": doc.getDocOnline("robot", "robot")},
        {"data": "host",
            "sName": "host",
            "title": doc.getDocOnline("robot", "host")},
        {"data": "port",
            "sName": "port",
            "title": doc.getDocOnline("robot", "port")},
        {"data": "platform",
            "sName": "platform",
            "title": doc.getDocOnline("robot", "platform")},
        {"data": "browser",
            "sName": "browser",
            "title": doc.getDocOnline("robot", "browser")},
        {"data": "version",
            "sName": "version",
            "title": doc.getDocOnline("robot", "version")},
        {"data": "active",
            "sName": "active",
            "title": doc.getDocOnline("robot", "active")},
        {"data": "userAgent",
            "sName": "userAgent",
            "title": doc.getDocOnline("robot", "useragent")},
        {"data": "screenSize",
            "sName": "screenSize",
            "title": doc.getDocOnline("robot", "screensize")},
        {"data": "description",
            "sName": "description",
            "title": doc.getDocOnline("robot", "description")}
    ];
    return aoColumns;
}