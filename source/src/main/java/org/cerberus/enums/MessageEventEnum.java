/* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
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
package org.cerberus.enums;

/**
 * Message is used to feedback the result of any Cerberus event. Events could by
 * Property, Action, Control or even Step. For every event, we have: - a number
 * - a 2 digit code that report the status of the event. - a clear message that
 * will be reported to the user. describing what was done or the error that
 * occured. - a boolean that define whether the complete test execution should
 * stop or not. - a boolean that define whether a screenshot will be done in
 * case of problem (only if screenshot option is set to 1). - the corresponding
 * Execution message that will be updated at the execution level.
 * <p/>
 * Code standard is : All SUCCESS are x00 (same code for all). All FAILED are
 * from x50 to x99 (different code for each). Pending is x99.
 */
public enum MessageEventEnum {

    PROPERTY_SUCCESS(100, "OK", "Property calculated successfully.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_SQL(100, "OK", "SQL executed against database '%DATABASE%' and JDBCPOOL '%JDBCPOOLNAME%'. SQL : '%SQL%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_SQL_RANDOM(100, "OK", "Random result fetch from SQL executed against database '%DATABASE%' and JDBCPOOL '%JDBCPOOLNAME%'. SQL : '%SQL%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_HTML(100, "OK", "HTML property calculated with '%VALUE%' from element '%ELEMENT%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_HTMLVISIBLE(100, "OK", "HTML Visible property calculated with '%VALUE%' from element '%ELEMENT%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETFROMXML(100, "OK", "Property from Xml '%VALUE1%' calculated and returned '%VALUE2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETDIFFERENCESFROMXML(100, "OK", "Differences successfully computed between '%VALUE1%' and '%VALUE2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_RANDOM(100, "OK", "Random property %FORCED%calculated with '%VALUE%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_TEXT(100, "OK", "Text property calculated with '%VALUE%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_RANDOM_NEW(100, "OK", "Random New property calculated with '%VALUE%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETATTRIBUTEFROMHTML(100, "OK", "Attribute '%ATTRIBUTE%' has returned '%VALUE%' for element '%ELEMENT%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETFROMCOOKIE(100, "OK", "Parameter '%PARAM%' from cookie '%COOKIE%' has been found and returned '%VALUE%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETFROMJSON(100, "OK", "Value '%PARAM%' from Json '%URL%' has been found and returned '%VALUE%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETFROMGROOVY(100, "OK", "Groovy script property calculated with '%VALUE%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_TESTDATA(100, "OK", "TestData %PROPERTY% correctly returned '%VALUE%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_SOAP(100, "OK", "SOAP Request executed.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETFROMDATALIB(100, "OK", "Data Library entry %ENTRY% (%ENTRYID%) retrieved with success. First result fetch from SQL executed against database '%DATABASE%' and JDBCPOOL '%JDBCPOOLNAME%'. SQL : '%SQL%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETFROMDATALIB_GLOBAL(100, "OK", "Data Library entry %ENTRY% (%ENTRYID%) retrieved with success. %DATAMESSAGE% and %FILTERNATUREMESSAGE%. Result : %RESULT%", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETFROMDATALIB_SUBDATA(100, "OK", "Subdata retreived with success.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETFROMDATALIB_DATA(100, "OK", "Data Library entry %ENTRY% (%ENTRYID%) retrieved with success.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETFROMDATALIB_INTERNAL(100, "OK", "%NBROW% row(s) retrieved  from INTERNAL Test Data lib with success", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETFROMDATALIB_SQL(100, "OK", "%NBROW% row(s) retrieved  from SQL executed against database '%DATABASE%' JDBCPOOL '%JDBCPOOLNAME%' with SQL : '%SQL%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETFROMDATALIB_CSV(100, "OK", "%NBROW% row(s) retrieved  from CSV retreived from %CSVURL%", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETFROMDATALIB_SOAP(100, "OK", "%NBROW% row(s) retrieved  from SOAP request on URL '%URL%' Operation '%OPER%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETFROMDATALIB_NATURE(100, "OK", "", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETFROMDATALIB_NATURESTATIC(100, "OK", "first %ROW% selected with success", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETFROMDATALIB_NATURERANDOM(100, "OK", "row %POS% out of %TOTALPOS% selected randomly", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETFROMDATALIB_NATURERANDOMNEW(100, "OK", "out of %TOTNB% entries, %REMNB% removed (Already used for the same property name, testcase in the same country environment and build), row %POS% out of %TOTALPOS% selected randomly", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_GETFROMDATALIB_NATURENOTINUSE(100, "OK", "out of %TOTNB% entries, %REMNB% removed (Recent pending (PE) executions for the same property name in the same country, environment), row %POS% out of %TOTALPOS% selected randomly", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_SUCCESS_CSV(100, "OK", "CSV found from '%URL%' and successfully parsed", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_FAILED(150, "NA", "PROPERTY_ERROR Generic error on getting the property.", true, false, false, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_NO_PROPERTY_DEFINITION(151, "NA", "Warning, Property not defined for %PROP% and country %COUNTRY%.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    PROPERTY_FAILED_SQL(152, "FA", "An error occur when connecting to ?! Error detail: ?", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_SQL_COLUMNNOTMATCHING(192, "FA", "Some column(s) do not match with the SQL Query! Columns mapping in error : %BADCOLUMNS%. Database: %DATABASE%. SQL: %SQL%", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_SQL_NOCOLUMNMATCH(192, "FA", "None of the column match with the SQL Query! Columns mapping in error : %BADCOLUMNS%. Database: %DATABASE%. SQL: %SQL%", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_SQL_NODATA(904, "NA", "The SQL performed against database %DATABASE% and JDBC Ressource %JDBCPOOLNAME% returned no data to test. SQL : %SQL%", true, false, false, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_SQL_NATURENOTINUSE_NOTIMPLEMENTED(154, "FA", "Nature NOTINUSE not supported.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_SQL_NATURERANDOMNEW_NOTIMPLEMENTED(154, "FA", "Nature NOTINUSE not supported.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_SQL_ERROR(154, "FA", "The SQL '%SQL%' has an error : '%EX%'.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_SQL_TIMEOUT(154, "FA", "The SQL '%SQL%' timeout out after %TIMEOUT% second(s). %EX%", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_SQL_CANNOTACCESSJDBC(155, "FA", "An error occur when connecting to JDBC datasource '%JDBC%'. Please verify with your administrator that the JDBC is configured inside the application server. Error detail: %EX%", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_SQL_JDBCPOOLNOTCONFIGURED(156, "FA", "The JDBC connection pool name does not exist for the corresponding System : %SYSTEM% Country : %COUNTRY%, environment : %ENV% and database : %DATABASE% . Please define it inside the database.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_SQL_EMPTYJDBCPOOL(157, "FA", "The JDBC connection pool name is empty for the corresponding System : %SYSTEM% Country : %COUNTRY%, environment : %ENV% and database : %DATABASE% . Please define it inside the database.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_SQL_SQLLIB_NOTEXIT(158, "FA", "The SQL Lib %SQLLIB% does not exist. Please define it inside the database or pick another one.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_SQL_DATABASENOTCONFIGURED(192, "FA", "An error occurred while calculating the SQL lib! Database %DATABASE% is not configured on the system %SYSTEM%, country %COUNTRY%, environment %ENV%.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_SQL_GENERIC(159, "FA", "An unknown error occur when connecting to %JDBC%.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_HTML_ELEMENTDONOTEXIST(180, "FA", "Failed to calculate property because could not find element '%ELEMENT%'!", true, true, true, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_HTML_ATTRIBUTEDONOTEXIST(180, "FA", "Failed to calculate property because could not find attribute '%ATTRIBUTE%' for element '%ELEMENT%'!", true, true, true, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_HTMLVISIBLE_ELEMENTDONOTEXIST(181, "FA", "Failed to calculate visible html property because could not find element '%ELEMENT%'!", true, true, true, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_UNKNOWNPROPERTY(182, "FA", "Property '%PROP%' does not exist or is not supported by the engine.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_TEXTRANDOMLENGHT0(183, "FA", "RANDOM or RANDOMNEW text Property function cannot have a lenght at 0. Pick STATIC nature (if you want an empty string) or increase the lenght.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_TESTDATA_PROPERTYDONOTEXIST(184, "NA", "TestData %PROPERTY% do not exist!", true, true, false, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_JS_EXCEPTION(185, "NA", "Failed to Execute script!\nException reached : %EXCEPTION%.", true, true, true, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_GETFROMXML(186, "FA", "Failed to get Data from '%VALUE1%' because could not find xpath : '%VALUE2%'!", true, true, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMCOOKIE_COOKIENOTFOUND(187, "FA", "Failed to get Parameter '%PARAM%' because could not find cookie '%COOKIE%'!", true, true, true, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMCOOKIE_PARAMETERNOTFOUND(187, "FA", "Cannot find Parameter '%PARAM%' form Cookie '%COOKIE%'. Parameter do not exist or is not supported!", true, true, true, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETDIFFERENCESFROMXML(188, "FA", "Failed to compute differences from '%VALUE1%' and '%VALUE2%'", true, true, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMJSON_PARAMETERNOTFOUND(190, "FA", "Value %PARAM% not found in Json file from %URL%", true, true, true, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_CALCULATE_OBJECTPROPERTYNULL(191, "FA", "Both object and property are null. Please specify one of them.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_NOT_FOUND_ERROR(153, "FA", "The test data library entry %ITEM% is not available for the selected system %SYSTEM%, environment %ENVIRONMENT% and country %COUNTRY%.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_NOT_EXIST_ERROR(153, "FA", "The test data library entry %ITEM% do not exist.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_GLOBAL_NODATALEFT(904, "NA", "Data Library entry %ENTRY% (%ENTRYID%) failed . %DATAMESSAGE% but %FILTERNATUREMESSAGE%.", true, false, false, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_GETFROMDATALIB_GLOBAL_NODATA(904, "NA", "Data Library entry %ENTRY% (%ENTRYID%) failed . %DATAMESSAGE%.", true, false, false, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_GETFROMDATALIB_GLOBAL_NOTENOUGHTDATA(904, "NA", "Data Library entry %ENTRY% (%ENTRYID%) failed . %DATAMESSAGE% but this is not enought in order to provide the requested %NBREQUEST% row(s).", true, false, false, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_GETFROMDATALIB_GLOBAL_DATAISSUE(153, "FA", "Data Library entry %ENTRY% (%ENTRYID%) failed . %DATAMESSAGE%.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_GLOBAL_SUBDATAISSUE(153, "FA", "Data Library entry %ENTRY% (%ENTRYID%) failed . %SUBDATAMESSAGE%.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_GLOBAL_GENERIC(153, "FA", "Data Library entry %ENTRY% (%ENTRYID%) failed.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_INTERNAL(153, "FA", "Error occured when retrieving the data ibrary for %SYSTEM% %ENV% %COUNTRY%.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_INTERNALNODATA(904, "NA", "No data for the data librry for %SYSTEM% %ENV% %COUNTRY%.", true, false, false, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_GETFROMDATALIB_SQLDATABASEEMPTY(153, "FA", "Database is not defined.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_SQLDATABASENOTCONFIGURED(153, "FA", "Database %DATABASE% is not configured on the system %SYSTEM%, country %COUNTRY%, environment %ENV%", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_SQLDATABASEJDBCRESSOURCEMPTY(153, "FA", "Database %DATABASE% is configured on the system %SYSTEM%, country %COUNTRY%, environment %ENV% but JDBC Ressource is empty", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_SQLDATABASENODATA(904, "NA", "SQL return no result for Database: %DATABASE% JDBCPOOL %JDBCPOOLNAME%, SQL: %SQL%", true, false, false, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_GETFROMDATALIB_CSVDATABASENODATA(904, "NA", "CSV return no result Database: %DATABASE% URL : %CSVURL%", true, false, false, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_GETFROMDATALIB_SUBDATA(153, "FA", "Generic Error.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_NOSUBDATASQL(153, "FA", "No subdata with non empty column name defined.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_NOSUBDATACSV(153, "FA", "No subdata with non empty CSV column position defined.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_NOSUBDATASOAP(153, "FA", "No subdata with non empty column name defined.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_SUBDATASQLNOKEY(153, "FA", "Key subdata has no column mapped.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_SUBDATACSVNOKEY(153, "FA", "Key subdata has no CSV column position mapped.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_SUBDATASOAPNOKEY(153, "FA", "Key subdata has no parsingAnswer mapped.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_SUBDATASQL(153, "FA", "Error when retreiving subdata with existing SQL column mapped.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_SUBDATACSV(153, "FA", "Error when retreiving subdata with existing CSV column position mapped.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_SUBDATASOAP(153, "FA", "Error when retreiving subdata with existing SOAP parsing answers mapped.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_GENERIC_NODATA(904, "NA", "", true, false, false, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_GETFROMDATALIB_GENERIC_NATURENOMORERECORD(904, "NA", "", true, false, false, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_GETFROMDATALIB_RANDOMNEW_NOMORERECORD(904, "NA", "there are no more data available. All %TOTNB% entry(ies), has been removed (Already used for the same property name, testcase in the same country environment and build).", true, false, false, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_GETFROMDATALIB_RANDOMNEW_NOTENOUGTHRECORDS(904, "NA", "%REMNB% entry(ies) has been removed out of the %TOTNB%, (Already used for the same property name, testcase in the same country environment and build). Not enought data left to feed the %NBREQUEST% row requested.", true, false, false, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_GETFROMDATALIB_NOTINUSE_NOMORERECORD(904, "NA", "there are no more data available. All %TOTNB% entries, has been removed (Recent pending (PE) executions for the same property name in the same country, environment).", true, false, false, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_GETFROMDATALIB_NOTINUSE_NOTENOUGTHRECORDS(904, "NA", "%REMNB% entry(ies), has been removed out of the %TOTNB%, (Recent pending (PE) executions for the same property name in the same country, environment). Not enought data left to feed the %NBREQUEST% row requested.", true, false, false, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_GETFROMDATALIB_SOAP_NOSUBDATA(197, "FA", "No Subdata defined for Data Library entry %ENTRY% (%ENTRYID%)!", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_SOAP_XMLEXCEPTION(197, "FA", "The evaluation of the xpath expression '%XPATH%' specified in the sub-data entry '%SUBDATA%' has failed! '%REASON%'", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_SOAP_SOAPCALLFAILED(197, "FA", "%SOAPERROR% - Library entry '%ENTRY%' (%ENTRYID%)", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_SOAP_URLKOANDNODATABASE(197, "FA", "The service URL '%SERVICEURL%' is not a valid URL and the corresponding DatabaseURL is not defined to prefix it", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_CSV_URLKOANDNODATABASE(197, "FA", "The CSV service URL '%SERVICEURL%' is not a valid URL and the corresponding CSV Database URL is not defined to prefix it", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_SOAP_URLKOANDDATABASESOAPURLNOTEXIST(197, "FA", "The service URL '%SERVICEURL%' is not a valid URL and the corresponding DatabaseURL %DATABASE% is not configured on the system %SYSTEM%, country %COUNTRY%, Environment %ENV%", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_CSV_URLKOANDDATABASECSVURLNOTEXIST(197, "FA", "The CSV service URL '%SERVICEURL%' is not a valid URL and the corresponding Csv DatabaseURL %DATABASE% is not configured on the system %SYSTEM%, country %COUNTRY%, Environment %ENV%", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_SOAP_URLKO(197, "FA", "The service URL '%SERVICEURL%' (Calculated from concatenation of '%SOAPURL%' with '%SERVICEPATH%') is not a valid URL", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_CSV_URLKO(197, "FA", "The CSV service URL '%SERVICEURL%' (Calculated from concatenation of '%SOAPURL%' with '%SERVICEPATH%') is not a valid URL", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_SOAP_URLKOANDDATABASESOAPURLEMPTY(197, "FA", "The service URL '%SERVICEURL%' is not a valid URL and the corresponding DatabaseURL %DATABASE% on the system %SYSTEM%, country %COUNTRY%, Environment %ENV% has no SoapURL defined", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_CSV_URLKOANDDATABASECSVURLEMPTY(197, "FA", "The CSV service URL '%SERVICEURL%' is not a valid URL and the corresponding Csv DatabaseURL %DATABASE% on the system %SYSTEM%, country %COUNTRY%, Environment %ENV% has no CsvURL defined", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_SOAP_XML_NOTFOUND(197, "NA", "No elements found! The evaluation of the xpath expression '%XPATH%' specified in the sub-data entry '%SUBDATA%' didn't match any XML elements! inside XML %XMLCONTENT%", true, false, false, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_GETFROMDATALIB_SOAP_CHECK_XPATH(197, "FA", "The Element specified on the xpath '%XPATH%' is correct but no data was retrieved! Please verify the xpath expression '%XPATH%' specified in the sub-data entry '%SUBDATA%' ! If not already done, you can try to add /text() and the end of the xpath.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_CSV_GENERIC(194, "FA", "An error occurred while trying to get data from CSV from %URL%", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_CSV_FILENOTDEFINED(194, "FA", "CSV File location not defined in Data Library.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_CSV_FILENOTFOUND(194, "FA", "An error occurred while trying to get CSV file calling %URL%. %EX%", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMDATALIB_CSV_NOCOLUMEDMAPPED(194, "FA", "No columns could be mapped. Probably because all column position are higher than number of columns of the file. Maybe check that separator %SEPARATOR% is correct.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_GETFROMGROOVY_NULL(904, "NA", "Property is 'null' or empty. Cannot calculate Groovy script from a 'null' or empty property.", true, false, false, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_GETFROMGROOVY_EXCEPTION(904, "FA", "Groovy evaluation error: %REASON%", true, true, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_FAILED_SOAPFROMLIB_NODATA(198, "NA", "SOAP Request executed but returned no data.", true, false, false, MessageGeneralEnum.EXECUTION_NA),
    PROPERTY_FAILED_FEATURENOTIMPLEMENTED(197, "FA", "Feature '%FEATURE%' is not yet implemented!", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    PROPERTY_PENDING(199, "PE", "Calculating property...", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    // *********** EXECUTION ACTIONS ***********
    ACTION_SUCCESS(200, "OK", "", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_CLICK(200, "OK", "Element '%ELEMENT%' clicked.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_CLICKANDWAIT(200, "OK", "Element '%ELEMENT%' clicked and waited %TIME% ms.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_CLICKANDNOWAIT(200, "OK", "Element '%ELEMENT%' clicked and waited for page to load", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_TYPE(200, "OK", "Element '%ELEMENT%' feeded with '%DATA%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_DOUBLECLICK(200, "OK", "Element '%ELEMENT%' double clicked.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_RIGHTCLICK(200, "OK", "Right click has been done on Element '%ELEMENT%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_URLLOGIN(200, "OK", "Opened '%URL%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_MOUSEOVER(200, "OK", "Mouse moved over '%ELEMENT%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_MOUSEOVERANDWAIT(200, "OK", "Mouse moved over '%ELEMENT%' and waited %TIME% ms.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_WAIT_TIME(200, "OK", "Waited %TIME% ms.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_WAIT_ELEMENT(200, "OK", "Waited for %ELEMENT%.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_KEYPRESS(200, "OK", "Element '%ELEMENT%' keypress with '%DATA%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_KEYPRESS_NO_ELEMENT(200, "OK", "Key '%KEY%' pressed with success.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_OPENURL(200, "OK", "Opened URL '%URL%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_SELECT(200, "OK", "Element '%ELEMENT%' selected with '%DATA%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_PROPERTYCALCULATED(200, "OK", "Property '%PROP%' has been calculated with value '%VALUE%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_FOCUSTOIFRAME(200, "OK", "Focus of Selenium was changed to Iframe '%IFRAME%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_FOCUSDEFAULTIFRAME(200, "OK", "Focus of Selenium was changed to default Iframe", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_TAKESCREENSHOT(200, "OK", "Screenshot taken.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_GETPAGESOURCE(200, "OK", "Page Source has been recorded.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_MOUSEDOWN(200, "OK", "Mouse Left Click pressed on Element '%ELEMENT%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_MOUSEUP(200, "OK", "Mouse Left Click Released on Element '%ELEMENT%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_SWITCHTOWINDOW(200, "OK", "Focus of Selenium was changed to Window '%WINDOW%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_CLOSE_ALERT(200, "OK", "Alert popup is closed !", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_CALLSOAP(200, "OK", "Call to SOAP Operation '%SOAPMETHOD%' on Service Path %SERVICEPATH% executed successfully and stored to memory!", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_MOUSEDOWNMOUSEUP(200, "OK", "Mouse Left Click pressed and released on Element '%ELEMENT%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_REMOVEDIFFERENCE(200, "OK", "Difference '%DIFFERENCE%' removed from '%DIFFERENCES%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_HIDEKEYBOARD(200, "OK", "Keyboard hidden.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_HIDEKEYBOARD_ALREADYHIDDEN(200, "OK", "Keyboard already hidden.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_SWIPE(200, "OK", "Screen swiped '%DIRECTION%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_EXECUTESQLUPDATE(200, "OK", "SQL '%SQL%' Executed against %JDBC% - %NBROWS% row(s) affected.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_SUCCESS_EXECUTESQLSTOREDPROCEDURE(200, "OK", "SQL '%SQL%' Executed against %JDBC% - %NBROWS% row(s) affected.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_FAILED(250, "FA", "Unknown Action Error.", true, true, false, MessageGeneralEnum.EXECUTION_FA),
    ACTION_FAILED_CLICK(251, "FA", "Failed to click on '%ELEMENT%'.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_SELENIUM_CONNECTIVITY(252, "CA", "The test case is cancelled due to lost connection to Selenium Server! Detailed error : %ERROR%", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_NO_SUCH_ELEMENT(253, "FA", "Identifier '%IDENTIFIER%=' isn't recognized! Use: id=, name=, class=, css= , xpath= , link= , picture= or data-cerberus=.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_SQL_NOT_ALLOWED_IDENTIFIER(253, "FA", "Identifier '%IDENTIFIER%=' isn't recognized for this action! Use: script=, procedure=.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_NO_ELEMENT_TO_PERFORM_ACTION(254, "FA", "Object and Property are ‘null’. At least one is mandatory in order to perform the action %ACTION%.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_CLICKANDWAIT_GENERIC(255, "FA", "Object is 'null'. This is mandatory in order to perform the action click and wait.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_CLICKANDWAIT(256, "FA", "Element '%ELEMENT%' clicked but failed to wait '%TIME%' ms.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_TYPE(257, "FA", "Object and/or Property are ‘null’. Both are mandatory in order to perform the action type.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_DOUBLECLICK(258, "FA", "Object and Property are ‘null’. At least one is mandatory in order to perform the action double click.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_URLLOGIN(259, "FA", "Failed to open '%URL%'.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_URLLOGIN_TIMEOUT(259, "FA", "Failed to open '%URL%'. Timeout of %TIMEOUT% milliseconds exceeded.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_MOUSEOVER(260, "FA", "Object and property are ‘null’. At least one is mandatory in order to perform the action mouse over.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_MOUSEOVERANDWAIT_GENERIC(261, "FA", "Object is 'null'. This is mandatory in order to perform the action mouse over and wait.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_MOUSEOVERANDWAIT(262, "FA", "Mouse over '%ELEMENT%' but failed to wait '%TIME%' ms.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_WAIT(263, "FA", "Failed to wait '%TIME%' ms.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_WAIT_INVALID_FORMAT(263, "FA", "Format of the timeout defined for this action is not numeric", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_KEYPRESS(264, "FA", "Object and/or Property are ‘null’. Both are mandatory in order to perform the action type.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_KEYPRESS_ENV_ERROR(264, "FA", "Environment configurations don't allow you to perform the KeyPress operation.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_KEYPRESS_NOT_AVAILABLE(264, "FA", "KeyPress failed! %KEY% is not available.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_KEYPRESS_OTHER(264, "FA", "KeyPress failed for key '%KEY%' due to '%REASON%'.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_OPENURL(265, "FA", "Failed to open '%URL%'.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_OPENURL_TIMEOUT(265, "FA", "Failed to open '%URL%'. Timeout of %TIMEOUT% milliseconds exceeded.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_SELECT(266, "FA", "Object and/or Property are ‘null’. Both are mandatory in order to perform the action type.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_CLICK_NO_SUCH_ELEMENT(267, "FA", "Failed to click because could not find element '%ELEMENT%'!", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_DOUBLECLICK_NO_SUCH_ELEMENT(268, "FA", "Failed to double click because could not find element '%ELEMENT%'!", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_TYPE_NO_SUCH_ELEMENT(269, "FA", "Failed to type because could not find element '%ELEMENT%'!", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_MOUSEOVER_NO_SUCH_ELEMENT(270, "FA", "Failed to move mouse over because could not find element '%ELEMENT%'!", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_WAIT_NO_SUCH_ELEMENT(271, "FA", "Failed to wait because could not find element '%ELEMENT%'!", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_KEYPRESS_NO_SUCH_ELEMENT(272, "FA", "Failed to keyPress key because could not find element '%ELEMENT%'!", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_SELECT_NO_SUCH_ELEMENT(273, "FA", "Failed to select because could not find element '%ELEMENT%'!", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_SELECT_NO_IDENTIFIER(274, "FA", "Identifier '%IDENTIFIER%=' isn't recognized! Use: value=, label= or index=.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_CLICKANDWAIT_NO_NUMERIC(275, "FA", "Failed to wait because '%TIME%' in not numeric!", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_MOUSEOVERANDWAIT_NO_NUMERIC(276, "FA", "Failed to wait because '%TIME%' in not numeric!", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_UNKNOWNACTION(277, "FA", "Action %ACTION% does not exist or is not supported yet.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    ACTION_FAILED_PROPERTYFAILED(278, "FA", "Action failed due to corresponding property. Please check the property message for more details.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    ACTION_FAILED_SELECT_NO_SUCH_VALUE(279, "FA", "Found the element '%ELEMENT%', but failed to select because could not find '%DATA%'!", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_FOCUS_NO_SUCH_ELEMENT(280, "FA", "Failed to focus to iframe because could not find element '%IFRAME%'!", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_SELECT_REGEX_INVALIDPATERN(281, "FA", "Pattern '%PATERN%' is not valid. Detailed error : %ERROR%", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_MOUSEUP_NO_SUCH_ELEMENT(282, "FA", "Failed to release click because could not find element '%ELEMENT%'!", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_MOUSEDOWN_NO_SUCH_ELEMENT(283, "FA", "Failed to left click because could not find element '%ELEMENT%'!", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_SWITCHTOWINDOW_NO_SUCH_ELEMENT(280, "FA", "Failed to switch to window because could not find element '%WINDOW%'!", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_CLOSE_ALERT(280, "FA", "Failed to close to alert popup !", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_CALLSERVICE(286, "FA", "Failed to call the Service '%SERVICE%'. Caused by : %DESCRIPTION%.", true, true, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_CALLSERVICEWITHPATH(286, "FA", "Failed to call the Service '%SERVICE%' on Service Path '%SERVICEPATH%'. Caused by : %DESCRIPTION%.", true, true, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_CALLSOAP(286, "FA", "Failed to call the SOAP Operation '%SOAPMETHOD%' on Service Path %SERVICEPATH% ! Caused by : %DESCRIPTION%.", true, true, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_CALLSOAP_ENVELOPEMISSING(286, "FA", "Failed to call the SOAP because Envelope is not defined.", true, true, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_CALLSOAP_SERVICEPATHMISSING(286, "FA", "Failed to call the SOAP because Service Path is not defined.", true, true, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_CALLSOAP_METHODMISSING(286, "FA", "Failed to call the SOAP because Method is not defined.", true, true, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_REMOVEDIFFERENCE(287, "FA", "Failed to remove difference '%DIFFERENCE%' from '%DIFFERENCES%'", true, true, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_RIGHTCLICK_NO_SUCH_ELEMENT(288, "FA", "Failed to Right Click on element %ELEMENT% because could not find element '%ELEMENT%'!", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_SIKULI_SERVER_NOT_REACHABLE(289, "FA", "Sikuli Server is not reachable at %URL%. Please verify that the required dependencies are present.", true, true, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_SIKULI_FILE_NOT_FOUND(289, "FA", "File %FILE% not found. Please verify that url defined in object field is correct and accessible from Cerberus Server.", true, true, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_SIKULI_ELEMENT_NOT_FOUND(289, "FA", "Failed to perform the action %ACTION% probably due to Element %ELEMENT% not found.", true, true, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_CALCULATEPROPERTY_MISSINGPROPERTY(289, "FA", "Failed to perform the action %ACTION% due to missing property to calculate.", true, true, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_NOTEXECUTED_NO_PROPERTY_DEFINITION(290, "NA", "Not executed because Property '%PROP%' is not defined for the country '%COUNTRY%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    ACTION_NOTEXECUTED_NOTSUPPORTED_FOR_APPLICATION(291, "FA", "Not executed because Action '%ACTION%' is not supported for application type '%APPLICATIONTYPE%'.", true, true, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_SQL_GENERIC(292, "FA", "An unknown error occur when connecting to %JDBC%.", true, false, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_SQL_ERROR(293, "FA", "The SQL '%SQL%' has an error : '%EX%'.", true, false, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_SQL_TIMEOUT(293, "FA", "The SQL '%SQL%' timeout out after %TIMEOUT% second(s). %EX%", true, false, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_SQL_CANNOTACCESSJDBC(294, "FA", "An error occur when connecting to JDBC datasource '%JDBC%'. Please verify with your administrator that the JDBC is configured inside the application server. Error detail: %EX%", true, false, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_SQL_DATABASENOTCONFIGURED(295, "FA", "The Database does not exist for the corresponding System : %SYSTEM% Country : %COUNTRY%, environment : %ENV% and database : %DATABASE% . Please configure it at environment level.", true, false, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_SQL_DATABASECONFIGUREDBUTJDBCPOOLEMPTY(295, "FA", "The JDBC Ressource is empty for the corresponding System : %SYSTEM% Country : %COUNTRY%, environment : %ENV% and database : %DATABASE% . Please configure it in environment screen.", true, false, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_SQL_AGAINST_CERBERUS(295, "FA", "You cannot perform executeSqlUpdate action on a JDBC ressource using Cerberus connection pool. Please create new connection with dedicated rights.", true, false, false, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_HIDEKEYBOARD(296, "FA", "Failed to hide keyboard. Check server logs.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_SWIPE(297, "FA", "Failed to swipe '%DIRECTION%' screen due to '%REASON%'.", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_FAILED_TIMEOUT(298, "FA", "Timeout exceeded when performing the action : %TIMEOUT% milliseconds", true, true, true, MessageGeneralEnum.EXECUTION_FA_ACTION),
    ACTION_PENDING(299, "PE", "Doing Action...", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    // *********** EXECUTION CONTROLS ***********
    CONTROL_SUCCESS(300, "OK", "", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_EQUAL(300, "OK", "'%STRING1%' is equal to '%STRING2%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_CONTAINS(300, "OK", "'%STRING1%' contains '%STRING2%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_DIFFERENT(300, "OK", "'%STRING1%' is different from '%STRING2%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_GREATER(300, "OK", "'%STRING1%' is greater than '%STRING2%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_MINOR(300, "OK", "'%STRING1%' is minor than '%STRING2%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_PRESENT(300, "OK", "Element '%STRING1%' is present on the page.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_NOTPRESENT(300, "OK", "Element '%STRING1%' is not present on the page.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_VISIBLE(300, "OK", "Element '%STRING1%' is visible on the page.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_NOTVISIBLE(300, "OK", "Element '%STRING1%' is present and not visible on the page.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_TEXTINELEMENT(300, "OK", "Element '%STRING1%' with value '%STRING2%' is equal to '%STRING3%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_TEXTNOTINELEMENT(300, "OK", "Element '%STRING1%' with value '%STRING2%' is different than '%STRING3%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_TEXTINPAGE(300, "OK", "Pattern '%STRING1%' found in page.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_TEXTNOTINPAGE(300, "OK", "Pattern '%STRING1%' not found in page.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_URL(300, "OK", "Current page '%STRING1%' is equal to '%STRING2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_TITLE(300, "OK", "Current title '%STRING1%' is equal to '%STRING2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_REGEXINELEMENT(300, "OK", "Element '%STRING1%' with value '%STRING2%' match '%STRING3%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_TEXTINALERT(300, "OK", "Alert text with value '%STRING1%' match '%STRING2%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_ELEMENTINELEMENT(300, "OK", "Element '%STRING1%' in child of element '%STRING2%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_SIMILARTREE(300, "OK", "Tree '%STRING1%' is equal to tree '%STRING2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_CLICKABLE(300, "OK", "Element '%ELEMENT%' is clickable", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_NOTCLICKABLE(300, "OK", "Element '%ELEMENT%' is not clickable", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_ELEMENTEQUALS(300, "OK", "Element in path '%XPATH%' is equal to '%EXPECTED_ELEMENT%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_ELEMENTDIFFERENT(300, "OK", "Element in path '%XPATH%' is different from '%DIFFERENT_ELEMENT%'.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_TAKESCREENSHOT(300, "OK", "Screenshot taken.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_SUCCESS_GETPAGESOURCE(300, "OK", "Page Source taken.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONTROL_FAILED(350, "KO", "Control Failed", false, true, false, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_UNKNOWNCONTROL(351, "KO", "Control function '%CONTROL%' does not exist or is not supported.", true, false, false, MessageGeneralEnum.EXECUTION_FA),
    CONTROL_FAILED_FATAL(352, "KO", "Fatal Control Failed", true, true, false, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_NO_SUCH_ELEMENT(353, "FA", "Element '%ELEMENT%' doesn't exist. Selenium Exception : %SELEX%.", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_SELENIUM_CONNECTIVITY(354, "CA", "The test case is cancelled due to lost connection to Selenium Server! Detailed error : %ERROR%", true, true, true, MessageGeneralEnum.EXECUTION_FA_CONNECTIVITY),
    CONTROL_FAILED_PROPERTY_NOTNUMERIC(355, "KO", "At least one of the Properties is not numeric, can not compare properties!", true, false, false, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_URL_NOT_MATCH_APPLICATION(356, "FA", "Cannot find application host '%HOST%' inside current URL '%CURRENTURL%'. Maybe this is due to a redirection done on the web site. That can be corrected by modifying the application URL.", true, true, false, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_EQUAL(357, "KO", "'%STRING1%' is not equal to '%STRING2%'.", true, true, false, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_DIFFERENT(358, "KO", "'%STRING1%' is not different from '%STRING2%'.", true, true, false, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_GREATER(359, "KO", "'%STRING1%' is not greater than '%STRING2%'.", true, true, false, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_MINOR(360, "KO", "'%STRING1%' is not minor than '%STRING2%'.", true, true, false, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_PRESENT(361, "KO", "Element '%STRING1%' is not present on the page.", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_PRESENT_NULL(362, "KO", "Object is 'null'. This is mandatory in order to perform the control verify element present", true, false, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_NOTPRESENT(363, "KO", "Element '%STRING1%' is present on the page.", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_NOTPRESENT_NULL(364, "KO", "Object is 'null'. This is mandatory in order to perform the control verify element not present", true, false, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_VISIBLE(365, "KO", "Element '%STRING1%' not visible on the page.", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_NOTVISIBLE(365, "KO", "Element '%STRING1%' is visible on the page.", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_VISIBLE_NULL(366, "KO", "Object is 'null'. This is mandatory in order to perform the control verify element visible", true, false, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_NOTVISIBLE_NULL(366, "KO", "Object is 'null'. This is mandatory in order to perform the control verify element not visible", true, false, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_TEXTINELEMENT(367, "KO", "Element '%STRING1%' with value '%STRING2%' is not equal to '%STRING3%'.", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_TEXTINELEMENT_NULL(368, "KO", "Found Element '%STRING1%' but can not find text or value.", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_TEXTINELEMENT_NO_SUCH_ELEMENT(369, "KO", "Failed to verifyTextInElement because could not find element '%ELEMENT%'", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_TEXTNOTINELEMENT(367, "KO", "Element '%STRING1%' with value '%STRING2%' is not different than '%STRING3%'.", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_TEXTNOTINELEMENT_NULL(368, "KO", "Found Element '%STRING1%' but can not find text or value.", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_TEXTNOTINELEMENT_NO_SUCH_ELEMENT(369, "KO", "Failed to verifyTextNotInElement because could not find element '%ELEMENT%'", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_TEXTINPAGE(370, "KO", "Pattern '%STRING1%' not found in page!", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_TEXTINPAGE_INVALIDPATERN(371, "KO", "Pattern '%PATERN%' is not valid. Detailed error : %ERROR%", true, false, true, MessageGeneralEnum.EXECUTION_FA),
    CONTROL_FAILED_URL(372, "KO", "Current page '%STRING1%' is not equal to '%STRING2%'", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_TITLE(373, "KO", "Current title '%STRING1%' is not equal to '%STRING2%'", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_TEXTNOTINPAGE(374, "KO", "Pattern '%STRING1%' found in page!", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_TEXTNOTINPAGE_INVALIDPATERN(375, "KO", "Pattern '%PATERN%' is not valid. Detailed error : %ERROR%", true, false, true, MessageGeneralEnum.EXECUTION_FA),
    CONTROL_FAILED_REGEXINELEMENT(376, "KO", "Element '%STRING1%' with value '%STRING2%' does not match '%STRING3%'.", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_REGEXINELEMENT_NULL(377, "KO", "Object is 'null'. This is mandatory in order to perform the control verify regex in element", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_REGEXINELEMENT_NO_SUCH_ELEMENT(378, "KO", "Failed to verifyRegex because could not find element '%ELEMENT%'", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_REGEXINELEMENT_INVALIDPATERN(379, "KO", "Pattern '%PATERN%' is not valid. Detailed error : %ERROR%", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_TEXTINALERT(380, "KO", "Alert text with value '%STRING1%' does not match '%STRING2%'.", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_TEXTINALERT_NULL(381, "KO", "Alert popup does not have text.", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_CONTAINS(382, "KO", "'%STRING1%' does not contain '%STRING2%'.", true, true, false, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_ELEMENTINELEMENT(383, "KO", "Element '%STRING1%' is not child of element '%STRING2%'.", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_SIMILARTREE(385, "KO", "Tree '%STRING1%' is not the same than tree '%STRING2%'.", true, true, false, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_CLICKABLE(386, "KO", "Element '%ELEMENT%' is not clickable.", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_NOTCLICKABLE(387, "KO", "Element '%ELEMENT%' is clickable but it shouldn't.", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_CLICKABLE_NULL(362, "KO", "Object is 'null'. This is mandatory in order to perform the control verify element clickable", true, false, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_NOTCLICKABLE_NULL(362, "KO", "Object is 'null'. This is mandatory in order to perform the control verify element not clickable", true, false, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_ELEMENTEQUALS(388, "KO", "Element in path '%XPATH%' is not equal to '%EXPECTED_ELEMENT%'.", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_FAILED_ELEMENTDIFFERENT(389, "KO", "Element in path '%XPATH%' is not different from '%DIFFERENT_ELEMENT%'.", true, true, true, MessageGeneralEnum.EXECUTION_KO),
    CONTROL_NOTEXECUTED_NOTSUPPORTED_FOR_APPLICATION(384, "KO", "Not executed because Control '%CONTROL%' is not supported for application type '%APPLICATIONTYPE%'.", true, true, false, MessageGeneralEnum.EXECUTION_FA),
    CONTROL_PENDING(399, "PE", "Control beeing performed...", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    // *********** EXECUTION STEP ***********
    STEP_SUCCESS(400, "OK", "", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    STEP_FAILED(450, "KO", "", false, true, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    STEP_PENDING(499, "PE", "Step %STEP%.%STEPINDEX% running...", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    // *********** CONDITION OPERATION ***********
    CONDITIONEVAL_PENDING(1200, "PE", "Evaluating Condition...", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_FAILED_UNKNOWNCONDITION(1290, "FA", "condition '%COND%' do not exist.", false, false, false, MessageGeneralEnum.EXECUTION_FA_CONDITION),
    CONDITIONEVAL_FAILED_DECODE_GENERICERROR(1215, "FA", "Error when decoding '%VALUE%'.", false, false, false, MessageGeneralEnum.EXECUTION_FA_CONDITION),
    CONDITIONEVAL_FAILED_IFPROPERTYEXIST_MISSINGPARAMETER(1220, "FA", "Missing mandatory parameter for '%COND%'.", false, false, false, MessageGeneralEnum.EXECUTION_FA_CONDITION),
    CONDITIONEVAL_FAILED_IFNUMERIC_GENERICCONVERSIONERROR(1230, "FA", "Cannot convert %STRINGVALUE% to numeric.", false, false, false, MessageGeneralEnum.EXECUTION_FA_CONDITION),
    CONDITIONEVAL_FALSE_NEVER(1210, "NA", "", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_FALSE_IFPROPERTYEXIST(1240, "NA", "Property %PROP% do not exist for country %COUNTRY%.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_FALSE_STRINGEQUAL(1210, "NA", "'%STR1%' is not equal to '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_FALSE_STRINGDIFFERENT(1210, "NA", "'%STR1%' is not different from '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_FALSE_STRINGGREATER(1210, "NA", "'%STR1%' is not greater than '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_FALSE_STRINGMINOR(1210, "NA", "'%STR1%' is not minor to '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_FALSE_STRINGCONTAINS(1210, "NA", "'%STR1%' does not contain '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_FALSE_NUMERICEQUAL(1210, "NA", "'%STR1%' is not equal to '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_FALSE_NUMERICDIFFERENT(1210, "NA", "'%STR1%' is not different from '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_FALSE_NUMERICGREATER(1210, "NA", "'%STR1%' is not greater than '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_FALSE_NUMERICGREATEROREQUAL(1210, "NA", "'%STR1%' is not greater or equal than '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_FALSE_NUMERICMINOR(1210, "NA", "'%STR1%' is not minor to '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_FALSE_NUMERICMINOROREQUAL(1210, "NA", "'%STR1%' is not minor or equal to '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_TRUE_ALWAYS(1210, "OK", "", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_TRUE_IFPROPERTYEXIST(1240, "OK", "Property %PROP% exist for country %COUNTRY%.", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_TRUE_STRINGEQUAL(1210, "OK", "'%STR1%' is equal to '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_TRUE_STRINGDIFFERENT(1210, "OK", "'%STR1%' is different from '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_TRUE_STRINGGREATER(1210, "OK", "'%STR1%' is greater than '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_TRUE_STRINGMINOR(1210, "OK", "'%STR1%' is minor to '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_TRUE_STRINGCONTAINS(1210, "OK", "'%STR1%' does contain '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_TRUE_NUMERICEQUAL(1210, "OK", "'%STR1%' is equal to '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_TRUE_NUMERICDIFFERENT(1210, "OK", "'%STR1%' is different from '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_TRUE_NUMERICGREATER(1210, "OK", "'%STR1%' is greater than '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_TRUE_NUMERICGREATEROREQUAL(1210, "OK", "'%STR1%' is greater or equal than '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_TRUE_NUMERICMINOR(1210, "OK", "'%STR1%' is minor to '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITIONEVAL_TRUE_NUMERICMINOROREQUAL(1210, "OK", "'%STR1%' is minor or equal to '%STR2%'", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITION_TESTCASE_NOTEXECUTED(1280, "NA", "Testcase not executed following condition : '%COND%'. %MESSAGE%", false, false, false, MessageGeneralEnum.EXECUTION_FA),
    CONDITION_TESTCASESTEP_NOTEXECUTED(1280, "NA", "Testcase Step not executed with loop '%LOOP%' following condition '%COND%'. %MESSAGE%", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITION_TESTCASEACTION_NOTEXECUTED(1280, "NA", "Action not executed following condition : '%COND%'. %MESSAGE%", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    CONDITION_TESTCASECONTROL_NOTEXECUTED(1280, "NA", "Control not executed following condition : '%COND%'. %MESSAGE%", false, false, false, MessageGeneralEnum.EXECUTION_PE_TESTSTARTED),
    // *********** DATA OPERATION ***********
    DATA_OPERATION_OK(500, MessageCodeEnum.GENERIC_CODE_SUCCESS.getCodeString(), "%ITEM% - %OPERATION% was finished with success!", false, false, false, MessageGeneralEnum.DATA_OPERATION_SUCCESS),
    DATA_OPERATION_WARNING_PARTIAL_RESULT(500, MessageCodeEnum.GENERIC_CODE_WARNING.getCodeString(), "Result may contain partial result. %DESCRIPTION%", false, false, false, MessageGeneralEnum.DATA_OPERATION_WARNING),
    DATA_OPERATION_WARNING(500, MessageCodeEnum.GENERIC_CODE_WARNING.getCodeString(), "%ITEM% - %OPERATION% was finished successfuly with warnings! %REASON%", false, false, false, MessageGeneralEnum.DATA_OPERATION_WARNING),
    DATA_OPERATION_NO_DATA_FOUND(500, MessageCodeEnum.GENERIC_CODE_SUCCESS.getCodeString(), "Could not find any data that match the required criteria.", true, true, false, MessageGeneralEnum.DATA_OPERATION_SUCCESS),
    DATA_OPERATION_ERROR_EXPECTED(550, MessageCodeEnum.GENERIC_CODE_ERROR.getCodeString(), "%ITEM% - operation %OPERATION% failed to complete. %REASON%", false, false, false, MessageGeneralEnum.DATA_OPERATION_ERROR),
    DATA_OPERATION_ERROR_DUPLICATE(551, MessageCodeEnum.GENERIC_CODE_ERROR.getCodeString(), "The %ITEM% that you are trying to %OPERATION% conflicts with an existing one! Please check for duplicates! %REASON%", false, false, false, MessageGeneralEnum.DATA_OPERATION_ERROR),
    DATA_OPERATION_ERROR_UNEXPECTED(552, MessageCodeEnum.GENERIC_CODE_ERROR.getCodeString(), "An unexpected problem occurred. %DESCRIPTION%", false, false, false, MessageGeneralEnum.DATA_OPERATION_ERROR),
    DATA_OPERATION_IMPORT_OK(003, MessageCodeEnum.GENERIC_CODE_SUCCESS.getCodeString(), "%ITEM% was imported with success!", false, false, false, MessageGeneralEnum.DATA_OPERATION_SUCCESS),
    DATA_OPERATION_IMPORT_ERROR(905, MessageCodeEnum.GENERIC_CODE_ERROR.getCodeString(), "%ITEM% - Import failed! %REASON%", false, false, false, MessageGeneralEnum.DATA_OPERATION_ERROR),
    DATA_OPERATION_IMPORT_ERROR_FORMAT(906, MessageCodeEnum.GENERIC_CODE_ERROR.getCodeString(), "%ITEM% Import failed! Format %FORMAT% is invalid!", false, false, false, MessageGeneralEnum.DATA_OPERATION_ERROR),
    DATA_OPERATION_VALIDATIONS_OK(002, MessageCodeEnum.GENERIC_CODE_SUCCESS.getCodeString(), "Data is valid!", false, false, false, MessageGeneralEnum.DATA_OPERATION_SUCCESS),
    DATA_OPERATION_VALIDATIONS_ERROR(905, MessageCodeEnum.GENERIC_CODE_ERROR.getCodeString(), "Data is invalid! Details: %DESCRIPTION%", false, false, false, MessageGeneralEnum.DATA_OPERATION_ERROR),
    // *********** GENERIC ***********
    GENERIC_OK(500, MessageCodeEnum.GENERIC_CODE_SUCCESS.getCodeString(), "Operation finished with success.", false, false, false, MessageGeneralEnum.GENERIC_SUCCESS),
    GENERIC_WARNING(500, MessageCodeEnum.GENERIC_CODE_WARNING.getCodeString(), "Operation finished with Warning :  %REASON%.", false, false, false, MessageGeneralEnum.GENERIC_WARNING),
    GENERIC_ERROR(500, MessageCodeEnum.GENERIC_CODE_ERROR.getCodeString(), "Operation finished with error ! %REASON%", false, false, false, MessageGeneralEnum.GENERIC_WARNING),
    // *********** OTHERS ***********
    NOT_IMPLEMEMTED(900, "", "Not Implememted.", true, true, false, MessageGeneralEnum.EXECUTION_FA);

    private final int code;
    private final String codeString;
    private final String description;
    private final boolean stopTest;
    private final boolean doScreenshot;
    private final boolean getPageSource;
    private final MessageGeneralEnum message;

    private MessageEventEnum(int tempCode, String tempCodeString, String tempDesc, boolean tempStopTest, boolean tempDoScreenshot, boolean tempGetPageSource, MessageGeneralEnum tempMessage) {
        this.code = tempCode;
        this.codeString = tempCodeString;
        this.description = tempDesc;
        this.stopTest = tempStopTest;
        this.doScreenshot = tempDoScreenshot;
        this.getPageSource = tempGetPageSource;
        this.message = tempMessage;
    }

    public String getDescription() {
        return description;
    }

    public MessageGeneralEnum getMessage() {
        return message;
    }

    public boolean isStopTest() {
        return stopTest;
    }

    public boolean isDoScreenshot() {
        return doScreenshot;
    }

    public boolean isGetPageSource() {
        return getPageSource;
    }

    public int getCode() {
        return this.code;
    }

    public String getCodeString() {
        return codeString;
    }

}
