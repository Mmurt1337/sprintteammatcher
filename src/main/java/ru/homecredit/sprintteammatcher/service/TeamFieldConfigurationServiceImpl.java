package ru.homecredit.sprintteammatcher.service;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.issue.CustomFieldManager;
import ru.homecredit.sprintteammatcher.entities.field.TeamFieldConfiguration;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Класс TeamFieldConfigurationServiceImpl предоставляет реализацию интерфейса TeamFieldConfigurationService.
 * Он используется для работы с конфигурацией поля команды.
 */
@Named
public class TeamFieldConfigurationServiceImpl implements TeamFieldConfigurationService {

    private final ActiveObjects activeObjects;
    private final CustomFieldManager customFieldManager;

    /**
     * Конструктор класса. Здесь мы инициализируем все необходимые зависимости.
     *
     * @param activeObjects объект ActiveObjects для работы с активными объектами в Atlassian.
     * @param customFieldManager менеджер для работы с пользовательскими полями JIRA.
     */
    @Inject
    public TeamFieldConfigurationServiceImpl(@ComponentImport ActiveObjects activeObjects,
                                             @ComponentImport CustomFieldManager customFieldManager) {
        this.activeObjects = activeObjects;
        this.customFieldManager = customFieldManager;
    }


    /**
     * Возвращает ID текущего поля команды.
     *
     * @return строка, содержащая ID поля, или null, если конфигурация поля отсутствует.
     */
    public String getFieldId() {
        TeamFieldConfiguration[] configs = activeObjects.find(TeamFieldConfiguration.class);
        return (configs.length > 0) ? configs[0].getFieldId() : null;
    }

    /**
     * Проверяет, существует ли поле с заданным ID.
     *
     * @param fieldId ID поля для проверки.
     * @return true, если поле существует, иначе false.
     */
    public boolean isFieldExists(String fieldId) {
        return customFieldManager.getCustomFieldObject(fieldId) != null;
    }

    /**
     * Устанавливает ID для поля команды. Если конфигурация поля уже существует, обновляет ее,
     * иначе создает новую конфигурацию.
     *
     * @param fieldId новый ID для поля.
     */
    public void setFieldId(String fieldId) {
        TeamFieldConfiguration[] configs = activeObjects.find(TeamFieldConfiguration.class);
        TeamFieldConfiguration config = (configs.length > 0) ? configs[0] : activeObjects.create(TeamFieldConfiguration.class);
        config.setFieldId(fieldId);
        config.save();
    }
    /**
     * Возвращает текущее сообщение для конфигурации поля команды.
     *
     * @return строка, содержащая сообщение, или null, если конфигурация поля отсутствует.
     */
    public String getMessage() {
        TeamFieldConfiguration[] configs = activeObjects.find(TeamFieldConfiguration.class);
        return (configs.length > 0) ? configs[0].getMessage() : null;
    }
    /**
     * Устанавливает сообщение для конфигурации поля команды. Если конфигурация поля уже существует, обновляет ее,
     * иначе создает новую конфигурацию.
     *
     * @param message новое сообщение для конфигурации поля.
     */
    public void setMessage(String message) {
        TeamFieldConfiguration[] configs = activeObjects.find(TeamFieldConfiguration.class);
        TeamFieldConfiguration config = (configs.length > 0) ? configs[0] : activeObjects.create(TeamFieldConfiguration.class);
        config.setMessage(message);
        config.save();
    }

}
