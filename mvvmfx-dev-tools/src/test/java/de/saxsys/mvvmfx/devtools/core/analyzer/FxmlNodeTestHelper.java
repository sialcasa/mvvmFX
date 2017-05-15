package de.saxsys.mvvmfx.devtools.core.analyzer; 
 
import static org.assertj.core.api.Assertions.assertThat; 
 
/** 
 * Created by gerardo.balderas on 25.04.2017. 
 */ 
public class FxmlNodeTestHelper { 
 
 
    public static void checkMvvmNode(FxmlNode node, Class<?> viewClass, Class<?> viewModelClass, int directChildren, int totalChildren){ 
        assertThat(node instanceof MvvmFxmlNode).isTrue(); 
        MvvmFxmlNode mvvmNode = (MvvmFxmlNode) node; 
        assertThat(mvvmNode.getControllerClass()).isEqualTo(viewClass); 
        assertThat(mvvmNode.getViewModelClass()).isEqualTo(viewModelClass); 
 
        assertThat(mvvmNode.getDirectChildren()).isEqualTo(directChildren); 
        assertThat(mvvmNode.getTotalChildren()).isEqualTo(totalChildren); 
 
    } 
 
} 