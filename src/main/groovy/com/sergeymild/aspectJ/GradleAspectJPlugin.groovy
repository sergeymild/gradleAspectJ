package com.sergeymild.aspectJ

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException
import org.gradle.api.tasks.compile.JavaCompile
import org.aspectj.tools.ajc.Main

class GradleAspectJPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        final def logger = project.logger
        logger.debug "AspectJ apply plugin"
        final def variants
        if (project.plugins.withType(AppPlugin)) {
            variants = project.android.applicationVariants
        } else if (project.plugins.withType(LibraryPlugin)) {
            variants = project.android.libraryVariants
        } else {
            throw new ProjectConfigurationException("Either 'com.android.application' or 'com.android.library' " +
                    "plugin is required.", null)
        }

        project.extensions.create('gradleAspectJ', GradleAspectJPluginExtension)
        final def ext = project.gradleAspectJ

        final aspectJVersion = "1.8.9"

        if (ext.enabled) {
            project.dependencies {
                compile "org.aspectj:aspectjrt:${aspectJVersion}"
            }
        }

        variants.all { variant ->
            if (!ext.enabled) {
                logger.debug("AspectJ weaving is disabled for build type '${variant.buildType.name}'.")
                return
            }

            JavaCompile javaCompile = variant.javaCompile
            javaCompile.doLast {

                final def inPath = javaCompile.destinationDir.toString()
                final def destinationDir = inPath
                final def aspectPath = javaCompile.classpath.asPath
                final def classPath = aspectPath
                final def bootClassPath = project.android.bootClasspath.join(File.pathSeparator)

                String[] args = ["-showWeaveInfo", "-1.5",
                                 "-inpath", inPath,
                                 "-aspectpath", aspectPath,
                                 "-d", destinationDir,
                                 "-classpath", classPath,
                                 "-bootclasspath", bootClassPath]
                Main main = new Main()
                main.runMain(args, false)
            }
        }
    }
}
