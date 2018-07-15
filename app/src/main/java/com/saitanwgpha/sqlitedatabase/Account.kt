package com.saitanwgpha.sqlitedatabase

class Account {
    var userName: String? = null
    var email: String? = null

    constructor() {}

    constructor(userName: String, email: String) {
        this.userName = userName
        this.email = email
    }
}
