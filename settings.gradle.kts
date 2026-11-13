val modName: Provider<String> = providers.gradleProperty("mod_name")
rootProject.name = modName.get()
pluginManagement {
 repositories {
  mavenCentral()
  gradlePluginPortal()
  maven("https://maven.fabricmc.net")
  maven("https://maven.thesignalumproject.net/infrastructure")
 }
 val foojayResolverVersion = providers.gradleProperty("foojay_resolver_version")
 plugins { id("org.gradle.toolchains.foojay-resolver-convention").version(foojayResolverVersion.get()) }
}
plugins { id("org.gradle.toolchains.foojay-resolver-convention") }
