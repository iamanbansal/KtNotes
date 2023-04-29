//
//  NoteListScreen.swift
//  iosApp
//
//  Copyright © 2023 Aman Bansal. All rights reserved.
//

import SwiftUI
import shared
import Combine

struct NoteListScreen: View {
    var onLogout:()->Void
    
    @State private var isNoteSelected = false
    @State private var selectedNoteId: String=""
    @StateObject var viewModel = ObservableNotesModel()
 
    var body: some View {
        VStack {
            NavigationLink(destination: NoteDetailsView(id: selectedNoteId), isActive: $isNoteSelected){
                EmptyView()
            }
            
            if viewModel.uiState?.notes != nil {
                List {
                    ForEach(viewModel.uiState!.notes, id: \.self.id) { note in
                        Button(action: {
                            isNoteSelected = true
                            selectedNoteId = note.id
                        }) {
                            NoteItem(
                                note: note,
                                onDeleteClick: {
                                viewModel.deleteNote(id: note.id)
                                })
                        }
                    }
                }
                .listStyle(.plain)
            }else{
                Text("Add your notes")
                    .fontWeight(.light)
                    .padding(.bottom, 3)
            }
        }
        .onAppear {
            viewModel.activate()
        }
        .onDisappear{
            if !isNoteSelected{
                viewModel.deactivate()
            }
        }
        .onChange(of: viewModel.uiState?.isUserLoggedIn , perform: { isUserLoggedIn in
            if isUserLoggedIn == false {
                onLogout()
            }
        })
        .navigationBarTitleDisplayMode(.inline)
        .navigationBarTitle("All Notes")
        .navigationBarItems(
            leading: HStack {
                
                Button(action: {
                    isNoteSelected = true
                    selectedNoteId = NoteDetailsSharedViewModelKt.NEW_NOTE_ID
                }, label: {Image(systemName: "plus")})
            },
            
            trailing: Button(action: {
                viewModel.logout()
            }) {
                Image(systemName: "arrow.uturn.left")
            }
    )
        
    }
}
struct NoteItem: View {
    var note: Note
    var onDeleteClick: () -> Void
    
    var body: some View {
        VStack(alignment: .leading) {
            HStack {
                Text(note.title)
                    .font(.title3)
                    .fontWeight(.semibold)
                Spacer()
                Button(action: onDeleteClick) {
                    Image(systemName: "xmark").foregroundColor(.black)
                }
            }.padding(.bottom, 3)
            
            Text(note.note)
                .fontWeight(.light)
                .padding(.bottom, 3)
                .lineLimit(2)
                .truncationMode(.tail)
            
            HStack {
                Spacer()
                Text(note.formattedCreatedDate)
                    .font(.footnote)
                    .fontWeight(.light)
            }
        }
        .padding()
        .clipShape(RoundedRectangle(cornerRadius: 5.0))
    }
}

class ObservableNotesModel: ObservableObject {
    
    private var viewmodel: NotesSharedViewModel = KotlinDependencies.shared.getNotesViewModel()
    private var cancellables = [AnyCancellable]()
    let adapter:FlowAdapter<NotesUiState>?
    
    @Published var uiState:NotesUiState?

    init(){
        adapter = FlowAdapter<NotesUiState>(scope:viewmodel.viewModelScope, flow:viewmodel.notesState)
    }
                                    
    func activate(){
        doPublish(adapter!){[weak self] notesState in

            self?.uiState = notesState
        }
        .store(in: &cancellables)
    }
    
    func deleteNote(id:String){
        viewmodel.deleteNote(id: id)
    }
    
    func logout(){
        viewmodel.logout()
    }
    
    func deactivate(){
        cancellables.forEach { $0.cancel() }
        cancellables.removeAll()
    }
    
    deinit{
        viewmodel.clear()
    }
}



struct NoteListScreen_Previews: PreviewProvider {
    static var previews: some View {
        NoteListScreen(onLogout: {})
    }
}
