/**
 * BreadWallet
 * <p/>
 * Created by Drew Carlson <drew.carlson@breadwallet.com> on 6/2/2019.
 * Copyright (c) 2019 breadwallet LLC
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.breadwallet.legacy.pricealert

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.breadwallet.R
import com.breadwallet.ext.bindCreated
import com.breadwallet.ext.viewModel
import com.breadwallet.legacy.presenter.activities.util.BRActivity
import kotlinx.android.synthetic.main.activity_price_alert_list.*

/**
 * Displays a list of all [PriceAlert]s and allows the user
 * to delete them.
 */
class PriceAlertListActivity : BRActivity() {

    //override fun getLayoutId(): Int = R.layout.activity_price_alert_list
    //override fun getBackButtonId(): Int = R.id.back_button

    private val viewModel: PriceAlertListViewModel by viewModel()

    private val adapter by bindCreated { PriceAlertAdapter(this, viewModel::removePriceAlert) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        price_alert_list.layoutManager = LinearLayoutManager(this)
        price_alert_list.adapter = adapter
        back_button.setOnClickListener { onBackPressed() }
        add_alert_button.setOnClickListener {
            NewPriceAlertActivity.open(this)
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left)
        }
    }

    override fun onResume() {
        super.onResume()

        val owner: LifecycleOwner = this
        viewModel.apply {
            getPriceAlerts().observe(owner, Observer { priceAlerts ->
                adapter.priceAlerts = priceAlerts!!
            })
        }
    }
}
