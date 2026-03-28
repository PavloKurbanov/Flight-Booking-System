package framework.validatorEngine;

import framework.validatorEngine.validatorAnnotation.NotBlank; // Зверни увагу, я виправив тут одруківку в назві пакета ;)
import framework.validatorEngine.validatorAnnotation.NotNull;

import java.lang.reflect.Field;

public class ValidationEngine {

    // Приймаємо Object, щоб наш фреймворк міг перевіряти будь-які класи (Polymorphism).
    // Метод static, бо це утиліта, яка не має власного стану (Stateless).
    public static void validator(Object obj) {

        // 1. Отримуємо "креслення" об'єкта під час виконання (Runtime метадані).
        Class<?> aClass = obj.getClass();

        // 2. Витягуємо масив УСІХ полів класу (включаючи private, protected, package-private).
        // getDeclaredFields() бачить усе, але не бачить полів батьківського класу (якщо є наслідування).
        Field[] fields = aClass.getDeclaredFields();

        // 3. Проходимося по кожному полю (Складність O(N), де N - кількість полів у класі).
        for (Field field : fields) {
            try {
                // --- БЛОК ВАЛІДАЦІЇ @NotNull ---

                // Запитуємо в поля: "Чи висить на тобі маркер @NotNull?"
                NotNull notNull = field.getAnnotation(NotNull.class);

                // Якщо маркер є (не null), значить це поле вимагає перевірки.
                if (notNull != null) {

                    // Злом інкапсуляції. Оскільки поля у нас private, ми наказуємо JVM
                    // ігнорувати модифікатори доступу, інакше наступний рядок впаде з помилкою.
                    field.setAccessible(true);

                    // Дістаємо ФАКТИЧНЕ значення цього поля з конкретного об'єкта obj.
                    Object object = field.get(obj);

                    // Бізнес-перевірка: якщо значення відсутнє...
                    if (object == null) {
                        // ...викидаємо виняток, динамічно читаючи повідомлення прямо з анотації!
                        throw new IllegalArgumentException(notNull.message());
                    }
                }

                // --- БЛОК ВАЛІДАЦІЇ @NotBlank ---

                // Перевіряємо наявність другого маркера.
                NotBlank notBlank = field.getAnnotation(NotBlank.class);

                if (notBlank != null) {

                    // Знову даємо дозвіл на читання (на випадок, якщо @NotNull не було і ми цього ще не зробили).
                    field.setAccessible(true);
                    Object object = field.get(obj);

                    // Pattern Matching (Type Safety): перевіряємо, чи об'єкт взагалі є текстом.
                    // Якщо так, Java автоматично створює змінну str типу String.
                    if (object instanceof String str) {

                        // Перевіряємо, чи текст складається лише з пробілів або є порожнім ("").
                        if (str.isBlank()) {
                            // Якщо так — обриваємо виконання і віддаємо повідомлення з анотації.
                            throw new IllegalArgumentException(notBlank.message());
                        }
                    }
                }

            } catch (IllegalAccessException e) {
                // Цей Exception вилетить, якщо SecurityManager Java (наприклад, у модульних системах Java 9+)
                // жорстко заборонить нам робити setAccessible(true) для цього класу.
                // Перехоплюємо і кидаємо свій RuntimeException, щоб не "забруднювати" сигнатуру методу.
                throw new RuntimeException("Помилка доступу до поля: неможливо прочитати значення", e);
            }
        }
    }
}