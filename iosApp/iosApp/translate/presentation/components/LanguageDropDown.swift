//
//  LanguageDropDown.swift
//  iosApp
//
//  Created by Tolga Pirim on 4.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDropDown: View {
    var language: UiLanguage
    var isOpen: Bool
    var selectLanguage: (UiLanguage) -> Void
    var body: some View {
        Menu {
            VStack {
                ForEach(UiLanguage.Companion().allLanguages,id:\.self.language.langCode) { language in
                    LanguageDropDownItem(
                        language: language,
                        onClick: {
                            selectLanguage(language)
                        }
                    )
                }
            }
        } label: {
            SmalLanguageIcon(language: self.language)
            Text(language.language.langName)
                .foregroundColor(.lightBlue)
            Image(systemName: isOpen ? "chevron.up" : "chevron.down")
                .foregroundColor(.lightBlue)
        }

    }
}

struct LanguageDropDown_Previews: PreviewProvider {
    static var previews: some View {
        LanguageDropDown(
            language: UiLanguage(language: .turkish, imageName: "turkish"),
            isOpen: false,
            selectLanguage: {language in
                
            }
        )
    }
}
