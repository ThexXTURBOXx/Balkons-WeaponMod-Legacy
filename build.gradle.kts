import org.apache.commons.lang3.SystemUtils
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

plugins {
    idea
    java
    id("gg.essential.loom") version "1.6.9999-fg3"
    // TODO: Migrate back when PR is merged: id("gg.essential.loom") version "1.6.+"
}

val minecraft_version: String by project
val minecraft_version_range: String by project
val archives_base_name: String by project
val mod_version: String by project
val maven_group: String by project
val forge_version: String by project
val forge_version_range: String by project
val loader_version_range: String by project
val mod_id: String by project
val mod_name: String by project
val mod_license: String by project
val mod_group_id: String by project
val mod_authors: String by project
val mod_authors_list: String by project
val mod_description: String by project
val mod_issues_url: String by project
val mod_sources_url: String by project
val mod_home_url: String by project
val mod_update_json: String by project

version = "forge-$minecraft_version-$mod_version"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

loom {
    runConfigs {
        "client" {
            if (SystemUtils.IS_OS_MAC_OSX) {
                // This argument causes a crash on macOS
                vmArgs.remove("-XstartOnFirstThread")
            }
        }
        remove(getByName("server"))
    }
    forge {
    }
}

sourceSets.main {
    output.setResourcesDir(sourceSets.main.flatMap { it.java.classesDirectory })
}

repositories {
    mavenCentral()
    maven("https://repo.spongepowered.org/maven/")
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft_version")
    mappings("de.oceanlabs.mcp:mcp_stable:58-1.14.4")
    forge("net.minecraftforge:forge:$forge_version")
}

tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
}

tasks.withType(Jar::class) {
    archiveBaseName.set(archives_base_name)
    manifest.attributes.run {
        this["Specification-Title"] = mod_name
        this["Specification-Vendor"] = mod_authors
        this["Specification-Version"] = "1" // We are version 1 of ourselves
        this["Implementation-Title"] = mod_name
        this["Implementation-Version"] = mod_version
        this["Implementation-Vendor"] = mod_authors
        this["Implementation-Timestamp"] =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ").format(OffsetDateTime.now())
    }
}

tasks.processResources {
    inputs.property("minecraft_version", minecraft_version)
    inputs.property("minecraft_version_range", minecraft_version_range)
    inputs.property("forge_version", forge_version)
    inputs.property("forge_version_range", forge_version_range)
    inputs.property("loader_version_range", loader_version_range)
    inputs.property("mod_id", mod_id)
    inputs.property("mod_name", mod_name)
    inputs.property("mod_license", mod_license)
    inputs.property("mod_version", mod_version)
    inputs.property("mod_authors", mod_authors)
    inputs.property("mod_authors_list", mod_authors_list)
    inputs.property("mod_description", mod_description)
    inputs.property("mod_issues_url", mod_issues_url)
    inputs.property("mod_sources_url", mod_sources_url)
    inputs.property("mod_home_url", mod_home_url)
    inputs.property("mod_update_json", mod_update_json)

    filesMatching(listOf("META-INF/mods.toml", "pack.mcmeta")) {
        expand(inputs.properties)
    }
}

tasks.remapJar {
    archiveBaseName.set(archives_base_name)
    archiveVersion.set(version.toString())
    archiveClassifier.set("")
}

tasks.assemble.get().dependsOn(tasks.remapJar)
