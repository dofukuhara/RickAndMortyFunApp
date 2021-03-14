package com.fukuhara.rickandmortyfunapp.feature.location.business.vo

import com.fukuhara.common.arch.ModelMapper
import com.fukuhara.common.ext.getPageIndex
import com.fukuhara.rickandmortyfunapp.feature.location.business.LocationInfoModel
import com.fukuhara.rickandmortyfunapp.feature.location.business.LocationModel
import com.fukuhara.rickandmortyfunapp.feature.location.business.LocationResultModel

class LocationModelMapper : ModelMapper<LocationVo, LocationModel> {

    override fun transform(voData: LocationVo): LocationModel {
        val nextIndex = voData.info.next?.getPageIndex("location")
        val previousIndex = voData.info.prev?.getPageIndex("location")

        val locationInfoModel = LocationInfoModel(
            voData.info.count,
            voData.info.pages,
            nextIndex,
            previousIndex
        )

        val locationResultList = voData.results.map {
            LocationResultModel(
                it.id, it.name, it.type, it.dimension, it.residents, it.url, it.created
            )
        }

        return LocationModel(locationInfoModel, locationResultList)
    }
}