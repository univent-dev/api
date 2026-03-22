package com.univent.api.iam.auth.core.domain

interface OAuthProvider {
    fun getToken(code: String): String
    fun getUserInfo(token: String): OAuthUser
    fun getAuthorizationUrl(state: String?): String
    fun unlinkAccount(oAuthId: String)
}
