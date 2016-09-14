package de.saxsys.mvvmfx.aspectj.aspects.warning;

/**
 * Created by gerardo.balderas on 14.09.2016.
 */
public aspect ReturnValuesError extends ReturnValues {

    declare error: withinModel() && executionViewModelReturn():
            "A method with a return value of type ViewModel was executed within the Model layer";
}
