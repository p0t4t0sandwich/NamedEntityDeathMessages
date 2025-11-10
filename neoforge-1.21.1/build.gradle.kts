import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import xyz.wagyourtail.unimined.api.minecraft.task.RemapJarTask
import java.time.Instant

plugins {
    id("java")
    id("maven-publish")
    id("idea")
    id("eclipse")
    alias(libs.plugins.blossom)
    alias(libs.plugins.shadow)
    alias(libs.plugins.spotless)
    alias(libs.plugins.unimined)
}

base {
    archivesName = "$modName-neoforge-1.21.1"
}

java.toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
java.sourceCompatibility = JavaVersion.toVersion(javaVersion)
java.targetCompatibility = JavaVersion.toVersion(javaVersion)

spotless {
    format("misc") {
        target("*.gradle.kts", ".gitattributes", ".gitignore")
        trimTrailingWhitespace()
        leadingTabsToSpaces()
        endWithNewline()
    }
    java {
        target("src/**/*.java", "src/**/*.java.peb")
        toggleOffOn()
        importOrder()
        removeUnusedImports()
        cleanthat()
        googleJavaFormat("1.24.0")
                .aosp()
                .formatJavadoc(true)
                .reorderImports(true)
        formatAnnotations()
        trimTrailingWhitespace()
        leadingTabsToSpaces()
        endWithNewline()
        licenseHeader("""/**
 * Copyright (c) 2025 $author
 * This project is Licensed under <a href="$sourceUrl/blob/main/LICENSE">$license</a>
 */""")
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<RemapJarTask> {
    mixinRemap {
        enableBaseMixin()
        disableRefmap()
    }
}

repositories {
    mavenLocal()
    maven("https://maven.neuralnexus.dev/mirror")
}

unimined.minecraft {
    version(minecraftVersion)
    mappings {
        parchment(parchmentMinecraft, parchmentVersion)
        mojmap()
        devFallbackNamespace("official")
    }
    neoForge {
        loader(neoForgeVersion)
    }
    defaultRemapJar = true
}

dependencies {
    compileOnly(project(":common"))
}

tasks.withType<ProcessResources> {
    filesMatching(listOf(
            "META-INF/neoforge.mods.toml"
    )) {
        expand(project.properties)
    }
}

tasks.jar {
    from(
        sourceSets.main.get().output,
        zipTree(project(":common").tasks.getByName<Jar>("shadowJar").archiveFile.get().asFile)
    )
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes(
                mapOf(
                        "Specification-Title" to modName,
                        "Specification-Version" to version,
                        "Specification-Vendor" to "SomeVendor",
                        "Implementation-Version" to version,
                        "Implementation-Vendor" to "SomeVendor",
                        "Implementation-Timestamp" to Instant.now().toString(),
                        //"FMLCorePluginContainsFMLMod" to "true",
                        //"TweakClass" to "org.spongepowered.asm.launch.MixinTweaker",
                        //"MixinConfigs" to "$modId.mixins.vanilla.json,$modId.mixins.forge.json"
                )
        )
    }
    from(listOf("README.md", "LICENSE")) {
        into("META-INF")
    }
}
tasks.getByName("compileJava").dependsOn(":common:shadowJar")
tasks.build.get().dependsOn("spotlessApply")
