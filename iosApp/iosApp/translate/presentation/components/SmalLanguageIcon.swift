//
//  SmalLanguageIcon.swift
//  iosApp
//
//  Created by Tolga Pirim on 4.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SmalLanguageIcon: View {
    var language: UiLanguage
    var body: some View {
        Image(uiImage: UIImage(named: language.imageName.lowercased())!)
            .resizable()
            .frame(width: 30, height: 30)
    }
}

struct SmalLanguageIcon_Previews: PreviewProvider {
    static var previews: some View {
        SmalLanguageIcon(language: UiLanguage(language: .turkish, imageName: "turkish"))
    }
}
