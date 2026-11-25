package $package$

import io.github.wickedsik.wsforge.api.plugin.*
import io.github.wickedsik.wsforge.tools.Tool
import $package$.tools.*
import zio.*

/**
 * $description$
 *
 * The host namespaces every tool as `plugin.$plugin;format="norm"$.<tool>`. It instantiates this class by
 * its full name (the `entrypoint` in the host's config), so it needs a public no-argument constructor.
 */
class $pluginClass$ extends ToolPlugin:

  def metadata: PluginMetadata =
    PluginMetadata(
      name = "$plugin;format="norm"$",
      version = "0.1.0",
      apiVersion = 3,
      requestedCapabilities = Set.empty
    )

  def tools(ctx: PluginContext): RIO[Any, List[Tool]] =
    ZIO.succeed(List(EchoTool()))
