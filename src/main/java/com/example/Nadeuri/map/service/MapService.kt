package com.example.Nadeuri.map.service

import com.example.Nadeuri.map.dto.MapResponseDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Service
class MapService(
    private val webClientBuilder: WebClient.Builder,
    @Value("\${kakao.api.key}") private val apiKey: String,
    @Value("\${kakao.api.url}") private val apiUrl: String
) {
    private val webClient: WebClient = webClientBuilder.baseUrl(apiUrl).build()

    suspend fun getCoordinates(query: String): MapResponseDTO {
        val response: MapResponseDTO = webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/v2/local/search/address.json")
                    .queryParam("query", query)
                    .build()
            }
            .header("Authorization", "KakaoAK $apiKey")
            .retrieve()
            .onStatus({ status -> status.isError }) { response ->
                throw RuntimeException("카카오 맵 API에서 데이터를 가져오는 데 실패했습니다. 상태 코드: ${response.statusCode()}")
            }
            .awaitBody()

        // 예외 발생시 적절하게 처리하거나, 기본 값을 설정합니다.
        requireNotNull(response) { "카카오 맵 API에서 빈 응답을 받았습니다." }

        return response
    }

}
