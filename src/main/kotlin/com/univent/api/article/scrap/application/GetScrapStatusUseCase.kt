package com.univent.api.article.scrap.application

import com.univent.api.article.scrap.application.query.GetScrapStatusQuery

interface GetScrapStatusUseCase {
    fun execute(query: GetScrapStatusQuery): Boolean
}
