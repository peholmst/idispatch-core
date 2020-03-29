package net.pkhapps.idispatch.core.client.api.status;

import java.util.Objects;

/**
 * At least until robots and AI take over, all resources will still be staffed by humans. This value object defines
 * the staffing of a unit, divided into chief officers, sub officers and non-officer crew members.
 */
public class Staffing {

    private final int chiefOfficers;
    private final int subOfficers;
    private final int crew;

    public Staffing(int chiefOfficers, int subOfficers, int crew) {
        this.chiefOfficers = chiefOfficers;
        this.subOfficers = subOfficers;
        this.crew = crew;
    }

    /**
     * The number of chief officers.
     */
    public int getChiefOfficers() {
        return chiefOfficers;
    }

    /**
     * The number of sub officers.
     */
    public int getSubOfficers() {
        return subOfficers;
    }

    /**
     * The number of non-officer crew members.
     */
    public int getCrew() {
        return crew;
    }

    /**
     * The total number of crew members, both officers and non-officers.
     */
    public int getTotalStaffing() {
        return chiefOfficers + subOfficers + crew;
    }

    @Override
    public String toString() {
        return String.format("%d+%d+%d", chiefOfficers, subOfficers, crew);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staffing staffing = (Staffing) o;
        return chiefOfficers == staffing.chiefOfficers &&
                subOfficers == staffing.subOfficers &&
                crew == staffing.crew;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chiefOfficers, subOfficers, crew);
    }
}
