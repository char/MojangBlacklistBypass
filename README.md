# Mojang Server Blacklist
Mojang Server Blacklist allows you to bypass the server blacklist in order to join certain EULA-infringing servers.  
To do this, we use Java Agents in order to retransform Mojang's custom Netty bootstrapper.

## Installation &amp; usage:
In order to use this you will want to head on over to the [releases](https://github.com/supercheese200/MojangBlacklistBypass/releases) section of this repository, and download the latest release.  
Then, inside your Minecraft launcher, edit your profile and edit the JVM arguments: prepending this:  
`-javaagent:/path/to/MojangServerBlacklist.jar`

**Make sure you change the path to wherever you are storing the jar!** I recommend keeping it in your .minecraft folder.

### Please note:
If your path has spaces in it, you will need to wrap it in double-quotes, like so:  
`-javaagent:"/path with spaces/to/MojangServerBlacklist.jar"`
