package com.sdk.datastorepref.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sdk.datastorepref.model.User
import com.sdk.datastorepref.util.Constants.NAME
import com.sdk.datastorepref.util.Constants.AGE
import com.sdk.datastorepref.util.Constants.LAST_NAME
import com.sdk.datastorepref.util.Constants.PREFS_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(
    private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFS_NAME)

    companion object {
        val name = stringPreferencesKey(name = NAME)
        val lastName = stringPreferencesKey(name = LAST_NAME)
        val age = stringPreferencesKey(name = AGE)
    }

    suspend fun saveUser(user: User) {
        context.dataStore.edit {
            it[name] = user.name
            it[lastName] = user.lastName
            it[age] = user.age
        }
    }

    suspend fun getUser(): Flow<User> = context.dataStore.data.map {
        User(
            name = it[name] ?: "",
            lastName = it[lastName] ?: "",
            age = it[age] ?: ""
        )
    }
}