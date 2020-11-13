/*
 * Unless explicitly stated otherwise all files in this repository are licensed under the Apache License Version 2.0.
 * This product includes software developed at Datadog (https://www.datadoghq.com/).
 * Copyright 2016-Present Datadog, Inc.
 */

package com.datadog.android.sdk.utils

import android.content.Intent
import com.datadog.android.privacy.TrackingConsent

private const val TRACKING_CONSENT_KEY = "tracking_consent"
private const val PENDING = 1
private const val GRANTED = 2
private const val NOT_GRANTED = 3

internal fun Intent.getTrackingConsent(): TrackingConsent {
    return when (getIntExtra(TRACKING_CONSENT_KEY, PENDING)) {
        PENDING -> TrackingConsent.PENDING
        GRANTED -> TrackingConsent.GRANTED
        else -> TrackingConsent.NOT_GRANTED
    }
}

internal fun Intent.addTrackingConsent(consent: TrackingConsent) {
    val consentToInt = when (consent) {
        TrackingConsent.PENDING -> PENDING
        TrackingConsent.GRANTED -> GRANTED
        else -> NOT_GRANTED
    }
    this.putExtra(TRACKING_CONSENT_KEY, consentToInt)
}
