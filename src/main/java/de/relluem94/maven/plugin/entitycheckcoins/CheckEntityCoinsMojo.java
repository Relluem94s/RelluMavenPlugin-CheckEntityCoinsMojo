package de.relluem94.maven.plugin.entitycheckcoins;

import java.util.HashSet;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.bukkit.entity.EntityType;

@Mojo(name = "checkEntityCoins")
public class CheckEntityCoinsMojo extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException {
        Set<String> enumNames = new HashSet<String>();
        for (EntityCoins coin : EntityCoins.values()) {
            enumNames.add(coin.name());
        }

        for (EntityType type : EntityType.values()) {
            if (!enumNames.contains(type.name())) {
                getLog().warn("Entity fehlt im Enum: " + type.name());
            }
        }
    }
}

