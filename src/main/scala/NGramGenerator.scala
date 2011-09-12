package ngram4web

import scala.collection.mutable

class NGramGenerator(samples: Seq[String]) {

  type ProbTable = Map[String, Seq[Char]]

  val probTableSize = 100
  val random = new scala.util.Random()

  val dict = buildDictionary(samples)

  def nextChar(s: String) = dict(s)(random.nextInt(probTableSize))

  def genWord(): String = {
    def impl(sb: StringBuilder, c: Char) {
      c match {
        case '.' => null
        case _ => {
          sb.append(c)
          impl(sb, nextChar(sb.takeRight(1).toString))
        }
      }
    }
    val sb = new StringBuilder
    impl(sb, nextChar(""))
    sb.toString
  }

  def buildDictionary(words: Seq[String]) = {
    val nGrams = buildNGrams(words, 2)
    //println("n-grams: " + nGrams)
    buildProbTableFromNGrams(nGrams)
  }

  private def buildNGrams(words: Seq[String], ngramSize: Int): Seq[String] = {
    for (w <- words; i <- -1 until w.length) yield i match {
      case -1 => w.last.toString + "."
      case 0 => w.first.toString
      case _ => w(i - 1).toString + w(i)
    }
  }

  private def buildProbTableFromNGrams(ngrams: Seq[String]): ProbTable = {
    val map = new mutable.HashMap[String, mutable.Map[Char, Int]]
    ngrams.foreach(ngram => {
      val prefix = ngram.dropRight(1)
      val c = ngram.last
      val probTable = map.getOrElse(prefix, new mutable.HashMap())
      probTable.put(c, probTable.getOrElse(c, 0) + 1)
      map.update(prefix, probTable)
    })
    map.toMap.mapValues(v => buildProbTableEntry(v))
  }

  private def buildProbTableEntry(m: collection.Map[Char, Int]): Seq[Char] = {
    val sum = m.values.sum
    val iter = m.iterator
    var e = iter.next
    var counter = e._2
    for (i <- 0 until probTableSize) yield {
      while (counter * probTableSize <= i * sum) {
        e = iter.next
        counter += e._2
      }
      e._1
    }
  }
}
