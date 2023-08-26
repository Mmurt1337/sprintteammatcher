package ru.homecredit.sprintteammatcher.conditions;



import com.atlassian.jira.plugin.webfragment.conditions.AbstractWebCondition;
import com.atlassian.jira.plugin.webfragment.model.JiraHelper;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.security.GlobalPermissionManager;
import com.atlassian.jira.security.Permissions;
import com.atlassian.jira.security.groups.GroupManager;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import ru.homecredit.sprintteammatcher.service.TeamGroupConfigurationService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class IsCurrentUserAdminOrInSavedGroup extends AbstractWebCondition {
    @ComponentImport
    private final JiraAuthenticationContext authenticationContext;
    private final TeamGroupConfigurationService TeamGroupConfigurationService;
    private final GroupManager groupManager;
    private final GlobalPermissionManager globalPermissionManager;

    public IsCurrentUserAdminOrInSavedGroup(TeamGroupConfigurationService TeamGroupConfigurationService,
                                            JiraAuthenticationContext authenticationContext)
                                            {
        this.TeamGroupConfigurationService = TeamGroupConfigurationService;
        this.groupManager = ComponentAccessor.getGroupManager();
        this.globalPermissionManager = ComponentAccessor.getGlobalPermissionManager();
        this.authenticationContext = authenticationContext;
    }

    @Override
    public boolean shouldDisplay(ApplicationUser applicationUser, JiraHelper jiraHelper) {
        ApplicationUser user = authenticationContext.getLoggedInUser();

        // Проверка на права администратора
        //log.warn("user: {}", user);
        if (globalPermissionManager.hasPermission(Permissions.ADMINISTER, user)) {
            return true;
        }

        // Проверка на наличие пользователя в группах из списка
        List<String> groupNames = TeamGroupConfigurationService.getGroupNames();
        for (String groupName : groupNames) {
            if (groupManager.isUserInGroup(user, groupName)) {
                return true;
            }
        }
        return false;
    }


}