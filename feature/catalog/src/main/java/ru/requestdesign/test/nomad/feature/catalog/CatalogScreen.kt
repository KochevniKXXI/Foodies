@file:OptIn(ExperimentalMaterial3Api::class)

package ru.requestdesign.test.nomad.feature.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.requestdesign.test.nomad.core.designsystem.components.BottomBarButton
import ru.requestdesign.test.nomad.core.designsystem.components.Counter
import ru.requestdesign.test.nomad.core.designsystem.components.EmptyScreen
import ru.requestdesign.test.nomad.core.designsystem.components.ErrorScreen
import ru.requestdesign.test.nomad.core.designsystem.components.LoadingScreen
import ru.requestdesign.test.nomad.core.designsystem.utils.formatAsPriceString
import ru.requestdesign.test.nomad.core.model.Category
import ru.requestdesign.test.nomad.core.model.Product
import ru.requestdesign.test.nomad.core.designsystem.R as designsystemR

@Composable
internal fun CatalogRoute(
    columns: GridCells,
    onProductClick: (Int) -> Unit,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CatalogViewModel = hiltViewModel()
) {
    val catalogUiState by viewModel.catalogUiState.collectAsStateWithLifecycle()
    val categoriesUiState by viewModel.categoriesUiState.collectAsStateWithLifecycle()
    val currentCategory = viewModel.currentCategory.value
    CatalogScreen(
        catalogUiState = catalogUiState,
        categoriesUiState = categoriesUiState,
        currentCategory = currentCategory,
        columns = columns,
        onAddProductClick = viewModel::addProductInCart,
        onRemoveProductClick = viewModel::removeProductFromCart,
        onCategoryClick = viewModel::updateCurrentCategory,
        onFilterClick = { /*TODO*/ },
        onSearchClick = { /*TODO*/ },
        onProductClick = onProductClick,
        onCartClick = onCartClick,
        modifier = modifier
    )
}

@Composable
private fun CatalogScreen(
    catalogUiState: CatalogUiState,
    categoriesUiState: CategoriesUiState,
    currentCategory: Category?,
    columns: GridCells,
    onAddProductClick: (Product) -> Unit,
    onRemoveProductClick: (Product) -> Unit,
    onCategoryClick: (Category) -> Unit,
    onFilterClick: () -> Unit,
    onSearchClick: () -> Unit,
    onProductClick: (Int) -> Unit,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CatalogTopAppBar(
                onFilterClick = onFilterClick,
                onSearchClick = onSearchClick,
            )
        },
        bottomBar = {
            CartButton(
                uiState = catalogUiState,
                onClick = onCartClick,
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(id = designsystemR.dimen.bottom_app_bar_margin_horizontal),
                        vertical = dimensionResource(id = designsystemR.dimen.bottom_app_bar_margin_vertical)
                    )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            CategoriesChips(
                uiState = categoriesUiState,
                currentCategory = currentCategory,
                onCategoryClick = onCategoryClick,
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.categories_chips_padding_top))
            )
            Catalog(
                uiState = catalogUiState,
                currentCategory = currentCategory,
                columns = columns,
                onCardClick = onProductClick,
                onAddClick = onAddProductClick,
                onRemoveClick = onRemoveProductClick,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.catalog_padding_horizontal))
                    .padding(top = dimensionResource(id = R.dimen.catalog_padding_top))
            )
        }
    }
}

@Composable
private fun CatalogTopAppBar(
    onFilterClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Image(
                painter = painterResource(id = designsystemR.drawable.logo),
                contentDescription = null
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onFilterClick
            ) {
                Icon(
                    painter = painterResource(id = designsystemR.drawable.ic_filter),
                    contentDescription = stringResource(designsystemR.string.filter_button_description)
                )
            }
        },
        actions = {
            IconButton(
                onClick = onSearchClick
            ) {
                Icon(
                    painter = painterResource(id = designsystemR.drawable.ic_search),
                    contentDescription = stringResource(designsystemR.string.search_button_description)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
    )
}

@Composable
private fun CartButton(
    uiState: CatalogUiState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState is CatalogUiState.Success) {
        val sumCart = uiState.cart.map { it.key.priceCurrent * it.value }.sum()
        if (sumCart > 0) {
            BottomBarButton(
                onClick = onClick,
                modifier = modifier
            ) {
                Icon(
                    painter = painterResource(id = designsystemR.drawable.ic_cart),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = dimensionResource(id = R.dimen.icon_cart_on_button_padding_end))
                        .size(dimensionResource(id = R.dimen.icon_cart_on_button_size))
                )
                Text(text = sumCart.formatAsPriceString())
            }
        }
    }
}

@Composable
private fun CategoriesChips(
    uiState: CategoriesUiState,
    currentCategory: Category?,
    onCategoryClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        CategoriesUiState.Loading -> { /*TODO*/
        }

        CategoriesUiState.Error -> { /*TODO*/
        }

        CategoriesUiState.Empty -> { /*TODO*/
        }

        is CategoriesUiState.Success -> {
            val categoriesList = uiState.categories
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.space_between_chips)),
                modifier = modifier
            ) {
                itemsIndexed(categoriesList) { index, category ->
                    // FIXME: Убрать обводку
                    FilterChip(
                        selected = category == currentCategory,
                        onClick = { onCategoryClick(category) },
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
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = false,
                            selected = false,
                            borderColor = Color.Transparent,
                            borderWidth = dimensionResource(id = designsystemR.dimen.zero)
                        ),
                        elevation = FilterChipDefaults.filterChipElevation(
                            elevation = dimensionResource(
                                id = designsystemR.dimen.zero
                            )
                        ),
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen.chip_height))
                            .padding(
                                start = if (index == 0) dimensionResource(id = R.dimen.start_chip_padding_start)
                                else dimensionResource(id = designsystemR.dimen.zero)
                            )
                    )
                }
            }
        }
    }

}

@Composable
private fun Catalog(
    uiState: CatalogUiState,
    currentCategory: Category?,
    columns: GridCells,
    onCardClick: (Int) -> Unit,
    onAddClick: (Product) -> Unit,
    onRemoveClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        CatalogUiState.Loading -> {
            LoadingScreen()
        }

        is CatalogUiState.Error -> { ErrorScreen(message = uiState.error.message ?: "") }
        CatalogUiState.Empty -> {
            EmptyScreen(message = stringResource(R.string.empty_screen_message))
        }

        is CatalogUiState.Success -> {
            val productsMap = currentCategory?.let { category ->
                uiState.cart.filterKeys { it.categoryId == category.id }
            } ?: uiState.cart
            if (productsMap.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = columns,
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.space_between_grid_items)),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.space_between_grid_items)),
                    modifier = modifier
                ) {
                    items(productsMap.keys.toList()) { product ->
                        ProductCard(
                            product = product,
                            quantityInCart = productsMap.getOrDefault(product, 0),
                            onClick = onCardClick,
                            onAddClick = onAddClick,
                            onRemoveClick = onRemoveClick
                        )
                    }
                }
            } else {
                EmptyScreen(message = stringResource(R.string.empty_filters_screen_message))
            }
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    quantityInCart: Int,
    onClick: (Int) -> Unit,
    onAddClick: (Product) -> Unit,
    onRemoveClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .clickable { onClick(product.id) }
    ) {
        Box {
            Image(
                painter = painterResource(id = designsystemR.drawable.tom_yum),
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = dimensionResource(id = R.dimen.image_padding_bottom))
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.space_between_tag_items)),
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.tags_row_padding),
                        top = dimensionResource(id = R.dimen.tags_row_padding)
                    )
            ) {
                product.priceOld?.let {
                    Image(
                        painter = painterResource(id = designsystemR.drawable.ic_discount),
                        contentDescription = null
                    )
                }
                if (product.tagIds.contains(2)) {
                    Image(
                        painter = painterResource(id = designsystemR.drawable.ic_without_meat),
                        contentDescription = null
                    )
                }
                if (product.tagIds.contains(4)) {
                    Image(
                        painter = painterResource(id = designsystemR.drawable.ic_spicy),
                        contentDescription = null
                    )
                }
            }
        }
        Text(
            text = product.name,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.product_name_padding_horizontal))
                .padding(bottom = dimensionResource(id = R.dimen.product_name_padding_bottom))
        )
        Text(
            text = "${product.measure} ${product.measureUnit}",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.product_name_padding_horizontal))
        )
        if (quantityInCart == 0) {
            ElevatedButton(
                onClick = {
                    onAddClick(product)
                },
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = dimensionResource(id = R.dimen.elevation_price_button)
                ),
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_price_button))
                    .fillMaxWidth()
            ) {
                Text(
                    text = product.priceCurrent.formatAsPriceString(),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium
                )
                product.priceOld?.let { priceOld ->
                    Text(
                        text = priceOld.formatAsPriceString(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textDecoration = TextDecoration.LineThrough,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.price_old_padding))
                    )
                }
            }
        } else {
            Counter(
                amount = quantityInCart,
                onMinusClick = { onRemoveClick(product) },
                onPlusClick = { onAddClick(product) },
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.counter_padding))
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun CatalogScreenPreview() {
    CatalogScreen(
        catalogUiState = CatalogUiState.Success(
            mapOf(
                Product(
                    name = "Том Ям",
                    categoryId = 1,
                    priceCurrent = 72000,
                    priceOld = 80000,
                    measure = 500,
                    measureUnit = "г"
                ) to 2,
                *List(5) {
                    Product(
                        name = "Название блюда $it",
                        categoryId = 1,
                        priceCurrent = 48000,
                        measure = 500,
                        measureUnit = "г"
                    ) to 0
                }.toTypedArray()
            )
        ),
        categoriesUiState = CategoriesUiState.Success(
            listOf(
                Category(id = 1, name = "Роллы"),
                Category(name = "Суши"),
                Category(name = "Наборы"),
                Category(name = "Горячие блюда"),
                Category(name = "Супы"),
                Category(name = "Десерты"),
            )
        ),
        currentCategory = Category(id = 1, name = "Роллы"),
        columns = GridCells.Fixed(2),
        onAddProductClick = {},
        onRemoveProductClick = {},
        onCategoryClick = {},
        onFilterClick = {},
        onSearchClick = {},
        onProductClick = {},
        onCartClick = {}
    )
}