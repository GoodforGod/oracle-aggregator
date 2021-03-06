package io.university.oracle.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.annotation.simple.string.GenId;
import io.dummymaker.annotation.simple.string.GenNick;
import io.university.oracle.model.IUpdatable;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 16.02.2019
 */
@Entity
public class OWorkHistory implements IUpdatable<OWorkHistory>, Serializable {

    @Id
    @GenId
    private String id;

    @GenTime
    private Timestamp startTimestamp;

    @GenTime
    private Timestamp endTimestamp;

    @GenNick
    private String position;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_uid")
    private ODepartment department;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "person_uid")
    private OPerson person;

    public String getId() {
        return id;
    }

    public Timestamp getStartTimestamp() {
        return startTimestamp;
    }

    public Timestamp getEndTimestamp() {
        return endTimestamp;
    }

    public String getPosition() {
        return position;
    }

    public ODepartment getDepartment() {
        return department;
    }

    public void setDepartment(ODepartment department) {
        this.department = department;
    }

    public OPerson getPerson() {
        return person;
    }

    public void setPerson(OPerson person) {
        this.person = person;
    }

    @Override
    public void update(OWorkHistory oWorkHistory) {
        this.endTimestamp = oWorkHistory.getEndTimestamp();
        this.position = oWorkHistory.getPosition();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OWorkHistory that = (OWorkHistory) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
