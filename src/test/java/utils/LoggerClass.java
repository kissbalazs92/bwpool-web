package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerClass {

    private static final Logger simpleLogger = LogManager.getLogger("simpleLogger");
    private static final Logger detailedLogger = LogManager.getLogger("detailedLogger");
    private static final Logger cucumberStepsLogger = LogManager.getLogger("cucumber");
    private static final Logger rootLogger = LogManager.getRootLogger();

    public static void logResultPass(String message) {
        cucumberStepsLogger.info(message);
    }

    public static void logResultFail(String message) {
        cucumberStepsLogger.error(message);
    }

    public static void logResultSkip(String message) {
        cucumberStepsLogger.warn(message);
    }

    public static void infoSimple(String message) {
        simpleLogger.info(message);
    }

    public static void infoDetailed(String message) {
        detailedLogger.info(message);}

    public static void errorDetailed(String message, Throwable throwable) {
        detailedLogger.error(message, throwable);
    }
    public static void infoRoot(String message) {
        rootLogger.info(message);
    }

    public static void errorRoot(String message) {
        rootLogger.error(message);
    }

    public static Logger getDetailedLoggerObject() {
        return detailedLogger;
    }
}


