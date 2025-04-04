package com.usman.animelistgraphql.data.domain

import com.usman.animelistgraphql.AnimeListQuery

data class AnimeDomain(
    val page: Page,
    val media: List<Media>
)

data class Page(
    val hasNextPage: Boolean,
    val currentPage: Int,
    val lastPage: Int
)

data class Media(
    val coverImage: String?,
    val title: String,
    val bannerImage: String?
)


//Converting query to domain ex function
fun AnimeListQuery.Data.toDomain(): AnimeDomain{
    val pageInfo = this.Page?.pageInfo
    return AnimeDomain(
        Page(
            pageInfo?.hasNextPage ?: false,
            pageInfo?.currentPage ?: 1,
            pageInfo?.lastPage ?: 1
        ),
        this.Page?.media?.map {
            Media(
                it?.coverImage?.medium,
                it?.title?.english ?: "Unknown",
                it?.bannerImage
            )
        } ?: emptyList()
    )
}