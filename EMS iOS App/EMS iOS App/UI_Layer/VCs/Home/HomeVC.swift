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
        cell.nedocsView.layer.cornerRadius = 5.0
        cell.nedocsView.backgroundColor = hospital.nedocsScore.getNedocsColor()
        cell.nedocsLabel.text = hospital.nedocsScore.rawValue
        if (hospital.hasDiversion) {
            cell.horStackView2.isHidden = false
            cell.expandedDiversionIconLabel.isHidden = false

            let currDiversionIcon: UIImage!
            switch hospital.diversions.count {
            case 1:
                currDiversionIcon = UIImage(named: "Warning1Icon")
            case 2:
                currDiversionIcon = UIImage(named: "Warning2Icon")
            case 3:
                currDiversionIcon = UIImage(named: "Warning3Icon")
            case 4:
                currDiversionIcon = UIImage(named: "Warning4Icon")
            case 5:
                currDiversionIcon = UIImage(named: "Warning5Icon")
            case 6:
                currDiversionIcon = UIImage(named: "Warning6Icon")
            default:
                currDiversionIcon = UIImage(named: "WarningIcon")
            }
            cell.diversionIcon?.image = currDiversionIcon
            cell.expandedDiversionIcon?.image = UIImage(named: "WarningIcon")
            cell.expandedDiversionIconLabel.text = "Medical Diversion"
        } else {
            cell.horStackView2.isHidden = true
            cell.diversionIcon?.image = nil
            cell.expandedDiversionIcon?.image = nil
            cell.expandedDiversionIconLabel.text = ""
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
            return 200
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
        
        if self.selectedRowIndex != -1 {
            self.tableView.cellForRow(at: NSIndexPath(row: self.selectedRowIndex, section: 0) as IndexPath)?.backgroundColor = UIColor.white
        }

        if selectedRowIndex != indexPath.row {
            self.thereIsCellTapped = true
            self.selectedRowIndex = indexPath.row
            let image = UIImage(named:"ArrowIconUp")
            cell.expandButton.setImage(image, for: .normal)
        }
        else {
            // there is no cell selected anymore
            self.thereIsCellTapped = false
            self.selectedRowIndex = -1
            let image = UIImage(named:"ArrowIcon")
            cell.expandButton.setImage(image, for: .normal)
            
            
        //cell.expandButton.origin.x
        }
        print(cell.expandButton.frame.origin.y)
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
        hospitals.append(HospitalIH(name: "Grady Health System", nedocsScore: .severe, hospitalType: .adultTraumaCenterLevelI, distance: 0.5, hasDiversion: true, diversions: ["Medical Diversion"], address: "80 Jesse Hill Jr Dr SE, Atlanta, GA 30303", phoneNumber: "4046161000", regionNumber: 3, county: "Fulton", rch: "D"))
        hospitals.append(HospitalIH(name: "WellStar - South", nedocsScore: .normal, hospitalType: .emergencyCardiacCenterLevelI, distance: 1.1, hasDiversion: false, diversions: [], address: "Test Address", phoneNumber: "234567890", regionNumber: 3, county: "Fulton", rch: "D"))
        hospitals.append(HospitalIH(name: "Emory - University Hospital", nedocsScore: .busy, hospitalType: .remoteStrokeCenter, distance: 1.3, hasDiversion: true, diversions: ["Medical Diversion", "Psych Diversion"], address: "Test Address", phoneNumber: "1234567890", regionNumber: 3, county: "Dekalb", rch: "D"))
        hospitals.append(HospitalIH(name: "Hospital 4", nedocsScore: .busy, hospitalType: .thrombectomyStrokeCenter, distance: 1.5, hasDiversion: false, diversions: [], address: "Test Address", phoneNumber: "1234567890", regionNumber: 1, county: "Fulton", rch: "D"))
        hospitals.append(HospitalIH(name: "Hospital 5", nedocsScore: .normal, hospitalType: .emergencyCardiacCenterLevelII, distance: 1.6, hasDiversion: false, diversions: [], address: "Test Address", phoneNumber: "1234567890", regionNumber: 3, county: "Fulton", rch: "D"))
        hospitals.append(HospitalIH(name: "Hospital 6", nedocsScore: .normal, hospitalType: .emergencyCardiacCenterLevelI, distance: 2.0, hasDiversion: true, diversions: ["Medical Diversion"], address: "Test Address", phoneNumber: "1234567890", regionNumber: 3, county: "Fulton", rch: "D"))
        hospitals.append(HospitalIH(name: "Hospital 7", nedocsScore: .severe, hospitalType: .pediatricTraumaCenterLevelI, distance: 3.5, hasDiversion: true, diversions: ["Medical Diversion"], address: "Test Address", phoneNumber: "1234567890", regionNumber: 3, county: "Fulton", rch: "D"))
        hospitals.append(HospitalIH(name: "Hospital 8", nedocsScore: .busy, hospitalType: .neonatalCenterLevelI, distance: 5.7, hasDiversion: false, diversions: [], address: "Test Address", phoneNumber: "1234567890", regionNumber: 3, county: "Fulton", rch: "D"))
        hospitals.append(HospitalIH(name: "Hospital 9", nedocsScore: .severe, hospitalType: .maternalCenterLevelII, distance: 7.9, hasDiversion: false, diversions: [], address: "Test Address", phoneNumber: "1234567890", regionNumber: 3, county: "Fulton", rch: "D"))
        hospitals.append(HospitalIH(name: "Hospital 10", nedocsScore: .severe, hospitalType: .adultTraumaCenterLevelII, distance: 10.5, hasDiversion: true, diversions: ["Medical Diversion", "Psych Diversion"],address: "Test Address", phoneNumber: "1234567890", regionNumber: 3, county: "Fulton", rch: "D"))
    }
    
}
