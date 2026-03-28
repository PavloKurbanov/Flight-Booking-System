package framework.menuEngine.menuValidation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MenuItem {
    int action();

    String description();

    MenuGroup menuGroup() default MenuGroup.MAIN;
}
