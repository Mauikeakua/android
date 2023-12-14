package com.tonkeeper.fragment.settings.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.tonkeeper.R
import com.tonkeeper.dialog.LogoutDialog
import com.tonkeeper.fragment.currency.CurrencyScreen
import com.tonkeeper.fragment.main.MainTabScreen
import com.tonkeeper.fragment.settings.accounts.AccountsScreen
import com.tonkeeper.fragment.settings.legal.LegalFragment
import com.tonkeeper.fragment.settings.list.SettingsAdapter
import com.tonkeeper.fragment.settings.list.item.SettingsIdItem
import com.tonkeeper.fragment.settings.security.SecurityFragment
import uikit.decoration.ListCellDecoration
import uikit.list.LinearLayoutManager
import uikit.mvi.UiScreen
import uikit.navigation.Navigation.Companion.navigation

class SettingsScreen: MainTabScreen<SettingsScreenState, SettingsScreenEffect, SettingsScreenFeature>(R.layout.fragment_settings) {

    companion object {
        fun newInstance() = SettingsScreen()
    }

    override val feature: SettingsScreenFeature by viewModels()

    private val logoutDialog: LogoutDialog by lazy {
        LogoutDialog(requireContext())
    }

    private val adapter = SettingsAdapter { item ->
        if (item is SettingsIdItem) {
            onCellClick(item)
        }
    }

    private lateinit var listView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView = view.findViewById(R.id.list)
        listView.layoutManager = LinearLayoutManager(view.context)
        listView.addItemDecoration(ListCellDecoration(view.context))
        listView.adapter = adapter
    }

    override fun newUiState(state: SettingsScreenState) {
        adapter.submitList(state.items)
    }

    override fun newUiEffect(effect: SettingsScreenEffect) {
        super.newUiEffect(effect)
        if (effect is SettingsScreenEffect.Logout) {
            navigation?.initRoot(true)
        }
    }

    private fun showLogoutDialog() {
        logoutDialog.show {
            feature.logout()
        }
    }

    private fun onCellClick(item: SettingsIdItem) {
        val nav = navigation ?: return

        when (item.id) {
            SettingsIdItem.MANAGE_WALLETS_ID -> {
                nav.add(AccountsScreen.newInstance())
            }
            SettingsIdItem.LOGOUT_ID -> {
                showLogoutDialog()
            }
            SettingsIdItem.CURRENCY_ID -> {
                nav.add(CurrencyScreen.newInstance())
            }
            SettingsIdItem.LEGAL_ID -> {
                nav.add(LegalFragment.newInstance())
            }
            SettingsIdItem.SECURITY_ID -> {
                nav.add(SecurityFragment.newInstance())
            }
            SettingsIdItem.CONTACT_US_ID -> {
                nav.openURL(feature.supportLink, true)
            }
            SettingsIdItem.TONKEEPER_NEWS_ID -> {
                nav.openURL(feature.tonkeeperNewsUrl, true)
            }
            SettingsIdItem.SUPPORT_ID -> {
                nav.openURL(feature.directSupportUrl, true)
            }
        }
    }

    override fun onUpScroll() {
        listView.scrollToPosition(0)
    }
}