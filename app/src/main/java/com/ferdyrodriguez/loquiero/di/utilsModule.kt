package com.ferdyrodriguez.loquiero.di

import com.ferdyrodriguez.loquiero.R
import com.ferdyrodriguez.loquiero.usecases.main.MainViewModel
import com.ferdyrodriguez.loquiero.usecases.main.ProductsAdapter
import com.ferdyrodriguez.loquiero.usecases.search.SearchProductAdapter
import com.ferdyrodriguez.loquiero.usecases.search.SearchViewModel
import com.ferdyrodriguez.loquiero.usecases.userProducts.UserProductViewModel
import com.ferdyrodriguez.loquiero.usecases.userProducts.UserProductsAdapter
import com.stripe.android.Stripe
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import pl.aprilapps.easyphotopicker.ChooserType
import pl.aprilapps.easyphotopicker.EasyImage

val utilsModule = module {

    single {
            EasyImage.Builder(get())
                .setChooserTitle("Selecciona")
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .allowMultiple(false)
                .setCopyImagesToPublicGalleryFolder(false)
                .build()
    }

    factory { (viewModel: MainViewModel) -> ProductsAdapter(viewModel) }
    factory { (viewModel: UserProductViewModel) -> UserProductsAdapter(viewModel) }
    factory { (viewModel: SearchViewModel) -> SearchProductAdapter(viewModel) }

    factory { Stripe(androidContext(), androidContext().getString(R.string.stripe_key) ) }
}