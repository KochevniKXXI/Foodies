@file:OptIn(ExperimentalMaterial3Api::class)

package ru.requestdesign.test.nomad.feature.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.requestdesign.test.nomad.core.designsystem.components.Counter
import ru.requestdesign.test.nomad.core.designsystem.theme.FoodiesTheme
import ru.requestdesign.test.nomad.core.model.Category
import ru.requestdesign.test.nomad.core.model.Product
import java.math.RoundingMode
import java.text.NumberFormat

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    viewModel: CatalogViewModel = viewModel()
) {
    val catalogUiState by viewModel.catalogUiState.collectAsState()
    val categoriesUiState by viewModel.categoriesUiState.collectAsState()
    Scaffold(
        topBar = {
            CatalogTopAppBar()
        },
        bottomBar = {

        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            CategoriesChips(
                uiState = categoriesUiState,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
            Catalog(
                uiState = catalogUiState,
                onAddClick = { viewModel.addProductInCart(it) },
                onRemoveClick = { viewModel.removeProductFromCart(it) },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            )
        }
    }
}

@Composable
private fun CatalogTopAppBar(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = stringResource(R.string.filter_button_description)
                )
            }
        },
        actions = {
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = stringResource(R.string.search_button_description)
                )
            }
        },
        modifier = modifier
    )
}

@Composable
private fun CategoriesChips(
    uiState: UiState<List<Category>>,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        UiState.Loading -> {}
        UiState.Error -> {}
        UiState.Empty -> {}
        is UiState.Success -> {
            val categoriesList = uiState.data
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier
            ) {
                itemsIndexed(categoriesList) { index, category ->
                    // FIXME: Убрать обводку
                    FilterChip(
                        selected = index == 0,
                        onClick = { /*TODO*/ },
                        label = {
                            Text(
                                text = category.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            labelColor = MaterialTheme.colorScheme.onSurface,
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.surface
                        ),
                        modifier = Modifier
                            .height(40.dp)
                            .padding(start = if (index == 0) 16.dp else 0.dp)
                    )
                }
            }
        }
    }

}

@Composable
private fun Catalog(
    uiState: UiState<Map<Product, Int>>,
    onAddClick: (Product) -> Unit,
    onRemoveClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        UiState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
        UiState.Error -> {}
        UiState.Empty -> {}
        is UiState.Success -> {
            val productsMap = uiState.data
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier
            ) {
                items(productsMap.keys.toList()) { product ->
                    ProductCard(
                        product = product,
                        quantityInCart = productsMap.getOrDefault(product, 0),
                        onAddClick = onAddClick,
                        onRemoveClick = onRemoveClick
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    quantityInCart: Int,
    onAddClick: (Product) -> Unit,
    onRemoveClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    // FIXME: Вписать картинку
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.food),
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = 12.dp)
            )
            product.priceOld?.let {
                Image(
                    painter = painterResource(id = R.drawable.ic_discount),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp)
                )
            }
        }
        Text(
            text = product.name,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 4.dp)
        )
        Text(
            text = "${product.measure} ${product.measureUnit}",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        if (quantityInCart == 0) {
            ElevatedButton(
                onClick = { onAddClick(product) },
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 4.dp
                ),
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = product.priceCurrent.formatAsPrice(),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                product.priceOld?.let { priceOld ->
                    Text(
                        text = priceOld.formatAsPrice(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textDecoration = TextDecoration.LineThrough,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        } else {
            Counter(
                amount = quantityInCart,
                onMinusClick = { onRemoveClick(product) },
                onPlusClick = { onAddClick(product) },
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@Preview
@Composable
private fun CatalogTopAppBarPreview() {
    FoodiesTheme {
        CatalogTopAppBar()
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoriesTabsPreview() {
    FoodiesTheme {
        CategoriesChips(
            uiState = UiState.Success(
                listOf(
                    Category(name = "Роллы"),
                    Category(name = "Суши"),
                    Category(name = "Наборы"),
                    Category(name = "Горячие блюда"),
                    Category(name = "Супы"),
                    Category(name = "Десерты"),
                )
            )
        )
    }
}

@Preview
@Composable
private fun ProductCardPreview() {
    FoodiesTheme {
        ProductCard(
            product = Product(
                name = "Том Ям",
                measure = 500,
                measureUnit = "г",
                priceCurrent = 72000
            ),
            quantityInCart = 0,
            onAddClick = {},
            onRemoveClick = {}
        )
    }
}

@Preview
@Composable
private fun CatalogPreview() {
    FoodiesTheme {
        Catalog(
            uiState = UiState.Success(
                mapOf(
                    Product(
                        name = "Том Ям",
                        priceCurrent = 72000,
                        priceOld = 80000,
                        measure = 500,
                        measureUnit = "г"
                    ) to 2,
                    *List(5) {
                        Product(
                            name = "Название блюда $it",
                            priceCurrent = 48000,
                            measure = 500,
                            measureUnit = "г"
                        ) to 0
                    }.toTypedArray()
                )
            ),
            onAddClick = {},
            onRemoveClick = {}
        )
    }
}

private fun Int.formatAsPrice(): String {
    val formatter = NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 0
        roundingMode = RoundingMode.CEILING
    }
    return formatter.format(this / 100.00)
}