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

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidationStatusTest {
	
	
	@Test
	public void test() {
		ValidationStatus status = new ValidationStatus();
		
		assertThat(status.isValid()).isTrue();
		assertThat(status.getMessages()).isEmpty();
		assertThat(status.getWarningMessages()).isEmpty();
		assertThat(status.getErrorMessages()).isEmpty();
		assertThat(status.getHighestMessage().isPresent()).isFalse();
		
		final ValidationMessage error1 = ValidationMessage.error("error1");
		status.addMessage(error1);
		
		assertThat(status.isValid()).isFalse();
		assertThat(status.getMessages()).hasSize(1);
		assertThat(status.getWarningMessages()).isEmpty();
		assertThat(status.getErrorMessages()).hasSize(1);
		assertThat(status.getHighestMessage().get().getMessage()).isEqualTo("error1");
		
		
		
		final ValidationMessage warning1 = ValidationMessage.warning("warning1");
		status.addMessage(warning1);
		
		assertThat(status.isValid()).isFalse();
		assertThat(status.getMessages()).hasSize(2);
		assertThat(status.getWarningMessages()).hasSize(1);
		assertThat(status.getErrorMessages()).hasSize(1);
		assertThat(status.getHighestMessage().get().getMessage()).isEqualTo("error1");
		
		final ValidationMessage error2 = ValidationMessage.error("error2");
		status.addMessage(error2);
		
		assertThat(status.isValid()).isFalse();
		assertThat(status.getMessages()).hasSize(3);
		assertThat(status.getWarningMessages()).hasSize(1);
		assertThat(status.getErrorMessages()).hasSize(2);
		assertThat(status.getHighestMessage().get().getMessage()).isEqualTo("error1");
		
		
		status.removeMessage(error1);
		
		assertThat(status.isValid()).isFalse();
		assertThat(status.getMessages()).hasSize(2);
		assertThat(status.getWarningMessages()).hasSize(1);
		assertThat(status.getErrorMessages()).hasSize(1);
		assertThat(status.getHighestMessage().get().getMessage()).isEqualTo("error2");
		
		
		status.removeMessage(error2);
		
		assertThat(status.isValid()).isFalse();
		assertThat(status.getMessages()).hasSize(1);
		assertThat(status.getWarningMessages()).hasSize(1);
		assertThat(status.getErrorMessages()).isEmpty();
		assertThat(status.getHighestMessage().get().getMessage()).isEqualTo("warning1");
		
		
		status.removeMessage(warning1);
		
		
		assertThat(status.isValid()).isTrue();
		assertThat(status.getMessages()).isEmpty();
		assertThat(status.getWarningMessages()).isEmpty();
		assertThat(status.getErrorMessages()).isEmpty();
		assertThat(status.getHighestMessage().isPresent()).isFalse();
		
		
	}
	
}
