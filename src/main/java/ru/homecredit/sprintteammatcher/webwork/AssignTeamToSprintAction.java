package ru.homecredit.sprintteammatcher.webwork;

import ru.homecredit.sprintteammatcher.dto.SprintDTO;
import ru.homecredit.sprintteammatcher.service.TeamFieldConfigurationService;
import ru.homecredit.sprintteammatcher.service.SprintTeamService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.customfields.manager.OptionsManager;
import com.atlassian.jira.issue.customfields.option.Option;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.atlassian.jira.issue.fields.config.FieldConfigScheme;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.greenhopper.model.rapid.RapidView;
import com.atlassian.greenhopper.service.Page;
import com.atlassian.greenhopper.service.ServiceOutcome;
import com.atlassian.greenhopper.service.rapid.view.RapidViewService;
import com.atlassian.greenhopper.service.sprint.SprintService;
import com.atlassian.greenhopper.service.PageRequests;
import com.atlassian.greenhopper.service.sprint.SprintQuery;
import com.atlassian.greenhopper.service.sprint.Sprint;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import lombok.extern.slf4j.Slf4j;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Класс AssignTeamToSprintAction предназначен для присвоения команды определенному спринту.
 * Он наследуется от класса JiraWebActionSupport для поддержки веб-действий JIRA.
 */
@Slf4j
public class AssignTeamToSprintAction extends JiraWebActionSupport {
    private final SprintTeamService sprintTeamService;
    private final TeamFieldConfigurationService fieldConfigurationService;
    private final CustomFieldManager customFieldManager;
    private final OptionsManager optionsManager;
    private final RapidViewService rapidViewService;
    private final SprintService sprintService;

    private List<Option> options;
    private List<Sprint> sprintsList;
    private List<SprintDTO> sprintDTOs;

    private String selectedSprintId;
    private String selectedSprintName;
    private String selectedTeamName;
    private HttpServletRequest request;


    /**
     * Конструктор класса. Здесь мы инициализируем все необходимые зависимости.
     *
     * @param sprintTeamService служба, отвечающая за работу с командами спринтов.
     * @param fieldConfigurationService служба для работы с конфигурацией полей.
     * @param customFieldManager менеджер для работы с пользовательскими полями JIRA.
     * @param rapidViewService сервис для работы с быстрыми представлениями (Rapid Views) в JIRA Agile.
     * @param sprintService сервис для работы со спринтами.
     * @param optionsManager менеджер для работы с опциями полей.
     */
    @Inject
    public AssignTeamToSprintAction(SprintTeamService sprintTeamService,
                                    TeamFieldConfigurationService fieldConfigurationService,
                                    @ComponentImport CustomFieldManager customFieldManager,
                                    @ComponentImport RapidViewService rapidViewService,
                                    @ComponentImport SprintService sprintService,
                                    @ComponentImport OptionsManager optionsManager) {
        this.sprintTeamService = sprintTeamService;
        this.fieldConfigurationService = fieldConfigurationService;
        this.customFieldManager = customFieldManager;
        this.rapidViewService = rapidViewService;
        this.sprintService = sprintService;
        this.optionsManager = optionsManager;
    }
    /**
     * Этот метод вызывается при выполнении действия. Он извлекает необходимые данные и делает
     * необходимую предварительную обработку.
     *
     * @return возвращает строку, которая указывает на результат действия.
     * @throws Exception если возникла ошибка в процессе выполнения.
     */


    @Override
    public String doExecute() throws Exception {
        String fieldId = fieldConfigurationService.getFieldId();
        CustomField customField = customFieldManager.getCustomFieldObject(fieldId);
        if (customField != null) {
            List<FieldConfigScheme> configSchemes = customField.getConfigurationSchemes();
            if (!configSchemes.isEmpty()) {
                Map<String, FieldConfig> configs = configSchemes.get(0).getConfigs();
                if (!configs.isEmpty()) {
                    List<Option> AllOptions = optionsManager.getOptions(
                            configSchemes
                                    .listIterator()
                                    .next()
                                    .getOneAndOnlyConfig()
                    );

                    options = AllOptions.stream()
                            .filter(option -> !option.getDisabled())
                            .collect(Collectors.toList());

                }
            }
        }

        ApplicationUser user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
        long boardId = Long.parseLong(getHttpRequest().getParameter("boardId"));
        String  boardType = getHttpRequest().getParameter("boardType");
        log.warn("boardType: {}", boardType);
        if ("SCRUM".equals(boardType)) {
            ServiceOutcome<RapidView> rapidViewOutcome = rapidViewService.getRapidView(user, boardId);
            RapidView board = rapidViewOutcome.isValid() ? rapidViewOutcome.get() : null;

                if (board != null) {
                    Set<Sprint.State> states = EnumSet.of(Sprint.State.ACTIVE, Sprint.State.FUTURE);
                    SprintQuery sprintQuery = SprintQuery.builder().states(states).build();
                    ServiceOutcome<Page<Sprint>> sprintsOutcome = sprintService.getSprints(user, board, PageRequests.all(), sprintQuery);

                    sprintsList = new ArrayList<>(sprintsOutcome.get().getValues());
                    sprintDTOs = new ArrayList<>();
                    for (Sprint sprint : sprintsList) {
                        SprintDTO sprintDTO = new SprintDTO(sprint.getId().toString(), sprint.getName());
                        sprintDTOs.add(sprintDTO);
                    }

                }
            return INPUT;
        }else {
            return ERROR;
        }


    }

    /**
     * Этот метод вызывается при сохранении действия. Он записывает выбранные данные
     * и присваивает команду выбранному спринту.
     *
     * @return возвращает строку, которая указывает на результат действия.
     * @throws Exception если возникла ошибка в процессе выполнения.
     */

    public String  doSave() throws Exception{
        log.warn("Selected sprint ID: {}", selectedSprintId);
        log.warn("Selected sprint name: {}", selectedSprintName);
        log.warn("Selected team name: {}", selectedTeamName);
        HttpServletRequest request = getHttpRequest();
        String referer = request.getHeader("Referer");
        sprintTeamService.assignTeamToSprint(selectedSprintId, selectedSprintName, selectedTeamName);
        return SUCCESS;
    }




    public List<Option> getOptions() {
        return options;
    }

    public List<SprintDTO> getSprintDTOs() {
        return sprintDTOs;
    }

    public String getSelectedSprintId() {
        return selectedSprintId;
    }

    public void setSelectedSprintId(String selectedSprintId) {
        this.selectedSprintId = selectedSprintId;
    }

    public String getSelectedSprintName() {
        return selectedSprintName;
    }

    public void setSelectedSprintName(String selectedSprintName) {
        this.selectedSprintName = selectedSprintName;
    }

    public String getSelectedTeamName() {
        return selectedTeamName;
    }

    public void setSelectedTeamName(String selectedTeamName) {
        this.selectedTeamName = selectedTeamName;
    }

}
