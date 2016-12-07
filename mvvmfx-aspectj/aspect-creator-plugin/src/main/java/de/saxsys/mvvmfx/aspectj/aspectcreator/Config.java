package de.saxsys.mvvmfx.aspectj.aspectcreator;

import java.util.ArrayList;

final class Config {

    static ArrayList<String> model = new ArrayList<>();
    static String modelInterface = "Model+";
    static String viewInterfaces = "de.saxsys.mvvmfx.JavaView+ || de.saxsys.mvvmfx.FxmlView+";
    static String viewModelInterface = "de.saxsys.mvvmfx.ViewModel+";


    static void addToModel(String packageName){
        model.add(packageName);
    }

}
