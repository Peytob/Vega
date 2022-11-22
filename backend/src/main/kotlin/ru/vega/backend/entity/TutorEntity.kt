package ru.vega.backend.entity

import javax.persistence.*

// TODO Переименовать в TutorDetailsEntity !!!
@Entity
@Table(name = "tutor_details")
open class TutorEntity(

    @get:ManyToOne(optional = false)
    open var district: DistrictEntity? = null,

    @get:Column(nullable = false)
    open var address: String? = null,

    @get:Column(nullable = false)
    open var name: String? = null,

    @get:Column(nullable = false)
    open var notificationChatId: Long? = null,

    @get:Column(nullable = false)
    open var email: String? = null,

    @get:Column(nullable = false)
    open var phone: String? = null,

    @get:Column(nullable = false)
    open var photoUrl: String? = null,

    @get:Column(nullable = false)
    open var offline: Boolean? = null,

    @get:Column(nullable = false)
    open var online: Boolean? = null,

    @get:Column(nullable = false, columnDefinition = "TEXT")
    open var description: String? = null,

    @get:Column(nullable = false)
    open var price: Int? = null,

    open var whatsApp: String? = null,

    @get:OneToOne(optional = false)
    open var user: TutorUser? = null,

    @get:ManyToMany
    @get:JoinTable(
        name = "tutor_discipline_link",
        joinColumns = [JoinColumn(name = "tutor_id")],
        inverseJoinColumns = [JoinColumn(name = "discipline_id")]
    )
    open var disciplines: Collection<DisciplineEntity>? = mutableSetOf()
) : AbstractEntity()
