package de.saxsys.mvvmfx.aspectj.aspects.warning;

/**
 * Created by gerardo.balderas on 14.09.2016.
 */
public aspect ConstructorsWarning extends Constructors{


    declare warning: withinView() && newModelCall(): "A Model Instance was made within the View Layer";
    declare warning: withinViewModel() && newViewCall(): "A View Instance was made within the Viewmodel Layer";
    declare warning: withinModel() && newViewCall(): "A View Instance was made within the Model Layer";
    declare warning: withinModel() && newViewModelCall(): "A ViewModel Instance was made within the Model Layer";
}
