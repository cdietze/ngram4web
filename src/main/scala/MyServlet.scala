
package ngram4web.server

//import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import ngram4web.shared._

import com.google.gwt.user.server.rpc.RemoteServiceServlet

class MyServlet extends RemoteServiceServlet with NGramService {

  
  override def test(text: String) = {
    "hallo, "+text
  }
}