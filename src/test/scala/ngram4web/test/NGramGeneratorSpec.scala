package ngram4web.test

import org.specs2.ScalaCheck
import org.specs2.mutable._
import org.scalacheck._
import ngram4web._

object NGramGeneratorSpec extends Specification with ScalaCheck {

  "The NGramGenerator" should {
    "run at all" in {
      "Hello world" must have size (11)
    }

    "return monotone output for monotone input" in {
      implicit def _c = Arbitrary { Gen.alphaChar }
      implicit def _len = Arbitrary { Gen.choose(1, 100) }
      check { (c: Char, len: Int) =>
        {
          val input = c.toString * len
          val gen = new NGramGenerator(Seq(c.toString()))
          gen.genWord() must beMatching("^" + c + "+$")
          // println("input is " + input + ", word is " + gen.genWord())
          success
        }
      }
    }
  }
}
