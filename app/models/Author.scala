package models

import java.util.UUID

import play.api.libs.json.{ Reads, Writes }

case class Author(
    idAuthor: UUID,
    name: String
)

object Author {
  implicit val reads: Reads[Author] = play.api.libs.json.Json.reads[Author]
  implicit val writes: Writes[Author] = play.api.libs.json.Json.writes[Author]
}
