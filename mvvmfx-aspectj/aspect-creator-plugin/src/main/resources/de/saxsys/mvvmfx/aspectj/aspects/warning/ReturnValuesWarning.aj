package de.saxsys.mvvmfx.aspectj.aspects.warning;

/**
 * Created by gerardo.balderas on 14.09.2016.
 */
public aspect ReturnValuesWarning extends ReturnValues{

    // MODEL
    declare warning: withinModel() && executionViewModelReturn():
            "A method with a return value of type ViewModel was executed within the Model layer";
    declare warning:  withinModel() && executionGenTypeViewModel():
            "A method with a return value with generic type of ViewModel was executed within the Model layer";
    declare warning: withinModel() && callViewModelReturn():
            "A method with a return value of type ViewModel called within the Model layer";
    declare warning: withinModel() && callGenTypeViewModel():
            "A method with a return value with generic type of ViewModel was called within the Model layer";

    declare warning: withinModel() && executionViewReturn():
            "A method with a return value of type ViewModel was executed within the Model layer";
    declare warning: withinModel() && executionGenTypeView():
            "A method with a return value with generic type of View was executed within the Model layer";
    declare warning: withinModel() && callViewReturn():
            "A method with a return value with of type View was called within the Model layer";
    declare warning: withinModel() && callGenTypeView():
            "A method with a return value with generic type of View was called within the Model layer";


    // VIEW-MODEL
    declare warning: withinViewModel() && executionViewReturn():
            "A method with a return value of type View was executed within the ViewModel layer";
    declare warning: withinViewModel() && executionGenTypeView():
            "A method with a return value with generic type of View was executed within the ViewModel layer";
    declare warning: withinViewModel() && callViewReturn():
            "A method with a return value of type View was called within the ViewModel layer";
    declare warning: withinViewModel() && callGenTypeView():
            "A method with a return value with generic type of View was called within the ViewModel layer";


    // VIEW
    declare warning: withinView() && executionModelReturn():
            "Method with a return value of type Model was executed in the View layer";
    declare warning: withinView() && executionGenTypeModel():
            "A method with a return value with generic type of Model was executed within the View layer";
    declare warning: withinView() && callModelReturn():
            "A method with a return value of type Model called within the View layer";
    declare warning: withinView() && callGenTypeModel():
            "A method with a return value with generic type of Model was called within the View layer";


}
