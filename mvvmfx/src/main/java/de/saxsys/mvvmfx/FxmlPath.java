/*******************************************************************************
 * Copyright 2017 Rafael Guillen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
 * Please be aware that this annotation only effects the parent
 * view that is loaded by the {@link FluentViewLoader}.
 * Views that are included via "<fx:include ... />" tag aren't
 * affected because the path is then determined by the value of
 * the "src" attribute in the include-tag.
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
