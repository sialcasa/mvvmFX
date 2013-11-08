/*******************************************************************************
 * Copyright 2013 manuel.mauky
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
package de.saxsys.jfx.mvvm.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * @author manuel.mauky
 *
 */
public class ListenerManager implements ICleanable{
	
	private final Multimap<ObservableValue<?>, ChangeListener> listenerMap = ArrayListMultimap.<ObservableValue<?>, ChangeListener>create();
	
	
	public <T> void register(ObservableValue<T> observable, ChangeListener<? super T> listener){
		observable.addListener(listener);
		listenerMap.put(observable, listener);
	}
	
	
	
	@Override
	public void clean() {
		for(ObservableValue<?> observable : listenerMap.keySet()){
			for(ChangeListener listener : listenerMap.get(observable)){
				observable.removeListener(listener);
			}
		}
	}
	
	

}
