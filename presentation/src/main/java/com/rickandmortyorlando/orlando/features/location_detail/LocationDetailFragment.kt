package com.rickandmortyorlando.orlando.features.location_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import com.example.domain.models.remote.character.Character
import com.example.domain.models.remote.character.Location
import com.example.domain.models.remote.character.getPairInfoLocation
import com.example.domain.models.remote.location.toLocation
import com.rickandmortyorlando.orlando.MainActivity
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.databinding.FragmentLocationDetailBinding
import com.rickandmortyorlando.orlando.features.base.BaseFragment
import com.rickandmortyorlando.orlando.features.characters.CharacterAdapter
import com.rickandmortyorlando.orlando.features.characters.CharacterViewModel
import com.rickandmortyorlando.orlando.features.characters_detail.CharacterDetailViewModel
import com.rickandmortyorlando.orlando.features.extensions.observeApiResultGeneric
import com.rickandmortyorlando.orlando.features.locations.LocationsViewModel
import com.rickandmortyorlando.orlando.utils.getListOfNumbersFromUrlWithPrefix
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LocationDetailFragment :
    BaseFragment<FragmentLocationDetailBinding>(R.layout.fragment_location_detail) {


    private val locationViewModel: LocationsViewModel by viewModels()
    private val characterViewModel: CharacterViewModel by viewModels()
    private val characterDetailViewModel: CharacterDetailViewModel by viewModels()
    private var adapter: CharacterAdapter? = null
    private val args: LocationDetailFragmentArgs by navArgs()
    private var idsOfCharacters = ""


    override fun setUpUi() {
        with(binding) {
            adapter = CharacterAdapter(clickOnCharacter = { clickOnCharacter(it) })
            recycler.adapter = adapter
            locationViewModel.getSingleLocation(args.idLocation)
        }
    }


    override fun configureToolbar() = MainActivity.ToolbarConfiguration(
        toolbarTitle = getString(R.string.location),
        showToolbar = true
    )

    override fun observerViewModel() {
        super.observerViewModel()
        observeApiResultGeneric(
            locationViewModel.singleLocationResponse,
            hasProgressTheView = true,
            shouldCloseTheViewOnApiError = true
        ) {
            with(binding) {
                locationInfo.setContent {
                    ItemInfoLocation(location = it.toLocation())
                }
            }
            idsOfCharacters = getListOfIdsOfCharacters(it.residents)
            if (isSingleCharacter()) {
                characterDetailViewModel.getCharacter(idsOfCharacters)
            } else {
                characterViewModel.getManyCharacters(idsOfCharacters)
            }
        }
        observeApiResultGeneric(
            characterViewModel.manyCharactersResponse,
            hasProgressTheView = true,
            shouldCloseTheViewOnApiError = true
        ) { characters ->
            showLocationInfo()
            lifecycleScope.launch {
                adapter?.submitData(PagingData.from(characters))
            }
        }
        observeApiResultGeneric(
            characterDetailViewModel.characterResponse,
            hasProgressTheView = true,
            shouldCloseTheViewOnApiError = true
        ) { character ->
            showLocationInfo()
            lifecycleScope.launch {
                adapter?.submitData(PagingData.from(listOf(character)))
            }
        }
    }

    private fun showLocationInfo() {
//        binding.locationInfo.visible()
    }

    private fun isSingleCharacter() = !idsOfCharacters.contains(",")

    private fun getListOfIdsOfCharacters(idsInUrl: List<String>): String {
        return getListOfNumbersFromUrlWithPrefix(
            idsInUrl,
            "character"
        )
    }

    private fun clickOnCharacter(character: Character) {
        findNavController().navigate(
            LocationDetailFragmentDirections.navigationToCharacterDetail(
                character.id
            )
        )
    }

}


@Composable
fun ItemInfoLocation(
    modifier: Modifier = Modifier,
    location: Location
) {
    Column(Modifier.fillMaxWidth()) {
        location.getPairInfoLocation().forEach { infoItemLocation ->
            Card(
                modifier
                    .padding(8.dp)
                    .background(Color.White)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(8.dp),
                        text = infoItemLocation.first
                    )
                    Text(
                        modifier =
                        Modifier.padding(8.dp),
                        text = infoItemLocation.second
                    )
                }
            }
        }
    }
}