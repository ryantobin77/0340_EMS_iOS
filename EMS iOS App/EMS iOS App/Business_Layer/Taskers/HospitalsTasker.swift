//
//  HospitalsTasker.swift
//  EMS iOS App
//
//  Created by Ryan Tobin on 2/22/21.
//  Copyright © 2021 JD_0340_EMS. All rights reserved.
//

import UIKit

class HospitalsTasker: NSObject {

    override init() {
        super.init()
    }
    
    func getAllHospitals(failure: @escaping () -> Void, success: @escaping (_ hospitals: Array<HospitalIH>?) -> Void){
        let webCallTasker: WebCallTasker = WebCallTasker()
        webCallTasker.makeGetRequest(forBaseURL: BackendURLs.GET_HOSPITALS_URL, withParams: "", failure: {
            failure()
        }, success: {(data) in
            let hospitals = HospitalIH.parseJson(jsonData: data)
            success(hospitals)
        })
    }
    
}
