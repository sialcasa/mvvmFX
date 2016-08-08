package de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example1;

import static de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.ViewNodeTestHelper.check;

import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.FxmlAnalyzer;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.ViewNode;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example1.a.AView;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example1.a.AViewModel;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example1.b.BView;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example1.b.BViewModel;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Example1Test {

	@Test
	public void test() {

		ViewNode root = FxmlAnalyzer.loadViewTree(RootView.class);

		check(root, 2, RootView.class, RootViewModel.class);

		List<ViewNode> children = root.getChildren();
		ViewNode a = children.get(0);
		ViewNode b = children.get(1);

		check(a, 0, AView.class, AViewModel.class);
		check(b, 0, BView.class, BViewModel.class);
	}

}
