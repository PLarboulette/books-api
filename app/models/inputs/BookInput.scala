package models.inputs

import java.time.Instant
import java.util.UUID

import models.{ Format, Status }

case class BookInput(
    title: String,
    idLibrary: UUID, // Link to the id of one of libraries of an user
    author: Option[String], // Link to the id of an author
    collection: String,
    status: Status,
    format: Format,
    boughtAt: Option[Instant],
    readAt: Option[Instant],
    rating: Option[Int]
)

object BookInput {
  implicit val reads = play.api.libs.json.Json.reads[BookInput]
  implicit val writes = play.api.libs.json.Json.writes[BookInput]
}
