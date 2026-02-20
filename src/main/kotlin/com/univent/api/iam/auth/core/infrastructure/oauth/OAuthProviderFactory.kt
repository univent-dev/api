package com.univent.api.iam.auth.core.infrastructure.oauth

import com.univent.api.iam.auth.core.domain.OAuthProvider
import com.univent.api.iam.auth.user.domain.vo.OAuthProviderType
import org.springframework.stereotype.Component

@Component
class OAuthProviderFactory(
    private val providers: Map<String, OAuthProvider>
) {
    fun getProvider(type: OAuthProviderType): OAuthProvider {
        val beanName = "${type.name.lowercase()}OAuthProvider"
        return providers[beanName]
            ?: throw IllegalArgumentException("OAuth provider for $type not found")
    }
}
