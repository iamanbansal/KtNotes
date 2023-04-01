//
//  NoteDetailsView.swift
//  iosApp
//
//  Created by Aman Bansal on 31/03/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
import Combine


struct NoteDetailsView:View {
    let id:String
    @Environment(\.presentationMode) var presentationMode
    @StateObject var viewModel = ObservableNoteDetailsModel()
    @State private var title = ""
    @State private var content = ""

    var body: some View {
        VStack {
                
            TextField("Title", text: $title)
                .frame(maxWidth: .infinity)
                .textFieldStyle(.roundedBorder)
                .padding()
            
            TextEditor(text: $content)
                .frame(maxWidth: .infinity)
                .border(Color.gray.opacity(0.3), width: 1)
                .cornerRadius(10)
                .padding()
           Spacer()
        }
        .overlay(
                    Group {
                        if viewModel.uiState?.isLoading == true {
                            ZStack {
                            
                                ProgressView()
                                    .progressViewStyle(CircularProgressViewStyle())
                                    .scaleEffect(2)
                                    .foregroundColor(.blue)
                            }
                            
                        }
                    }
        )
        .onAppear(){
            viewModel.activate(id: id)
        }
        .onDisappear(){
            viewModel.deactivate()
        }
        .onChange(of: viewModel.uiState?.note){note in
            if note != nil {
                title = note!.title
                content = note!.note
            }
        }
        
        .onChange(of: viewModel.uiState?.finishSaving){finishSaving in
            if finishSaving == true {
                presentationMode.wrappedValue.dismiss()
            }
        }
        
        .navigationBarItems(trailing:
          Button(action: {
            viewModel.saveNote(title: title, content: content)
           }) {
           Image(systemName: "checkmark")
          }
        )
    }
}

class ObservableNoteDetailsModel: ObservableObject {

    private var viewmodel: NoteDetailsCallbackVM?
    private var cancellables = [AnyCancellable]()

  
    @Published var uiState:NoteDetailsUiState?

    func activate(id:String){
        let viewmodel  =  KotlinDependencies.shared.getNoteDetailsViewModel()

        doPublish(viewmodel.stateAdapter){[weak self] authState in
            self?.uiState = authState
        }
        .store(in: &cancellables)
        
        viewmodel.getNoteDetails(id: id)
        
        self.viewmodel = viewmodel
    }
    
    func saveNote(title:String, content:String){
        viewmodel?.saveNote(title: title, content: content)
    }
    
    func deactivate(){
        cancellables.forEach { $0.cancel() }
        cancellables.removeAll()

        viewmodel?.clear()
        viewmodel = nil
    }
}

struct NoteDetailsView_Previews: PreviewProvider {
    static var previews: some View {
        NoteDetailsView(id:"sdfsf")
    }
}
