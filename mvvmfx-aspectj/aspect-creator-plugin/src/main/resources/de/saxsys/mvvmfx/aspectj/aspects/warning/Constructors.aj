package de.saxsys.mvvmfx.aspectj.aspects.warning;


import de.saxsys.mvvmfx.aspectj.aspects.WithinMVVM;

/**
 * Created by gerardo.balderas on 24.08.2016.
 */
public abstract aspect Constructors extends WithinMVVM {

    pointcut newViewCall(): call(de.saxsys.mvvmfx.FxmlView+.new(..)) || call(de.saxsys.mvvmfx.JavaView+.new(..));
    pointcut newViewModelCall(): call(de.saxsys.mvvmfx.ViewModel+.new(..));
    pointcut newModelCall(): call(de.saxsys.mvvmfx.aspectj.aspects.Model+.new(..));

}
