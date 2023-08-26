package ru.homecredit.sprintteammatcher.webwork;

import com.atlassian.jira.web.action.JiraWebActionSupport;
import org.springframework.beans.factory.annotation.Autowired;
import ru.homecredit.sprintteammatcher.service.TeamFieldConfigurationService;
import ru.homecredit.sprintteammatcher.service.TeamGroupConfigurationService;

import java.util.List;

/**
 * Класс TeamFieldConfigurationAction предназначен для обработки действий настройки поля команды.
 * Он наследуется от класса JiraWebActionSupport для поддержки веб-действий JIRA.
 */
public class TeamFieldConfigurationAction extends JiraWebActionSupport {

    private final TeamFieldConfigurationService teamFieldConfigurationService;
    private final TeamGroupConfigurationService teamGroupConfigurationService;

    private String fieldId;
    private String message;
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
        message = teamFieldConfigurationService.getMessage();
        groupNames = teamGroupConfigurationService.getGroupNames();

        return INPUT;
    }

    public void doSave() {
        if (!teamFieldConfigurationService.isFieldExists(fieldId)) {
            message = "Field does not exist!";
            return;
        }

        teamFieldConfigurationService.setFieldId(fieldId);

        if (groupNames != null) {
            for (String groupName : groupNames) {
                teamGroupConfigurationService.addGroupName(groupName);
            }
        }

        message = "Field ID saved: " + fieldId + ". Group Names saved: " + String.join(", ", groupNames);
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(List<String> groupNames) {
        this.groupNames = groupNames;
    }
}
