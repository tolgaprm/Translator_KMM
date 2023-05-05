//
//  GradientSurface.swift
//  iosApp
//
//  Created by Tolga Pirim on 5.05.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct GradientSurface : ViewModifier {
    @Environment(\.colorScheme) var colorScheme
    
    func body(content: Content) -> some View {
        if colorScheme == .dark {
            let gradientStart = Color(hex: 0xFF23262E)
            let gradientEnd = Color(hex: 0xFF212329)
            content.background(
                LinearGradient(
                    colors: [gradientStart,gradientEnd],
                    startPoint:.top,
                    endPoint: .bottom
                )
            )
        } else {
            content.background(Color.surface)
        }
    }
}

extension View {
    func gradientSurface() -> some View {
        modifier(GradientSurface())
    }
}
