package org.eclipse.daanse.emf.model.rolapmapping.provider.impl;

import java.util.List;

import org.eclipse.daanse.rolap.mapping.api.model.AccessCubeGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessDimensionGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessHierarchyGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessMemberGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessRoleMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AccessSchemaGrantMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationColumnNameMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationExcludeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationForeignKeyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationLevelMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationLevelPropertyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationMeasureFactCountMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationMeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AggregationTableMapping;
import org.eclipse.daanse.rolap.mapping.api.model.AnnotationMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CalculatedMemberPropertyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CellFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.CubeMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionConnectorMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DimensionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.DocumentationMapping;
import org.eclipse.daanse.rolap.mapping.api.model.HierarchyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableColumnDefinitionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableRowCellMapping;
import org.eclipse.daanse.rolap.mapping.api.model.InlineTableRowMappingMapping;
import org.eclipse.daanse.rolap.mapping.api.model.JoinQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.JoinedQueryElementMapping;
import org.eclipse.daanse.rolap.mapping.api.model.KpiMapping;
import org.eclipse.daanse.rolap.mapping.api.model.LevelMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MeasureGroupMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MeasureMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberPropertyFormatterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberPropertyMapping;
import org.eclipse.daanse.rolap.mapping.api.model.MemberReaderParameterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.NamedSetMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ParameterMapping;
import org.eclipse.daanse.rolap.mapping.api.model.ParentChildLinkMapping;
import org.eclipse.daanse.rolap.mapping.api.model.QueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SQLExpressionMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SQLMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SchemaMapping;
import org.eclipse.daanse.rolap.mapping.api.model.SqlSelectQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableQueryMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TableQueryOptimizationHintMapping;
import org.eclipse.daanse.rolap.mapping.api.model.TranslationMapping;

public abstract class AbstractSchemaMappingModifier {

    protected SchemaMapping schemaMapping;

    protected AbstractSchemaMappingModifier(SchemaMapping schemaMapping) {
        super();
        this.schemaMapping = schemaMapping;
    }

    public SchemaMapping get() {
        return modifyMappingSchema(schemaMapping);
    }

    /**
     * @param mappingSchemaOriginal
     * @return
     */
    protected SchemaMapping modifyMappingSchema(SchemaMapping schemaMappingOriginal) {

        if (schemaMappingOriginal != null) {

            List<? extends AnnotationMapping> annotations = schemaAnnotations(schemaMappingOriginal);
            String id = schemaId(schemaMappingOriginal);
            String description = schemaDescription(schemaMappingOriginal);
            String name = schemaName(schemaMappingOriginal);
            DocumentationMapping documentation = schemaDocumentation(schemaMappingOriginal);
            List<? extends ParameterMapping> parameters = schemaParameters(schemaMappingOriginal);
            List<? extends CubeMapping> cubes = schemaCubes(schemaMappingOriginal);
            List<? extends NamedSetMapping> namedSets = schemaNamedSets(schemaMappingOriginal);
            List<? extends AccessRoleMapping> accessRoles = schemaAccessRoles(schemaMappingOriginal);
            AccessRoleMapping defaultAccessRole = schemaDefaultAccessRole(schemaMappingOriginal);
            String measuresDimensionName = schemaMeasuresDimensionName(schemaMappingOriginal);

            return new_Schema(annotations, id, description, name, documentation, parameters, cubes, namedSets,
                accessRoles, defaultAccessRole, measuresDimensionName);
        }
        return null;
    }

    protected String schemaMeasuresDimensionName(SchemaMapping schemaMappingOriginal) {
        return schemaMappingOriginal.getMeasuresDimensionName();
    }

    protected AccessRoleMapping schemaDefaultAccessRole(SchemaMapping schemaMappingOriginal) {
        return accessRole(schemaMappingOriginal.getDefaultAccessRole());
    }

    protected AccessRoleMapping accessRole(AccessRoleMapping accessRole) {
        if (accessRole != null) {
            List<? extends AnnotationMapping> annotations = accessRoleAnnotations(accessRole);
            String id = accessRoleId(accessRole);
            String description = accessRoleDescription(accessRole);
            String name = accessRoleName(accessRole);
            DocumentationMapping documentation = accessRoleDocumentation(accessRole);

            List<? extends AccessSchemaGrantMapping> accessSchemaGrants = accessRoleAccessSchemaGrants(accessRole);
            List<? extends AccessRoleMapping> referencedAccessRoles = accessRoleReferencedAccessRoles(accessRole);
            return new_AccessRole(annotations, id, description, name, documentation, accessSchemaGrants,
                referencedAccessRoles);
        }
        return null;
    }

    protected List<? extends AnnotationMapping> accessRoleAnnotations(AccessRoleMapping accessRole) {
        return annotations(accessRole.getAnnotations());
    }

    protected DocumentationMapping accessRoleDocumentation(AccessRoleMapping accessRole) {
        return documentation(accessRole.getDocumentation());
    }

    protected String accessRoleName(AccessRoleMapping accessRole) {
        return accessRole.getName();
    }

    protected String accessRoleDescription(AccessRoleMapping accessRole) {
        return accessRole.getDescription();
    }

    protected String accessRoleId(AccessRoleMapping accessRole) {
        return accessRole.getId();
    }

    protected abstract AccessRoleMapping new_AccessRole(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation,
        List<? extends AccessSchemaGrantMapping> accessSchemaGrants,
        List<? extends AccessRoleMapping> referencedAccessRoles
    );

    protected List<? extends AccessRoleMapping> accessRoleReferencedAccessRoles(AccessRoleMapping accessRole) {
        return accessRoles(accessRole.getReferencedAccessRoles());
    }

    protected List<? extends AccessSchemaGrantMapping> accessRoleAccessSchemaGrants(AccessRoleMapping accessRole) {
        return accessSchemaGrants(accessRole.getAccessSchemaGrants());
    }

    protected List<? extends AccessSchemaGrantMapping> accessSchemaGrants(
        List<? extends AccessSchemaGrantMapping> accessSchemaGrants
    ) {
        if (accessSchemaGrants != null) {
            return accessSchemaGrants.stream().map(this::accessSchemaGrant).toList();
        }
        return null;
    }

    protected AccessSchemaGrantMapping accessSchemaGrant(AccessSchemaGrantMapping accessSchemaGrant) {
        if (accessSchemaGrant != null) {
            List<? extends AccessCubeGrantMapping> accessCubeGrant = accessSchemaGrantAccessCubeGrant(
                accessSchemaGrant);
            String access = accessSchemaGrantAccess(accessSchemaGrant);
            return new_AccessSchemaGrant(accessCubeGrant, access);
        }
        return null;
    }

    protected List<? extends AccessCubeGrantMapping> accessSchemaGrantAccessCubeGrant(
        AccessSchemaGrantMapping accessSchemaGrant
    ) {
        return accessCubeGrants(accessSchemaGrant.getCubeGrant());
    }

    protected List<? extends AccessCubeGrantMapping> accessCubeGrants(
        List<? extends AccessCubeGrantMapping> accessCubeGrants
    ) {
        if (accessCubeGrants != null) {
            return accessCubeGrants.stream().map(this::accessCubeGrant).toList();
        }
        return null;
    }

    protected AccessCubeGrantMapping accessCubeGrant(AccessCubeGrantMapping accessCubeGrant) {
        if (accessCubeGrant != null) {
            List<? extends AccessDimensionGrantMapping> dimensionGrants = accessCubeGrantAccessDimension(
                accessCubeGrant);

            List<? extends AccessHierarchyGrantMapping> hierarchyGrants = accessCubeGrantAccessHierarchyGrant(
                accessCubeGrant);

            String access = accessCubeGrantAccess(accessCubeGrant);

            CubeMapping cube = accessCubeGrantCube(accessCubeGrant);

            return new_AccessCubeGrant(dimensionGrants, hierarchyGrants, access, cube);
        }
        return null;
    }

    protected List<? extends AccessHierarchyGrantMapping> accessCubeGrantAccessHierarchyGrant(
        AccessCubeGrantMapping accessCubeGrant
    ) {
        return accessHierarchyGrants(accessCubeGrant.getHierarchyGrants());
    }

    protected List<? extends AccessHierarchyGrantMapping> accessHierarchyGrants(
        List<? extends AccessHierarchyGrantMapping> accessHierarchyGrants
    ) {
        if (accessHierarchyGrants != null) {
            return accessHierarchyGrants.stream().map(this::accessHierarchyGrant).toList();
        }
        return null;
    }

    protected AccessHierarchyGrantMapping accessHierarchyGrant(AccessHierarchyGrantMapping accessHierarchyGrant) {
        if (accessHierarchyGrant != null) {
            List<? extends AccessMemberGrantMapping> memberGrants = accessHierarchyGrantMemberGrants(
                accessHierarchyGrant);
            String access = accessHierarchyGrantAccess(accessHierarchyGrant);
            LevelMapping bottomLevel = accessHierarchyGrantBottomLevel(accessHierarchyGrant);
            String rollupPolicy = accessHierarchyGrantRollupPolicy(accessHierarchyGrant);
            LevelMapping topLevel = accessHierarchyGrantTopLevel(accessHierarchyGrant);
            HierarchyMapping hierarchy = accessHierarchyGrantHierarchy(accessHierarchyGrant);

            return new_AccessHierarchyGrant(memberGrants, access, bottomLevel, rollupPolicy, topLevel, hierarchy);
        }
        return null;
    }

    protected List<? extends AccessMemberGrantMapping> accessHierarchyGrantMemberGrants(
        AccessHierarchyGrantMapping accessHierarchyGrant
    ) {
        return accessMemberGrants(accessHierarchyGrant.getMemberGrants());
    }

    protected List<? extends AccessMemberGrantMapping> accessMemberGrants(
        List<? extends AccessMemberGrantMapping> accessMemberGrants
    ) {
        if (accessMemberGrants != null) {
            return accessMemberGrants.stream().map(this::accessMemberGrant).toList();
        }
        return null;
    }

    protected AccessMemberGrantMapping accessMemberGrant(AccessMemberGrantMapping accessMemberGrant) {
        if (accessMemberGrant != null) {
            String access = accessMemberGrantAccess(accessMemberGrant);
            String member = accessMemberGrantMember(accessMemberGrant);
            return new_AccessMemberGrant(access, member);
        }
        return null;
    }

    protected String accessMemberGrantMember(AccessMemberGrantMapping accessMemberGrant) {
        return accessMemberGrant.getMember();
    }

    protected String accessMemberGrantAccess(AccessMemberGrantMapping accessMemberGrant) {
        return accessMemberGrant.getAccess();
    }

    protected abstract AccessMemberGrantMapping new_AccessMemberGrant(String access, String member);

    protected HierarchyMapping accessHierarchyGrantHierarchy(AccessHierarchyGrantMapping accessHierarchyGrant) {
        return hierarchy(accessHierarchyGrant.getHierarchy());
    }

    protected HierarchyMapping hierarchy(HierarchyMapping hierarchy) {
        if (hierarchy != null) {
            List<? extends AnnotationMapping> annotations = hierarchyAnnotations(hierarchy);
            String id = hierarchyId(hierarchy);
            String description = hierarchyDescription(hierarchy);
            String name = hierarchyName(hierarchy);
            DocumentationMapping documentation = hierarchyDocumentation(hierarchy);

            List<? extends LevelMapping> levels = hierarchyLevels(hierarchy);
            List<? extends MemberReaderParameterMapping> memberReaderParameters = hierarchyMemberReaderParameters(
                hierarchy);
            String allLevelName = hierarchyAllLevelName(hierarchy);
            String allMemberCaption = hierarchyAllMemberCaption(hierarchy);
            String allMemberName = hierarchyAllMemberName(hierarchy);
            String defaultMember = hierarchyDefaultMember(hierarchy);
            String displayFolder = hierarchyDisplayFolder(hierarchy);
            boolean hasAll = hierarchyHasAll(hierarchy);
            String memberReaderClass = hierarchyMemberReaderClass(hierarchy);
            String origin = hierarchyOrigin(hierarchy);
            String primaryKey = hierarchyPrimaryKey(hierarchy);
            String primaryKeyTable = hierarchyPrimaryKeyTable(hierarchy);
            String uniqueKeyLevelName = hierarchyUniqueKeyLevelName(hierarchy);
            boolean visible = hierarchyVisible(hierarchy);
            QueryMapping query = hierarchyQuery(hierarchy);

            return new_Hierarchy(annotations, id, description, name, documentation, levels, memberReaderParameters,
                allLevelName, allMemberCaption, allMemberName, defaultMember, displayFolder, hasAll,
                memberReaderClass, origin, primaryKey, primaryKeyTable, uniqueKeyLevelName, visible, query);
        }
        return null;
    }

    protected QueryMapping hierarchyQuery(HierarchyMapping hierarchy) {
        return query(hierarchy.getQuery());
    }

    protected QueryMapping query(QueryMapping query) {
        if (query != null) {
            if (query instanceof TableQueryMapping tq) {
                return tableQuery(tq);
            }
            if (query instanceof SqlSelectQueryMapping ssq) {
                return sqlSelectQuery(ssq);
            }
            if (query instanceof JoinQueryMapping jq) {
                return joinQuery(jq);
            }
            if (query instanceof InlineTableQueryMapping itq) {
                return inlineTableQuery(itq);
            }
        }
        return null;
    }

    protected QueryMapping inlineTableQuery(InlineTableQueryMapping itq) {
        if (itq != null) {
            String alias = inlineTableQueryAlias(itq);
            List<? extends InlineTableColumnDefinitionMapping> columnDefinitions = inlineTableQueryColumnDefinitions(
                itq);
            List<? extends InlineTableRowMappingMapping> rows = inlineTableQueryRows(itq);
            return new_TableQuery(alias, columnDefinitions, rows);
        }
        return null;
    }

    protected abstract QueryMapping new_TableQuery(
        String alias,
        List<? extends InlineTableColumnDefinitionMapping> columnDefinitions,
        List<? extends InlineTableRowMappingMapping> rows
    );

    protected List<? extends InlineTableRowMappingMapping> inlineTableQueryRows(InlineTableQueryMapping itq) {
        return inlineTableRowMappings(itq.getRows());
    }

    protected List<? extends InlineTableRowMappingMapping> inlineTableRowMappings(
        List<? extends InlineTableRowMappingMapping> rows
    ) {
        if (rows != null) {
            return rows.stream().map(this::inlineTableRowMapping).toList();
        }
        return null;
    }

    protected InlineTableRowMappingMapping inlineTableRowMapping(InlineTableRowMappingMapping inlineTableRowMapping) {
        if (inlineTableRowMapping != null) {
            List<? extends InlineTableRowCellMapping> cells = inlineTableRowMappingCells(inlineTableRowMapping);
            return new_InlineTableRowMapping(cells);
        }
        return null;
    }

    protected List<? extends InlineTableRowCellMapping> inlineTableRowMappingCells(
        InlineTableRowMappingMapping inlineTableRowMapping
    ) {
        return inlineTableRowCells(inlineTableRowMapping.getCells());
    }

    protected List<? extends InlineTableRowCellMapping> inlineTableRowCells(
        List<? extends InlineTableRowCellMapping> cells
    ) {
        if (cells != null) {
            return cells.stream().map(this::inlineTableRowCell).toList();
        }
        return null;
    }

    protected InlineTableRowCellMapping inlineTableRowCell(InlineTableRowCellMapping inlineTableRowCell) {
        if (inlineTableRowCell != null) {
            String value = inlineTableRowCellValue(inlineTableRowCell);
            String columnName = inlineTableRowCellColumnName(inlineTableRowCell);
            return new_InlineTableRowCell(value, columnName);
        }
        return null;
    }

    protected abstract InlineTableRowCellMapping new_InlineTableRowCell(String value, String columnName);

    protected String inlineTableRowCellColumnName(InlineTableRowCellMapping inlineTableRowCell) {
        return inlineTableRowCell.getColumnName();
    }

    protected String inlineTableRowCellValue(InlineTableRowCellMapping inlineTableRowCell) {
        return inlineTableRowCell.getValue();
    }

    protected abstract InlineTableRowMappingMapping new_InlineTableRowMapping(
        List<? extends InlineTableRowCellMapping> cells
    );

    protected List<? extends InlineTableColumnDefinitionMapping> inlineTableQueryColumnDefinitions(
        InlineTableQueryMapping itq
    ) {
        return inlineTableColumnDefinitions(itq.getColumnDefinitions());
    }

    protected List<? extends InlineTableColumnDefinitionMapping> inlineTableColumnDefinitions(
        List<? extends InlineTableColumnDefinitionMapping> columnDefinitions
    ) {
        if (columnDefinitions != null) {
            return columnDefinitions.stream().map(this::inlineTableColumnDefinition).toList();
        }
        return null;
    }

    protected InlineTableColumnDefinitionMapping inlineTableColumnDefinition(
        InlineTableColumnDefinitionMapping inlineTableColumnDefinition
    ) {
        if (inlineTableColumnDefinition != null) {
            String name = inlineTableColumnDefinitionName(inlineTableColumnDefinition);
            String type = inlineTableColumnDefinitionType(inlineTableColumnDefinition);

            return new_InlineTableColumnDefinition(name, type);
        }
        return null;
    }

    protected abstract InlineTableColumnDefinitionMapping new_InlineTableColumnDefinition(String name, String type);

    protected String inlineTableColumnDefinitionType(InlineTableColumnDefinitionMapping inlineTableColumnDefinition) {
        return inlineTableColumnDefinition.getType();
    }

    protected String inlineTableColumnDefinitionName(InlineTableColumnDefinitionMapping inlineTableColumnDefinition) {
        return inlineTableColumnDefinition.getName();
    }

    protected String inlineTableQueryAlias(InlineTableQueryMapping itq) {
        return itq.getAlias();
    }

    protected QueryMapping joinQuery(JoinQueryMapping jq) {
        if (jq != null) {
            JoinedQueryElementMapping left = joinQueryLeft(jq);
            JoinedQueryElementMapping right = joinQueryRight(jq);
            return new_JoinQuery(left, right);
        }
        return null;
    }

    protected abstract QueryMapping new_JoinQuery(JoinedQueryElementMapping left, JoinedQueryElementMapping right);

    protected JoinedQueryElementMapping joinQueryRight(JoinQueryMapping jq) {
        return joinedQueryElement(jq.getRight());
    }

    protected JoinedQueryElementMapping joinedQueryElement(JoinedQueryElementMapping joinedQueryElement) {
        if (joinedQueryElement != null) {
            String alias = joinedQueryElementAlias(joinedQueryElement);
            String key = joinedQueryElementKey(joinedQueryElement);
            QueryMapping query = joinedQueryElementQuery(joinedQueryElement);
            return new_JoinedQueryElement(alias, key, query);
        }
        return null;
    }

    protected String joinedQueryElementAlias(JoinedQueryElementMapping joinedQueryElement) {
        return joinedQueryElement.getAlias();
    }

    protected String joinedQueryElementKey(JoinedQueryElementMapping joinedQueryElement) {
        return joinedQueryElement.getKey();
    }

    protected QueryMapping joinedQueryElementQuery(JoinedQueryElementMapping joinedQueryElement) {
        return query(joinedQueryElement.getQuery());
    }

    protected abstract JoinedQueryElementMapping new_JoinedQueryElement(String alias, String key, QueryMapping query);

    protected JoinedQueryElementMapping joinQueryLeft(JoinQueryMapping jq) {
        return joinedQueryElement(jq.getLeft());
    }

    protected QueryMapping sqlSelectQuery(SqlSelectQueryMapping ssq) {
        if (ssq != null) {
            String alias = sqlSelectQueryAlias(ssq);
            List<? extends SQLMapping> sqls = sqlSelectQuerySqls(ssq);

            return new_SqlSelectQuery(alias, sqls);
        }
        return null;
    }

    protected abstract QueryMapping new_SqlSelectQuery(String alias, List<? extends SQLMapping> sqls);

    protected List<? extends SQLMapping> sqlSelectQuerySqls(SqlSelectQueryMapping ssq) {
        return sqls(ssq.getSQL());
    }

    protected List<? extends SQLMapping> sqls(List<? extends SQLMapping> sqls) {
        if (sqls != null) {
            return sqls.stream().map(this::sql).toList();
        }
        return null;
    }

    protected String sqlSelectQueryAlias(SqlSelectQueryMapping ssq) {
        return ssq.getAlias();
    }

    protected TableQueryMapping tableQuery(TableQueryMapping tableQuery) {
        if (tableQuery != null) {
            String alias = tableQueryAlias(tableQuery);
            SQLMapping sqlWhereExpression = tableQuerySqlWhereExpression(tableQuery);

            List<? extends AggregationExcludeMapping> aggregationExcludes = tableQueryAggregationExcludes(tableQuery);

            List<? extends TableQueryOptimizationHintMapping> optimizationHints = tableQueryOptimizationHints(
                tableQuery);

            String name = tableQueryName(tableQuery);

            String schema = tableQuerySchema(tableQuery);

            List<? extends AggregationTableMapping> aggregationTables = tableQueryAggregationTables(tableQuery);

            return new_TableQuery(alias, sqlWhereExpression, aggregationExcludes, optimizationHints, name, schema,
                aggregationTables);
        }
        return null;

    }

    protected abstract TableQueryMapping new_TableQuery(
        String alias, SQLMapping sqlWhereExpression,
        List<? extends AggregationExcludeMapping> aggregationExcludes,
        List<? extends TableQueryOptimizationHintMapping> optimizationHints, String name, String schema,
        List<? extends AggregationTableMapping> aggregationTables
    );

    protected List<? extends AggregationTableMapping> tableQueryAggregationTables(TableQueryMapping tableQuery) {
        return aggregationTables(tableQuery.getAggregationTables());
    }

    protected List<? extends AggregationTableMapping> aggregationTables(
        List<? extends AggregationTableMapping> aggregationTables
    ) {
        if (aggregationTables != null) {
            return aggregationTables.stream().map(this::aggregationTable).toList();
        }
        return null;
    }

    protected AggregationTableMapping aggregationTable(AggregationTableMapping aggregationTable) {
        if (aggregationTable != null) {
            AggregationColumnNameMapping aggregationFactCount = aggregationTableAggregationFactCount(aggregationTable);
            List<? extends AggregationColumnNameMapping> aggregationIgnoreColumns =
                aggregationTableAggregationIgnoreColumns(
                aggregationTable);
            List<? extends AggregationForeignKeyMapping> aggregationForeignKeys =
                aggregationTableAggregationForeignKeys(
                aggregationTable);
            List<? extends AggregationMeasureMapping> aggregationMeasures = aggregationTableAggregationMeasures(
                aggregationTable);
            List<? extends AggregationLevelMapping> aggregationLevels = aggregationTableAggregationLevels(
                aggregationTable);
            List<? extends AggregationMeasureFactCountMapping> aggregationMeasureFactCounts =
                aggregationTableAggregationMeasureFactCounts(
                aggregationTable);
            boolean ignorecase = aggregationTableIgnorecase(aggregationTable);
            String id = aggregationTableId(aggregationTable);
            return new_AggregationTable(aggregationFactCount, aggregationIgnoreColumns, aggregationForeignKeys,
                aggregationMeasures, aggregationLevels, aggregationMeasureFactCounts, ignorecase, id);
        }
        return null;
    }

    protected abstract AggregationTableMapping new_AggregationTable(
        AggregationColumnNameMapping aggregationFactCount,
        List<? extends AggregationColumnNameMapping> aggregationIgnoreColumns,
        List<? extends AggregationForeignKeyMapping> aggregationForeignKeys,
        List<? extends AggregationMeasureMapping> aggregationMeasures,
        List<? extends AggregationLevelMapping> aggregationLevels,
        List<? extends AggregationMeasureFactCountMapping> aggregationMeasureFactCounts, boolean ignorecase,
        String id
    );

    protected String aggregationTableId(AggregationTableMapping aggregationTable) {
        return aggregationTable.getId();
    }

    protected boolean aggregationTableIgnorecase(AggregationTableMapping aggregationTable) {
        return aggregationTable.isIgnorecase();
    }

    protected List<? extends AggregationMeasureFactCountMapping> aggregationTableAggregationMeasureFactCounts(
        AggregationTableMapping aggregationTable
    ) {
        return aggregationMeasureFactCounts(aggregationTable.getAggregationMeasureFactCounts());
    }

    protected List<? extends AggregationMeasureFactCountMapping> aggregationMeasureFactCounts(
        List<? extends AggregationMeasureFactCountMapping> aggregationMeasureFactCounts
    ) {
        if (aggregationMeasureFactCounts != null) {
            return aggregationMeasureFactCounts.stream().map(this::aggregationMeasureFactCount).toList();
        }
        return null;
    }

    protected AggregationMeasureFactCountMapping aggregationMeasureFactCount(
        AggregationMeasureFactCountMapping aggregationMeasureFactCount
    ) {
        if (aggregationMeasureFactCount != null) {
            String column = aggregationMeasureFactCountColumn(aggregationMeasureFactCount);
            String factColumn = aggregationMeasureFactCountFactColumn(aggregationMeasureFactCount);
            return new_AggregationMeasureFactCount(column, factColumn);
        }
        return null;
    }

    protected abstract AggregationMeasureFactCountMapping new_AggregationMeasureFactCount(
        String column,
        String factColumn
    );

    protected String aggregationMeasureFactCountFactColumn(
        AggregationMeasureFactCountMapping aggregationMeasureFactCount
    ) {
        return aggregationMeasureFactCount.getFactColumn();
    }

    protected String aggregationMeasureFactCountColumn(AggregationMeasureFactCountMapping aggregationMeasureFactCount) {
        return aggregationMeasureFactCount.getColumn();
    }

    protected List<? extends AggregationLevelMapping> aggregationTableAggregationLevels(
        AggregationTableMapping aggregationTable
    ) {
        return AggregationLevels(aggregationTable.getAggregationLevels());
    }

    protected List<? extends AggregationLevelMapping> AggregationLevels(
        List<? extends AggregationLevelMapping> aggregationLevels
    ) {
        if (aggregationLevels != null) {
            return aggregationLevels.stream().map(this::aggregationLevel).toList();
        }
        return null;
    }

    protected AggregationLevelMapping aggregationLevel(AggregationLevelMapping aggregationLevel) {
        if (aggregationLevel != null) {
            List<? extends AggregationLevelPropertyMapping> aggregationLevelProperties =
                aggregationLevelAggregationLevelProperties(
                aggregationLevel);
            String captionColumn = aggregationLevelCaptionColumn(aggregationLevel);
            boolean collapsed = aggregationLevelCollapsed(aggregationLevel);
            String column = aggregationLevelColumn(aggregationLevel);
            String name = aggregationLevelName(aggregationLevel);
            String nameColumn = aggregationLevelNameColumn(aggregationLevel);
            String ordinalColumn = aggregationLevelOrdinalColumn(aggregationLevel);
            return new_AggregationLevel(aggregationLevelProperties, captionColumn, collapsed, column, name, nameColumn,
                ordinalColumn);
        }
        return null;
    }

    protected List<? extends AggregationLevelPropertyMapping> aggregationLevelAggregationLevelProperties(
        AggregationLevelMapping aggregationLevel
    ) {
        return aggregationLevelProperties(aggregationLevel.getAggregationLevelProperties());
    }

    protected List<? extends AggregationLevelPropertyMapping> aggregationLevelProperties(
        List<? extends AggregationLevelPropertyMapping> aggregationLevelProperties
    ) {
        if (aggregationLevelProperties != null) {
            return aggregationLevelProperties.stream().map(this::aggregationLevelProperty).toList();
        }
        return null;
    }

    protected AggregationLevelPropertyMapping aggregationLevelProperty(
        AggregationLevelPropertyMapping aggregationLevelProperty
    ) {
        if (aggregationLevelProperty != null) {
            String column = aggregationLevelPropertyColumn(aggregationLevelProperty);
            String name = aggregationLevelPropertyName(aggregationLevelProperty);
            return new_AggregationLevelProperty(column, name);
        }
        return null;
    }

    protected abstract AggregationLevelPropertyMapping new_AggregationLevelProperty(String column, String name);

    protected String aggregationLevelPropertyName(AggregationLevelPropertyMapping aggregationLevelProperty) {
        return aggregationLevelProperty.getName();
    }

    protected String aggregationLevelPropertyColumn(AggregationLevelPropertyMapping aggregationLevelProperty) {
        return aggregationLevelProperty.getColumn();
    }

    protected String aggregationLevelOrdinalColumn(AggregationLevelMapping aggregationLevel) {
        return aggregationLevel.getOrdinalColumn();
    }

    protected String aggregationLevelNameColumn(AggregationLevelMapping aggregationLevel) {
        return aggregationLevel.getNameColumn();
    }

    protected String aggregationLevelName(AggregationLevelMapping aggregationLevel) {
        return aggregationLevel.getName();
    }

    protected String aggregationLevelColumn(AggregationLevelMapping aggregationLevel) {
        return aggregationLevel.getColumn();
    }

    protected boolean aggregationLevelCollapsed(AggregationLevelMapping aggregationLevel) {
        return aggregationLevel.isCollapsed();
    }

    protected String aggregationLevelCaptionColumn(AggregationLevelMapping aggregationLevel) {
        return aggregationLevel.getCaptionColumn();
    }

    protected abstract AggregationLevelMapping new_AggregationLevel(
        List<? extends AggregationLevelPropertyMapping> aggregationLevelProperties, String captionColumn,
        boolean collapsed, String column, String name, String nameColumn, String ordinalColumn
    );

    protected List<? extends AggregationMeasureMapping> aggregationTableAggregationMeasures(
        AggregationTableMapping aggregationTable
    ) {
        return aggregationMeasures(aggregationTable.getAggregationMeasures());
    }

    protected List<? extends AggregationMeasureMapping> aggregationMeasures(
        List<? extends AggregationMeasureMapping> aggregationMeasures
    ) {
        if (aggregationMeasures != null) {
            return aggregationMeasures.stream().map(this::aggregationMeasure).toList();
        }
        return null;
    }

    protected AggregationMeasureMapping aggregationMeasure(AggregationMeasureMapping aggregationMeasure) {
        if (aggregationMeasure != null) {
            String column = aggregationMeasureColumn(aggregationMeasure);
            String name = aggregationMeasureName(aggregationMeasure);
            String rollupType = aggregationMeasureRollupType(aggregationMeasure);
            return new_AggregationMeasure(column, name, rollupType);
        }
        return null;
    }

    protected abstract AggregationMeasureMapping new_AggregationMeasure(String column, String name, String rollupType);

    protected String aggregationMeasureRollupType(AggregationMeasureMapping aggregationMeasure) {
        return aggregationMeasure.getRollupType();
    }

    protected String aggregationMeasureName(AggregationMeasureMapping aggregationMeasure) {
        return aggregationMeasure.getName();
    }

    protected String aggregationMeasureColumn(AggregationMeasureMapping aggregationMeasure) {
        return aggregationMeasure.getColumn();
    }

    protected List<? extends AggregationForeignKeyMapping> aggregationTableAggregationForeignKeys(
        AggregationTableMapping aggregationTable
    ) {
        return aggregationForeignKeys(aggregationTable.getAggregationForeignKeys());
    }

    protected List<? extends AggregationForeignKeyMapping> aggregationForeignKeys(
        List<? extends AggregationForeignKeyMapping> aggregationForeignKeys
    ) {
        if (aggregationForeignKeys != null) {
            return aggregationForeignKeys.stream().map(this::aggregationForeignKey).toList();
        }
        return null;
    }

    protected AggregationForeignKeyMapping aggregationForeignKey(AggregationForeignKeyMapping aggregationForeignKey) {
        if (aggregationForeignKey != null) {
            String aggregationColumn = aggregationForeignKeyAggregationColumn(aggregationForeignKey);
            String factColumn = aggregationForeignKeyFactColumn(aggregationForeignKey);
            return new_AggregationForeignKey(aggregationColumn, factColumn);
        }
        return null;
    }

    protected String aggregationForeignKeyFactColumn(AggregationForeignKeyMapping aggregationForeignKey) {
        return aggregationForeignKey.getFactColumn();
    }

    protected String aggregationForeignKeyAggregationColumn(AggregationForeignKeyMapping aggregationForeignKey) {
        return aggregationForeignKey.getAggregationColumn();
    }

    protected abstract AggregationForeignKeyMapping new_AggregationForeignKey(
        String aggregationColumn,
        String factColumn
    );

    protected List<? extends AggregationColumnNameMapping> aggregationTableAggregationIgnoreColumns(
        AggregationTableMapping aggregationTable
    ) {
        return aggregationColumnNames(aggregationTable.getAggregationIgnoreColumns());
    }

    protected List<? extends AggregationColumnNameMapping> aggregationColumnNames(
        List<? extends AggregationColumnNameMapping> aggregationIgnoreColumns
    ) {
        if (aggregationIgnoreColumns != null) {
            return aggregationIgnoreColumns.stream().map(this::aggregationColumnName).toList();
        }
        return null;
    }

    protected AggregationColumnNameMapping aggregationTableAggregationFactCount(
        AggregationTableMapping aggregationTable
    ) {
        return aggregationColumnName(aggregationTable.getAggregationFactCount());
    }

    protected AggregationColumnNameMapping aggregationColumnName(AggregationColumnNameMapping aggregationColumnName) {
        if (aggregationColumnName != null) {
            String column = aggregationColumnNameColumn(aggregationColumnName);
            return new_AggregationColumn(column);
        }
        return null;
    }

    protected String aggregationColumnNameColumn(AggregationColumnNameMapping aggregationColumnName) {
        return aggregationColumnName.getColumn();
    }

    protected abstract AggregationColumnNameMapping new_AggregationColumn(String column);

    protected String tableQuerySchema(TableQueryMapping tableQuery) {
        return tableQuery.getSchema();
    }

    protected String tableQueryName(TableQueryMapping tableQuery) {
        return tableQuery.getName();
    }

    protected List<? extends TableQueryOptimizationHintMapping> tableQueryOptimizationHints(
        TableQueryMapping tableQuery
    ) {
        return tableQueryOptimizationHints(tableQuery.getOptimizationHints());
    }

    protected List<? extends TableQueryOptimizationHintMapping> tableQueryOptimizationHints(
        List<? extends TableQueryOptimizationHintMapping> optimizationHints
    ) {
        if (optimizationHints != null) {
            return optimizationHints.stream().map(this::tableQueryOptimizationHint).toList();
        }
        return null;
    }

    protected TableQueryOptimizationHintMapping tableQueryOptimizationHint(
        TableQueryOptimizationHintMapping tableQueryOptimizationHint
    ) {
        if (tableQueryOptimizationHint != null) {
            String value = tableQueryOptimizationHintValue(tableQueryOptimizationHint);
            String type = tableQueryOptimizationHintType(tableQueryOptimizationHint);
            return new_TableQueryOptimizationHint(value, type);
        }
        return null;
    }

    protected String tableQueryOptimizationHintType(TableQueryOptimizationHintMapping tableQueryOptimizationHint) {
        return tableQueryOptimizationHint.getType();
    }

    protected String tableQueryOptimizationHintValue(TableQueryOptimizationHintMapping tableQueryOptimizationHint) {
        return tableQueryOptimizationHint.getValue();
    }

    protected abstract TableQueryOptimizationHintMapping new_TableQueryOptimizationHint(String value, String type);

    protected List<? extends AggregationExcludeMapping> tableQueryAggregationExcludes(TableQueryMapping tableQuery) {
        return aggregationExcludes(tableQuery.getAggregationExcludes());
    }

    protected List<? extends AggregationExcludeMapping> aggregationExcludes(
        List<? extends AggregationExcludeMapping> aggregationExcludes
    ) {
        if (aggregationExcludes != null) {
            return aggregationExcludes.stream().map(this::aggregationExclude).toList();
        }
        return null;
    }

    protected AggregationExcludeMapping aggregationExclude(AggregationExcludeMapping aggregationExclude) {
        if (aggregationExclude != null) {
            boolean ignorecase = aggregationExcludeIgnorecase(aggregationExclude);
            String name = aggregationExcludeName(aggregationExclude);
            String pattern = aggregationExcludePattern(aggregationExclude);
            String id = aggregationExcludeId(aggregationExclude);
            return new_AggregationExclude(ignorecase, name, pattern, id);
        }
        return null;
    }

    protected abstract AggregationExcludeMapping new_AggregationExclude(
        boolean ignorecase, String name, String pattern,
        String id
    );

    protected String aggregationExcludeId(AggregationExcludeMapping aggregationExclude) {
        return aggregationExclude.getId();
    }

    protected String aggregationExcludePattern(AggregationExcludeMapping aggregationExclude) {
        return aggregationExclude.getPattern();
    }

    protected String aggregationExcludeName(AggregationExcludeMapping aggregationExclude) {
        return aggregationExclude.getName();
    }

    protected boolean aggregationExcludeIgnorecase(AggregationExcludeMapping aggregationExclude) {
        return aggregationExclude.isIgnorecase();
    }

    protected SQLMapping tableQuerySqlWhereExpression(TableQueryMapping tableQuery) {
        return sql(tableQuery.getSqlWhereExpression());
    }

    protected SQLMapping sql(SQLMapping sql) {
        if (sql != null) {
            List<String> dialects = sqlDialects(sql);
            String statement = sqlStatement(sql);
            return new_SQL(dialects, statement);
        }
        return null;
    }

    protected String sqlStatement(SQLMapping sql) {
        return sql.getStatement();
    }

    protected abstract SQLMapping new_SQL(List<String> dialects, String statement);

    protected List<String> sqlDialects(SQLMapping sql) {
        return dialects(sql.getDialects());
    }

    protected List<String> dialects(List<String> dialects) {
        if (dialects != null) {
            return dialects.stream().map(d -> d).toList();
        }
        return null;
    }

    protected String tableQueryAlias(TableQueryMapping tableQuery) {
        return tableQuery.getAlias();
    }

    protected boolean hierarchyVisible(HierarchyMapping hierarchy) {
        return hierarchy.isVisible();
    }

    protected String hierarchyUniqueKeyLevelName(HierarchyMapping hierarchy) {
        return hierarchy.getUniqueKeyLevelName();
    }

    protected String hierarchyPrimaryKeyTable(HierarchyMapping hierarchy) {
        return hierarchy.getPrimaryKeyTable();
    }

    protected String hierarchyPrimaryKey(HierarchyMapping hierarchy) {
        return hierarchy.getPrimaryKey();
    }

    protected String hierarchyOrigin(HierarchyMapping hierarchy) {
        return hierarchy.getOrigin();
    }

    protected String hierarchyMemberReaderClass(HierarchyMapping hierarchy) {
        return hierarchy.getMemberReaderClass();
    }

    protected boolean hierarchyHasAll(HierarchyMapping hierarchy) {
        return hierarchy.isHasAll();
    }

    protected String hierarchyDisplayFolder(HierarchyMapping hierarchy) {
        return hierarchy.getDisplayFolder();
    }

    protected String hierarchyDefaultMember(HierarchyMapping hierarchy) {
        return hierarchy.getDefaultMember();
    }

    protected String hierarchyAllMemberName(HierarchyMapping hierarchy) {
        return hierarchy.getAllMemberName();
    }

    protected String hierarchyAllMemberCaption(HierarchyMapping hierarchy) {
        return hierarchy.getAllMemberCaption();
    }

    protected String hierarchyAllLevelName(HierarchyMapping hierarchy) {
        return hierarchy.getAllLevelName();
    }

    protected List<? extends MemberReaderParameterMapping> hierarchyMemberReaderParameters(HierarchyMapping hierarchy) {
        return memberReaderParameters(hierarchy.getMemberReaderParameters());
    }

    protected List<? extends MemberReaderParameterMapping> memberReaderParameters(
        List<? extends MemberReaderParameterMapping> memberReaderParameters
    ) {
        if (memberReaderParameters != null) {
            return memberReaderParameters.stream().map(this::memberReaderParameter).toList();
        }
        return null;
    }

    protected MemberReaderParameterMapping memberReaderParameter(MemberReaderParameterMapping memberReaderParameter) {
        if (memberReaderParameter != null) {
            String name = memberReaderParameterName(memberReaderParameter);
            String value = memberReaderParameterValue(memberReaderParameter);
            return new_MemberReaderParameter(name, value);
        }
        return null;
    }

    protected String memberReaderParameterName(MemberReaderParameterMapping memberReaderParameter) {
        return memberReaderParameter.getName();
    }

    protected String memberReaderParameterValue(MemberReaderParameterMapping memberReaderParameter) {
        return memberReaderParameter.getValue();

    }

    protected abstract MemberReaderParameterMapping new_MemberReaderParameter(String name, String value);

    protected List<? extends LevelMapping> hierarchyLevels(HierarchyMapping hierarchy) {
        return levels(hierarchy.getLevels());
    }

    protected List<? extends LevelMapping> levels(List<? extends LevelMapping> levels) {
        if (levels != null) {
            return levels.stream().map(this::level).toList();
        }
        return null;
    }

    protected DocumentationMapping hierarchyDocumentation(HierarchyMapping hierarchy) {
        return documentation(hierarchy.getDocumentation());
    }

    protected String hierarchyName(HierarchyMapping hierarchy) {
        return hierarchy.getName();
    }

    protected String hierarchyDescription(HierarchyMapping hierarchy) {
        return hierarchy.getDescription();
    }

    protected String hierarchyId(HierarchyMapping hierarchy) {
        return hierarchy.getId();
    }

    protected List<? extends AnnotationMapping> hierarchyAnnotations(HierarchyMapping hierarchy) {
        return annotations(hierarchy.getAnnotations());
    }

    protected abstract HierarchyMapping new_Hierarchy(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation, List<? extends LevelMapping> levels,
        List<? extends MemberReaderParameterMapping> memberReaderParameters, String allLevelName,
        String allMemberCaption, String allMemberName, String defaultMember, String displayFolder, boolean hasAll,
        String memberReaderClass, String origin, String primaryKey, String primaryKeyTable,
        String uniqueKeyLevelName, boolean visible, QueryMapping query
    );

    protected LevelMapping accessHierarchyGrantTopLevel(AccessHierarchyGrantMapping accessHierarchyGrant) {
        return level(accessHierarchyGrant.getTopLevel());
    }

    protected String accessHierarchyGrantRollupPolicy(AccessHierarchyGrantMapping accessHierarchyGrant) {
        return accessHierarchyGrant.getRollupPolicy();
    }

    protected LevelMapping accessHierarchyGrantBottomLevel(AccessHierarchyGrantMapping accessHierarchyGrant) {
        return level(accessHierarchyGrant.getBottomLevel());
    }

    protected LevelMapping level(LevelMapping level) {
        if (level != null) {
            SQLExpressionMapping keyExpression = levelKeyExpression(level);
            SQLExpressionMapping nameExpression = levelNameExpression(level);
            SQLExpressionMapping captionExpression = levelCaptionExpression(level);
            SQLExpressionMapping ordinalExpression = levelOrdinalExpression(level);
            SQLExpressionMapping parentExpression = levelParentExpression(level);
            ParentChildLinkMapping parentChildLink = levelParentChildLink(level);
            List<? extends MemberPropertyMapping> memberProperties = levelMemberProperties(level);
            MemberFormatterMapping memberFormatter = levelMemberFormatter(level);
            String approxRowCount = levelApproxRowCount(level);
            String captionColumn = levelCaptionColumn(level);
            String column = levelColumn(level);
            String hideMemberIf = levelHideMemberIf(level);
            String internalType = levelInternalType(level);
            String levelType = levelLevelType(level);
            String nameColumn = levelNameColumn(level);
            String nullParentValue = levelNullParentValue(level);
            String ordinalColumn = levelOrdinalColumn(level);
            String parentColumn = levelParentColumn(level);
            String table = levelTable(level);
            String type = levelType(level);
            boolean uniqueMembers = levelUniqueMembers(level);
            boolean visible = levelVisible(level);
            String name = levelName(level);
            String id = levelId(level);
            return new_Level(keyExpression, nameExpression, captionExpression, ordinalExpression, parentExpression,
                parentChildLink, memberProperties, memberFormatter, approxRowCount, captionColumn, column,
                hideMemberIf, internalType, levelType, nameColumn, nullParentValue, ordinalColumn, parentColumn,
                table, type, uniqueMembers, visible, name, id);
        }
        return null;
    }

    protected String levelId(LevelMapping level) {
        return level.getId();
    }

    protected String levelName(LevelMapping level) {
        return level.getName();
    }

    protected boolean levelVisible(LevelMapping level) {
        return level.isVisible();
    }

    protected boolean levelUniqueMembers(LevelMapping level) {
        return level.isUniqueMembers();
    }

    protected String levelType(LevelMapping level) {
        return level.getLevelType();
    }

    protected String levelTable(LevelMapping level) {
        return level.getTable();
    }

    protected String levelParentColumn(LevelMapping level) {
        return level.getParentColumn();
    }

    protected String levelOrdinalColumn(LevelMapping level) {
        return level.getOrdinalColumn();
    }

    protected String levelNullParentValue(LevelMapping level) {
        return level.getNullParentValue();
    }

    protected String levelNameColumn(LevelMapping level) {
        return level.getNameColumn();
    }

    protected String levelLevelType(LevelMapping level) {
        return level.getLevelType();
    }

    protected String levelInternalType(LevelMapping level) {
        return level.getInternalType();
    }

    protected String levelHideMemberIf(LevelMapping level) {
        return level.getHideMemberIf();
    }

    protected String levelColumn(LevelMapping level) {
        return level.getColumn();
    }

    protected String levelCaptionColumn(LevelMapping level) {
        return level.getCaptionColumn();
    }

    protected String levelApproxRowCount(LevelMapping level) {
        return level.getApproxRowCount();
    }

    protected MemberFormatterMapping levelMemberFormatter(LevelMapping level) {
        return memberFormatter(level.getMemberFormatter());
    }

    protected MemberFormatterMapping memberFormatter(MemberFormatterMapping memberFormatter) {
        if (memberFormatter != null) {
            List<? extends AnnotationMapping> annotations = memberFormatterAnnotations(memberFormatter);
            String id = memberFormatterId(memberFormatter);
            String description = memberFormatterDescription(memberFormatter);
            String name = memberFormatterName(memberFormatter);
            DocumentationMapping documentation = memberFormatterDocumentation(memberFormatter);
            String ref = memberFormatterRef(memberFormatter);
            return new_MemberFormatter(annotations, id, description, name, documentation, ref);
        }
        return null;
    }

    protected abstract MemberFormatterMapping new_MemberFormatter(
        List<? extends AnnotationMapping> annotations,
        String id, String description, String name, DocumentationMapping documentation, String ref
    );

    protected String memberFormatterRef(MemberFormatterMapping memberFormatter) {
        return memberFormatter.getRef();
    }

    protected DocumentationMapping memberFormatterDocumentation(MemberFormatterMapping memberFormatter) {
        return documentation(memberFormatter.getDocumentation());
    }

    protected String memberFormatterName(MemberFormatterMapping memberFormatter) {
        return memberFormatter.getName();
    }

    protected String memberFormatterDescription(MemberFormatterMapping memberFormatter) {
        return memberFormatter.getDescription();
    }

    protected String memberFormatterId(MemberFormatterMapping memberFormatter) {
        return memberFormatter.getId();
    }

    protected List<? extends AnnotationMapping> memberFormatterAnnotations(MemberFormatterMapping memberFormatter) {
        return annotations(memberFormatter.getAnnotations());
    }

    protected List<? extends MemberPropertyMapping> levelMemberProperties(LevelMapping level) {
        return memberProperties(level.getMemberProperties());
    }

    protected List<? extends MemberPropertyMapping> memberProperties(
        List<? extends MemberPropertyMapping> memberProperties
    ) {
        if (memberProperties != null) {
            return memberProperties.stream().map(this::memberProperty).toList();
        }
        return null;
    }

    protected MemberPropertyMapping memberProperty(MemberPropertyMapping memberProperty) {
        if (memberProperty != null) {
            List<? extends AnnotationMapping> annotations = memberPropertyAnnotations(memberProperty);
            String id = memberPropertyId(memberProperty);
            String description = memberPropertyDescription(memberProperty);
            String name = memberPropertyName(memberProperty);
            DocumentationMapping documentation = memberPropertyDocumentation(memberProperty);

            MemberPropertyFormatterMapping formatter = memberPropertyFormatter(memberProperty);
            String column = memberPropertyId(memberProperty);
            boolean dependsOnLevelValue = memberPropertyDependsOnLevelValue(memberProperty);
            String type = memberPropertyType(memberProperty);

            return new_MemberProperty(annotations, id, description, name, documentation, formatter, column,
                dependsOnLevelValue, type);
        }
        return null;
    }

    protected abstract MemberPropertyMapping new_MemberProperty(
        List<? extends AnnotationMapping> annotations,
        String id, String description, String name, DocumentationMapping documentation,
        MemberPropertyFormatterMapping formatter, String column, boolean dependsOnLevelValue, String type
    );

    protected String memberPropertyType(MemberPropertyMapping memberProperty) {
        return memberProperty.getType();
    }

    protected boolean memberPropertyDependsOnLevelValue(MemberPropertyMapping memberProperty) {
        return memberProperty.isDependsOnLevelValue();
    }

    protected MemberPropertyFormatterMapping memberPropertyFormatter(MemberPropertyMapping memberProperty) {
        return memberProperty.getFormatter();
    }

    protected DocumentationMapping memberPropertyDocumentation(MemberPropertyMapping memberProperty) {
        return documentation(memberProperty.getDocumentation());
    }

    protected String memberPropertyName(MemberPropertyMapping memberProperty) {
        return memberProperty.getName();
    }

    protected String memberPropertyDescription(MemberPropertyMapping memberProperty) {
        return memberProperty.getDescription();
    }

    protected String memberPropertyId(MemberPropertyMapping memberProperty) {
        return memberProperty.getId();
    }

    protected List<? extends AnnotationMapping> memberPropertyAnnotations(MemberPropertyMapping memberProperty) {
        return annotations(memberProperty.getAnnotations());
    }

    protected ParentChildLinkMapping levelParentChildLink(LevelMapping level) {
        return parentChildLink(level.getParentChildLink());
    }

    protected ParentChildLinkMapping parentChildLink(ParentChildLinkMapping parentChildLink) {
        if (parentChildLink != null) {
            TableQueryMapping table = parentChildLinkTable(parentChildLink);
            String childColumn = parentChildLinkChildColumn(parentChildLink);
            String parentColumn = parentChildLinkParentColumn(parentChildLink);
            return new_ParentChildLink(table, childColumn, parentColumn);
        }
        return null;
    }

    protected String parentChildLinkParentColumn(ParentChildLinkMapping parentChildLink) {
        return parentChildLink.getParentColumn();
    }

    protected String parentChildLinkChildColumn(ParentChildLinkMapping parentChildLink) {
        return parentChildLink.getChildColumn();
    }

    protected TableQueryMapping parentChildLinkTable(ParentChildLinkMapping parentChildLink) {
        return tableQuery(parentChildLink.getTable());
    }

    protected abstract ParentChildLinkMapping new_ParentChildLink(
        TableQueryMapping table, String childColumn,
        String parentColumn
    );

    protected SQLExpressionMapping levelParentExpression(LevelMapping level) {
        return sqlExpression(level.getParentExpression());
    }

    protected SQLExpressionMapping sqlExpression(SQLExpressionMapping sqlExpression) {
        if (sqlExpression != null) {
            List<? extends SQLMapping> sqls = sqlExpressionSqls(sqlExpression);
            return new_SQLExpression(sqls);
        }
        return null;
    }

    protected abstract SQLExpressionMapping new_SQLExpression(List<? extends SQLMapping> sqls);

    protected List<? extends SQLMapping> sqlExpressionSqls(SQLExpressionMapping sqlExpression) {
        return sqls(sqlExpression.getSqls());
    }

    protected SQLExpressionMapping levelOrdinalExpression(LevelMapping level) {
        return sqlExpression(level.getOrdinalExpression());
    }

    protected SQLExpressionMapping levelCaptionExpression(LevelMapping level) {
        return sqlExpression(level.getCaptionExpression());
    }

    protected SQLExpressionMapping levelNameExpression(LevelMapping level) {
        return sqlExpression(level.getNameExpression());
    }

    protected SQLExpressionMapping levelKeyExpression(LevelMapping level) {
        return sqlExpression(level.getKeyExpression());
    }

    protected abstract LevelMapping new_Level(
        SQLExpressionMapping keyExpression, SQLExpressionMapping nameExpression,
        SQLExpressionMapping captionExpression, SQLExpressionMapping ordinalExpression,
        SQLExpressionMapping parentExpression, ParentChildLinkMapping parentChildLink,
        List<? extends MemberPropertyMapping> memberProperties, MemberFormatterMapping memberFormatter,
        String approxRowCount, String captionColumn, String column, String hideMemberIf, String internalType,
        String levelType, String nameColumn, String nullParentValue, String ordinalColumn, String parentColumn,
        String table, String type, boolean uniqueMembers, boolean visible, String name, String id
    );

    protected String accessHierarchyGrantAccess(AccessHierarchyGrantMapping accessHierarchyGrant) {
        return accessHierarchyGrant.getAccess();
    }

    protected abstract AccessHierarchyGrantMapping new_AccessHierarchyGrant(
        List<? extends AccessMemberGrantMapping> memberGrants, String access, LevelMapping bottomLevel,
        String rollupPolicy, LevelMapping topLevel, HierarchyMapping hierarchy
    );

    protected CubeMapping accessCubeGrantCube(AccessCubeGrantMapping accessCubeGrant) {
        return cube(accessCubeGrant.getCube());
    }

    protected String accessCubeGrantAccess(AccessCubeGrantMapping accessCubeGrant) {
        return accessCubeGrant.getAccess();
    }

    protected List<? extends AccessDimensionGrantMapping> accessCubeGrantAccessDimension(
        AccessCubeGrantMapping accessCubeGrant
    ) {
        return accessDimensionGrants(accessCubeGrant.getDimensionGrants());
    }

    protected List<? extends AccessDimensionGrantMapping> accessDimensionGrants(
        List<? extends AccessDimensionGrantMapping> accessDimensionGrants
    ) {
        if (accessDimensionGrants != null) {
            return accessDimensionGrants.stream().map(this::accessDimensionGrant).toList();
        }
        return null;
    }

    protected AccessDimensionGrantMapping accessDimensionGrant(AccessDimensionGrantMapping accessDimensionGrant) {
        if (accessDimensionGrant != null) {
            String access = accessDimensionGrantAccess(accessDimensionGrant);
            DimensionMapping dimension = accessDimensionGrantDimension(accessDimensionGrant);

            return new_AccessDimensionGrant(access, dimension);
        }
        return null;
    }

    protected DimensionMapping accessDimensionGrantDimension(AccessDimensionGrantMapping accessDimensionGrant) {
        return dimension(accessDimensionGrant.getDimension());
    }

    protected DimensionMapping dimension(DimensionMapping dimension) {
        if (dimension != null) {
            List<? extends AnnotationMapping> annotations = dimensionAnnotations(dimension);
            String id = dimensionId(dimension);
            String description = dimensionDescription(dimension);
            String name = dimensionName(dimension);
            DocumentationMapping documentation = dimensionDocumentation(dimension);

            List<? extends HierarchyMapping> hierarchies = dimensionHierarchies(dimension);
            String usagePrefix = dimensionUsagePrefix(dimension);
            boolean visible = dimensionVisible(dimension);
            return new_Dimension(annotations, id, description, name, documentation, hierarchies, usagePrefix, visible);
        }
        return null;
    }

    protected abstract DimensionMapping new_Dimension(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation,
        List<? extends HierarchyMapping> hierarchies, String usagePrefix, boolean visible
    );

    protected boolean dimensionVisible(DimensionMapping dimension) {
        return dimension.isVisible();
    }

    protected String dimensionUsagePrefix(DimensionMapping dimension) {
        return dimension.getUsagePrefix();
    }

    protected List<? extends HierarchyMapping> dimensionHierarchies(DimensionMapping dimension) {
        return hierarchies(dimension.getHierarchies());
    }

    protected List<? extends HierarchyMapping> hierarchies(List<? extends HierarchyMapping> hierarchies) {
        if (hierarchies != null) {
            return hierarchies.stream().map(this::hierarchy).toList();
        }
        return null;
    }

    protected DocumentationMapping dimensionDocumentation(DimensionMapping dimension) {
        return documentation(dimension.getDocumentation());
    }

    protected String dimensionName(DimensionMapping dimension) {
        return dimension.getName();
    }

    protected String dimensionDescription(DimensionMapping dimension) {
        return dimension.getDescription();
    }

    protected String dimensionId(DimensionMapping dimension) {
        return dimension.getId();
    }

    protected List<? extends AnnotationMapping> dimensionAnnotations(DimensionMapping dimension) {
        return annotations(dimension.getAnnotations());
    }

    protected String accessDimensionGrantAccess(AccessDimensionGrantMapping accessDimensionGrant) {
        return accessDimensionGrant.getAccess();
    }

    protected abstract AccessDimensionGrantMapping new_AccessDimensionGrant(String access, DimensionMapping dimension);

    protected abstract AccessCubeGrantMapping new_AccessCubeGrant(
        List<? extends AccessDimensionGrantMapping> dimensionGrants,
        List<? extends AccessHierarchyGrantMapping> hierarchyGrants, String access, CubeMapping cube
    );

    protected String accessSchemaGrantAccess(AccessSchemaGrantMapping accessSchemaGrant) {
        return accessSchemaGrant.getAccess();
    }

    protected abstract AccessSchemaGrantMapping new_AccessSchemaGrant(
        List<? extends AccessCubeGrantMapping> accessCubeGrant, String access
    );

    protected List<? extends AccessRoleMapping> schemaAccessRoles(SchemaMapping schemaMappingOriginal) {
        return accessRoles(schemaMappingOriginal.getAccessRoles());
    }

    protected List<? extends AccessRoleMapping> accessRoles(List<? extends AccessRoleMapping> accessRoles) {
        if (accessRoles != null) {
            return accessRoles.stream().map(this::accessRole).toList();
        }
        return null;
    }

    protected List<? extends NamedSetMapping> schemaNamedSets(SchemaMapping schemaMappingOriginal) {
        return namedSets(schemaMappingOriginal.getNamedSets());
    }

    protected List<? extends NamedSetMapping> namedSets(List<? extends NamedSetMapping> namedSets) {
        if (namedSets != null) {
            return namedSets.stream().map(this::namedSet).toList();
        }
        return null;
    }

    protected NamedSetMapping namedSet(NamedSetMapping namedSet) {
        if (namedSet != null) {
            List<? extends AnnotationMapping> annotations = namedSetAnnotations(namedSet);
            String id = namedSetId(namedSet);
            String description = namedSetDescription(namedSet);
            String name = namedSetName(namedSet);
            DocumentationMapping documentation = namedSetDocumentation(namedSet);

            String displayFolder = namedSetDisplayFolder(namedSet);
            String formula = namedSetFormula(namedSet);
            return new_NamedSet(annotations, id, description, name, documentation, displayFolder, formula);
        }
        return null;
    }

    protected abstract NamedSetMapping new_NamedSet(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation, String displayFolder, String formula
    );

    protected String namedSetFormula(NamedSetMapping namedSet) {
        return namedSet.getFormula();
    }

    protected String namedSetDisplayFolder(NamedSetMapping namedSet) {
        return namedSet.getDisplayFolder();
    }

    protected DocumentationMapping namedSetDocumentation(NamedSetMapping namedSet) {
        return documentation(namedSet.getDocumentation());
    }

    protected String namedSetName(NamedSetMapping namedSet) {
        return namedSet.getName();
    }

    protected String namedSetDescription(NamedSetMapping namedSet) {
        return namedSet.getDescription();
    }

    protected String namedSetId(NamedSetMapping namedSet) {
        return namedSet.getId();
    }

    protected List<? extends AnnotationMapping> namedSetAnnotations(NamedSetMapping namedSet) {
        return annotations(namedSet.getAnnotations());
    }

    protected List<? extends CubeMapping> schemaCubes(SchemaMapping schemaMappingOriginal) {
        return cubes(schemaMappingOriginal.getCubes());
    }

    protected List<? extends CubeMapping> cubes(List<? extends CubeMapping> cubes) {
        if (cubes != null) {
            return cubes.stream().map(this::cube).toList();
        }
        return null;
    }

    protected CubeMapping cube(CubeMapping cube) {
        if (cube != null) {
            List<? extends AnnotationMapping> annotations = cubeAnnotations(cube);
            String id = cubeId(cube);
            String description = cubeDescription(cube);
            String name = cubeName(cube);
            DocumentationMapping documentation = cubeDocumentation(cube);

            List<? extends DimensionConnectorMapping> dimensionConnectors = cubeDimensionConnectors(cube);
            List<? extends CalculatedMemberMapping> calculatedMembers = cubeCalculatedMembers(cube);
            List<? extends NamedSetMapping> namedSets = cubeNamedSets(cube);
            List<? extends KpiMapping> kpis = cubeKpis(cube);
            MeasureMapping defaultMeasure = cubeDefaultMeasure(cube);
            boolean enabled = cubeEnabled(cube);
            boolean visible = cubeVisible(cube);
            List<? extends MeasureGroupMapping> measureGroups = getMeasureGroups(cube);
            return new_Cube(annotations, id, description, name, documentation, dimensionConnectors, calculatedMembers,
                namedSets, kpis, defaultMeasure, enabled, visible, measureGroups);
        }
        return null;
    }

    protected abstract CubeMapping new_Cube(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation,
        List<? extends DimensionConnectorMapping> dimensionConnectors,
        List<? extends CalculatedMemberMapping> calculatedMembers, List<? extends NamedSetMapping> namedSets,
        List<? extends KpiMapping> kpis, MeasureMapping defaultMeasure, boolean enabled, boolean visible,
        List<? extends MeasureGroupMapping> measureGroups
    );

    protected List<? extends MeasureGroupMapping> getMeasureGroups(CubeMapping cube) {
        return measureGroups(cube.getMeasureGroups());
    }

    protected List<? extends MeasureGroupMapping> measureGroups(List<? extends MeasureGroupMapping> measureGroups) {
        if (measureGroups != null) {
            return measureGroups.stream().map(this::measureGroup).toList();
        }
        return null;
    }

    protected MeasureGroupMapping measureGroup(MeasureGroupMapping measureGroup) {
        if (measureGroup != null) {
            List<? extends MeasureMapping> measures = measureGroupMeasures(measureGroup);
            String name = measureGroupName(measureGroup);
            return new_MeasureGroup(measures, name);
        }
        return null;
    }

    protected List<? extends MeasureMapping> measureGroupMeasures(MeasureGroupMapping measureGroup) {
        return measures(measureGroup.getMeasures());
    }

    protected List<? extends MeasureMapping> measures(List<? extends MeasureMapping> measures) {
        if (measures != null) {
            return measures.stream().map(this::measure).toList();
        }
        return null;
    }

    protected String measureGroupName(MeasureGroupMapping measureGroup) {
        return measureGroup.getName();
    }

    protected abstract MeasureGroupMapping new_MeasureGroup(List<? extends MeasureMapping> measures, String name);

    protected boolean cubeVisible(CubeMapping cube) {
        return cube.isVisible();
    }

    protected boolean cubeEnabled(CubeMapping cube) {
        return cube.isEnabled();
    }

    protected MeasureMapping cubeDefaultMeasure(CubeMapping cube) {
        return measure(cube.getDefaultMeasure());
    }

    protected MeasureMapping measure(MeasureMapping measure) {
        if (measure != null) {
            SQLExpressionMapping measureExpression = measureMeasureExpression(measure);
            List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperty = measureCalculatedMemberProperty(
                measure);
            CellFormatterMapping cellFormatter = measureCellFormatter(measure);
            String backColor = measureBackColor(measure);
            String column = measureColumn(measure);
            String datatype = measureDatatype(measure);
            String displayFolder = measureDisplayFolder(measure);
            String formatString = measureFormatString(measure);
            String formatter = measureFormatter(measure);
            boolean visible = measureVisible(measure);
            String name = measureName(measure);
            String id = measureId(measure);
            String type = measureType(measure);
            return new_Measure(measureExpression, calculatedMemberProperty, cellFormatter, backColor, column, datatype,
                displayFolder, formatString, formatter, visible, name, id, type);
        }
        return null;
    }

    protected abstract MeasureMapping new_Measure(
        SQLExpressionMapping measureExpression,
        List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperty,
        CellFormatterMapping cellFormatter, String backColor, String column, String datatype, String displayFolder,
        String formatString, String formatter, boolean visible, String name, String id, String type
    );

    private String measureType(MeasureMapping measure) {
        return measure.getType();
    }

    private String measureId(MeasureMapping measure) {
        return measure.getId();
    }

    private String measureName(MeasureMapping measure) {
        return measure.getName();
    }

    private boolean measureVisible(MeasureMapping measure) {
        return measure.isVisible();
    }

    private String measureFormatter(MeasureMapping measure) {
        return measure.getFormatter();
    }

    private String measureFormatString(MeasureMapping measure) {
        return measure.getFormatString();
    }

    private String measureDisplayFolder(MeasureMapping measure) {
        return measure.getDisplayFolder();
    }

    private String measureDatatype(MeasureMapping measure) {
        return measure.getDatatype();
    }

    private String measureColumn(MeasureMapping measure) {
        return measure.getColumn();

    }

    private String measureBackColor(MeasureMapping measure) {
        return measure.getBackColor();
    }

    private CellFormatterMapping measureCellFormatter(MeasureMapping measure) {
        return cellFormatter(measure.getCellFormatter());
    }

    private CellFormatterMapping cellFormatter(CellFormatterMapping cellFormatter) {
        if (cellFormatter != null) {
            List<? extends AnnotationMapping> annotations = cellFormatterAnnotations(cellFormatter);
            String id = cellFormatterId(cellFormatter);
            String description = cellFormatterDescription(cellFormatter);
            String name = cellFormatterName(cellFormatter);
            DocumentationMapping documentation = cellFormatterDocumentation(cellFormatter);
            String ref = cellFormatterRef(cellFormatter);
            return new_CellFormatter(annotations, id, description, name, documentation, ref);
        }
        return null;
    }

    private String cellFormatterRef(CellFormatterMapping cellFormatter) {
        return cellFormatter.getRef();
    }

    private DocumentationMapping cellFormatterDocumentation(CellFormatterMapping cellFormatter) {
        return documentation(cellFormatter.getDocumentation());
    }

    private String cellFormatterName(CellFormatterMapping cellFormatter) {
        return cellFormatter.getName();
    }

    private String cellFormatterDescription(CellFormatterMapping cellFormatter) {
        return cellFormatter.getDescription();
    }

    private String cellFormatterId(CellFormatterMapping cellFormatter) {
        return cellFormatter.getId();
    }

    private List<? extends AnnotationMapping> cellFormatterAnnotations(CellFormatterMapping cellFormatter) {
        return annotations(cellFormatter.getAnnotations());
    }

    protected abstract CellFormatterMapping new_CellFormatter(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation, String ref
    );

    private List<? extends CalculatedMemberPropertyMapping> measureCalculatedMemberProperty(MeasureMapping measure) {
        return calculatedMemberProperties(measure.getCalculatedMemberProperty());
    }

    private List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties(
        List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties
    ) {
        if (calculatedMemberProperties != null) {
            return calculatedMemberProperties.stream().map(this::calculatedMemberProperty).toList();
        }
        return null;
    }

    protected CalculatedMemberPropertyMapping calculatedMemberProperty(
        CalculatedMemberPropertyMapping calculatedMemberProperty
    ) {
        if (calculatedMemberProperty != null) {
            List<? extends AnnotationMapping> annotations = calculatedMemberPropertyAnnotations(
                calculatedMemberProperty);
            String id = calculatedMemberPropertyId(calculatedMemberProperty);
            String description = calculatedMemberPropertyDescription(calculatedMemberProperty);
            String name = calculatedMemberPropertyName(calculatedMemberProperty);
            DocumentationMapping documentation = calculatedMemberPropertyDocumentation(calculatedMemberProperty);

            String expression = calculatedMemberPropertyExpression(calculatedMemberProperty);
            String value = calculatedMemberPropertyValue(calculatedMemberProperty);

            return new_CalculatedMemberProperty(annotations, id, description, name, documentation, expression, value);
        }
        return null;
    }

    protected abstract CalculatedMemberPropertyMapping new_CalculatedMemberProperty(
        List<? extends AnnotationMapping> annotations, String id, String description, String name,
        DocumentationMapping documentation, String expression, String value
    );

    private String calculatedMemberPropertyValue(CalculatedMemberPropertyMapping calculatedMemberProperty) {
        return calculatedMemberProperty.getValue();
    }

    private String calculatedMemberPropertyExpression(CalculatedMemberPropertyMapping calculatedMemberProperty) {
        return calculatedMemberProperty.getExpression();
    }

    private DocumentationMapping calculatedMemberPropertyDocumentation(
        CalculatedMemberPropertyMapping calculatedMemberProperty
    ) {
        return documentation(calculatedMemberProperty.getDocumentation());
    }

    private String calculatedMemberPropertyName(CalculatedMemberPropertyMapping calculatedMemberProperty) {
        return calculatedMemberProperty.getName();
    }

    private String calculatedMemberPropertyDescription(CalculatedMemberPropertyMapping calculatedMemberProperty) {
        return calculatedMemberProperty.getDescription();
    }

    private String calculatedMemberPropertyId(CalculatedMemberPropertyMapping calculatedMemberProperty) {
        return calculatedMemberProperty.getId();
    }

    private List<? extends AnnotationMapping> calculatedMemberPropertyAnnotations(
        CalculatedMemberPropertyMapping calculatedMemberProperty
    ) {
        return annotations(calculatedMemberProperty.getAnnotations());
    }

    private SQLExpressionMapping measureMeasureExpression(MeasureMapping measure) {
        return sqlExpression(measure.getMeasureExpression());
    }

    protected List<? extends KpiMapping> cubeKpis(CubeMapping cube) {
        return kpis(cube.getKpis());
    }

    protected List<? extends KpiMapping> kpis(List<? extends KpiMapping> kpis) {
        if (kpis != null) {
            return kpis.stream().map(this::kpi).toList();
        }
        return null;
    }

    protected KpiMapping kpi(KpiMapping kpi) {
        if (kpi != null) {
            List<? extends AnnotationMapping> annotations = kpiAnnotations(kpi);
            String id = kpiId(kpi);
            String description = kpiDescription(kpi);
            String name = kpiName(kpi);
            DocumentationMapping documentation = kpiDocumentation(kpi);

            List<? extends TranslationMapping> translations = kpiTranslations(kpi);
            String displayFolder = kpiDisplayFolder(kpi);
            String associatedMeasureGroupID = kpiAssociatedMeasureGroupID(kpi);
            String value = kpiValue(kpi);
            String goal = kpiGoal(kpi);
            String status = kpiStatus(kpi);
            String trend = kpiTrend(kpi);
            String weight = kpiWeight(kpi);
            String trendGraphic = kpiTrendGraphic(kpi);
            String statusGraphic = kpiStatusGraphic(kpi);
            String currentTimeMember = kpiCurrentTimeMember(kpi);
            String parentKpiID = kpiParentKpiID(kpi);
            return new_Kpi(annotations, id, description, name, documentation, translations, displayFolder,
                associatedMeasureGroupID, value, goal, status, trend, weight, trendGraphic, statusGraphic,
                currentTimeMember, parentKpiID);
        }
        return null;
    }

    private String kpiParentKpiID(KpiMapping kpi) {
        return kpi.getParentKpiID();
    }

    private String kpiCurrentTimeMember(KpiMapping kpi) {
        return kpi.getCurrentTimeMember();
    }

    private String kpiStatusGraphic(KpiMapping kpi) {
        return kpi.getStatusGraphic();
    }

    private String kpiTrendGraphic(KpiMapping kpi) {
        return kpi.getTrendGraphic();
    }

    private String kpiWeight(KpiMapping kpi) {
        return kpi.getWeight();
    }

    private String kpiTrend(KpiMapping kpi) {
        return kpi.getTrend();
    }

    private String kpiStatus(KpiMapping kpi) {
        return kpi.getStatus();
    }

    private String kpiGoal(KpiMapping kpi) {
        return kpi.getGoal();
    }

    private String kpiValue(KpiMapping kpi) {
        return kpi.getValue();
    }

    private String kpiAssociatedMeasureGroupID(KpiMapping kpi) {
        return kpi.getAssociatedMeasureGroupID();
    }

    private String kpiDisplayFolder(KpiMapping kpi) {
        return kpi.getDisplayFolder();
    }

    private List<? extends TranslationMapping> kpiTranslations(KpiMapping kpi) {
        return translations(kpi.getTranslations());
    }

    private List<? extends TranslationMapping> translations(List<? extends TranslationMapping> translations) {
        if (translations != null) {
            return translations.stream().map(this::translation).toList();
        }
        return null;
    }

    protected TranslationMapping translation(TranslationMapping translation) {
        if (translation != null) {
            long language = translationLanguage(translation);
            String caption = translationCaption(translation);
            String description = translationDescription(translation);
            String displayFolder = translationDisplayFolder(translation);
            List<? extends AnnotationMapping> annotations = translationAnnotations(translation);
            return new_Translation(language, caption, description, displayFolder, annotations);
        }
        return null;
    }

    private List<? extends AnnotationMapping> translationAnnotations(TranslationMapping translation) {
        return annotations(translation.getAnnotations());
    }

    private String translationDisplayFolder(TranslationMapping translation) {
        return translation.getDisplayFolder();
    }

    private String translationDescription(TranslationMapping translation) {
        return translation.getDescription();
    }

    private String translationCaption(TranslationMapping translation) {
        return translation.getCaption();
    }

    private long translationLanguage(TranslationMapping translation) {
        return translation.getLanguage();
    }

    protected abstract TranslationMapping new_Translation(
        long language, String caption, String description,
        String displayFolder, List<? extends AnnotationMapping> annotations
    );

    private DocumentationMapping kpiDocumentation(KpiMapping kpi) {
        return documentation(kpi.getDocumentation());
    }

    private String kpiName(KpiMapping kpi) {
        return kpi.getName();
    }

    private List<? extends AnnotationMapping> kpiAnnotations(KpiMapping kpi) {
        return annotations(kpi.getAnnotations());
    }

    private String kpiDescription(KpiMapping kpi) {
        return kpi.getDescription();
    }

    private String kpiId(KpiMapping kpi) {
        return kpi.getId();
    }

    protected abstract KpiMapping new_Kpi(
        List<? extends AnnotationMapping> annotations, String id, String description,
        String name, DocumentationMapping documentation, List<? extends TranslationMapping> translations,
        String displayFolder, String associatedMeasureGroupID, String value, String goal, String status,
        String trend, String weight, String trendGraphic, String statusGraphic, String currentTimeMember,
        String parentKpiID
    );

    protected List<? extends NamedSetMapping> cubeNamedSets(CubeMapping cube) {
        return namedSets(cube.getNamedSets());
    }

    protected List<? extends CalculatedMemberMapping> cubeCalculatedMembers(CubeMapping cube) {
        return calculatedMembers(cube.getCalculatedMembers());
    }

    protected List<? extends CalculatedMemberMapping> calculatedMembers(
        List<? extends CalculatedMemberMapping> calculatedMembers
    ) {
        if (calculatedMembers != null) {
            return calculatedMembers.stream().map(this::calculatedMember).toList();
        }
        return null;
    }

    protected CalculatedMemberMapping calculatedMember(CalculatedMemberMapping calculatedMember) {
        if (calculatedMember != null) {
            List<? extends AnnotationMapping> annotations = calculatedMemberAnnotations(calculatedMember);
            String id = calculatedMemberId(calculatedMember);
            String description = calculatedMemberDescription(calculatedMember);
            String name = calculatedMemberName(calculatedMember);
            DocumentationMapping documentation = calculatedMemberDocumentation(calculatedMember);

            List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties = calculatedMemberCalculatedMemberProperties(
                calculatedMember);
            CellFormatterMapping cellFormatter = calculatedMemberCellFormatter(calculatedMember);
            String formula = calculatedMemberFormula(calculatedMember);
            String displayFolder = calculatedMemberDisplayFolder(calculatedMember);
            String formatString = calculatedMemberFormatString(calculatedMember);
            HierarchyMapping hierarchy = calculatedMemberHierarchy(calculatedMember);
            DimensionConnectorMapping dimensionConector = calculatedMemberDimensionConector(calculatedMember);
            String parent = calculatedMemberParent(calculatedMember);
            boolean visible = calculatedMemberVisible(calculatedMember);

            return new_CalculatedMember(annotations, id, description, name, documentation, calculatedMemberProperties,
                cellFormatter, formula, displayFolder, formatString, hierarchy, dimensionConector, parent, visible);
        }
        return null;
    }

    protected abstract CalculatedMemberMapping new_CalculatedMember(
        List<? extends AnnotationMapping> annotations,
        String id, String description, String name, DocumentationMapping documentation,
        List<? extends CalculatedMemberPropertyMapping> calculatedMemberProperties,
        CellFormatterMapping cellFormatter, String formula, String displayFolder, String formatString,
        HierarchyMapping hierarchy, DimensionConnectorMapping dimensionConector, String parent, boolean visible
    );

    private boolean calculatedMemberVisible(CalculatedMemberMapping calculatedMember) {
        return calculatedMember.isVisible();
    }

    private String calculatedMemberParent(CalculatedMemberMapping calculatedMember) {
        return calculatedMember.getParent();
    }

    private DimensionConnectorMapping calculatedMemberDimensionConector(CalculatedMemberMapping calculatedMember) {
        return dimensionConnector(calculatedMember.getDimensionConector());
    }

    private HierarchyMapping calculatedMemberHierarchy(CalculatedMemberMapping calculatedMember) {
        return hierarchy(calculatedMember.getHierarchy());
    }

    private String calculatedMemberFormatString(CalculatedMemberMapping calculatedMember) {
        return calculatedMember.getFormatString();
    }

    private String calculatedMemberDisplayFolder(CalculatedMemberMapping calculatedMember) {
        return calculatedMember.getDisplayFolder();
    }

    private String calculatedMemberFormula(CalculatedMemberMapping calculatedMember) {
        return calculatedMember.getFormula();
    }

    private CellFormatterMapping calculatedMemberCellFormatter(CalculatedMemberMapping calculatedMember) {
        return cellFormatter(calculatedMember.getCellFormatter());
    }

    private List<? extends CalculatedMemberPropertyMapping> calculatedMemberCalculatedMemberProperties(
        CalculatedMemberMapping calculatedMember
    ) {
        return calculatedMemberProperties(calculatedMember.getCalculatedMemberProperties());
    }

    private DocumentationMapping calculatedMemberDocumentation(CalculatedMemberMapping calculatedMember) {
        return documentation(calculatedMember.getDocumentation());
    }

    private String calculatedMemberName(CalculatedMemberMapping calculatedMember) {
        return calculatedMember.getName();
    }

    private String calculatedMemberDescription(CalculatedMemberMapping calculatedMember) {
        return calculatedMember.getDescription();
    }

    private String calculatedMemberId(CalculatedMemberMapping calculatedMember) {
        return calculatedMember.getId();
    }

    private List<? extends AnnotationMapping> calculatedMemberAnnotations(CalculatedMemberMapping calculatedMember) {
        return annotations(calculatedMember.getAnnotations());
    }

    protected List<? extends DimensionConnectorMapping> cubeDimensionConnectors(CubeMapping cube) {
        return dimensionConnectors(cube.getDimensionConnectors());
    }

    protected List<? extends DimensionConnectorMapping> dimensionConnectors(
        List<? extends DimensionConnectorMapping> dimensionConnectors
    ) {
        if (dimensionConnectors != null) {
            return dimensionConnectors.stream().map(this::dimensionConnector).toList();
        }
        return null;
    }

    protected DimensionConnectorMapping dimensionConnector(DimensionConnectorMapping dimensionConnector) {
        if (dimensionConnector != null) {
            String foreignKey = dimensionConnectorForeignKey(dimensionConnector);
            LevelMapping level = dimensionConnectorLevel(dimensionConnector);
            String usagePrefix = dimensionConnectorUsagePrefix(dimensionConnector);
            boolean visible = dimensionConnectorVisible(dimensionConnector);
            DimensionMapping dimension = dimensionConnectorDimension(dimensionConnector);
            String overrideDimensionName = dimensionConnectorOverrideDimensionName(dimensionConnector);
            return new_DimensionConnector(foreignKey, level, usagePrefix, visible, dimension, overrideDimensionName);
        }

        return null;
    }

    protected abstract DimensionConnectorMapping new_DimensionConnector(
        String foreignKey, LevelMapping level,
        String usagePrefix, boolean visible, DimensionMapping dimension, String overrideDimensionName
    );

    private String dimensionConnectorOverrideDimensionName(DimensionConnectorMapping dimensionConnector) {
        return dimensionConnector.getOverrideDimensionName();
    }

    private DimensionMapping dimensionConnectorDimension(DimensionConnectorMapping dimensionConnector) {
        return dimension(dimensionConnector.getDimension());
    }

    private boolean dimensionConnectorVisible(DimensionConnectorMapping dimensionConnector) {
        return dimensionConnector.isVisible();
    }

    private String dimensionConnectorUsagePrefix(DimensionConnectorMapping dimensionConnector) {
        return dimensionConnector.getUsagePrefix();
    }

    private LevelMapping dimensionConnectorLevel(DimensionConnectorMapping dimensionConnector) {
        return level(dimensionConnector.getLevel());
    }

    private String dimensionConnectorForeignKey(DimensionConnectorMapping dimensionConnector) {
        return dimensionConnector.getForeignKey();
    }

    protected DocumentationMapping cubeDocumentation(CubeMapping cube) {
        return documentation(cube.getDocumentation());
    }

    protected String cubeName(CubeMapping cube) {
        return cube.getName();
    }

    protected String cubeDescription(CubeMapping cube) {
        return cube.getDescription();
    }

    protected String cubeId(CubeMapping cube) {
        return cube.getId();
    }

    protected List<? extends AnnotationMapping> cubeAnnotations(CubeMapping cube) {
        return annotations(cube.getAnnotations());
    }

    protected List<? extends ParameterMapping> schemaParameters(SchemaMapping schemaMappingOriginal) {
        return parameters(schemaMappingOriginal.getParameters());
    }

    protected List<? extends ParameterMapping> parameters(List<? extends ParameterMapping> parameters) {
        return null;
    }

    protected DocumentationMapping schemaDocumentation(SchemaMapping schemaMappingOriginal) {
        return documentation(schemaMappingOriginal.getDocumentation());
    }

    protected DocumentationMapping documentation(DocumentationMapping documentation) {
        if (documentation != null) {
            String value = documentation.getValue();
            return new_Documentation(value);
        }
        return null;
    }

    protected abstract DocumentationMapping new_Documentation(String value);

    protected List<? extends AnnotationMapping> schemaAnnotations(SchemaMapping schemaMappingOriginal) {
        return annotations(schemaMappingOriginal.getAnnotations());
    }

    protected List<? extends AnnotationMapping> annotations(List<? extends AnnotationMapping> annotations) {
        if (annotations != null) {
            return annotations.stream().map(this::annotation).toList();
        }
        return null;
    }

    protected AnnotationMapping annotation(AnnotationMapping annotation) {
        if (annotation != null) {
            String value = annotation.getValue();
            String name = annotation.getName();
            return new_Annotation(value, name);
        }
        return null;
    }

    protected abstract AnnotationMapping new_Annotation(String value, String name);

    protected String schemaId(SchemaMapping schemaMapping) {
        return schemaMapping.getId();
    }

    protected String schemaDescription(SchemaMapping schemaMapping) {
        return schemaMapping.getDescription();
    }

    protected String schemaName(SchemaMapping schemaMapping) {
        return schemaMapping.getName();
    }

    protected abstract SchemaMapping new_Schema(
        List<? extends AnnotationMapping> annotations, String id,
        String description, String name, DocumentationMapping documentation,
        List<? extends ParameterMapping> parameters, List<? extends CubeMapping> cubes,
        List<? extends NamedSetMapping> namedSets, List<? extends AccessRoleMapping> accessRoles,
        AccessRoleMapping defaultAccessRole, String measuresDimensionName
    );

}
