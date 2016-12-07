package de.saxsys.mvvmfx.aspectj.aspectcreator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

class JarFileCopier {

    private JarFileCopier(){}

    static void copyResourceDirectory() {

        try {
            Files.createDirectories(Paths.get("./src/main/aspect/de/saxsys/mvvmfx/aspectj/aspects/warning"));

            File file = new File(JarFileCopier.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String path = "de/saxsys/mvvmfx/aspectj/aspects/";
            copyFilesFromJar(file, path);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private static void copyFilesFromJar(File jarFile, String path) throws IOException {
        JarFile jar = new JarFile(jarFile);

        jar.stream()
                .filter(jarEntry -> !jarEntry.isDirectory())
                .map(JarEntry::getName)
                .filter(fileName -> fileName.startsWith(path))
                .forEach(JarFileCopier::copyFile);
    }

    private static void copyFile(String fileName) {
        try {
            Path target = Paths.get("./src/main/aspect/" + fileName);

            FileOutputStream out = new FileOutputStream(target.toFile());
            byte[] byteArray = new byte[1024];
            int i;
            //Copies file from .jar to a the target file
            InputStream in = JarFileCopier.class.getClassLoader().getResourceAsStream(fileName);
            while ((i = in.read(byteArray)) > 0) {
                out.write(byteArray, 0, i);
            }

            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
