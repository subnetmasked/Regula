package org.regula.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import org.regula.app.R
import org.regula.app.ui.theme.RegulaGold
import org.regula.app.ui.theme.RegulaGoldDim
import org.regula.app.ui.theme.RegulaGoldLight
import org.regula.app.ui.theme.RegulaIvoryDim
import org.regula.app.ui.theme.RegulaSurfaceElevated
import org.regula.app.ui.theme.SearchBarShape

@Composable
fun FancySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(R.string.search_hint),
    onSearch: (() -> Unit)? = null,
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = RegulaIvoryDim,
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = RegulaGold,
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.clear_search),
                        tint = RegulaGoldDim,
                    )
                }
            }
        },
        singleLine = true,
        shape = SearchBarShape,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = RegulaSurfaceElevated,
            unfocusedContainerColor = RegulaSurfaceElevated.copy(alpha = 0.85f),
            disabledContainerColor = RegulaSurfaceElevated,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = RegulaGold,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedLeadingIconColor = RegulaGoldLight,
            unfocusedLeadingIconColor = RegulaGoldDim,
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch?.invoke() },
        ),
    )
}
