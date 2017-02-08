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
package org.cerberus.crud.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cerberus.crud.dao.ICountryEnvParamDAO;
import org.cerberus.crud.entity.CountryEnvParam;
import org.cerberus.crud.entity.CountryEnvironmentParameters;
import org.cerberus.engine.entity.MessageGeneral;
import org.cerberus.exception.CerberusException;
import org.cerberus.crud.factory.IFactoryCountryEnvParam;
import org.cerberus.crud.service.ICountryEnvParamService;
import org.cerberus.enums.MessageEventEnum;
import org.cerberus.enums.MessageGeneralEnum;
import org.cerberus.util.answer.Answer;
import org.cerberus.util.answer.AnswerItem;
import org.cerberus.util.answer.AnswerList;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.cerberus.crud.service.ICountryEnvironmentParametersService;
import org.cerberus.crud.factory.IFactoryCountryEnvironmentParameters;

/**
 *
 * @author bcivel
 */
@Service
public class CountryEnvParamService implements ICountryEnvParamService {

    @Autowired
    ICountryEnvParamDAO countryEnvParamDao;
    @Autowired
    IFactoryCountryEnvParam countryEnvParamFactory;
    @Autowired
    IFactoryCountryEnvironmentParameters countryEnvironmentParametersFactory;
    @Autowired
    ICountryEnvironmentParametersService countryEnvironmentParametersService;

    @Override
    public List<CountryEnvParam> findCountryEnvParamByCriteria(CountryEnvParam countryEnvParam) throws CerberusException {
        return countryEnvParamDao.findCountryEnvParamByCriteria(countryEnvParam);
    }

    @Override
    public List<JSONObject> findActiveEnvironmentBySystemCountryApplication(String system, String country, String application) throws CerberusException {
        List<JSONObject> result = new ArrayList();
        CountryEnvParam countryEnvParam = countryEnvParamFactory.create(system, country, true);
        CountryEnvironmentParameters countryEnvironmentParameters = countryEnvironmentParametersFactory.create(system, country, null, application, null, null, null, null, null, null, null, null, countryEnvironmentParametersService.defaultPoolSize());

        List<CountryEnvironmentParameters> ceaList = countryEnvironmentParametersService.findCountryEnvironmentParametersByCriteria(countryEnvironmentParameters);
        List<CountryEnvParam> ceList = this.findCountryEnvParamByCriteria(countryEnvParam);

        try {
            for (CountryEnvironmentParameters cea : ceaList) {
                for (CountryEnvParam ce : ceList) {
                    if (cea.getEnvironment().equals(ce.getEnvironment())) {

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("environment", ce.getEnvironment());
                        jsonObject.put("build", ce.getBuild());
                        jsonObject.put("revision", ce.getRevision());
                        jsonObject.put("ip", cea.getIp());
                        jsonObject.put("url", cea.getUrl());
                        result.add(jsonObject);

                    }
                }
            }
        } catch (JSONException ex) {
            Logger.getLogger(CountryEnvParamService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public AnswerItem readByKey(String system, String country, String environment) {
        return countryEnvParamDao.readByKey(system, country, environment);
    }

    @Override
    public AnswerList readActiveBySystem(String system) {
        return countryEnvParamDao.readActiveBySystem(system);
    }

    @Override
    public AnswerList readByCriteria(int start, int amount, String colName, String dir, String searchTerm, String individualSearch) {
        return countryEnvParamDao.readByCriteria(start, amount, colName, dir, searchTerm, individualSearch);
    }

    @Override
    public AnswerList readByVariousByCriteria(String system, String country, String environment, String build, String revision, String active, String envGp, int start, int amount, String colName, String dir, String searchTerm, Map<String, List<String>> individualSearch) {
        return countryEnvParamDao.readByVariousByCriteria(system, country, environment, build, revision, active, envGp, start, amount, colName, dir, searchTerm, individualSearch);
    }

    @Override
    public AnswerList readByVarious(String system, String country, String environment, String build, String revision, String active) {
        return countryEnvParamDao.readByVariousByCriteria(system, country, environment, build, revision, active, null, 0, 0, null, null, null, null);
    }

    @Override
    public AnswerList readDistinctEnvironmentByVarious(String system, String country, String environment, String build, String revision, String active) {
        return countryEnvParamDao.readDistinctEnvironmentByVariousByCriteria(system, country, environment, build, revision, active, null, 0, 0, null, null, null, null);
    }

    @Override
    public boolean exist(String system, String country, String environment) {
        AnswerItem objectAnswer = readByKey(system, country, environment);
        return (objectAnswer.isCodeEquals(MessageEventEnum.DATA_OPERATION_OK.getCode())) && (objectAnswer.getItem() != null); // Call was successfull and object was found.
    }

    @Override
    public Answer create(CountryEnvParam cep) {
        return countryEnvParamDao.create(cep);
    }

    @Override
    public Answer delete(CountryEnvParam cep) {
        return countryEnvParamDao.delete(cep);
    }

    @Override
    public Answer update(CountryEnvParam cep) {
        return countryEnvParamDao.update(cep);
    }

    @Override
    public CountryEnvParam convert(AnswerItem answerItem) throws CerberusException {
        if (answerItem.isCodeEquals(MessageEventEnum.DATA_OPERATION_OK.getCode())) {
            //if the service returns an OK message then we can get the item
            return (CountryEnvParam) answerItem.getItem();
        }
        throw new CerberusException(new MessageGeneral(MessageGeneralEnum.DATA_OPERATION_ERROR));
    }

    @Override
    public List<CountryEnvParam> convert(AnswerList answerList) throws CerberusException {
        if (answerList.isCodeEquals(MessageEventEnum.DATA_OPERATION_OK.getCode())) {
            //if the service returns an OK message then we can get the item
            return (List<CountryEnvParam>) answerList.getDataList();
        }
        throw new CerberusException(new MessageGeneral(MessageGeneralEnum.DATA_OPERATION_ERROR));
    }

    @Override
    public void convert(Answer answer) throws CerberusException {
        if (answer.isCodeEquals(MessageEventEnum.DATA_OPERATION_OK.getCode())) {
            //if the service returns an OK message then we can get the item
            return;
        }
        throw new CerberusException(new MessageGeneral(MessageGeneralEnum.DATA_OPERATION_ERROR));
    }

    @Override
    public AnswerList<List<String>> readDistinctValuesByCriteria(String system, String searchParameter, Map<String, List<String>> individualSearch, String columnName) {
        return countryEnvParamDao.readDistinctValuesByCriteria(system, searchParameter, individualSearch, columnName);
    }
}
