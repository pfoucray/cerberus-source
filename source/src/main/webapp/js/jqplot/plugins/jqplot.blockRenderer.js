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
(function($) {
    /**
     * Class: $.jqplot.BlockRenderer
     * Plugin renderer to draw a x-y block chart.  A Block chart has data points displayed as
     * colored squares with a text label inside.  Data must be supplied in the form:
     * 
     * > [[x1, y1, "label 1", {css}], [x2, y2, "label 2", {css}], ...]
     * 
     * The label and css object are optional.  If the label is ommitted, the
     * box will collapse unless a css height and/or width is specified.
     * 
     * The css object is an object specifying css properties 
     * such as:
     * 
     * > {background:'#4f98a5', border:'3px solid gray', padding:'1px'}
     * 
     * Note that css properties specified with the data point override defaults
     * specified with the series.
     * 
     */
    $.jqplot.BlockRenderer = function(){
        $.jqplot.LineRenderer.call(this);
    };
    
    $.jqplot.BlockRenderer.prototype = new $.jqplot.LineRenderer();
    $.jqplot.BlockRenderer.prototype.constructor = $.jqplot.BlockRenderer;
    
    // called with scope of a series
    $.jqplot.BlockRenderer.prototype.init = function(options) {
        // Group: Properties
        //
        // prop: css
        // default css styles that will be applied to all data blocks.
        // these values will be overridden by css styles supplied with the
        // individulal data points.
        this.css = {padding:'2px', border:'1px solid #999', textAlign:'center'};
        // prop: escapeHtml
        // true to escape html in the box label.
        this.escapeHtml = false;
        // prop: insertBreaks
        // true to turn spaces in data block label into html breaks <br />.
        this.insertBreaks = true;
        // prop: varyBlockColors
        // true to vary the color of each block in this series according to
        // the seriesColors array.  False to set each block to the color
        // specified on this series.  This has no effect if a css background color
        // option is specified in the renderer css options.
        this.varyBlockColors = false;
        $.extend(true, this, options);
        if (this.css.backgroundColor) {
            this.color = this.css.backgroundColor;
        }
        else if (this.css.background) {
            this.color = this.css.background;
        }
        else if (!this.varyBlockColors) {
            this.css.background = this.color;
        }
        this.canvas = new $.jqplot.BlockCanvas();
        this.shadowCanvas =  new $.jqplot.BlockCanvas();
        this.canvas._plotDimensions = this._plotDimensions;
        this.shadowCanvas._plotDimensions = this._plotDimensions;
        this._type = 'block';
        
        // group: Methods 
        //
        // Method: moveBlock
        // Moves an individual block.  More efficient than redrawing
        // the whole series by calling plot.drawSeries().
        // Properties:
        // idx - the 0 based index of the block or point in this series.
        // x - the x coordinate in data units (value on x axis) to move the block to.
        // y - the y coordinate in data units (value on the y axis) to move the block to.
        // duration - optional parameter to create an animated movement.  Can be a
        // number (higher is slower animation) or 'fast', 'normal' or 'slow'.  If not
        // provided, the element is moved without any animation.
        this.moveBlock = function (idx, x, y, duration) {
            // update plotData, stackData, data and gridData
            // x and y are in data coordinates.
            var el = this.canvas._elem.children(':eq('+idx+')');
            this.data[idx][0] = x;
            this.data[idx][1] = y;
            this._plotData[idx][0] = x;
            this._plotData[idx][1] = y;
            this._stackData[idx][0] = x;
            this._stackData[idx][1] = y;
            this.gridData[idx][0] = this._xaxis.series_u2p(x);
            this.gridData[idx][1] = this._yaxis.series_u2p(y);
            var w = el.outerWidth();
            var h = el.outerHeight();
            var left = this.gridData[idx][0] - w/2 + 'px';
            var top = this.gridData[idx][1] - h/2 + 'px';
            if (duration) {
                if (parseInt(duration, 10)) {
                    duration = parseInt(duration, 10);
                }
                el.animate({left:left, top:top}, duration);
            }
            else {
                el.css({left:left, top:top});
            }
            el = null;
        };
    };
    
    // called with scope of series
    $.jqplot.BlockRenderer.prototype.draw = function (ctx, gd, options) {
        if (this.plugins.pointLabels) {
            this.plugins.pointLabels.show = false;
        }
        var i, el, d, gd, t, css, w, h, left, top;
        var opts = (options != undefined) ? options : {};
        var colorGenerator = new $.jqplot.ColorGenerator(this.seriesColors);
        this.canvas._elem.empty();
        for (i=0; i<this.gridData.length; i++) {
            d = this.data[i];
            gd = this.gridData[i];
            t = '';
            css = {};
            if (typeof d[2] == 'string') {
                t = d[2];
            }
            else if (typeof d[2] == 'object') {
                css = d[2];
            }
            if (typeof d[3] ==  'object') {
                css = d[3];
            }
            if (this.insertBreaks){ 
                t = t.replace(/ /g, '<br />');
            }
            css = $.extend(true, {}, this.css, css);
            // create a div
            el = $('<div style="position:absolute;margin-left:auto;margin-right:auto;"></div>');
            this.canvas._elem.append(el);
            // set text
            this.escapeHtml ? el.text(t) : el.html(t);
            // style it
            // remove styles we don't want overridden.
            delete css.position;
            delete css.marginRight;
            delete css.marginLeft;
            if (!css.background && !css.backgroundColor && !css.backgroundImage){ 
                css.background = colorGenerator.next();
            }
            el.css(css);
            w = el.outerWidth();
            h = el.outerHeight();
            left = gd[0] - w/2 + 'px';
            top = gd[1] - h/2 + 'px';
            el.css({left:left, top:top});
            el = null;
        }
    };
    
    $.jqplot.BlockCanvas = function() {
        $.jqplot.ElemContainer.call(this);
        this._ctx;  
    };
    
    $.jqplot.BlockCanvas.prototype = new $.jqplot.ElemContainer();
    $.jqplot.BlockCanvas.prototype.constructor = $.jqplot.BlockCanvas;
    
    $.jqplot.BlockCanvas.prototype.createElement = function(offsets, clss, plotDimensions) {
        this._offsets = offsets;
        var klass = 'jqplot-blockCanvas';
        if (clss != undefined) {
            klass = clss;
        }
        var elem;
        // if this canvas already has a dom element, don't make a new one.
        if (this._elem) {
            elem = this._elem.get(0);
        }
        else {
            elem = document.createElement('div');
        }
        // if new plotDimensions supplied, use them.
        if (plotDimensions != undefined) {
            this._plotDimensions = plotDimensions;
        }
        
        var w = this._plotDimensions.width - this._offsets.left - this._offsets.right + 'px';
        var h = this._plotDimensions.height - this._offsets.top - this._offsets.bottom + 'px';
        this._elem = $(elem);
        this._elem.css({ position: 'absolute', width:w, height:h, left: this._offsets.left, top: this._offsets.top });
        
        this._elem.addClass(klass);
        return this._elem;
    };
    
    $.jqplot.BlockCanvas.prototype.setContext = function() {
        this._ctx = {
            canvas:{
                width:0,
                height:0
            },
            clearRect:function(){return null;}
        };
        return this._ctx;
    };
    
})(jQuery);
    
    