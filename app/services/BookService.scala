package services

import java.util.UUID

import cats.effect.IO
import com.typesafe.scalalogging.LazyLogging
import helpers.AppError
import javax.inject.{ Inject, Singleton }
import models.Book
import models.inputs.BookInput
import repositories.BookRepository

import scala.util.{ Failure, Success, Try }

@Singleton
class BookService @Inject()(
    bookRepository: BookRepository
) extends LazyLogging {

  def find(idLibrary: Option[UUID]): IO[List[Book]] = {
    IO.pure(bookRepository.find(idLibrary))
  }

  def findById(idBook: UUID): IO[Option[Book]] = {
    IO.pure(bookRepository.findById(idBook))
  }

  def save(bookInput: BookInput): IO[UUID] = {
    bookRepository.save(bookInput) match {
      case Success(value) => IO.pure(value)
      case Failure(exception) => IO.raiseError(AppError.InternalError(exception.getMessage))
    }
  }
}
