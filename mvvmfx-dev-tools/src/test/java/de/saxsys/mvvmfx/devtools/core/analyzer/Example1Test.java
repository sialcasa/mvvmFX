package de.saxsys.mvvmfx.devtools.core.analyzer; 
 
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example1.RootView; 
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example1.RootViewModel; 
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example1.a.AView; 
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example1.a.AViewModel; 
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example1.b.BView; 
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example1.b.BViewModel; 
import org.junit.Test; 
 
import static de.saxsys.mvvmfx.devtools.core.analyzer.FxmlNodeTestHelper.checkMvvmNode; 
 
/** 
 * Created by gerardo.balderas on 28.04.2017. 
 */ 
public class Example1Test { 
 
    @Test 
    public void test(){ 
        FxmlNode rootNode = Analyzer.parseFXML(de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example1.RootView.class); 
 
        checkMvvmNode(rootNode, RootView.class, RootViewModel.class, 2, 2); 
 
        //AView.fxml 
        FxmlNode aNode = rootNode.getFxIncludes().get(0); 
        checkMvvmNode(aNode, AView.class, AViewModel.class,0,0); 
 
        //BView.fxml 
        FxmlNode bNode = rootNode.getFxIncludes().get(1); 
        checkMvvmNode(bNode, BView.class, BViewModel.class,0,0); 
    } 
} 
