package com.dissiapps.crypto.data.models.fgindex

import com.dissiapps.crypto.data.models.fgindex.FGIndexData
import com.dissiapps.crypto.data.models.fgindex.Metadata

data class FGIndexResponse(
    val data: List<FGIndexData>?,
    val metadata: Metadata,
    val name: String
)