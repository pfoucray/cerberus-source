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

import org.cerberus.crud.entity.CountryEnvironmentParameters;
import org.cerberus.exception.CerberusException;
import org.cerberus.util.answer.Answer;
import org.cerberus.util.answer.AnswerItem;
import org.cerberus.util.answer.AnswerList;
import org.cerberus.util.observe.Observable;

/**
 *
 * @author bcivel
 */
public interface ICountryEnvironmentParametersService extends Observable<CountryEnvironmentParameters.Key, CountryEnvironmentParameters> {

    /**
     *
     * @param system
     * @param country
     * @param environment
     * @param application
     * @return
     */
    AnswerItem<CountryEnvironmentParameters> readByKey(String system, String country, String environment, String application);

    AnswerItem<Integer> readPoolSizeByKey(String system, String country, String environment, String application);

    /**
     *
     * @param countryEnvironmentParameter
     * @return
     * @throws CerberusException
     */
    List<CountryEnvironmentParameters> findCountryEnvironmentParametersByCriteria(CountryEnvironmentParameters countryEnvironmentParameter) throws CerberusException;

    /**
     *
     * @param system
     * @param country
     * @param environment
     * @param application
     * @param start
     * @param amount
     * @param column
     * @param dir
     * @param searchTerm
     * @param individualSearch
     * @return
     */
    public AnswerList readByVariousByCriteria(String system, String country, String environment, String application, int start, int amount, String column, String dir, String searchTerm, String individualSearch);

    /**
     *
     * @param system
     * @param country
     * @param environment
     * @param application
     * @return
     */
    public AnswerList readByVarious(String system, String country, String environment, String application);

    /**
     *
     * @param object
     * @return
     */
    public Answer update(CountryEnvironmentParameters object);

    /**
     *
     * @param object
     * @return
     */
    public Answer delete(CountryEnvironmentParameters object);

    /**
     *
     * @param object
     * @return
     */
    public Answer create(CountryEnvironmentParameters object);

    /**
     *
     * @param objectList
     * @return
     */
    public Answer createList(List<CountryEnvironmentParameters> objectList);

    /**
     *
     * @param objectList
     * @return
     */
    public Answer deleteList(List<CountryEnvironmentParameters> objectList);

    /**
     * Update all CountryEnvironmentDatabase from the sourceList to the
     * perimeter of system, country and environment list. All existing databases
     * from newList will be updated, the new ones added and missing ones
     * deleted.
     *
     * @param system
     * @param country
     * @param environement
     * @param newList
     * @return
     */
    public Answer compareListAndUpdateInsertDeleteElements(String system, String country, String environement, List<CountryEnvironmentParameters> newList);

    /**
     * Update all CountryEnvironmentDatabase from the sourceList to the
     * perimeter of the application list. All existing databases from newList
     * will be updated, the new ones added and missing ones deleted.
     *
     * @param system
     * @param application
     * @param newList
     * @return
     */
    public Answer compareListAndUpdateInsertDeleteElements(String system, String application, List<CountryEnvironmentParameters> newList);

    /**
     *
     * @param answerItem
     * @return
     * @throws CerberusException
     */
    CountryEnvironmentParameters convert(AnswerItem answerItem) throws CerberusException;

    /**
     *
     * @param answerList
     * @return
     * @throws CerberusException
     */
    List<CountryEnvironmentParameters> convert(AnswerList answerList) throws CerberusException;

    /**
     *
     * @param answer
     * @throws CerberusException
     */
    void convert(Answer answer) throws CerberusException;

    int defaultPoolSize();

}
