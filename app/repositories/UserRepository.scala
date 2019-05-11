package repositories

import java.util.UUID

import anorm._
import javax.inject.{ Inject, Singleton }
import models.User
import models.inputs.UserInput
import play.api.db.Database
import repositories.helpers.UserSupport

@Singleton
class UserRepository @Inject()(db: Database) extends UserSupport {

  def find(): List[User] = {
    db.withConnection { implicit connection =>
      SQL""" SELECT * FROM "User" """.as(userRowParser.*)
    }
  }

  def save(userInput: UserInput) = {
    db.withConnection { implicit connection =>
      SQL"""
           INSERT INTO "User"("login", "password", "firstName", "lastName") VALUES
           (${userInput.login}, ${userInput.password}, ${userInput.firstName}, ${userInput.lastName})
         RETURNING "idUser"
         """.asTry(SqlParser.scalar[UUID].single)
    }
  }
}
