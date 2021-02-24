//
//  Utils.swift
//  EMS iOS App
//
//  Created by Ryan Tobin on 11/9/20.
//  Copyright © 2020 JD_0340_EMS. All rights reserved.
//

import Foundation

enum NedocsScore: String {
    case normal = "Normal"
    case busy = "Busy"
    case overcrowded = "Overcrowded"
    case severe = "Severe"
}

enum HospitalType: String {
    case adultTraumaCenterLevelI = "Adult Trauma Center - Level 1"
    case adultTraumaCenterLevelII = "Adult Trauma Center - Level 2"
    case adultTraumaCenterLevelIII = "Adult Trauma Center - Level 3"
    case adultTraumaCenterLevelIV = "Adult Trauma Center - Level 4"
    case pediatricTraumaCenterLevelI = "Pediatric Trauma Center - Level 1"
    case pediatricTraumaCenterLevelII = "Pediatric Trauma Center - Level 2"
    case comprehensiveStrokeCenter = "Comprehensive Stroke Center"
    case thrombectomyStrokeCenter = "Thrombectomy Stroke Center"
    case primaryStrokeCenter = "Primary Stroke Center"
    case remoteStrokeCenter = "Remote Treatment Stroke Center"
    case emergencyCardiacCenterLevelI = "Emergency Cardiac Care Center - Level 1"
    case emergencyCardiacCenterLevelII = "Emergency Cardiac Care Center - Level 2"
    case emergencyCardiacCenterLevelIII = "Emergency Cardiac Care Center - Level 3"
    case neonatalCenterLevelI = "Neonatal Center - Level 1"
    case neonatalCenterLevelII = "Neonatal Center - Level 2"
    case neonatalCenterLevelIII = "Neonatal Center - Level 3"
    case maternalCenterLevelI = "Maternal Center - Level 1"
    case maternalCenterLevelII = "Maternal Center - Level 2"
    case maternalCenterLevelIII = "Maternal Center - Level 3"
}
