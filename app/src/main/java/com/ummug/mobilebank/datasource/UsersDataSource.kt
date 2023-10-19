package com.ummug.mobilebank.datasource

import com.ummug.mobilebank.model.users

 interface UsersDataSource {

     suspend fun getAllUsers() : List<users>
}