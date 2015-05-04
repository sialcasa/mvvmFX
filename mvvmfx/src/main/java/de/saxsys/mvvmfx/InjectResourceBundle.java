package de.saxsys.mvvmfx;

import javafx.fxml.Initializable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ResourceBundle;


/**
 * This annotation can be used to let mvvmFX inject the specified {@link ResourceBundle} into your {@link ViewModel}
 * and/or {@link FxmlView}/{@link JavaView}.
 * 
 * The annotation has to be set on a field of type {@link ResourceBundle}.
 * 
 * For fxml based views there are three options to get the ResourceBundle:
 * <ul>
 * <li>The mvvmFX mechanism using this annotation.</li>
 * <li>The standard mechanism provided by the JavaFX runtime using naming conventions.</li>
 * <li>Using the Interface {@link Initializable} provided by the JavaFX runtime.</li>
 * </ul>
 * 
 * The mvvmFX mechanism is independent from the standard JavaFX mechanism and can be used at the same time (even if this
 * isn't really useful). The difference is that the mvvmFX mechanism can be more explicit and more checks are done:
 * <ul>
 * <li>If there is a field annotated with this annotation but the type of the field isn't fitting ResourceBundle an
 * {@link IllegalStateException} will be thrown.</li>
 * <li>If there is a View/ViewModel that has a field with this annotation but <b>no</b> ResourceBundle is provided when
 * the view is loaded, an {@link IllegalStateException} will be thrown.</li>
 * <li>If the View/ViewModel has a field with this annotation and the attribute {@link #optional()} is set to
 * <code>true</code> (default value is <code>false</code>) and <b>no</b> ResourceBundle is provided when the view is
 * loaded, <b>no</b> exception will be thrown and no ResourceBundle will be injected. You have to check for
 * <code>null</code> in this case.</li>
 * </ul>
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectResourceBundle {
	
	/**
	 * Default value: <code>false</code>.
	 * 
	 * You can use this attribute to express that the resourceBundle injection is optional. If this attribute is set to
	 * <code>true</code> and there is a ResourceBundle available it will be injected as normal. If this attribute is set
	 * to <code>true</code> but no ResourceBundle is available, nothing will be injection and <b>no</b>
	 * {@link IllegalStateException} will be thrown to warn you.
	 */
	boolean optional() default false;
}
