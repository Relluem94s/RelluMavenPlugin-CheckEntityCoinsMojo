package de.relluem94.maven.plugin.entitycheckcoins;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;
import org.bukkit.entity.EntityType;

@Mojo(name = "checkEntityCoins", threadSafe = true)
public class CheckEntityCoinsMojo extends AbstractMojo {

    @Component
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException {
        try {
            URL[] urls = project.getCompileClasspathElements().stream()
                .map(path -> {
                    try {
                        return new java.io.File(path).toURI().toURL();
                    } catch (java.net.MalformedURLException e) {
                        getLog().error("Invalid path in project classpath: " + path, e);
                        return null;
                    }
                })
                .filter(url -> url != null)
                .toArray(URL[]::new);

            ClassLoader projectClassLoader = new URLClassLoader(urls, this.getClass().getClassLoader());

            Class<?> enumClass = Class.forName(
                "de.relluem94.minecraft.server.spigot.essentials.constants.EntityCoins",
                true,
                projectClassLoader
            );

            if (!enumClass.isEnum()) {
                throw new MojoExecutionException("EntityCoins is not an enum!");
            }

            Object[] enumConstants = enumClass.getEnumConstants();
            Set<String> enumNames = new HashSet<>();
            for (Object constant : enumConstants) {
                enumNames.add(constant.toString());
            }

            for (EntityType type : EntityType.values()) {
                if (!enumNames.contains(type.name())) {
                    getLog().warn("Entity missing in enum: " + type.name());
                }
            }
            
            for (String enumName : enumNames) {
                if ("UNKNOWN".equals(enumName)) {
                    continue;
                }
                boolean exists = false;
                for (EntityType type : EntityType.values()) {
                    if (type.name().equals(enumName)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    getLog().warn("Enum contains value not in EntityType: " + enumName);
                }
            }

        } catch (Exception e) {
            throw new MojoExecutionException("EntityCoins class not found in project", e);
        }
    }
}

