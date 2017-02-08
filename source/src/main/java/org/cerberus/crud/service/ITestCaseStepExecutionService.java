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

import org.cerberus.crud.entity.TestCaseStepExecution;
import org.cerberus.util.answer.AnswerList;

/**
 *
 * @author bcivel
 */
public interface ITestCaseStepExecutionService {

    /**
     *
     * @param testCaseStepExecution
     */
    void insertTestCaseStepExecution(TestCaseStepExecution testCaseStepExecution);

    /**
     *
     * @param testCaseStepExecution
     */
    void updateTestCaseStepExecution(TestCaseStepExecution testCaseStepExecution);

    /**
     *
     * @param id
     * @return List of testCaseStepExecution that correspond to the Id.
     */
    List<TestCaseStepExecution> findTestCaseStepExecutionById(long id);
    
    /**
     *
     * @param executionId
     * @param test
     * @param testcase
     * @return
     */
    AnswerList readByVarious1(long executionId, String test, String testcase);
    
    /**
     *
     * @param executionId
     * @param test
     * @param testcase
     * @return
     */
    AnswerList readByVarious1WithDependency(long executionId, String test, String testcase);
}
