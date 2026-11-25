package $package$.tools

import io.circe.Json
import io.github.wickedsik.wsforge.tools.ToolError
import zio.*
import zio.test.*
import zio.test.Assertion.*

object EchoToolSpec extends ZIOSpecDefault:

  private val tool = EchoTool()

  def spec = suite("EchoToolSpec")(
    test("returns the message") {
      for result <- tool.execute(Json.obj("message" -> Json.fromString("hello")))
      yield assertTrue(result.content == "hello")
    },
    test("fails with InvalidParams when 'message' is missing") {
      for err <- tool.execute(Json.obj()).flip
      yield assert(err)(isSubtype[ToolError.InvalidParams](anything))
    },
    test("fails with InvalidParams when 'message' is not a string") {
      for err <- tool.execute(Json.obj("message" -> Json.fromInt(1))).flip
      yield assert(err)(isSubtype[ToolError.InvalidParams](anything))
    },
    test("declares 'message' as a required string parameter") {
      val parameters = tool.definition.function.parameters.hcursor
      assertTrue(
        parameters.downField("properties").downField("message").get[String]("type") == Right("string"),
        parameters.get[List[String]]("required") == Right(List("message"))
      )
    }
  )
