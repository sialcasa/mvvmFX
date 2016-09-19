package de.saxsys.mvvmfx.aspectj.aspects.warning;


import de.saxsys.mvvmfx.aspectj.aspects.WithinMVVM;

/**
 * Created by gerardo.balderas on 24.08.2016.
 */
public abstract aspect Instancevariables extends WithinMVVM {

    pointcut getModelVarInView(): get(de.saxsys.mvvmfx.aspectj.aspects.Model+ de.saxsys.mvvmfx.FxmlView+.*) ||
            get(de.saxsys.mvvmfx.aspectj.aspects.Model+ de.saxsys.mvvmfx.JavaView+.*);
    pointcut getGenModelVarInView(): get(*..*<de.saxsys.mvvmfx.aspectj.aspects.Model+> de.saxsys.mvvmfx.FxmlView+.*) ||
            get(*..*<de.saxsys.mvvmfx.aspectj.aspects.Model+> de.saxsys.mvvmfx.JavaView+.*);

    pointcut getViewVarInViewModel(): get(de.saxsys.mvvmfx.FxmlView+ de.saxsys.mvvmfx.ViewModel+.*) ||
            get(de.saxsys.mvvmfx.JavaView+ de.saxsys.mvvmfx.ViewModel+.*);
    pointcut getGenViewVarInViewModel(): get(*..*<de.saxsys.mvvmfx.FxmlView+> de.saxsys.mvvmfx.ViewModel+.*) ||
            get(*..*<de.saxsys.mvvmfx.JavaView+> de.saxsys.mvvmfx.ViewModel+.*);

    pointcut getViewVarInModel(): get(de.saxsys.mvvmfx.FxmlView+ de.saxsys.mvvmfx.aspectj.aspects.Model+.*) ||
            get(de.saxsys.mvvmfx.JavaView+ de.saxsys.mvvmfx.aspectj.aspects.Model+.*);
    pointcut getGenViewVarInModel(): get(*..*<de.saxsys.mvvmfx.FxmlView+> de.saxsys.mvvmfx.aspectj.aspects.Model+.*) ||
            get(*..*<de.saxsys.mvvmfx.JavaView+> de.saxsys.mvvmfx.aspectj.aspects.Model+.*);
    pointcut getViewModelVarInModel(): get(de.saxsys.mvvmfx.ViewModel+ de.saxsys.mvvmfx.aspectj.aspects.Model+.*);
    pointcut getGenViewModelVarInModel(): get(*..*<de.saxsys.mvvmfx.ViewModel+> de.saxsys.mvvmfx.aspectj.aspects.Model+.*);



    pointcut setModelVarInView(): set(de.saxsys.mvvmfx.aspectj.aspects.Model+ de.saxsys.mvvmfx.FxmlView+.*) ||
            set(de.saxsys.mvvmfx.aspectj.aspects.Model+ de.saxsys.mvvmfx.JavaView+.*);
    pointcut setGenModelVarInView(): set(*..*<de.saxsys.mvvmfx.aspectj.aspects.Model+> de.saxsys.mvvmfx.FxmlView+.*) ||
            set(*..*<de.saxsys.mvvmfx.aspectj.aspects.Model+> de.saxsys.mvvmfx.JavaView+.*);

    pointcut setViewVarInViewModel(): set(de.saxsys.mvvmfx.FxmlView+ de.saxsys.mvvmfx.ViewModel+.*) ||
            set(de.saxsys.mvvmfx.JavaView+ de.saxsys.mvvmfx.ViewModel+.*);
    pointcut setGenViewVarInViewModel(): set(*..*<de.saxsys.mvvmfx.FxmlView+> de.saxsys.mvvmfx.ViewModel+.*) ||
            set(*..*<de.saxsys.mvvmfx.JavaView+> de.saxsys.mvvmfx.ViewModel+.*);


    pointcut setViewVarInModel(): set(de.saxsys.mvvmfx.FxmlView+ de.saxsys.mvvmfx.aspectj.aspects.Model+.*) ||
            set(de.saxsys.mvvmfx.JavaView+ de.saxsys.mvvmfx.aspectj.aspects.Model+.*);
    pointcut setViewModelVarInModel(): set(de.saxsys.mvvmfx.ViewModel+ de.saxsys.mvvmfx.aspectj.aspects.Model+.*);
    pointcut setGenViewVarInModel(): set(*..*<de.saxsys.mvvmfx.FxmlView+> de.saxsys.mvvmfx.aspectj.aspects.Model+.*) ||
            set(*..*<de.saxsys.mvvmfx.JavaView+> de.saxsys.mvvmfx.aspectj.aspects.Model+.*);
    pointcut setGenViewModelVarInModel(): set(*..*<de.saxsys.mvvmfx.ViewModel+> de.saxsys.mvvmfx.aspectj.aspects.Model+.*);


}
