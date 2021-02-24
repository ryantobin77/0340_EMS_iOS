//
//  HospitalIH.swift
//  EMS iOS App
//
//  Created by Ryan Tobin on 11/9/20.
//  Copyright © 2020 JD_0340_EMS. All rights reserved.
//

import UIKit

class HospitalIH: NSObject {
    
    var name: String!
    var nedocsScore: NedocsScore!
    var hospitalType: HospitalType!
    var distance: Float!
    var hasDiversion: Bool!
    var diversions: [String]!
    var address: String!
    var phoneNumber: String!
    var regionNumber: Int!
    var county: String!
    var rch: String! //Regional Coordinating Hospital
    
    init(name: String, nedocsScore: NedocsScore, hospitalType: HospitalType, distance: Float, hasDiversion: Bool, diversions: [String], address: String, phoneNumber: String,
         regionNumber: Int,
         county: String,
         rch: String) {
        self.name = name
        self.nedocsScore = nedocsScore
        self.hospitalType = hospitalType
        self.distance = distance
        self.hasDiversion = hasDiversion
        self.diversions = diversions
        self.address = address
        self.phoneNumber = phoneNumber
        self.regionNumber = regionNumber
        self.county = county
        self.rch = rch
    }

}
