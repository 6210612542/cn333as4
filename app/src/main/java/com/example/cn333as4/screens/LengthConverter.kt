package com.example.cn333as4.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cn333as4.R
import com.example.cn333as4.viewmodels.LengthViewModel


@Composable
fun LengthConverter() {
    val viewModel: LengthViewModel = viewModel()
    val strCen = stringResource(id = R.string.cen)
    val strInch = stringResource(id = R.string.inch)
    val currentValue = viewModel.length.observeAsState(viewModel.length.value ?: "")
    val cm = viewModel.cm.observeAsState(viewModel.cm.value ?: R.string.cen)
    var result by rememberSaveable { mutableStateOf("") }

    val enabled by remember(currentValue.value) {
        mutableStateOf(!viewModel.getLengthAsFloat().isNaN())
    }
    val calc = {
        val temp = viewModel.convert()
        result = if (temp.isNaN())
            ""
        else
            "$temp${
                if (cm.value == R.string.cen)
                    strInch
                else strCen
            }"
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LengthTextField(
            temperature = currentValue,
            modifier = Modifier.padding(bottom = 16.dp),
            callback = calc,
            viewModel = viewModel
        )
        LengthButtonGroup(
            selected = cm,
            modifier = Modifier.padding(bottom = 16.dp)
        ) { resId: Int -> viewModel.setCm(resId) }
        Button(
            onClick = calc,
            enabled = enabled
        ) {
            Text(text = stringResource(id = R.string.convert))
        }
        if (result.isNotEmpty()) {
            Text(
                text = result,
                style = MaterialTheme.typography.h3
            )
        }
    }
}

@Composable
fun LengthTextField(
    temperature: State<String>,
    modifier: Modifier = Modifier,
    callback: () -> Unit,
    viewModel: LengthViewModel
) {
    TextField(
        value = temperature.value,
        onValueChange = {
            viewModel.setLength(it)
        },
        placeholder = {
            Text(text = stringResource(id = R.string.placeholder_length))
        },
        modifier = modifier,
        keyboardActions = KeyboardActions(onAny = {
            callback()
        }),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true
    )
}

@Composable
fun LengthButtonGroup(
    selected: State<Int>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    val sel = selected.value
    Row(modifier = modifier) {
        LengthRadioButton(
            selected = sel == R.string.cen,
            resId = R.string.cen,
            onClick = onClick
        )
        LengthRadioButton(
            selected = sel == R.string.inch,
            resId = R.string.inch,
            onClick = onClick,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun  LengthRadioButton(
    selected: Boolean,
    resId: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        RadioButton(
            selected = selected,
            onClick = {
                onClick(resId)
            }
        )
        Text(
            text = stringResource(resId),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}