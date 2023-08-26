package ru.homecredit.sprintteammatcher.service;


import ru.homecredit.sprintteammatcher.entities.sprint.SprintTeam;


public interface SprintTeamService {

    void assignTeamToSprint(String sprintId,String sprintName, String team);
    SprintTeam getSprint(String sprintId);

}
