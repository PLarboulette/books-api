package repositories.helpers

import java.time.Instant
import java.util.UUID
import anorm.{ SqlParser, ~ }
import models.User

trait UserSupport {

  implicit val userRowParser =
    (
      SqlParser.get[UUID]("idUser") ~
        SqlParser.get[String]("login") ~
        SqlParser.get[String]("password") ~
        SqlParser.get[Option[String]]("firstName") ~
        SqlParser.get[Option[String]]("lastName") ~
        SqlParser.get[Instant]("createdAt") ~
        SqlParser.get[Instant]("updatedAt") ~
        SqlParser.get[Option[Instant]]("deletedAt")
    ).map {
      case idUser ~ login ~ password ~ firstName ~ lastName ~ createdAt ~ updatedAt ~ deletedAt =>
        User(idUser, login, password, firstName, lastName, createdAt, updatedAt, deletedAt)
    }
}
