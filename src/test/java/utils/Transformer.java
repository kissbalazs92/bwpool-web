package utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class Transformer implements IAnnotationTransformer {

    // Do not worry about calling this method as testNG calls it behind the scenes before EVERY method (or test).
    // It will disable single tests, not the entire suite like SkipException
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod){

        // If we have chose not to run this test then disable it.
        if (disableMe()){
            annotation.setEnabled(false);
        }
    }

    // logic YOU control
    private boolean disableMe() {
        return true;
    }
}
