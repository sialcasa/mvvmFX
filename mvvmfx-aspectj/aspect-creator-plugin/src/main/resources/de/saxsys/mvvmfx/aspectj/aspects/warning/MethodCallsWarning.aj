package de.saxsys.mvvmfx.aspectj.aspects.warning;

/**
 * Created by gerardo.balderas on 14.09.2016.
 */
public aspect MethodCallsWarning extends MethodCalls{

    declare warning: withinView() && callToModel():"The Model layer was called within the View layer";
    declare warning: withinViewModel() && callToView():"The View layer was called within the Viewmodel layer";
    declare warning: withinModel() && withinView():"The View layer was called within the Model layer";
    declare warning: withinModel() && withinViewModel(): "The ViewModel layer was called within the Model layer";


    declare warning: withinModel() && callWithViewModelArgs(): "A method call with arguments of type ViewModel was made within the Model layer";
    declare warning: withinModel() && callWithGenericArgsViewModel(): "A method call with arguments of generic type ViewModel was made within the Model layer";
    declare warning: withinModel() && callWithViewArgs(): "A method call with arguments of type View was made within the Model layer";
    declare warning: withinModel() && callWithGenericArgsView(): "A method call with arguments of generic type ViewModel was made within the Model layer";

    declare warning: withinViewModel() && callWithViewArgs(): "A method call with arguments of type View was made within the ViewModel layer";
    declare warning: withinViewModel() && callWithGenericArgsView(): "A method call with arguments of generic type View was made within the ViewModel layer";

    declare warning: withinView() && callWithModelArgs(): "A method clal with arguments of type Model was made within the View layer";
    declare warning: withinView() && callWithGenericArgsModel(): "A method call with arguments of generic type Model was made within the View layer";
}
