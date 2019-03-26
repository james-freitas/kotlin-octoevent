package com.company.model

import com.fasterxml.jackson.annotation.JsonIgnore

class Issue (

    var number: Int,
    var created_at: String?,

    @JsonIgnore
    var url: String?,

    @JsonIgnore
    var repository_url: String?,

    @JsonIgnore
    var labels_url: String?,

    @JsonIgnore
    var comments_url: String?,

    @JsonIgnore
    var events_url: String?,

    @JsonIgnore
    var html_url: String?,

    @JsonIgnore
    var id: Int?,

    @JsonIgnore
    var node_id: String?,

    @JsonIgnore
    var title: String?,

    @JsonIgnore
    var user: Any?,

    @JsonIgnore
    var labels: Any?,

    @JsonIgnore
    var state: String?,

    @JsonIgnore
    var locked: Boolean?,

    @JsonIgnore
    var assignee: String?,

    @JsonIgnore
    var assignees: Any?,

    @JsonIgnore
    var milestone: String?,

    @JsonIgnore
    var comments: Int?,

    @JsonIgnore
    var updated_at: String?,

    @JsonIgnore
    var closed_at: String?,

    @JsonIgnore
    var author_association: String?,

    @JsonIgnore
    var body: String?
)

