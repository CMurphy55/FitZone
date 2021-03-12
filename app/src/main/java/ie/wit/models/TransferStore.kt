package ie.wit.models;

interface TransferStore {

    fun findAll() : List<TransferModel>
    fun findById(id: String) : TransferModel?
    fun create(donation: TransferModel)
    fun update(donation: TransferModel)
    fun delete(donation: TransferModel)
}