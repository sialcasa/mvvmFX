package de.saxsys.mvvmfx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to define a {@link FxmlView} custom
 * FXML file path. An empty file paths will be ignored.
 *
 * Note that the full path to the FXML file must be provided.
 *
 * Example: <br>
 *     <br>
 * <pre>
 * package example.view;
 *
 *{@literal @}FxmlPath("/fxml/CustomPathView.fxml")
 * public class CustomView implements {@link FxmlView} {
 *
 *         ...
 *
 * }
 * </pre>
 *
 * @author rafael.guillen
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FxmlPath {

    /**
     * Custom fxml file path, empty by default
     * @return path to the fxml file
     */
    String value() default "";
}
