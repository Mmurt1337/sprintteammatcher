package ru.homecredit.sprintteammatcher.entities.sprint;

import net.java.ao.Entity;
import net.java.ao.Preload;
import net.java.ao.schema.NotNull;
import net.java.ao.schema.Table;

@Preload
@Table("SPRINT_TEAM_MAPPING")
public interface SprintTeam extends Entity {


    String getSprintId();
    void setSprintId(String sprintId);


    String getSprintName();
    void setSprintName(String SprintName);

    String getTeam();
    void setTeam(String team);
}
