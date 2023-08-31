package com.ubcompany.umbba_android.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var isReceiver: Boolean,
    var name: String? = null,
    var gender: String? = null,
    var bornYear: Int? = null,
    var isInvitorChild: Boolean? = null,
    var relationInfo: String? = null
) : Parcelable
