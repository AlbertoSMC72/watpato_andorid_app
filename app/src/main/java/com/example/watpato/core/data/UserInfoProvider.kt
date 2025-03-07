package com.example.watpato.core.data

object UserInfoProvider {
    private var _userID: Number? = null
    private var _username: String? = null

    var userID: Number?
        get() = _userID
        set(value) { _userID = value }

    var username: String?
        get() = _username
        set(value) { _username = value }
}
