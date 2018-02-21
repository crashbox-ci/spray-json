import spray.json._

object Tests extends DefaultJsonProtocol with Util {

  case class A()
  case class B(x: Int, b: String, mp: Map[String, Int])
  case class C(b: B)
  case object D
  case class E(d: D.type)
  case class F(x: Int)

  {
    println("No-parameter product")
    checkCoherence(A(), "{}")
  }

  {
    println("Simple parameter product")
    checkCoherence(
      B(42, "Hello World", Map("a" -> 1, "b" -> -1024)),
      """{ "x": 42, "b": "Hello World", "mp": { "a": 1, "b": -1024 } }"""
    )
  }

  {
    println("Nested parameter product")
    checkCoherence(
      C(B(42, "Hello World", Map("a" -> 1, "b" -> -1024))),
      """{"b" :{ "x": 42, "b": "Hello World", "mp": { "a": 1, "b": -1024 } } }"""
    )
  }

  {
    println("Case object")
    checkCoherence(
      D,
      """"D""""
    )
  }

  {
    println("Case object as parameter")
    checkCoherence(
      E(D),
      """{"d":"D"}"""
    )
  }

  {
    // custom format for F, that inverts the value of parameter x
    implicit val fFormat: JsonFormat[F] = new JsonFormat[F] {
      override def write(f: F): JsValue = JsObject("x" -> JsNumber(-f.x))
      override def read(js: JsValue): F =
        F(-js.asJsObject.fields("x").convertTo[Int])
    }

    println("Overriding with a custom format")
    checkCoherence(
      F(2),
      """{"x":-2}"""
    )
  }

  sealed trait Expr
  case class Zero() extends Expr
  case class Value(x: Int) extends Expr
  case class Plus(lhs: Expr, rhs: Expr) extends Expr
  case object One extends Expr

  {
    println("No-parameter case class child")
    checkCoherence[Expr](
      Zero(),
      """{"type":"Zero"}"""
    )
  }

  {
    println("Simple parameter case class child")
    checkCoherence[Expr](
      Value(42),
      """{"type":"Value","x":42}"""
    )
  }

  {
    println("Nested parameter case class child")
    checkCoherence[Expr](
      Plus(Value(42), One),
      """{"type":"Plus","lhs":{"type":"Value","x":42},"rhs":"One"}"""
    )
  }

  {
    println("Case object child")
    checkCoherence[Expr](
      One,
      """"One""""
    )
  }

  @gadt("kind")
  sealed abstract class Keyword(`type`: String)
  case class If(`type`: String) extends Keyword(`type`)

  {
    println("GADT with type field alias")
    checkCoherence[Keyword](
      If("class"),
      """{"kind":"If","type":"class"}"""
    )
  }

  sealed trait Enum
  case object A extends Enum
  case object B extends Enum

  {
    println("Enum")
    checkCoherence[List[Enum]](
      A :: B :: Nil,
      """["A", "B"]"""
    )
  }


  {
    println("Serializing as sealed trait an deserializing as child")
    val expr: Expr = Plus(Value(42), Plus(Zero(), One))
    assert(expr.toJson.convertTo[Plus] == expr)
  }

}