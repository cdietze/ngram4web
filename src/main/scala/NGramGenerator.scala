package ngram4web

import scala.collection.mutable

class NGramGenerator(samples: Seq[String]) {

  type ProbTable = Map[String, Seq[Char]]

  val probTableSize = 100
  val random = new scala.util.Random()

  val dict = buildDictionary(samples)
  
  def nextChar(s: String): Option[Char] = {
    val word = dict.getOrElse(s.takeRight(1), return None)
    var counter = random.nextInt(word._1)
    Some(word._2.find(e => { counter -= e._2; counter < 0 }).getOrElse(return None)._1)
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

  def buildDictionary(words: Seq[String]) = {
    val nGrams = buildNGrams(words, 2)
    //println("n-grams: " + nGrams)
    buildDictFromNGrams(nGrams)
  }

  private def buildNGrams(words: Seq[String], ngramSize: Int): Seq[String] = {
    for (w <- words; i <- -1 until w.length) yield i match {
      case -1 => w.last.toString + "$"
      case 0 => w.first.toString
      case _ => w(i - 1).toString + w(i)
    }
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
