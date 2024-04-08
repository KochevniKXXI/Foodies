package ru.requestdesign.test.nomad.feature.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.requestdesign.test.nomad.core.designsystem.components.BottomBarButton
import ru.requestdesign.test.nomad.core.designsystem.components.Counter
import ru.requestdesign.test.nomad.core.designsystem.components.EmptyScreen
import ru.requestdesign.test.nomad.core.designsystem.components.ErrorScreen
import ru.requestdesign.test.nomad.core.designsystem.theme.FoodiesTheme
import ru.requestdesign.test.nomad.core.designsystem.utils.formatAsPriceString
import ru.requestdesign.test.nomad.core.model.Product
import ru.requestdesign.test.nomad.core.designsystem.R as designsystemR

@Composable
internal fun ProductRoute(
    shouldShowExpandedLayout: Boolean,
    onUpClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ProductScreen(
        uiState = uiState,
        shouldShowExpandedLayout = shouldShowExpandedLayout,
        onAddProductClick = viewModel::addProductInCart,
        onRemoveProductClick = viewModel::removeProductFromCart,
        onUpClick = onUpClick,
        modifier = modifier
    )
}

@Composable
private fun ProductScreen(
    uiState: ProductUiState,
    shouldShowExpandedLayout: Boolean,
    onAddProductClick: (Product) -> Unit,
    onRemoveProductClick: (Product) -> Unit,
    onUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ProductAppBar(onUpClick)
        },
        bottomBar = {
            if (uiState is ProductUiState.Success) {
                val product = uiState.productWithQuantity.first
                val quantity = uiState.productWithQuantity.second
                if (quantity == 0) {
                    BottomBarButton(
                        onClick = { onAddProductClick(product) },
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(id = designsystemR.dimen.bottom_app_bar_margin_horizontal),
                            vertical = dimensionResource(id = designsystemR.dimen.bottom_app_bar_margin_vertical)
                        )
                    ) {
                        Text(text = stringResource(
                            R.string.button_in_cart_text,
                            product.priceCurrent.formatAsPriceString()
                        ))
                    }
                } else {
                    Counter(
                        amount = quantity,
                        onMinusClick = { onRemoveProductClick(product) },
                        onPlusClick = { onAddProductClick(product) },
                        modifier = Modifier
                            .padding(
                                horizontal = dimensionResource(id = designsystemR.dimen.bottom_app_bar_margin_horizontal),
                                vertical = dimensionResource(id = designsystemR.dimen.bottom_app_bar_margin_vertical)
                            )
                            .fillMaxWidth()
                    )
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Content(
            uiState = uiState,
            shouldShowExpandedLayout = shouldShowExpandedLayout,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ProductAppBar(
    onUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            SmallFloatingActionButton(
                onClick = onUpClick,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                shape = CircleShape,
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.navigation_icon_padding_start))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(designsystemR.string.button_up_description)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        modifier = modifier
    )
}

@Composable
private fun Content(
    uiState: ProductUiState,
    shouldShowExpandedLayout: Boolean,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        ProductUiState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

        is ProductUiState.Error -> { ErrorScreen(message = uiState.error.message ?: "") }

        ProductUiState.Empty -> {
            EmptyScreen(message = stringResource(R.string.empty_screen_message))
        }

        is ProductUiState.Success -> {
            val product = uiState.productWithQuantity.first
            if (shouldShowExpandedLayout) {
                Row(modifier = modifier) {
                    Image(
                        painter = painterResource(id = ru.requestdesign.test.nomad.core.designsystem.R.drawable.tom_yum),
                        contentDescription = null
                    )
                    ProductDescription(
                        product = product,
                        modifier = Modifier
                            .padding(top = dimensionResource(id = R.dimen.product_description_padding_top))
                            .verticalScroll(rememberScrollState())
                    )
                }
            } else {
                Column(
                    modifier = modifier.verticalScroll(rememberScrollState())
                ) {
                    Image(
                        painter = painterResource(id = ru.requestdesign.test.nomad.core.designsystem.R.drawable.tom_yum),
                        contentDescription = null
                    )
                    ProductDescription(product = product)
                }
            }
        }
    }
}

@Composable
private fun ProductDescription(
    product: Product,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = product.name,
            fontSize = 34.sp,
            lineHeight = 36.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.product_name_padding_horizontal))
                .padding(bottom = dimensionResource(id = R.dimen.bottom_app_bar_margin_bottom))
        )
        Text(
            text = product.description,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.product_name_padding_horizontal))
                .padding(bottom = dimensionResource(id = R.dimen.product_description_padding_bottom))
        )
        HorizontalDivider()
        ParameterListItem(
            parameter = "Вес",
            value = "${product.measure} ${product.measureUnit}"
        )
        ParameterListItem(
            parameter = "Энерг. ценность",
            value = "${product.energyPer100Grams} ккал"
        )
        ParameterListItem(
            parameter = "Белки",
            value = "${product.proteinsPer100Grams} г"
        )
        ParameterListItem(
            parameter = "Жиры",
            value = "${product.fatsPer100Grams} г"
        )
        ParameterListItem(
            parameter = "Углеводы",
            value = "${product.carbohydratesPer100Grams} г"
        )
    }
}

@Composable
private fun ParameterListItem(
    parameter: String,
    value: String,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
            Text(
                text = parameter,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        colors = ListItemDefaults.colors(
            headlineColor = MaterialTheme.colorScheme.onSurfaceVariant,
            trailingIconColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
    )
    HorizontalDivider()
}

@Preview
@Composable
private fun ProductScreenPreview() {
    FoodiesTheme {
        ProductScreen(
            uiState = ProductUiState.Success(
                Product(
                    name = "Том Ям",
                    description = "Кокосовое молоко, кальмары, креветки,\n" +
                            "помидоры черри, грибы вешанки",
                    priceCurrent = 72000,
                    measure = 400,
                    measureUnit = "г",
                    energyPer100Grams = 198.9,
                    proteinsPer100Grams = 10.0,
                    fatsPer100Grams = 8.5,
                    carbohydratesPer100Grams = 19.7
                ) to 0
            ),
            shouldShowExpandedLayout = false,
            onAddProductClick = {},
            onRemoveProductClick = {},
            onUpClick = {}
        )
    }
}