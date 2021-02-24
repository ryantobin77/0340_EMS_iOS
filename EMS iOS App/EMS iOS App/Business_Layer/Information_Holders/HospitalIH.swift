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
    var address: String!
    var phoneNumber: String!
    var regionNumber: String!
    var county: String!
    var rch: String! //Regional Coordinating Hospital
    
    init(name: String, nedocsScore: NedocsScore, hospitalType: HospitalType, distance: Float, hasDiversion: Bool, address: String, phoneNumber: String,
         regionNumber: String,
         county: String,
         rch: String) {
        self.name = name
        self.nedocsScore = nedocsScore
        self.hospitalType = hospitalType
        self.distance = distance
        self.hasDiversion = hasDiversion
        self.address = address
        self.phoneNumber = phoneNumber
        self.regionNumber = regionNumber
        self.county = county
        self.rch = rch
    }
    
    class func parseJson(jsonData: Data) -> Array<HospitalIH>? {
        guard let hospitals = try? JSONSerialization.jsonObject(with: jsonData) as? Array<[String: Any]> else {
            return nil
        }
        var result: Array<HospitalIH> = Array<HospitalIH>()
        for hospital in hospitals {
            let name = hospital["name"] as! String
            let street = hospital["street"] as! String
            let city = hospital["city"] as! String
            let state = hospital["state"] as! String
            let zip = hospital["zip"] as! String
            let phone = hospital["phone"] as! String
            let rch = hospital["rch"] as! String
            let emsRegion = hospital["ems_region"] as! String
            let diversions = hospital["diversions"] as! Array<String>
            let nedocsScore = hospital["nedocs_score"] as! String
            let address = street + ", " + city + ", " + state + " " + zip
            
            // Hardcoded: hospital type, distance, county
            let hosp = HospitalIH(name: name, nedocsScore: NedocsScore(rawValue: nedocsScore)!, hospitalType: HospitalType.adultTraumaCenter, distance: 1.0, hasDiversion: (diversions.count > 0), address: address, phoneNumber: phone, regionNumber: emsRegion, county: "Fulton County", rch: rch)
            result.append(hosp)
        }
        return result
    }

}
