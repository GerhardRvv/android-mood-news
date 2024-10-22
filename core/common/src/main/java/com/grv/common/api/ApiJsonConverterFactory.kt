package com.grv.common.api

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.io.StringWriter
import java.lang.reflect.Type

class ApiJsonConverterFactory(private val gson: Gson) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return GsonResponseBodyConverter<Any>(gson, type)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        val mediaType = "application/vnd.api+json".toMediaTypeOrNull()
        return if (mediaType != null) {
            GsonRequestBodyConverter<Any>(gson, type, mediaType)
        } else {
            null
        }
    }
}

class GsonRequestBodyConverter<T>(
    private val gson: Gson,
    private val type: Type,
    private val mediaType: MediaType
) : Converter<T, RequestBody> {

    @Throws(IOException::class)
    override fun convert(value: T): RequestBody {
        val adapter: TypeAdapter<T> = gson.getAdapter(TypeToken.get(type)) as TypeAdapter<T>
        val writer = StringWriter()
        val jsonWriter = gson.newJsonWriter(writer)
        adapter.write(jsonWriter, value)
        jsonWriter.close()
        return writer.toString().toByteArray(Charsets.UTF_8)
            .toRequestBody(mediaType, 0)
    }
}

class GsonResponseBodyConverter<T>(
    private val gson: Gson,
    private val type: Type
) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val adapter: TypeAdapter<*> = gson.getAdapter(TypeToken.get(type))
        val jsonReader = gson.newJsonReader(value.charStream())
        return adapter.read(jsonReader) as? T
    }
}