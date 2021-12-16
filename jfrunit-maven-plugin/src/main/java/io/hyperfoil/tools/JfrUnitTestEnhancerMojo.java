package io.hyperfoil.tools;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.jboss.logging.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


@Mojo(name = "jfrEnhance"
        , requiresProject = false
        , defaultPhase = LifecyclePhase.PROCESS_TEST_CLASSES
        , requiresDependencyResolution = ResolutionScope.TEST
)
public class JfrUnitTestEnhancerMojo extends AbstractMojo {

    private static final Logger log = Logger.getLogger(JfrUnitTestEnhancerMojo.class);

    @Parameter(defaultValue = "${project}", property = "javassist.project", required = true,
            readonly = true)
    private MavenProject project;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        log.info("Running JFR Unit Enhancement");
        log.info("Scanning for target tests");
/*

        final ClassLoader originalContextClassLoader = currentThread().getContextClassLoader();

        currentThread().setContextClassLoader(new CustomClassloader(originalContextClassLoader));

        ClassLoader classLoader = JfrUnitTestEnhancerMojo.class.getClassLoader();
*/

        try {
            final List<URL> classPath = new ArrayList<URL>();
            List<String> testClasspathElements = project.getTestClasspathElements();
            for (String element : testClasspathElements){
                log.info(element);
                classPath.add(resolveUrl(element));
            }

//            classLoader.



        } catch (DependencyResolutionRequiredException e) {
            e.printStackTrace();
        }

    }

    private URL resolveUrl(final String resource) {
        try {
            return Path.of(resource).toUri().toURL();
        } catch (final InvalidPathException | MalformedURLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
