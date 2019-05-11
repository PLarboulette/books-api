package repositories

import java.util.UUID
import anorm._
import javax.inject.{ Inject, Singleton }
import models.{ Book, Status }
import models.inputs.BookInput
import play.api.db.Database
import repositories.helpers.BookSupport

import scala.util.Try

@Singleton
final class BookRepository @Inject()(db: Database) extends BookSupport {

  def find(idLibrary: Option[UUID]): List[Book] = {

    db.withConnection { implicit connection =>
      idLibrary match {
        case Some(id) =>
          SQL"""SELECT * FROM "Book" WHERE "idLibrary" = $id::UUID """
            .as(bookRowParser.*)
        case None =>
          SQL"""SELECT * FROM "Book" """.as(bookRowParser.*)
      }
    }
  }

  def findById(idBook: UUID): Option[Book] = {
    db.withConnection { implicit connection =>
      SQL"""SELECT * FROM "Book WHERE "idBook" = $idBook::UUID """"
        .as(bookRowParser.singleOpt)
    }
  }

  def findByIdLibrary(idLibrary: UUID): List[Book] = {
    db.withConnection { implicit connection =>
      SQL"""SELECT * FROM "Book WHERE "idLibrary" = $idLibrary::UUID """"
        .as(bookRowParser.*)
    }
  }

  def save(bookInput: BookInput): Try[UUID] = {
    import Status._

    db.withConnection { implicit connection =>
      bookInput match {

        case BookInput(title, idLibrary, author, collection, TO_BUY, format, _, _, _) =>
          SQL"""
            INSERT INTO "Book" ("title", "idLibrary", "author", "collection", "status", "format")
            VALUES ($title, $idLibrary, $author, $collection, ${TO_BUY.entryName}, ${format.entryName})
        """.asTry(SqlParser.scalar[UUID].single)

        case BookInput(title, idLibrary, author, collection, TO_READ, format, Some(boughtAt), None, None) =>
          SQL"""
            INSERT INTO "Book" ("title", "idLibrary", "author", "collection", "status", "format", "boughtAt")
            VALUES ($title, $idLibrary, $author, $collection, ${TO_READ.entryName}, ${format.entryName}, $boughtAt)
        """.asTry(SqlParser.scalar[UUID].single)

        case BookInput(title, idLibrary, author, collection, IN_PROGRESS, format, Some(boughtAt), None, None) =>
          SQL"""
            INSERT INTO "Book" ("title", "idLibrary", "author", "collection", "status", "format", "boughtAt")
            VALUES ($title, $idLibrary, $author, $collection, ${IN_PROGRESS.entryName}, ${format.entryName}, $boughtAt)
        """.asTry(SqlParser.scalar[UUID].single)

        case BookInput(
            title,
            idLibrary,
            author,
            collection,
            READ,
            format,
            Some(boughtAt),
            Some(readAt),
            Some(rating)
            ) =>
          SQL"""
            INSERT INTO "Book" ("title", "idLibrary", "author", "collection", "status", "format", "boughtAt", "readAt", "rating)
            VALUES ($title, $idLibrary, $author, $collection, ${READ.entryName}, ${format.entryName}, $boughtAt, $readAt, $rating)
        """.asTry(SqlParser.scalar[UUID].single)

      }
    }
  }

}
