package com.example.dragonballwiki.core.ui

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * View object representing cache status information for character data.
 * Contains all pre-calculated UI properties to avoid business logic in Composables.
 *
 * @property lastUpdated Timestamp (millis) when data was last updated
 * @property daysOld Number of days since last update
 * @property emoji Visual indicator emoji for cache status
 * @property message Localized message describing cache status
 * @property severity Cache freshness level
 */
@Immutable
data class CacheInfoVO(
    val lastUpdated: Long,
    val daysOld: Long,
    val emoji: String,
    val message: UiText,
    val severity: CacheSeverity
)

/**
 * Enum representing cache data freshness levels.
 * Used to determine background color and visual treatment.
 */
enum class CacheSeverity {
    /** Data updated today (0 days old) */
    FRESH,

    /** Data is recent (1-4 days old) */
    RECENT,

    /** Data is getting old (5-9 days old) */
    OLD,

    /** Data may be outdated (10+ days old) */
    STALE
}
