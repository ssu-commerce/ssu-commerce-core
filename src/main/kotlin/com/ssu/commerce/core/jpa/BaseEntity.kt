package com.ssu.commerce.core.jpa

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
open class BaseEntity(
    @CreatedBy
    @Column(name = "created_who", nullable = false, updatable = false)
    private var _createdWho: String? = null,

    @LastModifiedBy
    @Column(name = "updated_who")
    private var _updatedWho: String? = null
) : BaseTimeEntity() {
    val createdWho: String?
        get() = _createdWho

    val updatedWho: String?
        get() = _updatedWho
}