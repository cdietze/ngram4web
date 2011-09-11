resolvers += "GWT plugin repo" at "http://thunderklaus.github.com/maven"

// addSbtPlugin( "com.github.siasia" % "xsbt-web-plugin" % "0.1.1")

addSbtPlugin("net.thunderklaus" % "sbt-gwt-plugin" % "1.1-SNAPSHOT")

//libraryDependencies += "net.thunderklaus" %% "sbt-gwt-plugin" % "1.1-SNAPSHOT"

//libraryDependencies <+= (sbtVersion) { sv => "com.eed3si9n" %% "sbt-appengine" % ("sbt" + sv + "_0.2") }

