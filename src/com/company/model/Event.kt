package com.company.com.company.model

import com.company.model.Issue
import com.fasterxml.jackson.annotation.JsonIgnore

data class Event(
    val id: Int,
    val action: String,
    val issue: Issue,

    @JsonIgnore
    var repository: Any?,

    @JsonIgnore
    var sender: Any?
)