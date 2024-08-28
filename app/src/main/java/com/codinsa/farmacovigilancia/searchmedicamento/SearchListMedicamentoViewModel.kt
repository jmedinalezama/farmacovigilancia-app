package com.codinsa.farmacovigilancia.searchmedicamento

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codinsa.farmacovigilancia.network.response.SearchMedicamentoResponse
import com.codinsa.farmacovigilancia.network.response.SearchMedicamentosResponse
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class SearchListMedicamentoViewModel : ViewModel() {

    private val repository = SearchListMedicamentoRepository()

    private val disposable = CompositeDisposable()

    var medicamentosListService = MutableLiveData<List<SearchMedicamentoResponse>>()

    var detalleMedicamentoService = MutableLiveData<List<SearchMedicamentoResponse>>()

    fun getListMedicamentosPorNombre(nombre: String) {
        disposable.add(
            repository.getMedicamentosPorNombre(nombre)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<SearchMedicamentosResponse>() {
                    override fun onSuccess(t: SearchMedicamentosResponse) {
                        medicamentosListService.value = t.resultados
                    }

                    override fun onError(e: Throwable) {
                        medicamentosListService.value = emptyList()
                    }
                })
        )
    }

    fun getDetallesMedicamento(nregistro: String) {
       disposable.add(
           repository.getDetallesMedicamento(nregistro)
               .subscribeOn(Schedulers.newThread())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribeWith(object: DisposableSingleObserver<SearchMedicamentosResponse>() {
                   override fun onSuccess(t: SearchMedicamentosResponse) {
                       detalleMedicamentoService.value = t.resultados
                   }

                   override fun onError(e: Throwable) {
                       detalleMedicamentoService.value = emptyList()
                   }
               })
       )
    }

}