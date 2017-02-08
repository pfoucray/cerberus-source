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
package org.cerberus.crud.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.cerberus.crud.dao.ITestCaseStepExecutionDAO;
import org.cerberus.engine.entity.MessageEvent;
import org.cerberus.database.DatabaseSpring;
import org.cerberus.crud.entity.TestCaseStepExecution;
import org.cerberus.crud.factory.IFactoryTestCaseStepExecution;
import org.cerberus.enums.MessageEventEnum;
import org.cerberus.log.MyLogger;
import org.cerberus.util.DateUtil;
import org.cerberus.util.ParameterParserUtil;
import org.cerberus.util.StringUtil;
import org.cerberus.util.answer.AnswerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * {Insert class description here}
 *
 * @author Tiago Bernardes
 * @version 1.0, 03/01/2013
 * @since 0.9.0
 */
@Repository
public class TestCaseStepExecutionDAO implements ITestCaseStepExecutionDAO {

    /**
     * Description of the variable here.
     */
    @Autowired
    private DatabaseSpring databaseSpring;
    @Autowired
    private IFactoryTestCaseStepExecution factoryTestCaseStepExecution;

    private static final Logger LOG = Logger.getLogger(TestCaseStepExecutionDAO.class);

    /**
     * Short one line description.
     * <p/>
     * Longer description. If there were any, it would be here.
     * <p>
     * And even more explanations to follow in consecutive paragraphs separated
     * by HTML paragraph breaks.
     *
     * @param variable Description text text text.
     */
    @Override
    public void insertTestCaseStepExecution(TestCaseStepExecution testCaseStepExecution) {
        final String query = "INSERT INTO testcasestepexecution(id, test, testcase, step, `index`, sort, `loop`, batnumexe, returncode, start, fullstart, "
                + "returnMessage, description, conditionOper, conditionVal1Init, conditionVal2Init, conditionVal1, conditionVal2) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query);
            try {
                int i = 1;
                preStat.setLong(i++, testCaseStepExecution.getId());
                preStat.setString(i++, testCaseStepExecution.getTest());
                preStat.setString(i++, testCaseStepExecution.getTestCase());
                preStat.setInt(i++, testCaseStepExecution.getStep());
                preStat.setInt(i++, testCaseStepExecution.getIndex());
                preStat.setInt(i++, testCaseStepExecution.getSort());
                preStat.setString(i++, testCaseStepExecution.getLoop());
                preStat.setString(i++, testCaseStepExecution.getBatNumExe());
                preStat.setString(i++, testCaseStepExecution.getReturnCode());
                preStat.setTimestamp(i++, new Timestamp(testCaseStepExecution.getStart()));
                DateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT_TIMESTAMP);
                preStat.setString(i++, df.format(testCaseStepExecution.getStart()));
                preStat.setString(i++, testCaseStepExecution.getReturnMessage());
                preStat.setString(i++, testCaseStepExecution.getDescription());
                preStat.setString(i++, testCaseStepExecution.getConditionOper());
                preStat.setString(i++, testCaseStepExecution.getConditionVal1Init());
                preStat.setString(i++, testCaseStepExecution.getConditionVal2Init());
                preStat.setString(i++, testCaseStepExecution.getConditionVal1());
                preStat.setString(i++, testCaseStepExecution.getConditionVal2());
                MyLogger.log(TestCaseStepExecutionDAO.class.getName(), Level.DEBUG, "Insert testcasestepexecution " + testCaseStepExecution.getId() + "-"
                        + testCaseStepExecution.getTest() + "-" + testCaseStepExecution.getTestCase() + "-" + testCaseStepExecution.getStep());

                preStat.executeUpdate();

            } catch (SQLException exception) {
                MyLogger.log(TestCaseStepExecutionDAO.class.getName(), Level.ERROR, "Unable to execute query : " + exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            MyLogger.log(TestCaseStepExecutionDAO.class.getName(), Level.ERROR, "Unable to execute query : " + exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                MyLogger.log(TestCaseStepExecutionDAO.class.getName(), Level.WARN, e.toString());
            }
        }
    }

    @Override
    public void updateTestCaseStepExecution(TestCaseStepExecution testCaseStepExecution) {
        final String query = "UPDATE testcasestepexecution SET returncode = ?, start = ?, fullstart = ?, end = ?, fullend = ?, timeelapsed = ?, "
                + "returnmessage = ?, description = ?, sort = ?, `loop` = ?, conditionOper = ?, conditionVal1Init = ?, conditionVal2Init = ?, conditionVal1 = ?, conditionVal2 = ? "
                + "WHERE id = ? AND step = ? AND `index` = ? AND test = ? AND testcase = ?";

        Connection connection = this.databaseSpring.connect();
        try {
            Timestamp timeStart = new Timestamp(testCaseStepExecution.getStart());
            Timestamp timeEnd = new Timestamp(testCaseStepExecution.getEnd());

            PreparedStatement preStat = connection.prepareStatement(query);
            try {
                int i = 1;
                DateFormat df = new SimpleDateFormat(DateUtil.DATE_FORMAT_TIMESTAMP);
                preStat.setString(i++, ParameterParserUtil.parseStringParam(testCaseStepExecution.getReturnCode(), ""));
                preStat.setTimestamp(i++, timeStart);
                preStat.setString(i++, df.format(timeStart));
                preStat.setTimestamp(i++, timeEnd);
                preStat.setString(i++, df.format(timeEnd));
                preStat.setFloat(i++, (timeEnd.getTime() - timeStart.getTime()) / (float) 1000);
                preStat.setString(i++, testCaseStepExecution.getReturnMessage());
                preStat.setString(i++, testCaseStepExecution.getDescription());
                preStat.setInt(i++, testCaseStepExecution.getSort());
                preStat.setString(i++, testCaseStepExecution.getLoop());
                preStat.setString(i++, testCaseStepExecution.getConditionOper());
                preStat.setString(i++, testCaseStepExecution.getConditionVal1Init());
                preStat.setString(i++, testCaseStepExecution.getConditionVal2Init());
                preStat.setString(i++, testCaseStepExecution.getConditionVal1());
                preStat.setString(i++, testCaseStepExecution.getConditionVal2());
                preStat.setLong(i++, testCaseStepExecution.getId());
                preStat.setInt(i++, testCaseStepExecution.getStep());
                preStat.setInt(i++, testCaseStepExecution.getIndex());
                preStat.setString(i++, testCaseStepExecution.getTest());
                preStat.setString(i++, testCaseStepExecution.getTestCase());
                MyLogger.log(TestCaseStepExecutionDAO.class.getName(), Level.DEBUG, "Update testcasestepexecution " + testCaseStepExecution.getId() + "-"
                        + testCaseStepExecution.getTest() + "-" + testCaseStepExecution.getTestCase() + "-" + testCaseStepExecution.getStep());

                preStat.executeUpdate();

            } catch (SQLException exception) {
                MyLogger.log(TestCaseStepExecutionDAO.class.getName(), Level.ERROR, "Unable to execute query : " + exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            MyLogger.log(TestCaseStepExecutionDAO.class.getName(), Level.ERROR, "Unable to execute query : " + exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                MyLogger.log(TestCaseStepExecutionDAO.class.getName(), Level.WARN, e.toString());
            }
        }
    }

    @Override
    public List<TestCaseStepExecution> findTestCaseStepExecutionById(long id) {
        List<TestCaseStepExecution> result = null;
        TestCaseStepExecution resultData;
        final String query = "SELECT * FROM testcasestepexecution WHERE id = ? ORDER BY fullstart, sort";

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query);
            try {
                preStat.setString(1, String.valueOf(id));

                ResultSet resultSet = preStat.executeQuery();
                result = new ArrayList<TestCaseStepExecution>();
                try {
                    while (resultSet.next()) {
                        result.add(this.loadFromResultSet(resultSet));
                    }
                } catch (SQLException exception) {
                    MyLogger.log(TestCaseStepExecutionDAO.class.getName(), Level.ERROR, "Unable to execute query : " + exception.toString());
                } finally {
                    resultSet.close();
                }
            } catch (SQLException exception) {
                MyLogger.log(TestCaseStepExecutionDAO.class.getName(), Level.ERROR, "Unable to execute query : " + exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            MyLogger.log(TestCaseStepExecutionDAO.class.getName(), Level.ERROR, "Unable to execute query : " + exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                MyLogger.log(TestCaseStepExecutionDAO.class.getName(), Level.WARN, e.toString());
            }
        }
        return result;
    }

    @Override
    public AnswerList readByVarious1(long executionId, String test, String testcase) {
        MessageEvent msg;
        AnswerList answer = new AnswerList();
        List<TestCaseStepExecution> list = new ArrayList<TestCaseStepExecution>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM testcasestepexecution a ");
        query.append("where 1=1 and id = ? ");
        if (!(StringUtil.isNullOrEmpty(test))) {
            query.append("and test = ? ");
        }
        if (!(StringUtil.isNullOrEmpty(test))) {
            query.append("and testcase = ? ");
        }
            query.append(" order by start ");

        // Debug message on SQL.
        if (LOG.isDebugEnabled()) {
            LOG.debug("SQL : " + query.toString());
        }
        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query.toString());
            try {
                int i = 1;
                preStat.setLong(i++, executionId);
                if (!(StringUtil.isNullOrEmpty(test))) {
                    preStat.setString(i++, test);
                }
                if (!(StringUtil.isNullOrEmpty(test))) {
                    preStat.setString(i++, testcase);
                }
                ResultSet resultSet = preStat.executeQuery();
                try {
                    while (resultSet.next()) {
                        list.add(this.loadFromResultSet(resultSet));
                    }
                    if (list.isEmpty()) {
                        msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_NO_DATA_FOUND);
                    } else {
                        msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_OK);
                    }
                } catch (SQLException exception) {
                    LOG.error("Unable to execute query : " + exception.toString());
                    msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_UNEXPECTED);
                    msg.setDescription(msg.getDescription().replace("%DESCRIPTION%", exception.toString()));
                    list.clear();
                } finally {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                }
            } catch (SQLException exception) {
                LOG.error("Unable to execute query : " + exception.toString());
                msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_UNEXPECTED);
                msg.setDescription(msg.getDescription().replace("%DESCRIPTION%", exception.toString()));
            } finally {
                if (preStat != null) {
                    preStat.close();
                }
            }
        } catch (SQLException exception) {
            LOG.error("Unable to execute query : " + exception.toString());
            msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_UNEXPECTED);
            msg.setDescription(msg.getDescription().replace("%DESCRIPTION%", exception.toString()));
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException exception) {
                LOG.warn("Unable to close connection : " + exception.toString());
            }
        }

        answer.setTotalRows(list.size());
        answer.setDataList(list);
        answer.setResultMessage(msg);
        return answer;
    }

    @Override
    public TestCaseStepExecution loadFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getInt("id");
        String test = resultSet.getString("test");
        String testcase = resultSet.getString("testcase");
        int step = resultSet.getInt("step");
        int index = resultSet.getInt("index");
        int sort = resultSet.getInt("sort");
        String loop = resultSet.getString("loop");
        String conditionOper = resultSet.getString("conditionOper");
        String conditionVal1 = resultSet.getString("conditionVal1");
        String conditionVal2 = resultSet.getString("conditionVal2");
        String conditionVal1Init = resultSet.getString("conditionVal1Init");
        String conditionVal2Init = resultSet.getString("conditionVal2Init");
        String batNumExe = resultSet.getString("batnumexe");
        long start = resultSet.getTimestamp("start") == null ? 0 : resultSet.getTimestamp("start").getTime();
        long end = resultSet.getTimestamp("end") == null ? 0 : resultSet.getTimestamp("end").getTime();
        long fullstart = resultSet.getLong("fullstart");
        long fullend = resultSet.getLong("Fullend");
        BigDecimal timeelapsed = resultSet.getBigDecimal("timeelapsed");
        String returnCode = resultSet.getString("returncode");
        String returnMessage = resultSet.getString("returnMessage");
        String description = resultSet.getString("description");
        return factoryTestCaseStepExecution.create(id, test, testcase, step, index, sort, loop, conditionOper, conditionVal1Init, conditionVal2Init, conditionVal1, conditionVal2, batNumExe, start, end, fullstart, fullend, timeelapsed, returnCode, returnMessage, description);
    }
}
