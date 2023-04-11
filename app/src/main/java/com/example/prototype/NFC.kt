package com.example.prototype


import android.app.Activity
import android.nfc.NfcAdapter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext


@Composable
fun NfcReader(onTagDiscovered: (String?) -> Unit) {
    val context = LocalContext.current
    val nfcAdapter = LocalNfcAdapter.current ?: NfcAdapter.getDefaultAdapter(context)

    DisposableEffect(nfcAdapter) {
        val callback = NfcAdapter.ReaderCallback { onTagDiscovered(null) }

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