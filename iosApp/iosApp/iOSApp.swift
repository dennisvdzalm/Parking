import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        let config = Config(dataSourceConfig: DataSourceConfig.Remote(parkingApiHost: "parkeren.leiden.nl"))
        KoinKt.doInitKoin(config: config)
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
