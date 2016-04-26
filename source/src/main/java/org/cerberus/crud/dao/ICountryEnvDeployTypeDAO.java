/**
 * Cerberus  Copyright (C) 2013 - 2016  vertigo17
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
package org.cerberus.crud.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.cerberus.crud.entity.CountryEnvDeployType;
import org.cerberus.util.answer.Answer;
import org.cerberus.util.answer.AnswerList;

public interface ICountryEnvDeployTypeDAO {

    /**
     *
     * @param system
     * @param country
     * @param env
     * @param deploy
     * @return
     */
    List<String> findJenkinsAgentByKey(String system, String country, String env, String deploy);

    /**
     *
     * @param system
     * @param country
     * @param environment
     * @param startPosition
     * @param length
     * @param columnName
     * @param sort
     * @param searchParameter
     * @param string
     * @return
     */
    AnswerList readByVariousByCriteria(String system, String country, String environment, int startPosition, int length, String columnName, String sort, String searchParameter, String string);

    /**
     *
     * @param object
     * @return
     */
    Answer create(CountryEnvDeployType object);

    /**
     *
     * @param object
     * @return
     */
    Answer delete(CountryEnvDeployType object);

    /**
     *
     * @param object
     * @return
     */
    Answer update(CountryEnvDeployType object);

    /**
     * Uses data of ResultSet to create object {@link Application}
     *
     * @param rs ResultSet relative to select from table countryenvlink
     * @return object {@link CountryEnvLink}
     * @throws SQLException when trying to get value from
     * {@link java.sql.ResultSet#getString(String)}
     * @see FactoryCountryEnvLink
     */
    CountryEnvDeployType loadFromResultSet(ResultSet rs) throws SQLException;

}
