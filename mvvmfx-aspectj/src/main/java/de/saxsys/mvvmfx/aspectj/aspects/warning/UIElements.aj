package de.saxsys.mvvmfx.aspectj.aspects.warning;


import de.saxsys.mvvmfx.aspectj.aspects.WithinMVVM;

/**
 * Created by gerardo.balderas on 24.08.2016.
 */
public abstract aspect UIElements extends WithinMVVM {

    pointcut callUILibrary(): call(* javafx.scene..*+.*(..)) || call(javafx.scene..*+.new(..));
    pointcut callForUIElements(): call(javafx.scene..*+ *.*(..));
    pointcut executionUIElements(): execution(javafx.scene..*+ *.*(..));

    pointcut callGenericUIElement(): call(*..*<javafx.scene..*+> *.*(..));
    pointcut executionGenericUIElements(): execution(*..*<javafx.scene..*+> *.*(..));

    pointcut callArgsUIElements(): call(* *.*(.., javafx.scene..*+, ..));
    pointcut callGenericArgsUIElements(): call(* *.*(.., *..*<javafx.scene..*+>, ..));

    
    pointcut setUIElementsOnModel(): set(javafx.scene..*+ de.saxsys.mvvmfx.aspectj.aspects.Model+.*);
    pointcut setGenericUIElementsOnModel(): set(*..*<javafx.scene..*+> de.saxsys.mvvmfx.aspectj.aspects.Model+.*);

    pointcut setUIElementsOnViewModel(): set(javafx.scene..*+ de.saxsys.mvvmfx.ViewModel+.*);
    pointcut setGenericUIElementsOnViewModel(): set(*..*<javafx.scene..*+> de.saxsys.mvvmfx.ViewModel+.*);

    pointcut getUIElementsOnModel(): get(javafx.scene..*+ de.saxsys.mvvmfx.aspectj.aspects.Model+.*);
    pointcut getGenericUIElementsOnModel(): get(*..*<javafx.scene..*+> de.saxsys.mvvmfx.aspectj.aspects.Model+.*);

    pointcut getUIElementsOnViewModel(): get(javafx.scene..*+ de.saxsys.mvvmfx.ViewModel+.*);
    pointcut getGenericUIElementsOnViewModel(): get(*..*<javafx.scene..*+> de.saxsys.mvvmfx.ViewModel+.*);

}
