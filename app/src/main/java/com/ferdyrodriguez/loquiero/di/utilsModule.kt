package com.ferdyrodriguez.loquiero.di

import com.ferdyrodriguez.loquiero.usecases.addproduct.AddProductActivity
import com.ferdyrodriguez.loquiero.usecases.main.ProductsAdapter
import com.ferdyrodriguez.loquiero.usecases.userProducts.UserProductViewModel
import com.ferdyrodriguez.loquiero.usecases.userProducts.UserProductsAdapter
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pl.aprilapps.easyphotopicker.ChooserType
import pl.aprilapps.easyphotopicker.EasyImage

val utilsModule = module {

    scope(named<AddProductActivity>()) {
        scoped {
            EasyImage.Builder(get())
                .setChooserTitle("Selecciona")
                .setChooserType(ChooserType.CAMERA_AND_GALLERY)
                .allowMultiple(false)
                .setCopyImagesToPublicGalleryFolder(false)
                .build()
        }
    }

    factory { ProductsAdapter(get()) }
    factory { (viewModel: UserProductViewModel) -> UserProductsAdapter(viewModel) }
}