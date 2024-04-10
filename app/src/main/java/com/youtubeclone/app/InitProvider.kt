package com.youtubeclone.app

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import timber.log.Timber

abstract class InitProvider : ContentProvider() {

    private fun unsupported(errorMessage: String? = null): Nothing =
        throw UnsupportedOperationException(errorMessage)

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? = unsupported()

    override fun getType(uri: Uri): String? = unsupported()

    override fun insert(uri: Uri, values: ContentValues?) = unsupported()

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int =
        unsupported()

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = unsupported()
}