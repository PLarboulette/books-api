package models

import java.time.Instant
import java.util.UUID

import play.api.libs.json.{ Reads, Writes }

case class Library(
    idLibrary: UUID,
    idUser: UUID,
    name: String,
    createdAt: Instant,
    updatedAt: Instant,
    deletedAt: Option[Instant]
)

object Library {
  implicit val reads: Reads[Library] = play.api.libs.json.Json.reads[Library]
  implicit val writes: Writes[Library] = play.api.libs.json.Json.writes[Library]
}

case class LibraryEnriched(
    idLibrary: UUID,
    name: String,
    books: List[Book], // a library is own by an user
    createdAt: Instant,
    updatedAt: Instant,
    deletedAt: Option[Instant]
)

object LibraryEnriched {
  implicit val reads: Reads[LibraryEnriched] = play.api.libs.json.Json.reads[LibraryEnriched]
  implicit val writes: Writes[LibraryEnriched] = play.api.libs.json.Json.writes[LibraryEnriched]
}
