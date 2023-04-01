//
//  RegistrationView.swift
//  iosApp
//
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import Combine

struct LoginView: View {
    var onLogin:()->Void
    
    @StateObject var viewModel = ObservableAuthModel()
    @State private var name = ""
    @State private var email = ""
    @State private var password = ""
    @State private var isLogin = false
    @State private var title = "Signup"
    
    var body: some View {
            VStack {
                Spacer()
                if !isLogin {
                    TextField("Name", text: $name)
                }
                
                TextField("Email", text: $email)
                SecureField("Password", text: $password)

                if isLogin {
                    Button("Login") {
                        viewModel.login(email: email, password: password)
                    }
                } else {
                    Button("Signup") {
                        viewModel.register(name: name, email: email, password: password)
                    }
                }
                Spacer()

                if isLogin {
                    HStack {
                        Text("Don't have an account?")
                        Button("Signup") {
                            isLogin = false
                        }.foregroundColor(.blue)
                    }
                } else {
                    HStack {
                        Text("Already have an account?")
                        Button("Login") {
                            isLogin = true
                        }.foregroundColor(.blue)
                    }
                }

            }.padding()
            .onAppear(perform: {
                self.viewModel.activate()
                self.title = isLogin ? "Login" : "Signup"
            })
            .onDisappear(perform: {
                self.viewModel.deactivate()
            })
            .onChange(of: viewModel.uiState?.isLoggedIn, perform: { isLoggedIn in
                       if isLoggedIn == true {
                           onLogin()
                       }
                   })
        
            .onChange(of: isLogin, perform: { isLogin in
                self.title = isLogin ? "Login" : "Signup"
                })
        
            .navigationBarTitleDisplayMode(.inline)
            .navigationBarTitle(title)
        
        }

}

class ObservableAuthModel: ObservableObject {
    
    private var viewmodel: AuthCallbackViewModel?
    private var cancellables = [AnyCancellable]()
    
    @Published var error: String?
    @Published var uiState:LoginUiState?
    
    func activate(){
        let viewmodel  =  KotlinDependencies.shared.getCallbackAuthViewModel()
        
        doPublish(viewmodel.stateAdapter){[weak self] authState in
            self?.error = authState.message
            self?.uiState = authState
        }
        .store(in: &cancellables)
        
        
        self.viewmodel = viewmodel
    }
    func login(email: String, password: String) {
        // Login logic
        viewmodel?.login(email: email, password: password)
    }

    func register(name: String, email: String, password: String) {
        viewmodel?.register(name: name, email: email, password: password)
    }
    
    func deactivate(){
        cancellables.forEach { $0.cancel() }
        cancellables.removeAll()

        viewmodel?.clear()
        viewmodel = nil
}
}

struct Login_Previews: PreviewProvider {
    static var previews: some View {
        LoginView(onLogin: {})
    }
}
