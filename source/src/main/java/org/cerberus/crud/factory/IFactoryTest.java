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
package org.cerberus.crud.factory;

import org.cerberus.crud.entity.Test;

/**
 *
 * @author bcivel
 */
public interface IFactoryTest {

    /**
     * 
     * @param test Name of the Test
     * @param description Description of the Test
     * @param active Boolean active : Y=Active / N=Inactive
     * @param automated Boolean Automated : Y=Automated / N=Manual
     * @param tDateCrea String Date of Creation of the test
     * @return Test Object Created
     */
    Test create(String test, String description,
            String active, String automated, String tDateCrea);
}
