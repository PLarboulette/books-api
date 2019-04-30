package models
import enumeratum._

import scala.collection.immutable


sealed trait Status extends EnumEntry

object Status extends Enum[Status] with PlayJsonEnum[Status] {

  override def values: immutable.IndexedSeq[Status] = findValues

  case object TO_BUY extends Status
  case object TO_READ extends Status
  case object IN_PROGRESS extends Status
  case object READ extends Status

}