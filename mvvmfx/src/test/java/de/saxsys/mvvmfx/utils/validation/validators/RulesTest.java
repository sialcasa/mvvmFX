package de.saxsys.mvvmfx.utils.validation.validators;

import de.saxsys.mvvmfx.utils.validation.validators.ObservableRules;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author manuel.mauky
 */
public class RulesTest {
	
	
	@Test
	public void testNotNullOrEmpty() {
		StringProperty value = new SimpleStringProperty();

		final ObservableBooleanValue rule = ObservableRules.notEmpty(value);
		
		assertThat(rule.get()).isFalse();

		value.set("test");
		assertThat(rule.get()).isTrue();

		value.set("");
		assertThat(rule.get()).isFalse();

		value.set("   dds ");
		assertThat(rule.get()).isTrue();

		value.set("    ");
		assertThat(rule.get()).isFalse();

		value.set("1");
		assertThat(rule.get()).isTrue();
		
		value.set(null);
		assertThat(rule.get()).isFalse();
	}
	
	
	@Test
	public void testMatches() {
		StringProperty value = new SimpleStringProperty();

		final ObservableBooleanValue rule = ObservableRules.matches(value, Pattern.compile("[0-9]"));
		
		assertThat(rule.get()).isFalse();
		
		value.set("1");

		assertThat(rule.get()).isTrue();
		
		value.set("55");
		assertThat(rule.get()).isFalse();

		value.set("5");
		assertThat(rule.get()).isTrue();

		value.set(null);
		assertThat(rule.get()).isFalse();

	}
}
