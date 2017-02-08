<%--
  ~ Cerberus  Copyright (C) 2013  vertigo17
  ~ DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
  ~
  ~ This file is part of Cerberus.
  ~
  ~ Cerberus is free software: you can redistribute it and/or modify
  ~ it under the terms of the GNU General Public License as published by
  ~ the Free Software Foundation, either version 3 of the License, or
  ~ (at your option) any later version.
  ~
  ~ Cerberus is distributed in the hope that it will be useful,
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~ GNU General Public License for more details.
  ~
  ~ You should have received a copy of the GNU General Public License
  ~ along with Cerberus.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ include file="include/dependenciesInclusions.html" %>
        <script type="text/javascript" src="js/pages/CerberusInformation.js"></script>
        <title id="pageTitle">Cerberus Information</title>
    </head>
    <body>
        <%@ include file="include/header.html" %>
        <div class="container-fluid center" id="page-layout">
            <%@ include file="include/messagesArea.html"%>
            <%@ include file="include/utils/modal-confirmation.html"%>

            <h1 class="page-title-line" id="title">Cerberus Information</h1>

            <div class="row">

                <div class="col-lg-12" id="FiltersPanel">

                    <div class="panel panel-default">
                        <div class="panel-heading card clearfix" data-toggle="collapse" data-target="#cerberusActivity">
                            <span class="fa fa-tag fa-fw"></span>
                            <label id="filters" name="filtersField">Cerberus Activity</label>
                            <span class="toggle glyphicon glyphicon-chevron-right pull-right"></span>
                        </div>
                        <div class="panel-body collapse in" id="cerberusActivity">
                            <div class="row">
                                <div class="form-group col-xs-12">
                                    <button type="button" class="btn btn-default" id="btnReset" name="btnReset"  onclick="resetThreadPool()">Reset Queue</button>
                                    <button type="button" class="btn btn-default" id="btnRefresh" name="btnRefresh"  onclick="feedContent()">Refresh</button>
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-xs-12">
                                    <table class="table table-bordered table-hover nomarginbottom dataTable" id="sessionNbTable">
                                        <thead>
                                            <tr>
                                                <th class="text-center" id="systemHeader" name="systemHeader">Number of HTTP Session opened</th>
                                            </tr>
                                        </thead>
                                        <tbody id="sessionNbTableBody">
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-xs-12">
                                    <table class="table table-bordered table-hover nomarginbottom dataTable" id="sessionTable">
                                        <thead>
                                            <tr>
                                                <th class="text-center" id="systemHeader" name="systemHeader">Active User List</th>
                                            </tr>
                                        </thead>
                                        <tbody id="sessionTableBody">
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-xs-12">
                                    <table class="table table-bordered table-hover nomarginbottom dataTable" id="exeNbTable">
                                        <thead>
                                            <tr>
                                                <th class="text-center" id="systemHeader" name="systemHeader">Number of Actual Simultaneous Execution</th>
                                            </tr>
                                        </thead>
                                        <tbody id="exeNbTableBody">
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-xs-12">
                                    <table class="table table-bordered table-hover nomarginbottom dataTable" id="exeTable">
                                        <thead>
                                            <tr>
                                                <th class="text-center">ID</th>
                                                <th class="text-center">Start</th>
                                                <th class="text-center">System</th>
                                                <th class="text-center">Application</th>
                                                <th class="text-center">Test</th>
                                                <th class="text-center">TestCase</th>
                                                <th class="text-center">Environment</th>
                                                <th class="text-center">Country</th>
                                                <th class="text-center">Robot</th>
                                                <th class="text-center">Tag</th>
                                            </tr>
                                        </thead>
                                        <tbody id="exeTableBody">
                                        </tbody>
                                    </table>
                                </div>
                            </div>
<!--                            <div class="row">
                                <div class="form-group col-xs-12">
                                    <table class="table table-bordered table-hover nomarginbottom dataTable" id="threadTable">
                                        <thead>
                                            <tr>
                                                <th class="text-center" id="systemHeader" name="systemHeader">Number of Current and Pending Executions / Size of Execution Queue</th>
                                                <th class="text-center" id="systemHeader" name="systemHeader">Number of Workers in Execution</th>
                                            </tr>
                                        </thead>
                                        <tbody id="threadTableBody">
                                        </tbody>
                                    </table>
                                </div>
                            </div>-->
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-xs-6">

                        <div class="col-lg-12" id="FiltersPanel">
                            <div class="panel panel-default">
                                <div class="panel-heading card" data-toggle="collapse"  data-target="#cerberusInformation">
                                    <span class="fa fa-tag fa-fw"></span>
                                    <label id="filters" name="filtersField">Cerberus Information</label>
                                    <span class="toggle glyphicon glyphicon-chevron-right pull-right"></span>
                                </div>
                                <div class="panel-body collapse in" id="cerberusInformation">
                                    <div class="row">
                                        <div class="form-group col-xs-12">
                                            <table class="table table-bordered table-hover nomarginbottom dataTable" id="cerberusTable">
                                                <thead>
                                                    <tr>
                                                        <th class="text-center" id="systemHeader" name="systemHeader">Project</th>
                                                        <th class="text-center" id="countryHeader" name="countryHeader">Version</th>
                                                        <th class="text-center" id="countryHeader" name="countryHeader">Database Target Version</th>
                                                        <th class="text-center" id="countryHeader" name="countryHeader">Database Current Version</th>
                                                        <th class="text-center" id="environmentHeader" name="environmentHeader">Environment</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="cerberusTableBody">
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group col-xs-6">

                        <div class="col-lg-12" id="FiltersPanel">
                            <div class="panel panel-default">
                                <div class="panel-heading card" data-toggle="collapse"  data-target="#jvmInformation">
                                    <span class="fa fa-tag fa-fw"></span>
                                    <label id="filters" name="filtersField">JVM Information</label>
                                    <span class="toggle glyphicon glyphicon-chevron-right pull-right"></span>
                                </div>
                                <div class="panel-body collapse in" id="jvmInformation">
                                    <div class="row">
                                        <div class="form-group col-xs-12">
                                            <table class="table table-bordered table-hover nomarginbottom dataTable" id="jvmTable">
                                                <thead>
                                                    <tr>
                                                        <th class="text-center" id="systemHeader" name="systemHeader">JAVA Version</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="jvmTableBody">
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-12" id="FiltersPanel">
                    <div class="panel panel-default">
                        <div class="panel-heading card" data-toggle="collapse"  data-target="#dtbInformation">
                            <span class="fa fa-tag fa-fw"></span>
                            <label id="filters" name="filtersField">Database Information</label>
                            <span class="toggle glyphicon glyphicon-chevron-right pull-right"></span>
                        </div>
                        <div class="panel-body collapse in" id="dtbInformation">
                            <div class="row">
                                <div class="form-group col-xs-12">
                                    <table class="table table-bordered table-hover nomarginbottom dataTable" id="databaseTable">
                                        <thead>
                                            <tr>
                                                <th class="text-center" id="systemHeader" name="systemHeader">Database</th>
                                                <th class="text-center" id="systemHeader" name="systemHeader">Database Version</th>
                                                <th class="text-center" id="systemHeader" name="systemHeader">Major Version</th>
                                                <th class="text-center" id="systemHeader" name="systemHeader">Minor Version</th>
                                            </tr>
                                        </thead>
                                        <tbody id="databaseTableBody">
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-xs-12">
                                    <table class="table table-bordered table-hover nomarginbottom dataTable" id="driverTable">
                                        <thead>
                                            <tr>
                                                <th class="text-center" id="systemHeader" name="systemHeader">Driver Name</th>
                                                <th class="text-center" id="systemHeader" name="systemHeader">Driver Version</th>
                                                <th class="text-center" id="systemHeader" name="systemHeader">Major Version</th>
                                                <th class="text-center" id="systemHeader" name="systemHeader">Minor Version</th>
                                            </tr>
                                        </thead>
                                        <tbody id="driverTableBody">
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-xs-12">
                                    <table class="table table-bordered table-hover nomarginbottom dataTable" id="jdbcTable">
                                        <thead>
                                            <tr>
                                                <th class="text-center" id="systemHeader" name="systemHeader">JDBC Minor Version</th>
                                                <th class="text-center" id="systemHeader" name="systemHeader">JDBC Major Version</th>
                                            </tr>
                                        </thead>
                                        <tbody id="jdbcTableBody">
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

            <footer class="footer">
                <div class="container-fluid" id="footer"></div>
            </footer>
        </div>
    </body>
</html>
