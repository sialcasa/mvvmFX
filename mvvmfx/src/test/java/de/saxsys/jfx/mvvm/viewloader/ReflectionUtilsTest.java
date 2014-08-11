package de.saxsys.jfx.mvvm.viewloader;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import de.saxsys.jfx.mvvm.api.ViewModel;
import de.saxsys.jfx.mvvm.base.view.View;
import de.saxsys.jfx.mvvm.viewloader.example.TestViewModel;

public class ReflectionUtilsTest {
	
	
	@Test
	public void testCreateViewModel(){
		class TestView implements View<TestViewModel> {
		}

		ViewModel viewModel = ReflectionUtils.createViewModel(TestView.class);
		
		assertThat(viewModel).isNotNull().isInstanceOf(TestViewModel.class);
	}
	
	@Test
	public void testCreateViewModelWithoutViewModelType(){
		class TestView implements View{
		}

		ViewModel viewModel = ReflectionUtils.createViewModel(TestView.class);
		
		assertThat(viewModel).isNull();
	}
	
	@Test
	public void testCreateViewModelWithGeneticViewModelAsType(){
		class TestView implements View<ViewModel>{
		}
		
		ViewModel viewModel = ReflectionUtils.createViewModel(TestView.class);
		
		assertThat(viewModel).isNull();
	}
}
