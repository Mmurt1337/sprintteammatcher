package ru.homecredit.sprintteammatcher.service;

import java.util.List;

public interface TeamGroupConfigurationService {
    List<String> getGroupNames();
    void addGroupName(String groupName);
    boolean isGroupNameExists(String groupName);
}
