//
//  MicrofonePowerObserver.swift
//  iosApp
//
//  Created by Tolga Pirim on 9.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import Speech
import Combine
// Combine gives us to access to reactive programming.
// we will need here to consistenly update our state with the corresponding power ratio of our microfon.

class MicrofonePowerObserver : ObservableObject {
    private var cancelable: AnyCancellable? = nil
    private var audioRecord: AVAudioRecorder? = nil
    
    @Published private(set) var micPowerRatio = 0.0
    
    private let powerRatioEmissionsPerSecond = 20.0
    
    func startObserving(){
        do{
            let recorderSetting: [String: Any] = [
                AVFormatIDKey: NSNumber(value: kAudioFormatAppleLossless),
                AVNumberOfChannelsKey: 1
            ]
            
            let recorder = try AVAudioRecorder(url:URL(fileURLWithPath: "/dev/null", isDirectory: true), settings: recorderSetting)
            recorder.isMeteringEnabled = true
            recorder.record()
            self.audioRecord = recorder
            
            self.cancelable = Timer.publish(
                every: 1.0 / self.powerRatioEmissionsPerSecond,
                tolerance: 1.0 / self.powerRatioEmissionsPerSecond,
                on: .main,
                in: .common
            )
            .autoconnect()
            .sink{[weak self]_ in // equlivient to collecting in Flows
                recorder.updateMeters()
                let powerOffset = recorder.averagePower(forChannel: 0)
                if powerOffset < -50 {
                    self?.micPowerRatio = 0.0
                } else{
                    let normalizedOffset =  CGFloat(50 + powerOffset) / 50
                    self?.micPowerRatio = normalizedOffset
                }
            }
            
        } catch{
            print("An error occured when observing microhone power \(error.localizedDescription)")
        }
    }
    
    
    func release(){
        cancelable = nil
        audioRecord?.stop()
        audioRecord = nil
        micPowerRatio = 0.0
    }
}
