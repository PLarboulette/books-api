package models

import java.time.Instant
import java.util.UUID

import play.api.libs.json.{ Reads, Writes }

case class Book(
    idBook: UUID,
    title: String,
    idLibrary: UUID, // Link to the id of one of libraries of an user
    author: Option[String], // Link to the id of an author
    collection: String,
    status: Status,
    format: Format,
    boughtAt: Option[Instant],
    readAt: Option[Instant],
    rating: Option[Int],
    createdAt: Instant,
    updatedAt: Instant,
    deletedAt: Option[Instant]
)

object Book {
  implicit val reads: Reads[Book] = play.api.libs.json.Json.reads[Book]
  implicit val writes: Writes[Book] = play.api.libs.json.Json.writes[Book]
}
