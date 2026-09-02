package com.example.dragonballwiki.core.ui

/**
 * Sealed interface representing different loading states in the UI.
 * Provides granular control over loading indicators.
 *
 * @see Idle when no loading operation is in progress
 * @see Loading for initial data loading
 * @see Refreshing for refresh operations (pull-to-refresh)
 */
sealed interface LoadingState {
    /**
     * No loading operation in progress.
     * UI should display normal content or empty state.
     */
    data object Idle : LoadingState

    /**
     * Initial loading operation in progress.
     * UI should display a full-screen loading indicator.
     */
    data object Loading : LoadingState

    /**
     * Refresh operation in progress.
     * UI should display content with a refresh indicator (e.g., SwipeRefresh).
     */
    data object Refreshing : LoadingState
}
