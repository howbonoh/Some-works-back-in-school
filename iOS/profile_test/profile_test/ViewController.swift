//
//  ViewController.swift
//  profile_test
//
//  Created by Jac on 2016/9/28.
//  Copyright © 2016年 Jac. All rights reserved.
//

import UIKit
import Foundation

class ViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    @IBOutlet weak var tableview: UITableView!
    
    var profile : [String] = []

    override func viewDidLoad() {
        super.viewDidLoad()
        
        var request = URLRequest(url: URL(string: "http://140.113.72.25:8100/api/account/?format=json")!)
        request.httpMethod = "GET"
        
        URLSession.shared.dataTask(with: request) {data, response, err in
            
            do{
                let json = try! JSONSerialization.jsonObject(with: data!)
                
                if let Section = json as? NSArray{
                    let i = 1
                    if let profile_data = Section[i] as? NSDictionary{
                        let realname = profile_data["realname"]! as! String
                        let education = profile_data["education"]! as! Int
                        let language = profile_data["language"]! as! Int
                        let gender = profile_data["gender"]! as! Int
                        self.profile.append("用戶名稱: \(realname)")
                        self.profile.append("用戶性別: \(self.gender_decode(g: gender))")
                        self.profile.append("使用語言: \(self.language_decode(l: language))")
                        self.profile.append("教育程度: \(self.education_decode(e: education))")
                        self.tableview.reloadData()
                        print("this is my STR \(self.profile)")
                    }
                }
            }catch{
                print("Couldn't Serialize")
            }
            }.resume()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        if let cell = tableview.dequeueReusableCell(withIdentifier: "mycell") as? ProfileCell{
            
            cell.configureCell(text: profile[indexPath.row])
            
            return cell
        }
        else{
            return ProfileCell()
        }
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return profile.count
    }
    
    func education_decode(e: Int) -> String{
        switch e {
        case 0:
            return "學前教育"
        case 1:
            return "學前教育"
        case 2:
            return "國民中學"
        case 3:
            return "高級中學"
        case 4:
            return "專科（副學士）"
        case 5:
            return "大學（學士）"
        case 6:
            return "碩士"
        case 7:
            return "博士"
        default:
            return "SOME THING WENT WRONG"
        }
    }
    
    func language_decode(l: Int) -> String{
        switch l {
        case 1:
            return "客家話"
        case 2:
            return "閩南語"
        case 3:
            return "中文"
        case 4:
            return "英文"
        default:
            return "SOME THING WENT WRONG"
        }
    }
    
    func gender_decode(g: Int) -> String{
        if g==0{
            return "男"
        }
        else{
            return "女"
        }
    }
    
    func level_decode(l: Int) -> String{
        switch l {
        case 1:
            return "輕度視障"
        case 2:
            return "中度視障"
        case 3:
            return "高度視障"
        default:
            return "SOME THING WENT WRONG"
        }
    }
//    func uploadRequest(){
//        //get image Data from ImageView
//        let imageData:NSData = UIImagePNGRepresentation(picture.image!)!
//        //let strBase64 = String(imageData.base64EncodedDataWithOptions(.Encoding64CharacterLineLength))
//        let strBase64 = imageData.base64EncodedStringWithOptions(NSDataBase64EncodingOptions(rawValue: 0))
//        let request = NSMutableURLRequest(URL: NSURL(string: "http://140.113.72.29:8100/api/photo/")!)
//        let session = NSURLSession.sharedSession()
//        request.HTTPMethod = "POST"
//        let key = "http://140.113.72.29:8100/api/account/" + self.ID + "/"
//        let params = NSMutableDictionary()
//        params.setValue(key, forKey: "account")
//        params.setValue(1, forKey: "state")
//        //params.setValue(strBase64, forKey: "image")
//        print("json content")
//        print(params)
//        request.HTTPBody = try! NSJSONSerialization.dataWithJSONObject(params, options: [])
//        request.addValue("application/json", forHTTPHeaderField: "Content-Type")
//        
//        let task = session.dataTaskWithRequest(request,completionHandler: {data,response,error -> Void in
//            print("Response: \(response)")})
//        task.resume()
//    }

}
