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
package de.saxsys.mvvmfx.utils.validation;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class ValidationMessageTest {

	/**
	 * Verify that the {@link ValidationMessage} class implements the
	 * {@link Object#equals(Object)} and {@link Object#hashCode()} methods
	 * correctly.
	 */
	@Test
	public void testEqualsAndHashcode() {

		EqualsVerifier.forClass(ValidationMessage.class)
				.suppress(Warning.STRICT_INHERITANCE) // others can create subclasses but it's their task to properly override equals and hashcode
				.suppress(Warning.NULL_FIELDS) // class is immutable and checks arguments for non-null in the constructor
				.verify();
		
	}
	
}
