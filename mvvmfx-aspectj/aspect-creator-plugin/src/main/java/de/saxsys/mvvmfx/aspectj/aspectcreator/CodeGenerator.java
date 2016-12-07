package de.saxsys.mvvmfx.aspectj.aspectcreator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

class CodeGenerator {

    CodeGenerator() {
    }

    private Pointcut withinModelPointcut = new Pointcut("withinModel()", "within", Config.modelInterface);
    private Pointcut withinViewModelPointcut = new Pointcut("withinViewModel()", "within", Config.viewModelInterface);
    private Pointcut withinViewPointcut = new Pointcut("withinView()","within", Config.viewInterfaces);

    void createAspects() {
        Path aspectsPath = Paths.get("./src/main/aspect");
        if (aspectsPath.toFile().exists()) {
            deleteFiles(aspectsPath);
        }
        JarFileCopier.copyResourceDirectory();
        createWithinMVVMAspect();
    }

    private void createWithinMVVMAspect() {
        String aspect = "package de.saxsys.mvvmfx.aspectj.aspects; \n\n" +
                "public abstract aspect " + "WithinMVVM" + " {\n\n" +
                "    public " + withinModelPointcut.toString() + "\n" +
                "    public " + withinViewPointcut.toString() + "\n" +
                "    public " + withinViewModelPointcut.toString() + "\n\n" +
                "    " + Declaration.declareParents(Config.modelInterface, Config.model) +
                "\n}";

        Path withinMVVMPath = Paths.get("./src/main/aspect/de/saxsys/mvvmfx/aspectj/aspects/WithinMVVM.aj");
        byte data[] = aspect.getBytes();
        try {
            if (!withinMVVMPath.toFile().exists()) {
                Files.createFile(withinMVVMPath);
                Files.write(withinMVVMPath, data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFiles(Path dir){
        try {

            Files.walk(dir)
                    .map(Path::toFile)
                    .sorted(Collections.reverseOrder())
                    .forEach(File::delete);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
