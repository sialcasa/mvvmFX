package de.saxsys.jfx.mvvmfx.fx_root_example;

import org.junit.Before;
import org.junit.Test;

import static eu.lestard.assertj.javafx.api.Assertions.assertThat;

public class LabeledTextFieldTest {
    
    private LabeledTextFieldViewModel viewModel;
    
    @Before
    public void setup(){
        viewModel = new LabeledTextFieldViewModel();
    }
    
    @Test
    public void testButtonDisabled(){
        
        viewModel.inputTextProperty().set("");
        assertThat(viewModel.buttonDisabledProperty()).isTrue();
        
        
        viewModel.inputTextProperty().set("hello");
        assertThat(viewModel.buttonDisabledProperty()).isFalse();
        
        viewModel.inputTextProperty().set(null);
        assertThat(viewModel.buttonDisabledProperty()).isTrue();
    }

    /**
     * When the user presses the action button, the entered text should be used as new label text. 
     * The input textfield should be cleared.
     */
    @Test
    public void testOnAction(){
        assertThat(viewModel.labelTextProperty()).hasValue("default");
        
        viewModel.inputTextProperty().set("hello");
        
        assertThat(viewModel.labelTextProperty()).hasValue("default"); // label has still the old value
        
        viewModel.changeLabel(); 
        
        assertThat(viewModel.labelTextProperty()).hasValue("hello"); // now the label has the new value
        
        assertThat(viewModel.inputTextProperty()).hasValue("");
    }
}
