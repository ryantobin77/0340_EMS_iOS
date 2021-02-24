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
    var specialtyCenters: [HospitalType]!
    var distance: Float!
    var hasDiversion: Bool!
    var diversions: [String]!
    var address: String!
    var phoneNumber: String!
    var regionNumber: String!
    var county: String!
    var rch: String! //Regional Coordinating Hospital
    

    init(name: String, nedocsScore: NedocsScore, specialtyCenters: [HospitalType], distance: Float, hasDiversion: Bool, diversions: [String], address: String, phoneNumber: String, regionNumber: String, county: String, rch: String) {
        self.name = name
        self.nedocsScore = nedocsScore
        self.specialtyCenters = specialtyCenters
        self.distance = distance
        self.hasDiversion = hasDiversion
        self.diversions = diversions
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
            var rch_value = ""
            if let rch = hospital["rch"] as? String {
                rch_value = rch
            }
            let emsRegion = hospital["ems_region"] as! String
            var county_val = ""
            if let county = hospital["county"] as? String {
                county_val = county
            }
            let diversions = hospital["diversions"] as! Array<String>
            let nedocsScore = hospital["nedocs_score"] as! String
            let specialtyCenters = hospital["specialty_centers"] as! Array<String>
            var centers: [HospitalType] = [HospitalType]()
            for center in specialtyCenters {
                if let type = HospitalType(rawValue: center) {
                    centers.append(type)
                } else {
                    centers.append(HospitalType(rawValue: "Adult Trauma Center - Level 1")!)
                }
            }
            let address = street + ", " + city + ", " + state + " " + zip
            
            // Hardcoded: specialty centers, distance
            let hosp = HospitalIH(name: name, nedocsScore: NedocsScore(rawValue: nedocsScore)!, specialtyCenters: centers, distance: 1.0, hasDiversion: (diversions.count > 0), diversions: diversions, address: address, phoneNumber: phone, regionNumber: emsRegion, county: county_val, rch: rch_value)
            result.append(hosp)
        }
        return result
    }

}
