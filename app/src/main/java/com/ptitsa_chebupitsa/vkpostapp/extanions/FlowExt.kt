package com.ptitsa_chebupitsa.vkpostapp.extanions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge

fun <T> Flow<T>.mergeWith(other: Flow<T>): Flow<T> = merge(this, other)