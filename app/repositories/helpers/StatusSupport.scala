package repositories.helpers

import anorm.{ Column, TypeDoesNotMatch }
import models.Status

trait StatusSupport {

  implicit val columnToStatus: Column[Status] = Column.nonNull[Status] { (value, _) =>
    value match {
      case value: String =>
        Status.withNameOption(value) match {
          case Some(status) =>
            Right(status)
          case None =>
            Left(TypeDoesNotMatch(s"Can not parse Status with $value"))
        }
      case _ =>
        Left(TypeDoesNotMatch(s"Can not parse Status with non string : $value"))
    }
  }

}
