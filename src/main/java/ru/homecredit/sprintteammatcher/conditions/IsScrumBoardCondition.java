package ru.homecredit.sprintteammatcher.conditions;

import com.atlassian.greenhopper.model.rapid.RapidView;
import com.atlassian.plugin.PluginParseException;
import com.atlassian.plugin.web.Condition;
import java.util.Map;

public class IsScrumBoardCondition implements Condition {
    public IsScrumBoardCondition() {
    }

    @Override
    public void init(Map<String, String> params) throws PluginParseException {
    }

    @Override
    public boolean shouldDisplay(Map<String, Object> context) {
        //получаем объект доски из контекста
        RapidView board = (RapidView)context.get("board");
        //доступны ли для доски спринты если доска канбан вернет фолс
        return board.isSprintSupportEnabled();
    }
}