package models

import java.time.Instant
import java.util.UUID

import play.api.libs.json.{Reads, Writes}

case class User
(
  idUser: UUID,
  login: String,
  password: String,
  firstName: Option[String],
  lastName: Option[String],
  createdAt: Instant,
  updatedAt: Instant,
  deletedAt: Option[Instant]
)

object User {
  implicit val reads: Reads[User] = play.api.libs.json.Json.reads[User]
  implicit val writes: Writes[User] = play.api.libs.json.Json.writes[User]
}