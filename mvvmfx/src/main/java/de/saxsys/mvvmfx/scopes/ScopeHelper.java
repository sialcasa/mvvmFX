package de.saxsys.mvvmfx.scopes;

import de.saxsys.mvvmfx.ViewModel;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ScopeHelper {
	
	
	public static void newScope(ViewModel scopeViewModel, ViewModel ... subViewModels){

		Optional<ViewModel> nonScopeTarget = Arrays.stream(subViewModels)
				.filter(vm -> !(vm instanceof ScopeTarget))
				.findFirst();
		
		if(nonScopeTarget.isPresent()){
			throw new IllegalStateException("Cannot create Scope. At least one sub-viewModel doesn't implement the interface de.saxsys.mvvmfx.scopes.ScopeTarget. Instance was: " + nonScopeTarget.get());
		}
		


		List<ScopeTarget> scopeTargets = Arrays.stream(subViewModels)
				.map(vm -> (ScopeTarget) vm)
				.collect(Collectors.toList());

		scopeTargets.forEach(target -> injectSubViewModelIntoScope(scopeViewModel, target));
		
	}
	
	private static void injectSubViewModelIntoScope(ViewModel scopeViewModel, ScopeTarget subViewModel){
		// todo
	}
	
}
