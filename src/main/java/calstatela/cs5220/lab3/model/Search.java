package calstatela.cs5220.lab3.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
public class Search implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private int id;

    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    @JsonProperty("departmentId")
    @ManyToOne 
    private Department department;

    private String position;
    @ManyToOne
    private User committeeChair;

    // A user may serve on multiple committees
    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> committeeMembers = new ArrayList<User>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public User getCommitteeChair() {
        return committeeChair;
    }

    public void setCommitteeChair(User committeeChair) {
        this.committeeChair = committeeChair;
    }

    public List<User> getCommitteeMembers() {
        return committeeMembers;
    }

    public void setCommitteeMembers(List<User> committeeMembers) {
        this.committeeMembers = committeeMembers;
    }

}
