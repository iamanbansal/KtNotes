import UIKit
import shared
import SwiftUI

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        KoinIOSKt.doInitKoin()

        let viewController = UIHostingController(rootView: ContentView())

                self.window = UIWindow(frame: UIScreen.main.bounds)
                self.window?.rootViewController = viewController
                self.window?.makeKeyAndVisible()

        return true
    }
    
    
}
