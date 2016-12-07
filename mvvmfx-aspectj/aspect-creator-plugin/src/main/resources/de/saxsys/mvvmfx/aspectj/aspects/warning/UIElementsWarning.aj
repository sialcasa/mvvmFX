package de.saxsys.mvvmfx.aspectj.aspects.warning;

/**
 * Created by gerardo.balderas on 14.09.2016.
 */
public aspect UIElementsWarning extends UIElements {

    declare warning: withinViewModel() && callUILibrary():
            "The UI-library were called within the ViewModel layer";
    declare warning: withinModel() && callUILibrary():
            "The UI-library were called within the Model layer";

    declare warning: withinViewModel() && callForUIElements():
            "A UI-element was called within the ViewModel layer";
    declare warning: withinModel() && callForUIElements():
            "A UI-element was called within the Model layer";

    declare warning: withinViewModel() && executionUIElements():
            "Methods returning UI-elements were executed within the ViewModel layer";
    declare warning: withinModel() && executionUIElements():
            "Methods returning UI-elements were executed within the Model layer";

    declare warning: withinViewModel() && executionGenericUIElements():
            "Methods returning a generic UI-Element were executed wihtin the ViewModel layer";
    declare warning: withinModel() && executionGenericUIElements():
            "Methods returning a generic UI-Element were executed wihtin the Model layer";


    declare warning: setUIElementsOnModel():
            "UI-Element was set on the Model layer";
    declare warning: setGenericUIElementsOnModel():
            "a generic UI-Element was set on the Model layer";
    declare warning: setUIElementsOnViewModel():
            "UI-Element was set on the ViewModel layer";
    declare warning: setGenericUIElementsOnViewModel():
            "UI-Element was set on the ViewModel layer";

    declare warning: getUIElementsOnModel():
            "UI-Element was accessed on the Model layer";
    declare warning: getGenericUIElementsOnModel():
            "a generic UI-Element was accessed on the Model layer";
    declare warning: getUIElementsOnViewModel():
            "UI-Element was accessed on the ViewModel layer";
    declare warning: getGenericUIElementsOnViewModel():
            "UI-Element was accessed on the ViewModel layer";

}
