package ru.homecredit.sprintteammatcher.service;

import com.atlassian.activeobjects.external.ActiveObjects;
import ru.homecredit.sprintteammatcher.entities.group.TeamGroupConfiguration;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class TeamGroupConfigurationServiceImpl implements TeamGroupConfigurationService {

    private final ActiveObjects activeObjects;

    @Inject
    public TeamGroupConfigurationServiceImpl(ActiveObjects activeObjects) {
        this.activeObjects = activeObjects;
    }

    @Override
    public List<String> getGroupNames() {
        TeamGroupConfiguration[] configs = activeObjects.find(TeamGroupConfiguration.class);
        return Arrays.stream(configs)
                .map(TeamGroupConfiguration::getGroupName)
                .collect(Collectors.toList());
    }

    @Override
    public void addGroupName(String groupName) {
        if (!isGroupNameExists(groupName)) {
            TeamGroupConfiguration config = activeObjects.create(TeamGroupConfiguration.class);
            config.setGroupName(groupName);
            config.save();
        }
    }

    @Override
    public boolean isGroupNameExists(String groupName) {
        TeamGroupConfiguration[] configs = activeObjects.find(TeamGroupConfiguration.class, "GROUP_NAME = ?", groupName);
        return configs.length > 0;
    }
}
