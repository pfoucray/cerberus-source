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
package org.cerberus.service.sikuli.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.cerberus.engine.entity.MessageEvent;
import org.cerberus.engine.entity.Session;
import org.cerberus.crud.entity.TestCaseStepAction;
import org.cerberus.crud.entity.TestCaseStepActionControl;
import org.cerberus.enums.MessageEventEnum;
import org.cerberus.service.sikuli.ISikuliService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 *
 * @author bcivel
 */
@Service
public class SikuliService implements ISikuliService {

    private JSONObject generatePostParameters(String action, String locator, String text, long defaultWait) throws JSONException, IOException, MalformedURLException, MimeTypeException {
        JSONObject result = new JSONObject();
        String picture = "";
        String extension = "";
        /**
         * Get Picture from URL and convert to Base64
         */
        if (locator!=null){
        URL url = new URL(locator);
        URLConnection connection = url.openConnection();
        
        InputStream istream = new BufferedInputStream(connection.getInputStream());
        
        /**
         * Get the MimeType and the extension
         */
        String mimeType = URLConnection.guessContentTypeFromStream(istream);
        MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
        MimeType mt = allTypes.forName(mimeType);
        extension = mt.getExtension(); 
  
        /**
         * Encode in Base64
         */
        byte[] bytes = IOUtils.toByteArray(istream);
        picture = Base64.encodeBase64URLSafeString(bytes);
        }
        /**
         * Build JSONObject with parameters action : Action expected to be done
         * by Sikuli picture : Picture in Base64 format text : Text to type
         * defaultWait : Timeout for the action pictureExtension : Extension for
         * Base64 decoding
         */
        result.put("action", action);
        result.put("picture", picture);
        result.put("text", text);
        result.put("defaultWait", defaultWait);
        result.put("pictureExtension", extension);
        return result;
    }

    @Override
    public MessageEvent doSikuliAction(Session session, String action, String locator, String text) {
        URL url;
        String urlToConnect = "";
        try {
            urlToConnect = "http://" + session.getHost() + ":" + session.getPort() + "/extra/ExecuteSikuliAction";
            /**
             * Connect to ExecuteSikuliAction Servlet Through SeleniumServer
             */
            url = new URL(urlToConnect);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            JSONObject postParameters = generatePostParameters(action, locator, text, session.getCerberus_selenium_wait_element());
            connection.setDoOutput(true);

            // Send post request
            PrintStream os = new PrintStream(connection.getOutputStream());
            os.println(postParameters.toString());
            os.println("|ENDS|");

            if (connection == null || connection.getResponseCode() != 200) {
                return new MessageEvent(MessageEventEnum.ACTION_FAILED_SIKULI_SERVER_NOT_REACHABLE);
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            /**
             * Wait here until receiving |ENDR| String
             */
            while (!(inputLine = in.readLine()).equals("|ENDR|")) {
                response.append(inputLine);
            }

            /**
             * If response contains Failed, return failed message
             */
            if (response.toString().contains("Failed")) {
                if (!action.equals("verifyElementPresent")){
                MessageEvent mes = new MessageEvent(MessageEventEnum.ACTION_FAILED_SIKULI_ELEMENT_NOT_FOUND);
                mes.setDescription(mes.getDescription().replace("%ACTION%", action));
                mes.setDescription(mes.getDescription().replace("%ELEMENT%", locator));
                return mes;
                } else {
                MessageEvent mes = new MessageEvent(MessageEventEnum.CONTROL_FAILED_PRESENT);
                mes.setDescription(mes.getDescription().replace("%STRING1%", locator));
                return mes;
                }
            }
            in.close();
            os.close();
        } catch (MalformedURLException ex) {
            Logger.getLogger(SikuliService.class.getName()).log(Level.FATAL, ex);
            MessageEvent mes = new MessageEvent(MessageEventEnum.ACTION_FAILED_SIKULI_SERVER_NOT_REACHABLE);
            mes.setDescription(mes.getDescription().replace("%URL%", urlToConnect));
            return mes;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SikuliService.class.getName()).log(Level.FATAL, ex);
            MessageEvent mes = new MessageEvent(MessageEventEnum.ACTION_FAILED_SIKULI_FILE_NOT_FOUND);
            mes.setDescription(mes.getDescription().replace("%FILE%", locator));
            return mes;
        } catch (IOException ex) {
            Logger.getLogger(SikuliService.class.getName()).log(Level.FATAL, ex);
            return new MessageEvent(MessageEventEnum.ACTION_FAILED);
        } catch (JSONException ex) {
            Logger.getLogger(SikuliService.class.getName()).log(Level.FATAL, ex);
            return new MessageEvent(MessageEventEnum.ACTION_FAILED);
        } catch (MimeTypeException ex) {
            Logger.getLogger(SikuliService.class.getName()).log(Level.FATAL, ex);
            return new MessageEvent(MessageEventEnum.ACTION_FAILED);
        } finally {

        }
        return getResultMessage(action, locator, text);
    }

    private MessageEvent getResultMessage(String action, String locator, String text) {
        MessageEvent message = null;
        if (action.equals(TestCaseStepAction.ACTION_CLICK)) {
            message = new MessageEvent(MessageEventEnum.ACTION_SUCCESS_CLICK);
            message.setDescription(message.getDescription().replace("%ELEMENT%", locator));
        } else if (action.equals( TestCaseStepAction.ACTION_RIGHTCLICK)) {
            message = new MessageEvent(MessageEventEnum.ACTION_SUCCESS_RIGHTCLICK);
            message.setDescription(message.getDescription().replace("%ELEMENT%", locator));
        } else if (action.equals( TestCaseStepAction.ACTION_DOUBLECLICK)) {
            message = new MessageEvent(MessageEventEnum.ACTION_SUCCESS_DOUBLECLICK);
            message.setDescription(message.getDescription().replace("%ELEMENT%", locator));
        } else if (action.equals( TestCaseStepAction.ACTION_TYPE)) {
            message = new MessageEvent(MessageEventEnum.ACTION_SUCCESS_TYPE);
            message.setDescription(message.getDescription().replace("%ELEMENT%", locator));
            message.setDescription(message.getDescription().replace("%DATA%", text));
        } else if (action.equals( TestCaseStepAction.ACTION_MOUSEOVER)) {
            message = new MessageEvent(MessageEventEnum.ACTION_SUCCESS_MOUSEOVER);
            message.setDescription(message.getDescription().replace("%ELEMENT%", locator));
        } else if (action.equals( TestCaseStepAction.ACTION_KEYPRESS)) {
            message = new MessageEvent(MessageEventEnum.ACTION_SUCCESS_KEYPRESS);
            message.setDescription(message.getDescription().replace("%ELEMENT%", locator));
        } else if (action.equals( TestCaseStepAction.ACTION_WAIT)) {
            message = new MessageEvent(MessageEventEnum.ACTION_SUCCESS_WAIT_ELEMENT);
            message.setDescription(message.getDescription().replace("%ELEMENT%", locator));
        } else if (action.equals(TestCaseStepActionControl.CONTROL_VERIFYELEMENTPRESENT)) {
            message = new MessageEvent(MessageEventEnum.CONTROL_SUCCESS_PRESENT);
            message.setDescription(message.getDescription().replace("%STRING1%", locator));
        }
        return message;

    }

}
