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
    private let parser = IOSVoiceToTextParser()
    @ObservedObject var viewModel: IOSTranslateViewModel
    
    init(historyDataSource: HistoryDataSource, translateUseCase: TranslateUseCase) {
        self.historyDataSource = historyDataSource
        self.translateUseCase = translateUseCase
        self.viewModel = IOSTranslateViewModel(
            historyDataSource: historyDataSource,
            translateUseCase: translateUseCase
        )
    }
    
    var body: some View {
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
                    Text("History")
                        .font(.title)
                        .bold()
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.backgroundColor)
                    
                    ForEach(viewModel.state.history, id: \.self.id){ item in
                        TranslateHistoryItem(
                            item: item,
                            onClick: {
                                viewModel.onEvent(event: TranslateEvent.SelectHistoryItem(historyItem: item))
                            }
                        ).listRowSeparator(.hidden)
                            .listRowBackground(Color.backgroundColor)
                    }
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

