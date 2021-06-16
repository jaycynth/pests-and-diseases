package com.julie.adchakathon.repositories

import com.julie.adchakathon.remote.HomeRemoteDataSource
import javax.inject.Inject

class HomeRepo @Inject constructor(val homeRemoteDataSource: HomeRemoteDataSource) {
}