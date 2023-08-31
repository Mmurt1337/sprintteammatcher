package ru.homecredit.sprintteammatcher.webwork;

import com.atlassian.jira.web.action.JiraWebActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import ru.homecredit.sprintteammatcher.service.TeamFieldConfigurationService;
import ru.homecredit.sprintteammatcher.service.TeamGroupConfigurationService;

import java.util.List;

public class TeamFieldConfigurationAction extends JiraWebActionSupport {

    private final TeamFieldConfigurationService teamFieldConfigurationService;
    private final TeamGroupConfigurationService teamGroupConfigurationService;

    private String fieldId;
    private String newGroupName;
    private List<String> groupNames;

    @Autowired
    public TeamFieldConfigurationAction(TeamFieldConfigurationService teamFieldConfigurationService,
                                        TeamGroupConfigurationService teamGroupConfigurationService) {
        this.teamFieldConfigurationService = teamFieldConfigurationService;
        this.teamGroupConfigurationService = teamGroupConfigurationService;
    }

    @Override
    protected String doExecute() throws Exception {
        fieldId = teamFieldConfigurationService.getFieldId();
        groupNames = teamGroupConfigurationService.getGroupNames();
        return INPUT;
    }

    public String doSave() {
        if (teamFieldConfigurationService.isFieldExists(fieldId)) {
            teamFieldConfigurationService.setFieldId(fieldId);
        }

        if (newGroupName != null && !newGroupName.trim().isEmpty() && !teamGroupConfigurationService.isGroupNameExists(newGroupName)) {
            teamGroupConfigurationService.addGroupName(newGroupName);
        }

        return SUCCESS;
    }

    // Геттеры и сеттеры
    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getNewGroupName() {
        return newGroupName;
    }

    public void setNewGroupName(String newGroupName) {
        this.newGroupName = newGroupName;
    }

    public List<String> getGroupNames() {
        return groupNames;
    }
}
