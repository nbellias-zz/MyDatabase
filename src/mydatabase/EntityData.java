/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mydatabase;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author nikolaos
 */
public class EntityData implements Serializable {

    private Entity entity;
    private LocalDate dateOfCreation;
    private List<Object> record = new ArrayList();
    private int deleted; //0 no, 1 yes

    public EntityData() {
    }

    public EntityData(Entity entity, LocalDate dateOfCreation, List<Object> record, int deleted) {
        this.entity = entity;
        this.dateOfCreation = dateOfCreation;
        this.record = record;
        this.deleted = deleted;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public List<Object> getRecord() {
        return record;
    }

    public void setRecord(List<Object> record) {
        this.record = record;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.entity);
        hash = 11 * hash + Objects.hashCode(this.dateOfCreation);
        hash = 11 * hash + Objects.hashCode(this.record);
        hash = 11 * hash + this.deleted;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EntityData other = (EntityData) obj;
        if (this.deleted != other.deleted) {
            return false;
        }
        if (!Objects.equals(this.entity, other.entity)) {
            return false;
        }
        if (!Objects.equals(this.dateOfCreation, other.dateOfCreation)) {
            return false;
        }
        if (!Objects.equals(this.record, other.record)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntityData{" + "entity=" + entity + ", dateOfCreation=" + dateOfCreation + ", record=" + record + ", deleted=" + deleted + '}';
    }

}
