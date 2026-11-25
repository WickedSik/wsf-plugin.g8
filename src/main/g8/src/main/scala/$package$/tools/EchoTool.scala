package $package$.tools

import io.circe.Json
import io.github.wickedsik.wsforge.tools.*
import zio.*

/** Sample tool: returns its `message` argument. Replace it with your own. */
class EchoTool extends Tool:

  def name: String = "echo"

  def definition: ToolDefinition = ToolDefinition(
    function = ToolSchema(
      name = name,
      description = "Echo back the provided 'message' string.",
      parameters = Json.obj(
        "type" -> Json.fromString("object"),
        "properties" -> Json.obj(
          "message" -> Json.obj(
            "type"        -> Json.fromString("string"),
            "description" -> Json.fromString("The message to echo back.")
          )
        ),
        "required" -> Json.arr(Json.fromString("message"))
      )
    )
  )

  def execute(argumentsJson: Json): RIO[Any, ToolResult] =
    ToolArgs.string(argumentsJson, "message", name).map(message => ToolResult(content = message))
