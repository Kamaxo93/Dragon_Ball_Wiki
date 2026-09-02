package com.example.dragonballwiki.core.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * Sealed interface for representing text in the UI layer.
 * Supports both static string resources and dynamic strings.
 *
 * @see StringResource for localized strings from resources
 * @see DynamicString for runtime-generated strings
 * @see Empty for empty text
 */
sealed interface UiText {
    /**
     * Represents a string from Android resources (strings.xml).
     * Supports localization and parameterized strings.
     *
     * @property resId The string resource ID
     * @property args Optional arguments for parameterized strings
     */
    data class StringResource(
        @StringRes val resId: Int,
        val args: List<Any> = emptyList()
    ) : UiText

    /**
     * Represents a dynamic string created at runtime.
     * Use this for server-provided messages or computed text.
     *
     * @property value The text value
     */
    data class DynamicString(val value: String) : UiText

    /**
     * Represents empty text (no content).
     */
    data object Empty : UiText

    /**
     * Converts this UiText to a String in a Composable context.
     * Automatically resolves string resources and handles arguments.
     *
     * @return The resolved string
     */
    @Composable
    fun asString(): String = when (this) {
        is StringResource -> stringResource(resId, *args.toTypedArray())
        is DynamicString -> value
        Empty -> ""
    }

    /**
     * Converts this UiText to a String outside of Composable context.
     * Requires a Context to resolve string resources.
     *
     * @param context Android Context for resource resolution
     * @return The resolved string
     */
    fun asString(context: Context): String = when (this) {
        is StringResource -> context.getString(resId, *args.toTypedArray())
        is DynamicString -> value
        Empty -> ""
    }
}
