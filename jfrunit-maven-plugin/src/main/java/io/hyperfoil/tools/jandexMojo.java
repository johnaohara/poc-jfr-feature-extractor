package io.hyperfoil.tools;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.jboss.jandex.*;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;
import static org.reflections.scanners.Scanners.TypesAnnotated;

@Mojo(name = "jandex"
        , requiresProject = false
        , defaultPhase = LifecyclePhase.PROCESS_TEST_CLASSES
        , requiresDependencyResolution = ResolutionScope.TEST
)
public class jandexMojo extends AbstractMojo {

    private static final Logger log = Logger.getLogger(jandexMojo.class);

    @Parameter(defaultValue = "${project}", property = "javassist.project", required = true,
            readonly = true)
    private MavenProject project;

    @Parameter(defaultValue = "/home/johara/.m2/repository/io/quarkus/quarkus-integration-test-resteasy-jackson/2.4.1.Final/quarkus-integration-test-resteasy-jackson-2.4.1.Final-tests-jar.idx", property = "indexLocation", required = true,
            readonly = true)
    private String indexlocation;




    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        log.info("Loading jandex index");

        Index index;
        try(FileInputStream input = new FileInputStream(indexlocation)) {

            IndexReader reader = new IndexReader(input);
            index = reader.read();

            // Retrieve Map from the index and print its declared methods
            ClassInfo clazz = index.getClassByName(DotName.createSimple("java.util.Map"));

            for (MethodInfo method : clazz.methods()) {
                System.out.println(method);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

}
