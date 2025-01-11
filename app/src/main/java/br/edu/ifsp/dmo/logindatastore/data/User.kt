package br.edu.ifsp.dmo.logindatastore.data

object User {
    private const val login = "admin"
    private const val password = "admin"

    fun autenticate(login: String, password: String): Boolean {
        return this.login == login && this.password == password
    }
}