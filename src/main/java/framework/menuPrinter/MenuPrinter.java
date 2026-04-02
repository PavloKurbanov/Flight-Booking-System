package framework.menuPrinter;

import framework.menuEngine.menuValidation.MenuItem;
import ui.command.Command;

import java.awt.*;
import java.util.Map;

public class MenuPrinter {
    public static void printMenu(Map<Integer, Command> menu) {

        for (Command command : menu.values()) {

            MenuItem menuItem = command.getClass().getAnnotation(MenuItem.class);

            if (menuItem != null) {
                System.out.println(menuItem.action() + ") " + menuItem.description());
            }
        }
        System.out.println("0) Вийти / Повернутись");
    }
}
