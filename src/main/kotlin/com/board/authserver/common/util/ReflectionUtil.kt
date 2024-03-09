package com.board.authserver.common.util

import kotlin.reflect.KClass

/**
 * @author jinwook.kim
 * @since 3/9/24
 */
object ReflectionUtil {

    fun <T : Any> mapToObject(map: Map<String, Any>, clazz: KClass<T>) : T {
        //Get default constructor
        val constructor = clazz.constructors.first()

        //Map constructor parameters to map values
        val args = constructor
            .parameters
            .map { it to map.get(it.name) }
            .toMap()

        //return object from constructor call
        return constructor.callBy(args)
    }
}