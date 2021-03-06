package io.university.oracle.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.annotation.simple.number.GenUShort;
import io.dummymaker.annotation.simple.string.GenHexNumber;
import io.dummymaker.annotation.simple.string.GenId;
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
public class OStudy implements IUpdatable<OStudy>, Serializable {

    @Id
    @GenId
    private String id;

    @GenHexNumber
    private String course;

    @GenUShort
    private String groupNum;

    @GenTime
    private Timestamp startTimestamp;

    @GenTime
    private Timestamp graduateTimestamp;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "person_uid")
    private OPerson person;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_uid")
    private ODepartment department;

    @ManyToOne
    @JoinColumn(name = "speciality_uid")
    private OSpeciality speciality;

    public void setSpeciality(OSpeciality speciality) {
        this.speciality = speciality;
    }

    public OSpeciality getSpeciality() {
        return speciality;
    }

    public String getId() {
        return id;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setDepartment(ODepartment department) {
        this.department = department;
    }

    public ODepartment getDepartment() {
        return department;
    }

    public String getCourse() {
        return course;
    }

    public Timestamp getStartTimestamp() {
        return startTimestamp;
    }

    public Timestamp getGraduateTimestamp() {
        return graduateTimestamp;
    }

    public OPerson getPerson() {
        return person;
    }

    public void setPerson(OPerson person) {
        this.person = person;
    }

    @Override
    public void update(OStudy oStudy) {
        this.course = oStudy.getCourse();
        this.groupNum = oStudy.getGroupNum();
        this.graduateTimestamp = oStudy.getGraduateTimestamp();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OStudy oStudy = (OStudy) o;

        return id != null ? id.equals(oStudy.id) : oStudy.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
