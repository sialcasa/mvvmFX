/*******************************************************************************
 * Copyright 2013 Alexander Casall, Manuel Mauky
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
 * This annotation is used to mark the ViewModel field in the View so that the mvvmFX framework will inject the
 * corresponding ViewModel instance. <br>
 * Example: <br>
 * <br>
 * 
 * <pre>
 * public class MyCoolView implements FxmlView{@code <MyCoolViewModel>} {
 * 
 *        {@literal @}InjectViewModel
 *         private MyCoolViewModel viewModel;
 *         ...
 * }
 * </pre>
 * 
 * 
 * @author manuel.mauky
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectViewModel {
}
