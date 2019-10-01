//
//  ProfileCell.swift
//  profile_test
//
//  Created by Jac on 2016/9/28.
//  Copyright © 2016年 Jac. All rights reserved.
//

import UIKit

class ProfileCell: UITableViewCell {
    
    @IBOutlet weak var mainImg: UIImage!
    @IBOutlet weak var mainLbl: UILabel!

    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
    func configureCell(text:String){
        mainLbl.text = text
    }

}
