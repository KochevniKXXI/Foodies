package ru.requestdesign.test.nomad.core.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.requestdesign.test.nomad.core.designsystem.R
import ru.requestdesign.test.nomad.core.designsystem.theme.FoodiesTheme

@Composable
fun Counter(
    amount: Int,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        ElevatedButton(
            onClick = onMinusClick,
            shape = MaterialTheme.shapes.small,
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 4.dp
            ),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.size(40.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_minus),
                contentDescription = stringResource(R.string.button_minus_description)
            )
        }
        Text(text = "$amount")
        ElevatedButton(
            onClick = onPlusClick,
            shape = MaterialTheme.shapes.small,
            elevation = ButtonDefaults.elevatedButtonElevation(
                defaultElevation = 4.dp
            ),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.size(40.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = stringResource(R.string.button_plus_description)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CounterPreview() {
    FoodiesTheme {
        Counter(
            amount = 1,
            onMinusClick = {},
            onPlusClick = {}
        )
    }
}