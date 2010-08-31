/*
Copyright 2008-2010 Gephi
Authors : Jeremy Subtil <jeremy.subtil@gephi.org>
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.gephi.preview.api.supervisors;

import org.gephi.preview.api.EdgeColorizer;

/**
 * Self-loop supervisor.
 *
 * @author Jérémy Subtil <jeremy.subtil@gephi.org>
 */
public interface SelfLoopSupervisor extends Supervisor {

    /**
     * Returns true if the self-loops must be displayed in the preview.
     *
     * @return true if the self-loops must be displayed in the preview
     */
    public Boolean getShowFlag();

    /**
     * Defines if the self-loops must be displayed in the preview.
     *
     * @param value  true to display the self-loops in the preview
     */
    public void setShowFlag(Boolean value);

    /**
     * Returns the self-loop colorizer.
     *
     * @return the self-loop colorizer
     */
    public EdgeColorizer getColorizer();

    /**
     * Sets the self-loop colorizer.
     *
     * @param value  the self-loop colorizer to set
     */
    public void setColorizer(EdgeColorizer value);
}
