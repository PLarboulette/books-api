package fixtures

import models.inputs.UserInput

trait UserFixtures {

  val user = UserInput(
    "Login",
    "Password",
    Some("FirstName"),
    Some("LastName")
  )

}
