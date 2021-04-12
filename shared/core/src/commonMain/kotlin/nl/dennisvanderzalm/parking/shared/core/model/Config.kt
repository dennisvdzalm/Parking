package nl.dennisvanderzalm.parking.shared.core.model

data class Config(
    val dataSourceConfig: DataSourceConfig
)

sealed class DataSourceConfig {

    data class Remote(val parkingApiHost: String) : DataSourceConfig()

    object Fake : DataSourceConfig()
}
