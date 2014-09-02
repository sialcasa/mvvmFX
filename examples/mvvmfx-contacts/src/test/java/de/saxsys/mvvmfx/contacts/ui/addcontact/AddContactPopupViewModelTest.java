package de.saxsys.mvvmfx.contacts.ui.addcontact;

import static eu.lestard.assertj.javafx.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.mvvmfx.contacts.util.CentralClock;


@RunWith(JfxRunner.class)
public class AddContactPopupViewModelTest {
	
	
	private AddContactPopupViewModel viewModel;
	
	@Before
	public void setup(){
		viewModel = new AddContactPopupViewModel();
		
		ZonedDateTime now = ZonedDateTime
				.of(LocalDate.of(2014, Month.JANUARY, 1), LocalTime.of(0, 0), ZoneId.systemDefault());

		CentralClock.setFixedClock(now);
	}
	
	
	@Test
	public void testValidationOfFirstname(){
		TextField firstnameInput = new TextField();

		assertThat(viewModel.validProperty()).isTrue();

		viewModel.initValidationForFirstname(firstnameInput);
		sleep();

		assertThat(viewModel.validProperty()).isFalse();

		firstnameInput.setText("Luke");
		
		sleep();
		assertThat(viewModel.validProperty()).isTrue();
	}
	
	
	@Test
	public void testValidationOfLastname() {
		TextField lastnameInput = new TextField();
		
		assertThat(viewModel.validProperty()).isTrue();

		viewModel.initValidationForLastname(lastnameInput);
		sleep();
		
		assertThat(viewModel.validProperty()).isFalse();

		lastnameInput.setText("Skywalker");
		
		sleep();
		assertThat(viewModel.validProperty()).isTrue();
	}
	
	@Test
	public void testValidationOfBirthday(){
		DatePicker datePicker = new DatePicker();

		assertThat(viewModel.validProperty()).isTrue();
		
		viewModel.initValidationForBirthday(datePicker);
		
		sleep();
		
		// The birthday isn't mandatory and can be empty
		// so when no date is picked the form is still valid.
		assertThat(viewModel.validProperty()).isTrue();

		LocalDate now = LocalDate.now(CentralClock.getClock());
		LocalDate dateInTheFuture = now.plus(10, ChronoUnit.YEARS);
		
		datePicker.setValue(dateInTheFuture);
		sleep();
		
		// a birthday in the future is invalid
		assertThat(viewModel.validProperty()).isFalse();



		LocalDate dateInThePast = now.minus(10, ChronoUnit.YEARS);
		datePicker.setValue(dateInThePast);

		sleep();
		assertThat(viewModel.validProperty()).isTrue();
	}
	
	
	@Test
	public void testValidationOfEmail(){
		TextField emailInput = new TextField();

		assertThat(viewModel.validProperty()).isTrue();

		viewModel.initValidationForEmail(emailInput);
		sleep();

		assertThat(viewModel.validProperty()).isFalse();

		emailInput.setText("darthvader@imperium.org");
		sleep();
		
		assertThat(viewModel.validProperty()).isTrue();

		emailInput.setText("darthvader.imperium.org"); // wrong email format
		sleep();
		
		assertThat(viewModel.validProperty()).isFalse();

		
		emailInput.setText("luke@example.org");
		sleep();
		
		assertThat(viewModel.validProperty()).isTrue();

	}
	
	
	@Test
	public void testValidationOfPhoneNumber(){
		TextField phoneNumberInput = new TextField();

		assertThat(viewModel.validProperty()).isTrue();

		viewModel.initValidationForPhoneNumber(phoneNumberInput);
		sleep();

		assertThat(viewModel.validProperty()).isTrue(); // phonenumber can be left empty

		phoneNumberInput.setText("012345678");
		sleep();

		assertThat(viewModel.validProperty()).isTrue();

		phoneNumberInput.setText("abc");
		sleep();

		assertThat(viewModel.validProperty()).isFalse();


		phoneNumberInput.setText("+49 1234 324541");
		sleep();

		assertThat(viewModel.validProperty()).isTrue();
	}
	
	
	private void sleep(){
		try {
			Thread.sleep(100);
		}catch (InterruptedException e){
			fail("Thread Sleep didn't work:");
		}
	}
	
}
