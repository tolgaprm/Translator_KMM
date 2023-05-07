//
//  TranslateHistoryItem.swift
//  iosApp
//
//  Created by Tolga Pirim on 7.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateHistoryItem: View {

    let item: UiHistoryItem
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick){
            VStack(alignment: .leading){
                HStack{
                    SmalLanguageIcon(language: item.fromLanguage)
                        .padding(.trailing)
                    Text(item.fromText)
                        .foregroundColor(.lightBlue)
                        .font(.body)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.bottom)
                
                HStack{
                    SmalLanguageIcon(language: item.toLanguage)
                        .padding(.trailing)
                    Text(item.toText)
                        .foregroundColor(.onSurface)
                        .font(.body.weight(.semibold))
                }
                .frame(maxWidth: .infinity, alignment: .leading)
            }
            .frame(maxWidth: .infinity)
            .padding()
            .gradientSurface()
            .cornerRadius(15)
            .shadow(radius: 4       )
        }
    }
}

struct TranslateHistoryItem_Previews: PreviewProvider {
    static var previews: some View {
        TranslateHistoryItem(
        item: UiHistoryItem(
            id: 1,
            fromText: "This is test translation",
            toText: "Bu test çevirisidir",
            fromLanguage: UiLanguage(language: .english, imageName: "english"),
            toLanguage: UiLanguage(language: .turkish, imageName: "turkish")
        ),
        onClick: {
            
        })
    }
}
