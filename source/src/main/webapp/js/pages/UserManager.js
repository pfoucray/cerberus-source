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
    displayPageLabel();

    // handle the click for specific action buttons
    $("#editUserButton").click(editEntryModalSaveHandler);
    $("#addUserButton").click(addEntryModalSaveHandler);

    //clear the modals fields when closed
    $('#editUserModal').on('hidden.bs.modal', editEntryModalCloseHandler);
    $('#addUserModal').on('hidden.bs.modal', addEntryModalCloseHandler);

    $('#addcheckall').click(function (e) {
        $("#addUserModal").find("#systems option").prop('selected', true)
    });
    $('#adduncheckall').click(function (e) {
        $("#addUserModal").find("#systems option").prop('selected', false)
    });
    $('#editcheckall').click(function (e) {
        $("#editUserModal").find("#systems option").prop('selected', true)
    });
    $('#edituncheckall').click(function (e) {
        $("#editUserModal").find("#systems option").prop('selected', false)
    });

    //configure and create the dataTable
    var configurations = new TableConfigurationsServerSide("usersTable", "ReadUser?systems=true&groups=true", "contentTable", aoColumnsFunc(), [1, 'asc']);
    createDataTableWithPermissions(configurations, renderOptionsForUser, "#userList");
}

function displayPageLabel() {
    var doc = new Doc();

    $("#title").html(doc.getDocLabel("page_user", "allUsers"));
    $("[name='editUserField']").html(doc.getDocLabel("page_user", "edituser_field"));
    $("[name='addUserField']").html(doc.getDocLabel("page_user", "adduser_field"));
    $("[name='loginField']").html(doc.getDocLabel("page_user", "login_field"));
    $("[name='nameField']").html(doc.getDocLabel("page_user", "name_field"));
    $("[name='teamField']").html(doc.getDocLabel("page_user", "team_field"));
    $("[name='defaultSystemField']").html(doc.getDocLabel("page_user", "defaultsystem_field"));
    $("[name='requestField']").html(doc.getDocLabel("page_user", "request_field"));
    $("[name='emailField']").html(doc.getDocLabel("page_user", "email_field"));
    $("[name='systemsField']").html(doc.getDocLabel("page_user", "systems_field"));
    $("[name='groupsField']").html(doc.getDocLabel("page_user", "groups_field"));
    $("[name='tabInformation']").html(doc.getDocLabel("page_user", "information_tab"));
    $("[name='tabSystems']").html(doc.getDocLabel("page_user", "systems_tab"));
    $("[name='buttonClose']").html(doc.getDocLabel("page_user", "close_btn"));
    $("[name='buttonAdd']").html(doc.getDocLabel("page_user", "save_btn"));

    displayHeaderLabel(doc);

    displayFooter(doc);
    displayGlobalLabel(doc);
}

function renderOptionsForUser(data) {
    var doc = new Doc();
    if ($("#createUserButton").length === 0) {
        var contentToAdd = "<div class='marginBottom10'><button id='createUserButton' type='button' class='btn btn-default'>\n\
            <span class='glyphicon glyphicon-plus-sign'></span> " + doc.getDocLabel("page_user", "button_create") + "</button></div>";
        $("#usersTable_wrapper div#usersTable_length").before(contentToAdd);
        $('#userList #createUserButton').click(addEntryClick);
    }
}

function editEntryClick(param) {
    clearResponseMessageMainPage();

    $("#editUserModal #id").val(param);

    var formEdit = $('#editUserModal');

    var jqxhr = $.getJSON("ReadUser?systems=true&groups=true", "login=" + param);
    $.when(jqxhr).then(function (data) {
        var obj = data["contentTable"];


        formEdit.find("#login").prop("value", obj["login"]);
        formEdit.find("#name").prop("value", obj["name"]);
        formEdit.find("#email").prop("value", obj["email"]);

        formEdit.find("#systems").empty();
        formEdit.find("#defaultSystem").empty();
        formEdit.find("#groups").empty();
        formEdit.find("#team").empty();

        displayInvariantList("systems", "SYSTEM", false, undefined, undefined, false);
        displayInvariantList("defaultSystem", "SYSTEM", false, undefined, undefined, false);
        displayInvariantList("team", "TEAM", false, "", "", false);
        displayInvariantList("groups", "USERGROUP", false, undefined, undefined, false);

        formEdit.find("#defaultSystem option[value='" + obj["defaultSystem"] + "']").attr('selected', true);
        formEdit.find("#request option").attr('selected', false);
        formEdit.find("#request option[value='" + obj["request"] + "']").attr('selected', true);
        formEdit.find("#team option[value='" + obj["team"] + "']").attr('selected', true);

        if (!(data["hasPermissions"])) { // If readonly, we only readonly all fields
            formEdit.find("#login").prop("readonly", "readonly");
            formEdit.find("#name").prop("readonly", "readonly");
            formEdit.find("#team").prop("readonly", "readonly");
            formEdit.find("#defaultSystem").prop("readonly", "readonly");
            formEdit.find("#request").prop("readonly", "readonly");
            formEdit.find("#email").prop("readonly", "readonly");
            formEdit.find("#systems").prop("readonly", "readonly");
            formEdit.find("#groups").prop("readonly", "readonly");

            $('#editUserButton').attr('class', '');
            $('#editUserButton').attr('hidden', 'hidden');
        }

        // SYSTEMS
        // System size will take the full size of total systems.
        var nbsystem = formEdit.find("#systems option").size();
        formEdit.find("#systems").prop('size', nbsystem);
        // Selecting the values from the current user loaded.
        formEdit.find("#systems option").each(function (i, e) {
            for (var i = 0; i < obj.systems.length; i++) {
                if (obj.systems[i].system == $(e).val()) {
                    $(e).attr('selected', 'selected');
                }
            }
        });
        // Removing the need to press ctrl on modify a selection.
        formEdit.find("#systems option").mousedown(function (e) {
            e.preventDefault();
            $(this).prop('selected', !$(this).prop('selected'));
            return false;
        });

        // GROUPS
        // Selecting the values from the current user loaded.
        formEdit.find("#groups option").each(function (i, e) {
            for (var i = 0; i < obj.groups.length; i++) {
                if (obj.groups[i].groupName == $(e).val()) {
                    $(e).attr('selected', 'selected');
                }
            }
        });
        // Removing the need to press ctrl on modify a selection AND pre(un)select on some groups.
        formEdit.find("#groups option").mousedown(function (e) {
            e.preventDefault();
            $(this).prop('selected', !$(this).prop('selected'));
            clickGroup($(this).val(), $(this).prop('selected'), $("#editUserModal"));
            return false;
        });

        formEdit.find("#groups option").click(function () {
            clickGroup($(this).val(), $(this).prop('selected'), formEdit);
        });

        formEdit.find("#defaultSystem").select2();
        formEdit.find("#team").select2({
            allowClear: true,
            placeholder: "Select a Team (Optionnal)"
        });
        formEdit.find("#request").select2({
            minimumResultsForSearch: -1
        });
    });

    formEdit.modal('show');
}

function clickGroup(groupClicked, selected, formEdit) {
    console.debug("clickGroup : " + selected);
    if (selected) {
        switch (groupClicked) {
            case "TestRO":
                break;
            case "Test":
                formEdit.find("#groups option").each(function (i, e) {
                    if ("TestRO" === $(e).val()) {
                        $(e).prop('selected', 'selected');
                    }
                });
                break;
            case "TestAdmin":
                formEdit.find("#groups option").each(function (i, e) {
                    if (("TestRO" === $(e).val()) || ("Test" === $(e).val())) {
                        $(e).prop('selected', 'selected');
                    }
                });
                break;
            case "IntegratorRO":
                break;
            case "Integrator":
                formEdit.find("#groups option").each(function (i, e) {
                    if ("IntegratorRO" === $(e).val()) {
                        $(e).prop('selected', 'selected');
                    }
                });
                break;
            case "IntegratorNewChain":
                formEdit.find("#groups option").each(function (i, e) {
                    if (("IntegratorRO" === $(e).val())) {
                        $(e).prop('selected', 'selected');
                    }
                });
                break;
            case "IntegratorDeploy":
                formEdit.find("#groups option").each(function (i, e) {
                    if (("IntegratorRO" === $(e).val())) {
                        $(e).prop('selected', 'selected');
                    }
                });
                break;
        }
    } else {
        switch (groupClicked) {
            case "TestRO":
                formEdit.find("#groups option").each(function (i, e) {
                    if (("Test" === $(e).val()) || ("TestAdmin" === $(e).val())) {
                        $(e).prop('selected', '');
                    }
                });
                break;
            case "Test":
                formEdit.find("#groups option").each(function (i, e) {
                    if ("TestAdmin" === $(e).val()) {
                        $(e).prop('selected', '');
                    }
                });
                break;
            case "TestAdmin":
                break;
            case "IntegratorRO":
                formEdit.find("#groups option").each(function (i, e) {
                    if (("Integrator" === $(e).val()) || ("IntegratorNewChain" === $(e).val()) || ("IntegratorDeploy" === $(e).val())) {
                        $(e).prop('selected', '');
                    }
                });
                break;
            case "Integrator":
                break;
            case "IntegratorNewChain":
                break;
            case "IntegratorDeploy":
                break;
        }
    }
}

function editEntryModalSaveHandler() {
    clearResponseMessage($('#editUserModal'));
    var formEdit = $('#editUserModal #editUserModalForm');

    var sa = formEdit.serializeArray();
    var data = {}
    for (var i in sa) {
        data[sa[i].name] = sa[i].value;
    }

    var systems = [];
    $('#editUserModal #systems :selected').each(function (i, selected) {
        systems[i] = $(selected).val();
    });

    data["systems"] = JSON.stringify(systems);

    var groups = [];
    $('#editUserModal #groups :selected').each(function (i, selected) {
        groups[i] = $(selected).val();
    });

    data["groups"] = JSON.stringify(groups);

    data["defaultSystem"] = $('#editUserModal #defaultSystem :selected').val();
    data["request"] = $('#editUserModal #request :selected').val();
    data["team"] = $('#editUserModal #team :selected').val();
    // Get the header data from the form.
    //var data = convertSerialToJSONObject(formEdit.serialize());

    showLoaderInModal('#editUserModal');
    $.ajax({
        url: "UpdateUser2",
        async: true,
        method: "POST",
        data: data,
        success: function (data) {
            data = JSON.parse(data);
            hideLoaderInModal('#editUserModal');
            if (getAlertType(data.messageType) === 'success') {
                $('#editUserModal').modal('hide');
                var oTable = $("#usersTable").dataTable();
                oTable.fnDraw(true);
                showMessage(data);
            } else {
                showMessage(data, $('#editUserModal'));
            }
        },
        error: showUnexpectedError
    });

}

function editEntryModalCloseHandler() {
    // reset form values
    $('#editUserModal #editUserModalForm')[0].reset();
    // remove all errors on the form fields
    $(this).find('div.has-error').removeClass("has-error");
    // clear the response messages of the modal
    clearResponseMessage($('#editUserModal'));
}

function addEntryClick() {
    clearResponseMessageMainPage();
    $("#addUserModal #user").empty();

    $("#addUserModal").find("#systems").empty();
    $("#addUserModal").find("#groups").empty();
    $("#addUserModal").find("#defaultSystem").empty();
    $("#addUserModal").find("#team").empty();

    displayInvariantList("systems", "SYSTEM", false, undefined, undefined, false);
    displayInvariantList("defaultSystem", "SYSTEM", false, undefined, undefined, false);
    displayInvariantList("groups", "USERGROUP", false, undefined, undefined, false);
    displayInvariantList("team", "TEAM", false, "", "", false);

    // System size will take the full size of total systems.
    var nbsystem = $("#addUserModal").find("#systems option").size();
    $("#addUserModal").find("#systems").prop('size', nbsystem);
    $("#addUserModal").find('#systems option').mousedown(function (e) {
        e.preventDefault();
        var select = this;
        var scroll = select.scrollTop;
        e.target.selected = !e.target.selected;
        setTimeout(function () {
            select.scrollTop = scroll;
        }, 0);
        $(select).focus();
    }).mousemove(function (e) {
        e.preventDefault()
    });

    $("#addUserModal").find('#groups option').mousedown(function (e) {
        e.preventDefault();
        $(this).prop('selected', !$(this).prop('selected'));
        clickGroup($(this).val(), $(this).prop('selected'), $("#addUserModal"));
        return false;
    });

    $("#addUserModal").find("#defaultSystem").select2();
    $("#addUserModal").find("#team").select2({
        allowClear: true,
        placeholder: "Select a Team (Optionnal)"
    });
    $("#addUserModal").find("#request").select2({
        minimumResultsForSearch: -1
    });

    $('#addUserModal').modal('show');
}

function addEntryModalSaveHandler() {
    clearResponseMessage($('#addUserModal'));
    var formEdit = $('#addUserModal #addUserModalForm');

    var sa = formEdit.serializeArray();
    var data = {}
    for (var i in sa) {
        data[sa[i].name] = sa[i].value;
    }

    var systems = [];
    $('#addUserModal #systems :selected').each(function (i, selected) {
        systems[i] = $(selected).val();
    });

    data["systems"] = JSON.stringify(systems);

    var groups = [];
    $('#addUserModal #groups :selected').each(function (i, selected) {
        groups[i] = $(selected).val();
    });

    data["groups"] = JSON.stringify(groups);

    data["defaultSystem"] = $('#addUserModal #defaultSystem :selected').val();
    data["request"] = $('#addUserModal #request :selected').val();
    data["team"] = $('#addUserModal #team :selected').val();

    showLoaderInModal('#addUserModal');
    $.ajax({
        url: "CreateUser2",
        async: true,
        method: "POST",
        data: data,
        success: function (data) {
            data = JSON.parse(data);
            hideLoaderInModal('#addUserModal');
            if (getAlertType(data.messageType) === 'success') {
                $('#addUserModal').modal('hide');
                var oTable = $("#usersTable").dataTable();
                oTable.fnDraw(true);
                showMessage(data);
            } else {
                showMessage(data, $('#addUserModal'));
            }
        },
        error: showUnexpectedError
    });

}

function addEntryModalCloseHandler() {
    // reset form values
    $('#addUserModal #addUserModalForm')[0].reset();
    // remove all errors on the form fields
    $(this).find('div.has-error').removeClass("has-error");
    // clear the response messages of the modal
    clearResponseMessage($('#addUserModal'));
}

function removeEntryClick(key) {
    var doc = new Doc();
    showModalConfirmation(function (ev) {
        var id = $('#confirmationModal #hiddenField1').prop("value");
        $.ajax({
            url: "DeleteUser2?login=" + key,
            async: true,
            method: "GET",
            success: function (data) {
                hideLoaderInModal('#removeTestampaignModal');
                var oTable = $("#usersTable").dataTable();
                oTable.fnDraw(true);
                $('#removeUserModal').modal('hide');
                showMessage(data);
            },
            error: showUnexpectedError
        });

        $('#confirmationModal').modal('hide');
    }, doc.getDocLabel("page_user", "title_remove"), doc.getDocLabel("page_user", "message_remove"), id, undefined, undefined, undefined);
}

function aoColumnsFunc(tableId) {
    var doc = new Doc();
    var aoColumns = [
        {
            "data": null,
            "bSortable": false,
            "bSearchable": false,
            "title": doc.getDocLabel("page_user", "button_col"),
            "mRender": function (data, type, obj) {
                var hasPermissions = $("#" + tableId).attr("hasPermissions");

                var editUser = '<button id="editUser" onclick="editEntryClick(\'' + obj["login"] + '\');"\n\
                                        class="editUser btn btn-default btn-xs margin-right5" \n\
                                        name="editUser" title="' + doc.getDocLabel("page_user", "button_edit") + '" type="button">\n\
                                        <span class="glyphicon glyphicon-pencil"></span></button>';
                var removeUser = '<button id="removeUser" onclick="removeEntryClick(\'' + obj["login"] + '\');"\n\
                                        class="removeUser btn btn-default btn-xs margin-right5" \n\
                                        name="removeUser" title="' + doc.getDocLabel("page_user", "button_remove") + '" type="button">\n\
                                        <span class="glyphicon glyphicon-trash"></span></button>';

                return '<div class="center btn-group width150">' + editUser + removeUser + '</div>';

            },
            "width": "100px"
        },
        {"data": "login", "sName": "login", "title": doc.getDocLabel("page_user", "login_col")},
        {"data": "name", "sName": "name", "title": doc.getDocLabel("page_user", "name_col")},
        {
            "data": null,
            "bSortable": false,
            "bSearchable": false,
            "title": doc.getDocLabel("page_user", "groups_col"),
            "mRender": function (data, type, obj) {
                var systems = "";
                for (var i = 0; i < obj["groups"].length; i++) {
                    if (i > 0) {
                        systems += ", ";
                    }
                    systems += obj["groups"][i].groupName;
                }

                return '<div class="center btn-group width150">' + systems + '</div>';

            }
        },
        {
            "data": null,
            "bSortable": false,
            "bSearchable": false,
            "title": doc.getDocLabel("page_user", "systems_col"),
            "mRender": function (data, type, obj) {
                var systems = "";
                for (var i = 0; i < obj["systems"].length; i++) {
                    if (i > 0) {
                        systems += ", ";
                    }
                    systems += obj["systems"][i].system;
                }

                return '<div class="center btn-group width150">' + systems + '</div>';

            }
        },
        {"data": "team", "sName": "team", "title": doc.getDocLabel("page_user", "team_col")},
        {"data": "defaultSystem", "sName": "defaultSystem", "title": doc.getDocLabel("page_user", "defaultsystem_col")},
        {"data": "request", "sName": "reqest", "title": doc.getDocLabel("page_user", "request_col")},
        {"data": "email", "sName": "email", "title": doc.getDocLabel("page_user", "email_col")}
    ];
    return aoColumns;
}
