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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.cerberus.crud.dao.ITestCaseStepDAO;
import org.cerberus.engine.entity.MessageEvent;
import org.cerberus.database.DatabaseSpring;
import org.cerberus.engine.entity.MessageGeneral;
import org.cerberus.enums.MessageGeneralEnum;
import org.cerberus.crud.entity.TestCase;
import org.cerberus.crud.entity.TestCaseStep;
import org.cerberus.exception.CerberusException;
import org.cerberus.crud.factory.IFactoryTestCaseStep;
import org.cerberus.enums.MessageEventEnum;
import org.cerberus.util.answer.Answer;
import org.cerberus.util.answer.AnswerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.cerberus.crud.factory.IFactoryTestCase;

/**
 * {Insert class description here}
 *
 * @author Tiago Bernardes
 * @version 1.0, 29/12/2012
 * @since 2.0.0
 */
@Repository
public class TestCaseStepDAO implements ITestCaseStepDAO {

    /**
     * Description of the variable here.
     */
    @Autowired
    private DatabaseSpring databaseSpring;
    @Autowired
    private IFactoryTestCaseStep factoryTestCaseStep;
    @Autowired
    private IFactoryTestCase factoryTestCase;

    private static final Logger LOG = Logger.getLogger(TestCaseStepDAO.class);

    private final String OBJECT_NAME = "TestCaseStep";
    private final String SQL_DUPLICATED_CODE = "23000";
    private final int MAX_ROW_SELECTED = 100000;

    /**
     * Short one line description.
     * <p/>
     * Longer description. If there were any, it would be here.
     * <p>
     * And even more explanations to follow in consecutive paragraphs separated
     * by HTML paragraph breaks.
     *
     * @param variable Description text text text.
     * @return Description text text text.
     */
    @Override
    public List<TestCaseStep> findTestCaseStepByTestCase(String test, String testcase) {
        List<TestCaseStep> list = null;
        final String query = "SELECT * FROM testcasestep WHERE test = ? AND testcase = ? ORDER BY sort";

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query);
            try {
                preStat.setString(1, test);
                preStat.setString(2, testcase);

                ResultSet resultSet = preStat.executeQuery();
                list = new ArrayList<TestCaseStep>();
                try {
                    while (resultSet.next()) {
                        list.add(loadFromResultSet(resultSet));
                    }
                } catch (SQLException exception) {
                    LOG.error("Unable to execute query : " + exception.toString());
                } finally {
                    resultSet.close();
                }
            } catch (SQLException exception) {
                LOG.error("Unable to execute query : " + exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            LOG.error("Unable to execute query : " + exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.warn("Exception Closing the connection : " + e.toString());
            }
        }
        return list;
    }

    @Override
    public List<String> getLoginStepFromTestCase(String countryCode, String application) {
        List<String> list = null;
        final String query = "SELECT tc.testcase FROM testcasecountry t, testcase tc WHERE t.country = ? AND t.test = 'Pre Testing' "
                + "AND tc.application = ? AND tc.tcActive = 'Y' AND t.test = tc.test AND t.testcase = tc.testcase ORDER BY testcase ASC";

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query);
            try {
                preStat.setString(1, countryCode);
                preStat.setString(2, application);

                ResultSet resultSet = preStat.executeQuery();
                list = new ArrayList<String>();
                try {
                    while (resultSet.next()) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Found active Pretest : " + resultSet.getString("testcase"));
                        }
                        list.add(resultSet.getString("testcase"));
                    }
                } catch (SQLException exception) {
                    LOG.error("Unable to execute query : " + exception.toString());
                } finally {
                    resultSet.close();
                }
            } catch (SQLException exception) {
                LOG.error("Unable to execute query : " + exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            LOG.error("Unable to execute query : " + exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.warn("Exception Closing the connection : " + e.toString());
            }
        }
        return list;
    }

    @Override
    public void insertTestCaseStep(TestCaseStep testCaseStep) throws CerberusException {
        boolean throwExcep = false;
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO `testcasestep` (`Test`,`TestCase`,`Step`,`Sort`,`Description`,`useStep`,`useStepTest`,`useStepTestCase`,`useStepStep`, `inLibrary`, `loop`, `conditionOper`, `conditionVal1`, `conditionVal2`) ");
        query.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query.toString());
            try {
                int i=1;
                preStat.setString(i++, testCaseStep.getTest());
                preStat.setString(i++, testCaseStep.getTestCase());
                preStat.setInt(i++, testCaseStep.getStep());
                preStat.setInt(i++, testCaseStep.getSort());
                preStat.setString(i++, testCaseStep.getDescription());
                preStat.setString(i++, testCaseStep.getUseStep() == null ? "N" : testCaseStep.getUseStep());
                preStat.setString(i++, testCaseStep.getUseStepTest() == null ? "" : testCaseStep.getUseStepTest());
                preStat.setString(i++, testCaseStep.getUseStepTestCase() == null ? "" : testCaseStep.getUseStepTestCase());
                preStat.setInt(i++, testCaseStep.getUseStepStep() == null ? 0 : testCaseStep.getUseStepStep());
                preStat.setString(i++, testCaseStep.getInLibrary() == null ? "N" : testCaseStep.getInLibrary());
                preStat.setString(i++, testCaseStep.getLoop()== null ? "" : testCaseStep.getLoop());
                preStat.setString(i++, testCaseStep.getConditionOper()== null ? "" : testCaseStep.getConditionOper());
                preStat.setString(i++, testCaseStep.getConditionVal1()== null ? "" : testCaseStep.getConditionVal1());
                preStat.setString(i++, testCaseStep.getConditionVal2()== null ? "" : testCaseStep.getConditionVal2());

                preStat.executeUpdate();
                throwExcep = false;

            } catch (SQLException exception) {
                LOG.error("Unable to execute query : " + exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            LOG.error("Unable to execute query : " + exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.warn("Exception Closing the connection : " + e.toString());
            }
        }
        if (throwExcep) {
            throw new CerberusException(new MessageGeneral(MessageGeneralEnum.CANNOT_UPDATE_TABLE));
        }
    }

    @Override
    public TestCaseStep findTestCaseStep(String test, String testcase, Integer step) {
        TestCaseStep result = null;
        final String query = "SELECT * FROM testcasestep WHERE test = ? AND testcase = ? AND step = ?";

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query);
            try {
                preStat.setString(1, test);
                preStat.setString(2, testcase);
                preStat.setInt(3, step);

                ResultSet resultSet = preStat.executeQuery();
                try {
                    if (resultSet.first()) {
                        result = loadFromResultSet(resultSet);
                    }
                } catch (SQLException exception) {
                    LOG.error("Unable to execute query : " + exception.toString());
                } finally {
                    resultSet.close();
                }
            } catch (SQLException exception) {
                LOG.error("Unable to execute query : " + exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            LOG.error("Unable to execute query : " + exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.warn("Exception Closing the connection : " + e.toString());
            }
        }
        return result;
    }

    @Override
    public void deleteTestCaseStep(TestCaseStep tcs) throws CerberusException {
        boolean throwExcep = false;
        final String query = "DELETE FROM testcasestep WHERE test = ? and testcase = ? and step = ?";

        // Debug message on SQL.
        if (LOG.isDebugEnabled()) {
            LOG.debug("SQL : " + query);
        }
        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query);
            try {
                preStat.setString(1, tcs.getTest());
                preStat.setString(2, tcs.getTestCase());
                preStat.setInt(3, tcs.getStep());

                throwExcep = preStat.executeUpdate() == 0;
            } catch (SQLException exception) {
                LOG.error("Unable to execute query : " + exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            LOG.error("Unable to execute query : " + exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.warn("Exception Closing the connection : " + e.toString());
            }
        }
        if (throwExcep) {
            throw new CerberusException(new MessageGeneral(MessageGeneralEnum.CANNOT_UPDATE_TABLE));
        }
    }

    @Override
    public void updateTestCaseStep(TestCaseStep tcs) throws CerberusException {
        boolean throwExcep = false;
        StringBuilder query = new StringBuilder();
        query.append("UPDATE testcasestep SET ");
        query.append(" `Description` = ?,`useStep`=?,`useStepTest`=?,`useStepTestCase`=?,`useStepStep`=?,");
        query.append(" `inlibrary` = ?, `Sort` = ?, `loop` = ?, `conditionOper` = ?, `conditionVal1` = ?, `conditionVal2` = ? WHERE Test = ? AND TestCase = ? AND step = ?");

        // Debug message on SQL.
        if (LOG.isDebugEnabled()) {
            LOG.debug("SQL : " + query.toString());
        }
        
        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query.toString());
            try {
                int i=1;
                preStat.setString(i++, tcs.getDescription());
                preStat.setString(i++, tcs.getUseStep() == null ? "N" : tcs.getUseStep());
                preStat.setString(i++, tcs.getUseStepTest() == null ? "" : tcs.getUseStepTest());
                preStat.setString(i++, tcs.getUseStepTestCase() == null ? "" : tcs.getUseStepTestCase());
                preStat.setInt(i++, tcs.getUseStepStep() == null ? 0 : tcs.getUseStepStep());
                preStat.setString(i++, tcs.getInLibrary() == null ? "N" : tcs.getInLibrary());
                preStat.setInt(i++, tcs.getSort());
                preStat.setString(i++, tcs.getLoop()== null ? "" : tcs.getLoop());
                preStat.setString(i++, tcs.getConditionOper()== null ? "" : tcs.getConditionOper());
                preStat.setString(i++, tcs.getConditionVal1()== null ? "" : tcs.getConditionVal1());
                preStat.setString(i++, tcs.getConditionVal2()== null ? "" : tcs.getConditionVal2());

                preStat.setString(i++, tcs.getTest());
                preStat.setString(i++, tcs.getTestCase());
                preStat.setInt(i++, tcs.getStep());

                preStat.executeUpdate();
                throwExcep = false;

            } catch (SQLException exception) {
                LOG.error("Unable to execute query : " + exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            LOG.error("Unable to execute query : " + exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.warn("Exception Closing the connection : " + e.toString());
            }
        }
        if (throwExcep) {
            throw new CerberusException(new MessageGeneral(MessageGeneralEnum.CANNOT_UPDATE_TABLE));
        }
    }

    @Override
    public List<TestCaseStep> getTestCaseStepUsingStepInParamter(String test, String testCase, int step) throws CerberusException {
        List<TestCaseStep> list = new ArrayList<TestCaseStep>();
        final String query = "SELECT * FROM testcasestep WHERE usestep='Y' AND usesteptest = ? AND usesteptestcase = ? AND usestepstep = ?";

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query);
            try {
                preStat.setString(1, test);
                preStat.setString(2, testCase);
                preStat.setInt(3, step);

                ResultSet resultSet = preStat.executeQuery();
                try {
                    while (resultSet.next()) {
                        list.add(loadFromResultSet(resultSet));
                    }
                } catch (SQLException exception) {
                    LOG.error("Unable to execute query : " + exception.toString());
                } finally {
                    resultSet.close();
                }
            } catch (SQLException exception) {
                LOG.error("Unable to execute query : " + exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            LOG.error("Unable to execute query : " + exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.warn("Exception Closing the connection : " + e.toString());
            }
        }
        return list;
    }

    @Override
    public List<TestCaseStep> getTestCaseStepUsingTestCaseInParamter(String test, String testCase) throws CerberusException {
        List<TestCaseStep> list = null;
        final String query = "SELECT * FROM testcasestep WHERE usestep='Y' AND usesteptest = ? AND usesteptestcase = ?";

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query);
            try {
                preStat.setString(1, test);
                preStat.setString(2, testCase);

                ResultSet resultSet = preStat.executeQuery();
                list = new ArrayList<TestCaseStep>();
                try {
                    while (resultSet.next()) {
                        list.add(loadFromResultSet(resultSet));
                    }
                } catch (SQLException exception) {
                    LOG.error("Unable to execute query : " + exception.toString());
                } finally {
                    resultSet.close();
                }
            } catch (SQLException exception) {
                LOG.error("Unable to execute query : " + exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            LOG.error("Unable to execute query : " + exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.warn("Exception Closing the connection : " + e.toString());
            }
        }
        return list;
    }

    @Override
    public List<TestCaseStep> getStepUsedAsLibraryInOtherTestCaseByApplication(String application) throws CerberusException {
        List<TestCaseStep> list = null;
        StringBuilder query = new StringBuilder();
        query.append("SELECT tcs.usesteptest, tcs.usesteptestcase,tcs.usestepstep,tcs.sort, tcs2.description FROM testcasestep tcs ");
        query.append("join testcase tc on tc.test=tcs.test and tc.testcase=tcs.testcase ");
        query.append("join testcasestep tcs2 on tcs.test=tcs2.test and tcs.testcase=tcs2.testcase and tcs.step=tcs2.step ");
        query.append("where tcs.usestep = 'Y' and tc.application = ?  ");
        query.append("group by tcs.usesteptest, tcs.usesteptestcase, tcs.usestepstep ");

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query.toString());
            try {
                preStat.setString(1, application);

                ResultSet resultSet = preStat.executeQuery();
                list = new ArrayList<TestCaseStep>();
                try {
                    while (resultSet.next()) {
                        String t = resultSet.getString("usesteptest");
                        String tc = resultSet.getString("usesteptestcase");
                        int s = resultSet.getInt("usestepstep");
                        int sort = resultSet.getInt("sort");
                        String description = resultSet.getString("description");
                        list.add(factoryTestCaseStep.create(t, tc, s, sort, null, null, null, null, description, null, null, null, 0, null));
                    }
                } catch (SQLException exception) {
                    LOG.error("Unable to execute query : " + exception.toString());
                } finally {
                    resultSet.close();
                }
            } catch (SQLException exception) {
                LOG.error("Unable to execute query : " + exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            LOG.error("Unable to execute query : " + exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.warn("Exception Closing the connection : " + e.toString());
            }
        }
        return list;

    }

    @Override
    public List<TestCaseStep> getStepLibraryBySystem(String system) throws CerberusException {
        List<TestCaseStep> list = null;
        StringBuilder query = new StringBuilder();
        query.append("SELECT tcs.test, tcs.testcase, tcs.step, tcs.sort, tcs.description, tc.description as tcdesc FROM testcasestep tcs ");
        query.append("join testcase tc on tc.test=tcs.test and tc.testcase=tcs.testcase ");
        query.append("join application app  on tc.application=app.application ");
        query.append("where tcs.inlibrary = 'Y' and app.system = ?  ");
        query.append("order by tcs.test, tcs.testcase, tcs.sort");

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query.toString());
            try {
                preStat.setString(1, system);

                ResultSet resultSet = preStat.executeQuery();
                list = new ArrayList<TestCaseStep>();
                try {
                    while (resultSet.next()) {
                        String t = resultSet.getString("test");
                        String tc = resultSet.getString("testcase");
                        String tcdesc = resultSet.getString("tcdesc");
                        int s = resultSet.getInt("step");
                        int sort = resultSet.getInt("sort");
                        String description = resultSet.getString("description");
                        TestCaseStep tcs= factoryTestCaseStep.create(t, tc, s, sort, null, null, null, null, description, null, null, null, 0, null);
                        TestCase tcObj = factoryTestCase.create(t,tc,tcdesc);
                        tcs.setTestCaseObj(tcObj);
                        list.add(tcs);
                    }
                } catch (SQLException exception) {
                    LOG.error("Unable to execute query : " + exception.toString());
                } finally {
                    resultSet.close();
                }
            } catch (SQLException exception) {
                LOG.error("Unable to execute query : " + exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            LOG.error("Unable to execute query : " + exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.warn("Exception Closing the connection : " + e.toString());
            }
        }
        return list;
    }

    @Override
    public List<TestCaseStep> getStepLibraryBySystemTest(String system, String test) throws CerberusException {
        List<TestCaseStep> list = null;
        StringBuilder query = new StringBuilder();
        query.append("SELECT tcs.test, tcs.testcase,tcs.step, tcs.sort, tcs.description, tc.description as tcdesc, tc.application as tcapp FROM testcasestep tcs ");
        query.append("join testcase tc on tc.test=tcs.test and tc.testcase=tcs.testcase ");
        query.append("join application app  on tc.application=app.application ");
        query.append("where tcs.inlibrary = 'Y' ");
        if (system != null) {
            query.append("and app.system = ? ");
        }
        if (test != null) {
            query.append("and tcs.test = ? ");
        }
        query.append("order by tcs.test, tcs.testcase, tcs.sort");

        // Debug message on SQL.
        if (LOG.isDebugEnabled()) {
            LOG.debug("SQL : " + query.toString());
            LOG.debug("SQL.param.system : " + system);
            LOG.debug("SQL.param.test : " + test);
        }

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query.toString());
            try {
                int i = 1;
                if (system != null) {
                    preStat.setString(i++, system);
                }
                if (test != null) {
                    preStat.setString(i++, test);
                }

                ResultSet resultSet = preStat.executeQuery();
                list = new ArrayList<TestCaseStep>();
                try {
                    while (resultSet.next()) {
                        String t = resultSet.getString("test");
                        String tc = resultSet.getString("testcase");
                        int s = resultSet.getInt("step");
                        int sort = resultSet.getInt("sort");
                        String description = resultSet.getString("description");
                        String tcdesc = resultSet.getString("tcdesc");
                        TestCase tcToAdd = factoryTestCase.create(t, tc, tcdesc);
                        tcToAdd.setApplication(resultSet.getString("tcapp"));
                        TestCaseStep tcsToAdd = factoryTestCaseStep.create(t, tc, s, sort, null, null, null, null, description, null, null, null, 0, null);
                        tcsToAdd.setTestCaseObj(tcToAdd);
                        list.add(tcsToAdd);
                    }
                } catch (SQLException exception) {
                    LOG.error("Unable to execute query : " + exception.toString());
                } finally {
                    resultSet.close();
                }
            } catch (SQLException exception) {
                LOG.error("Unable to execute query : " + exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            LOG.error("Unable to execute query : " + exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.warn("Exception Closing the connection : " + e.toString());
            }
        }
        return list;
    }

    @Override
    public List<TestCaseStep> getStepLibraryBySystemTestTestCase(String system, String test, String testCase) throws CerberusException {
        List<TestCaseStep> list = null;
        StringBuilder query = new StringBuilder();
        query.append("SELECT tcs.test, tcs.testcase,tcs.step, tcs.sort, tcs.description FROM testcasestep tcs ");
        query.append("join testcase tc on tc.test=tcs.test and tc.testcase=tcs.testcase ");
        query.append("join application app  on tc.application=app.application ");
        query.append("where tcs.inlibrary = 'Y' ");
        if (system != null) {
            query.append("and app.system = ? ");
        }
        if (test != null) {
            query.append("and tcs.test = ? ");
        }
        if (testCase != null) {
            query.append("and tcs.testcase = ? ");
        }
        query.append("order by tcs.test, tcs.testcase, tcs.sort");

        // Debug message on SQL.
        if (LOG.isDebugEnabled()) {
            LOG.debug("SQL : " + query.toString());
            LOG.debug("SQL.param.system : " + system);
            LOG.debug("SQL.param.test : " + test);
            LOG.debug("SQL.param.testcase : " + testCase);
        }

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query.toString());
            try {
                int i = 1;
                if (system != null) {
                    preStat.setString(i++, system);
                }
                if (test != null) {
                    preStat.setString(i++, test);
                }
                if (testCase != null) {
                    preStat.setString(i++, testCase);
                }

                ResultSet resultSet = preStat.executeQuery();
                list = new ArrayList<TestCaseStep>();
                try {
                    while (resultSet.next()) {
                        String t = resultSet.getString("test");
                        String tc = resultSet.getString("testcase");
                        int s = resultSet.getInt("step");
                        int sort = resultSet.getInt("sort");
                        String description = resultSet.getString("description");
                        list.add(factoryTestCaseStep.create(t, tc, s, sort, null, null, null, null, description, null, null, null, 0, null));
                    }
                } catch (SQLException exception) {
                    LOG.error("Unable to execute query : " + exception.toString());
                } finally {
                    resultSet.close();
                }
            } catch (SQLException exception) {
                LOG.error("Unable to execute query : " + exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            LOG.error("Unable to execute query : " + exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.warn("Exception Closing the connection : " + e.toString());
            }
        }
        return list;
    }

    @Override
    public AnswerList readByTestTestCase(String test, String testcase) {
        AnswerList response = new AnswerList();
        MessageEvent msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_UNEXPECTED);
        msg.setDescription(msg.getDescription().replace("%DESCRIPTION%", ""));
        List<TestCaseStep> stepList = new ArrayList<TestCaseStep>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT tcs.*, CASE WHEN tcs1.test + tcs1.testcase + tcs1.step is NULL THEN 0 ELSE 1 END as isStepInUseByOtherTestCase FROM testcasestep tcs LEFT JOIN testcasestep tcs1 ON tcs1.useStep = 'Y' AND tcs1.useStepTest = ? AND tcs1.useStepTestCase = ? AND tcs1.useStepStep = tcs.step WHERE tcs.test = ? AND tcs.testcase = ? GROUP BY tcs.test, tcs.testcase, tcs.step ORDER BY tcs.sort");

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query.toString());
            try {
                preStat.setString(1, test);
                preStat.setString(2, testcase);
                preStat.setString(3, test);
                preStat.setString(4, testcase);
                ResultSet resultSet = preStat.executeQuery();
                try {
                    //gets the data
                    while (resultSet.next()) {
                        stepList.add(this.loadFromResultSet(resultSet));
                    }

                    //get the total number of rows
                    resultSet = preStat.executeQuery("SELECT FOUND_ROWS()");
                    int nrTotalRows = 0;

                    if (resultSet != null && resultSet.next()) {
                        nrTotalRows = resultSet.getInt(1);
                    }

                    if (stepList.size() >= MAX_ROW_SELECTED) { // Result of SQl was limited by MAX_ROW_SELECTED constrain. That means that we may miss some lines in the resultList.
                        LOG.error("Partial Result in the query.");
                        msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_WARNING_PARTIAL_RESULT);
                        msg.setDescription(msg.getDescription().replace("%DESCRIPTION%", "Maximum row reached : " + MAX_ROW_SELECTED));
                        response = new AnswerList(stepList, stepList.size());
                    } else if (stepList.size() <= 0) {
                        msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_NO_DATA_FOUND);
                        response = new AnswerList(stepList, stepList.size());
                    } else {
                        msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_OK);
                        msg.setDescription(msg.getDescription().replace("%ITEM%", OBJECT_NAME).replace("%OPERATION%", "SELECT"));
                        response = new AnswerList(stepList, stepList.size());
                    }

                } catch (SQLException exception) {
                    LOG.error("Unable to execute query : " + exception.toString());
                    msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_UNEXPECTED);
                    msg.setDescription(msg.getDescription().replace("%DESCRIPTION%", "Unable to retrieve the list of entries!"));

                } finally {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                }

            } catch (SQLException exception) {
                LOG.error("Unable to execute query : " + exception.toString());
                msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_UNEXPECTED);
                msg.setDescription(msg.getDescription().replace("%DESCRIPTION%", "Unable to retrieve the list of entries!"));
            } finally {
                if (preStat != null) {
                    preStat.close();
                }
            }

        } catch (SQLException exception) {
            LOG.error("Unable to execute query : " + exception.toString());
            msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_UNEXPECTED);
            msg.setDescription(msg.getDescription().replace("%DESCRIPTION%", "Unable to retrieve the list of entries!"));
        } finally {
            try {
                if (!this.databaseSpring.isOnTransaction()) {
                    if (connection != null) {
                        connection.close();
                    }
                }
            } catch (SQLException exception) {
                LOG.warn("Unable to close connection : " + exception.toString());
            }
        }

        response.setResultMessage(msg);
        return response;
    }

    @Override
    public AnswerList readByLibraryUsed(String test, String testcase, int step) {
        AnswerList response = new AnswerList();
        MessageEvent msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_UNEXPECTED);
        msg.setDescription(msg.getDescription().replace("%DESCRIPTION%", ""));
        List<TestCaseStep> stepList = new ArrayList<TestCaseStep>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM testcasestep tcs WHERE tcs.useStep = 'Y' AND tcs.useStepTest = ? AND tcs.useStepTestCase = ? AND tcs.useStepStep = ?");

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query.toString());
            try {
                preStat.setString(1, test);
                preStat.setString(2, testcase);
                preStat.setInt(3, step);
                ResultSet resultSet = preStat.executeQuery();
                try {
                    //gets the data
                    while (resultSet.next()) {
                        stepList.add(this.loadFromResultSet(resultSet));
                    }

                    //get the total number of rows
                    resultSet = preStat.executeQuery("SELECT FOUND_ROWS()");
                    int nrTotalRows = 0;

                    if (resultSet != null && resultSet.next()) {
                        nrTotalRows = resultSet.getInt(1);
                    }

                    if (stepList.size() >= MAX_ROW_SELECTED) { // Result of SQl was limited by MAX_ROW_SELECTED constrain. That means that we may miss some lines in the resultList.
                        LOG.error("Partial Result in the query.");
                        msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_WARNING_PARTIAL_RESULT);
                        msg.setDescription(msg.getDescription().replace("%DESCRIPTION%", "Maximum row reached : " + MAX_ROW_SELECTED));
                        response = new AnswerList(stepList, stepList.size());
                    } else if (stepList.size() <= 0) {
                        msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_NO_DATA_FOUND);
                        response = new AnswerList(stepList, stepList.size());
                    } else {
                        msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_OK);
                        msg.setDescription(msg.getDescription().replace("%ITEM%", OBJECT_NAME).replace("%OPERATION%", "SELECT"));
                        response = new AnswerList(stepList, stepList.size());
                    }

                } catch (SQLException exception) {
                    LOG.error("Unable to execute query : " + exception.toString());
                    msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_UNEXPECTED);
                    msg.setDescription(msg.getDescription().replace("%DESCRIPTION%", "Unable to retrieve the list of entries!"));

                } finally {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                }

            } catch (SQLException exception) {
                LOG.error("Unable to execute query : " + exception.toString());
                msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_UNEXPECTED);
                msg.setDescription(msg.getDescription().replace("%DESCRIPTION%", "Unable to retrieve the list of entries!"));
            } finally {
                if (preStat != null) {
                    preStat.close();
                }
            }

        } catch (SQLException exception) {
            LOG.error("Unable to execute query : " + exception.toString());
            msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_UNEXPECTED);
            msg.setDescription(msg.getDescription().replace("%DESCRIPTION%", "Unable to retrieve the list of entries!"));
        } finally {
            try {
                if (!this.databaseSpring.isOnTransaction()) {
                    if (connection != null) {
                        connection.close();
                    }
                }
            } catch (SQLException exception) {
                LOG.warn("Unable to close connection : " + exception.toString());
            }
        }

        response.setResultMessage(msg);
        return response;
    }

    @Override
    public Answer create(TestCaseStep testCaseStep) {
        Answer ans = new Answer();
        MessageEvent msg = null;
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO `testcasestep` (`Test`,`TestCase`,`Step`,`Sort`,`Description`,`useStep`,`useStepTest`,`useStepTestCase`,`useStepStep`, `inLibrary`, `loop`, `conditionOper`, `conditionVal1`, `conditionVal2`) ");
        query.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

        // Debug message on SQL.
        if (LOG.isDebugEnabled()) {
            LOG.debug("SQL : " + query.toString());
        }
        
        try (Connection connection = databaseSpring.connect();
                PreparedStatement preStat = connection.prepareStatement(query.toString())) {
            // Prepare and execute query
            int i=1;
            preStat.setString(i++, testCaseStep.getTest());
            preStat.setString(i++, testCaseStep.getTestCase());
            preStat.setInt(i++, testCaseStep.getStep());
            preStat.setInt(i++, testCaseStep.getSort());
            preStat.setString(i++, testCaseStep.getDescription());
            preStat.setString(i++, testCaseStep.getUseStep() == null ? "N" : testCaseStep.getUseStep());
            preStat.setString(i++, testCaseStep.getUseStepTest() == null ? "" : testCaseStep.getUseStepTest());
            preStat.setString(i++, testCaseStep.getUseStepTestCase() == null ? "" : testCaseStep.getUseStepTestCase());
            preStat.setInt(i++, testCaseStep.getUseStepStep() == null ? 0 : testCaseStep.getUseStepStep());
            preStat.setString(i++, testCaseStep.getInLibrary() == null ? "N" : testCaseStep.getInLibrary());
            preStat.setString(i++, testCaseStep.getLoop()== null ? "" : testCaseStep.getLoop());
            preStat.setString(i++, testCaseStep.getConditionOper()== null ? "" : testCaseStep.getConditionOper());
            preStat.setString(i++, testCaseStep.getConditionVal1()== null ? "" : testCaseStep.getConditionVal1());
            preStat.setString(i++, testCaseStep.getConditionVal2()== null ? "" : testCaseStep.getConditionVal2());
            preStat.executeUpdate();

            // Set the final message
            msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_OK).resolveDescription("ITEM", OBJECT_NAME)
                    .resolveDescription("OPERATION", "CREATE");
        } catch (Exception e) {
            LOG.warn("Unable to create TestCaseStep: " + e.getMessage());
            msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_UNEXPECTED).resolveDescription("DESCRIPTION",
                    e.toString());
        } finally {
            ans.setResultMessage(msg);
        }

        return ans;
    }

    private TestCaseStep loadFromResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }

        String test = resultSet.getString("test") == null ? "" : resultSet.getString("test");
        String testcase = resultSet.getString("testcase") == null ? "" : resultSet.getString("testcase");
        int step = resultSet.getInt("step") == 0 ? 0 : resultSet.getInt("step");
        int sort = resultSet.getInt("sort");
        String loop = resultSet.getString("loop") == null ? "" : resultSet.getString("loop");
        String conditionOper = resultSet.getString("conditionOper") == null ? "" : resultSet.getString("conditionOper");
        String conditionVal1 = resultSet.getString("conditionVal1") == null ? "" : resultSet.getString("conditionVal1");
        String conditionVal2 = resultSet.getString("conditionVal2") == null ? "" : resultSet.getString("conditionVal2");
        String description = resultSet.getString("description") == null ? "" : resultSet.getString("description");
        String useStep = resultSet.getString("useStep") == null ? "" : resultSet.getString("useStep");
        String useStepTest = resultSet.getString("useStepTest") == null ? "" : resultSet.getString("useStepTest");
        String useStepTestCase = resultSet.getString("useStepTestCase") == null ? "" : resultSet.getString("useStepTestCase");
        int useStepStep = resultSet.getInt("useStepStep") == 0 ? 0 : resultSet.getInt("useStepStep");
        String inLibrary = resultSet.getString("inLibrary") == null ? "" : resultSet.getString("inLibrary");

        TestCaseStep tcs = factoryTestCaseStep.create(test, testcase, step, sort, loop, conditionOper, conditionVal1, conditionVal2, description, useStep, useStepTest, useStepTestCase, useStepStep, inLibrary);

        try {
            resultSet.findColumn("isStepInUseByOtherTestCase");
            boolean isStepInUseByOtherTestCase = resultSet.getInt("isStepInUseByOtherTestCase") == 1 ? true : false;
            tcs.setIsStepInUseByOtherTestCase(isStepInUseByOtherTestCase);
        } catch (SQLException sqlex)
        {
            // That means there is not this column, so nothing to do
        }

        return tcs;

    }
}
