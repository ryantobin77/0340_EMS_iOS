//
//  LoadingVC.swift
//  EMS iOS App
//
//  Created by Ryan Tobin on 3/11/21.
//  Copyright © 2021 JD_0340_EMS. All rights reserved.
//

import UIKit
import MapKit

class LoadingVC: UIViewController {
    
    @IBOutlet var spinner: UIActivityIndicatorView!
    var hospitals: Array<HospitalIH>!
    var locationManager: CLLocationManager!

    func getHospitalDistances() {
        let currentLocation: CLLocation = self.determineCurrentLocation()
        var count = 0
        for hospital in self.hospitals {
            let hospitalLocation = CLLocation(latitude: CLLocationDegrees(exactly: hospital.lat)!, longitude: CLLocationDegrees(exactly: hospital.long)!)
            let request = MKDirections.Request()
            request.source = MKMapItem(placemark: MKPlacemark(coordinate: currentLocation.coordinate))
            request.destination = MKMapItem(placemark: MKPlacemark(coordinate: hospitalLocation.coordinate))
            request.requestsAlternateRoutes = true
            request.transportType = .automobile
            let directions = MKDirections(request: request)
            directions.calculate { response, error in
                if let unwrappedResponse = response {
                    let route = unwrappedResponse.routes[0]
                    hospital.distance = route.distance
                } else {
                    var distance = currentLocation.distance(from: hospitalLocation)
                    distance = (distance / 1000) * 0.62137
                    distance = Double(round(distance * 100) / 100)
                    hospital.distance = distance
                }
                count += 1
                if count == self.hospitals.count {
                    DispatchQueue.main.async {
                        self.spinner.stopAnimating()
                        self.performSegue(withIdentifier: "showHospitals", sender: self)
                    }
                }
            }
        }
    }
    
    func determineCurrentLocation() -> CLLocation {
        if (CLLocationManager.authorizationStatus() == .authorizedWhenInUse || CLLocationManager.authorizationStatus() == .authorizedAlways){
            if let location = self.locationManager.location {
                return location
            } else {
                return self.determineCurrentLocation()
            }
        }
        return CLLocation()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.spinner.startAnimating()
        self.locationManager = CLLocationManager()
        self.locationManager.requestWhenInUseAuthorization()
        self.hospitals = Array<HospitalIH>()
        let tasker = HospitalsTasker()
        tasker.getAllHospitals(failure: {
            print("Failure")
        }, success: { (hospitals) in
            guard let hospitals = hospitals else {
                self.hospitals = Array<HospitalIH>()
                DispatchQueue.main.async {
                    self.spinner.stopAnimating()
                    self.performSegue(withIdentifier: "showHospitals", sender: self)
                }
                return
            }
            self.hospitals = hospitals
            self.getHospitalDistances()
        })
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let navVC = segue.destination as! UINavigationController
        let homeVC = navVC.viewControllers.first as! HomeVC
        homeVC.hospitals = self.hospitals
    }
    

}
