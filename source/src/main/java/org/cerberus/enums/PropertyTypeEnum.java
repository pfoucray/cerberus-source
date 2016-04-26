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
package org.cerberus.enums;
/**
 * Enumeration that defines all property types
 * @author FNogueira
 */
public enum PropertyTypeEnum {
    GET_FROM_DATALIB("getFromDataLib_BETA"),
    EXECUTE_SQL("executeSql"),
    EXECUTE_SQL_FROM_LIB("executeSqlFromLib"), 
    ACCESS_SUBDATA("accessSubData"), 
    TEXT("text"), 
    GET_FROM_HTML_VISIBLE("getFromHtmlVisible"), 
    GET_FROM_HTML("getFromHtml"), 
    GET_FROM_JS("getFromJS"), 
    GET_FROM_TEST_DATA("getFromTestData"), 
    GET_ATTRIBUTE_FROM_HTML("getAttributeFromHtml"), 
    GET_FROM_COOKIE("getFromCookie"), 
    GET_FROM_XML("getFromXml"), 
    GET_FROM_JSON("getFromJson"), 
    EXECUTE_SOAP_FROM_LIB("executeSoapFromLib"), 
    GET_DIFFERENCES_FROM_XML("getDifferencesFromXml");
    
    private final String propertyName;

    public String getPropertyName() {
        return propertyName;
    }


    private PropertyTypeEnum(String propertyName) {
        this.propertyName = propertyName; 
    }

    /**
     * Verifies if the property name exists in the list of system properties
     * @param propertyName
     * @return true if property is defined in the enumeration, false if not
     */
    public static boolean contains(String propertyName){
        for(PropertyTypeEnum en : values()){
            if(en.getPropertyName().compareTo(propertyName) == 0){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        return propertyName;
    }
}