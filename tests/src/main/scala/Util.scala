import spray.json._

trait Util {

  def checkCoherence[A: JsonFormat](a: A, expectedJson: String) = {
    println("  - it should serialize to the expected JSON value")
    val expected: JsValue = expectedJson.parseJson
    assert(a.toJson == expected)

    println("  - it should serialize then deserialize back to itself")
    val reread = a.toJson.convertTo[A]
    assert(reread == a)
  }

}
