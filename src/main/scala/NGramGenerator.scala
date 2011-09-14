package ngram4web

import scala.collection.mutable

class NGramGenerator(samples: Seq[String]) {

  type ProbTable = Map[String, Seq[Char]]

  val maxNGramLength = 3
  val random = new scala.util.Random()

  val dict = buildDictionary(samples)

  def nextChar(s: String): Option[Char] = {
    val maxPrefixLen = maxNGramLength - 1
    //    val maxPrefixLen = random.nextInt(maxNGramLength - 1) + 1
    (maxPrefixLen to 1 by -1).flatMap(n => dict.get(s.takeRight(n))).headOption match {
      case None => return None
      case Some(word) => {
        var counter = random.nextInt(word._1)
        Some(word._2.find(e => { counter -= e._2; counter < 0 }).getOrElse(return None)._1)
      }
    }
  }

  def genWord(): String = {
    def impl(s: String): String = {
      nextChar(s) match {
        case None => s
        case Some('$') => s
        case Some(x) => impl(s + x)
      }
    }
    impl("")
  }

  def buildDictionary(sourceWords: Seq[String]) = {
    val nGrams = (2 to maxNGramLength).flatMap(buildNGrams(sourceWords, _)) ++ sourceWords.flatMap(_.firstOption).map(_.toString)
    //println("n-grams: " + nGrams)
    buildDictFromNGrams(nGrams)
  }

  private def buildNGrams(words: Seq[String], ngramSize: Int): Seq[String] = {
    words.flatMap(w => (w + "$").sliding(ngramSize))
  }

  def buildDictFromNGrams(ngrams: Seq[String]) = {
    val tempDict: Map[String, Map[Char, Int]] = ngrams.foldLeft(Map.empty[String, Map[Char, Int]])({ (map, ngram) =>
      {
        val prefix = ngram.dropRight(1)
        val c = ngram.last
        map.updated(prefix, {
          val e = map.getOrElse(prefix, Map.empty)
          e.updated(c, e.getOrElse(c, 0) + 1)
        })
      }
    })
    tempDict.map(w => (w._1, (w._2.toSeq.map(e => e._2).sum, w._2.toSeq.sortBy(e => e._2))))
  }
}
