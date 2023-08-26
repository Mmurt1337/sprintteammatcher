package ru.homecredit.sprintteammatcher.service;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import ru.homecredit.sprintteammatcher.entities.sprint.SprintTeam;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * Класс SprintTeamServiceImpl предоставляет реализацию интерфейса SprintTeamService.
 * Он используется для работы с командами спринта.
 */
@Named
public class SprintTeamServiceImpl implements SprintTeamService {


    private final ActiveObjects activeObjects;

    /**
     * Конструктор класса. Здесь мы инициализируем все необходимые зависимости.
     *
     * @param activeObjects объект ActiveObjects для работы с активными объектами в Atlassian.
     */

    @Inject
    public SprintTeamServiceImpl(@ComponentImport ActiveObjects activeObjects) {
        this.activeObjects = activeObjects;
    }

    /**
     * Назначает команду спринту. Если спринт с указанным ID уже существует, обновляет его,
     * иначе создает новый спринт.
     *
     * @param sprintId ID спринта.
     * @param sprintName имя спринта.
     * @param team имя команды.
     * @throws IllegalArgumentException если любой из параметров равен null.
     */
    public void assignTeamToSprint(String sprintId, String sprintName, String team) {
        if (sprintId == null || sprintName == null || team == null) {
            throw new IllegalArgumentException("sprintId, sprintName and team cannot be null");
        }

        SprintTeam[] existingSprints = activeObjects.find(SprintTeam.class, "SPRINT_ID = ?", sprintId);

        SprintTeam sprintTeam;
        if(existingSprints.length > 0) {
            // if sprint with given id already exists, update the first matching record
            sprintTeam = existingSprints[0];
        } else {
            // if no matching record found, create a new one
            sprintTeam = activeObjects.create(SprintTeam.class);
        }

        // set all fields every time
        sprintTeam.setSprintId(sprintId);
        sprintTeam.setSprintName(sprintName);
        sprintTeam.setTeam(team);
        sprintTeam.save();
    }

    /**
     * Возвращает спринт с указанным ID.
     *
     * @param sprintId ID спринта.
     * @return объект SprintTeam, представляющий спринт, если он существует, иначе null.
     * @throws IllegalArgumentException если sprintId равен null.
     */

    public SprintTeam getSprint(String sprintId) {
        if (sprintId == null) {
            throw new IllegalArgumentException("sprintId cannot be null");
        }

        SprintTeam[] existingSprints = activeObjects.find(SprintTeam.class, "SPRINT_ID = ?", sprintId);
        if (existingSprints.length > 0) {
            // if sprint with given id exists, return the first matching record
            return existingSprints[0];
        }

        // if no matching record found, return null
        return null;
    }


}

