import org.eclipse.jgit.internal.storage.file.FileRepository
import org.eclipse.jgit.api.Git

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath("org.eclipse.jgit:org.eclipse.jgit:5.10.0.202012080955-r")
        classpath(kotlin("gradle-plugin", version = "1.4.31"))
    }
}

val repo = FileRepository(rootProject.file(".git"))
val refId = repo.refDatabase.exactRef("refs/remotes/origin/master").objectId!!
val commitCount = Git(repo).log().add(refId).call().count()

val versionName: String by project

allprojects {
    extra["versionCode"] = commitCount + 4200
    extra["versionName"] = versionName
    extra["zipPathMagiskReleasePath"] = project(":core").projectDir.path + "/build/tmp/release/magisk/"
    extra["sourceCompatibility"] = JavaVersion.VERSION_1_8
    extra["targetCompatibility"] = JavaVersion.VERSION_1_8
    extra["versionCode"] = commitCount + 4200

    repositories {
        google()
        jcenter()
        maven(url="https://jitpack.io")
        maven(url="https://dl.bintray.com/rikkaw/Libraries")
    }
}

tasks.register("Delete", Delete::class) {
    delete(rootProject.buildDir)
}
