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

import java.util.List;
import org.cerberus.engine.entity.MessageGeneral;
import org.cerberus.engine.entity.MessageEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author bcivel
 */
public class TestCaseStepActionControlExecution {

    private long id;
    private String test;
    private String testCase;
    private int step;
    private int index;
    private int sequence;
    private int controlSequence;
    private int sort;
    private String conditionOper;
    private String conditionVal1Init;
    private String conditionVal2Init;
    private String conditionVal1;
    private String conditionVal2;
    private String control;
    private String value1Init;
    private String value2Init;
    private String value1;
    private String value2;
    private String fatal;
    private String description;
    private String returnCode;
    private String returnMessage;
    private long start;
    private long end;
    private long startLong;
    private long endLong;
    /**
     *
     */
    private TestCaseStepActionExecution testCaseStepActionExecution;
    private List<TestCaseExecutionFile> fileList; // Host the list of the files stored at control level
    private MessageEvent controlResultMessage;
    private MessageGeneral executionResultMessage;
    private boolean stopExecution;

    public List<TestCaseExecutionFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<TestCaseExecutionFile> fileList) {
        this.fileList = fileList;
    }

    public void addFileList(TestCaseExecutionFile file) {
        this.fileList.add(file);
    }

    public void addFileList(List<TestCaseExecutionFile> fileList) {
        if (fileList != null) {
            for (TestCaseExecutionFile testCaseExecutionFile : fileList) {
                this.fileList.add(testCaseExecutionFile);
            }
        }
    }

    public MessageEvent getControlResultMessage() {
        return controlResultMessage;
    }

    public void setControlResultMessage(MessageEvent controlResultMessage) {
        this.controlResultMessage = controlResultMessage;
        if (controlResultMessage != null) {
            this.setReturnCode(controlResultMessage.getCodeString());
            this.setReturnMessage(controlResultMessage.getDescription());
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

    public TestCaseStepActionExecution getTestCaseStepActionExecution() {
        return testCaseStepActionExecution;
    }

    public void setTestCaseStepActionExecution(TestCaseStepActionExecution testCaseStepActionExecution) {
        this.testCaseStepActionExecution = testCaseStepActionExecution;
    }

    public int getControlSequence() {
        return controlSequence;
    }

    public void setControlSequence(int control) {
        this.controlSequence = control;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getConditionOper() {
        return conditionOper;
    }

    public void setConditionOper(String conditionOper) {
        this.conditionOper = conditionOper;
    }

    public String getConditionVal1() {
        return conditionVal1;
    }

    public String getConditionVal1Init() {
        return conditionVal1Init;
    }

    public void setConditionVal1Init(String conditionVal1Init) {
        this.conditionVal1Init = conditionVal1Init;
    }

    public String getConditionVal2Init() {
        return conditionVal2Init;
    }

    public void setConditionVal2Init(String conditionVal2Init) {
        this.conditionVal2Init = conditionVal2Init;
    }

    public void setConditionVal1(String conditionVal1) {
        this.conditionVal1 = conditionVal1;
    }

    public String getConditionVal2() {
        return conditionVal2;
    }

    public void setConditionVal2(String conditionVal2) {
        this.conditionVal2 = conditionVal2;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String controlProperty) {
        this.value1 = controlProperty;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String controlType) {
        this.control = controlType;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String controlValue) {
        this.value2 = controlValue;
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

    public String getFatal() {
        return fatal;
    }

    public void setFatal(String fatal) {
        this.fatal = fatal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
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

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getTest() {
        return test;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getTestCase() {
        return testCase;
    }

    public void setTestCase(String testCase) {
        this.testCase = testCase;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        try {
            result.put("id", this.getId());
            result.put("test", this.getTest());
            result.put("testcase", this.getTestCase());
            result.put("step", this.getStep());
            result.put("index", this.getIndex());
            result.put("sequence", this.getSequence());
            result.put("control", this.getControlSequence());
            result.put("sort", this.getSort());
            result.put("conditionOper", this.getConditionOper());
            result.put("conditionVal1Init", this.getConditionVal1Init());
            result.put("conditionVal2Init", this.getConditionVal2Init());
            result.put("conditionVal1", this.getConditionVal1());
            result.put("conditionVal2", this.getConditionVal2());
            result.put("controlType", this.getControl());
            result.put("controlProperty", this.getValue1());
            result.put("controlPropertyInit", this.getValue1Init());
            result.put("controlValue", this.getValue2());
            result.put("controlValueInit", this.getValue2Init());
            result.put("fatal", this.getFatal());
            result.put("start", this.getStart());
            result.put("end", this.getEnd());
            result.put("startlong", this.getStartLong());
            result.put("endlong", this.getEndLong());
            result.put("description", this.getDescription());
            result.put("returnCode", this.getReturnCode());
            result.put("returnMessage", this.getReturnMessage());
            
            JSONArray array = new JSONArray();
            if (this.getFileList()!= null) {
                for (Object actionFileList : this.getFileList()) {
                    array.put(((TestCaseExecutionFile) actionFileList).toJson());
                }
            }
            result.put("fileList", array);
            
        } catch (JSONException ex) {
            Logger.getLogger(TestCaseStepExecution.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
