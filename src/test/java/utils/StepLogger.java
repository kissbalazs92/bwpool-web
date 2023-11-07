package utils;

import java.util.ArrayList;
import java.util.List;

public class StepLogger {
    private static final ThreadLocal<StepLogger> instance = new ThreadLocal<>();
    private final List<String> messages = new ArrayList<>();

    public static StepLogger getInstance() {
        if (instance.get() == null) {
            instance.set(new StepLogger());
        }
        return instance.get();
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public void writeMessages() {
        for (String message : messages) {
            LoggerClass.infoSimple(message);
        }
        messages.clear();
    }
}

