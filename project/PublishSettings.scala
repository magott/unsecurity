import com.jsuereth.sbtpgp.PgpKeys
import sbt._
import sbt.Keys._
import sbtrelease.ReleasePlugin.autoImport._

object PublishSettings {
  def apply(project: Project): Project = {
    project
      .settings(
        Seq(
          scmInfo := Some(
            ScmInfo(
              new URL("https://github.com/unsecurityio/unsecurity"),
              "scm:git:git@github.com:unsecurityio/unsecurity.git",
              Some("scm:git:git@github.com:unsecurityio/unsecurity.git")
            )),
          developers := List(
            Developer(
              id = "eirikm",
              name = "Eirik Meland",
              email = "eirik.meland@gmail.com",
              url = new URL("http://twitter.com/eirikme")
            ),
            Developer(
              id = "kaarenilsen",
              name = "Kaare Nilsen",
              email = "kaare.nilsen@gmail.com",
              url = new URL("http://twitter.com/kaarenilsen")
            )
          ),
          licenses := Seq("MIT" -> url("https://raw.githubusercontent.com/unsecurityio/unsecurity/master/LICENSE")),
          homepage := Some(url("https://unsecurity.io")),
          pomIncludeRepository := { x =>
            false
          },
          publishTo := {
            if (isSnapshot.value) {
              Some(Opts.resolver.sonatypeSnapshots)
            } else {
              Some(Opts.resolver.sonatypeStaging)
            }
          },
          publishMavenStyle := true,
          packageOptions += {
            val title  = name.value
            val ver    = version.value
            val vendor = organization.value

            Package.ManifestAttributes(
              "Created-By"               -> "Scala Build Tool",
              "Built-By"                 -> System.getProperty("user.name"),
              "Build-Jdk"                -> System.getProperty("java.version"),
              "Specification-Title"      -> title,
              "Specification-Version"    -> ver,
              "Specification-Vendor"     -> vendor,
              "Implementation-Title"     -> title,
              "Implementation-Version"   -> ver,
              "Implementation-Vendor-Id" -> vendor,
              "Implementation-Vendor"    -> vendor
            )
          },
          credentials ++= Seq(
            Credentials(Path.userHome / ".sbt" / "sonatype_credential")
          ),
          startYear := Some(2019),
          publishArtifact in Test := false,
          releaseCrossBuild := false,
          releasePublishArtifactsAction := PgpKeys.publishSigned.value
        )
      )
  }
}
