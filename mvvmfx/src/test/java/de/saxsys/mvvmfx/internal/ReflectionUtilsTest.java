package de.saxsys.mvvmfx.internal;

import static org.assertj.core.api.Assertions.*;

import de.saxsys.mvvmfx.internal.ReflectionUtils;
import de.saxsys.mvvmfx.internal.viewloader.View;
import org.junit.Test;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.internal.viewloader.example.TestViewModel;

public class ReflectionUtilsTest {
	
	
	@Test
	public void testCreateViewModel(){
		class TestView implements View<TestViewModel> {
		}

		ViewModel viewModel = ReflectionUtils.createViewModel(new TestView());
		
		assertThat(viewModel).isNotNull().isInstanceOf(TestViewModel.class);
	}
	
	@Test
	public void testCreateViewModelWithoutViewModelType(){
		class TestView implements View{
		}

		ViewModel viewModel = ReflectionUtils.createViewModel(new TestView());
		
		assertThat(viewModel).isNull();
	}
	
	@Test
	public void testCreateViewModelWithGeneticViewModelAsType(){
		class TestView implements View<ViewModel>{
		}
		
		ViewModel viewModel = ReflectionUtils.createViewModel(new TestView());
		
		assertThat(viewModel).isNull();
	}
}
