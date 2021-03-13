package com.fukuhara.common.arch

/*
    This Mapper structure will be used to transform VO objects that were received from the Rest Call
    into a Model object, that the View will consume
 */
interface ModelMapper<IN, OUT> {
    fun transform(voData: IN) : OUT
}