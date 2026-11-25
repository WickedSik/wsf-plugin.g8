package $package$

import io.circe.Json
import io.github.wickedsik.wsforge.api.plugin.*
import zio.*
import zio.test.*

/** Checks the rules the host enforces when it loads a plugin. */
object $pluginClass$Spec extends ZIOSpecDefault:

  private val plugin = $pluginClass$()

  /** The context of a plugin that was granted no capabilities. */
  private val noGrants = PluginContext(filesystem = None, http = None, session = None, config = Json.obj())

  private def isValidName(name: String): Boolean = name.matches("[a-z0-9._-]+")

  def spec = suite("$pluginClass$Spec")(
    test("has a name the host accepts") {
      assertTrue(isValidName(plugin.metadata.name))
    },
    test("targets plugin API version 3") {
      assertTrue(plugin.metadata.apiVersion == 3)
    },
    test("can be instantiated through a public no-argument constructor") {
      for instance <- ZIO.attempt(classOf[$pluginClass$].getConstructor().newInstance())
      yield assertTrue(instance.metadata == plugin.metadata)
    },
    test("contributes its tools without any granted capability") {
      for tools <- plugin.tools(noGrants)
      yield assertTrue(tools.map(_.name) == List("echo"))
    },
    test("gives every tool a valid, unique name that matches its definition") {
      for
        tools <- plugin.tools(noGrants)
        names = tools.map(_.name)
      yield assertTrue(
        names.forall(isValidName),
        names.distinct == names,
        tools.map(_.definition.function.name) == names
      )
    }
  )
