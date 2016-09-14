package de.saxsys.mvvmfx.aspectj.aspects;

/**
 * Created by gerardo.balderas on 24.08.2016.
 */
public abstract aspect WithinMVVM {
    public pointcut withinView(): within(de.saxsys.mvvmfx.FxmlView+) || within(de.saxsys.mvvmfx.JavaView+);
    public pointcut withinViewModel(): within(de.saxsys.mvvmfx.ViewModel+);
    public pointcut withinModel(): within(de.saxsys.mvvmfx.aspectj.aspects.Model+);


    declare parents: com.example.business..* || com.example.domain..* || com.example.util..* implements de.saxsys.mvvmfx.aspectj.aspects.Model;

    declare parents: *..model..* implements de.saxsys.mvvmfx.aspectj.aspects.Model;

}
