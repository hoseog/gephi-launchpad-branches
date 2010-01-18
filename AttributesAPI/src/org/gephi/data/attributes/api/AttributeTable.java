/*
Copyright 2008 WebAtlas
Authors : Mathieu Bastian, Mathieu Jacomy, Julian Bilcke
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gephi.data.attributes.api;

import org.openide.util.Lookup;
import org.openide.util.LookupListener;

/**
 * Table that contains columns, holded by an {@link AttributModel}.
 *
 * @author Mathieu Bastian
 * @see AttributeColumn
 * @see AttributeRow
 */
public interface AttributeTable {

    /**
     * Returns the name of this table.
     *
     * @return          the name of this table
     */
    public String getName();

    /**
     * Returns the current columns. Call this method to iterate over columns.
     *
     * @return              the current columns.
     */
    public AttributeColumn[] getColumns();

    /**
     * Returns the number of column in this table.
     *
     * @return              the number of columns
     */
    public int countColumns();

    /**
     * Creates and add a new column to this table. The default origin is set at <code>DATA</code>.
     * <p>
     * The title of the column is the identifier.
     * 
     * @param id            the identifier of the column
     * @param type          the type of the column
     * @return              the newly created column
     */
    public AttributeColumn addColumn(String id, AttributeType type);

    /**
     * Creates and add a new column to this table.
     * <p>
     * The title of the column is the identifier
     * .
     * @param id            the identifier of the column
     * @param type          the type of the column
     * @param origin        the origin of the column
     * @return              the newly created column
     */
    public AttributeColumn addColumn(String id, AttributeType type, AttributeOrigin origin);

    /**
     * Creates and add a new column to this table.
     *
     * @param id            the identifier of the column
     * @param title         the title of the column
     * @param type          the type of the column
     * @param origin        the origin of the column.
     * @param defaultValue  the default value of the column.
     * @return              the newly created column
     */
    public AttributeColumn addColumn(String id, String title, AttributeType type, AttributeOrigin origin, Object defaultValue);

    /**
     * If exists, remove the column and all rows values.
     *
     * @param column        the column that is to be removed
     */
    public void removeColumn(AttributeColumn column);

    /**
     * Gets the column at the <code>index</code> of <code>null</code> if the
     * index is not valid.
     *
     * @param index         a valid column index range
     * @return              the column, or <code>null</code> if not found
     */
    public AttributeColumn getColumn(int index);

    /**
     * Gets the column with the given identifier or <code>null</code> if it is
     * not found.
     * 
     * @param id            the column <code>id</code> or <code>title</code>
     * @return              the column, or <code>null</code> if not found
     */
    public AttributeColumn getColumn(String id);

    /**
     * Gets the column which match the given parameters or <code>null</code> 
     * if it is not found.
     *
     * @param title         the column <code>id</code> or <code>title</code>
     * @param type          the column <code>type</code>
     * @return              the column, or <code>null</code> if not found
     */
    public AttributeColumn getColumn(String title, AttributeType type);

    /**
     * Return <code>true</code> if this table has a column with the given
     * <code>title</code> or <code>id</code>.
     * 
     * @param title         the column <code>title</code> that is to be searched
     * @return              <code>true</code> if found, or <code>false</code>
     *                      otherwise
     */
    public boolean hasColumn(String title);

    /**
     * Return this table's lookup, which contains {@link AttributeColumn} objects.
     * <p>
     * Add a {@link LookupListener} to be notified when columns are added or
     * removed from this table.
     *
     * @return              this table's columns <code>Lookup</code>
     */
    public Lookup getLookup();

    /**
     * Merge this table with the given <code>table</code> given. New columns from
     * <code>table</code> are added to this table. 
     * <p>
     * Columns are compared according to their <code>id</code> and <code>type</code>.
     * Columns found in <code>model</code> are appended only if they no column
     * exist with the same <code>id</code> and <code>type</code>.
     * 
     * @param table         the table that is to be merged with this table
     */
    public void mergeTable(AttributeTable table);
}
