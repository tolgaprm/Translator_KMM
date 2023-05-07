//
//  SwapLanguageButton.swift
//  iosApp
//
//  Created by Tolga Pirim on 4.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct SwapLanguageButton: View {
    var onclick: () -> Void

    var body: some View {
        Button(action: onclick) {
            Image(uiImage: UIImage(named: "swap_languages")!)
                .padding()
                .background(Color.primaryColor)
                .clipShape(Circle())
            
        }
    }
}

struct SwapLanguageButton_Previews: PreviewProvider {
    static var previews: some View {
        SwapLanguageButton(
        onclick: {})
    }
}
