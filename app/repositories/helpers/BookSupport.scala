package repositories.helpers

import java.time.Instant
import java.util.UUID

import anorm.{ SqlParser, ~ }
import models.{ Book, Format, Status }

trait BookSupport extends StatusSupport with FormatSupport {

  implicit val bookRowParser =
    (
      SqlParser.get[UUID]("idBook") ~
        SqlParser.get[String]("title") ~
        SqlParser.get[UUID]("idLibrary") ~
        SqlParser.get[Option[String]]("author") ~
        SqlParser.get[String]("collection") ~
        SqlParser.get[Status]("status") ~
        SqlParser.get[Format]("format") ~
        SqlParser.get[Option[Instant]]("boughtAt") ~
        SqlParser.get[Option[Instant]]("readAt") ~
        SqlParser.get[Option[Int]]("rating") ~
        SqlParser.get[Instant]("createdAt") ~
        SqlParser.get[Instant]("updatedAt") ~
        SqlParser.get[Option[Instant]]("deletedAt")
    ).map {
      case idBook ~ title ~ idLibrary ~ author ~ collection ~ status ~ format ~ boughtAt ~ readAt ~ rating ~ createdAt ~ updatedAt ~ deletedAt =>
        Book(
          idBook,
          title,
          idLibrary,
          author,
          collection,
          status,
          format,
          boughtAt,
          readAt,
          rating,
          createdAt,
          updatedAt,
          deletedAt
        )
    }
}
