//
//  ContentView.swift
//  parking
//
//  Created by Dennis van der Zalm on 28/01/2023.
//

import SwiftUI
import Combine
import shared


struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        Main_iosKt.ParkingApp()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}


