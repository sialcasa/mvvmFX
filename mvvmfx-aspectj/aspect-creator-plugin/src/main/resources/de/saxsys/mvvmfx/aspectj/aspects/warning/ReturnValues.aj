package de.saxsys.mvvmfx.aspectj.aspects.warning;


import de.saxsys.mvvmfx.aspectj.aspects.WithinMVVM;

/**
 * Created by gerardo.balderas on 24.08.2016.
 */
public abstract aspect ReturnValues extends WithinMVVM {

    // MODEL
    pointcut executionModelReturn(): execution(de.saxsys.mvvmfx.aspectj.aspects.Model+ *.*(..));
    pointcut executionGenTypeModel(): execution(*..*<de.saxsys.mvvmfx.aspectj.aspects.Model+> *.*(..));
    pointcut callModelReturn(): call(de.saxsys.mvvmfx.aspectj.aspects.Model+ *.*(..));
    pointcut callGenTypeModel(): call(*..*<de.saxsys.mvvmfx.aspectj.aspects.Model+> *.*(..));

    // VIEW-MODEL
    pointcut executionViewModelReturn(): execution(de.saxsys.mvvmfx.ViewModel+ *.*(..));
    pointcut executionGenTypeViewModel(): execution(*..*<de.saxsys.mvvmfx.ViewModel+> *.*(..));
    pointcut callViewModelReturn(): call(de.saxsys.mvvmfx.ViewModel+ *.*(..));
    pointcut callGenTypeViewModel(): call(*..*<de.saxsys.mvvmfx.ViewModel+> *.*(..));

    // VIEW
    pointcut executionViewReturn(): execution(de.saxsys.mvvmfx.FxmlView+ *.*(..)) ||
            execution(de.saxsys.mvvmfx.JavaView+ *.*(..));
    pointcut executionGenTypeView(): execution(*..*<de.saxsys.mvvmfx.FxmlView+> *.*(..)) ||
            execution(*..*<de.saxsys.mvvmfx.JavaView+> *.*(..));
    pointcut callViewReturn(): call(de.saxsys.mvvmfx.FxmlView+ *.*(..)) ||
            call(de.saxsys.mvvmfx.JavaView+ *.*(..));
    pointcut callGenTypeView(): call(*..*<de.saxsys.mvvmfx.FxmlView+> *.*(..)) ||
            call(*..*<de.saxsys.mvvmfx.JavaView+> *.*(..));




}
