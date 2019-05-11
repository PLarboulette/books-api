package models.inputs

import java.util.UUID

case class LibraryInput(
    idUser: UUID,
    name: String
)

object LibraryInput {
  implicit val reads = play.api.libs.json.Json.reads[LibraryInput]
  implicit val writes = play.api.libs.json.Json.writes[LibraryInput]
}
