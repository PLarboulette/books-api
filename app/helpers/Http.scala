package helpers

import cats.effect.IO
import play.api.libs.json.{ Json, Writes }
import play.api.mvc.Result
import play.api.mvc.Results.{ NotFound, Ok }

import scala.concurrent.Future
import scala.util.Try

trait Http {

  def ioToFuture[A](io: IO[A])(implicit writes: Writes[A]): Future[Result] = {
    io.attempt.map {
      case Right(value) => Ok(Json.toJson(value))
      case Left(e) =>
        e match {
          case notFound: AppError.NotFound =>
            NotFound(notFound.message)
        }
    }.unsafeToFuture()
  }

}
