package repositories

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import anorm.{ SQL, _ }
import com.typesafe.config.Config
import fixtures.UserFixtures
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{ BeforeAndAfterAll, BeforeAndAfterEach, Inside }
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.db.Database
import play.api.db.evolutions.Evolutions
import play.api.inject.Injector
import repositories.helpers.UserSupport

import scala.concurrent.ExecutionContextExecutor

class UserRepositorySpec
  extends PlaySpec
  with BeforeAndAfterAll
  with BeforeAndAfterEach
  with GuiceOneAppPerSuite
  with Inside
  with ScalaFutures
  with UserSupport
  with UserFixtures {

  implicit val system: ActorSystem = ActorSystem("test-system", app.injector.instanceOf[Config])
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = materializer.executionContext

  val injector: Injector = app.injector

  val db: Database = injector.instanceOf[Database]

  override def beforeAll() = {
    db.withConnection { implicit connection =>
      SQL("""DROP SCHEMA public CASCADE""").execute()
      SQL("""CREATE SCHEMA public""").execute()
    }

    Evolutions.cleanupEvolutions(db)
    Evolutions.applyEvolutions(db)
  }

  override def beforeEach(): Unit = {
    db.withConnection { implicit connection =>
      SQL("""TRUNCATE "Book", "Library", "User" CASCADE;""").execute()
    }
  }

  override def afterAll(): Unit = {
    db.shutdown()
  }

  val userRepository: UserRepository = injector.instanceOf[repositories.UserRepository]

  "UserRepository" should {

    "Retrieve a list of user" in {
      userRepository.save(user)
      val users = userRepository.find()
      assert(users.size == 1)
    }

    "Retrieve an empty list of user" in {
      val users = userRepository.find()
      assert(users.isEmpty)
    }

    "Save an user" in {
      userRepository.save(user)
      db.withConnection { implicit connection =>
        val users = SQL""" SELECT * FROM "User" """.as(userRowParser.*)
        assert(users.size == 1)
      }
    }

    "Doesn't insert an user with an existing login" in {
      val insert1 = userRepository.save(user)
      val insert2 = userRepository.save(user)
      assert(insert1.isSuccess)
      assert(insert2.isFailure)
      assert(userRepository.find().size == 1)
    }
  }

}
