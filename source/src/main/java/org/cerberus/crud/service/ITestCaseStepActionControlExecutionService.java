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
package org.cerberus.crud.service;

import java.util.List;

import org.cerberus.crud.entity.TestCaseStepActionControlExecution;
import org.cerberus.util.answer.AnswerItem;
import org.cerberus.util.answer.AnswerList;

/**
 *
 * @author bcivel
 */
public interface ITestCaseStepActionControlExecutionService {

    /**
     *
     * @param testCaseStepActionControlExecution
     */
    void insertTestCaseStepActionControlExecution(TestCaseStepActionControlExecution testCaseStepActionControlExecution);

    /**
     *
     * @param testCaseStepActionControlExecution
     */
    void updateTestCaseStepActionControlExecution(TestCaseStepActionControlExecution testCaseStepActionControlExecution);
    
    List<TestCaseStepActionControlExecution> findTestCaseStepActionControlExecutionByCriteria(long id, String test, String testCase, int step, int index, int sequence);

    /**
     * Return the testcasestepactioncontrolexecution list of an execution, step, action
     * @param executionId : ID of the execution
     * @param test : test
     * @param testcase : testcase
     * @param step : ID of the step
     * @param index
     * @param sequence : ID of the action
     * @return List of testcasestepactioncontrol object
     */
    public AnswerList readByVarious1(long executionId, String test, String testcase, int step, int index, int sequence);

    /**
     * Return the testcasestepactioncontrolexecution list of an execution, step, action
     * @param executionId : ID of the execution
     * @param test : test
     * @param testcase : testcase
     * @param step : ID of the step
     * @param index
     * @param sequence : ID of the action
     * @param controlSequence : ID of the control
     * @return List of testcasestepactioncontrol object
     */
    public AnswerItem readByKey(long executionId, String test, String testcase, int step, int index, int sequence, int controlSequence);

    /**
     * Return the testcasestepactioncontrolexecution list of an execution, step, action
     * @param executionId : ID of the execution
     * @param test : test
     * @param testcase : testcase
     * @param step : ID of the step
     * @param index
     * @param sequence : ID of the action
     * @return List of testcasestepactioncontrol object
     */
    public AnswerList readByVarious1WithDependency(long executionId, String test, String testcase, int step, int index, int sequence);
}
