package com.ummug.mobilebank.datasource

import com.ummug.mobilebank.model.users
import javax.inject.Inject

class UsersDataSourceImpl @Inject constructor() :UsersDataSource {
    override suspend fun getAllUsers(): List<users> {
        return emptyList()
    }
}