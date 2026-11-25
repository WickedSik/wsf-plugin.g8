# wsf-plugin.g8

A [Giter8](https://www.foundweekends.org/giter8/) template for ws-forge plugins.

## Use

```bash
sbt new WickedSik/wsf-plugin.g8 --plugin=notes
# or from a local checkout
sbt new file:///path/to/wsf-plugin.g8 --plugin=notes
```

A local path must end in `.g8`.

This creates `wsf-plugin-notes/` with a plugin class, one `echo` tool, tests and a thin-JAR build.

| Field | Default | Used for |
|---|---|---|
| `plugin` | `example` | Tool namespace (`plugin.<plugin>.`) and the defaults below |
| `name` | `wsf-plugin-<plugin>` | Project, directory and JAR name |
| `pluginClass` | `<Plugin>Plugin` | Plugin class, the `entrypoint` in the host config |
| `organization` | `io.github.wickedsik` | sbt organization |
| `package` | `<organization>.wsforge.<plugin>` | Scala package |
| `description` | `A ws-forge plugin` | README and build description |

`plugin` must start with a letter. Use letters, digits, spaces and hyphens. Do not use `api` or `tools`: those packages belong to ws-forge.

Generated projects resolve `ws-forge-api` from the local Ivy repository. Publish it first, from the tag the template pins:

```bash
cd ../ws-forge && git checkout v0.4.0 && sbt api/publishLocal
```

## Maintain

When ws-forge releases a new version, update these together:

- `src/main/g8/build.sbt`: the `ws-forge-api` pin, Scala, ZIO and circe versions
- `src/main/g8/src/main/scala/$package$/$pluginClass$.scala` and its spec: `apiVersion`
- `src/main/g8/README.md`: the host tag
- `src/test/g8/test`: the Scala version in the JAR path

Then run `sbt g8Test`, which generates the default project, tests it and packages it.
