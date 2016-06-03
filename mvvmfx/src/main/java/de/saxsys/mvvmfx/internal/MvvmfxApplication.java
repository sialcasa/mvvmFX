/*******************************************************************************
 * Copyright 2015 Alexander Casall, Manuel Mauky
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
package de.saxsys.mvvmfx.internal;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This interface defines a common set of methods that the root application classes of extensions of mvvmfx should
 * implement.
 * 
 * The interface is intended as an internal helper to unify the API of the official mvvmFX extensions (Guice and CDI at
 * the moment). It is <strong>not</strong> intended to be used for other use cases.
 * 
 * A mvvmfx extension has to do the following:
 * <ol>
 * <li>Overwrite {@link Application#init()} method with <string>final</string>. In this method the extension can do some
 * bootstrapping (if needed).</li>
 * <li>Call {@link #initMvvmfx()} as last step in the overwritten init method. The contract is that the
 * {@link #initMvvmfx()} is called when the basic container bootstrapping is done so that the user can do her own
 * initialization in this method.</li>
 * <li>Implement {@link Application#start(Stage)} method. In this method own startup logic can be done (if needed).</li>
 * <li>Call {@link #startMvvmfx(Stage)} as last step in the overwritten start method. Pass the stage instance from the
 * original {@link Application#start(Stage)} method to {@link #startMvvmfx(Stage)}.</li>
 * <li>Implement {@link Application#stop()} method. In this method own shutdown logic can be done (if needed).</li>
 * <li>Call {@link #stopMvvmfx()} as last step in the overwritten stop method.</li>
 * </ol>
 * 
 */
public interface MvvmfxApplication {
	
	/**
	 * This method is called when the javafx application is initialized. See
	 * {@link javafx.application.Application#init()} for more details.
	 * 
	 * @throws Exception
	 */
	default void initMvvmfx() throws Exception {
	}
	
	/**
	 * Override this method with your application startup logic.
	 * <p/>
	 * This method is a wrapper method for javafx's {@link javafx.application.Application#start(javafx.stage.Stage)}.
	 */
	void startMvvmfx(Stage stage) throws Exception;
	
	/**
	 * This method is called when the application should stop. See {@link javafx.application.Application#stop()} for
	 * more details.
	 *
	 * @throws Exception
	 */
	default void stopMvvmfx() throws Exception {
	}
	
}
