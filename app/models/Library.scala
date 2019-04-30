package models

import java.time.Instant
import java.util.UUID

import play.api.libs.json.{Reads, Writes}

case class Library
(
  idLibrary: UUID,
  idUser: UUID, // a library is own by an user
  name: String,
  createdAt: Instant,
  updatedAt: Instant,
  deletedAt: Option[Instant]
)

object Library {
  implicit val reads: Reads[Library] = play.api.libs.json.Json.reads[Library]
  implicit val writes: Writes[Library] = play.api.libs.json.Json.writes[Library]
}