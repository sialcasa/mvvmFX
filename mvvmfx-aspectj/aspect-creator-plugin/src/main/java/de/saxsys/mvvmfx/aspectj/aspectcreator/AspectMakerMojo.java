package de.saxsys.mvvmfx.aspectj.aspectcreator;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.ArrayList;
import java.util.List;

@Mojo(name = "createaspects")
public class AspectMakerMojo extends AbstractMojo{

    /**
     * Model Packages.
     */
    @Parameter
    private List<String> modelPackages = new ArrayList<>();

    /**
     * Model Classes.
     */
    @Parameter
    private List<String> modelClasses = new ArrayList<>();

    public void execute() throws MojoExecutionException {
        Log log = getLog();
        log.info("Creating aspects");

            modelPackages.forEach((String p) -> {
                p = p.trim() + "..*";
                Config.addToModel(p);
                log.info("added as model: "+p);
            });

            modelClasses.forEach((String c) -> {
                c = c.trim();
                Config.addToModel(c);
                log.info("added as model: "+c);

            });

        CodeGenerator codeGenerator = new CodeGenerator();
        codeGenerator.createAspects();

        log.info("Aspects created");
    }
}
