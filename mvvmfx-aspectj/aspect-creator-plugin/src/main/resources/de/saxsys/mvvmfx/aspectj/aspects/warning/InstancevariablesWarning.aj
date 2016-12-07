package de.saxsys.mvvmfx.aspectj.aspects.warning;

/**
 * Created by gerardo.balderas on 14.09.2016.
 */
public aspect InstancevariablesWarning extends Instancevariables {

    declare warning: getModelVarInView() || getGenModelVarInView(): "Model variable was accessed in View layer";

    declare warning: getViewVarInModel() || getGenViewVarInModel(): "View variable was accessed in ViewModel layer";

    declare warning: getViewVarInModel() || getGenViewVarInModel(): "View variable was accessed in Model layer";
    declare warning: getViewModelVarInModel() || getGenViewModelVarInModel(): "ViewModel variable was accessed in Model layer";


    declare warning: setModelVarInView() || setGenModelVarInView(): "Model variable was set in View layer";

    declare warning: setViewVarInModel() || setGenViewVarInModel(): "View variable was set in ViewModel layer";

    declare warning: setViewVarInModel() || setGenViewVarInModel(): "View variable was set in Model layer";
    declare warning: setViewModelVarInModel() || setGenViewModelVarInModel(): "ViewModel set was found in Model layer";
}
