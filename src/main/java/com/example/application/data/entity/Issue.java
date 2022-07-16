package com.example.application.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table
public class Issue extends AbstractEntity {
    @NotBlank
    private String description;
    private String comment;

    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    private String equipName;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date inspectionDate;
    @PrePersist
    private void onCreate(){
        inspectionDate = new Date();
    }


    @ManyToOne
    @JoinColumn(name = "equip_id")
    private Equipment equipment;

    public Issue() {
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    @Override
    public String toString() {
        return "Issue{" +
                "description='" + description + '\'' +
                ", comment='" + comment + '\'' +
                ", equipName='" + equipName + '\'' +
                ", inspectionDate=" + inspectionDate +
                '}';
    }




}


