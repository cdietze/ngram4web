resolvers += "GWT plugin repo" at "http://thunderklaus.github.com/maven"

resolvers += "Web plugin repo" at "http://siasia.github.com/maven2"

addSbtPlugin( "com.github.siasia" % "xsbt-web-plugin" % "0.1.2")

addSbtPlugin("net.thunderklaus" % "sbt-gwt-plugin" % "1.1-SNAPSHOT")
