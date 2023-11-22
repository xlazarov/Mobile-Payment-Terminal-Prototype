package com.example.prototype


import android.app.Activity
import android.nfc.NfcAdapter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import com.example.prototype.root.LocalNfcAdapter


/**
 *  Composable function that is responsible for setting up and handling NFC tag reading.
 */
@Composable
fun NfcReader(onTagDiscovered: () -> Unit) {
    val context = LocalContext.current
    val nfcAdapter = LocalNfcAdapter.current ?: NfcAdapter.getDefaultAdapter(context)

    DisposableEffect(nfcAdapter) {
        val callback = NfcAdapter.ReaderCallback { onTagDiscovered() }

        nfcAdapter.enableReaderMode(
            context as Activity,
            callback,
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            null
        )
        onDispose {
            nfcAdapter.disableReaderMode(context)
        }
    }
}