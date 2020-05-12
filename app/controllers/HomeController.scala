package controllers
import akka.stream.scaladsl.Source
import javax.inject._
import play.api.mvc._

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  /*implicit def wString(implicit codec: Codec): Writeable[String] =
    Writeable[String](str => codec.encode(str))

   */
  val logger=play.api.Logger(getClass)
  implicit  val ec:ExecutionContext=cc.executionContext
  def index: Action[AnyContent] = Action {
    Ok(views.html.index("Your new application is ready."))
  }
// class Writeable[-A](val transform: A => ByteString, val contentType: Option[String])
  // Writeable Transform a value of type A to a Byte Array.
  // simply import the default implementation
  def streamList: Action[AnyContent] =Action{ request=>
    import  play.api.http.Writeable.wString
    val source=Source.apply(List("Peter","Kelly","Pius","Herman","Paul"))
    Ok.chunked(source)
  }
  def streamFile: Action[AnyContent] =Action{ request=>
    val file= new java.io.File("location of file")
    logger.info("sending file")
    Ok.sendFile(file)
  }

}

