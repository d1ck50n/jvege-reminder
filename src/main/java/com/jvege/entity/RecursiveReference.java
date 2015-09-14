package com.jvege.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Dickson
 */
@Entity
//@Table(name = "RECURSIVE_REFERENCE", catalog = "DATABASE", schema = "PUBLIC")
@Table(name = "RECURSIVE_REFERENCE")
@NamedQueries({@NamedQuery(name = "RecursiveReference.findById", query = "SELECT r FROM RecursiveReference r WHERE r.id = :id")})
public class RecursiveReference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "MONDAY")
    private int monday;
    @Column(name = "TUESDAY")
    private int tuesday;
    @Column(name = "WEDNESDAY")
    private int wednesday;
    @Column(name = "THURSDAY")
    private int thursday;
    @Column(name = "FRIDAY")
    private int friday;
    @Column(name = "SATURDAY")
    private int saturday;
    @Column(name = "SUNDAY")
    private int sunday;
    @Column(name = "MONTHLY")
    private int monthly;

    public RecursiveReference() {
    }

    public RecursiveReference(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMonday() {
        return monday;
    }

    public void setMonday(int monday) {
        this.monday = monday;
    }

    public int getTuesday() {
        return tuesday;
    }

    public void setTuesday(int tuesday) {
        this.tuesday = tuesday;
    }

    public int getWednesday() {
        return wednesday;
    }

    public void setWednesday(int wednesday) {
        this.wednesday = wednesday;
    }

    public int getThursday() {
        return thursday;
    }

    public void setThursday(int thursday) {
        this.thursday = thursday;
    }

    public int getFriday() {
        return friday;
    }

    public void setFriday(int friday) {
        this.friday = friday;
    }

    public int getSaturday() {
        return saturday;
    }

    public void setSaturday(int saturday) {
        this.saturday = saturday;
    }

    public int getSunday() {
        return sunday;
    }

    public void setSunday(int sunday) {
        this.sunday = sunday;
    }

    public int getMonthly() {
        return monthly;
    }

    public void setMonthly(int monthly) {
        this.monthly = monthly;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecursiveReference)) {
            return false;
        }
        RecursiveReference other = (RecursiveReference) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RecursiveReference[id=" + id + "], " +
                "[monday=" + monday + "], " +
                "[tuesday=" + tuesday + "], " +
                "[wednesday=" + wednesday + "], " +
                "[thursday=" + thursday + "], " +
                "[friday=" + friday + "], " +
                "[saturday=" + saturday + "], " +
                "[sunday=" + sunday + "], " +
                "[monthly=" + monthly + "]";
    }

}
