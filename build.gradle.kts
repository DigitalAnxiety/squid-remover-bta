plugins { alias(libs.plugins.loom); java }
val lwjglNatives = "natives-linux"
val modVersion = "${providers.gradleProperty("mod_version").get()}+${libs.versions.bta.get()}"
val modGroup = providers.gradleProperty("mod_group")
val modName = providers.gradleProperty("mod_name")
val javaVersion = libs.versions.java.map { it.toInt() }
base.archivesName = modName
group = modGroup.get()
version = modVersion
loom {
 val btaChannel = libs.versions.btaChannel.get()
 val btaVersion = (if (btaChannel == "nightly") "" else "v") + libs.versions.bta.get()
 customMinecraftMetadata.set("https://downloads.betterthanadventure.net/bta-client/${btaChannel}/${btaVersion}/manifest.json")
}
repositories {
 mavenCentral()
 ivy("https://piston-data.mojang.com") { patternLayout { artifact("v1/[organisation]/[revision]/[module].jar") }; metadataSources { artifact() } }
 maven("https://maven.fabricmc.net/")
 maven("https://maven.danygames2014.net/signalum")
 maven("https://maven.thesignalumproject.net/infrastructure")
 maven("https://maven.thesignalumproject.net/releases")
 maven("https://maven.thesignalumproject.net/nightly")
}
dependencies {
 minecraft("::${libs.versions.bta.get()}")
 implementation(libs.loader)
 implementation(libs.halplibe)
 compileOnly(libs.bundles.btaLwjgl)
 compileOnly(libs.joml); compileOnly(libs.joml.primitives); compileOnly(libs.slf4jApi)
 localRuntime(libs.modMenu); runtimeClasspath(libs.clientJar)
 val lwjglVer=libs.versions.lwjgl.get()
 localRuntime(platform("org.lwjgl:lwjgl-bom:${lwjglVer}"))
 localRuntime("org.lwjgl:lwjgl::${lwjglNatives}")
 localRuntime("org.lwjgl:lwjgl-glfw::${lwjglNatives}")
 localRuntime("org.lwjgl:lwjgl-openal::${lwjglNatives}")
 localRuntime("org.lwjgl:lwjgl-opengl::${lwjglNatives}")
 localRuntime("org.lwjgl:lwjgl-stb::${lwjglNatives}")
}
java { toolchain { languageVersion=javaVersion.map { JavaLanguageVersion.of(it) }; vendor=JvmVendorSpec.ADOPTIUM }; sourceCompatibility=JavaVersion.toVersion(javaVersion.get()); targetCompatibility=JavaVersion.toVersion(javaVersion.get()); withSourcesJar() }
tasks.withType<JavaCompile>().configureEach { options.encoding="UTF-8"; if(javaVersion.get()>8) options.release=javaVersion }
tasks.processResources {
 val resourceMap=mapOf("version" to modVersion,"fabricloader" to libs.versions.loader.get(),"halplibe" to libs.versions.halplibe.get(),"java" to libs.versions.java.get(),"modmenu" to libs.versions.modMenu.get())
 inputs.properties(resourceMap); duplicatesStrategy=DuplicatesStrategy.INCLUDE
 filesMatching(listOf("fabric.mod.json","*.mixins.json")) { expand(resourceMap) }
}


// Prevent old Minecraft LWJGL 2 dependencies from being pulled into BTA 8.0.1.
configurations.configureEach {
    exclude(group = "org.lwjgl.lwjgl")
    exclude(group = "net.java.jutils")
    exclude(group = "net.java.jinput")
    exclude(group = "net.sf.jopt-simple")
    exclude(group = "net.minecraft", module = "launchwrapper")
}
