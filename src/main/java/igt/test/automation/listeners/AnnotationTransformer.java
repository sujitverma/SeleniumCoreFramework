
package igt.test.automation.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

/*********************************************************************** 
 * This class implements IAnnotationTranformer
 * This interface is used to programmatically add annotation to your test
 * methods during run time.
 * 
 * @author 
 * *********************************************************************/
public class AnnotationTransformer implements IAnnotationTransformer{

    
    /**
     * transform method is called for every test during test run. 
     */
    @Override
    public void transform(final ITestAnnotation annotation, final Class testClass, final Constructor testConstructor,
            final Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }

}

