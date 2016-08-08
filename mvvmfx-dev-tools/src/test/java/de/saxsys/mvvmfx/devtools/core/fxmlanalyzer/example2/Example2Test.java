package de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2;

import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.FxmlAnalyzer;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.ViewNode;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.a.AView;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.a.AViewModel;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.a.aa.AAView;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.a.aa.AAViewModel;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.a.ab.ABView;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.a.ab.ABViewModel;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.a.ac.ACView;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.a.ac.ACViewModel;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.b.BView;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.b.BViewModel;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.b.ba.BAView;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.b.ba.BAViewModel;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.b.bb.BBView;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.b.bb.BBViewModel;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.b.bc.BCView;
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.b.bc.BCViewModel;
import org.junit.Test;

import static de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.ViewNodeTestHelper.check;
import static org.assertj.core.api.Assertions.assertThat;

public class Example2Test {

	@Test
	public void test() {
		ViewNode root = FxmlAnalyzer.loadViewTree(RootView.class);

		check(root, 2, RootView.class, RootViewModel.class);
		assertThat(root.getChildren()).hasSize(2);

		ViewNode a = root.getChildren().get(0);
		ViewNode b = root.getChildren().get(1);

		check(a, 3, AView.class, AViewModel.class);
		ViewNode aa = a.getChildren().get(0);
		ViewNode ab = a.getChildren().get(1);
		ViewNode ac = a.getChildren().get(2);

		check(aa, 0, AAView.class, AAViewModel.class);
		check(ab, 0, ABView.class, ABViewModel.class);
		check(ac, 0, ACView.class, ACViewModel.class);


		check(b, 3, BView.class, BViewModel.class);
		ViewNode ba = b.getChildren().get(0);
		ViewNode bb = b.getChildren().get(1);
		ViewNode bc = b.getChildren().get(2);

		check(ba, 0, BAView.class, BAViewModel.class);
		check(bb, 0, BBView.class, BBViewModel.class);
		check(bc, 0, BCView.class, BCViewModel.class);
	}



}
