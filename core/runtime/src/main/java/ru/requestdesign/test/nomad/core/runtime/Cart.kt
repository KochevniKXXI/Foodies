package ru.requestdesign.test.nomad.core.runtime

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import ru.requestdesign.test.nomad.core.model.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Cart @Inject constructor() : RuntimeDataSource {
    private val productsMap = mutableMapOf<Product, Int>()
    private var mListener: OnChangeListener? = null

    override val cartFlow = callbackFlow {
        val listener = object : OnChangeListener {
            override fun onChange(cart: Map<Product, Int>) {
                trySend(cart)
            }
        }
        mListener = listener
        awaitClose { mListener = null }
    }.onStart { emit(productsMap) }

    override fun getSum(): Flow<Int> = flowOf(
        productsMap.map { it.key.priceCurrent * it.value }.sum()
    )

    override fun addProduct(product: Product) {
        productsMap.merge(product, 1) { oldValue, _ ->
            oldValue + 1
        }
        mListener?.onChange(productsMap)
    }

    override fun removeProduct(product: Product) {
        productsMap.merge(product, 0) { oldValue, _ ->
            if (oldValue <= 1) {
                null
            } else {
                oldValue - 1
            }
        }
        mListener?.onChange(productsMap)
    }
}

interface OnChangeListener {
    fun onChange(cart: Map<Product, Int>)
}