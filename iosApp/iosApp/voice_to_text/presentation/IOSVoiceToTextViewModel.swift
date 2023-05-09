//
//  VoiceToTextViewModel.swift
//  iosApp
//
//  Created by Tolga Pirim on 9.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import Combine


extension VoiceToTextScreen{
   @MainActor class IOSVoiceToTextViewModel : ObservableObject{
        private let parser: any VoiceToTextParser
        private let languageCode: String
       
        private let viewModel: VoiceToTextViewModel
       
       @Published var state = VoiceToTextState(
        powerRatios: [],
        spokenText:"",
        canRecord: false,
        recordError: nil,
        displayState: nil
       )
       
       private var handle: DisposableHandle?
        
       init(parser: VoiceToTextParser, languageCode: String) {
            self.parser = parser
            self.languageCode = languageCode
            self.viewModel = VoiceToTextViewModel(parser: parser, coroutineScope: nil)
           self.viewModel.onEvent(event: VoiceToTextEvent.PermissionResult(isGranted: true, isPermanentlyDeclined: false))
        }
        
        func onEvent(event:VoiceToTextEvent){
            self.viewModel.onEvent(event: event)
        }
        
       func startObserving(){
           handle = viewModel.state.subscribe{ [weak self] state in
               if let  state {
                   self?.state = state
               }
           }
       }
       
       func dispose(){
           handle?.dispose()
           viewModel.onEvent(event: VoiceToTextEvent.Reset())
       }
    }
}
