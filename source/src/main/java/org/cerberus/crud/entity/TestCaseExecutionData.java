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
package org.cerberus.crud.entity;

import org.apache.log4j.Logger;
import org.cerberus.engine.entity.MessageGeneral;
import org.cerberus.engine.entity.MessageEvent;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;

/**
 *
 * @author bcivel
 */
public class TestCaseExecutionData {

    private long id;
    private String property;
    private int index;
    private String type;
    private String value;
    private String database;
    private String value1Init;
    private String value2Init;
    private String value1;
    private String value2;
    private int length;
    private int rowLimit;
    private String nature;
    private int retryNb;
    private int retryPeriod;
    private long start;
    private long end;
    private long startLong;
    private long endLong;
    private String RC;
    private String rMessage;
    private String description;
    /**
     *
     */
    private List<TestCaseExecutionFile> fileList; // Host the list of the files stored at control level
    private TestCaseExecution tCExecution;
    private MessageEvent propertyResultMessage;
    private MessageGeneral executionResultMessage;
    private boolean stopExecution;
    private TestCaseCountryProperties testCaseCountryProperties;
    private List<HashMap<String, String>> dataLibRawData; // Have the raw data of all subdata when comming from testDataLibrary

    private static final Logger LOG = Logger.getLogger(TestCaseExecutionData.class);

    public List<TestCaseExecutionFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<TestCaseExecutionFile> fileList) {
        this.fileList = fileList;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getValue1Init() {
        return value1Init;
    }

    public void setValue1Init(String value1Init) {
        this.value1Init = value1Init;
    }

    public String getValue2Init() {
        return value2Init;
    }

    public void setValue2Init(String value2Init) {
        this.value2Init = value2Init;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getRowLimit() {
        return rowLimit;
    }

    public void setRowLimit(int rowLimit) {
        this.rowLimit = rowLimit;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }
    public int getRetryNb() {
        return retryNb;
    }

    public void setRetryNb(int retrynb) {
        this.retryNb = retrynb;
    }

    public int getRetryPeriod() {
        return retryPeriod;
    }

    public void setRetryPeriod(int retryperiod) {
        this.retryPeriod = retryperiod;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<HashMap<String, String>> getDataLibRawData() {
        return dataLibRawData;
    }

    public void setDataLibRawData(List<HashMap<String, String>> dataLibRawData) {
        this.dataLibRawData = dataLibRawData;
    }

    public TestCaseCountryProperties getTestCaseCountryProperties() {
        return testCaseCountryProperties;
    }

    public void setTestCaseCountryProperties(TestCaseCountryProperties testCaseCountryProperties) {
        this.testCaseCountryProperties = testCaseCountryProperties;
    }

    public String getrMessage() {
        return rMessage;
    }

    public void setrMessage(String rMessage) {
        this.rMessage = rMessage;
    }

    public MessageEvent getPropertyResultMessage() {
        return propertyResultMessage;
    }

    public void setPropertyResultMessage(MessageEvent propertyResultMessage) {
        this.propertyResultMessage = propertyResultMessage;
        if (propertyResultMessage != null) {
            this.setRC(propertyResultMessage.getCodeString());
            this.setrMessage(propertyResultMessage.getDescription());
            this.executionResultMessage = new MessageGeneral(propertyResultMessage.getMessage());
            this.stopExecution = propertyResultMessage.isStopTest();
        }
    }

    public MessageGeneral getExecutionResultMessage() {
        return executionResultMessage;
    }

    public void setExecutionResultMessage(MessageGeneral executionResultMessage) {
        this.executionResultMessage = executionResultMessage;
    }

    public boolean isStopExecution() {
        return stopExecution;
    }

    public void setStopExecution(boolean stopExecution) {
        this.stopExecution = stopExecution;
    }

    public TestCaseExecution gettCExecution() {
        return tCExecution;
    }

    public void settCExecution(TestCaseExecution tCExecution) {
        this.tCExecution = tCExecution;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getEndLong() {
        return endLong;
    }

    public void setEndLong(long endLong) {
        this.endLong = endLong;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRC() {
        return RC;
    }

    public void setRC(String rc) {
        this.RC = rc;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getStartLong() {
        return startLong;
    }

    public void setStartLong(long startLong) {
        this.startLong = startLong;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TestCaseExecutionData{" + "id=" + id + ", property=" + property + ", value=" + value + ", type=" + type + ", value1=" + value1 + ", value2=" + value2 + ", RC=" + RC + ", rMessage=" + rMessage + ", start=" + start + ", end=" + end + ", startLong=" + startLong + ", endLong=" + endLong + ", propertyResultMessage=" + propertyResultMessage.toString() + ", executionResultMessage=" + executionResultMessage + ", stopExecution=" + stopExecution + '}';
    }

    public JSONObject toJson(){
        JSONObject result = new JSONObject();
        try {
            result.put("id", this.getId());
            result.put("property", this.getProperty());
            result.put("index", this.getIndex());
            result.put("database", this.getDatabase());
            result.put("value", this.getValue());
            result.put("type", this.getType());
            result.put("value1Init", this.getValue1Init());
            result.put("value2Init", this.getValue2Init());
            result.put("value1", this.getValue1());
            result.put("value2", this.getValue2());
            result.put("length", this.getLength());
            result.put("rowLimit", this.getRowLimit());
            result.put("nature", this.getNature());
            result.put("retryNb", this.getRetryNb());
            result.put("retryPeriod", this.getRetryPeriod());
            result.put("start", this.getStart());
            result.put("end", this.getEnd());
            result.put("startLong", this.getStartLong());
            result.put("endLong", this.getEndLong());
            result.put("RC", this.getRC());
            result.put("rMessage", this.getrMessage());
            result.put("description", this.getDescription());
            
            JSONArray array = new JSONArray();
            if (this.getFileList()!= null) {
                for (Object dataFileList : this.getFileList()) {
                    array.put(((TestCaseExecutionFile) dataFileList).toJson());
                }
            }
            result.put("fileList", array);
            
            
        } catch (JSONException ex) {
            LOG.error(ex.toString());
        }
        return result;
    }

}
