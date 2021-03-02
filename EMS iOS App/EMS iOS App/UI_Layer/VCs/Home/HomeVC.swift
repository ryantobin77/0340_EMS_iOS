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
    var favoritePinTapped = false
    var thereIsCellTapped = false
    var selectedRowIndex = -1
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationController?.navigationBar.barStyle = UIBarStyle.black
        self.hospitals = Array<HospitalIH>()
        self.tableView.delegate = self
        self.tableView.dataSource = self
        let tasker = HospitalsTasker()
        tasker.getAllHospitals(failure: {
            print("Failure")
        }, success: { (hospitals) in
            guard let hospitals = hospitals else {
                self.hospitals = Array<HospitalIH>()
                DispatchQueue.main.async {
                    self.tableView.reloadData()
                }
                return
            }
            self.hospitals = hospitals
            DispatchQueue.main.async {
                self.tableView.reloadData()
            }
        })
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
        if (hospital.nedocsScore == NedocsScore(rawValue: "Overcrowded")) {
            cell.nedocsLabel.font = cell.nedocsLabel.font.withSize(11)
        } else {
            cell.nedocsLabel.font = cell.nedocsLabel.font.withSize(16)
        }
        if (hospital.hasDiversion) {
            cell.diversionIcon?.isHidden = false
            cell.diversionsHolder.isHidden = false
            cell.expandedDiversionIconLabel.isHidden = false

            let currDiversionIcon: UIImage?
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
            cell.expandedDiversionIcon?.image = currDiversionIcon
            cell.expandedDiversionIconLabel.text = ""
            for i in 0...(hospital.diversions.count - 1) {
                if (i != hospital.diversions.count - 1) {
                    cell.expandedDiversionIconLabel.text! += hospital.diversions[i] + "\n"
                } else {
                    cell.expandedDiversionIconLabel.text! += hospital.diversions[i]
                }
            }
            
        } else {
            // hide diversion icon when no diversion
            cell.diversionIcon?.isHidden = true
            cell.diversionIconWidth.constant = 0
            cell.diversionIconLeading.constant = 0
            
            // hide expanded info about diversion when no diversion
            cell.diversionsHolder.isHidden = true
            cell.expandedDiversionIconLabel.isHidden = true
        }

        if (hospital.specialtyCenters.count != 0) {
            for i in 0...(hospital.specialtyCenters.count - 1) {
                var currHospitalTypeIcon: UIImageView? = nil
                var currHospitalTypeHolder: UIStackView? = nil
                var currExpandedHospitalTypeIcon: UIImageView? = nil
                var currHospitalTypeLabel: UILabel? = nil

                switch i {
                case 0:
                    currHospitalTypeIcon = cell.hospitalTypeIcon1
                    currHospitalTypeHolder = cell.hospitalTypeHolder1
                    currExpandedHospitalTypeIcon = cell.expandedHospitalTypeIcon1
                    currHospitalTypeLabel = cell.hospitalTypeLabel1
                case 1:
                    currHospitalTypeIcon = cell.hospitalTypeIcon2
                    currHospitalTypeHolder = cell.hospitalTypeHolder2
                    currExpandedHospitalTypeIcon = cell.expandedHospitalTypeIcon2
                    currHospitalTypeLabel = cell.hospitalTypeLabel2
                case 2:
                    currHospitalTypeIcon = cell.hospitalTypeIcon3
                    currHospitalTypeHolder = cell.hospitalTypeHolder3
                    currExpandedHospitalTypeIcon = cell.expandedHospitalTypeIcon3
                    currHospitalTypeLabel = cell.hospitalTypeLabel3
                default:
                    break
                }
                
                if (currHospitalTypeIcon != nil && currExpandedHospitalTypeIcon != nil && currHospitalTypeLabel != nil) {
                    currHospitalTypeIcon?.isHidden = false
                    currHospitalTypeHolder?.isHidden = false
                    currExpandedHospitalTypeIcon?.isHidden = false
                    currHospitalTypeLabel?.isHidden = false
                    
                    currHospitalTypeIcon?.image = hospital.specialtyCenters[i].getHospitalIcon()
                    currExpandedHospitalTypeIcon?.image = hospital.specialtyCenters[i].getHospitalIcon()
                    currHospitalTypeLabel?.text = hospital.specialtyCenters[i].getHospitalDisplayString()
                }
            }
        }
        
        cell.distanceLabel.text = "\(String(hospital.distance)) mi"
        cell.address.attributedText = NSAttributedString(string: hospital.address, attributes:
            [.underlineStyle: NSUnderlineStyle.single.rawValue])
        cell.address.textColor = UIColor.blue
        cell.phoneNumber.attributedText = NSAttributedString(string: hospital.phoneNumber, attributes:
            [.underlineStyle: NSUnderlineStyle.single.rawValue])
        cell.phoneNumber.textColor = UIColor.blue
        cell.countyLabel.text = "\(String(hospital.county)) County - EMS Region \(String(hospital.regionNumber))"
        cell.rchLabel.text = "Regional Coordination Hospital \(String(hospital.rch))"
    
        return cell
    }

    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.row == selectedRowIndex && thereIsCellTapped {
            let hospital = self.hospitals[indexPath.row]
            if hospital.hasDiversion {
                return CGFloat(177 + 28 * (hospital.specialtyCenters.count) + (6 * (hospital.diversions.count - 1)))
            } else {
                return CGFloat(152 + 28 * hospital.specialtyCenters.count)
            }
        }
        return 90
    }
    
    @IBAction func expandButton(_ sender: UIButton) {
        guard let cell = sender.superview?.superview as? HomeTableViewCell else {
            return
        }

        guard let indexPath = tableView.indexPath(for: cell) else {
            return
        }
        
        if self.selectedRowIndex != -1 {
            self.tableView.cellForRow(at: NSIndexPath(row: self.selectedRowIndex, section: 0) as IndexPath)?.backgroundColor = UIColor.white
            guard let prevCell = self.tableView.cellForRow(at: NSIndexPath(row: self.selectedRowIndex, section: 0) as IndexPath) as? HomeTableViewCell else {
                return
            }
            prevCell.expandButton.setImage(UIImage(named:"ArrowIcon"), for: .normal)
            
        }

        if selectedRowIndex != indexPath.row {
            self.thereIsCellTapped = true
            self.selectedRowIndex = indexPath.row
            cell.expandButton.setImage(UIImage(named:"ArrowIconUp"), for: .normal)
        } else {
            self.thereIsCellTapped = false
            self.selectedRowIndex = -1
            cell.expandButton.setImage(UIImage(named:"ArrowIcon"), for: .normal)
        }
        self.tableView.beginUpdates()
        self.tableView.endUpdates()
    }
    
    
    @IBAction func favoritePin(_ sender: UIButton) {
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
    
}
