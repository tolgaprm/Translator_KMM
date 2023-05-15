//
//  TranslateScreen.swift
//  iosApp
//
//  Created by Tolga Pirim on 4.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateScreen: View {
    private var historyDataSource: HistoryDataSource
    private var translateUseCase: TranslateUseCase
    private var parser :any VoiceToTextParser
    @ObservedObject var viewModel: IOSTranslateViewModel
    
    init(historyDataSource: HistoryDataSource, translateUseCase: TranslateUseCase,parser:VoiceToTextParser) {
        self.historyDataSource = historyDataSource
        self.translateUseCase = translateUseCase
        self.parser = parser
        self.viewModel = IOSTranslateViewModel(
            historyDataSource: historyDataSource,
            translateUseCase: translateUseCase
        )
    }
    
    var body: some View {
        
        ScrollViewReader { proxyReader in
                ZStack {
                    List {
                        HStack(alignment: .center){
                            LanguageDropDown(
                                language: viewModel.state.fromLanguage,
                                isOpen: viewModel.state.isChoosingFromLanguage,
                                selectLanguage: {uiLanguage in
                                    viewModel.onEvent(event: TranslateEvent.ChooseFromLanguage(language: uiLanguage))
                                }
                            )
                            Spacer()
                            SwapLanguageButton(onclick: {
                                viewModel.onEvent(event: TranslateEvent.SwapLanguages())
                            })
                            Spacer()
                            LanguageDropDown(
                                language: viewModel.state.toLanguage,
                                isOpen: viewModel.state.isChoosingToLanguage,
                                selectLanguage: {uiLanguage in
                                    viewModel.onEvent(event: TranslateEvent.ChooseToLanguage(language: uiLanguage))
                                }
                            )
                        }
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.backgroundColor)
                        .id("Scroll_to_top")
                        
                        TranslateTextField(
                            fromText: Binding(get: {viewModel.state.fromText}, set: { value in
                                viewModel.onEvent(event: TranslateEvent.ChangeTranslationText(text: value))
                            }),
                            toText: viewModel.state.toText,
                            isTranslating: viewModel.state.isTranslating,
                            fromLanguage: viewModel.state.fromLanguage,
                            toLanguage: viewModel.state.toLanguage,
                            onTranslateEvent: {event in
                                viewModel.onEvent(event: event)
                            }
                        )
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.backgroundColor)
                        
                        if !viewModel.state.history.isEmpty {
                            HStack{
                                Text("History")
                                    .font(.title)
                                    .bold()
                                    .frame(maxWidth: .infinity, alignment: .leading)
                                    .listRowSeparator(.hidden)
                                    .listRowBackground(Color.backgroundColor)
                                Spacer()
                                Button(
                                    action: {
                                        viewModel.onEvent(event: TranslateEvent.DeleteAllHistory())
                                    }
                                ) {
                                    Text("Clear all")
                                        .font(.caption)
                                        .foregroundColor(.lightBlue)
                                }
                            }.padding()
                                .listRowSeparator(.hidden)
                                .listRowBackground(Color.backgroundColor)
                        
                            ForEach(viewModel.state.history, id: \.self.id){ item in
                                TranslateHistoryItem(
                                    item: item,
                                    onClick: {
                                        viewModel.onEvent(event: TranslateEvent.SelectHistoryItem(historyItem: item))
                                        withAnimation(.spring()){
                                            proxyReader.scrollTo("Scroll_to_top", anchor: .top)
                                        }
                                    }
                                ).listRowSeparator(.hidden)
                                .listRowBackground(Color.backgroundColor)
                                    
                            }
                            .onDelete(perform: {indexSet in
                                for i in indexSet{
                                    let deletedItem = viewModel.state.history[i] as UiHistoryItem
                                    if (deletedItem.id != nil) {
                                        viewModel.onEvent(event: TranslateEvent.DeleteHistoryItem(id: deletedItem.id as! Int64))
                                    }
                                }
                            })
                        }
                    
                    }
                    .listStyle(.plain)
                    .buttonStyle(.plain)
                    
                    VStack{
                        Spacer()
                        NavigationLink(
                            destination: VoiceToTextScreen(
                                onResult: {spokenText in
                                    viewModel.onEvent(event: TranslateEvent.SubmitVoiceResult(result: spokenText))
                                },
                                parser: parser,
                                languageCode: viewModel.state.fromLanguage.language.langCode
                            )
                        ){
                            ZStack{
                                Circle()
                                    .foregroundColor(.primaryColor)
                                    .padding()
                                Image(uiImage: UIImage(named: "mic")!)
                                    .foregroundColor(.onPrimary)
                                    .accessibilityIdentifier("Record audio")
                            }
                            .frame(maxWidth: 100, maxHeight:  100)
                        }
                    }
                }
                .onAppear{
                    viewModel.startObserving()
                }
                .onDisappear{
                    viewModel.dispose()
                }
                
            }
        }
}

