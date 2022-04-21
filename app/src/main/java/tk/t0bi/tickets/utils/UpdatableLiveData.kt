package tk.t0bi.tickets.utils

import androidx.lifecycle.MutableLiveData

class UpdatableLiveData<T> : MutableLiveData<T>() {

    fun update() {
        //Calling the setter triggers the update
        value = value
    }

}