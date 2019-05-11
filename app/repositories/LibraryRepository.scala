package repositories
import java.util.UUID

import javax.inject.{ Inject, Singleton }
import play.api.db.Database
import repositories.helpers.LibrarySupport
import anorm._
import models.Library
import models.inputs.LibraryInput

import scala.util.Try

@Singleton
final class LibraryRepository @Inject()(db: Database) extends LibrarySupport {

  def find(idUser: Option[UUID]): List[Library] = {
    db.withConnection { implicit connection =>
      (idUser match {
        case Some(id) => SQL""" SELECT * FROM "Library" WHERE "idUser" = $id::UUID """
        case None => SQL""" SELECT * FROM "Library" """
      }).as(libraryRowParser.*)
    }
  }

  def save(libraryInput: LibraryInput): Try[UUID] = {
    db.withConnection { implicit connection =>
      SQL""" INSERT INTO "Library"("idUser", "name") VALUES (${libraryInput.idUser}::UUID, ${libraryInput.name})
             RETURNING "idLibrary"
         """
        .asTry(SqlParser.scalar[UUID].single)
    }
  }
}
