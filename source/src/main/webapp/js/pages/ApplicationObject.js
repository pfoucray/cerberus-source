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
        initPageModal("applicationObject");
    });
});

function initPage() {
    displayPageLabel();

    var application = GetURLParameter("application");

    // handle the click for specific action buttons
    $("#editApplicationObjectButton").click(editApplicationObjectModalSaveHandler);

    //clear the modals fields when closed
    $('#editApplicationObjectModal').on('hidden.bs.modal', editApplicationObjectModalCloseHandler);

    //configure and create the dataTable
    var configurations = new TableConfigurationsServerSide("applicationObjectsTable", "ReadApplicationObject?system=" + getUser().defaultSystem, "contentTable", aoColumnsFunc("applicationObjectsTable"), [1, 'asc']);
    createDataTableWithPermissions(configurations, renderOptionsForApplicationObject, "#applicationObjectList");

    if(application != null) {
        clearIndividualFilter("applicationObjectsTable",undefined,true);
        filterOnColumn("applicationObjectsTable", "application", application);
    }
}

function displayPageLabel() {
    var doc = new Doc();

    displayHeaderLabel(doc);
    $("#pageTitle").html(doc.getDocLabel("page_applicationObject", "title"));
    $("#title").html(doc.getDocOnline("page_applicationObject", "title"));
    $("[name='editApplicationObjectField']").html(doc.getDocLabel("page_applicationObject", "editapplicationobjectfield"));

    displayFooter(doc);
}

function renderOptionsForApplicationObject(data) {
    var doc = new Doc();
    //check if user has permissions to perform the add and import operations
    if (data["hasPermissions"]) {
        if ($("#createApplicationObjectButton").length === 0) {
            var contentToAdd = "<div class='marginBottom10'><button id='createApplicationObjectButton' type='button' class='btn btn-default'>\n\
            <span class='glyphicon glyphicon-plus-sign'></span> " + doc.getDocLabel("page_applicationObject", "button_create") + "</button></div>";

            $("#applicationObjectsTable_wrapper div#applicationObjectsTable_length").before(contentToAdd);
            $('#applicationObjectList #createApplicationObjectButton').click(addApplicationObjectModalClick);
        }
    }
}

function deleteEntryHandlerClick() {
    var application = $('#confirmationModal').find('#hiddenField1').prop("value");
    var object = $('#confirmationModal').find('#hiddenField2').prop("value");
    var jqxhr = $.post("DeleteApplicationObject", {application:application,object:object}, "json");
    $.when(jqxhr).then(function (data) {
        var messageType = getAlertType(data.messageType);
        if (messageType === "success") {
            //redraw the datatable
            var oTable = $("#applicationObjectsTable").dataTable();
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

function deleteEntryClick(application, object) {
    clearResponseMessageMainPage();
    var doc = new Doc();
    var messageComplete = doc.getDocLabel("page_applicationObject", "message_delete");
    messageComplete = messageComplete.replace("%ENTRY%", application + ", " + object);
    showModalConfirmation(deleteEntryHandlerClick, doc.getDocLabel("page_applicationObject", "button_delete"), messageComplete, application, object, "", "");
}


function editApplicationObjectModalSaveHandler() {
    clearResponseMessage($('#editApplicationObjectModal'));
    $('#editApplicationObjectModal #editApplicationObjectModalForm select#application').attr("disabled",false);
    var formEdit = $('#editApplicationObjectModal #editApplicationObjectModalForm');
    var file = $("#editApplicationObjectModal input[type=file]");
    // Get the header data from the form.
    var sa = formEdit.serializeArray();
    var formData = new FormData();
    var data = {}
    for (var i in sa) {
        formData.append(sa[i].name, sa[i].value);
    }

    formData.append("file",file.prop("files")[0]);

    showLoaderInModal('#editApplicationObjectModal');
    $.ajax({
        type: "POST",
        url: "UpdateApplicationObject",
        data: formData,
        async: true,
        processData: false,
        contentType: false,
        success: function (data) {
            hideLoaderInModal('#editApplicationObjectModal');
            if (getAlertType(data.messageType) === "success") {
                var oTable = $("#applicationObjectsTable").dataTable();
                oTable.fnDraw(true);
                $('#editApplicationObjectModal').modal('hide');
                showMessage(data);
            } else {
                showMessage(data, $('#editApplicationObjectModal'));
            }
        },
        error: showUnexpectedError
    });

}

function editApplicationObjectModalCloseHandler() {
    // reset form values
    $('#editApplicationObjectModal #editApplicationObjectModalForm')[0].reset();
    // remove all errors on the form fields
    $(this).find('div.has-error').removeClass("has-error");
    // clear the response messages of the modal
    clearResponseMessage($('#editApplicationObjectModal'));
}

function editApplicationObjectClick(application, object) {
    clearResponseMessageMainPage();
    $('#editApplicationObjectModal #application').empty();
    displayApplicationList("application","",application);
    var jqxhr = $.getJSON("ReadApplicationObject", "application=" + application + "&object=" + object);
    $.when(jqxhr).then(function (data) {
        var obj = data["contentTable"];

        var formEdit = $('#editApplicationObjectModal');

        formEdit.find("#application option[value='" + obj["application"] + "']").prop("selected", true);
        formEdit.find("#object").prop("value", obj["object"]);
        formEdit.find("#value").prop("value", obj["value"]);
        //formEdit.find("#screenshotfilename").prop("value", obj["screenshotfilename"]);

        formEdit.find("#object").prop("readonly", "readonly");
        formEdit.find("#application").prop("disabled", "disabled");

        if (!(data["hasPermissions"])) { // If readonly, we only readonly all fields

            formEdit.find("#screenshotfilename").prop("readonly", "readonly");
            formEdit.find("#application").prop("readonly", "readonly");

            $('#editApplicationObjectButton').attr('class', '');
            $('#editApplicationObjectButton').attr('hidden', 'hidden');
        }

        formEdit.modal('show');
    });

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

                var editApplicationObject = '<button id="editApplicationObject" onclick="editApplicationObjectClick(\'' + obj["application"] + '\', \'' + obj["object"] + '\');"\n\
                                    class="editApplicationObject btn btn-default btn-xs margin-right5" \n\
                                    name="editApplicationObject" title="' + doc.getDocLabel("page_applicationObject", "button_edit") + '" type="button">\n\
                                    <span class="glyphicon glyphicon-pencil"></span></button>';
                var viewApplicationObject = '<button id="editApplicationObject" onclick="editApplicationObjectClick(\'' + obj["application"] + '\', \'' + obj["object"] + '\');"\n\
                                    class="editApplicationObject btn btn-default btn-xs margin-right5" \n\
                                    name="editApplicationObject" title="' + doc.getDocLabel("page_applicationObject", "button_edit") + '" type="button">\n\
                                    <span class="glyphicon glyphicon-eye-open"></span></button>';
                var deleteApplicationObject = '<button id="deleteApplicationObject" onclick="deleteEntryClick(\'' + obj["application"] + '\', \'' + obj["object"] + '\');" \n\
                                    class="deleteApplicationObject btn btn-default btn-xs margin-right5" \n\
                                    name="deleteApplicationObject" title="' + doc.getDocLabel("page_applicationObject", "button_delete") + '" type="button">\n\
                                    <span class="glyphicon glyphicon-trash"></span></button>';
                if (hasPermissions === "true") { //only draws the options if the user has the correct privileges
                    return '<div class="center btn-group width150">' + editApplicationObject + deleteApplicationObject + '</div>';
                }
                return '<div class="center btn-group width150">' + viewApplicationObject + '</div>';
            }
        },
        {"data": "application",
            "sName": "application",
            "title": doc.getDocOnline("page_applicationObject", "Application")},
        {"data": "object",
            "sName": "object",
            "title": doc.getDocOnline("page_applicationObject", "Object")},
        {"data": "value",
            "sName": "value",
            "title": doc.getDocOnline("page_applicationObject", "Value")},
        {"data": "screenshotfilename",
            "sName": "screenshotfilename",
            "title": doc.getDocOnline("page_applicationObject", "ScreenshotFileName"),
            "mRender": function(data, type, obj) {
                return "<image src='ReadApplicationObjectImage?application=" + obj["application"] + "&object=" + obj["object"] + "&time=" + new Date().getTime() + "'></image>"
            }
        },
        {"data": "usrcreated",
            "sName": "usrcreated",
            "title": doc.getDocOnline("page_applicationObject", "UsrCreated")},
        {"data": "datecreated",
            "sName": "datecreated",
            "title": doc.getDocOnline("page_applicationObject", "DateCreated")},
        {"data": "usrmodif",
            "sName": "usrmodif",
            "title": doc.getDocOnline("page_applicationObject", "UsrModif")
        },
        {"data": "datemodif",
            "sName": "datemodif",
            "title": doc.getDocOnline("page_applicationObject", "DateModif")
        }
    ];
    return aoColumns;
}