//
//  HomeVC.swift
//  EMS iOS App
//
//  Created by Ryan Tobin on 11/9/20.
//  Copyright © 2020 JD_0340_EMS. All rights reserved.
//

import UIKit

class HomeVC: UIViewController, UITableViewDelegate, UITableViewDataSource, UIGestureRecognizerDelegate {
    
    @IBOutlet var tableView: UITableView!
    var hospitals: Array<HospitalIH>!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationController?.navigationBar.barStyle = UIBarStyle.black
        self.tableView.delegate = self
        self.tableView.dataSource = self
        self.getHospitals()
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return hospitals.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "hospitalCell", for: indexPath) as! HomeTableViewCell
        let hospital = self.hospitals[indexPath.row]
        
        cell.hospitalName.text = hospital.name
        cell.hospitalName.sizeToFit()
        cell.nedocsView.layer.cornerRadius = 5.0
        cell.nedocsView.backgroundColor = hospital.nedocsScore.getNedocsColor()
        cell.nedocsLabel.text = hospital.nedocsScore.rawValue
        if (hospital.hasDiversion) {
            cell.horStackView2.isHidden = false
            cell.medicalDiversionIcon2Label2.isHidden = false
            cell.medicalDiversionIcon2Label3.isHidden = true

            cell.medicalDiversionIcon?.image = UIImage(named: "WarningIcon")
            cell.medicalDiversionIcon2?.image = UIImage(named: "WarningIcon")
            cell.medicalDiversionIcon2Label.text = "Medical Diversion"
            //cell.medicalDiversionIcon2Label2.text = "Psych Diversion"
            cell.medicalDiversionIcon2Label2.isHidden = true
        } else {
            cell.horStackView2.isHidden = true
            cell.medicalDiversionIcon?.image = nil
            cell.medicalDiversionIcon2?.image = nil
            cell.medicalDiversionIcon2Label.text = ""
            cell.medicalDiversionIcon2Label2.isHidden = true
            cell.medicalDiversionIcon2Label3.isHidden = true
        }
        cell.hospitalTypeIcon2Label.text = hospital.hospitalType.map { $0.rawValue }
        
        
        cell.hospitalTypeIcon.image = hospital.hospitalType.getHospitalIcon()
        cell.hospitalTypeIcon2.image = hospital.hospitalType.getHospitalIcon()
        cell.distanceLabel.text = "\(String(hospital.distance)) mi"
        //cell.address?.text = hospital.address
        cell.address.attributedText = NSAttributedString(string: hospital.address, attributes:
            [.underlineStyle: NSUnderlineStyle.single.rawValue])
        cell.address.textColor = UIColor.blue
        //cell.phoneNumber?.text = hospital.phoneNumber
        cell.phoneNumber.attributedText = NSAttributedString(string: hospital.phoneNumber, attributes:
            [.underlineStyle: NSUnderlineStyle.single.rawValue])
        cell.phoneNumber.textColor = UIColor.blue
        cell.countyLabel.text = "\(String(hospital.county)) County - EMS Region \(String(hospital.regionNumber))"
        cell.rchLabel.text = "Regional Coordination Hospital \(String(hospital.rch))"
    
        return cell
    }
    
    var thereIsCellTapped = false
    var selectedRowIndex = -1

    func tableView(_ tableView: UITableView,
                   heightForRowAt indexPath: IndexPath) -> CGFloat {

        if indexPath.row == selectedRowIndex && thereIsCellTapped {
            return 220
        }

        return 90
    }
    
    @IBAction func expandButton(_ sender: UIButton) {
        print("button clicked")
        guard let cell = sender.superview?.superview as? HomeTableViewCell else {
            return
        }

        guard let indexPath = tableView.indexPath(for: cell) else {
            return
        }
        cell.expandButton.isHidden = true
        cell.minimizeButton.isHidden = false
        
        //cell.expandButton.frame.origin.y = cell.expandButton.frame.origin.y + 110

        
        if self.selectedRowIndex != -1 {
            self.tableView.cellForRow(at: NSIndexPath(row: self.selectedRowIndex, section: 0) as IndexPath)?.backgroundColor = UIColor.white
        }

        if selectedRowIndex != indexPath.row {
            self.thereIsCellTapped = true
            self.selectedRowIndex = indexPath.row
            let image = UIImage(named:"ArrowIcon")
            cell.expandButton.setImage(image, for: .normal)
        }
        else {
            // there is no cell selected anymore
            cell.minimizeButton.isHidden = true
            cell.expandButton.isHidden = false
            self.thereIsCellTapped = false
            self.selectedRowIndex = -1
            let image = UIImage(named:"ArrowIcon")
            cell.expandButton.setImage(image, for: .normal)
            
            
        //cell.expandButton.origin.x
        }
        //cell.expandButton.isHidden = false
        //print(cell.expandButton.frame.origin.y)
        self.tableView.beginUpdates()
        self.tableView.endUpdates()
        
    }
    var favoritePinTapped = false
    
    @IBAction func favoritePin(_ sender: UIButton) {
        print("fav button clicked")
        guard let cell = sender.superview?.superview as? HomeTableViewCell else {
            return
        }
        
        if (!favoritePinTapped) {
            let myImage = UIImage(named: "FilledFavoritePin")
            cell.favoritePin.setImage(myImage, for: .normal)
            self.favoritePinTapped = true
        } else {
            let myImage = UIImage(named: "OutlineFavoritePin")
            cell.favoritePin.setImage(myImage, for: .normal)
            self.favoritePinTapped = false
        }
    }
    
    
    
    func getHospitals() {
        hospitals = Array<HospitalIH>()
        hospitals.append(HospitalIH(name: "Grady Health System", nedocsScore: .severe, hospitalType: .adultTraumaCenter, distance: 0.5, hasDiversion: true, address: "80 Jesse Hill Jr Dr SE, Atlanta, GA 30303", phoneNumber: "4046161000", regionNumber: 3, county: "Fulton", rch: "D"))
        hospitals.append(HospitalIH(name: "WellStar - South", nedocsScore: .normal, hospitalType: .heart, distance: 1.1, hasDiversion: false, address: "Test Address", phoneNumber: "234567890", regionNumber: 3, county: "Fulton", rch: "D"))
        hospitals.append(HospitalIH(name: "Emory - University Hospital", nedocsScore: .busy, hospitalType: .brain, distance: 1.3, hasDiversion: true, address: "Test Address", phoneNumber: "1234567890", regionNumber: 3, county: "Dekalb", rch: "D"))
        hospitals.append(HospitalIH(name: "Hospital 4", nedocsScore: .busy, hospitalType: .brain, distance: 1.5, hasDiversion: false, address: "Test Address", phoneNumber: "1234567890", regionNumber: 1, county: "Fulton", rch: "D"))
        hospitals.append(HospitalIH(name: "Hospital 5", nedocsScore: .normal, hospitalType: .heart, distance: 1.6, hasDiversion: false, address: "Test Address", phoneNumber: "1234567890", regionNumber: 3, county: "Fulton", rch: "D"))
        hospitals.append(HospitalIH(name: "Hospital 6", nedocsScore: .normal, hospitalType: .heart, distance: 2.0, hasDiversion: true, address: "Test Address", phoneNumber: "1234567890", regionNumber: 3, county: "Fulton", rch: "D"))
        hospitals.append(HospitalIH(name: "Hospital 7", nedocsScore: .severe, hospitalType: .brain, distance: 3.5, hasDiversion: true, address: "Test Address", phoneNumber: "1234567890", regionNumber: 3, county: "Fulton", rch: "D"))
        hospitals.append(HospitalIH(name: "Hospital 8", nedocsScore: .busy, hospitalType: .brain, distance: 5.7, hasDiversion: false, address: "Test Address", phoneNumber: "1234567890", regionNumber: 3, county: "Fulton", rch: "D"))
        hospitals.append(HospitalIH(name: "Hospital 9", nedocsScore: .severe, hospitalType: .brain, distance: 7.9, hasDiversion: false, address: "Test Address", phoneNumber: "1234567890", regionNumber: 3, county: "Fulton", rch: "D"))
        hospitals.append(HospitalIH(name: "Hospital 10", nedocsScore: .severe, hospitalType: .adultTraumaCenter, distance: 10.5, hasDiversion: true, address: "Test Address", phoneNumber: "1234567890", regionNumber: 3, county: "Fulton", rch: "D"))
    }
    
}
