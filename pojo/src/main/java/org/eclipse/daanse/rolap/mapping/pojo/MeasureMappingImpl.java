/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 */
package org.eclipse.daanse.rolap.mapping.pojo;

import java.util.Collections;
import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.MeasureMapping;

public class MeasureMappingImpl implements MeasureMapping {

    private SQLExpressionMappingImpl measureExpression;

    private List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperty;

    private CellFormatterMappingImpl cellFormatter;

    private String backColor;

    private String column;

    private String datatype;

    private String displayFolder;

    private String formatString;

    private String formatter;

    private boolean visible;

    private String name;

    private String id;

    private String type;

    private MeasureMappingImpl(Builder builder) {
        this.measureExpression = builder.measureExpression;
        this.calculatedMemberProperty = builder.calculatedMemberProperty;
        this.cellFormatter = builder.cellFormatter;
        this.backColor = builder.backColor;
        this.column = builder.column;
        this.datatype = builder.datatype;
        this.displayFolder = builder.displayFolder;
        this.formatString = builder.formatString;
        this.formatter = builder.formatter;
        this.visible = builder.visible;
        this.name = builder.name;
        this.id = builder.id;
        this.type = builder.type;
    }

    public SQLExpressionMappingImpl getMeasureExpression() {
        return measureExpression;
    }

    public void setMeasureExpression(SQLExpressionMappingImpl measureExpression) {
        this.measureExpression = measureExpression;
    }

    public List<CalculatedMemberPropertyMappingImpl> getCalculatedMemberProperty() {
        return calculatedMemberProperty;
    }

    public void setCalculatedMemberProperty(List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperty) {
        this.calculatedMemberProperty = calculatedMemberProperty;
    }

    public CellFormatterMappingImpl getCellFormatter() {
        return cellFormatter;
    }

    public void setCellFormatter(CellFormatterMappingImpl cellFormatter) {
        this.cellFormatter = cellFormatter;
    }

    public String getBackColor() {
        return backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getDisplayFolder() {
        return displayFolder;
    }

    public void setDisplayFolder(String displayFolder) {
        this.displayFolder = displayFolder;
    }

    public String getFormatString() {
        return formatString;
    }

    public void setFormatString(String formatString) {
        this.formatString = formatString;
    }

    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private SQLExpressionMappingImpl measureExpression;
        private List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperty = Collections.emptyList();
        private CellFormatterMappingImpl cellFormatter;
        private String backColor;
        private String column;
        private String datatype;
        private String displayFolder;
        private String formatString;
        private String formatter;
        private boolean visible;
        private String name;
        private String id;
        private String type;

        private Builder() {
        }

        public Builder withMeasureExpression(SQLExpressionMappingImpl measureExpression) {
            this.measureExpression = measureExpression;
            return this;
        }

        public Builder withCalculatedMemberProperty(
                List<CalculatedMemberPropertyMappingImpl> calculatedMemberProperty) {
            this.calculatedMemberProperty = calculatedMemberProperty;
            return this;
        }

        public Builder withCellFormatter(CellFormatterMappingImpl cellFormatter) {
            this.cellFormatter = cellFormatter;
            return this;
        }

        public Builder withBackColor(String backColor) {
            this.backColor = backColor;
            return this;
        }

        public Builder withColumn(String column) {
            this.column = column;
            return this;
        }

        public Builder withDatatype(String datatype) {
            this.datatype = datatype;
            return this;
        }

        public Builder withDisplayFolder(String displayFolder) {
            this.displayFolder = displayFolder;
            return this;
        }

        public Builder withFormatString(String formatString) {
            this.formatString = formatString;
            return this;
        }

        public Builder withFormatter(String formatter) {
            this.formatter = formatter;
            return this;
        }

        public Builder withVisible(boolean visible) {
            this.visible = visible;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public MeasureMappingImpl build() {
            return new MeasureMappingImpl(this);
        }
    }

}