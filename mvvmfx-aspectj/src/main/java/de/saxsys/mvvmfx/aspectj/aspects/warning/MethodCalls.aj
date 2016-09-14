package de.saxsys.mvvmfx.aspectj.aspects.warning;


import de.saxsys.mvvmfx.aspectj.aspects.WithinMVVM;

/**
 * Created by gerardo.balderas on 24.08.2016.
 */
public abstract aspect MethodCalls extends WithinMVVM {

    pointcut callToView(): call(* de.saxsys.mvvmfx.FxmlView+.*(..)) ||
            call(* de.saxsys.mvvmfx.JavaView+.*(..));
    pointcut callToViewModel(): call(* de.saxsys.mvvmfx.ViewModel+.*(..));
    pointcut callToModel(): call(* de.saxsys.mvvmfx.aspectj.aspects.Model+.*(..));


    pointcut callWithModelArgs(): call(* *.*(.., de.saxsys.mvvmfx.aspectj.aspects.Model+, ..));
    pointcut callWithGenericArgsModel(): call(* *.*(.., *..*<de.saxsys.mvvmfx.aspectj.aspects.Model+>, ..));
    pointcut callWithViewModelArgs(): call(* *.*(.., de.saxsys.mvvmfx.ViewModel+, ..));
    pointcut callWithGenericArgsViewModel(): call(* *.*(.., *..*<de.saxsys.mvvmfx.ViewModel+>, ..));
    pointcut callWithViewArgs(): call(* *.*(.., de.saxsys.mvvmfx.FxmlView+, ..)) ||
            call(* *.*(.., de.saxsys.mvvmfx.JavaView+, ..));
    pointcut callWithGenericArgsView(): call(* *.*(.., *..*<de.saxsys.mvvmfx.FxmlView+>, ..)) ||
            call(* *.*(.., *..*<de.saxsys.mvvmfx.JavaView+>, ..));

}