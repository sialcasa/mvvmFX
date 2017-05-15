package de.saxsys.mvvmfx.devtools.core.analyzer; 
 
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.RootView; 
import de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.example2.RootViewModel; 
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
 
import static de.saxsys.mvvmfx.devtools.core.analyzer.FxmlNodeTestHelper.checkMvvmNode; 
 
/** 
 * Created by gerardo.balderas on 28.04.2017. 
 */ 
public class Example2Test { 
 
 
    @Test 
    public void test() { 
        FxmlNode rootNode = Analyzer.parseFXML(RootView.class); 
        checkMvvmNode(rootNode, RootView.class, RootViewModel.class, 2, 8); 
 
        FxmlNode a = rootNode.getFxIncludes().get(0); 
        checkMvvmNode(a, AView.class, AViewModel.class, 3, 3); 
 
        FxmlNode aa = a.getFxIncludes().get(0); 
        FxmlNode ab = a.getFxIncludes().get(1); 
        FxmlNode ac = a.getFxIncludes().get(2); 
 
        checkMvvmNode(aa, AAView.class, AAViewModel.class, 0, 0); 
        checkMvvmNode(ab, ABView.class, ABViewModel.class, 0, 0); 
        checkMvvmNode(ac, ACView.class, ACViewModel.class, 0, 0); 
 
 
        FxmlNode b = rootNode.getFxIncludes().get(1); 
        checkMvvmNode(b, BView.class, BViewModel.class, 3, 3); 
 
        FxmlNode ba = b.getFxIncludes().get(0); 
        FxmlNode bb = b.getFxIncludes().get(1); 
        FxmlNode bc = b.getFxIncludes().get(2); 
 
        checkMvvmNode(ba, BAView.class, BAViewModel.class, 0, 0); 
        checkMvvmNode(bb, BBView.class, BBViewModel.class, 0, 0); 
        checkMvvmNode(bc, BCView.class, BCViewModel.class, 0, 0); 
    } 
 
} 