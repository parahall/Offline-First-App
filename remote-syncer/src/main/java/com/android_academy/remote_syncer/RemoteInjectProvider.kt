package com.android_academy.remote_syncer

interface RemoteInjectProvider {
    fun inject(specificRemoteWorker: SpecificRemoteWorker)
}