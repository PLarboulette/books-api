package services

import java.util.UUID

import cats.effect.IO
import com.typesafe.scalalogging.LazyLogging
import helpers.AppError
import javax.inject.{ Inject, Singleton }
import models.Library
import models.inputs.LibraryInput
import repositories.LibraryRepository

import scala.util.{ Failure, Success, Try }

@Singleton
class LibraryService @Inject()(
    libraryRepository: LibraryRepository
) extends LazyLogging {

  def save(libraryInput: LibraryInput): IO[UUID] = {
    libraryRepository.save(libraryInput) match {
      case Success(uuid) => IO.pure(uuid)
      case Failure(e) => IO.raiseError(AppError.InternalError(e.getMessage))
    }
  }

  def find(idUser: Option[UUID]): IO[List[Library]] = {
    IO.pure(libraryRepository.find(idUser))
  }
}
