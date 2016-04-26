/*
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
(function(){
	"use strict";

	var root = this,
		Chart = root.Chart,
		helpers = Chart.helpers;

        // Notice now we're extending the particular Line chart type, rather than the base class.
        Chart.types.Bar.extend({
            // Passing in a name registers this chart in the Chart namespace in the same way
            name: "BarColor",
            initialize: function (data) {
                Chart.types.Bar.prototype.initialize.apply(this, arguments);
                this.eachBars(function (bar, index, datasetIndex) {
                    helpers.extend(bar, {
                        fillColor: data.datasets[datasetIndex].fillColors[index] || data.datasets[datasetIndex].fillColor,
                        strokeColor: data.datasets[datasetIndex].strokeColors[index] || data.datasets[datasetIndex].strokeColor,
                        highlightFill: data.datasets[datasetIndex].highlightFills[index] || data.datasets[datasetIndex].highlightFill,
                        highlightStroke: data.datasets[datasetIndex].highlightStrokes[index] || data.datasets[datasetIndex].highlightStroke
                    });

                    bar.save();
                }, this);
            }
        });
}).call(this);
