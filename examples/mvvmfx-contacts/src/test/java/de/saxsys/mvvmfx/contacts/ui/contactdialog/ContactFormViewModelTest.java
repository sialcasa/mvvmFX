package de.saxsys.mvvmfx.contacts.ui.contactdialog;

import de.saxsys.mvvmfx.contacts.ui.contactform.ContactFormViewModel;
import de.saxsys.mvvmfx.testingutils.GCVerifier;
import org.junit.Before;
import org.junit.Test;

import static eu.lestard.assertj.javafx.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

public class ContactFormViewModelTest {

    private ContactFormViewModel viewModel;

    @Before
    public void setup() {
        viewModel = new ContactFormViewModel();
    }


    @Test
    public void testFirstname() {

        assertThat(viewModel.firstnameValidation().getErrorMessages()).hasSize(1);
        assertThat(viewModel.firstnameValidation().validProperty()).isFalse();


        viewModel.firstnameProperty().set("Horst");

        assertThat(viewModel.firstnameValidation().validProperty()).isTrue();
        assertThat(viewModel.firstnameValidation().getErrorMessages()).isEmpty();


        viewModel.firstnameProperty().setValue("");
        assertThat(viewModel.firstnameValidation().getErrorMessages()).hasSize(1);
        assertThat(viewModel.firstnameValidation().validProperty()).isFalse();
    }

    @Test
    public void testEmail() {
        GCVerifier.forceGC();


        assertThat(viewModel.emailValidation().getErrorMessages()).hasSize(2);
        assertThat(viewModel.emailValidation().validProperty()).isFalse();

        viewModel.emailProperty().set("Something");

        assertThat(viewModel.emailValidation().getErrorMessages()).hasSize(1);
        assertThat(viewModel.emailValidation().validProperty()).isFalse();

        viewModel.emailProperty().set("test@example.org");

        assertThat(viewModel.emailValidation().getErrorMessages()).isEmpty();
        assertThat(viewModel.emailValidation().validProperty()).isTrue();
    }



}
