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
package org.cerberus.crud.factory;

import org.cerberus.engine.entity.MessageEvent;
import org.cerberus.crud.entity.TestCaseStepAction;
import org.cerberus.crud.entity.TestCaseStepActionExecution;
import org.cerberus.crud.entity.TestCaseStepExecution;

/**
 * @author bcivel
 */
public interface IFactoryTestCaseStepActionExecution {

    TestCaseStepActionExecution create(long id, String test, String testCase, int step, int index, int sequence, int sort, String returnCode, String returnMessage,
            String conditionOper, String conditionVal1Init, String conditionVal2Init, String conditionVal1, String conditionVal2, String action, String value1Init, String value2Init, String value1, String value2,
            String forceExeStatus, long start, long end, long startLong, long endLong, MessageEvent resultMessage, String description, TestCaseStepAction testCaseStepAction,
            TestCaseStepExecution testCaseStepExecution);

}
