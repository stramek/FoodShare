package com.marcinstramowski.socialmeal.screens.main

import com.marcinstramowski.socialmeal.screens.base.BaseContract

/**
 * This specifies the contract between the view and the presenter.
 */
interface MainContract {

    interface View : BaseContract.View<Presenter> {

    }

    interface Presenter : BaseContract.Presenter {

    }
}