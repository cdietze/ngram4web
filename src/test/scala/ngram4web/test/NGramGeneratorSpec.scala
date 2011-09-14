package ngram4web.test

import org.specs2.ScalaCheck
import org.specs2.mutable._
import org.scalacheck._
import ngram4web._

class NGramGeneratorSpec extends SpecificationWithJUnit with ScalaCheck {

  "The NGramGenerator" should {
    "run at all" in {
      "Hello world" must have size (11)
    }

    "contain the starting element" in {
      val gen = new NGramGenerator(Seq("Hello"))
      gen.dict must be not empty
      gen.dict.get("") must be not empty
      gen.nextChar("") must be equalTo (Some('H'))
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

    "run samples" in {
      val gen = new NGramGenerator(ngram4web.client.Constants.samples.split(",").map(_.trim))
      // println("Dict: " + gen.dict)
      println("Samples: " + (0 until 50).map(_ => gen.genWord()))
      success
    }
  }
}
