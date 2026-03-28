package framework.menuEngine;

import framework.menuEngine.menuValidation.MenuGroup;
import framework.menuEngine.menuValidation.MenuItem;
import ui.command.Command;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MenuEngine {
    public static Map<Integer, Command> commands(List<Command> commands, MenuGroup menuGroup) {
        Map<Integer, Command> map = new TreeMap<>();

        for (Command command : commands) {
            MenuItem annotation = command.getClass().getAnnotation(MenuItem.class);

            if (annotation != null && annotation.menuGroup().equals(menuGroup)) {
                map.put(annotation.action(), command);
            }
        }
        return map;
    }
}
