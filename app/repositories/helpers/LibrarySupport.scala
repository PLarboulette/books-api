package repositories.helpers

import java.time.Instant
import java.util.UUID

import anorm.{ SqlParser, ~ }
import models.Library

trait LibrarySupport {

  implicit val libraryRowParser =
    (
      SqlParser.get[UUID]("idLibrary") ~
        SqlParser.get[UUID]("idUser") ~
        SqlParser.get[String]("name") ~
        SqlParser.get[Instant]("createdAt") ~
        SqlParser.get[Instant]("updatedAt") ~
        SqlParser.get[Option[Instant]]("deletedAt")
    ).map {
      case idLibrary ~ idUser ~ name ~ createdAt ~ updatedAt ~ deletedAt =>
        Library(idLibrary, idUser, name, createdAt, updatedAt, deletedAt)
    }
}
