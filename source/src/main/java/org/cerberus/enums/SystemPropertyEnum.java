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
 * Enum that defines all system properties
 * @author FN
 */
public enum SystemPropertyEnum {
            SYS_SYSTEM("SYS_SYSTEM"),
            SYS_TAG("SYS_TAG"),
            SYS_APPLI("SYS_APPLI"),
            SYS_APP_DOMAIN("SYS_APP_DOMAIN"),
            SYS_APP_HOST("SYS_APP_HOST"),
            SYS_ENV("SYS_ENV"),
            SYS_ENVGP("SYS_ENVGP"),
            SYS_COUNTRY("SYS_COUNTRY"),
            SYS_COUNTRYGP1("SYS_COUNTRYGP1"),
            SYS_SSIP("SYS_SSIP"),
            SYS_SSPORT("SYS_SSPORT"),
            SYS_EXECUTIONID("SYS_EXECUTIONID"),
            SYS_TODAY_YYYY("SYS_TODAY-yyyy"),
            SYS_TODAY_MM("SYS_TODAY-MM"),
            SYS_TODAY_dd("SYS_TODAY-dd"),
            SYS_TODAY_doy("SYS_TODAY-doy"),
            SYS_TODAY_HH("SYS_TODAY-HH"),
            SYS_TODAY_mm("SYS_TODAY-mm"),
            SYS_TODAY_ss("SYS_TODAY-ss"),
            SYS_YESTERDAY_yyyy("SYS_YESTERDAY-yyyy"),
            SYS_YESTERDAY_MM("SYS_YESTERDAY-MM"),
            SYS_YESTERDAY_dd("SYS_YESTERDAY-dd"),
            SYS_YESTERDAY_doy("SYS_YESTERDAY-doy"),
            SYS_YESTERDAY_HH("SYS_YESTERDAY-HH"),
            SYS_YESTERDAY_mm("SYS_YESTERDAY-mm"),
            SYS_YESTERDAY_ss("SYS_YESTERDAY-ss"),
            SYS_ELAPSED_EXESTART("SYS_ELAPSED-EXESTART"),
            SYS_ELAPSED_STEPSTART("SYS_ELAPSED-STEPSTART");
        private final String propertyName;

        public String getPropertyName() {
            return propertyName;
        }


        private SystemPropertyEnum(String propertyName) {
            this.propertyName = propertyName; 
        }
        
        /**
         * Verifies if the property name exists in the list of system properties
         * @param propertyName
         * @return true if property is defined in the enumeration, false if not
         */
        public static boolean contains(String propertyName){
            for(SystemPropertyEnum en : values()){
                if(en.getPropertyName().compareTo(propertyName) == 0){
                    return true;
                }
            }
            return false;
        }
}