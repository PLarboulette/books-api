package services

import java.util.UUID

import cats.effect.IO
import com.typesafe.scalalogging.LazyLogging
import helpers.AppError
import javax.inject.{ Inject, Singleton }
import models.User
import models.inputs.UserInput
import repositories.UserRepository

import scala.util.{ Failure, Success }

@Singleton
class UserService @Inject()(
    userRepository: UserRepository
) extends LazyLogging {

  def save(userInput: UserInput): IO[UUID] = {
    userRepository.save(userInput) match {
      case Success(uuid) => IO.pure(uuid)
      case Failure(e) => IO.raiseError(AppError.InternalError(e.getMessage))
    }
  }

  def find(): IO[List[User]] = {
    IO.pure(userRepository.find())
  }
}
