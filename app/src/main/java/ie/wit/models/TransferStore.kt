package ie.wit.models;

import android.content.Context

interface TransferStore {

    fun findAll() : List<TransferModel>
    fun findById(id: String) : TransferModel?
    fun create(meal: TransferModel)
    fun update(meal: TransferModel)
    fun delete(meal: TransferModel)

}