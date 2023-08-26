package ru.homecredit.sprintteammatcher.webwork;

import com.atlassian.jira.web.action.JiraWebActionSupport;
import ru.homecredit.sprintteammatcher.entities.sprint.SprintTeam;
import ru.homecredit.sprintteammatcher.service.SprintTeamService;


import javax.inject.Inject;

public class EditeTeamAction extends JiraWebActionSupport {

    private final SprintTeamService sprintTeamService;
    private SprintTeam sprint;


    @Inject
    public EditeTeamAction(SprintTeamService sprintTeamService) {
        this.sprintTeamService = sprintTeamService;
    }

    @Override
    public String doExecute() throws Exception {

        return INPUT;
    }
    // Метод для обновления информации о команде
    public void doUpdate() {
        String sprintId = getHttpRequest().getParameter("sprintId");
        String sprintName = getHttpRequest().getParameter("sprintName");
        String team = getHttpRequest().getParameter("team");
        sprintTeamService.assignTeamToSprint(sprintId, sprintName, team);
    }

    public String doSearch() throws Exception {
        String sprintId = getHttpRequest().getParameter("sprintId");
        sprint = sprintTeamService.getSprint(sprintId);

        return INPUT;
    }

}
