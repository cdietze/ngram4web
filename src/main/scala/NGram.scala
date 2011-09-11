package ngram4web

import scala.collection.mutable

class NGramGenerator(samples: Seq[String]) {

  type ProbTable = Map[String, Seq[Char]]

  val probTableSize = 100
  val random = new scala.util.Random()

  println("Welcome to the n-gram word generator!")
  /*
  val female1890sNames = List("Anna", "Frieda", "Frida", "Martha", "Marta", "Erna", "Emma", "Marie", "Gertrud",
      "Margarethe", "Margarete", "Maria", "Elisabeth", "Berta", "Bertha", "Elsa", "Helene", "Luise", "Louise", "Johanna", "Hedwig",
      "Klara", "Clara", "Minna", "Else", "Paula", "Ella", "Ida", "Auguste", "Olga", "Wilhelmine", "Dora", "Alma", "Käthe", "Käte", "Herta",
      "Hertha", "Elise", "Margaretha", "Margareta", "Meta", "Metha", "Charlotte", "Katharina", "Catharina", "Katarina", "Agnes",
      "Emilie", "Dorothea", "Anni", "Anny", "Emmi", "Emmy", "Alice", "Elli", "Elly", "Gretchen", "Matilde", "Mathilde", "Caroline",
      "Karoline", "Henriette", "Henny", "Henni", "Lina", "Elfriede", "Sophie", "Sofie", "Alwine", "Grete", "Grethe", "Amanda", "Rosa",
      "Franziska", "Hermine", "Christine", "Magdalene", "Lucy", "Lucie", "Antonie", "Johanne", "Hildegard", "Lilly", "Lilli", "Lily",
      "Irma", "Adele", "Anita", "Anne", "Pauline", "Magdalena", "Marianne", "Friederike") */
  //val female1890sNames = List("AäBüCßD")
val dict = buildDictionary(samples)

  /*
  def main(args: Array[String]) {
    //println("dict: " + dict)
    val firstLetter = nextChar("")
    val words: Seq[String] = (0 until 20).map(_ => genWord)
    println("Names: " + words.mkString(", "))
  } */

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
