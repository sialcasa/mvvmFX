/*******************************************************************************
 * Copyright 2017 Gleb Koval
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
 *
 * This annotation is used to mark a method in a ViewModel to be called after all mvvmFX injections are finished.
 * It's possible to use this annotation on multiple methods in the ViewModel and all these methods will be invoked.
 * However, you may not depend on any invocation order. <br>
 *
 * This approach can be combined with the default naming convention of JavaFX. By this convention a method
 * with the signature <code>public initialize()</code> will be invoked after initialization is finished.<br>
 *
 * While the naming convention approach only works with a "public" method, this {@link Initialize} annotation can
 * also be used on "private"/"protected"/default scope methods.
 *
 * Example: <br>
 * <br>
 *
 * <pre>
 * public class SomeViewModel implements {@link ViewModel} {
 *
 *         // mvvmFx injections
 *        {@literal @}{@link InjectScope}
 *         private SomeScope someScope;
 *         ...
 *
 *        {@literal @}Initialize
 *         private void init() {
 *             someScope.subscribe(...);
 *             ...
 *         }
 * }
 * </pre>
 *
 * @author Gleb Koval, Manuel Mauky
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Initialize {
}
