package com.example.dragonballwiki.charactersdetail.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dragonballwiki.R
import com.example.dragonballwiki.charactersdetail.ui.model.CharacterDetailVO
import com.example.dragonballwiki.charactersdetail.ui.model.Transformation
import com.example.dragonballwiki.charactersdetail.ui.viewmodel.CharacterDetailViewModel
import com.example.dragonballwiki.core.isTrue
import com.example.dragonballwiki.core.ui.CacheSeverity
import com.example.dragonballwiki.core.ui.CacheInfoVO
import com.example.dragonballwiki.core.ui.LoadingState
import com.example.dragonballwiki.dragonlist.ui.compose.ImageCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.KiTransformationCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.NameCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.TextBreedAndGenreCharacter
import com.example.dragonballwiki.dragonlist.ui.compose.TextOtherData
import com.example.dragonballwiki.dragonlist.ui.screen.LoginBall
import com.example.dragonballwiki.ui.components.ErrorStateCompose
import com.example.dragonballwiki.ui.theme.CardSizes
import com.example.dragonballwiki.ui.theme.ImageSizes
import com.example.dragonballwiki.ui.theme.Spacing
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    characterDetailViewModel: CharacterDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit = {}
) {
    val state = characterDetailViewModel.state

    when {
        state.error != null -> {
            ErrorStateCompose(
                title = state.error.asString(),
                description = stringResource(R.string.error_loading_character),
                onRetryClick = { /* Character will reload on init */ }
            )
        }

        state.loadingState == LoadingState.Loading -> {
            LoginBall(stringResource(R.string.character_detail_label_loading))
        }

        state.characterDetail != null -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = state.characterDetail.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(R.string.content_description_back),
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            ) { paddingValues ->
                CharacterDetail(
                    characterDetailVO = state.characterDetail,
                    cacheInfo = state.cacheInfo,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun CharacterDetail(
    characterDetailVO: CharacterDetailVO,
    cacheInfo: CacheInfoVO?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Hero Section with Image
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.medium)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                ImageCharacter(
                    characterDetailVO.image,
                    Modifier
                        .fillMaxWidth()
                        .size(ImageSizes.characterDetailHero)
                        .padding(Spacing.medium)
                )
                
                NameCharacter(
                    nameCharacter = characterDetailVO.name,
                    isDetail = false,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = Spacing.small)
                )
            }
        }

        // Stats Section
        Text(
            text = stringResource(R.string.section_stats),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                start = Spacing.medium,
                end = Spacing.medium,
                top = Spacing.medium,
                bottom = Spacing.small
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.medium),
            horizontalArrangement = Arrangement.spacedBy(Spacing.small)
        ) {
            StatCard(
                title = stringResource(R.string.stat_race),
                value = characterDetailVO.race,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = stringResource(R.string.stat_gender),
                value = characterDetailVO.gender,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.medium, vertical = Spacing.small),
            horizontalArrangement = Arrangement.spacedBy(Spacing.small)
        ) {
            StatCard(
                title = stringResource(R.string.stat_base_ki),
                value = characterDetailVO.ki,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                title = stringResource(R.string.stat_max_ki),
                value = characterDetailVO.maxKi,
                modifier = Modifier.weight(1f)
            )
        }

        StatCard(
            title = stringResource(R.string.stat_affiliation),
            value = characterDetailVO.affiliation,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.medium)
        )

        // Description Section
        if (characterDetailVO.description.isNotEmpty()) {
            Text(
                text = stringResource(R.string.section_description),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = Spacing.medium,
                    end = Spacing.medium,
                    top = Spacing.large,
                    bottom = Spacing.small
                )
            )
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.medium)
            ) {
                Text(
                    text = characterDetailVO.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(Spacing.medium)
                )
            }
        }

        // Transformations Section
        if (characterDetailVO.transformations.isNotEmpty()) {
            Text(
                text = stringResource(R.string.character_detail_label_transformations),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = Spacing.medium,
                    end = Spacing.medium,
                    top = Spacing.large,
                    bottom = Spacing.small
                )
            )
            
            LazyRow(
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = Spacing.medium),
                horizontalArrangement = Arrangement.spacedBy(Spacing.small)
            ) {
                items(characterDetailVO.transformations, key = { it.id }) {
                    ItemTransformation(it)
                }
            }
        }
        
        // Cache Status Banner - using pre-calculated cacheInfo from ViewModel
        cacheInfo?.let { info ->
            CacheStatusBanner(
                cacheInfo = info,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.medium)
            )
        }
        
        Spacer(modifier = Modifier.size(Spacing.large))
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.medium)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.size(Spacing.extraSmall))
            Text(
                text = value.ifEmpty { stringResource(R.string.value_unknown) },
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun CacheStatusBanner(
    cacheInfo: CacheInfoVO,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (cacheInfo.severity) {
        CacheSeverity.FRESH -> MaterialTheme.colorScheme.primaryContainer
        CacheSeverity.RECENT -> MaterialTheme.colorScheme.tertiaryContainer
        CacheSeverity.OLD -> MaterialTheme.colorScheme.secondaryContainer
        CacheSeverity.STALE -> MaterialTheme.colorScheme.errorContainer
    }
    
    Surface(
        modifier = modifier.clip(RoundedCornerShape(Spacing.small)),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.medium),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = cacheInfo.emoji, fontSize = 16.sp)
            Spacer(modifier = Modifier.size(Spacing.small))
            Text(
                text = cacheInfo.message.asString(),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun ItemTransformation(item: Transformation) {
    Card(
        modifier = Modifier.size(width = CardSizes.transformationWidth, height = CardSizes.transformationHeight)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(Spacing.small),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ImageCharacter(
                urlImage = item.image,
                modifier = Modifier
                    .size(ImageSizes.transformationThumbnail)
            )
            Spacer(modifier = Modifier.size(Spacing.small))
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = Spacing.extraSmall)
            )
            Text(
                text = stringResource(R.string.stat_ki, item.ki),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = Spacing.extraSmall)
            )
        }
    }
}

