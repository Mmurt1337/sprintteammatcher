package ru.homecredit.sprintteammatcher.entities.field;

import net.java.ao.Entity;
import net.java.ao.Preload;
import net.java.ao.schema.Table;

@Preload
@Table("TEAM_FIELD")
public interface TeamFieldConfiguration extends Entity {
    String getFieldId();
    void setFieldId(String fieldId);
}
