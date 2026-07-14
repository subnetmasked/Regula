package org.regula.app.ui.navigation

object Routes {
    const val WELCOME = "welcome"
    const val CATEGORY = "category/{categoryId}"
    const val ENTRY = "entry/{entryId}"

    fun category(categoryId: String): String = "category/$categoryId"
    fun entry(entryId: String): String = "entry/$entryId"
}
