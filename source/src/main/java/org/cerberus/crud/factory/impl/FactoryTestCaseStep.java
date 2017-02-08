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
package org.cerberus.crud.factory.impl;

import org.cerberus.crud.entity.TestCaseStep;
import org.cerberus.crud.factory.IFactoryTestCaseStep;
import org.springframework.stereotype.Service;

/**
 * @author bcivel
 */
@Service
public class FactoryTestCaseStep implements IFactoryTestCaseStep {

    @Override
    public TestCaseStep create(String test, String testCase, int step, int sort, String loop, String conditionOper, String conditionVal1, String conditionVal2, String description, String useStep,
            String useStepTest, String useStepTestCase, Integer useStepStep, String inLibrary) {
        TestCaseStep testCaseStep = new TestCaseStep();
        testCaseStep.setDescription(description);
        testCaseStep.setStep(step);
        testCaseStep.setSort(sort);
        testCaseStep.setLoop(loop);
        testCaseStep.setConditionOper(conditionOper);
        testCaseStep.setConditionVal1(conditionVal1);
        testCaseStep.setConditionVal2(conditionVal2);
        testCaseStep.setTest(test);
        testCaseStep.setTestCase(testCase);
        testCaseStep.setUseStep(useStep);
        testCaseStep.setUseStepTest(useStepTest);
        testCaseStep.setUseStepTestCase(useStepTestCase);
        testCaseStep.setUseStepStep(useStepStep);
        testCaseStep.setInLibrary(inLibrary);
        return testCaseStep;
    }

}
