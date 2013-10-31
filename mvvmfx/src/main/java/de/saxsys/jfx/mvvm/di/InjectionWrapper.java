/*******************************************************************************
 * Copyright 2013 Alexander Casall
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
package de.saxsys.jfx.mvvm.di;

/**
 * Wrapper to encapsulate functionality provided by the dependeny injection framework.
 * It is used to separate the code of the mvvm framework from the used DI-Framework.
 * 
 * @author manuel.mauky
 *
 */
public interface InjectionWrapper {
	
	/**
	 * Returns an instance of the given class type. 
	 */
	<T> T getInstance(Class<T> type);

}
