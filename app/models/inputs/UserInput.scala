package models.inputs

case class UserInput(
    login: String,
    password: String,
    firstName: Option[String],
    lastName: Option[String]
)

object UserInput {
  implicit val reads = play.api.libs.json.Json.reads[UserInput]
  implicit val writes = play.api.libs.json.Json.writes[UserInput]
}
