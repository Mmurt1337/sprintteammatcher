package ru.homecredit.sprintteammatcher.service;


public interface TeamFieldConfigurationService {
    String getFieldId();
    void setFieldId(String fieldId);
    boolean isFieldExists(String fieldId);

}
