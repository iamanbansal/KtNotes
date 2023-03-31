import SwiftUI
import shared
import Combine


struct ContentView: View {
    @State private var isLoggedIn = true
       var body: some View {
           NavigationView {
               VStack {
                   if isLoggedIn {
                       NoteListScreen(onLogout: {isLoggedIn = false})
                   }
                   else {
                       LoginView(onLogin: {isLoggedIn = true})
                   }
                   
               }
               .transition(.opacity)
               .animation(.easeInOut)
           }
       }
}


