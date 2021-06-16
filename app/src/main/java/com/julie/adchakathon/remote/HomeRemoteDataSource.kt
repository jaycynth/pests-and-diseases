package com.julie.adchakathon.remote

import javax.inject.Inject

class HomeRemoteDataSource @Inject constructor(
    private val adcService: ADCService
) : BaseDataSource()  {
}