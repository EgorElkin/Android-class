package com.example.zulipapp.presentation.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V : MvpView>(private var view: V?) : MvpPresenter<V> {

    val compositeDisposable = CompositeDisposable()

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        view = null
        compositeDisposable.dispose()
    }

    fun getView() = view
}