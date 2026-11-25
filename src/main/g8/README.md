# $name$

$description$

An external plugin for ws-forge. See `docs/plugins.md` in ws-forge for the full plugin contract.

## Tools

The host namespaces every tool as `plugin.$plugin;format="norm"$.<tool>`.

| Tool | Does | Capability |
|---|---|---|
| `echo` | Return the `message` argument | none |

## Build

`ws-forge-api` comes from the local Ivy repository. Publish it once, from the ws-forge checkout at the tag pinned in `build.sbt`:

```bash
git checkout v0.4.0 && sbt api/publishLocal
```

Then:

```bash
sbt test
sbt package   # target/scala-3.9.0/$name;format="norm"$_3-0.1.0-SNAPSHOT.jar
```

Keep `ws-forge-api`, `zio` and `circe` as `Provided`. The host supplies them, and bundling them breaks class loading.

## Enable it in the host

1. Copy the JAR into the host's plugin directory. Remove older builds of it first: the host refuses two JARs with the same entrypoint.
2. Configure it in the host's `application.conf`:
   ```hocon
   plugins {
     enabled = true
     directory = "plugins"
     loaded = [
       {
         entrypoint = "$package$.$pluginClass$"
         granted-capabilities = []
       }
     ]
   }
   ```
3. Start the host. It logs the JAR's `sha256`. Add it to the entry to pin the JAR.

## Use a capability

Add it to `requestedCapabilities` in the plugin class and to `granted-capabilities` in the host config, then pass the matching service (such as `ctx.filesystem`) to your tool. `ToolArgs.requireFs` fails clearly when the grant is missing. `wsf-plugin-character` shows this pattern, with an in-memory `PluginFilesystem` for tests.
