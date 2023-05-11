package com.example.prototype.root

import android.nfc.NfcAdapter
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prototype.*
import com.example.prototype.screens.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * enum values that represent the screens in the app
 */
enum class PaymentScreen {
    Price,
    TapCard,
    PIN,
    Loading,
    Success,
    Failure
}

val LocalNfcAdapter = compositionLocalOf<NfcAdapter?> { null }

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PaymentApp(
    modifier: Modifier = Modifier,
    PaymentViewModel: PaymentViewModel = PaymentViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current

    Scaffold { innerPadding ->
        val state = PaymentViewModel.state

        NavHost(
            navController = navController,
            startDestination = PaymentScreen.Price.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = PaymentScreen.Price.name) {
                EnterPriceScreen(
                    state = state,
                    onAction = PaymentViewModel::onAction,
                    onContinueButtonClicked = {
                        PaymentViewModel.alignDecimal()
                        navController.navigate(PaymentScreen.TapCard.name)
                    },
                    onCancelButtonClicked = {
                        cancelPaymentAndNavigateToStart(PaymentViewModel, navController)
                    },
                )
            }
            composable(route = PaymentScreen.TapCard.name) {
                val defaultNfcAdapter = NfcAdapter.getDefaultAdapter(LocalContext.current)
                CompositionLocalProvider(LocalNfcAdapter provides defaultNfcAdapter) {
                    TapCardScreen(
                        state = state,
                        navController = navController,
                        onCancelButtonClicked = {
                            cancelPaymentAndNavigateToStart(PaymentViewModel, navController)
                        }
                    )
                }
            }
            composable(route = PaymentScreen.PIN.name) {
                val coroutineScope = rememberCoroutineScope()
                EnterPinScreen(
                    state = state,
                    onAction = PaymentViewModel::onAction,
                    onConfirmButtonClicked = {
                        clickResponse(context)
                        coroutineScope.launch {
                            checkCorrectPin(PaymentViewModel, navController)
                        }
                    },
                    onBackButtonClicked = {
                        clickResponse(context)
                        PaymentViewModel.deletePin()
                    },
                    onCancelButtonClicked = {
                        clickResponse(context)
                        PaymentViewModel.deletePin()
                        PaymentViewModel.resetPinAttempts()
                        navController.navigate(PaymentScreen.TapCard.name)
                    }
                )
            }
            composable(route = PaymentScreen.Loading.name) {
                LoadingScreen()
                LaunchedEffect(key1 = Unit) {
                    delay(4000)
                    navController.navigate(PaymentScreen.Success.name)
                }
            }
            composable(route = PaymentScreen.Success.name) {
                SuccessScreen(
                    state = state,
                    onCloseButtonClicked = {
                        cancelPaymentAndNavigateToStart(PaymentViewModel, navController)
                    })
                PaymentViewModel.analysis(context)
            }
            composable(route = PaymentScreen.Failure.name) {
                FailedScreen(
                    state = state,
                    onRetryButtonClicked = {
                        PaymentViewModel.resetPinAttempts()
                        navController.navigate(PaymentScreen.TapCard.name)
                    },
                    onCancelButtonClicked = {
                        cancelPaymentAndNavigateToStart(PaymentViewModel, navController)
                    })
                PaymentViewModel.analysis(context)
            }
        }
    }
}

/**
 * Checks the [PaymentState.pin] and pops up to [PaymentScreen.Loading] if correct,
 * otherwise lowers the [PaymentState.pinAttempts], resets the PIN,
 * and pops up to [PaymentScreen.Failure] if no attempts left
 */
private suspend fun checkCorrectPin(
    viewModel: PaymentViewModel,
    navController: NavHostController
) {
    if (viewModel.isCorrectPin()) {
        navController.navigate(PaymentScreen.Loading.name)
    } else {
        viewModel.wrongPin()
        if (viewModel.state.pinAttempts == 0) {
            delay(timeMillis = 500)
            navController.navigate(PaymentScreen.Failure.name)
        }
    }
}

/**
 * Resets the [PaymentState] and pops up to [PaymentScreen.Price]
 */
private fun cancelPaymentAndNavigateToStart(
    viewModel: PaymentViewModel,
    navController: NavHostController
) {
    viewModel.resetPayment()
    navController.popBackStack(PaymentScreen.Price.name, inclusive = false)
}