package com.saif.oceanofimage.api

import com.saif.oceanofimage.data.UnsplashPhoto

data class UnsplashResponse(
    val results: List<UnsplashPhoto>
) {
}