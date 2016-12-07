package de.saxsys.mvvmfx.aspectj.aspectcreator;

import java.util.ArrayList;

class Declaration {

    static String declareParents(String parentName, ArrayList<String> signatures) {
        if(signatures.isEmpty()) return "";

        StringBuilder declaration = new StringBuilder("declare parents: ");

        for(int i = 0; i<signatures.size()-1; i++){
            declaration.append(signatures.get(i)).append(" || ");
        }

        //last item
        declaration.append(signatures.get(signatures.size()-1));

        declaration.append(" implements ").append(parentName).append(";");



        return declaration.toString();
    }
}
