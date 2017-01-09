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

import de.saxsys.mvvmfx.testingutils.GCVerifier;
import javafx.beans.property.*;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class ObservableRulesValidatorTest {
	
	private BooleanProperty rule1;
	private BooleanProperty rule2;
    private ObjectProperty<ValidationMessage> rule3;
    private ObjectProperty<ValidationMessage> rule4;

	private ObservableRuleBasedValidator validator;

	@Before
	public void setup() {
		rule1 = new SimpleBooleanProperty();
		rule2 = new SimpleBooleanProperty();

        rule3 = new SimpleObjectProperty<>();
        rule4 = new SimpleObjectProperty<>();

		validator = new ObservableRuleBasedValidator();
	}

    /**
     * This test is used to reproduce a special behavior when garbage collection is done after
     * adding rules.
     * The validator implementation has to keep references to the given rules (for example by storing
     * them in a list). Otherwise they may be removed by the garbage collector. In this case they wouldn't be
     * evaluated again after a value has changed.
     */
	@Test
    public void testGarbageCollection() {
        StringProperty value = new SimpleStringProperty();
        final ValidationStatus status = validator.getValidationStatus();
        Pattern uppercasePattern = Pattern
                .compile("[A-Z]+");

        validator.addRule(ObservableRules.notEmpty(value), ValidationMessage.error("not empty"));
        validator.addRule(ObservableRules.matches(value, uppercasePattern), ValidationMessage.error("not matching"));

        GCVerifier.forceGC();

        assertThat(status.isValid()).isFalse();
        assertThat(status.getErrorMessages()).hasSize(2);

        value.setValue("something");
        assertThat(status.isValid()).isFalse();
        assertThat(status.getErrorMessages()).hasSize(1);

        value.setValue("SOMETHING");
        assertThat(status.isValid()).isTrue();
        assertThat(status.getErrorMessages()).isEmpty();
    }


	@Test
	public void testBooleanRules() {
		final ValidationStatus status = validator.getValidationStatus();
		assertThat(status.isValid()).isTrue();
		
		rule1.setValue(false);
		
		validator.addRule(rule1, ValidationMessage.error("error"));

        GCVerifier.forceGC();
		
		assertThat(status.isValid()).isFalse();
		assertThat(status.getErrorMessages()).hasSize(1);
		assertThat(status.getErrorMessages().get(0).getMessage()).isEqualTo("error");
		
		rule1.setValue(true);
		assertThat(status.isValid()).isTrue();
		assertThat(status.getErrorMessages()).isEmpty();
		
		rule2.setValue(false);
		
		validator.addRule(rule2, ValidationMessage.warning("warning"));

        GCVerifier.forceGC();
		
		assertThat(status.isValid()).isFalse();
		assertThat(status.getErrorMessages()).isEmpty();
		assertThat(status.getWarningMessages()).hasSize(1);
		assertThat(status.getWarningMessages().get(0).getMessage()).isEqualTo("warning");
		
		
		
		rule1.setValue(false);
		assertThat(status.isValid()).isFalse();
		assertThat(status.getErrorMessages()).hasSize(1);
		assertThat(status.getErrorMessages().get(0).getMessage()).isEqualTo("error");
		assertThat(status.getWarningMessages()).hasSize(1);
		assertThat(status.getWarningMessages().get(0).getMessage()).isEqualTo("warning");
		
		rule1.setValue(true);
		rule2.setValue(true);
		assertThat(status.isValid()).isTrue();
		assertThat(status.getErrorMessages()).isEmpty();
		assertThat(status.getWarningMessages()).isEmpty();
	}


	@Test
    public void testComplexRules() {
        final ValidationStatus status = validator.getValidationStatus();

        validator.addRule(rule3);
        validator.addRule(rule4);

        GCVerifier.forceGC();

        assertThat(status.isValid()).isTrue();
        assertThat(status.getErrorMessages()).isEmpty();
        assertThat(status.getWarningMessages()).isEmpty();


        // when
        rule3.setValue(ValidationMessage.error("Error"));

        // then
        assertThat(status.isValid()).isFalse();
        assertThat(status.getErrorMessages()).hasSize(1);
        assertThat(status.getErrorMessages().get(0).getMessage()).isEqualTo("Error");
        assertThat(status.getWarningMessages()).isEmpty();

        // when
        rule4.setValue(ValidationMessage.warning("Warning"));

        // then
        assertThat(status.isValid()).isFalse();
        assertThat(status.getErrorMessages()).hasSize(1);
        assertThat(status.getErrorMessages().get(0).getMessage()).isEqualTo("Error");
        assertThat(status.getWarningMessages()).hasSize(1);
        assertThat(status.getWarningMessages().get(0).getMessage()).isEqualTo("Warning");

        // when
        rule3.setValue(null);

        // then
        assertThat(status.isValid()).isFalse();
        assertThat(status.getErrorMessages()).isEmpty();
        assertThat(status.getWarningMessages()).hasSize(1);
        assertThat(status.getWarningMessages().get(0).getMessage()).isEqualTo("Warning");

        // when
        rule4.setValue(null);

        // then
        assertThat(status.isValid()).isTrue();
        assertThat(status.getErrorMessages()).isEmpty();
        assertThat(status.getWarningMessages()).isEmpty();
    }

    @Test
    public void testBooleanAndComplexRulesCombined() {
        final ValidationStatus status = validator.getValidationStatus();


        rule1.setValue(true);
        rule2.setValue(true);

        validator.addRule(rule1, ValidationMessage.error("error"));
        validator.addRule(rule2, ValidationMessage.warning("warning"));

        validator.addRule(rule3);
        validator.addRule(rule4);

        GCVerifier.forceGC();

        assertThat(status.isValid()).isTrue();
        assertThat(status.getErrorMessages()).isEmpty();
        assertThat(status.getWarningMessages()).isEmpty();

        // when
        rule1.setValue(false);
        rule3.setValue(ValidationMessage.error("other error"));

        assertThat(status.isValid()).isFalse();
        assertThat(status.getErrorMessages()).hasSize(2);
        assertThat(status.getErrorMessages().get(0).getMessage()).isEqualTo("error");
        assertThat(status.getErrorMessages().get(1).getMessage()).isEqualTo("other error");
        assertThat(status.getWarningMessages()).isEmpty();


        // when
        rule2.setValue(false);
        rule4.setValue(ValidationMessage.warning("other warning"));

        assertThat(status.isValid()).isFalse();
        assertThat(status.getErrorMessages()).hasSize(2);
        assertThat(status.getErrorMessages().get(0).getMessage()).isEqualTo("error");
        assertThat(status.getErrorMessages().get(1).getMessage()).isEqualTo("other error");
        assertThat(status.getWarningMessages()).hasSize(2);
        assertThat(status.getWarningMessages().get(0).getMessage()).isEqualTo("warning");
        assertThat(status.getWarningMessages().get(1).getMessage()).isEqualTo("other warning");


        // when
        rule1.setValue(true);
        rule2.setValue(true);
        rule3.setValue(null);
        rule4.setValue(null);

        // then
        assertThat(status.isValid()).isTrue();
        assertThat(status.getErrorMessages()).isEmpty();
        assertThat(status.getWarningMessages()).isEmpty();

    }
	
	
}
