package com.ferdyrodriguez.loquiero.di

import com.ferdyrodriguez.loquiero.usecases.addproduct.AddProductActivity
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
}