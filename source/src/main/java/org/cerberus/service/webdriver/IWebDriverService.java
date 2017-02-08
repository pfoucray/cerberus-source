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
package org.cerberus.service.webdriver;

import java.awt.image.BufferedImage;
import java.util.List;
import org.cerberus.engine.entity.Identifier;
import org.cerberus.engine.entity.MessageEvent;
import org.cerberus.engine.entity.Session;
import org.cerberus.exception.CerberusEventException;

/**
 *
 * @author bcivel
 */
public interface IWebDriverService {

    String getValueFromHTMLVisible(Session session, Identifier identifier);

    String getValueFromHTML(Session session, Identifier identifier);

    String getAlertText(Session session);

    String getValueFromJS(Session session, String script);

    boolean isElementPresent(Session session, Identifier identifier);

    boolean isElementNotPresent(Session session, Identifier identifier);

    boolean isElementVisible(Session session, Identifier identifier);

    boolean isElementNotVisible(Session session, Identifier identifier);

    boolean isElementInElement(Session session, Identifier identifier, Identifier childIdentifier);

    boolean isElementNotClickable(Session session, Identifier identifier);

    boolean isElementClickable(Session session, Identifier identifier);

    String getPageSource(Session session);

    String getTitle(Session session);

    /**
     *
     * Method return a string with the right part of the URL in order to be
     * agnostic of the environment. ex :
     * http://redoute.com/mypathlevel1/mypathlevel2/file.aspx will return
     * /mypathlevel1/mypathlevel2/file.aspx
     *
     * @param session
     * @param url
     * @return string with the right part of the URL in order to be agnostic of
     * the environment.
     * @throws org.cerberus.exception.CerberusEventException in case the URL
     * does not contain the host of the application beeing tested. That could
     * happen if the application redirect to a different host during the
     * testcase execution.
     */
    String getCurrentUrl(Session session, String url) throws CerberusEventException;

    String getAttributeFromHtml(Session session, Identifier identifier, String attribute);

    String getFromCookie(Session session, String cookieName, String cookieParameter);

    BufferedImage takeScreenShot(Session session);

    List<String> getSeleniumLog(Session session);

    MessageEvent doSeleniumActionClick(Session session, Identifier identifier, boolean waitForVisibility, boolean waitForClickability);

    MessageEvent doSeleniumActionMouseDown(Session session, Identifier identifier);

    MessageEvent doSeleniumActionMouseUp(Session session, Identifier identifier);

    MessageEvent doSeleniumActionSwitchToWindow(Session session, Identifier identifier);

    MessageEvent doSeleniumActionManageDialog(Session session, Identifier identifier);

    MessageEvent doSeleniumActionDoubleClick(Session session, Identifier identifier, boolean waitForVisibility, boolean waitForClickability);

    MessageEvent doSeleniumActionType(Session session, Identifier identifier, String property, String propertyName);

    MessageEvent doSeleniumActionMouseOver(Session session, Identifier identifier);

    MessageEvent doSeleniumActionWait(Session session, Identifier identifier);

    MessageEvent doSeleniumActionKeyPress(Session session, Identifier identifier, String property);

    MessageEvent doSeleniumActionOpenURL(Session session, String host, Identifier identifier, boolean withBase);

    MessageEvent doSeleniumActionSelect(Session session, Identifier object, Identifier property);

    MessageEvent doSeleniumActionUrlLogin(Session session, String host, String uri);

    MessageEvent doSeleniumActionFocusToIframe(Session session, Identifier identifier);

    MessageEvent doSeleniumActionFocusDefaultIframe(Session session);

    MessageEvent doSeleniumActionRightClick(Session session, Identifier identifier);
}
