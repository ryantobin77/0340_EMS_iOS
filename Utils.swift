//
//  Utils.swift
//  EMS iOS App
//
//  Created by Ryan Tobin on 11/10/20.
//  Copyright © 2020 JD_0340_EMS. All rights reserved.
//

import Foundation
import UIKit

extension UIColor {
   convenience init(red: Int, green: Int, blue: Int) {
       assert(red >= 0 && red <= 255, "Invalid red component")
       assert(green >= 0 && green <= 255, "Invalid green component")
       assert(blue >= 0 && blue <= 255, "Invalid blue component")

       self.init(red: CGFloat(red) / 255.0, green: CGFloat(green) / 255.0, blue: CGFloat(blue) / 255.0, alpha: 1.0)
   }

   convenience init(rgb: Int) {
       self.init(
           red: (rgb >> 16) & 0xFF,
           green: (rgb >> 8) & 0xFF,
           blue: rgb & 0xFF
       )
   }
}

extension NedocsScore {
    func getNedocsColor() -> UIColor {
        var color: UIColor!
        switch self {
        case .severe:
            color = UIColor(rgb: 0xff0000)
        case .busy:
            color = UIColor(rgb: 0xffc000)
        case .normal:
            color = UIColor(rgb: 0x70ad47)
        }
        return color
    }
}

extension HospitalType {
    func getHospitalIcon() -> UIImage {
        var image: UIImage!
        switch self {
        case .adultTraumaCenter:
            image = UIImage(named: "PersonIcon")
        case .heart:
            image = UIImage(named: "HeartIcon")
        case .brain:
            image = UIImage(named: "BrainIcon")
        }
        return image
    }
}
