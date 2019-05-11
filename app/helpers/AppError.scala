package helpers

import play.api.libs.json.{ JsValue, Json, Writes }

sealed trait AppError extends Exception {
  val code: Int
  val title: String
  val message: String
  val cause: Option[Throwable]

  // We need this to be conpatible with java.lang.Throwable
  override def getCause = cause.getOrElse(null)
}

object AppError {

  object Codes {
    val conflictReplaceNotificationHasFallbacks: Int = 4091
  }

  case class NotFound(
      message: String = "",
      code: Int = 404,
      title: String = "NotFound",
      cause: Option[Throwable] = None
  ) extends AppError
  case class Forbidden(
      message: String = "",
      code: Int = 403,
      title: String = "Forbidden",
      cause: Option[Throwable] = None
  ) extends AppError
  case class InternalError(
      message: String = "",
      code: Int = 500,
      title: String = "InternalError",
      cause: Option[Throwable] = None
  ) extends AppError
  case class BadRequest(
      message: String = "",
      code: Int = 400,
      title: String = "BadRequest",
      cause: Option[Throwable] = None
  ) extends AppError
  case class Conflict(
      message: String = "",
      code: Int = 409,
      title: String = "Conflict",
      cause: Option[Throwable] = None
  ) extends AppError

  implicit val writes: Writes[AppError] = new Writes[AppError] {
    override def writes(o: AppError): JsValue = {
      o match {
        case error: InternalError =>
          Json.obj(
            "code" -> Json.parse(error.code.toString),
            "title" -> Json.parse(error.title)
          )
        case error: BadRequest =>
          Json.obj(
            "code" -> Json.parse(error.code.toString),
            "title" -> Json.parse(error.title)
          )
        case error =>
          Json.obj(
            "code" -> Json.parse(error.code.toString),
            "title" -> Json.parse(error.title),
            "details" -> Json.parse(error.message)
          )
      }
    }
  }
}
