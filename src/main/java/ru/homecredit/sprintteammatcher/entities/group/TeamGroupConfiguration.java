package ru.homecredit.sprintteammatcher.entities.group;

import net.java.ao.Entity;
import net.java.ao.Preload;
import net.java.ao.schema.Table;

@Preload
@Table("TEAM_GROUP")
public interface TeamGroupConfiguration extends Entity {
    String getGroupName();
    void setGroupName(String groupName);
}
