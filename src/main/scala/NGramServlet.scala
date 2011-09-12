
package ngram4web.server

import ngram4web.shared._
import ngram4web.NGramGenerator

import com.google.gwt.user.server.rpc.RemoteServiceServlet

class NGramServlet extends RemoteServiceServlet with NGramService {

  private val log = java.util.logging.Logger.getLogger(this.getClass().toString())

  override def generateWords(samples: Array[String]) = {
    val s = samples.toList
    log.info("received samples: " + s)

    val gen = new NGramGenerator(samples)
    
    val words = (0 until 10).map(_ => gen.genWord())

    // List("a", "b", "c", "d").toArray
    log.info("result: "+words)
    words.toArray
  }
}
