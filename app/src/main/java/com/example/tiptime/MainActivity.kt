package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tiptime.ui.theme.TipTimeTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipTimeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TipTimeLayout(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@VisibleForTesting
internal fun calculateTip(amount: Double, tipPercentage: Double = 15.0, isRoundUp: Boolean = false): String {
    var tip = tipPercentage / 100 * amount
    if (isRoundUp)
        tip = kotlin.math.ceil(tip)
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Composable
fun EditNumberField(label: String, value: String, leadingIcon: Painter, onValueChange: (String) -> Unit, keyboardOptions: KeyboardOptions, modifier: Modifier = Modifier) {
    TextField(
        label = { Text(text = label) },
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        leadingIcon = { Icon(
            leadingIcon,
            contentDescription = ""
        ) },
        modifier = modifier
    )
}

@Composable
fun RoundTheTipRow(isRoundUp: Boolean, onRoundUpChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
    ) {
        Text(
            text = stringResource(id = R.string.round_up_tip)
        )
        Switch(
            checked = isRoundUp,
            onCheckedChange = onRoundUpChange
        )
    }
}

@Composable
fun TipTimeLayout(modifier: Modifier = Modifier) {
    var amountInput by remember { mutableStateOf("") }
    val amount = amountInput.toDoubleOrNull() ?: 0.0

    var tipPercentageInput by remember { mutableStateOf("") }
    val tipPercentage = tipPercentageInput.toDoubleOrNull() ?: 0.0

    var isRoundUp by remember { mutableStateOf(false) }

    val tip = calculateTip(amount = amount, tipPercentage = tipPercentage, isRoundUp = isRoundUp)

    Column(
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp, start = 20.dp)
                .align(alignment = Alignment.Start)
        )

        EditNumberField(
            label = stringResource(id = R.string.bill_amount),
            value = amountInput,
            leadingIcon = painterResource(id = R.drawable.money_24dp_e8eaed_fill0_wght400_grad0_opsz24),
            onValueChange = {amountInput = it},
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.padding(bottom = 40.dp)
        )

        EditNumberField(
            label = stringResource(id = R.string.how_was_the_service),
            value = tipPercentageInput,
            leadingIcon = painterResource(id = R.drawable.percent_24dp_e8eaed_fill0_wght400_grad0_opsz24),
            onValueChange = {tipPercentageInput = it},
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.padding(bottom = 40.dp)
        )

        RoundTheTipRow(
            isRoundUp = isRoundUp,
            onRoundUpChange = { isRoundUp = it }
        )

        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TipTimePreview() {
    TipTimeTheme {
        TipTimeLayout()
    }
}